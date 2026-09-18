/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.memory

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue
import org.moqui.math.model.Matrix
import org.moqui.math.model.Vector
import org.moqui.math.model.Tensor

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.nio.file.Path

import static org.junit.jupiter.api.Assertions.*

class ExternalContentTest {

    @TempDir
    Path tempDir

    @Test
    void testNpyMatrixExternalContent() {
        Path matFile = tempDir.resolve("matrix_2x3.npy")
        float[] matData = [1.5f, 2.5f, 3.5f, 4.5f, 5.5f, 6.5f] as float[]
        NpyReader.writeFloatArray(matFile, [2L, 3L], matData)

        assertTrue(matFile.toFile().exists())
        NpyReader.NpyHeader hdr = NpyReader.parseHeader(matFile)
        assertEquals([2L, 3L], hdr.shape)
        assertEquals(6L, hdr.totalElements)
        assertEquals("<f4", hdr.dtype)
        assertFalse(hdr.fortranOrder)

        MathMeta meta = MathDsl.fluent {
            matrix('MatA') {
                content(location: matFile.toAbsolutePath().toString())
            }
        }

        ModelValue valA = meta.entity('Matrix').findByName('MatA')
        assertNotNull(valA)
        Matrix m = new Matrix(valA)

        ModelValue valContent = meta.entity('MatrixContent').findByName('MatA_Content')
        assertNotNull(valContent)
        org.moqui.math.model.MatrixContent content = new org.moqui.math.model.MatrixContent(valContent)
        assertEquals(matFile.toAbsolutePath().toString(), content.contentLocation)

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment seg = NativeMemoryMapper.mapMatrix(arena, m, content)
            assertNotNull(seg)
            assertEquals(2L, m.rows)
            assertEquals(3L, m.cols)

            assertEquals(1.5f, seg.getAtIndex(ValueLayout.JAVA_FLOAT, 0L), 1e-5f)
            assertEquals(2.5f, seg.getAtIndex(ValueLayout.JAVA_FLOAT, 1L), 1e-5f)
            assertEquals(6.5f, seg.getAtIndex(ValueLayout.JAVA_FLOAT, 5L), 1e-5f)
        }
    }

    @Test
    void testNpyVectorExternalContent() {
        Path vecFile = tempDir.resolve("vector_4.npy")
        float[] vecData = [10.0f, 20.0f, 30.0f, 40.0f] as float[]
        NpyReader.writeFloatArray(vecFile, [4L], vecData)

        MathMeta meta = MathDsl.fluent {
            vector('VecB') {
                content(location: vecFile.toAbsolutePath().toString())
            }
        }

        ModelValue valB = meta.entity('Vector').findByName('VecB')
        assertNotNull(valB)
        Vector v = new Vector(valB)

        ModelValue valContent = meta.entity('VectorContent').findByName('VecB_Content')
        assertNotNull(valContent)
        org.moqui.math.model.VectorContent content = new org.moqui.math.model.VectorContent(valContent)
        assertEquals(vecFile.toAbsolutePath().toString(), content.contentLocation)

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment seg = NativeMemoryMapper.mapVector(arena, v, content)
            assertNotNull(seg)
            assertEquals(4L, v.dimension)

            assertEquals(10.0f, seg.getAtIndex(ValueLayout.JAVA_FLOAT, 0L), 1e-5f)
            assertEquals(40.0f, seg.getAtIndex(ValueLayout.JAVA_FLOAT, 3L), 1e-5f)
        }
    }

    @Test
    void testNpyTensorExternalContent() {
        Path tensorFile = tempDir.resolve("tensor_2x2x2.npy")
        float[] tensorData = [1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f] as float[]
        NpyReader.writeFloatArray(tensorFile, [2L, 2L, 2L], tensorData)

        MathMeta meta = MathDsl.fluent {
            tensor('TensC') {
                content(location: tensorFile.toAbsolutePath().toString())
            }
        }

        ModelValue valC = meta.entity('Tensor').findByName('TensC')
        assertNotNull(valC)
        Tensor t = new Tensor(valC)

        ModelValue valContent = meta.entity('TensorContent').findByName('TensC_Content')
        assertNotNull(valContent)
        org.moqui.math.model.TensorContent content = new org.moqui.math.model.TensorContent(valContent)
        assertEquals(tensorFile.toAbsolutePath().toString(), content.contentLocation)

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment seg = NativeMemoryMapper.mapTensor(arena, t, null, content)
            assertNotNull(seg)
            assertEquals("[2,2,2]", t.shape)
            assertEquals(3L, t.rank)
            assertEquals(8L, t.size)

            assertEquals(1.0f, seg.getAtIndex(ValueLayout.JAVA_FLOAT, 0L), 1e-5f)
            assertEquals(8.0f, seg.getAtIndex(ValueLayout.JAVA_FLOAT, 7L), 1e-5f)
        }
    }
}
