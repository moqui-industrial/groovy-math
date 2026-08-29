/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.jax

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.libtorch.LibTorchPanama
import groovy.math.libtorch.LibTorchResult
import groovy.math.libtorch.PyTorch

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

@CompileStatic
@Tag('jax-native')
class JaxPanamaTest {

    @Test
    void testDirectMatmulViaMemorySegment() {
        JaxPanama panama = JaxPanama.INSTANCE

        try (Arena arena = Arena.ofConfined()) {
            // Matrix A: 2x3
            float[] aData = [1f, 2f, 3f, 4f, 5f, 6f] as float[]
            MemorySegment a = JaxPanama.allocateFloats(arena, aData)

            // Matrix B: 3x2
            float[] bData = [7f, 8f, 9f, 1f, 2f, 3f] as float[]
            MemorySegment b = JaxPanama.allocateFloats(arena, bData)

            // Output C: 2x2
            MemorySegment out = JaxPanama.allocateFloatBuffer(arena, 4L)

            panama.matmul(a, 2L, 3L, b, 3L, 2L, out)

            float[] result = out.toArray(ValueLayout.JAVA_FLOAT)
            assert result[0] == 31f
            assert result[1] == 19f
            assert result[2] == 85f
            assert result[3] == 55f
        }
    }

    @Test
    void testPlanWithJaxExecution() {
        JaxPanama panama = JaxPanama.INSTANCE

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
                MemorySegment inputSeg = JaxPanama.allocateFloats(arena, input)
                MemorySegment outputSeg = JaxPanama.allocateFloatBuffer(arena, 4L)

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
    void testCategoricalAttentionMaskViaJax() {
        JaxPanama panama = JaxPanama.INSTANCE

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
                MemorySegment inputSeg = JaxPanama.allocateFloats(arena, scores)
                MemorySegment outputSeg = JaxPanama.allocateFloatBuffer(arena, 6L)

                panama.executeSegment(planHandle, inputSeg, 2, outputSeg)

                float[] probs = outputSeg.toArray(ValueLayout.JAVA_FLOAT)
                assert probs[2] == 0.0f
                assert Math.abs((probs[0] + probs[1]) - 1.0f) < 1e-5
            }
        } finally {
            panama.destroy(planHandle)
        }
    }

    @Test
    void testJaxAndTorchParity() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?: '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
        MathMeta mathMeta = MathDsl.evaluate(
            new File(schemaPath),
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy'))

        // Execute on LibTorch
        LibTorchResult torchResult = PyTorch.execute(mathMeta, 'MatrixProduct') {
            input 'A', [[1, 2, 3], [4, 5, 6]]
        }

        // Execute on Google JAX
        LibTorchResult jaxResult = Jax.execute(mathMeta, 'MatrixProduct') {
            input 'A', [[1, 2, 3], [4, 5, 6]]
        }

        assert torchResult.tensorName == jaxResult.tensorName
        assert torchResult.batchSize == jaxResult.batchSize
        assert torchResult.width == jaxResult.width
        assert torchResult.values.toList() == jaxResult.values.toList()
        assert jaxResult.values.toList() == [58f, 64f, 139f, 154f]
    }
}
