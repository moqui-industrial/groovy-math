/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.pool;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AutoCloseable session that safely returns a native engine to its pool upon completion.
 */
public final class PooledSession<T extends PanamaEngine> implements AutoCloseable {

    private final PanamaEnginePool<T> pool;
    private final T engine;
    private final AtomicBoolean closed = new AtomicBoolean(false);

    public PooledSession(PanamaEnginePool<T> pool, T engine) {
        this.pool = Objects.requireNonNull(pool, "pool cannot be null");
        this.engine = Objects.requireNonNull(engine, "engine cannot be null");
    }

    public T engine() {
        if (closed.get()) {
            throw new IllegalStateException("PooledSession is already closed");
        }
        return engine;
    }

    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            pool.returnEngine(engine);
        }
    }
}
