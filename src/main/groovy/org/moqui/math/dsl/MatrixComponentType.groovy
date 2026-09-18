/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MatrixComponentType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MatrixComponentType implements DslEnumValue {
    Canonical('MctCanonical', '', 'Canonical Matrix Component', ''),
    Sparse('MctSparse', '', 'Sparse Matrix Component', ''),
    Symbolic('MctSymbolic', '', 'Symbolic Matrix Component', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MatrixComponentType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MatrixComponentType fromId(final String id) {
        if (id == null) return null
        for (MatrixComponentType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MatrixComponentType fromCode(final String code) {
        if (code == null) return null
        for (MatrixComponentType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
