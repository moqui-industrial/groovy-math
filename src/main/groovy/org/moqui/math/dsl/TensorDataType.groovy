/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorDataType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorDataType implements DslEnumValue {
    Float32('DtFloat32', 'float32', '32-bit IEEE 754 Floating Point', ''),
    Float64('DtFloat64', 'float64', '64-bit IEEE 754 Floating Point', ''),
    Float16('DtFloat16', 'float16', '16-bit IEEE 754 Floating Point', ''),
    BFloat16('DtBFloat16', 'bfloat16', '16-bit Brain Floating Point', ''),
    Int32('DtInt32', 'int32', '32-bit Signed Integer', ''),
    Int64('DtInt64', 'int64', '64-bit Signed Integer', ''),
    Int8('DtInt8', 'int8', '8-bit Signed Integer', ''),
    UInt8('DtUInt8', 'uint8', '8-bit Unsigned Integer', ''),
    Bool('DtBool', 'bool', 'Boolean', ''),
    Complex64('DtComplex64', 'complex64', '64-bit Complex', ''),
    Complex128('DtComplex128', 'complex128', '128-bit Complex', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorDataType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorDataType fromId(final String id) {
        if (id == null) return null
        for (TensorDataType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorDataType fromCode(final String code) {
        if (code == null) return null
        for (TensorDataType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
