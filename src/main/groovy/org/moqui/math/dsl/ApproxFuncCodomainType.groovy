/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ApproxFuncCodomainType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ApproxFuncCodomainType implements DslEnumValue {
    Scalar('AfctScalar', '', 'Scalar-valued function', ''),
    Vector('AfctVector', '', 'Vector-valued function', ''),
    Matrix('AfctMatrix', '', 'Matrix-valued function', ''),
    Tensor('AfctTensor', '', 'Tensor-valued function', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ApproxFuncCodomainType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ApproxFuncCodomainType fromId(final String id) {
        if (id == null) return null
        for (ApproxFuncCodomainType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ApproxFuncCodomainType fromCode(final String code) {
        if (code == null) return null
        for (ApproxFuncCodomainType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static ApproxFuncCodomainType fromName(final String name) {
        if (name == null) return null
        for (ApproxFuncCodomainType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('ScalarValuedFunction'.equalsIgnoreCase(name)) return Scalar
        if ('VectorValuedFunction'.equalsIgnoreCase(name)) return Vector
        if ('MatrixValuedFunction'.equalsIgnoreCase(name)) return Matrix
        if ('TensorValuedFunction'.equalsIgnoreCase(name)) return Tensor
        null
    }
}
