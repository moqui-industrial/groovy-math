/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.petsctao

import groovy.transform.CompileStatic

@CompileStatic
final class PetscTaoResult {
    private static final Map<Integer, String> REASONS = Collections.unmodifiableMap([
        (3): 'TAO_CONVERGED_GATOL',
        (4): 'TAO_CONVERGED_GRTOL',
        (5): 'TAO_CONVERGED_GTTOL',
        (6): 'TAO_CONVERGED_STEPTOL',
        (7): 'TAO_CONVERGED_MINF',
        (8): 'TAO_CONVERGED_USER',
        (-2): 'TAO_DIVERGED_MAXITS',
        (-4): 'TAO_DIVERGED_NAN',
        (-5): 'TAO_DIVERGED_MAXFCN',
        (-6): 'TAO_DIVERGED_LS_FAILURE',
        (-7): 'TAO_DIVERGED_TR_REDUCTION',
        (-8): 'TAO_DIVERGED_USER',
        (0): 'TAO_CONTINUE_ITERATING'
    ] as LinkedHashMap<Integer, String>)

    final String mathModelId
    final String solverType
    final int reasonCode
    final String reason
    final double objectiveValue
    final double gradientNorm
    final int iterations
    final Map<String, Double> variableValues

    PetscTaoResult(final String mathModelId, final String solverType, final int reasonCode,
                   final double objectiveValue, final double gradientNorm,
                   final int iterations, final Map<String, Double> variableValues) {
        this.mathModelId = mathModelId
        this.solverType = solverType
        this.reasonCode = reasonCode
        this.reason = REASONS.get(reasonCode) ?: "TAO_REASON_${reasonCode}"
        this.objectiveValue = objectiveValue
        this.gradientNorm = gradientNorm
        this.iterations = iterations
        this.variableValues = Collections.unmodifiableMap(new LinkedHashMap<>(variableValues))
    }

    boolean isSuccess() { reasonCode > 0 }
}
