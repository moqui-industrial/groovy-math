/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.spi

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.EnumerationDefinition
import org.moqui.math.entity.ModelValue

/**
 * Resolves which backend runs a declared model, from the factories on the classpath.
 *
 * <p>This replaces a hard-coded if/else over backend enum ids. The behaviour is the same, but
 * the list of backends is now open: the order that used to be the order of the branches is now
 * {@link MathProviderFactory#getPriority()}.
 */
@CompileStatic
final class MathProviderRegistry {
    private MathProviderRegistry() { }

    /** Factories visible on the current thread's context classpath, highest priority first. */
    static List<MathProviderFactory> factories() {
        List<MathProviderFactory> found = []
        ServiceLoader.load(MathProviderFactory, MathProviderRegistry.classLoader)
            .each { MathProviderFactory factory -> found.add(factory) }
        sorted(found)
    }

    /** The factory that claims this model, or null when none does. */
    static MathProviderFactory select(final MathMeta mathMeta, final ModelValue model) {
        select(factories(), mathMeta, model)
    }

    /** Selection over an explicit candidate list, so callers and tests can supply their own. */
    static MathProviderFactory select(final Iterable<MathProviderFactory> candidates,
                                      final MathMeta mathMeta, final ModelValue model) {
        DeclaredModel declared = DeclaredModel.of(mathMeta, model)
        for (MathProviderFactory factory : sorted(candidates)) {
            if (factory.claims(mathMeta, declared)) return factory
        }
        null
    }

    /**
     * Why no backend took this model, with the choices the schema actually offers.
     *
     * <p>A model that nothing claims is usually one whose solvingMethodEnumId names an algorithm
     * rather than a runtime, so the message lists the runtimes the schema declares instead of
     * leaving the author to guess.
     */
    static String unclaimedMessage(final MathMeta mathMeta, final ModelValue model) {
        DeclaredModel declared = DeclaredModel.of(mathMeta, model)
        List<String> runtimes = []
        ['MmsmOpenCv', 'MmsmJax', 'MmsmLibTorch', 'MmsmPetscTao', 'MmsmOrTools', 'MmsmOpenFoam']
            .each { String root ->
                mathMeta.definition.enumerationsUnder(root, true, true)
                    .each { EnumerationDefinition value -> runtimes.add(value.enumId) }
            }
        "No MathProviderFactory claims MathModel '${declared.mathModelId}' " +
            "(solvingMethod=${declared.solvingMethod}, modelType=${declared.modelType}). " +
            "Declared runtime solving methods are: ${runtimes.join(', ')}"
    }

    private static List<MathProviderFactory> sorted(final Iterable<MathProviderFactory> candidates) {
        List<MathProviderFactory> list = []
        for (MathProviderFactory factory : candidates) list.add(factory)
        // Descending priority; ties fall back to the provider id so the order is deterministic
        // rather than dependent on classpath scan order.
        list.sort { MathProviderFactory left, MathProviderFactory right ->
            int byPriority = Integer.compare(right.priority, left.priority)
            byPriority != 0 ? byPriority : left.providerId <=> right.providerId
        }
        list
    }
}
