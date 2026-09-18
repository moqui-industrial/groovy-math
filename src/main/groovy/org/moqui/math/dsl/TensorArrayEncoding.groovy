/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TensorArrayEncoding
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TensorArrayEncoding implements DslEnumValue {
    Json('TaeJson', '', 'JSON Numbers', ''),
    Cbor('TaeCbor', '', 'CBOR', ''),
    MsgPack('TaeMsgPack', '', 'Message Pack', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TensorArrayEncoding(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TensorArrayEncoding fromId(final String id) {
        if (id == null) return null
        for (TensorArrayEncoding val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TensorArrayEncoding fromCode(final String code) {
        if (code == null) return null
        for (TensorArrayEncoding val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TensorArrayEncoding fromName(final String name) {
        if (name == null) return null
        for (TensorArrayEncoding val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('JsonNumbers'.equalsIgnoreCase(name)) return Json
        if ('MessagePack'.equalsIgnoreCase(name)) return MsgPack
        null
    }
}
