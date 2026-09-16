/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum TriangularExtractionType implements DslEnumValue {
    Upper('TetUpper'),
    Lower('TetLower')

    final String id

    TriangularExtractionType(final String id) {
        this.id = id
    }
}
