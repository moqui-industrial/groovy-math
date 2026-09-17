/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode
class DslSymbol implements DslEnumValue, Serializable {
    private static final long serialVersionUID = 1L

    final String name
    final String id
    final String enumTypeId

    DslSymbol(final String name, final String id, final String enumTypeId = null) {
        this.name = name
        this.id = id
        this.enumTypeId = enumTypeId
    }

    @Override
    String getId() {
        id
    }

    @Override
    String toString() {
        id
    }
}
