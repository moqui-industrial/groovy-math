/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.tensor

import org.junit.jupiter.api.Test
import java.lang.foreign.Arena

class TensorValidatorTest {

    @Test
    void testShapeValidationSuccess() {
        long[] expected = [4, 3, 2] as long[]
        long[] actual = [4, 3, 2] as long[]
        TensorValidator.validateShape("tensor-1", expected, actual)
    }

    @Test
    void testShapeValidationMismatchThrows() {
        long[] expected = [4, 3, 2] as long[]
        long[] actual = [4, 2, 2] as long[]
        try {
            TensorValidator.validateShape("tensor-mismatch", expected, actual)
            assert false : "Should have thrown TensorContractException"
        } catch (TensorContractException e) {
            assert e.tensorId == "tensor-mismatch"
            assert e.message.contains("Dimension mismatch")
        }
    }

    @Test
    void testRankMismatchThrows() {
        long[] expected = [4, 3] as long[]
        long[] actual = [4, 3, 1] as long[]
        try {
            TensorValidator.validateShape("tensor-rank", expected, actual)
            assert false : "Should have thrown TensorContractException"
        } catch (TensorContractException e) {
            assert e.message.contains("Rank mismatch")
        }
    }

    @Test
    void testContractValidationAgainstJson() {
        try (Arena arena = Arena.ofConfined()) {
            float[] data = new float[12]
            TensorDescriptor desc = TensorDescriptor.ofFloats(arena, data, [4, 3] as long[])

            // Valid
            TensorValidator.validateContract("t-valid", desc, "[4, 3]", TensorDescriptor.DTYPE_FLOAT32)

            // Invalid DType
            try {
                TensorValidator.validateContract("t-invalid-dtype", desc, "[4, 3]", TensorDescriptor.DTYPE_FLOAT64)
                assert false : "Should fail for dtype mismatch"
            } catch (TensorContractException e) {
                assert e.message.contains("Data type mismatch")
            }

            // Invalid Shape
            try {
                TensorValidator.validateContract("t-invalid-shape", desc, "[3, 4]", TensorDescriptor.DTYPE_FLOAT32)
                assert false : "Should fail for shape mismatch"
            } catch (TensorContractException e) {
                assert e.message.contains("Dimension mismatch")
            }
        }
    }

    @Test
    void testLinearLayerValidation() {
        // Linear layer: in=3, out=4 -> weight must be [4, 3]
        TensorValidator.validateLinearLayer(2, 3, 4, [4, 3] as long[], [4] as long[])

        try {
            // Passing inverted weights [3, 4] must fail
            TensorValidator.validateLinearLayer(2, 3, 4, [3, 4] as long[], [4] as long[])
            assert false : "Should fail with inverted weight shape"
        } catch (TensorContractException e) {
            assert e.message.contains("Weight matrix shape mismatch")
        }
    }

    @Test
    void testBoundsValidation() {
        long[] shape = [10, 20] as long[]
        TensorValidator.validateBounds([5, 15] as long[], shape)

        try {
            TensorValidator.validateBounds([10, 5] as long[], shape)
            assert false : "Should fail on index 10 for size 10"
        } catch (IndexOutOfBoundsException e) {
            assert e.message.contains("out of bounds")
        }
    }
}
