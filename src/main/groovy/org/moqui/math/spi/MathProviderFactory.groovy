/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.spi

import org.moqui.math.dsl.MathMeta

/**
 * Discovers a provider for a declared model.
 *
 * <p>Implementations are found with {@link java.util.ServiceLoader}, so a backend can be added
 * from another jar by shipping an implementation and a
 * {@code META-INF/services/org.moqui.math.spi.MathProviderFactory} entry. Nothing in Groovy
 * Math needs to know the new backend exists.
 *
 * <p>Selection is on declared metadata, never on free-text names: a model whose author happened
 * to call a step "blur" must not be routed to a vision backend because of it.
 */
interface MathProviderFactory {
    /** Stable identifier, matching the provider's own {@code getProviderId()}. */
    String getProviderId()

    /**
     * Order among factories that all claim the same model; the highest wins. The general-purpose
     * fallback uses {@link Integer#MIN_VALUE} so that any declared backend outranks it.
     */
    int getPriority()

    /**
     * Whether this factory claims the model. {@code model} carries the declared facts already
     * resolved, including the model type reached through MathModelDef; {@code mathMeta} is
     * there for claims that depend on the declared pipeline rather than on those enums.
     */
    boolean claims(MathMeta mathMeta, DeclaredModel model)

    /** Builds the provider for this model. */
    MathProvider<?, ?> create(String mathModelId)
}
