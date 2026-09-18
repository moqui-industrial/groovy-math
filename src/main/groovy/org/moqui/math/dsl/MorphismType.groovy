/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MorphismType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MorphismType implements DslEnumValue {
    MtGeneral('MtGeneral', '', 'General Morphism', ''),
    MtIso('MtIso', '', 'Isomorphism', ''),
    MtEndo('MtEndo', '', 'Endomorphism', ''),
    MtEpi('MtEpi', '', 'Epimorphism', ''),
    MtMono('MtMono', '', 'Monomorphism', ''),
    MtId('MtId', '', 'Identity morphism; may have a composite implementation', ''),
    MphZero('MphZero', '', 'Zero morphism', ''),
    MtTerm('MtTerm', '', 'Typed Term from a Context to a Type', ''),
    MtDisplayMap('MtDisplayMap', '', 'Display Map representing a Dependent Type', ''),
    MtRetract('MtRetract', '', 'Retract (split epi)', ''),
    MtSection('MtSection', '', 'Section (split mono)', ''),
    MtLeftInv('MtLeftInv', '', 'Retraction left-inverse', ''),
    MtRightInv('MtRightInv', '', 'Section right-inverse', ''),
    MtLaw('MtLaw', '', 'Structure-Preserving Law', ''),
    MtLawGroupHom('MtLawGroupHom', '', 'Group Homomorphism', 'MtLaw'),
    MtLawRingHom('MtLawRingHom', '', 'Ring Homomorphism', 'MtLaw'),
    MtLawLinear('MtLawLinear', '', 'Linear Map (Vector Space)', 'MtLaw');

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

    static MorphismType fromName(final String name) {
        if (name == null) return null
        for (MorphismType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('GeneralMorphism'.equalsIgnoreCase(name)) return MtGeneral
        if ('Isomorphism'.equalsIgnoreCase(name)) return MtIso
        if ('Endomorphism'.equalsIgnoreCase(name)) return MtEndo
        if ('Epimorphism'.equalsIgnoreCase(name)) return MtEpi
        if ('Monomorphism'.equalsIgnoreCase(name)) return MtMono
        if ('IdentityMorphismMayHaveACompositeImplementation'.equalsIgnoreCase(name)) return MtId
        if ('ZeroMorphism'.equalsIgnoreCase(name)) return MphZero
        if ('TypedTermFromAContextToAType'.equalsIgnoreCase(name)) return MtTerm
        if ('DisplayMapRepresentingADependentType'.equalsIgnoreCase(name)) return MtDisplayMap
        if ('Retract'.equalsIgnoreCase(name)) return MtRetract
        if ('Section'.equalsIgnoreCase(name)) return MtSection
        if ('RetractionLeftInverse'.equalsIgnoreCase(name)) return MtLeftInv
        if ('SectionRightInverse'.equalsIgnoreCase(name)) return MtRightInv
        if ('StructurePreservingLaw'.equalsIgnoreCase(name)) return MtLaw
        if ('GroupHomomorphism'.equalsIgnoreCase(name)) return MtLawGroupHom
        if ('RingHomomorphism'.equalsIgnoreCase(name)) return MtLawRingHom
        if ('LinearMap'.equalsIgnoreCase(name)) return MtLawLinear
        null
    }
}
