/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorAxisType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorAxisType implements DslEnumValue {
    Dense('TatDense', '', 'Dense Indexing (stride)', ''),
    Sparse('TatSparse', '', 'Sparse Index / Pointer', ''),
    Ragged('TatRagged', '', 'Ragged / Jagged Blocks', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorAxisType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorAxisType fromId(final String id) {
        if (id == null) return null
        for (TensorAxisType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorAxisType fromCode(final String code) {
        if (code == null) return null
        for (TensorAxisType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TensorAxisType fromName(final String name) {
        if (name == null) return null
        for (TensorAxisType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('DenseIndexing'.equalsIgnoreCase(name)) return Dense
        if ('SparseIndexPointer'.equalsIgnoreCase(name)) return Sparse
        if ('RaggedJaggedBlocks'.equalsIgnoreCase(name)) return Ragged
        null
    }
}
