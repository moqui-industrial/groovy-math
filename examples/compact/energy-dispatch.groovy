ParameterDef('QuadraticObjectiveSense',
    parameterType: TextShort,
    purpose: ParameterPurpose.MathModel,
    parameterCode: 'objectiveSense',
    parameterName: 'Quadratic optimization objective sense')

MathModelDef('QuadraticEnergyDispatch',
    modelType: QuadraticProgram,
    usageContext: Optimisation,
    modelName: 'Bounded quadratic energy dispatch',
    description: 'Allocate two energy sources by minimizing convex operating cost') {

    pipeline('DispatchSolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'BqpipSolve',
        solvingMethod: InteriorPoint)

    MathModel('EnergyDispatch',
        modelAlias: 'energy_dispatch',
        source: Manual,
        description: 'Bounded convex QP solved by PETSc/TAO BQPIP',
        statusId: 'MathModelDraft') {

        parameters('EnergyDispatch.ObjectiveSense',
            parameterDefId: 'QuadraticObjectiveSense',
            parameterAlias: 'objectiveSense',
            symbolicValue: OptimizationObjectiveSense.Minimize)

        data('EnergySourceVariablesData',
            dataType: MathModelDataType.Vector,
            purpose: DecisionVariables,
            vectorId: 'EnergySourceVariables', sequenceNum: 0) {
            Vector('EnergySourceVariables', name: 'Energy source outputs', dimension: 2,
                componentArray: '["GridPower","StoredEnergy"]')
        }

        data('DispatchHessianData',
            dataType: MathModelDataType.Matrix,
            purpose: MathModelDataPurpose.Hessian,
            matrixId: 'DispatchHessian', sequenceNum: 1) {
            Matrix('DispatchHessian', matrixType: MatrixType.Symmetric,
                domainSpace: R2, codomainSpace: R2,
                name: 'Quadratic operating cost', rows: 2, cols: 2,
                componentArray: '[[2,0],[0,4]]')
        }

        data('DispatchLinearCostData',
            dataType: MathModelDataType.Vector,
            purpose: CostVector,
            vectorId: 'DispatchLinearCost', sequenceNum: 2) {
            Vector('DispatchLinearCost', name: 'Linear operating cost', dimension: 2,
                componentArray: '[-8,-8]')
        }

        data('DispatchBoundsData',
            dataType: MathModelDataType.Matrix,
            purpose: VariableBounds,
            matrixId: 'DispatchBounds', sequenceNum: 3) {
            Matrix('DispatchBounds', matrixType: MatrixType.Rectangular,
                domainSpace: R2, codomainSpace: R2,
                name: 'Lower and upper dispatch bounds', rows: 2, cols: 2,
                componentArray: '[[0,0],[5,3]]')
        }

        data('DispatchInitialConditionData',
            dataType: MathModelDataType.Vector,
            purpose: MathModelDataPurpose.InitialCondition,
            vectorId: 'DispatchInitialCondition', sequenceNum: 4) {
            Vector('DispatchInitialCondition', name: 'Initial dispatch', dimension: 2,
                componentArray: '[1,1]')
        }
    }
}
