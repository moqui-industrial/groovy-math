/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('LinearProductionPlanning',
    modelTypeEnum: MathModelType.Lp,
    usageContextEnum: MathModelUsageContext.Optimisation,
    modelName: 'Linear production planning',
    description: 'Maximize production margin under machine-capacity constraints') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'SimplexSolve',
        solvingMethodEnum: MathModelSolvingMethod.Simplex)

    MathModel('ProductionPlan',
        modelAlias: 'production_plan',
        sourceEnum: MathModelSource.Manual,
        description: 'Choose Standard and Premium production quantities',
        statusId: 'MathModelDraft') {

        parameters('ProductionPlan.ObjectiveSense',
            parameterDefId: 'OptimizationObjectiveSense',
            parameterAlias: 'objectiveSense',
            symbolicValue: 'MAXIMIZE')

        data('ProductionPlan_D_Vars',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.DecisionVars,
            vectorId: 'ProductionPlan_Variables', sequenceNum: 0) {
            Vector('ProductionPlan_Variables', name: 'Decision Variables', dimension: 2,
                componentArray: '["Standard","Premium"]') {
                VectorComponent('ProductionPlan_Variables_0', dimensionIndex: 0,
                    symbolicValue: 'Standard', componentTypeEnum: VectorComponentType.Symbolic)
                VectorComponent('ProductionPlan_Variables_1', dimensionIndex: 1,
                    symbolicValue: 'Premium', componentTypeEnum: VectorComponentType.Symbolic)
            }
        }

        data('ProductionPlan_D_Bounds',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.VarBounds,
            matrixId: 'ProductionPlan_VariableBounds', sequenceNum: 1) {
            Matrix('ProductionPlan_VariableBounds', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: AlgebraicStructureType.EuclideanSpace, codomainSpaceEnum: AlgebraicStructureType.EuclideanSpace,
                name: 'Variable Bounds', rows: 2, cols: 2,
                componentArray: '[[0.0,0.0],[40.0,50.0]]')
        }

        data('ProductionPlan_D_Cost',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.CostVector,
            vectorId: 'ProductionPlan_CostVector', sequenceNum: 2) {
            Vector('ProductionPlan_CostVector', name: 'Cost Vector', dimension: 2,
                componentArray: '[40.0,30.0]') {
                VectorComponent('ProductionPlan_CostVector_0', dimensionIndex: 0,
                    realValue: 40.0, componentTypeEnum: VectorComponentType.Canonical)
                VectorComponent('ProductionPlan_CostVector_1', dimensionIndex: 1,
                    realValue: 30.0, componentTypeEnum: VectorComponentType.Canonical)
            }
        }

        data('ProductionPlan_D_ConsMat',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.ConstraintMatrix,
            matrixId: 'ProductionPlan_ConstraintMatrix', sequenceNum: 3) {
            Matrix('ProductionPlan_ConstraintMatrix', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: AlgebraicStructureType.EuclideanSpace, codomainSpaceEnum: AlgebraicStructureType.EuclideanSpace,
                name: 'Constraint Matrix', rows: 2, cols: 2,
                componentArray: '[[2.0,1.0],[1.0,2.0]]')
        }

        data('ProductionPlan_D_Rhs',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.RhsVector,
            vectorId: 'ProductionPlan_RightHandSide', sequenceNum: 4) {
            Vector('ProductionPlan_RightHandSide', name: 'Right Hand Side', dimension: 2,
                componentArray: '[100.0,80.0]') {
                VectorComponent('ProductionPlan_RightHandSide_0', dimensionIndex: 0,
                    realValue: 100.0, componentTypeEnum: VectorComponentType.Canonical)
                VectorComponent('ProductionPlan_RightHandSide_1', dimensionIndex: 1,
                    realValue: 80.0, componentTypeEnum: VectorComponentType.Canonical)
            }
        }

        data('ProductionPlan_D_Sense',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.Constraint,
            vectorId: 'ProductionPlan_ConstraintSense', sequenceNum: 5) {
            Vector('ProductionPlan_ConstraintSense', name: 'Constraint Sense', dimension: 2,
                componentArray: '["TtLessEqual","TtLessEqual"]') {
                VectorComponent('ProductionPlan_ConstraintSense_0', dimensionIndex: 0,
                    symbolicValue: 'TtLessEqual', componentTypeEnum: VectorComponentType.Symbolic)
                VectorComponent('ProductionPlan_ConstraintSense_1', dimensionIndex: 1,
                    symbolicValue: 'TtLessEqual', componentTypeEnum: VectorComponentType.Symbolic)
            }
        }
    }
}

Transformation('ProductionPlan_Constraint_MachineA',
    transformationTypeEnum: TransformationType.LessEqual,
    name: 'MachineA')

Transformation('ProductionPlan_Constraint_MachineB',
    transformationTypeEnum: TransformationType.LessEqual,
    name: 'MachineB')


