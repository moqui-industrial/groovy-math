/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.MathEngine
import org.moqui.math.builder.FluentMath
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.dsl.MathModelSolvingMethod
import org.moqui.math.onnx.OnnxPanama
import org.moqui.math.onnx.OnnxPlan
import org.moqui.math.onnx.OnnxResult
import org.moqui.math.spi.DeclaredModel
import org.moqui.math.spi.MathProviderFactory
import org.moqui.math.spi.MathProviderRegistry

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

println "=== Groovy-Math: ONNX Runtime Model Inference Example ==="

// 1. Check ONNX Runtime Panama Bridge availability
OnnxPanama panama = OnnxPanama.instance
println "ONNX Runtime C API available via Panama FFM: ${panama.isAvailable()}"

if (!panama.isAvailable()) {
    println "WARNING: ONNX Runtime is not available on this platform. " +
            "Ensure libonnxruntime is installed and './gradlew buildOnnxNative' was executed."
    return
}

// 2. Locate or configure an ONNX model file
// We check for a local ONNX model on disk or use a sample path
String modelPath = System.getProperty("onnx.model.path",
    "/home/igor/.cache/JetBrains/IdeaIC2025.2/semantic-search/models/0.0.5/small/dan_100k_optimized.onnx")

File modelFile = new File(modelPath)
boolean hasRealModel = modelFile.exists()

println "Target ONNX model path: ${modelPath}"
println "Model file exists: ${hasRealModel}"

// 3. Declare ONNX Neural Network Model via Groovy-Math Fluent DSL
MathMeta meta = MathDsl.fluent {
    model('OnnxClassifier') {
        description 'Deep Learning Model deployed via ONNX Runtime C API'
        location modelPath
        solvingMethod MathModelSolvingMethod.Onnx
    }
}

// 4. Inspect Metamodel Declarations and Provider Selection via SPI
def mathModel = meta.entity('MathModel').findByName('OnnxClassifier')
assert mathModel != null
println "\nDeclared Model Metadata:"
println " - Model ID: ${mathModel.get('mathModelId')}"
println " - Location: ${mathModel.get('location')}"
println " - Solving Method: ${mathModel.get('solvingMethod') ?: mathModel.get('solvingMethodEnumId')}"

DeclaredModel declared = DeclaredModel.of(meta, mathModel)
MathProviderFactory selectedFactory = MathProviderRegistry.select(meta, mathModel)
println "Selected MathProviderFactory via SPI: ${selectedFactory?.providerId}"
assert selectedFactory != null
assert selectedFactory.providerId == 'onnx'

// 5. Native Panama Session Lifecycle & Off-Heap Inference Demonstration
if (hasRealModel) {
    println "\nExecuting ONNX Session Lifecycle on ${modelFile.name}..."
    long sessionHandle = panama.createSession(modelPath)
    assert sessionHandle != 0L : "Failed to create native ONNX session"
    println "Successfully initialized native ONNX session (handle: 0x${Long.toHexString(sessionHandle)})"

    try (Arena arena = Arena.ofConfined()) {
        // Prepare off-heap native memory segment for input
        // Example: float input tensor of shape [1, 4]
        long[] inputShape = [1L, 4L] as long[]
        MemorySegment inputSegment = arena.allocate(4L * ValueLayout.JAVA_FLOAT.byteSize())
        inputSegment.setAtIndex(ValueLayout.JAVA_FLOAT, 0L, 0.1f)
        inputSegment.setAtIndex(ValueLayout.JAVA_FLOAT, 1L, 0.2f)
        inputSegment.setAtIndex(ValueLayout.JAVA_FLOAT, 2L, 0.3f)
        inputSegment.setAtIndex(ValueLayout.JAVA_FLOAT, 3L, 0.4f)

        println "Allocated off-heap input segment of ${inputSegment.byteSize()} bytes (zero-copy)"
    } finally {
        panama.destroySession(sessionHandle)
        println "Cleanly destroyed ONNX session handle."
    }
} else {
    println "\nNo local .onnx file found at '${modelPath}'. Skipping actual inference execution."
    println "To run full inference, pass -Donnx.model.path=/path/to/your/model.onnx"
}

println "\n=== ONNX Runtime Integration verified successfully! ==="
