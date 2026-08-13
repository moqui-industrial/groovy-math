/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

ParameterDef('OptimizationObjectiveSense',
    parameterTypeEnumId: 'PtTextShort',
    purposeEnumId: 'PpMathModel',
    parameterCode: 'objectiveSense',
    parameterName: 'Optimization objective sense')

MathModelDef('LinearProductionPlanning',
    modelTypeEnum: MathModelType.LinearProgram,
    usageContextEnum: MathModelUsageContext.Optimisation,
    modelName: 'Linear production planning',
    description: 'Maximize production margin under machine-capacity constraints') {

    MathModel('ProductionPlan',
        modelAlias: 'production_plan',
        solvingMethodEnum: MathModelSolvingMethod.Simplex,
        sourceEnum: MathModelSource.Manual,
        description: 'Choose Standard and Premium production quantities',
        statusId: 'MathModelDraft') {

        parameters('ProductionPlan.ObjectiveSense',
            parameterDefId: 'OptimizationObjectiveSense',
            parameterAlias: 'objectiveSense',
            symbolicValue: OptimizationObjectiveSense.Maximize)

        data('ProductionVariablesData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.DecisionVariables,
            vectorId: 'ProductionVariables', sequenceNum: 0) {
            Vector('ProductionVariables', name: 'Production quantities', dimension: 2,
                componentArray: '["Standard","Premium"]')
        }

        data('UnitMarginData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.CostVector,
            vectorId: 'UnitMargin', sequenceNum: 1) {
            Vector('UnitMargin', name: 'Unit contribution margin', dimension: 2,
                componentArray: '[40,30]')
        }

        data('MachineCapacityCoefficientsData',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.ConstraintMatrix,
            matrixId: 'MachineCapacityCoefficients', sequenceNum: 2) {
            Matrix('MachineCapacityCoefficients', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'Machine capacity coefficients', rows: 2, cols: 2,
                componentArray: '[[2,1],[1,2]]')
        }

        data('MachineCapacityData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.RightHandSide,
            vectorId: 'MachineCapacity', sequenceNum: 3) {
            Vector('MachineCapacity', name: 'Available machine capacity', dimension: 2,
                componentArray: '[100,80]')
        }

        data('ProductionBoundsData',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.VariableBounds,
            matrixId: 'ProductionBounds', sequenceNum: 4) {
            Matrix('ProductionBounds', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'Lower and upper production bounds', rows: 2, cols: 2,
                componentArray: '[[0,0],[40,50]]')
        }
    }
}
