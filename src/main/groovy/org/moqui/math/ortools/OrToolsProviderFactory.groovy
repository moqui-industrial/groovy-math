/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.ortools

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.spi.DeclaredModel
import org.moqui.math.spi.MathProvider
import org.moqui.math.spi.MathProviderFactory

/**
 * Claims OR-Tools solving methods, linear and mixed-integer programs.
 */
@CompileStatic
final class OrToolsProviderFactory implements MathProviderFactory {
    @Override
    String getProviderId() { 'ortools' }

    @Override
    int getPriority() { 20 }

    @Override
    boolean claims(final MathMeta mathMeta, final DeclaredModel model) {
        model.solvingMethod == 'MmsmOrTools' ||
            model.modelType in ['MmtLp', 'MmtMilp']
    }

    @Override
    MathProvider<?, ?> create(final String mathModelId) {
        new OrToolsProvider(mathModelId)
    }
}
