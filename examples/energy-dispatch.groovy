/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

ParameterDef('QuadraticObjectiveSense',
    parameterTypeEnumId: 'PtTextShort',
    purposeEnumId: 'PpMathModel',
    parameterCode: 'objectiveSense',
    parameterName: 'Quadratic optimization objective sense')

MathModelDef('QuadraticEnergyDispatch',
    modelTypeEnum: MathModelType.QuadraticProgram,
    usageContextEnum: MathModelUsageContext.Optimisation,
    modelName: 'Bounded quadratic energy dispatch',
    description: 'Allocate two energy sources by minimizing convex operating cost') {

    MathModel('EnergyDispatch',
        modelAlias: 'energy_dispatch',
        solvingMethodEnum: MathModelSolvingMethod.InteriorPoint,
        sourceEnum: MathModelSource.Manual,
        description: 'Bounded convex QP solved by PETSc/TAO BQPIP',
        statusId: 'MathModelDraft') {

        parameters('EnergyDispatch.ObjectiveSense',
            parameterDefId: 'QuadraticObjectiveSense',
            parameterAlias: 'objectiveSense',
            symbolicValue: OptimizationObjectiveSense.Minimize)

        data('EnergySourceVariablesData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.DecisionVariables,
            vectorId: 'EnergySourceVariables', sequenceNum: 0) {
            Vector('EnergySourceVariables', name: 'Energy source outputs', dimension: 2,
                componentArray: '["GridPower","StoredEnergy"]')
        }

        data('DispatchHessianData',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.Hessian,
            matrixId: 'DispatchHessian', sequenceNum: 1) {
            Matrix('DispatchHessian', matrixTypeEnum: MatrixType.Symmetric,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'Quadratic operating cost', rows: 2, cols: 2,
                componentArray: '[[2,0],[0,4]]')
        }

        data('DispatchLinearCostData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.CostVector,
            vectorId: 'DispatchLinearCost', sequenceNum: 2) {
            Vector('DispatchLinearCost', name: 'Linear operating cost', dimension: 2,
                componentArray: '[-8,-8]')
        }

        data('DispatchBoundsData',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.VariableBounds,
            matrixId: 'DispatchBounds', sequenceNum: 3) {
            Matrix('DispatchBounds', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'Lower and upper dispatch bounds', rows: 2, cols: 2,
                componentArray: '[[0,0],[5,3]]')
        }

        data('DispatchInitialConditionData',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.InitialCondition,
            vectorId: 'DispatchInitialCondition', sequenceNum: 4) {
            Vector('DispatchInitialCondition', name: 'Initial dispatch', dimension: 2,
                componentArray: '[1,1]')
        }
    }
}
