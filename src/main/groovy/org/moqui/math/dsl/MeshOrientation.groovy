/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MeshOrientation
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MeshOrientation implements DslEnumValue {
    NotOrientable('MoNotOrientable', '', 'Not Orientable', ''),
    Positive('MoPositive', '', 'Positive or Clockwise or Right‑Handed', ''),
    Negative('MoNegative', '', 'Negative or Anticlockwise or Left‑Handed', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MeshOrientation(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MeshOrientation fromId(final String id) {
        if (id == null) return null
        for (MeshOrientation val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MeshOrientation fromCode(final String code) {
        if (code == null) return null
        for (MeshOrientation val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
