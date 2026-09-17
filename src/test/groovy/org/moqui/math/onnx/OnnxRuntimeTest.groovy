/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.onnx

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.spi.DeclaredModel

import static org.junit.jupiter.api.Assertions.*

@Tag('onnx-native')
class OnnxRuntimeTest {

    @Test
    void testOnnxPanamaAvailability() {
        OnnxPanama panama = OnnxPanama.instance
        assertNotNull(panama)
        println "ONNX Runtime available: " + panama.isAvailable()
    }

    @Test
    void testOnnxProviderFactoryClaims() {
        MathMeta meta = MathDsl.fluent {
            model('VisionModel') {
                location 'models/resnet50.onnx'
            }
        }

        OnnxProviderFactory factory = new OnnxProviderFactory()
        assertEquals('onnx', factory.providerId)
        DeclaredModel declared = DeclaredModel.of(meta, meta.entity('MathModel').findByName('VisionModel'))
        assertTrue(factory.claims(meta, declared))
    }

    @Test
    void testOnnxSessionLifecycleIfModelExists() {
        OnnxPanama panama = OnnxPanama.instance
        if (!panama.isAvailable()) return

        File testModel = new File('/home/igor/.cache/JetBrains/IdeaIC2025.2/semantic-search/models/0.0.5/small/dan_100k_optimized.onnx')
        if (!testModel.exists()) return

        long handle = panama.createSession(testModel.absolutePath)
        assertTrue(handle != 0L, "Failed to create ONNX session for test model")
        panama.destroySession(handle)
    }

    @Test
    void testOnnxConcurrentLifecycleNoCrash() {
        OnnxPanama panama = OnnxPanama.instance
        if (!panama.isAvailable()) return

        File testModel = new File('/home/igor/.cache/JetBrains/IdeaIC2025.2/semantic-search/models/0.0.5/small/dan_100k_optimized.onnx')
        if (!testModel.exists()) return

        long handle = panama.createSession(testModel.absolutePath)
        assertTrue(handle != 0L, "Failed to create ONNX session for test model")

        int threadsCount = 8
        java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(threadsCount + 1)
        java.util.concurrent.CountDownLatch startLatch = new java.util.concurrent.CountDownLatch(1)
        java.util.concurrent.atomic.AtomicInteger fatalErrors = new java.util.concurrent.atomic.AtomicInteger()

        List<java.util.concurrent.Future<?>> futures = []
        for (int i = 0; i < threadsCount; i++) {
            futures.add(executor.submit({
                startLatch.await()
                try (java.lang.foreign.Arena arena = java.lang.foreign.Arena.ofConfined()) {
                    java.lang.foreign.MemorySegment inSeg = arena.allocate(16L * Float.BYTES)
                    java.lang.foreign.MemorySegment outSeg = arena.allocate(16L * Float.BYTES)
                    long[] shape = [1L, 16L] as long[]
                    for (int iter = 0; iter < 50; iter++) {
                        try {
                            panama.run(handle, null, inSeg, shape, null, outSeg, 16L)
                        } catch (RuntimeException re) {
                            // Expected when session destroyed or invalid handle/shape
                            break
                        } catch (Throwable t) {
                            fatalErrors.incrementAndGet()
                            break
                        }
                    }
                }
            }))
        }

        futures.add(executor.submit({
            startLatch.await()
            Thread.sleep(5)
            panama.destroySession(handle)
        }))

        startLatch.countDown()
        for (java.util.concurrent.Future<?> f : futures) {
            f.get(10, java.util.concurrent.TimeUnit.SECONDS)
        }
        executor.shutdown()

        assertEquals(0, fatalErrors.get(), "No JVM crashes or unexpected non-RuntimeExceptions")
    }
}

