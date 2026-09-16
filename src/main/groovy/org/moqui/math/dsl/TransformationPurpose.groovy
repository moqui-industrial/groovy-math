/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

enum TransformationPurpose implements DslEnumValue {
    CoordTransform('TpCoordTransform'),
    GeometricModeling('TpGeometricModeling'),
    SymbolicSimplification('TpSymbolicSimplification'),
    FrameChange('TpFrameChange'),
    DataCompression('TpDataCompression'),
    EigenAnalysis('TpEigenAnalysis'),
    Equation('TpEquation'),
    Constraint('TpConstraint'),
    Predicate('TpPredicate'),
    TypeJudgement('TpTypeJudgement')

    final String id

    TransformationPurpose(final String id) {
        this.id = id
    }
}
