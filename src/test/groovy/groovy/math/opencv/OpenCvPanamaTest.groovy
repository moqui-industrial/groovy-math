/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.opencv

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

@CompileStatic
@Tag('opencv-native')
class OpenCvPanamaTest {

    @Test
    void testDirectFilter2dViaMemorySegment() {
        OpenCvPanama panama = OpenCvPanama.INSTANCE

        try (Arena arena = Arena.ofConfined()) {
            // Source Image: 4x4 image
            float[] srcData = [
                10f, 10f, 10f, 10f,
                10f, 50f, 50f, 10f,
                10f, 50f, 50f, 10f,
                10f, 10f, 10f, 10f
            ] as float[]
            MemorySegment src = OpenCvPanama.allocateFloats(arena, srcData)

            // Kernel: 3x3 Mean / Average Filter (each element 1/9)
            float v = ((float) (1.0 / 9.0))
            float[] kernelData = [
                v, v, v,
                v, v, v,
                v, v, v
            ] as float[]
            MemorySegment kernel = OpenCvPanama.allocateFloats(arena, kernelData)

            // Destination: 4x4
            MemorySegment dst = OpenCvPanama.allocateFloatBuffer(arena, 16L)

            panama.filter2d(src, 4, 4, kernel, 3, 3, dst)

            float[] result = dst.toArray(ValueLayout.JAVA_FLOAT)
            assert result.length == 16
            // The central values should be smoothed
            assert result[5] > 10.0f && result[5] < 50.0f
        }
    }

    @Test
    void testDirectWarpAffineViaMemorySegment() {
        OpenCvPanama panama = OpenCvPanama.INSTANCE

        try (Arena arena = Arena.ofConfined()) {
            // Source Image: 3x3
            float[] srcData = [
                1f, 2f, 3f,
                4f, 5f, 6f,
                7f, 8f, 9f
            ] as float[]
            MemorySegment src = OpenCvPanama.allocateFloats(arena, srcData)

            // Identity 2x3 Affine Matrix
            double[] m2x3 = [
                1.0, 0.0, 0.0,
                0.0, 1.0, 0.0
            ] as double[]
            MemorySegment matrix = OpenCvPanama.allocateDoubles(arena, m2x3)

            // Output 3x3
            MemorySegment dst = OpenCvPanama.allocateFloatBuffer(arena, 9L)

            panama.warpAffine(src, 3, 3, matrix, 3, 3, dst)

            float[] result = dst.toArray(ValueLayout.JAVA_FLOAT)
            assert result.length == 9
            assert result[0] == 1f
            assert result[4] == 5f
            assert result[8] == 9f
        }
    }

    @Test
    void testGaussianBlurAndSobelPipeline() {
        OpenCvPanama panama = OpenCvPanama.INSTANCE

        long planHandle = panama.createPlan(4, 4)
        assert planHandle != 0L

        try {
            panama.addGaussianBlur(planHandle, 3, 1.0)
            panama.addSobel(planHandle, 1, 0, 3)
            panama.seal(planHandle, 4, 4)

            // Image with a vertical edge in the middle (cols 0,1 are dark=0, cols 2,3 are bright=100)
            float[] inputImage = [
                0f, 0f, 100f, 100f,
                0f, 0f, 100f, 100f,
                0f, 0f, 100f, 100f,
                0f, 0f, 100f, 100f
            ] as float[]

            float[] output = panama.execute(planHandle, inputImage)
            assert output.length == 16

            // Sobel in X direction detects strong horizontal gradient at the edge
            boolean hasGradient = false
            for (float val : output) {
                if (Math.abs(val) > 1.0f) hasGradient = true
            }
            assert hasGradient
        } finally {
            panama.destroy(planHandle)
        }
    }
}
