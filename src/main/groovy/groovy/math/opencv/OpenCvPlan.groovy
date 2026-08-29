/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.opencv

import groovy.transform.CompileStatic

import java.util.concurrent.locks.ReentrantReadWriteLock

@CompileStatic
final class OpenCvPlan implements AutoCloseable {
    final String mathModelId
    final String inputName
    final String outputName
    final int width
    final int height
    final int outWidth
    final int outHeight
    private final OpenCvPanama panama
    private final ReentrantReadWriteLock lifecycle = new ReentrantReadWriteLock()
    private long handle

    OpenCvPlan(final String mathModelId, final String inputName, final String outputName,
               final int width, final int height, final int outWidth, final int outHeight,
               final OpenCvPanama panama, final long handle) {
        this.mathModelId = mathModelId
        this.inputName = inputName
        this.outputName = outputName
        this.width = width
        this.height = height
        this.outWidth = outWidth
        this.outHeight = outHeight
        this.panama = panama
        this.handle = handle
    }

    OpenCvResult execute(final float[] input) {
        if (input == null || input.length != width * height) {
            throw new IllegalArgumentException("Input image length must be ${width * height} (got ${input?.length})")
        }
        lifecycle.readLock().lock()
        try {
            assertOpen()
            float[] out = panama.execute(handle, input)
            new OpenCvResult(outputName, outWidth, outHeight, out)
        } finally {
            lifecycle.readLock().unlock()
        }
    }

    private void assertOpen() {
        if (handle == 0L) throw new IllegalStateException('OpenCV plan is closed')
    }

    @Override
    void close() {
        lifecycle.writeLock().lock()
        try {
            if (handle != 0L) {
                panama.destroy(handle)
                handle = 0L
            }
        } finally {
            lifecycle.writeLock().unlock()
        }
    }
}
