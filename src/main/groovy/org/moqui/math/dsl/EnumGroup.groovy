/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: EnumGroup
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum EnumGroup implements DslEnumValue {
    Eng3DEuclideanSpace('Eng3DEuclideanSpace', 'R3', '3D Euclidean Space', ''),
    SpaceTime('EngSpaceTime', '', 'Space Time', ''),
    Eng2DEuclideanSpace('Eng2DEuclideanSpace', 'R2', '2D Euclidean Space', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    EnumGroup(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static EnumGroup fromId(final String id) {
        if (id == null) return null
        for (EnumGroup val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static EnumGroup fromCode(final String code) {
        if (code == null) return null
        for (EnumGroup val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
