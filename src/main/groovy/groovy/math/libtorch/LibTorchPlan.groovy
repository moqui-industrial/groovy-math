/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.libtorch

import groovy.transform.CompileStatic

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
    private final LibTorchBackend backend
    private final ReentrantReadWriteLock lifecycle = new ReentrantReadWriteLock()
    private long handle

    LibTorchPlan(final String mathModelId, final String inputName, final String outputName,
                 final int inputRows, final int inputWidth, final int outputWidth, final int operationCount,
                 final LibTorchBackend backend, final long handle) {
        this.mathModelId = mathModelId
        this.inputName = inputName
        this.outputName = outputName
        this.inputRows = inputRows
        this.inputWidth = inputWidth
        this.outputWidth = outputWidth
        this.operationCount = operationCount
        this.backend = backend
        this.handle = handle
    }

    LibTorchResult execute(final float[] input) {
        if (input == null || input.length == 0 || input.length % inputWidth != 0) {
            throw new IllegalArgumentException("Input length must be a positive multiple of ${inputWidth}")
        }
        int batchSize = input.length.intdiv(inputWidth)
        assertBatchSize(batchSize)
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
        assertBatchSize(batchSize)
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

    private void assertBatchSize(final int batchSize) {
        if (inputRows > 0 && batchSize != inputRows) {
            throw new IllegalArgumentException("Input has ${batchSize} rows; ${mathModelId} requires ${inputRows}")
        }
    }
}
