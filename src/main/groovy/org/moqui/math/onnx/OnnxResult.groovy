/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.onnx

import groovy.transform.CompileStatic

@CompileStatic
final class OnnxResult {
    final String outputName
    final List<Long> shape
    final float[] data

    OnnxResult(final String outputName, final List<Long> shape, final float[] data) {
        this.outputName = outputName
        this.shape = shape
        this.data = data
    }
}
