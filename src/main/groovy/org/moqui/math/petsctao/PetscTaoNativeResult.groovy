/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.petsctao

import groovy.transform.CompileStatic

@CompileStatic
final class PetscTaoNativeResult {
    final double[] solution
    final double objectiveValue
    final double gradientNorm
    final int iterations
    final int reasonCode

    PetscTaoNativeResult(final double[] solution, final double objectiveValue,
                         final double gradientNorm, final int iterations,
                         final int reasonCode) {
        this.solution = solution.clone()
        this.objectiveValue = objectiveValue
        this.gradientNorm = gradientNorm
        this.iterations = iterations
        this.reasonCode = reasonCode
    }
}
