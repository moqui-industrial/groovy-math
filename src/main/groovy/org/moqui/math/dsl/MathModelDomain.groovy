/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelDomain
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelDomain implements DslEnumValue {
    Engineering('MmdEngineering', '', 'Engineering', ''),
    Finance('MmdFinance', '', 'Finance', ''),
    Biology('MmdBiology', '', 'Biology', ''),
    Physics('MmdPhysics', '', 'Physics', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelDomain(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelDomain fromId(final String id) {
        if (id == null) return null
        for (MathModelDomain val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelDomain fromCode(final String code) {
        if (code == null) return null
        for (MathModelDomain val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MathModelDomain fromName(final String name) {
        if (name == null) return null
        for (MathModelDomain val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        null
    }
}
