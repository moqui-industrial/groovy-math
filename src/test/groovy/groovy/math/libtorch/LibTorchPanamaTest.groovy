/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.libtorch

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

@CompileStatic
@Tag('libtorch-native')
class LibTorchPanamaTest {

    @Test
    void testDirectMatmulViaMemorySegment() {
        LibTorchPanama panama = LibTorchPanama.INSTANCE

        try (Arena arena = Arena.ofConfined()) {
            // Matrix A: 2x3
            float[] aData = [1f, 2f, 3f, 4f, 5f, 6f] as float[]
            MemorySegment a = LibTorchPanama.allocateFloats(arena, aData)

            // Matrix B: 3x2
            float[] bData = [7f, 8f, 9f, 1f, 2f, 3f] as float[]
            MemorySegment b = LibTorchPanama.allocateFloats(arena, bData)

            // Output C: 2x2 (4 floats)
            MemorySegment out = LibTorchPanama.allocateFloatBuffer(arena, 4L)

            panama.matmul(a, 2L, 3L, b, 3L, 2L, out)

            float[] result = out.toArray(ValueLayout.JAVA_FLOAT)
            assert result[0] == 31f
            assert result[1] == 19f
            assert result[2] == 85f
            assert result[3] == 55f
        }
    }

    @Test
    void testPlanWithPanamaExecution() {
        LibTorchPanama panama = LibTorchPanama.INSTANCE

        long planHandle = panama.createPlan(3)
        assert planHandle != 0L

        try {
            // Add matrix product with 3x2 matrix
            float[] right = [7f, 8f, 9f, 1f, 2f, 3f] as float[]
            panama.addMatrixProduct(planHandle, 0, 1, 3, 2, right)
            panama.seal(planHandle, 1, 2)

            // Execute batch of 2 rows (input width 3)
            float[] input = [1f, 2f, 3f, 4f, 5f, 6f] as float[]
            try (Arena arena = Arena.ofConfined()) {
                MemorySegment inputSeg = LibTorchPanama.allocateFloats(arena, input)
                MemorySegment outputSeg = LibTorchPanama.allocateFloatBuffer(arena, 4L)

                panama.executeSegment(planHandle, inputSeg, 2, outputSeg)

                float[] result = outputSeg.toArray(ValueLayout.JAVA_FLOAT)
                assert result[0] == 31f
                assert result[1] == 19f
                assert result[2] == 85f
                assert result[3] == 55f
            }
        } finally {
            panama.destroy(planHandle)
        }
    }

    @Test
    void testCategoricalAttentionMaskViaPanama() {
        LibTorchPanama panama = LibTorchPanama.INSTANCE

        long planHandle = panama.createPlan(3)
        assert planHandle != 0L

        try {
            // Mask with -infinity for forbidden state transition at index (0, 2)
            float[] mask = [
                0f, 0f, Float.NEGATIVE_INFINITY,
                0f, 0f, 0f
            ] as float[]

            panama.addAttentionMask(planHandle, 0, 1, 2L, 3L, mask)
            panama.addSoftmax(planHandle, 1, 2, -1L)
            panama.seal(planHandle, 2, 3)

            // Input attention scores: 2x3
            float[] scores = [
                1.0f, 2.0f, 10.0f,
                1.0f, 1.0f, 1.0f
            ] as float[]

            try (Arena arena = Arena.ofConfined()) {
                MemorySegment inputSeg = LibTorchPanama.allocateFloats(arena, scores)
                MemorySegment outputSeg = LibTorchPanama.allocateFloatBuffer(arena, 6L)

                panama.executeSegment(planHandle, inputSeg, 2, outputSeg)

                float[] probs = outputSeg.toArray(ValueLayout.JAVA_FLOAT)
                // In row 0, the 3rd element had score 10.0 but was masked with -inf
                // Softmax(-inf) must be 0.0!
                assert probs[2] == 0.0f
                // Total prob of row 0 must sum to ~1.0
                assert Math.abs((probs[0] + probs[1]) - 1.0f) < 1e-5
            }
        } finally {
            panama.destroy(planHandle)
        }
    }
}
