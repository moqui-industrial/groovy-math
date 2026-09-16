/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.tensor;

import java.lang.foreign.MemorySegment;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enterprise Guard-Rail Validator for Zero-Crash FFM Invocations.
 * Validates memory segments and descriptors against declared contracts in Moqui MathEntities.
 */
public final class TensorValidator {

    // Negative entries are allowed on purpose: validateShape treats a dimension of zero or less
    // as free, which is how a model declares a dynamic batch. A parser that rejected "[-1, 4]"
    // would contradict the checker it feeds.
    private static final Pattern JSON_INT_ARRAY_PATTERN = Pattern.compile("\\[([-0-9,\\s]*)\\]");

    private TensorValidator() { }

    /**
     * Validates that actual shape exactly matches expected shape.
     */
    public static void validateShape(String tensorId, long[] expectedShape, long[] actualShape) {
        Objects.requireNonNull(expectedShape, "expectedShape cannot be null");
        Objects.requireNonNull(actualShape, "actualShape cannot be null");

        if (expectedShape.length != actualShape.length) {
            throw new TensorContractException(tensorId, "Rank mismatch",
                    "rank " + expectedShape.length + " (" + Arrays.toString(expectedShape) + ")",
                    "rank " + actualShape.length + " (" + Arrays.toString(actualShape) + ")");
        }

        for (int i = 0; i < expectedShape.length; i++) {
            if (expectedShape[i] != actualShape[i] && expectedShape[i] > 0) { // >0 allows dynamic dimensions if <=0
                throw new TensorContractException(tensorId, "Dimension mismatch at axis " + i,
                        Arrays.toString(expectedShape), Arrays.toString(actualShape));
            }
        }
    }

