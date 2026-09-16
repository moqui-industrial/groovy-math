/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.jax

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.spi.DeclaredModel
import org.moqui.math.spi.MathProvider
import org.moqui.math.spi.MathProviderFactory

/**
 * Claims models whose declared solving method is JAX / OpenXLA, or any runtime declared beneath
 * it. The variants are not listed here: MmsmJaxJit already declares MmsmJax as its parentEnumId,
 * so walking the hierarchy the schema states keeps this rule correct when a new one is seeded.
 */
@CompileStatic
final class JaxProviderFactory implements MathProviderFactory {
    @Override
    String getProviderId() { 'jax' }

    @Override
    int getPriority() { 50 }

    @Override
    boolean claims(final MathMeta mathMeta, final DeclaredModel model) {
        mathMeta.definition.isEnumOrDescendantOf(model.solvingMethod, 'MmsmJax')
    }

    @Override
    MathProvider<?, ?> create(final String mathModelId) {
        new JaxProvider(mathModelId)
    }
}
