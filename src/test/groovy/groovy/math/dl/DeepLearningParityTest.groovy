/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.dl

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import groovy.math.libtorch.LibTorchPanama
import groovy.math.jax.JaxPanama

@CompileStatic
@Tag('libtorch-native')
@Tag('jax-native')
class DeepLearningParityTest {

    @Test
    void testDirectTensorOpParity() {
        LibTorchPanama torch = LibTorchPanama.INSTANCE
        JaxPanama jax = JaxPanama.INSTANCE

        float[] input = [-2.5f, -1.0f, -0.5f, 0.0f, 0.5f, 1.0f, 2.5f] as float[]
        int n = input.length
        float[] torchOut = new float[n]
        float[] jaxOut = new float[n]

        // Test 1: GELU
        torch.tensorOp(1, input, torchOut, 0.0f)
        jax.tensorOp(1, input, jaxOut, 0.0f)
        for (int i = 0; i < n; i++) {
            assert Math.abs(torchOut[i] - jaxOut[i]) < 1e-4f
        }

        // Test 2: SiLU (Swish)
        torch.tensorOp(2, input, torchOut, 0.0f)
        jax.tensorOp(2, input, jaxOut, 0.0f)
        for (int i = 0; i < n; i++) {
            assert Math.abs(torchOut[i] - jaxOut[i]) < 1e-4f
        }

        // Test 3: Tanh
        torch.tensorOp(3, input, torchOut, 0.0f)
        jax.tensorOp(3, input, jaxOut, 0.0f)
        for (int i = 0; i < n; i++) {
            assert Math.abs(torchOut[i] - jaxOut[i]) < 1e-4f
        }

        // Test 4: Sigmoid
        torch.tensorOp(4, input, torchOut, 0.0f)
        jax.tensorOp(4, input, jaxOut, 0.0f)
        for (int i = 0; i < n; i++) {
            assert Math.abs(torchOut[i] - jaxOut[i]) < 1e-4f
        }

        // Test 5: LeakyReLU (slope = 0.05)
        torch.tensorOp(5, input, torchOut, 0.05f)
        jax.tensorOp(5, input, jaxOut, 0.05f)
        for (int i = 0; i < n; i++) {
            assert Math.abs(torchOut[i] - jaxOut[i]) < 1e-4f
        }

        // Test 6: ELU (alpha = 1.0)
        torch.tensorOp(6, input, torchOut, 1.0f)
        jax.tensorOp(6, input, jaxOut, 1.0f)
        for (int i = 0; i < n; i++) {
            assert Math.abs(torchOut[i] - jaxOut[i]) < 1e-4f
        }
    }

    @Test
    void testLayerNormParity() {
        LibTorchPanama torch = LibTorchPanama.INSTANCE
        JaxPanama jax = JaxPanama.INSTANCE

        long torchPlan = torch.createPlan(4)
        long jaxPlan = jax.createPlan(4)

        try {
            torch.addLayerNorm(torchPlan, 0, 1, 4, null, null, 1e-5f)
            torch.seal(torchPlan, 1, 4)

            jax.addLayerNorm(jaxPlan, 0, 1, 4, null, null, 1e-5f)
            jax.seal(jaxPlan, 1, 4)

            float[] input = [1.2f, 3.4f, -0.5f, 2.1f,  5.0f, -2.0f, 0.0f, 1.0f] as float[]
            float[] torchRes = torch.execute(torchPlan, input, 2)
            float[] jaxRes = jax.execute(jaxPlan, input, 2)

            for (int i = 0; i < 8; i++) {
                assert Math.abs(torchRes[i] - jaxRes[i]) < 1e-4f
            }
        } finally {
            torch.destroy(torchPlan)
            jax.destroy(jaxPlan)
        }
    }

    @Test
    void testDeepMlpParity() {
        LibTorchPanama torch = LibTorchPanama.INSTANCE
        JaxPanama jax = JaxPanama.INSTANCE

        long torchPlan = torch.createPlan(3)
        long jaxPlan = jax.createPlan(3)

        try {
            // Layer 1: 3 -> 4
            float[] w1 = [
                0.1f, 0.2f, 0.3f,
                0.4f, 0.5f, 0.6f,
                -0.1f, 0.0f, 0.2f,
                0.3f, -0.2f, 0.1f
            ] as float[]
            float[] b1 = [0.1f, -0.1f, 0.0f, 0.2f] as float[]

            torch.addAffine(torchPlan, 0, 1, 3, 4, w1, b1)
            torch.addGelu(torchPlan, 1, 2)
            torch.seal(torchPlan, 2, 4)

            jax.addAffine(jaxPlan, 0, 1, 3, 4, w1, b1)
            jax.addGelu(jaxPlan, 1, 2)
            jax.seal(jaxPlan, 2, 4)

            float[] input = [1.0f, 2.0f, 3.0f, -1.0f, 0.5f, 2.0f] as float[]
            float[] torchRes = torch.execute(torchPlan, input, 2)
            float[] jaxRes = jax.execute(jaxPlan, input, 2)

            for (int i = 0; i < 8; i++) {
                assert Math.abs(torchRes[i] - jaxRes[i]) < 1e-4f
            }
        } finally {
            torch.destroy(torchPlan)
            jax.destroy(jaxPlan)
        }
    }
}
