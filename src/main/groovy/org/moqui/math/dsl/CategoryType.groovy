/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: CategoryType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum CategoryType implements DslEnumValue {
    Small('CtSmall', '', 'Small / finite category', ''),
    Monoidal('CtMonoidal', '', 'Monoidal category', ''),
    Topos('CtTopos', '', 'Topos', ''),
    Large('CtLarge', '', 'Large category', ''),
    CCC('CtCCC', '', 'Cartesian-closed category', ''),
    Syntactic('CtSyntactic', '', 'Syntactic category of a type theory', ''),
    LCCC('CtLCCC', '', 'Locally Cartesian-closed category for dependent types', ''),
    Abelian('CtAbelian', '', 'Abelian category', ''),
    Enriched('CtEnriched', '', 'Enriched (V-) category', ''),
    Groupoid('CtGroupoid', '', 'Groupoid', ''),
    Poset('CtPoset', '', 'Poset viewed as category', ''),
    Discrete('CtDiscrete', '', 'Discrete category', ''),
    PreAdditive('CtPreAdditive', '', 'Pre-additive category', ''),
    Set('CtSet', '', 'Category of small sets: objects = sets, morphisms = functions', ''),
    FinSet('CtFinSet', '', 'Finite sets', ''),
    Grp('CtGrp', '', 'Category of Groups and Group Homomorphisms', ''),
    Ab('CtAb', '', 'Abelian Groups and Homomorphisms', ''),
    VectR('CtVectR', '', 'Real Vector Spaces (finite dimension) with Linear Maps', ''),
    VectC('CtVectC', '', 'Complex Vector Spaces (finite dimension) with Linear Maps', ''),
    Top('CtTop', '', 'Topological Spaces with Continuous Maps', ''),
    Cat('CtCat', '', 'Big Category of Small Categories + Functors', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    CategoryType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static CategoryType fromId(final String id) {
        if (id == null) return null
        for (CategoryType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static CategoryType fromCode(final String code) {
        if (code == null) return null
        for (CategoryType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static CategoryType fromName(final String name) {
        if (name == null) return null
        for (CategoryType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('SmallFiniteCategory'.equalsIgnoreCase(name)) return Small
        if ('MonoidalCategory'.equalsIgnoreCase(name)) return Monoidal
        if ('LargeCategory'.equalsIgnoreCase(name)) return Large
        if ('CartesianClosedCategory'.equalsIgnoreCase(name)) return CCC
        if ('SyntacticCategoryOfATypeTheory'.equalsIgnoreCase(name)) return Syntactic
        if ('LocallyCartesianClosedCategoryForDependentTypes'.equalsIgnoreCase(name)) return LCCC
        if ('AbelianCategory'.equalsIgnoreCase(name)) return Abelian
        if ('EnrichedCategory'.equalsIgnoreCase(name)) return Enriched
        if ('PosetViewedAsCategory'.equalsIgnoreCase(name)) return Poset
        if ('DiscreteCategory'.equalsIgnoreCase(name)) return Discrete
        if ('PreAdditiveCategory'.equalsIgnoreCase(name)) return PreAdditive
        if ('CategoryOfSmallSetsObjectsSetsMorphismsFunctions'.equalsIgnoreCase(name)) return Set
        if ('FiniteSets'.equalsIgnoreCase(name)) return FinSet
        if ('CategoryOfGroupsAndGroupHomomorphisms'.equalsIgnoreCase(name)) return Grp
        if ('AbelianGroupsAndHomomorphisms'.equalsIgnoreCase(name)) return Ab
        if ('RealVectorSpacesWithLinearMaps'.equalsIgnoreCase(name)) return VectR
        if ('ComplexVectorSpacesWithLinearMaps'.equalsIgnoreCase(name)) return VectC
        if ('TopologicalSpacesWithContinuousMaps'.equalsIgnoreCase(name)) return Top
        if ('BigCategoryOfSmallCategoriesFunctors'.equalsIgnoreCase(name)) return Cat
        null
    }
}
