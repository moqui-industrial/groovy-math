/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ParameterClass
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ParameterClass implements DslEnumValue {
    Main('PcMain', '', 'Main Parameter', ''),
    Auxiliary('PcAuxiliary', '', 'Auxiliary Parameter', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ParameterClass(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ParameterClass fromId(final String id) {
        if (id == null) return null
        for (ParameterClass val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ParameterClass fromCode(final String code) {
        if (code == null) return null
        for (ParameterClass val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static ParameterClass fromName(final String name) {
        if (name == null) return null
        for (ParameterClass val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('MainParameter'.equalsIgnoreCase(name)) return Main
        if ('AuxiliaryParameter'.equalsIgnoreCase(name)) return Auxiliary
        null
    }
}
