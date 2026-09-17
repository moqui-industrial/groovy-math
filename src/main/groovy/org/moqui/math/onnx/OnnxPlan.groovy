/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.onnx

import groovy.transform.CompileStatic

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout

@CompileStatic
final class OnnxPlan implements AutoCloseable {
    final String mathModelId
    final String modelPath
    final String inputName
    final String outputName
    final List<Long> inputShape
    final List<Long> outputShape
    final OnnxPanama panama
    private long handle

    OnnxPlan(final String mathModelId, final String modelPath, final String inputName,
             final String outputName, final List<Long> inputShape, final List<Long> outputShape,
             final OnnxPanama panama, final long handle) {
        this.mathModelId = mathModelId
        this.modelPath = modelPath
        this.inputName = inputName
        this.outputName = outputName
        this.inputShape = inputShape ?: [1L, 1L]
        this.outputShape = outputShape ?: [1L, 1L]
        this.panama = panama
        this.handle = handle
    }

    OnnxResult execute(final float[] input) {
        long[] shapeArr = new long[inputShape.size()]
        for (int i = 0; i < inputShape.size(); i++) shapeArr[i] = inputShape.get(i)

        long outSize = 1L
        for (Long d : outputShape) outSize *= d
        float[] out = new float[(int) outSize]

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment inSeg = arena.allocate((long) input.length * ValueLayout.JAVA_FLOAT.byteSize())
            for (int i = 0; i < input.length; i++) {
                inSeg.setAtIndex(ValueLayout.JAVA_FLOAT, (long) i, input[i])
            }
            MemorySegment outSeg = arena.allocate(outSize * ValueLayout.JAVA_FLOAT.byteSize())
            panama.run(handle, inputName, inSeg, shapeArr, outputName, outSeg, outSize)
            for (int i = 0; i < (int) outSize; i++) {
                out[i] = outSeg.getAtIndex(ValueLayout.JAVA_FLOAT, (long) i)
            }
        }
        new OnnxResult(outputName, outputShape, out)
    }

    @Override
    void close() {
        if (handle != 0L) {
            panama.destroySession(handle)
            handle = 0L
        }
    }
}
