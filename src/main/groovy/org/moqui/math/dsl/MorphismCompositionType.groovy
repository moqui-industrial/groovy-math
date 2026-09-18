/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MorphismCompositionType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MorphismCompositionType implements DslEnumValue {
    Binary('McBinary', '', 'Binary Composition (g ∘ f)', ''),
    Identity('McIdentity', '', 'Identity Law Proof', ''),
    Assoc('McAssoc', '', 'Associativity Proof', ''),
    UnitLaw('McUnitLaw', '', 'Unit Law Proof', ''),
    ZeroLaw('McZeroLaw', '', 'Zero-Arrow Annihilation', ''),
    Nary('McNary', '', 'N-ary Composition (f1 o f2 o ... o fn)', ''),
    NatVert('McNatVert', '', 'Natural-Transformation Vertical Composition', ''),
    Iterate('McIterate', '', 'Iterated Composition (f o f o ... o f, once per element of a context list)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MorphismCompositionType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MorphismCompositionType fromId(final String id) {
        if (id == null) return null
        for (MorphismCompositionType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MorphismCompositionType fromCode(final String code) {
        if (code == null) return null
        for (MorphismCompositionType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
