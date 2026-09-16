/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.pool

import org.moqui.math.libtorch.LibTorchPanama
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * What is under test here is the pool: capacity, permit accounting, session lifecycle and
 * registry bookkeeping. None of that is LibTorch-specific, and driving it through a real
 * native plan would make `./gradlew check` require a C++ toolchain on a fresh clone. The
 * concurrency tests therefore use an in-JVM engine, and a separate libtorch-native test
 * proves the same pool works with a real Panama engine.
 */
class ConcurrentEnginePoolTest {

    /** Stand-in engine: applies ReLU on the JVM side, with no native handle behind it. */
    static class FakeEngine implements PanamaEngine {
        private static final AtomicInteger HANDLES = new AtomicInteger()

        final long handle = HANDLES.incrementAndGet()
        private volatile boolean destroyed = false

        @Override
        long nativeHandle() { return handle }

        @Override
        boolean isValid() { return !destroyed }

        @Override
        void destroy() { destroyed = true }

        float[] relu(final float[] input) {
            float[] output = new float[input.length]
            for (int i = 0; i < input.length; i++) output[i] = Math.max(0.0f, input[i])
            output
        }
    }

    static class NativeTorchEngine implements PanamaEngine {
        final long handle
        private boolean destroyed = false

        NativeTorchEngine(int width) {
            LibTorchPanama torch = LibTorchPanama.INSTANCE
            this.handle = torch.createPlan(width)
            torch.addRelu(this.handle, 0, 1)
            torch.seal(this.handle, 1, width)
        }

        @Override
        long nativeHandle() { return handle }

        @Override
        boolean isValid() { return !destroyed && handle != 0 }

        @Override
        void destroy() {
            if (!destroyed) {
                LibTorchPanama.INSTANCE.destroy(handle)
                destroyed = true
            }
        }
    }

    @Test
    void testConcurrentExecutionWithPool() {
        int poolCapacity = 4
        int totalWorkers = 16
        int width = 3

        PanamaEngineFactory<FakeEngine> factory = { -> new FakeEngine() }
        PanamaEnginePool<FakeEngine> pool = new PanamaEnginePool<>("FakePool", factory, poolCapacity)

        ExecutorService executor = Executors.newFixedThreadPool(8)
        CountDownLatch startLatch = new CountDownLatch(1)
        CountDownLatch doneLatch = new CountDownLatch(totalWorkers)
        AtomicInteger successCounter = new AtomicInteger(0)
        List<Throwable> errors = Collections.synchronizedList(new ArrayList<>())

        for (int i = 0; i < totalWorkers; i++) {
            final int workerId = i
            executor.submit({
                try {
                    startLatch.await()
                    try (PooledSession<FakeEngine> session = pool.acquire(5, TimeUnit.SECONDS)) {
                        FakeEngine engine = session.engine()
                        assert engine.isValid()
                        float[] input = [workerId * 1.0f, -2.0f, 5.0f] as float[]
                        float[] output = engine.relu(input)
                        assert output[0] == Math.max(0.0f, workerId * 1.0f)
                        assert output[1] == 0.0f
                        assert output[2] == 5.0f
                        successCounter.incrementAndGet()
                    }
                } catch (Throwable t) {
                    errors.add(t)
                } finally {
                    doneLatch.countDown()
                }
            })
        }

        // Fire all threads simultaneously
        startLatch.countDown()
        boolean finished = doneLatch.await(10, TimeUnit.SECONDS)
        executor.shutdown()

        assert finished : "Not all workers finished in time"
        assert errors.isEmpty() : "Errors during concurrent execution: " + errors
        assert successCounter.get() == totalWorkers : "Expected " + totalWorkers + " successful runs, got: " + successCounter.get()
        assert pool.getTotalCreated() <= poolCapacity : "Created engines (" + pool.getTotalCreated() + ") exceeded capacity (" + poolCapacity + ")"
        assert pool.getAvailablePermits() == poolCapacity : "Permits were leaked"

        pool.close()
    }

    @Test
    void testRegistryLifecycle() {
        PanamaPoolRegistry registry = PanamaPoolRegistry.getInstance()
        PanamaEngineFactory<FakeEngine> factory = { -> new FakeEngine() }

        PanamaEnginePool<FakeEngine> pool = registry.getOrCreatePool("test-reg-pool", factory, 2)
        assert pool != null
        assert registry.getPool("test-reg-pool") == pool

        try (PooledSession<FakeEngine> session = pool.acquire()) {
            assert session.engine().isValid()
        }

        registry.destroyPool("test-reg-pool")
        assert registry.getPool("test-reg-pool") == null
    }

    @Test
    @Tag('libtorch-native')
    void poolsRealPanamaEnginesConcurrently() {
        int poolCapacity = 2
        int width = 3

        PanamaEngineFactory<NativeTorchEngine> factory = { -> new NativeTorchEngine(width) }
        PanamaEnginePool<NativeTorchEngine> pool = new PanamaEnginePool<>("TorchPool", factory, poolCapacity)
        try {
            try (PooledSession<NativeTorchEngine> session = pool.acquire(5, TimeUnit.SECONDS)) {
                NativeTorchEngine engine = session.engine()
                assert engine.isValid()
                float[] output = LibTorchPanama.INSTANCE.execute(engine.nativeHandle(), [1.0f, -2.0f, 5.0f] as float[], 1)
                assert output[0] == 1.0f
                assert output[1] == 0.0f
                assert output[2] == 5.0f
            }
            assert pool.getAvailablePermits() == poolCapacity : 'Permits were leaked'
        } finally {
            pool.close()
        }
    }
}
