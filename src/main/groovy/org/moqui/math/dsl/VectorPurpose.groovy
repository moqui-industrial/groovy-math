/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: VectorPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum VectorPurpose implements DslEnumValue {
    Base('VpBase', '', 'Base Vector', ''),
    Eigenvector('VpEigenvector', '', 'Eigenvector', ''),
    Normal('VpNormal', '', 'Normal Vector', ''),
    Gradient('VpGradient', '', 'Gradient Vector', ''),
    Tangent('VpTangent', '', 'Tangent Vector', ''),
    Cotangent('VpCotangent', '', 'Cotangent Vector', ''),
    VectorOfVectors('VpVectorOfVectors', '', 'Vector of Vectors', ''),
    Column('VpColumn', '', 'Column Vector', ''),
    Row('VpRow', '', 'Row Vector', ''),
    Position('VpPosition', '', 'Position Vector', ''),
    Displacement('VpDisplacement', '', 'Displacement Vector', ''),
    Velocity('VpVelocity', '', 'Velocity Vector', ''),
    Acceleration('VpAcceleration', '', 'Acceleration Vector', ''),
    Force('VpForce', '', 'Force Vector', ''),
    Polar('VpPolar', '', 'Polar Vector', ''),
    Axial('VpAxial', '', 'Axial Vector', ''),
    Random('VpRandom', '', 'Random Vector', ''),
    Knot('VpKnot', '', 'Knot Vector', ''),
    Basis('VpBasis', '', 'Basis Vector', ''),
    ObliqueBasis('VpObliqueBasis', '', 'Oblique Basis Vector', ''),
    OrthonormalBasis('VpOrthonormalBasis', '', 'Orthonormal Basis Vector', ''),
    NonOrthogonalBasis('VpNonOrthogonalBasis', '', 'Non-Orthogonal Basis Vector', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    VectorPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static VectorPurpose fromId(final String id) {
        if (id == null) return null
        for (VectorPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static VectorPurpose fromCode(final String code) {
        if (code == null) return null
        for (VectorPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
