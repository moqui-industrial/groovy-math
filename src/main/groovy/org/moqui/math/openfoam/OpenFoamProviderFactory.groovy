/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.openfoam

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.spi.DeclaredModel
import org.moqui.math.spi.MathProvider
import org.moqui.math.spi.MathProviderFactory

/**
 * Claims OpenFOAM solvers and computational fluid dynamics models. The specific solvers are not
 * listed here: MmsmOpenFoamIcoFoam and MmsmOpenFoamSimpleFoam declare MmsmOpenFoam as their
 * parentEnumId, so the schema's own hierarchy decides what counts as an OpenFOAM runtime.
 */
@CompileStatic
final class OpenFoamProviderFactory implements MathProviderFactory {
    @Override
    String getProviderId() { 'openfoam' }

    @Override
    int getPriority() { 30 }

    @Override
    boolean claims(final MathMeta mathMeta, final DeclaredModel model) {
        mathMeta.definition.isEnumOrDescendantOf(model.solvingMethod, 'MmsmOpenFoam') ||
            model.modelType == 'MmtCFD'
    }

    @Override
    MathProvider<?, ?> create(final String mathModelId) {
        new OpenFoamProvider(mathModelId)
    }
}
