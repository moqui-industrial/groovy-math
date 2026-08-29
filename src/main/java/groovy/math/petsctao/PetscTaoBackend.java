/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.petsctao;

public interface PetscTaoBackend {
    long createBoundedQuadraticPlan(int dimension, double[] hessian, double[] linear,
                                    double[] lowerBounds, double[] upperBounds,
                                    double[] initialPoint);
    PetscTaoNativeResult solve(long handle, int dimension);
    void destroy(long handle);
}
