/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.pool

import groovy.math.libtorch.LibTorchPanama
import org.junit.jupiter.api.Test

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class ConcurrentEnginePoolTest {

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

        PanamaEngineFactory<NativeTorchEngine> factory = { -> new NativeTorchEngine(width) }
        PanamaEnginePool<NativeTorchEngine> pool = new PanamaEnginePool<>("TorchPool", factory, poolCapacity)

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
                    try (PooledSession<NativeTorchEngine> session = pool.acquire(5, TimeUnit.SECONDS)) {
                        NativeTorchEngine engine = session.engine()
                        assert engine.isValid()
                        float[] input = [workerId * 1.0f, -2.0f, 5.0f] as float[]
                        float[] output = LibTorchPanama.INSTANCE.execute(engine.nativeHandle(), input, 1)
                        // ReLU of [workerId, -2.0, 5.0]
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
        PanamaEngineFactory<NativeTorchEngine> factory = { -> new NativeTorchEngine(2) }

        PanamaEnginePool<NativeTorchEngine> pool = registry.getOrCreatePool("test-reg-pool", factory, 2)
        assert pool != null
        assert registry.getPool("test-reg-pool") == pool

        try (PooledSession<NativeTorchEngine> session = pool.acquire()) {
            assert session.engine().isValid()
        }

        registry.destroyPool("test-reg-pool")
        assert registry.getPool("test-reg-pool") == null
    }
}
