/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.moqui.math.entity.ModelProvider

@CompileStatic
class DecompositionResult {
    final ModelProvider U
    final ModelProvider Sigma
    final ModelProvider Vt

    DecompositionResult(final ModelProvider U, final ModelProvider Sigma, final ModelProvider Vt) {
        this.U = U
        this.Sigma = Sigma
        this.Vt = Vt
    }

    Object getAt(final int index) {
        switch (index) {
            case 0: return U
            case 1: return Sigma
            case 2: return Vt
            default: throw new IndexOutOfBoundsException("Decomposition index ${index} out of range [0..2]")
        }
    }
}
