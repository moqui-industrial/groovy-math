/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelInterpolation
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelInterpolation implements DslEnumValue {
    Linear('MmiLinear', '', 'Linear Interpolation', ''),
    Spline('MmiSpline', '', 'Spline-based Interpolation', ''),
    Lagrange('MmiLagrange', '', 'Lagrange Shape Function', ''),
    Hermite('MmiHermite', '', 'Hermite Shape Function', ''),
    Serendipity('MmiSerendipity', '', 'Serendipity Shape Function', ''),
    Nurbs('MmiNurbs', '', 'NURBS / Isogeometric Shape Function', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelInterpolation(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelInterpolation fromId(final String id) {
        if (id == null) return null
        for (MathModelInterpolation val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelInterpolation fromCode(final String code) {
        if (code == null) return null
        for (MathModelInterpolation val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
