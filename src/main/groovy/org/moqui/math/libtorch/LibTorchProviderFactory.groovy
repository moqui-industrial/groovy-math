/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.spi.DeclaredModel
import org.moqui.math.spi.MathProvider
import org.moqui.math.spi.MathProviderFactory

/**
 * The general-purpose backend: claims every model, at the lowest possible priority, so that any
 * factory declaring an actual affinity for a model outranks it. This is what used to be the
 * final else branch of the dispatcher.
 */
@CompileStatic
final class LibTorchProviderFactory implements MathProviderFactory {
    @Override
    String getProviderId() { 'libtorch' }

    @Override
    int getPriority() { Integer.MIN_VALUE }

    @Override
    boolean claims(final MathMeta mathMeta, final DeclaredModel model) { true }

    @Override
    MathProvider<?, ?> create(final String mathModelId) {
        new LibTorchProvider(mathModelId)
    }
}
