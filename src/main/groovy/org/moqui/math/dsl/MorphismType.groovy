/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MorphismType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MorphismType implements DslEnumValue {
    General('MtGeneral', '', 'General Morphism', ''),
    tIso('MtIso', '', 'Isomorphism', ''),
    Endo('MtEndo', '', 'Endomorphism', ''),
    tEpi('MtEpi', '', 'Epimorphism', ''),
    tMono('MtMono', '', 'Monomorphism', ''),
    tId('MtId', '', 'Identity morphism; may have a composite implementation', ''),
    phZero('MphZero', '', 'Zero morphism', ''),
    tTerm('MtTerm', '', 'Typed Term from a Context to a Type', ''),
    tDisplayMap('MtDisplayMap', '', 'Display Map representing a Dependent Type', ''),
    tRetract('MtRetract', '', 'Retract (split epi)', ''),
    tSection('MtSection', '', 'Section (split mono)', ''),
    tLeftInv('MtLeftInv', '', 'Retraction left-inverse', ''),
    tRightInv('MtRightInv', '', 'Section right-inverse', ''),
    tLaw('MtLaw', '', 'Structure-Preserving Law', ''),
    tLawGroupHom('MtLawGroupHom', '', 'Group Homomorphism', 'MtLaw'),
    tLawRingHom('MtLawRingHom', '', 'Ring Homomorphism', 'MtLaw'),
    tLawLinear('MtLawLinear', '', 'Linear Map (Vector Space)', 'MtLaw');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MorphismType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MorphismType fromId(final String id) {
        if (id == null) return null
        for (MorphismType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MorphismType fromCode(final String code) {
        if (code == null) return null
        for (MorphismType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
