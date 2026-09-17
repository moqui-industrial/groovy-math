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
}
