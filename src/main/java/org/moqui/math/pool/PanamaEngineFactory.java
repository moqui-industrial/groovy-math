/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.pool;

/**
 * Factory responsible for instantiating and destroying native Panama engines.
 */
@FunctionalInterface
public interface PanamaEngineFactory<T extends PanamaEngine> {

    /**
     * Creates a new, fully initialized native engine instance.
     */
    T createEngine();

    /**
     * Destroys an engine instance when evicted or pool is shut down.
     */
    default void destroyEngine(T engine) {
        if (engine != null && engine.isValid()) {
            engine.destroy();
        }
    }
}
