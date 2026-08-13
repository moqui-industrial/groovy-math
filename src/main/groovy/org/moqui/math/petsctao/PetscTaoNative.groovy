/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.petsctao

import groovy.transform.CompileStatic

@CompileStatic
final class PetscTaoNative implements PetscTaoBackend {
    static final PetscTaoNative INSTANCE = new PetscTaoNative()
    private static boolean loaded

    private PetscTaoNative() { }

    private static synchronized void ensureLoaded() {
        if (loaded) return
        String explicitPath = System.getProperty('groovy.math.petsctao.library')
        if (explicitPath) System.load(new File(explicitPath).absolutePath)
        else System.loadLibrary('groovy_math_petsctao')
        loaded = true
    }

    @Override
    long createBoundedQuadraticPlan(final int dimension, final double[] hessian,
                                    final double[] linear, final double[] lowerBounds,
                                    final double[] upperBounds, final double[] initialPoint) {
        ensureLoaded()
        PetscTaoBindings.nativeCreateBoundedQuadraticPlan(
            dimension, hessian, linear, lowerBounds, upperBounds, initialPoint)
    }

    @Override
    PetscTaoNativeResult solve(final long handle, final int dimension) {
        ensureLoaded()
        double[] encoded = PetscTaoBindings.nativeSolve(handle)
        if (encoded == null || encoded.length != dimension + 4) {
            throw new IllegalStateException('PETSc/TAO native result has an invalid length')
        }
        double[] solution = Arrays.copyOfRange(encoded, 0, dimension)
        new PetscTaoNativeResult(solution, encoded[dimension], encoded[dimension + 1],
            (int) encoded[dimension + 2], (int) encoded[dimension + 3])
    }

    @Override
    void destroy(final long handle) {
        ensureLoaded()
        PetscTaoBindings.nativeDestroy(handle)
    }
}
