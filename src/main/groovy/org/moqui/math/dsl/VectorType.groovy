/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: VectorType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum VectorType implements DslEnumValue {
    Ordinary('VtOrdinary', '', 'Ordinary Vector', ''),
    Zero('VtZero', '', 'Zero Vector', ''),
    Unit('VtUnit', '', 'Unit Vector', ''),
    Versor('VtVersor', '', 'Versor', ''),
    StandardBasisVector('VtStandardBasisVector', '', 'Standard Basis Vector', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    VectorType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static VectorType fromId(final String id) {
        if (id == null) return null
        for (VectorType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static VectorType fromCode(final String code) {
        if (code == null) return null
        for (VectorType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static VectorType fromName(final String name) {
        if (name == null) return null
        for (VectorType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('OrdinaryVector'.equalsIgnoreCase(name)) return Ordinary
        if ('ZeroVector'.equalsIgnoreCase(name)) return Zero
        if ('UnitVector'.equalsIgnoreCase(name)) return Unit
        null
    }
}
