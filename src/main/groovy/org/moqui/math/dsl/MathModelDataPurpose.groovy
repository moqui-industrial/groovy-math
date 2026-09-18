/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelDataPurpose
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelDataPurpose implements DslEnumValue {
    InitialCondition('MmdpInitialCondition', '', 'Initial Condition', ''),
    BoundaryCondition('MmdpBoundaryCondition', '', 'Boundary Condition', ''),
    BcDirichlet('MmdpBcDirichlet', '', 'Dirichlet Boundary Condition', 'MmdpBoundaryCondition'),
    BcNeumann('MmdpBcNeumann', '', 'Neumann Boundary Condition', 'MmdpBoundaryCondition'),
    BcRobin('MmdpBcRobin', '', 'Robin/Mixed Boundary', 'MmdpBoundaryCondition'),
    ErrorMetric('MmdpErrorMetric', '', 'Error Metric', ''),
    StdDev('MmdpStdDev', '', 'Standard Deviation', 'MmdpErrorMetric'),
    Confidence('MmdpConfidence', '', 'Confidence Interval', 'MmdpErrorMetric'),
    Absolute('MmdpAbsolute', '', 'Absolute Error', 'MmdpErrorMetric'),
    Constraint('MmdpConstraint', '', 'Constraint', ''),
    Equality('MmdpEquality', '', 'Equality Constraint', 'MmdpConstraint'),
    Inequality('MmdpInequality', '', 'Inequality Constraint', 'MmdpConstraint'),
    Symbolic('MmdpSymbolic', '', 'Symbolic Rule', 'MmdpConstraint'),
    ModelInputs('MmdpModelInputs', '', 'Model Inputs', ''),
    ModelOutputs('MmdpModelOutputs', '', 'Model Outputs', ''),
    ModelCoefficients('MmdpModelCoefficients', '', 'Model Coefficients', ''),
    ModelParams('MmdpModelParams', '', 'Model Parameters', ''),
    IntermediateState('MmdpIntermediateState', '', 'Intermediate State', ''),
    SnapshotState('MmdpSnapshotState', '', 'Snapshot State', ''),
    LastSuccessfulState('MmdpLastSuccessfulState', '', 'LastSuccessful State', ''),
    CheckpointState('MmdpCheckpointState', '', 'Checkpoint State', ''),
    TrainingData('MmdpTrainingData', '', 'Training Data', ''),
    ValidationData('MmdpValidationData', '', 'Validation Data', ''),
    CoreTensor('MmdpCoreTensor', '', 'Core Tensor (HOSVD/Tucker)', ''),
    FactorMatrix('MmdpFactorMatrix', '', 'Factor Matrix (mode-k)', ''),
    Derivative('MmdpDerivative', '', 'Derivative', ''),
    Gradient('MmdpGradient', '', 'Gradient', 'MmdpDerivative'),
    Hessian('MmdpHessian', '', 'Hessian', 'MmdpDerivative'),
    Jacobian('MmdpJacobian', '', 'Jacobian', 'MmdpDerivative'),
    Curvature('MmdpCurvature', '', 'Curvature', 'MmdpDerivative'),
    Torsion('MmdpTorsion', '', 'Torsion', 'MmdpDerivative'),
    DecisionVariables('MmdpDecisionVars', '', 'Decision Variables', ''),
    Objective('MmdpObjective', '', 'Objective Function', ''),
    CostVector('MmdpCostVector', '', 'Cost / Objective Coefficients (c)', ''),
    ConstraintMatrix('MmdpConstraintMatrix', '', 'Constraint Matrix (A)', ''),
    RightHandSide('MmdpRhsVector', '', 'Right-Hand Side (b)', ''),
    VariableBounds('MmdpVarBounds', '', 'Variable Bounds', ''),
    DualValue('MmdpDualValue', '', 'Dual Value / Shadow Price (y)', ''),
    ReducedCost('MmdpReducedCost', '', 'Reduced Cost (rc)', ''),
    Sensitivity('MmdpSensitivity', '', 'Sensitivity Range', ''),
    VariableDomain('MmdpVariableDomain', '', 'Variable Domain (Continuous/Integer/Binary)', ''),
    ArrivalRate('MmdpArrivalRate', '', 'Arrival Rate (λ)', ''),
    ServiceRate('MmdpServiceRate', '', 'Service Rate (μ)', ''),
    ServiceTimeDist('MmdpServiceTimeDist', '', 'Service Time Distribution', ''),
    InterarrivalDist('MmdpInterarrivalDist', '', 'Interarrival Time Distribution', ''),
    QueueDiscipline('MmdpQueueDiscipline', '', 'Queue Discipline (FIFO/LIFO/Priority)', ''),
    TransitionMatrix('MmdpTransitionMatrix', '', 'State Transition Matrix (P)', ''),
    Reward('MmdpReward', '', 'Reward Function (R)', ''),
    DiscountFactor('MmdpDiscountFactor', '', 'Discount Factor (γ)', ''),
    Policy('MmdpPolicy', '', 'Policy (π)', ''),
    ValueFunction('MmdpValueFunction', '', 'State-Value Function (V)', ''),
    QFunction('MmdpQFunction', '', 'Action-Value Function (Q)', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelDataPurpose(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
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

    static MathModelDataPurpose fromId(final String id) {
        if (id == null) return null
        for (MathModelDataPurpose val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelDataPurpose fromCode(final String code) {
        if (code == null) return null
        for (MathModelDataPurpose val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