    /**
     * Parses a JSON shape string like "[32, 3, 224, 224]" into a long[].
     */
    public static long[] parseShapeJson(String shapeJson) {
        if (shapeJson == null || shapeJson.trim().isEmpty()) {
            throw new IllegalArgumentException("shapeJson cannot be null or empty");
        }
        String clean = shapeJson.trim();
        Matcher m = JSON_INT_ARRAY_PATTERN.matcher(clean);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid JSON shape format: " + shapeJson);
        }
        String content = m.group(1).trim();
        if (content.isEmpty()) return new long[0];
        String[] parts = content.split(",");
        long[] res = new long[parts.length];
        for (int i = 0; i < parts.length; i++) {
            res[i] = Long.parseLong(parts[i].trim());
        }
        return res;
    }

    /**
     * Validates a descriptor against Moqui entity declared attributes.
     */
    public static void validateContract(String tensorId, TensorDescriptor descriptor,
                                         String declaredShapeJson, int declaredDtype) {
        Objects.requireNonNull(descriptor, "TensorDescriptor cannot be null");
        long[] expectedShape = parseShapeJson(declaredShapeJson);
        validateShape(tensorId, expectedShape, descriptor.shape());

        if (descriptor.dtype() != declaredDtype) {
            throw new TensorContractException(tensorId, "Data type mismatch",
                    "dtype " + declaredDtype, "dtype " + descriptor.dtype());
        }

        long expectedNumel = TensorDescriptor.computeNumel(expectedShape);
        long actualBytes = descriptor.segment().byteSize();
        long expectedMinBytes = expectedNumel * TensorDescriptor.elementByteSize(declaredDtype);

        if (actualBytes < expectedMinBytes) {
            throw new TensorContractException(tensorId, "Underallocated off-heap memory buffer",
                    expectedMinBytes + " bytes", actualBytes + " bytes");
        }
    }

    /**
     * Validates input/output dimensions for linear layers (Affine / GEMM).
     */
    public static void validateLinearLayer(long batchSize, long inFeatures, long outFeatures,
                                           long[] weightShape, long[] biasShape) {
        if (inFeatures <= 0 || outFeatures <= 0 || batchSize <= 0) {
            throw new TensorContractException(null, "Invalid layer dimensions",
                    "batchSize > 0, inFeatures > 0, outFeatures > 0",
                    "batchSize=" + batchSize + ", inFeatures=" + inFeatures + ", outFeatures=" + outFeatures);
        }

        if (weightShape == null || weightShape.length != 2) {
            throw new TensorContractException(null, "Weight tensor must be 2D",
                    "rank 2 [outFeatures, inFeatures]",
                    weightShape != null ? Arrays.toString(weightShape) : "null");
        }

        if (weightShape[0] != outFeatures || weightShape[1] != inFeatures) {
            throw new TensorContractException(null, "Weight matrix shape mismatch",
                    "[" + outFeatures + ", " + inFeatures + "]", Arrays.toString(weightShape));
        }

        if (biasShape != null) {
            if (biasShape.length != 1 || biasShape[0] != outFeatures) {
                throw new TensorContractException(null, "Bias vector shape mismatch",
                        "[" + outFeatures + "]", Arrays.toString(biasShape));
            }
        }
    }

    /**
     * Validates that an off-heap segment is large enough for the element count a native call
     * is about to read or write through it.
     *
     * <p>This is the guard that stands between a caller-supplied {@link MemorySegment} and the
     * FFM boundary. The native side reshapes the pointer against dimensions it is told, not
     * dimensions it can verify, so an undersized segment is read or written out of bounds and
     * the process dies with SIGSEGV instead of throwing.
     *
     * <p>A zero-length segment is rejected whenever any element is required: a segment obtained
     * from a bare address carries no size, and an unverifiable size cannot be declared safe.
     * Call {@code MemorySegment.reinterpret(byteSize)} to give such a segment its real extent.
     *
     * @param role what the segment is to the call ("input", "output", "kernel"), used in the message
     */
    public static void validateSegmentCapacity(String tensorId, String role, MemorySegment segment,
                                               long requiredElements, int dtype) {
        if (segment == null || MemorySegment.NULL.equals(segment)) {
            throw new TensorContractException(tensorId, "Null " + role + " segment",
                    requiredElements + " elements", "null");
        }
        if (requiredElements < 0) {
            throw new IllegalArgumentException("requiredElements cannot be negative: " + requiredElements);
        }
        long requiredBytes = requiredElements * TensorDescriptor.elementByteSize(dtype);
        long actualBytes = segment.byteSize();
        if (actualBytes < requiredBytes) {
            throw new TensorContractException(tensorId, "Undersized " + role + " segment",
                    requiredBytes + " bytes (" + requiredElements + " elements)",
                    actualBytes + " bytes");
        }
    }

    /**
     * Validates the shapes of a matrix product and the segments backing it. The inner dimensions
     * must agree and each segment must hold its full operand; the native GEMM checks neither.
     */
    public static void validateMatmul(MemorySegment a, long aRows, long aCols,
                                      MemorySegment b, long bRows, long bCols,
                                      MemorySegment out, int dtype) {
        if (aRows <= 0 || aCols <= 0 || bRows <= 0 || bCols <= 0) {
            throw new TensorContractException(null, "Invalid matmul dimensions",
                    "all dimensions > 0",
                    "a=" + aRows + "x" + aCols + ", b=" + bRows + "x" + bCols);
        }
        if (aCols != bRows) {
            throw new TensorContractException(null, "Matmul inner dimension mismatch",
                    "a columns == b rows", aCols + " != " + bRows);
        }
        validateSegmentCapacity("matmul.a", "left operand", a, aRows * aCols, dtype);
        validateSegmentCapacity("matmul.b", "right operand", b, bRows * bCols, dtype);
        validateSegmentCapacity("matmul.out", "output", out, aRows * bCols, dtype);
    }

    /**
     * Validates a batched plan invocation: the input segment must hold
     * {@code batchSize * inputWidth} elements and the output {@code batchSize * outputWidth}.
     * An input width of zero or less means the plan did not declare one, and only the output
     * side is checked.
     */
    public static void validatePlanInvocation(String planId, MemorySegment input, MemorySegment output,
                                              int batchSize, int inputWidth, int outputWidth, int dtype) {
        if (batchSize <= 0) {
            throw new TensorContractException(planId, "Invalid batch size", "batchSize > 0", String.valueOf(batchSize));
        }
        if (inputWidth > 0) {
            validateSegmentCapacity(planId, "input", input, (long) batchSize * inputWidth, dtype);
        }
        if (outputWidth > 0) {
            validateSegmentCapacity(planId, "output", output, (long) batchSize * outputWidth, dtype);
        }
    }

    /**
     * Validates multi-dimensional coordinate bounds.
     */
    public static void validateBounds(long[] coords, long[] shape) {
        Objects.requireNonNull(coords, "coords cannot be null");
        Objects.requireNonNull(shape, "shape cannot be null");
        if (coords.length != shape.length) {
            throw new IndexOutOfBoundsException("Coordinate rank " + coords.length + " does not match tensor rank " + shape.length);
        }
        for (int i = 0; i < coords.length; i++) {
            if (coords[i] < 0 || coords[i] >= shape[i]) {
                throw new IndexOutOfBoundsException(String.format("Index %d out of bounds for axis %d with size %d",
                        coords[i], i, shape[i]));
            }
        }
    }
}
