/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: FunctorType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum FunctorType implements DslEnumValue {
    Cov('FtCov', '', 'Covariant', ''),
    Contra('FtContra', '', 'Contravariant', ''),
    Faithful('FtFaithful', '', 'Faithful', ''),
    Full('FtFull', '', 'Full', ''),
    Embedding('FtEmbedding', '', 'Faithful and Full (Embedding)', ''),
    Equiv('FtEquiv', '', 'Equivalence (quasi-inverse)', ''),
    Forgetful('FtForgetful', '', 'Forgetful functor', ''),
    HomCov('FtHomCov', '', 'Hom(-,X) Covariant', ''),
    HomContra('FtHomContra', '', 'Hom(X,-) Contravariant', ''),
    Powerset('FtPowerset', '', 'Power-Set Functor', ''),
    TypeConstructor('FtTypeConstructor', '', 'Type Constructor Functor', ''),
    Execution('FtExecution', '', 'Execution / Interpretation Functor', 'FtCov'),
    Diagram('FtDiagram', '', 'Diagram Functor (Shape Graph to Target Category)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    FunctorType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static FunctorType fromId(final String id) {
        if (id == null) return null
        for (FunctorType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static FunctorType fromCode(final String code) {
        if (code == null) return null
        for (FunctorType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static FunctorType fromName(final String name) {
        if (name == null) return null
        for (FunctorType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('Covariant'.equalsIgnoreCase(name)) return Cov
        if ('Contravariant'.equalsIgnoreCase(name)) return Contra
        if ('FaithfulAndFull'.equalsIgnoreCase(name)) return Embedding
        if ('Equivalence'.equalsIgnoreCase(name)) return Equiv
        if ('ForgetfulFunctor'.equalsIgnoreCase(name)) return Forgetful
        if ('HomCovariant'.equalsIgnoreCase(name)) return HomCov
        if ('HomContravariant'.equalsIgnoreCase(name)) return HomContra
        if ('PowerSetFunctor'.equalsIgnoreCase(name)) return Powerset
        if ('TypeConstructorFunctor'.equalsIgnoreCase(name)) return TypeConstructor
        if ('ExecutionInterpretationFunctor'.equalsIgnoreCase(name)) return Execution
        if ('DiagramFunctor'.equalsIgnoreCase(name)) return Diagram
        null
    }
}
