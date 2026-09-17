/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue

@CompileStatic
@Tag('libtorch-native')
class LibTorchNativeLifecycleTest {

    @Test
    void doubleDestroyIsIdempotentAndSafe() {
        LibTorchPanama panama = LibTorchPanama.INSTANCE
        long handle = panama.createPlan(4)
        assertTrue(handle > 0L)

        // First destroy should succeed cleanly
        assertDoesNotThrow({
            panama.destroy(handle)
        } as org.junit.jupiter.api.function.Executable)

        // Second destroy on the exact same handle must be idempotent and safe (no segfault)
        assertDoesNotThrow({
            panama.destroy(handle)
        } as org.junit.jupiter.api.function.Executable)

        // Destroying zero or negative handles must also be safe
        assertDoesNotThrow({
            panama.destroy(0L)
            panama.destroy(-1L)
            panama.destroy(99999999L)
        } as org.junit.jupiter.api.function.Executable)
    }

    @Test
    void operationsOnDestroyedHandleFailGracefullyWithoutCrash() {
        LibTorchPanama panama = LibTorchPanama.INSTANCE
        long handle = panama.createPlan(4)
        assertTrue(handle > 0L)

        panama.destroy(handle)

        // Operations on destroyed handle must throw a descriptive RuntimeException, not crash JVM
        Exception ex1 = assertThrows(RuntimeException) {
            panama.seal(handle, 0, 4)
        }
        assertTrue(ex1.message.contains("Plan handle not found or already destroyed") ||
                   ex1.message.contains("failed"),
                   "Expected destroyed handle error, got: ${ex1.message}")

        Exception ex2 = assertThrows(RuntimeException) {
            panama.addAffine(handle, 0, 1, 4, 4,
                [1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f] as float[],
                [0f, 0f, 0f, 0f] as float[])
        }
        assertTrue(ex2.message.contains("Plan handle not found or already destroyed") ||
                   ex2.message.contains("failed"),
                   "Expected destroyed handle error, got: ${ex2.message}")

        Exception ex3 = assertThrows(RuntimeException) {
            panama.execute(handle, [1f, 2f, 3f, 4f] as float[], 1)
        }
        assertTrue(ex3.message != null)
    }

    @Test
    void nonExistentHandleFailsGracefully() {
        LibTorchPanama panama = LibTorchPanama.INSTANCE
        long bogusHandle = 88888888L

        Exception ex = assertThrows(RuntimeException) {
            panama.seal(bogusHandle, 0, 2)
        }
        assertTrue(ex.message.contains("Plan handle not found or already destroyed") ||
                   ex.message.contains("failed"))
    }

    @Test
    void concurrentExecuteAndDestroyNoCrash() {
        LibTorchPanama panama = LibTorchPanama.INSTANCE
        long handle = panama.createPlan(4)
        panama.addAffine(handle, 0, 1, 4, 4,
            [1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f] as float[],
            [0f, 0f, 0f, 0f] as float[])
        panama.seal(handle, 1, 4)

        int threadsCount = 10
        java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(threadsCount + 1)
        java.util.concurrent.CountDownLatch startLatch = new java.util.concurrent.CountDownLatch(1)
        java.util.concurrent.atomic.AtomicInteger successes = new java.util.concurrent.atomic.AtomicInteger()
        java.util.concurrent.atomic.AtomicInteger controlledErrors = new java.util.concurrent.atomic.AtomicInteger()
        java.util.concurrent.atomic.AtomicInteger fatalErrors = new java.util.concurrent.atomic.AtomicInteger()

        List<java.util.concurrent.Future<?>> futures = []
        for (int i = 0; i < threadsCount; i++) {
            futures.add(executor.submit({
                startLatch.await()
                float[] input = [1f, 2f, 3f, 4f] as float[]
                for (int iter = 0; iter < 100; iter++) {
                    try {
                        float[] out = panama.execute(handle, input, 1)
                        if (out != null && out.length == 4) successes.incrementAndGet()
                    } catch (RuntimeException re) {
                        controlledErrors.incrementAndGet()
                        break
                    } catch (Throwable t) {
                        fatalErrors.incrementAndGet()
                        break
                    }
                }
            }))
        }

        futures.add(executor.submit({
            startLatch.await()
            Thread.sleep(5)
            panama.destroy(handle)
        }))

        startLatch.countDown()
        for (java.util.concurrent.Future<?> f : futures) {
            f.get(10, java.util.concurrent.TimeUnit.SECONDS)
        }
        executor.shutdown()

        assertTrue(fatalErrors.get() == 0, "No fatal non-runtime errors or SIGSEGV allowed")
        assertTrue(successes.get() + controlledErrors.get() > 0, "All threads completed or caught controlled errors")
    }

    @Test
    void concurrentPlanExecuteAndCloseNoCrash() {
        LibTorchPanama panama = LibTorchPanama.INSTANCE
        long handle = panama.createPlan(4)
        panama.addAffine(handle, 0, 1, 4, 4,
            [1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f] as float[],
            [0f, 0f, 0f, 0f] as float[])
        panama.seal(handle, 1, 4)

        LibTorchPlan plan = new LibTorchPlan('testModel', 'input', 'output', 1, 4, 4, 1, panama, handle)
        int threadsCount = 10
        java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(threadsCount + 1)
        java.util.concurrent.CountDownLatch startLatch = new java.util.concurrent.CountDownLatch(1)
        java.util.concurrent.atomic.AtomicInteger successes = new java.util.concurrent.atomic.AtomicInteger()
        java.util.concurrent.atomic.AtomicInteger planClosedErrors = new java.util.concurrent.atomic.AtomicInteger()
        java.util.concurrent.atomic.AtomicInteger fatalErrors = new java.util.concurrent.atomic.AtomicInteger()

        List<java.util.concurrent.Future<?>> futures = []
        for (int i = 0; i < threadsCount; i++) {
            futures.add(executor.submit({
                startLatch.await()
                float[] input = [1f, 2f, 3f, 4f] as float[]
                for (int iter = 0; iter < 100; iter++) {
                    try {
                        LibTorchResult out = plan.execute(input)
                        if (out != null && out.values != null) successes.incrementAndGet()
                    } catch (IllegalStateException ise) {
                        planClosedErrors.incrementAndGet()
                        break
                    } catch (Throwable t) {
                        t.printStackTrace()
                        fatalErrors.incrementAndGet()
                        break
                    }
                }
            }))
        }

        futures.add(executor.submit({
            startLatch.await()
            Thread.sleep(5)
            plan.close()
        }))

        startLatch.countDown()
        for (java.util.concurrent.Future<?> f : futures) {
            f.get(10, java.util.concurrent.TimeUnit.SECONDS)
        }
        executor.shutdown()

        assertTrue(fatalErrors.get() == 0, "No unexpected crashes allowed")
        assertTrue(successes.get() + planClosedErrors.get() > 0)
    }
}
