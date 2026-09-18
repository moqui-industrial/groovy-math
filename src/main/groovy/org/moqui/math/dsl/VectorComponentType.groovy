/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: VectorComponentType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum VectorComponentType implements DslEnumValue {
    Canonical('VctCanonical', '', 'Canonical (Basis-Free) Component', ''),
    InCoordinateSystem('VctInCoordinateSystem', '', 'Component in Coordinate System', ''),
    Symbolic('VctSymbolic', '', 'Symbolic Vector Component', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    VectorComponentType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static VectorComponentType fromId(final String id) {
        if (id == null) return null
        for (VectorComponentType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static VectorComponentType fromCode(final String code) {
        if (code == null) return null
        for (VectorComponentType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
