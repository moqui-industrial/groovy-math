/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.petsctao

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.spi.DeclaredModel
import org.moqui.math.spi.MathProvider
import org.moqui.math.spi.MathProviderFactory

/**
 * Claims PETSc/TAO solving methods and quadratic programs.
 */
@CompileStatic
final class PetscTaoProviderFactory implements MathProviderFactory {
    @Override
    String getProviderId() { 'petsctao' }

    @Override
    int getPriority() { 40 }

    @Override
    boolean claims(final MathMeta mathMeta, final DeclaredModel model) {
        model.solvingMethod == 'MmsmPetscTao' || model.modelType == 'MmtQp'
    }

    @Override
    MathProvider<?, ?> create(final String mathModelId) {
        new PetscTaoProvider(mathModelId)
    }
}
