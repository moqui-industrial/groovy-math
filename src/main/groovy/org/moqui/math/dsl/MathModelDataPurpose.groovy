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
    DecisionVars('MmdpDecisionVars', '', 'Decision Variables', ''),
    Objective('MmdpObjective', '', 'Objective Function', ''),
    CostVector('MmdpCostVector', '', 'Cost / Objective Coefficients (c)', ''),
    ConstraintMatrix('MmdpConstraintMatrix', '', 'Constraint Matrix (A)', ''),
    RhsVector('MmdpRhsVector', '', 'Right-Hand Side (b)', ''),
    VarBounds('MmdpVarBounds', '', 'Variable Bounds', ''),
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

    static MathModelDataPurpose fromName(final String name) {
        if (name == null) return null
        for (MathModelDataPurpose val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('DirichletBoundaryCondition'.equalsIgnoreCase(name)) return BcDirichlet
        if ('NeumannBoundaryCondition'.equalsIgnoreCase(name)) return BcNeumann
        if ('RobinMixedBoundary'.equalsIgnoreCase(name)) return BcRobin
        if ('StandardDeviation'.equalsIgnoreCase(name)) return StdDev
        if ('ConfidenceInterval'.equalsIgnoreCase(name)) return Confidence
        if ('AbsoluteError'.equalsIgnoreCase(name)) return Absolute
        if ('EqualityConstraint'.equalsIgnoreCase(name)) return Equality
        if ('InequalityConstraint'.equalsIgnoreCase(name)) return Inequality
        if ('SymbolicRule'.equalsIgnoreCase(name)) return Symbolic
        if ('ModelParameters'.equalsIgnoreCase(name)) return ModelParams
        if ('LastsuccessfulState'.equalsIgnoreCase(name)) return LastSuccessfulState
        if ('DecisionVariables'.equalsIgnoreCase(name)) return DecisionVars
        if ('ObjectiveFunction'.equalsIgnoreCase(name)) return Objective
        if ('CostObjectiveCoefficients'.equalsIgnoreCase(name)) return CostVector
        if ('RightHandSide'.equalsIgnoreCase(name)) return RhsVector
        if ('VariableBounds'.equalsIgnoreCase(name)) return VarBounds
        if ('DualValueShadowPrice'.equalsIgnoreCase(name)) return DualValue
        if ('SensitivityRange'.equalsIgnoreCase(name)) return Sensitivity
        if ('ServiceTimeDistribution'.equalsIgnoreCase(name)) return ServiceTimeDist
        if ('InterarrivalTimeDistribution'.equalsIgnoreCase(name)) return InterarrivalDist
        if ('StateTransitionMatrix'.equalsIgnoreCase(name)) return TransitionMatrix
        if ('RewardFunction'.equalsIgnoreCase(name)) return Reward
        if ('StateValueFunction'.equalsIgnoreCase(name)) return ValueFunction
        if ('ActionValueFunction'.equalsIgnoreCase(name)) return QFunction
        null
    }
}
