/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.tensor;

/**
 * Thrown when a tensor in memory violates the declared geometrical or storage contract
 * defined in the Moqui Model Lifecycle Management metamodel (moqui.math.Tensor).
 *
 * This exception prevents invalid memory segments from being passed across the Foreign
 * Function and Memory (FFM) boundary, completely preventing native Segmentation Faults (SIGSEGV).
 */
public class TensorContractException extends RuntimeException {

    private final String tensorId;
    private final String expected;
    private final String actual;

    public TensorContractException(String message) {
        super(message);
        this.tensorId = null;
        this.expected = null;
        this.actual = null;
    }

    public TensorContractException(String tensorId, String reason, String expected, String actual) {
        super(String.format("Tensor contract violation for tensor '%s': %s. Expected: %s, Actual: %s",
                tensorId != null ? tensorId : "anonymous", reason, expected, actual));
        this.tensorId = tensorId;
        this.expected = expected;
        this.actual = actual;
    }

    public String getTensorId() { return tensorId; }
    public String getExpected() { return expected; }
    public String getActual() { return actual; }
}
