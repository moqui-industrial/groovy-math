/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('QuadraticEnergyDispatch',
    modelTypeEnum: MathModelType.Qp,
    usageContextEnum: MathModelUsageContext.Optimisation,
    modelName: 'Bounded quadratic energy dispatch',
    description: 'Allocate two energy sources by minimizing convex operating cost') {

    pipeline('DispatchSolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'BqpipSolve',
        solvingMethodEnum: MathModelSolvingMethod.InteriorPoint)

    MathModel('EnergyDispatch',
        modelAlias: 'energy_dispatch',
        sourceEnum: MathModelSource.Manual,
        description: 'Bounded convex QP solved by PETSc/TAO BQPIP',
        statusId: 'MathModelDraft') {

        parameters('EnergyDispatch.ObjectiveSense',
            parameterDefId: 'OptimizationObjectiveSense',
            parameterAlias: 'objectiveSense',
            symbolicValue: 'MINIMIZE')

        data('EnergyDispatch_D_Vars',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.DecisionVars,
            vectorId: 'EnergyDispatch_Variables', sequenceNum: 0) {
            Vector('EnergyDispatch_Variables', name: 'Decision Variables', dimension: 2,
                componentArray: '["GridPower","StoredEnergy"]') {
                VectorComponent('EnergyDispatch_Variables_0', dimensionIndex: 0,
                    symbolicValue: 'GridPower', componentTypeEnum: VectorComponentType.Symbolic)
                VectorComponent('EnergyDispatch_Variables_1', dimensionIndex: 1,
                    symbolicValue: 'StoredEnergy', componentTypeEnum: VectorComponentType.Symbolic)
            }
        }

        data('EnergyDispatch_D_Bounds',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.VarBounds,
            matrixId: 'EnergyDispatch_VariableBounds', sequenceNum: 1) {
            Matrix('EnergyDispatch_VariableBounds', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: AlgebraicStructureType.EuclideanSpace, codomainSpaceEnum: AlgebraicStructureType.EuclideanSpace,
                name: 'Variable Bounds', rows: 2, cols: 2,
                componentArray: '[[0.0,0.0],[5.0,3.0]]')
        }

        data('EnergyDispatch_D_Init',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.InitialCondition,
            vectorId: 'EnergyDispatch_InitialCondition', sequenceNum: 2) {
            Vector('EnergyDispatch_InitialCondition', name: 'Initial Condition', dimension: 2,
                componentArray: '[1.0,1.0]') {
                VectorComponent('EnergyDispatch_InitialCondition_0', dimensionIndex: 0,
                    realValue: 1.0, componentTypeEnum: VectorComponentType.Canonical)
                VectorComponent('EnergyDispatch_InitialCondition_1', dimensionIndex: 1,
                    realValue: 1.0, componentTypeEnum: VectorComponentType.Canonical)
            }
        }

        data('EnergyDispatch_D_Cost',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.CostVector,
            vectorId: 'EnergyDispatch_CostVector', sequenceNum: 3) {
            Vector('EnergyDispatch_CostVector', name: 'Cost Vector', dimension: 2,
                componentArray: '[-8.0,-8.0]') {
                VectorComponent('EnergyDispatch_CostVector_0', dimensionIndex: 0,
                    realValue: -8.0, componentTypeEnum: VectorComponentType.Canonical)
                VectorComponent('EnergyDispatch_CostVector_1', dimensionIndex: 1,
                    realValue: -8.0, componentTypeEnum: VectorComponentType.Canonical)
            }
        }

        data('EnergyDispatch_D_Hessian',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.Hessian,
            matrixId: 'EnergyDispatch_Hessian', sequenceNum: 4) {
            Matrix('EnergyDispatch_Hessian', matrixTypeEnum: MatrixType.Symmetric,
                domainSpaceEnum: AlgebraicStructureType.EuclideanSpace, codomainSpaceEnum: AlgebraicStructureType.EuclideanSpace,
                name: 'Hessian Matrix', rows: 2, cols: 2,
                componentArray: '[[2.0,0.0],[0.0,4.0]]')
        }
    }
}

