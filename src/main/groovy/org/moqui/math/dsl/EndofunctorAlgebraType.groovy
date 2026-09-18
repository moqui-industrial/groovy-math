/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: EndofunctorAlgebraType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum EndofunctorAlgebraType implements DslEnumValue {
    Algebra('EatAlgebra', '', 'Algebra F(A) -> A', ''),
    Coalgebra('EatCoalgebra', '', 'Coalgebra A -> F(A)', ''),
    EilenbergMoore('EatEilenbergMoore', '', 'Eilenberg-Moore algebra', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    EndofunctorAlgebraType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static EndofunctorAlgebraType fromId(final String id) {
        if (id == null) return null
        for (EndofunctorAlgebraType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static EndofunctorAlgebraType fromCode(final String code) {
        if (code == null) return null
        for (EndofunctorAlgebraType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static EndofunctorAlgebraType fromName(final String name) {
        if (name == null) return null
        for (EndofunctorAlgebraType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('AlgebraFA'.equalsIgnoreCase(name)) return Algebra
        if ('CoalgebraAF'.equalsIgnoreCase(name)) return Coalgebra
        if ('EilenbergMooreAlgebra'.equalsIgnoreCase(name)) return EilenbergMoore
        null
    }
}
