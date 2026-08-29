/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.libtorch

import groovy.transform.CompileStatic

@CompileStatic
final class LibTorchResult {
    final String tensorName
    final int batchSize
    final int width
    final float[] values

    LibTorchResult(final String tensorName, final int batchSize, final int width, final float[] values) {
        this.tensorName = tensorName
        this.batchSize = batchSize
        this.width = width
        this.values = values.clone()
    }
}
