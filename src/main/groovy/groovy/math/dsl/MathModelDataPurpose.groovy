/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.dsl

enum MathModelDataPurpose implements DslEnumValue {
    DecisionVariables('MmdpDecisionVars'),
    CostVector('MmdpCostVector'),
    ConstraintMatrix('MmdpConstraintMatrix'),
    RightHandSide('MmdpRhsVector'),
    VariableBounds('MmdpVarBounds'),
    Hessian('MmdpHessian'),
    InitialCondition('MmdpInitialCondition')

    final String id

    MathModelDataPurpose(final String id) {
        this.id = id
    }
}
