/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelDataType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelDataType implements DslEnumValue {
    Vector('MmdtVector', '', 'Vector', ''),
    Matrix('MmdtMatrix', '', 'Matrix', ''),
    Tensor('MmdtTensor', '', 'Tensor', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelDataType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelDataType fromId(final String id) {
        if (id == null) return null
        for (MathModelDataType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelDataType fromCode(final String code) {
        if (code == null) return null
        for (MathModelDataType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
