/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.tensor;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enterprise Guard-Rail Validator for Zero-Crash FFM Invocations.
 * Validates memory segments and descriptors against declared contracts in Moqui MathEntities.
 */
public final class TensorValidator {

    private static final Pattern JSON_INT_ARRAY_PATTERN = Pattern.compile("\\[([0-9,\\s]*)\\]");

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
