/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.tensor

import org.junit.jupiter.api.Test
import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment

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
    void parsesAFreeDimensionInADeclaredShape() {
        // validateShape treats a dimension of zero or less as free, which is how a model declares
        // a dynamic batch. The parser that feeds it has to accept the same notation.
        assert TensorValidator.parseShapeJson('[-1, 4]').toList() == [-1L, 4L]
        assert TensorValidator.parseShapeJson('[2, 3]').toList() == [2L, 3L]
        assert TensorValidator.parseShapeJson('[]').length == 0

        // A free leading dimension accepts any batch; the fixed one still has to match.
        long[] declared = [-1L, 4L] as long[]
        TensorValidator.validateShape('t', declared, [7L, 4L] as long[])
        try {
            TensorValidator.validateShape('t', declared, [7L, 5L] as long[])
            assert false : 'Should have refused a width of 5 against a declared 4'
        } catch (TensorContractException e) {
            assert e.message.contains('Dimension mismatch at axis 1')
        }
    }

    @Test
    void mapsDeclaredElementTypesToTheCodesTheFfmLayerSpeaks() {
        assert TensorDataTypes.codeForEnumId('DtFloat32') == TensorDescriptor.DTYPE_FLOAT32
        assert TensorDataTypes.codeForEnumId('DtFloat64') == TensorDescriptor.DTYPE_FLOAT64
        assert TensorDataTypes.enumIdForCode(TensorDescriptor.DTYPE_INT64) == 'DtInt64'

        // TensorDataType seeds element types the descriptor has no code for. Reporting -1 says
        // "declarable but not dispatchable", which beats silently treating them as float32.
        assert TensorDataTypes.codeForEnumId('DtComplex64') == -1
        assert TensorDataTypes.codeForEnumId('DtFloat16') == -1
        assert TensorDataTypes.codeForEnumId(null) == -1
        assert TensorDataTypes.describe(TensorDescriptor.DTYPE_FLOAT32) == 'DtFloat32'
        assert TensorDataTypes.describe(99) == 'dtype 99'
    }

    @Test
    void validatesAWholeDeclaredContractAtOnce() {
        try (Arena arena = Arena.ofConfined()) {
            TensorDescriptor descriptor = TensorDescriptor.ofFloats(
                arena, [1f, 2f, 3f, 4f, 5f, 6f] as float[], [2L, 3L] as long[])

            TensorValidator.validateContract('t', descriptor, '[2, 3]', TensorDescriptor.DTYPE_FLOAT32)
            TensorValidator.validateContract('t', descriptor, '[-1, 3]', TensorDescriptor.DTYPE_FLOAT32)

            try {
                TensorValidator.validateContract('t', descriptor, '[2, 3]', TensorDescriptor.DTYPE_FLOAT64)
                assert false : 'Should have refused a float32 buffer against a float64 declaration'
            } catch (TensorContractException e) {
                assert e.message.contains('Data type mismatch')
            }

            // Shape and element type agree, but the segment cannot hold what they describe.
            MemorySegment tooSmall = arena.allocate(4L * Float.BYTES)
            TensorDescriptor lying = new TensorDescriptor(
                tooSmall, TensorDescriptor.DTYPE_FLOAT32, [2L, 3L] as long[], null, TensorDescriptor.DEVICE_CPU)
            try {
                TensorValidator.validateContract('t', lying, '[2, 3]', TensorDescriptor.DTYPE_FLOAT32)
                assert false : 'Should have refused a 6-element contract over a 4-element segment'
            } catch (TensorContractException e) {
                assert e.message.contains('Underallocated')
            }
        }
    }

    @Test
    void refusesUndersizedSegmentBeforeTheNativeCall() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(8L * Float.BYTES)

            // Exactly enough, and more than enough, are both fine.
            TensorValidator.validateSegmentCapacity("t", "input", segment, 8L, TensorDescriptor.DTYPE_FLOAT32)
            TensorValidator.validateSegmentCapacity("t", "input", segment, 7L, TensorDescriptor.DTYPE_FLOAT32)

            try {
                TensorValidator.validateSegmentCapacity("t", "input", segment, 9L, TensorDescriptor.DTYPE_FLOAT32)
                assert false : 'Should have refused a 9-element read from an 8-element segment'
            } catch (TensorContractException e) {
                assert e.tensorId == 't'
                assert e.message.contains('Undersized input segment')
            }

            // Same bytes, wider element type: 8 floats cannot hold 8 doubles.
            try {
                TensorValidator.validateSegmentCapacity("t", "input", segment, 8L, TensorDescriptor.DTYPE_FLOAT64)
                assert false : 'Should have refused 8 doubles in a 32-byte segment'
            } catch (TensorContractException e) {
                assert e.message.contains('64 bytes')
            }
        }
    }

    @Test
    void refusesNullAndZeroLengthSegments() {
        try {
            TensorValidator.validateSegmentCapacity("t", "output", null, 1L, TensorDescriptor.DTYPE_FLOAT32)
            assert false : 'Should have refused a null segment'
        } catch (TensorContractException e) {
            assert e.message.contains('Null output segment')
        }

        // A segment built from a bare address carries no size, so it cannot be declared safe.
        try {
            TensorValidator.validateSegmentCapacity("t", "output", MemorySegment.NULL, 1L,
                TensorDescriptor.DTYPE_FLOAT32)
            assert false : 'Should have refused a zero-length segment'
        } catch (TensorContractException e) {
            assert e.message.contains('output segment')
        }

        // Requiring nothing of a zero-length segment is not an error.
        try (Arena arena = Arena.ofConfined()) {
            TensorValidator.validateSegmentCapacity("t", "output", arena.allocate(4L), 0L,
                TensorDescriptor.DTYPE_FLOAT32)
        }
    }

    @Test
    void refusesMatmulThatWouldReadOutOfBounds() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment a = arena.allocate(6L * Float.BYTES)   // 2x3
            MemorySegment b = arena.allocate(12L * Float.BYTES)  // 3x4
            MemorySegment out = arena.allocate(8L * Float.BYTES) // 2x4

            TensorValidator.validateMatmul(a, 2, 3, b, 3, 4, out, TensorDescriptor.DTYPE_FLOAT32)

            try {
                TensorValidator.validateMatmul(a, 2, 3, b, 4, 3, out, TensorDescriptor.DTYPE_FLOAT32)
                assert false : 'Should have refused a 3-vs-4 inner dimension'
            } catch (TensorContractException e) {
                assert e.message.contains('inner dimension mismatch')
            }

            // Dimensions agree, but the left operand is a 3x3 worth of reads from a 6-float buffer.
            try {
                TensorValidator.validateMatmul(a, 3, 3, b, 3, 4, out, TensorDescriptor.DTYPE_FLOAT32)
                assert false : 'Should have refused a 9-element read from a 6-element segment'
            } catch (TensorContractException e) {
                assert e.tensorId == 'matmul.a'
            }

            try {
                TensorValidator.validateMatmul(a, 0, 3, b, 3, 4, out, TensorDescriptor.DTYPE_FLOAT32)
                assert false : 'Should have refused a zero dimension'
            } catch (TensorContractException e) {
                assert e.message.contains('Invalid matmul dimensions')
            }
        }
    }

    @Test
    void refusesPlanInvocationThatDoesNotFitTheBatch() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment input = arena.allocate(6L * Float.BYTES)
            MemorySegment output = arena.allocate(4L * Float.BYTES)

            // batch 2 of width 3 in, width 2 out: fits exactly.
            TensorValidator.validatePlanInvocation('p', input, output, 2, 3, 2, TensorDescriptor.DTYPE_FLOAT32)

            try {
                TensorValidator.validatePlanInvocation('p', input, output, 3, 3, 2, TensorDescriptor.DTYPE_FLOAT32)
                assert false : 'Should have refused a batch of 3 against a 6-float input'
            } catch (TensorContractException e) {
                assert e.message.contains('Undersized input segment')
            }

            try {
                TensorValidator.validatePlanInvocation('p', input, output, 2, 3, 4, TensorDescriptor.DTYPE_FLOAT32)
                assert false : 'Should have refused an output too small for the batch'
            } catch (TensorContractException e) {
                assert e.message.contains('Undersized output segment')
            }

            try {
                TensorValidator.validatePlanInvocation('p', input, output, 0, 3, 2, TensorDescriptor.DTYPE_FLOAT32)
                assert false : 'Should have refused a non-positive batch'
            } catch (TensorContractException e) {
                assert e.message.contains('Invalid batch size')
            }

            // An unknown input width (handle not created by this wrapper) checks only the output.
            TensorValidator.validatePlanInvocation('p', input, output, 2, -1, 2, TensorDescriptor.DTYPE_FLOAT32)
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
