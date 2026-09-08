/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.pool;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * High-performance concurrent pool of native Panama engines.
 * Eliminates global C++ mutex contention by providing dedicated, thread-safe native handles.
 */
public class PanamaEnginePool<T extends PanamaEngine> implements AutoCloseable {

    private final String poolName;
    private final PanamaEngineFactory<T> factory;
    private final int maxCapacity;
    private final Semaphore permits;
    private final ConcurrentLinkedQueue<T> idleEngines;
    private final AtomicInteger totalCreated = new AtomicInteger(0);
    private final AtomicBoolean shuttingDown = new AtomicBoolean(false);

    public PanamaEnginePool(String poolName, PanamaEngineFactory<T> factory, int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("maxCapacity must be greater than 0");
        }
        this.poolName = poolName != null ? poolName : "AnonymousPanamaPool";
        this.factory = Objects.requireNonNull(factory, "factory cannot be null");
        this.maxCapacity = maxCapacity;
        this.permits = new Semaphore(maxCapacity, true);
        this.idleEngines = new ConcurrentLinkedQueue<>();
    }

    /**
     * Acquires a native engine wrapped in an AutoCloseable PooledSession.
     * Usage:
     * <pre>
     * try (PooledSession&lt;MyEngine&gt; session = pool.acquire()) {
     *     session.engine().execute(...);
     * }
     * </pre>
     */
    public PooledSession<T> acquire() throws InterruptedException {
        return acquire(10, TimeUnit.SECONDS);
    }

    public PooledSession<T> acquire(long timeout, TimeUnit unit) throws InterruptedException {
        if (shuttingDown.get()) {
            throw new IllegalStateException("Pool '" + poolName + "' is shutting down; refusing new acquisitions.");
        }

        boolean acquired = permits.tryAcquire(timeout, unit);
        if (!acquired) {
            throw new IllegalStateException("Timeout acquiring engine from pool '" + poolName +
                    "' after " + timeout + " " + unit);
        }

        T engine = idleEngines.poll();
        if (engine == null || !engine.isValid()) {
            try {
                engine = factory.createEngine();
                totalCreated.incrementAndGet();
            } catch (Throwable t) {
                permits.release();
                throw new RuntimeException("Failed to instantiate native engine in pool '" + poolName + "': " + t.getMessage(), t);
            }
        }

        return new PooledSession<>(this, engine);
    }

    /**
     * Returns an engine to the pool.
     */
    public void returnEngine(T engine) {
        if (engine == null) {
            permits.release();
            return;
        }

        if (shuttingDown.get() || !engine.isValid()) {
            factory.destroyEngine(engine);
            totalCreated.decrementAndGet();
            permits.release();
            return;
        }

        idleEngines.offer(engine);
        permits.release();
    }

    public int getMaxCapacity() { return maxCapacity; }
    public int getAvailablePermits() { return permits.availablePermits(); }
    public int getIdleCount() { return idleEngines.size(); }
    public int getTotalCreated() { return totalCreated.get(); }
    public String getPoolName() { return poolName; }

    @Override
    public void close() {
        if (shuttingDown.compareAndSet(false, true)) {
            T engine;
            while ((engine = idleEngines.poll()) != null) {
                factory.destroyEngine(engine);
                totalCreated.decrementAndGet();
            }
        }
    }
}
