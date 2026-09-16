/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

/**
 * Written into Parameter.symbolicValue, a free-text field, so these are literals rather than
 * enumeration ids: see DslSymbolicValue.
 */
enum OptimizationObjectiveSense implements DslSymbolicValue {
    Minimize('MINIMIZE'),
    Maximize('MAXIMIZE')

    final String id

    OptimizationObjectiveSense(final String id) {
        this.id = id
    }
}
