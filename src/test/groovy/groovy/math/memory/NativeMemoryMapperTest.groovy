/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.memory

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import groovy.math.libtorch.LibTorchPanama
import groovy.math.model.Matrix
import groovy.math.model.MatrixComponent
import groovy.math.model.Tensor
import groovy.math.model.TensorContent
import groovy.math.model.TensorElement
import groovy.math.model.Vector
import groovy.math.model.VectorComponent

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.nio.file.Files
import java.nio.file.Path

@CompileStatic
@Tag('libtorch-native')
class NativeMemoryMapperTest {

    @Test
    void testMapAndUnmapMatrixWithLibTorchMatmul() {
        LibTorchPanama panama = LibTorchPanama.INSTANCE

        // Matrix A: 2x3
        Matrix matrixA = new Matrix(matrixId: 'A', rows: 2L, cols: 3L)
        matrixA.components = [
            new MatrixComponent(rowIndex: 0L, colIndex: 0L, realValue: 1.0),
            new MatrixComponent(rowIndex: 0L, colIndex: 1L, realValue: 2.0),
            new MatrixComponent(rowIndex: 0L, colIndex: 2L, realValue: 3.0),
            new MatrixComponent(rowIndex: 1L, colIndex: 0L, realValue: 4.0),
            new MatrixComponent(rowIndex: 1L, colIndex: 1L, realValue: 5.0),
            new MatrixComponent(rowIndex: 1L, colIndex: 2L, realValue: 6.0)
        ]

        // Matrix B: 3x2
        Matrix matrixB = new Matrix(matrixId: 'B', rows: 3L, cols: 2L)
        matrixB.components = [
            new MatrixComponent(rowIndex: 0L, colIndex: 0L, realValue: 7.0),
            new MatrixComponent(rowIndex: 0L, colIndex: 1L, realValue: 8.0),
            new MatrixComponent(rowIndex: 1L, colIndex: 0L, realValue: 9.0),
            new MatrixComponent(rowIndex: 1L, colIndex: 1L, realValue: 1.0),
            new MatrixComponent(rowIndex: 2L, colIndex: 0L, realValue: 2.0),
            new MatrixComponent(rowIndex: 2L, colIndex: 1L, realValue: 3.0)
        ]

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segA = NativeMemoryMapper.mapMatrix(arena, matrixA)
            MemorySegment segB = NativeMemoryMapper.mapMatrix(arena, matrixB)
            MemorySegment segOut = LibTorchPanama.allocateFloatBuffer(arena, 4L)

            // C = A * B in LibTorch C++ via Panama
            panama.matmul(segA, 2L, 3L, segB, 3L, 2L, segOut)

            // Unmap native output memory back into Groovy Matrix entity
            Matrix matrixC = NativeMemoryMapper.unmapMatrix(segOut, 'C', 2, 2)

            assert matrixC.rows == 2L
            assert matrixC.cols == 2L
            assert matrixC.components.size() == 4
            assert matrixC.components[0].realValue == 31.0
            assert matrixC.components[1].realValue == 19.0
            assert matrixC.components[2].realValue == 85.0
            assert matrixC.components[3].realValue == 55.0
        }
    }

    @Test
    void testMapDiscreteTensorElementsForPhysics() {
        // Discrete 3D stress tensor [3, 3]
        Tensor stressTensor = new Tensor(tensorId: 'StressTensor', rank: 2L, shape: '3,3', size: 9L)
        stressTensor.elements = [
            new TensorElement(tensorId: 'StressTensor', linearIndex: 0L, realValue: 100.0),
            new TensorElement(tensorId: 'StressTensor', linearIndex: 4L, realValue: 200.0),
            new TensorElement(tensorId: 'StressTensor', linearIndex: 8L, realValue: 300.0)
        ]

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = NativeMemoryMapper.mapTensor(arena, stressTensor)
            assert segment.byteSize() == 9L * 4L
            assert segment.getAtIndex(ValueLayout.JAVA_FLOAT, 0L) == 100.0f
            assert segment.getAtIndex(ValueLayout.JAVA_FLOAT, 4L) == 200.0f
            assert segment.getAtIndex(ValueLayout.JAVA_FLOAT, 8L) == 300.0f
            assert segment.getAtIndex(ValueLayout.JAVA_FLOAT, 1L) == 0.0f
        }
    }

    @Test
    void testMapLargeExternalFileViaTensorContent() {
        // Create a temporary binary file mimicking an ONNX / large weight file
        Path tempFile = Files.createTempFile("large_weights", ".bin")
        try {
            float[] weights = [0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f] as float[]
            byte[] bytes = new byte[weights.length * 4]
            java.nio.ByteBuffer.wrap(bytes).order(java.nio.ByteOrder.nativeOrder()).asFloatBuffer().put(weights)
            Files.write(tempFile, bytes)

            Tensor nnWeightTensor = new Tensor(
                tensorId: 'Layer1Weights',
                rank: 2L,
                shape: '2,4',
                size: 8L
            )
            TensorContent content = new TensorContent(
                tensorContentId: 'Layer1Weights_Content',
                tensorId: 'Layer1Weights',
                contentLocation: tempFile.toAbsolutePath().toString(),
                contentTypeEnumId: 'TctOnnx'
            )

            try (Arena arena = Arena.ofConfined()) {
                // Memory-mapped zero copy
                MemorySegment segment = NativeMemoryMapper.mapTensor(arena, nnWeightTensor, null, content)
                assert segment.byteSize() == (long) bytes.length
                assert segment.getAtIndex(ValueLayout.JAVA_FLOAT, 0L) == 0.1f
                assert segment.getAtIndex(ValueLayout.JAVA_FLOAT, 7L) == 0.8f
            }
        } finally {
            Files.deleteIfExists(tempFile)
        }
    }

    @Test
    void testMapVector() {
        Vector vector = new Vector(vectorId: 'V1', dimension: 3L)
        vector.components = [
            new VectorComponent(dimensionIndex: 0L, realValue: 10.5),
            new VectorComponent(dimensionIndex: 1L, realValue: 20.5),
            new VectorComponent(dimensionIndex: 2L, realValue: 30.5)
        ]

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = NativeMemoryMapper.mapVector(arena, vector)
            assert segment.getAtIndex(ValueLayout.JAVA_FLOAT, 0L) == 10.5f
            assert segment.getAtIndex(ValueLayout.JAVA_FLOAT, 1L) == 20.5f
            assert segment.getAtIndex(ValueLayout.JAVA_FLOAT, 2L) == 30.5f
        }
    }
}
