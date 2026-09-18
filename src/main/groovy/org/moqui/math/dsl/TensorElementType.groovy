/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorElementType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorElementType implements DslEnumValue {
    Dense('TctDense', '', 'Dense Entry', ''),
    Sparse('TctSparse', '', 'Sparse Non-zero Entry', ''),
    Symbolic('TctSymbolic', '', 'Symbolic Entry', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorElementType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorElementType fromId(final String id) {
        if (id == null) return null
        for (TensorElementType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorElementType fromCode(final String code) {
        if (code == null) return null
        for (TensorElementType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TensorElementType fromName(final String name) {
        if (name == null) return null
        for (TensorElementType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('DenseEntry'.equalsIgnoreCase(name)) return Dense
        if ('SparseNonZeroEntry'.equalsIgnoreCase(name)) return Sparse
        if ('SymbolicEntry'.equalsIgnoreCase(name)) return Symbolic
        null
    }
}
