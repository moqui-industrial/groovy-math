/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: NaturalTransformationType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum NaturalTransformationType implements DslEnumValue {
    Iso('NtIso', '', 'Natural Isomorphism', ''),
    Mod('NtMod', '', 'Modification', ''),
    DiNat('NtDiNat', '', 'Dinatural Transformation', ''),
    Cone('NtCone', '', 'Cone (Constant Functor to Diagram Functor)', ''),
    LimitCone('NtLimitCone', '', 'Limit Cone (Universal Cone)', 'NtCone'),
    Cocone('NtCocone', '', 'Cocone (Diagram Functor to Constant Functor)', ''),
    ColimitCocone('NtColimitCocone', '', 'Colimit Cocone (Universal Cocone)', 'NtCocone');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    NaturalTransformationType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static NaturalTransformationType fromId(final String id) {
        if (id == null) return null
        for (NaturalTransformationType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static NaturalTransformationType fromCode(final String code) {
        if (code == null) return null
        for (NaturalTransformationType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static NaturalTransformationType fromName(final String name) {
        if (name == null) return null
        for (NaturalTransformationType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('NaturalIsomorphism'.equalsIgnoreCase(name)) return Iso
        if ('Modification'.equalsIgnoreCase(name)) return Mod
        if ('DinaturalTransformation'.equalsIgnoreCase(name)) return DiNat
        null
    }
}
