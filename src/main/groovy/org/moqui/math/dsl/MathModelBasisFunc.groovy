/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelBasisFunc
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelBasisFunc implements DslEnumValue {
    Lagrange('MmbLagrange', '', 'Lagrange Polynomial', ''),
    Hermite('MmbHermite', '', 'Hermite Polynomial', ''),
    Serendipity('MmbSerendipity', '', 'Serendipity Family', ''),
    Nurbs('MmbNurbs', '', 'NURBS / IGA Basis', ''),
    Chebyshev('MmbChebyshev', '', 'Chebyshev Basis', ''),
    Legendre('MmbLegendre', '', 'Legendre Basis', ''),
    Fourier('MmbFourier', '', 'Fourier Basis', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelBasisFunc(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelBasisFunc fromId(final String id) {
        if (id == null) return null
        for (MathModelBasisFunc val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelBasisFunc fromCode(final String code) {
        if (code == null) return null
        for (MathModelBasisFunc val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
