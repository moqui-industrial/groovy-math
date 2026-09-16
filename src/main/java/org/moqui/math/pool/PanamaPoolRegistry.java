/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.pool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Enterprise Registry for Panama Engine Pools.
 * Matches Moqui CacheFacade and Plc4jToolFactory lifecycle patterns.
 */
public final class PanamaPoolRegistry {

    private static final PanamaPoolRegistry INSTANCE = new PanamaPoolRegistry();
    private final ConcurrentMap<String, PanamaEnginePool<?>> registry = new ConcurrentHashMap<>();

    private PanamaPoolRegistry() { }

    public static PanamaPoolRegistry getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T extends PanamaEngine> PanamaEnginePool<T> getOrCreatePool(String name, PanamaEngineFactory<T> factory, int capacity) {
        return (PanamaEnginePool<T>) registry.computeIfAbsent(name, k -> new PanamaEnginePool<>(k, factory, capacity));
    }

    @SuppressWarnings("unchecked")
    public <T extends PanamaEngine> PanamaEnginePool<T> getPool(String name) {
        return (PanamaEnginePool<T>) registry.get(name);
    }

    public void destroyPool(String name) {
        PanamaEnginePool<?> pool = registry.remove(name);
        if (pool != null) {
            pool.close();
        }
    }

    public void destroyAll() {
        for (String name : registry.keySet()) {
            destroyPool(name);
        }
    }
}
