/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: CategoryObjectType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum CategoryObjectType implements DslEnumValue {
    Generic('CotGeneric', '', 'Generic object', ''),
    Type('CotType', '', 'Type', ''),
    Context('CotContext', '', 'Typing Context', ''),
    Proposition('CotProposition', '', 'Proposition as Type', 'CotType'),
    Universe('CotUniverse', '', 'Universe of Types', 'CotType'),
    AlgebraicStruct('CotAlgebraicStruct', '', 'Algebraic Structure (one-object category)', ''),
    Monoid('CotMonoid', '', 'Monoid', 'CotAlgebraicStruct'),
    Group('CotGroup', '', 'Group', 'CotAlgebraicStruct'),
    AbGroup('CotAbGroup', '', 'Abelian group', 'CotGroup'),
    Ring('CotRing', '', 'Ring', 'CotAlgebraicStruct'),
    Field('CotField', '', 'Field', 'CotAlgebraicStruct'),
    Module('CotModule', '', 'Module', 'CotAlgebraicStruct'),
    Specification('CotSpecification', '', 'Specification or Observable Predicate', 'CotProposition'),
    OperationalState('CotOperationalState', '', 'Operational State Specification', 'CotSpecification');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    CategoryObjectType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static CategoryObjectType fromId(final String id) {
        if (id == null) return null
        for (CategoryObjectType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static CategoryObjectType fromCode(final String code) {
        if (code == null) return null
        for (CategoryObjectType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static CategoryObjectType fromName(final String name) {
        if (name == null) return null
        for (CategoryObjectType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('GenericObject'.equalsIgnoreCase(name)) return Generic
        if ('TypingContext'.equalsIgnoreCase(name)) return Context
        if ('PropositionAsType'.equalsIgnoreCase(name)) return Proposition
        if ('UniverseOfTypes'.equalsIgnoreCase(name)) return Universe
        if ('AlgebraicStructure'.equalsIgnoreCase(name)) return AlgebraicStruct
        if ('AbelianGroup'.equalsIgnoreCase(name)) return AbGroup
        if ('SpecificationOrObservablePredicate'.equalsIgnoreCase(name)) return Specification
        if ('OperationalStateSpecification'.equalsIgnoreCase(name)) return OperationalState
        null
    }
}
