/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.opencv

import groovy.transform.CompileStatic

@CompileStatic
final class OpenCvResult {
    final String name
    final int width
    final int height
    final float[] values

    OpenCvResult(final String name, final int width, final int height, final float[] values) {
        this.name = name
        this.width = width
        this.height = height
        this.values = values != null ? (float[]) values.clone() : new float[0]
    }

    int size() { values.length }

    float getAt(final int index) { values[index] }

    float getAt(final int row, final int col) { values[row * width + col] }

    List<Float> toList() { values.toList() }
}
