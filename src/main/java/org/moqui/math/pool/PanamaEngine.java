/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.pool;

/**
 * Represents a pooled native FFM engine resource (e.g. a native Plan or Solver).
 */
public interface PanamaEngine {

    /**
     * The underlying native C/C++ memory handle or pointer.
     */
    long nativeHandle();

    /**
     * Checks if this engine resource is valid and active.
     */
    boolean isValid();

    /**
     * Explicitly destroys the native C/C++ memory allocation.
     * Called when the pool shuts down or an engine is retired.
     */
    void destroy();
}
