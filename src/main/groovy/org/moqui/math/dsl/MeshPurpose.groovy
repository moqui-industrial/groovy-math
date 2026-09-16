/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum MeshPurpose implements DslEnumValue {
    FEM('MpFEM'),
    FVM('MpFVM'),
    CFD('MpCFD'),
    TopologicalMesh('MpTopologicalMesh')

    final String id

    MeshPurpose(final String id) {
        this.id = id
    }
}
