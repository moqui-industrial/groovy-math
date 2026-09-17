ParameterDef('OptimizationObjectiveSense',
    parameterType: TextShort,
    purpose: ParameterPurpose.MathModel,
    parameterCode: 'objectiveSense',
    parameterName: 'Optimization objective sense')

MathModelDef('LinearProductionPlanning',
    modelType: LinearProgram,
    usageContext: Optimisation,
    modelName: 'Linear production planning',
    description: 'Maximize production margin under machine-capacity constraints') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'SimplexSolve',
        solvingMethod: Simplex)

    MathModel('ProductionPlan',
        modelAlias: 'production_plan',
        source: Manual,
        description: 'Choose Standard and Premium production quantities',
        statusId: 'MathModelDraft') {

        parameters('ProductionPlan.ObjectiveSense',
            parameterDefId: 'OptimizationObjectiveSense',
            parameterAlias: 'objectiveSense',
            symbolicValue: OptimizationObjectiveSense.Maximize)

        data('ProductionVariablesData',
            dataType: MathModelDataType.Vector,
            purpose: DecisionVariables,
            vectorId: 'ProductionVariables', sequenceNum: 0) {
            Vector('ProductionVariables', name: 'Production quantities', dimension: 2,
                componentArray: '["Standard","Premium"]')
        }

        data('UnitMarginData',
            dataType: MathModelDataType.Vector,
            purpose: CostVector,
            vectorId: 'UnitMargin', sequenceNum: 1) {
            Vector('UnitMargin', name: 'Unit contribution margin', dimension: 2,
                componentArray: '[40,30]')
        }

        data('MachineCapacityCoefficientsData',
            dataType: MathModelDataType.Matrix,
            purpose: ConstraintMatrix,
            matrixId: 'MachineCapacityCoefficients', sequenceNum: 2) {
            Matrix('MachineCapacityCoefficients', matrixType: MatrixType.Rectangular,
                domainSpace: R2, codomainSpace: R2,
                name: 'Machine capacity coefficients', rows: 2, cols: 2,
                componentArray: '[[2,1],[1,2]]')
        }

        data('MachineCapacityData',
            dataType: MathModelDataType.Vector,
            purpose: RightHandSide,
            vectorId: 'MachineCapacity', sequenceNum: 3) {
            Vector('MachineCapacity', name: 'Available machine capacity', dimension: 2,
                componentArray: '[100,80]')
        }

        data('ProductionBoundsData',
            dataType: MathModelDataType.Matrix,
            purpose: VariableBounds,
            matrixId: 'ProductionBounds', sequenceNum: 4) {
            Matrix('ProductionBounds', matrixType: MatrixType.Rectangular,
                domainSpace: R2, codomainSpace: R2,
                name: 'Lower and upper production bounds', rows: 2, cols: 2,
                componentArray: '[[0,0],[40,50]]')
        }
    }
}
