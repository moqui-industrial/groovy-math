/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: ParametricPathEventType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum ParametricPathEventType implements DslEnumValue {
    Start('PpetStart', '', 'Start Event', ''),
    Stop('PpetStop', '', 'Stop Event', ''),
    Custom('PpetCustom', '', 'Custom Action', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    ParametricPathEventType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static ParametricPathEventType fromId(final String id) {
        if (id == null) return null
        for (ParametricPathEventType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static ParametricPathEventType fromCode(final String code) {
        if (code == null) return null
        for (ParametricPathEventType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static ParametricPathEventType fromName(final String name) {
        if (name == null) return null
        for (ParametricPathEventType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('StartEvent'.equalsIgnoreCase(name)) return Start
        if ('StopEvent'.equalsIgnoreCase(name)) return Stop
        if ('CustomAction'.equalsIgnoreCase(name)) return Custom
        null
    }
}
