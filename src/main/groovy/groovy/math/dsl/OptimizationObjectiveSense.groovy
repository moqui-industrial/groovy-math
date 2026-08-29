/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.dsl

enum OptimizationObjectiveSense implements DslEnumValue {
    Minimize('MINIMIZE'),
    Maximize('MAXIMIZE')

    final String id

    OptimizationObjectiveSense(final String id) {
        this.id = id
    }
}
