/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.petsctao;

final class PetscTaoBindings {
    private PetscTaoBindings() { }

    static native long nativeCreateBoundedQuadraticPlan(
            int dimension, double[] hessian, double[] linear,
            double[] lowerBounds, double[] upperBounds, double[] initialPoint);
    static native double[] nativeSolve(long handle);
    static native void nativeDestroy(long handle);
}
