/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.dsl

enum MeshAdaptationType implements DslEnumValue {
    None('MatNone'),
    HRefinement('MatHRefinement'),
    HDerefinement('MatHDerefinement'),
    RRefinement('MatRRefinement'),
    PRefinement('MatPRefinement'),
    Remeshing('MatRemeshing'),
    RhRefinement('MatRhRefinement'),
    Automatic('MatAutomatic'),
    Custom('MatCustom')

    final String id

    MeshAdaptationType(final String id) {
        this.id = id
    }
}
