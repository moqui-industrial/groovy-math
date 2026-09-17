/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import groovy.transform.CompileStatic
import org.moqui.math.tensor.TensorContractException
import org.moqui.math.tensor.TensorDataTypes
import org.moqui.math.tensor.TensorDescriptor
import org.moqui.math.tensor.TensorValidator

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.locks.ReentrantReadWriteLock

@CompileStatic
final class LibTorchPlan implements AutoCloseable {
    final String mathModelId
    final String inputName
    final String outputName
    final int inputRows
    final int inputWidth
    final int outputWidth
    final int operationCount
    /** Id of the declared Matrix or Tensor this plan takes as input, for contract messages. */
    final String inputId
    /** Shape as declared in the model, or null when the model did not fix one. */
    private final long[] declaredInputShape
    /** Element type declared for the input, as an FFM dtype code. */
    final int declaredDataType
    final float[] defaultInputValues
    private final LibTorchBackend backend
    private final ReentrantReadWriteLock lifecycle = new ReentrantReadWriteLock()
    private long handle

    LibTorchPlan(final String mathModelId, final String inputName, final String outputName,
                 final int inputRows, final int inputWidth, final int outputWidth, final int operationCount,
                 final LibTorchBackend backend, final long handle,
                 final String inputId = null, final long[] declaredInputShape = null,
                 final int declaredDataType = TensorDescriptor.DTYPE_FLOAT32,
                 final float[] defaultInputValues = null) {
        this.mathModelId = mathModelId
        this.inputName = inputName
        this.outputName = outputName
        this.inputRows = inputRows
        this.inputWidth = inputWidth
        this.outputWidth = outputWidth
        this.operationCount = operationCount
        this.backend = backend
        this.handle = handle
        this.inputId = inputId
        this.declaredInputShape = declaredInputShape == null ? null : Arrays.copyOf(declaredInputShape, declaredInputShape.length)
        this.declaredDataType = declaredDataType
        this.defaultInputValues = defaultInputValues
    }

    /**
     * Runs the plan against an off-heap tensor, checking the whole declared contract first.
     *
     * <p>This is the one path where every clause the model declared is enforced together: rank
     * and shape axis by axis, element type, and that the segment is actually big enough to hold
     * what it claims. The float[] entry point can only check the first of those, because an
     * array carries no element type of its own and no notion of being undersized.
     */
    LibTorchResult execute(final TensorDescriptor input) {
        Objects.requireNonNull(input, 'Tensor descriptor must not be null')
        if (input.dtype() != declaredDataType) {
            throw new TensorContractException(contractId(), 'Element type does not match the declaration',
                TensorDataTypes.describe(declaredDataType), TensorDataTypes.describe(input.dtype()))
        }
        if (declaredInputShape != null) {
            TensorValidator.validateContract(contractId(), input, Arrays.toString(declaredInputShape), declaredDataType)
        }

        long[] actual = input.shape()
        if (actual.length == 0) {
            throw new TensorContractException(contractId(), 'Input has no dimensions', 'rank >= 1', 'rank 0')
        }
        if (actual[actual.length - 1] != inputWidth) {
            throw new TensorContractException(contractId(), 'Trailing dimension does not match the plan input width',
                String.valueOf(inputWidth), String.valueOf(actual[actual.length - 1]))
        }
        int batchSize = (int) (input.numel() / inputWidth)
        assertDeclaredShape(batchSize)

        ByteBuffer inputBuffer = input.segment().asByteBuffer().order(ByteOrder.nativeOrder())
        ByteBuffer outputBuffer = ByteBuffer.allocateDirect(batchSize * outputWidth * Float.BYTES)
            .order(ByteOrder.nativeOrder())
        executeDirect(inputBuffer, batchSize, outputBuffer)

        float[] values = new float[batchSize * outputWidth]
        outputBuffer.rewind()
        outputBuffer.asFloatBuffer().get(values)
        new LibTorchResult(outputName, batchSize, outputWidth, values)
    }

