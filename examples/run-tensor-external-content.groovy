/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.builder.FluentMath
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.dsl.MathSpace
import org.moqui.math.dsl.MatrixPurpose
import org.moqui.math.dsl.MatrixType
import org.moqui.math.memory.NativeMemoryMapper
import org.moqui.math.memory.NpyReader
import org.moqui.math.model.Matrix
import org.moqui.math.model.Tensor
import org.moqui.math.model.Vector

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.nio.file.Files
import java.nio.file.Path

println "=== Groovy-Math: External Content Loading Example (Matrix, Vector, Tensor) ==="

// 1. Prepare sample external data files (.npy format)
Path tempDir = Files.createTempDirectory("groovy_math_external_")
Path matrixFile = tempDir.resolve("weights_2x3.npy")
Path vectorFile = tempDir.resolve("bias_2.npy")
Path tensorFile = tempDir.resolve("feature_map_2x2x3.npy")

try {
    // Write 2x3 matrix: [[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]
    float[] matrixData = [1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f] as float[]
    NpyReader.writeFloatArray(matrixFile, [2L, 3L], matrixData)

    // Write 1D vector (dim 2): [0.5, -0.5]
    float[] vectorData = [0.5f, -0.5f] as float[]
    NpyReader.writeFloatArray(vectorFile, [2L], vectorData)

    // Write 3D tensor (2x2x3): 12 elements
    float[] tensorData = (1..12).collect { (float) it } as float[]
    NpyReader.writeFloatArray(tensorFile, [2L, 2L, 3L], tensorData)

    println "Generated test .npy files in ${tempDir}"

    // 2. Define entities using Fluent DSL
    MathMeta meta = MathDsl.fluent {
        matrix('Weights') {
            matrixType MatrixType.Dense
            purpose MatrixPurpose.Original
            domainSpace MathSpace.R3
            codomainSpace MathSpace.R2
            rows 2L
            cols 3L
            componentArray "[1.0, 2.0, 3.0, 4.0, 5.0, 6.0]"
        }

        vector('Bias') {
            domainSpace MathSpace.R2
            size 2L
            componentArray "[0.5, -0.5]"
        }

        tensor('FeatureMap') {
            contentLocation tensorFile.toAbsolutePath().toString()
            contentType 'TCntNpy'
        }
    }

    println "Declared entities in metamodel:"
    println " - Matrix: Weights -> ${meta.entity('Matrix').findByName('Weights') != null}"
    println " - Vector: Bias -> ${meta.entity('Vector').findByName('Bias') != null}"
    println " - Tensor: FeatureMap -> ${meta.entity('Tensor').findByName('FeatureMap') != null}"
    println " - TensorContent: FeatureMap_Content -> ${meta.entity('TensorContent').findByName('FeatureMap_Content') != null}"

    // 3. Map entities off-heap zero-copy using Panama FFM Arena
    try (Arena arena = Arena.ofConfined()) {
        Matrix matrix = new Matrix(meta.entity('Matrix').findByName('Weights').toMap())
        Vector vector = new Vector(meta.entity('Vector').findByName('Bias').toMap())
        Tensor tensor = new Tensor(meta.entity('Tensor').findByName('FeatureMap').toMap())
        org.moqui.math.model.TensorContent tensorContent = new org.moqui.math.model.TensorContent(meta.entity('TensorContent').findByName('FeatureMap_Content').toMap())

        MemorySegment matrixSegment = NativeMemoryMapper.mapMatrix(arena, matrix)
        MemorySegment vectorSegment = NativeMemoryMapper.mapVector(arena, vector)
        MemorySegment tensorSegment = NativeMemoryMapper.mapTensor(arena, tensor, null, tensorContent)

        println "\nOff-heap Memory Mappings created:"
        println " Matrix rows: ${matrix.rows}, cols: ${matrix.cols}, bytes: ${matrixSegment.byteSize()}"
        println " Vector dimension: ${vector.dimension}, bytes: ${vectorSegment.byteSize()}"
        println " Tensor shape: [${tensor.shape}], rank: ${tensor.rank}, totalElements: ${tensor.size}, bytes: ${tensorSegment.byteSize()}"

        // Validate values read directly from native memory
        assert matrix.rows == 2L && matrix.cols == 3L
        assert matrixSegment.getAtIndex(ValueLayout.JAVA_FLOAT, 0L) == 1.0f
        assert matrixSegment.getAtIndex(ValueLayout.JAVA_FLOAT, 5L) == 6.0f

        assert vector.dimension == 2L
        assert vectorSegment.getAtIndex(ValueLayout.JAVA_FLOAT, 0L) == 0.5f
        assert vectorSegment.getAtIndex(ValueLayout.JAVA_FLOAT, 1L) == -0.5f

        assert tensor.rank == 3L
        assert tensor.size == 12L
        assert tensorSegment.getAtIndex(ValueLayout.JAVA_FLOAT, 0L) == 1.0f
        assert tensorSegment.getAtIndex(ValueLayout.JAVA_FLOAT, 11L) == 12.0f

        // 4. Perform a linear transformation calculation: y = W * x + b
        // where input x = [1.0, 1.0, 1.0]
        float[] inputX = [1.0f, 1.0f, 1.0f] as float[]
        float[] resultY = new float[2]

        for (int r = 0; r < 2; r++) {
            float sum = 0.0f
            for (int c = 0; c < 3; c++) {
                float w = matrixSegment.getAtIndex(ValueLayout.JAVA_FLOAT, (long) (r * 3 + c))
                sum += w * inputX[c]
            }
            float b = vectorSegment.getAtIndex(ValueLayout.JAVA_FLOAT, (long) r)
            resultY[r] = sum + b
        }

        println "\nComputed Linear Layer y = W * x + b:"
        println " Input x: ${inputX}"
        println " Output y: ${resultY}"
        // Row 0: 1*1 + 2*1 + 3*1 + 0.5 = 6.5
        // Row 1: 4*1 + 5*1 + 6*1 - 0.5 = 14.5
        assert Math.abs(resultY[0] - 6.5f) < 1e-5
        assert Math.abs(resultY[1] - 14.5f) < 1e-5

        println "Verification successful: All external files loaded zero-copy and computed correctly!"
    }
} finally {
    Files.deleteIfExists(matrixFile)
    Files.deleteIfExists(vectorFile)
    Files.deleteIfExists(tensorFile)
    Files.deleteIfExists(tempDir)
}
