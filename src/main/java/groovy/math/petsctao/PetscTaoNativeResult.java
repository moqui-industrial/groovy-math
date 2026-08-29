/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.petsctao;

public final class PetscTaoNativeResult {
    public final double[] solution;
    public final double objectiveValue;
    public final double gradientNorm;
    public final int iterations;
    public final int reasonCode;

    public PetscTaoNativeResult(final double[] solution, final double objectiveValue,
                                final double gradientNorm, final int iterations,
                                final int reasonCode) {
        this.solution = solution != null ? solution.clone() : new double[0];
        this.objectiveValue = objectiveValue;
        this.gradientNorm = gradientNorm;
        this.iterations = iterations;
        this.reasonCode = reasonCode;
    }
}