    /** Shape this plan's input was declared with in the model, or null. */
    long[] getDeclaredInputShape() {
        declaredInputShape == null ? null : Arrays.copyOf(declaredInputShape, declaredInputShape.length)
    }

    LibTorchResult execute(final float[] input) {
        int batchSize = batchSizeOf(input == null ? 0 : input.length)
        lifecycle.readLock().lock()
        try {
            assertOpen()
            new LibTorchResult(outputName, batchSize, outputWidth, backend.execute(handle, input, batchSize))
        } finally {
            lifecycle.readLock().unlock()
        }
    }

    ByteBuffer executeDirect(final ByteBuffer input, final int batchSize) {
        ByteBuffer output = ByteBuffer.allocateDirect(batchSize * outputWidth * Float.BYTES)
            .order(ByteOrder.nativeOrder())
        executeDirect(input, batchSize, output)
        output.clear()
        output
    }

    void executeDirect(final ByteBuffer input, final int batchSize, final ByteBuffer output) {
        if (input == null || !input.direct) throw new IllegalArgumentException('Input must be a direct ByteBuffer')
        if (output == null || !output.direct) throw new IllegalArgumentException('Output must be a direct ByteBuffer')
        if (input.position() != 0 || output.position() != 0) {
            throw new IllegalArgumentException('Direct buffers must have position zero')
        }
        if (input.order() != ByteOrder.nativeOrder() || output.order() != ByteOrder.nativeOrder()) {
            throw new IllegalArgumentException('Direct buffers must use native byte order')
        }
        if (batchSize <= 0 || input.remaining() < batchSize * inputWidth * Float.BYTES) {
            throw new IllegalArgumentException('Input buffer is smaller than the declared batch')
        }
        if (output.remaining() < batchSize * outputWidth * Float.BYTES) {
            throw new IllegalArgumentException('Output buffer is smaller than the declared batch')
        }
        assertDeclaredShape(batchSize)
        lifecycle.readLock().lock()
        try {
            assertOpen()
            backend.executeDirect(handle, input, batchSize, output)
        } finally {
            lifecycle.readLock().unlock()
        }
    }

    @Override
    void close() {
        lifecycle.writeLock().lock()
        try {
            if (handle != 0L) {
                backend.destroy(handle)
                handle = 0L
            }
        } finally {
            lifecycle.writeLock().unlock()
        }
    }

    boolean isClosed() {
        lifecycle.readLock().lock()
        try { handle == 0L } finally { lifecycle.readLock().unlock() }
    }

    private void assertOpen() {
        if (handle == 0L) throw new IllegalStateException('LibTorch plan is closed')
    }

    /** Name used in contract messages: the declared object when known, else the plan input name. */
    private String contractId() {
        inputId ?: inputName
    }

    private int batchSizeOf(final int length) {
        if (length <= 0) {
            throw new TensorContractException(contractId(), 'Empty input',
                "a positive multiple of ${inputWidth}", String.valueOf(length))
        }
        if (length % inputWidth != 0) {
            throw new TensorContractException(contractId(), 'Input length is not a whole number of rows',
                "a multiple of ${inputWidth}", "${length} floats")
        }
        int batchSize = length.intdiv(inputWidth)
        assertDeclaredShape(batchSize)
        batchSize
    }

    /**
     * Checks the runtime batch against the shape the model declared. This is the point where a
     * declaration in the DSL becomes an enforced contract: a rank-2 declaration is compared axis
     * by axis, and a dimension declared as zero or negative is treated as free. Models whose
     * input was declared with another rank fall back to the row count the provider derived,
     * because the plan itself is two-dimensional at the FFM boundary.
     */
    private void assertDeclaredShape(final int batchSize) {
        if (declaredInputShape != null && declaredInputShape.length == 2) {
            TensorValidator.validateShape(contractId(), declaredInputShape,
                [(long) batchSize, (long) inputWidth] as long[])
            return
        }
        if (inputRows > 0 && batchSize != inputRows) {
            throw new TensorContractException(contractId(), 'Batch size does not match the declared rows',
                "${inputRows} rows", "${batchSize} rows")
        }
    }
}
