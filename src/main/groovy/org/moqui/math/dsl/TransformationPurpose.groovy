/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TransformationPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TransformationPurpose implements DslEnumValue {
    CoordTransform('TpCoordTransform', '', 'Coordinate System Transformation', ''),
    GeometricModeling('TpGeometricModeling', '', 'Geometrical Transformation (shearing, rotation, reflection, scaling, etc.)', ''),
    SymbolicSimplification('TpSymbolicSimplification', '', 'Symbolic or Algebraic Simplification', ''),
    FrameChange('TpFrameChange', '', 'Frame Change', ''),
    DataCompression('TpDataCompression', '', 'Data Compression', ''),
    EigenAnalysis('TpEigenAnalysis', '', 'Spectral Analysis or Stability', ''),
    Equation('TpEquation', '', 'Equation', ''),
    Constraint('TpConstraint', '', 'Constraint', ''),
    Predicate('TpPredicate', '', 'Predicate', ''),
    TypeJudgement('TpTypeJudgement', '', 'Type Judgement', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TransformationPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static TransformationPurpose fromId(final String id) {
        if (id == null) return null
        for (TransformationPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TransformationPurpose fromCode(final String code) {
        if (code == null) return null
        for (TransformationPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
