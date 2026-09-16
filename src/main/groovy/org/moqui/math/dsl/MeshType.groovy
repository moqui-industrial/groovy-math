/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum MeshType implements DslEnumValue {
    Triangle('MtTriangle'),
    Quad('MtQuad'),
    Polygon('MtPolygon'),
    Tetrahedral('MtTetrahedral'),
    Hexahedral('MtHexahedral')

    final String id

    MeshType(final String id) {
        this.id = id
    }
}
