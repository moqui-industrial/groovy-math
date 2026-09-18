/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorType implements DslEnumValue {
    Dense('TtDense', '', 'Dense Tensor', ''),
    Sparse('TtSparse', '', 'Sparse Tensor', ''),
    Symmetric('TtSymmetric', '', 'Symmetric Tensor', ''),
    Diagonal('TtDiagonal', '', 'Diagonal Tensor', ''),
    Jagged('TtJagged', '', 'Jagged / Ragged Tensor', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorType fromId(final String id) {
        if (id == null) return null
        for (TensorType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorType fromCode(final String code) {
        if (code == null) return null
        for (TensorType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TensorType fromName(final String name) {
        if (name == null) return null
        for (TensorType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('DenseTensor'.equalsIgnoreCase(name)) return Dense
        if ('SparseTensor'.equalsIgnoreCase(name)) return Sparse
        if ('SymmetricTensor'.equalsIgnoreCase(name)) return Symmetric
        if ('DiagonalTensor'.equalsIgnoreCase(name)) return Diagonal
        if ('JaggedRaggedTensor'.equalsIgnoreCase(name)) return Jagged
        null
    }
}
