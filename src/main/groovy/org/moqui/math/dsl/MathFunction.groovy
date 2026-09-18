/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathFunction
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathFunction implements DslEnumValue {
    Sin('MfSin', '', 'Sine Function', ''),
    Cos('MfCos', '', 'Cosine Function', ''),
    Exp('MfExp', '', 'Exponential Function', ''),
    Log('MfLog', '', 'Natural Logarithm', ''),
    Poly('MfPoly', '', 'Polynomial', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathFunction(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathFunction fromId(final String id) {
        if (id == null) return null
        for (MathFunction val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathFunction fromCode(final String code) {
        if (code == null) return null
        for (MathFunction val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static MathFunction fromName(final String name) {
        if (name == null) return null
        for (MathFunction val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('SineFunction'.equalsIgnoreCase(name)) return Sin
        if ('CosineFunction'.equalsIgnoreCase(name)) return Cos
        if ('ExponentialFunction'.equalsIgnoreCase(name)) return Exp
        if ('NaturalLogarithm'.equalsIgnoreCase(name)) return Log
        if ('Polynomial'.equalsIgnoreCase(name)) return Poly
        null
    }
}
