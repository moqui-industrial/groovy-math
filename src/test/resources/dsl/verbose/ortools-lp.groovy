/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('OrToolsLinearProgram',
    modelTypeEnum: MathModelType.Lp,
    usageContextEnum: MathModelUsageContext.Optimisation,
    modelName: 'OR-Tools Linear Programming Example',
    description: 'Linear programming with GLOP solver based on Google OR-Tools guide') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'GlopSolve',
        solvingMethodEnum: MathModelSolvingMethod.Simplex)

    MathModel('LpModel',
        modelAlias: 'ortools_lp',
        sourceEnum: MathModelSource.Manual,
        description: 'Maximize 3x + 4y under linear constraints',
        statusId: 'MathModelDraft') {

        parameters('LpModel.ObjectiveSense',
            parameterDefId: 'OptimizationObjectiveSense',
            parameterAlias: 'objectiveSense',
            symbolicValue: 'MAXIMIZE')

        data('LpModel_D_Vars',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.DecisionVars,
            vectorId: 'LpModel_Variables', sequenceNum: 0) {
            Vector('LpModel_Variables', name: 'Decision Variables', dimension: 2,
                componentArray: '["x","y"]') {
                VectorComponent('LpModel_Variables_0', dimensionIndex: 0,
                    symbolicValue: 'x', componentTypeEnum: VectorComponentType.Symbolic)
                VectorComponent('LpModel_Variables_1', dimensionIndex: 1,
                    symbolicValue: 'y', componentTypeEnum: VectorComponentType.Symbolic)
            }
        }

        data('LpModel_D_Bounds',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.VarBounds,
            matrixId: 'LpModel_VariableBounds', sequenceNum: 1) {
            Matrix('LpModel_VariableBounds', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: AlgebraicStructureType.EuclideanSpace, codomainSpaceEnum: AlgebraicStructureType.EuclideanSpace,
                name: 'Variable Bounds', rows: 2, cols: 2,
                componentArray: '[[0.0,0.0],[Infinity,Infinity]]')
        }

        data('LpModel_D_Cost',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.CostVector,
            vectorId: 'LpModel_CostVector', sequenceNum: 2) {
            Vector('LpModel_CostVector', name: 'Cost Vector', dimension: 2,
                componentArray: '[3.0,4.0]') {
                VectorComponent('LpModel_CostVector_0', dimensionIndex: 0,
                    realValue: 3.0, componentTypeEnum: VectorComponentType.Canonical)
                VectorComponent('LpModel_CostVector_1', dimensionIndex: 1,
                    realValue: 4.0, componentTypeEnum: VectorComponentType.Canonical)
            }
        }

        data('LpModel_D_ConsMat',
            dataTypeEnum: MathModelDataType.Matrix,
            purposeEnum: MathModelDataPurpose.ConstraintMatrix,
            matrixId: 'LpModel_ConstraintMatrix', sequenceNum: 3) {
            Matrix('LpModel_ConstraintMatrix', matrixTypeEnum: MatrixType.Rectangular,
                domainSpaceEnum: AlgebraicStructureType.EuclideanSpace, codomainSpaceEnum: AlgebraicStructureType.EuclideanSpace,
                name: 'Constraint Matrix', rows: 3, cols: 2,
                componentArray: '[[1.0,2.0],[3.0,-1.0],[1.0,-1.0]]')
        }

        data('LpModel_D_Rhs',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.RhsVector,
            vectorId: 'LpModel_RightHandSide', sequenceNum: 4) {
            Vector('LpModel_RightHandSide', name: 'Right Hand Side', dimension: 3,
                componentArray: '[14.0,0.0,2.0]') {
                VectorComponent('LpModel_RightHandSide_0', dimensionIndex: 0,
                    realValue: 14.0, componentTypeEnum: VectorComponentType.Canonical)
                VectorComponent('LpModel_RightHandSide_1', dimensionIndex: 1,
                    realValue: 0.0, componentTypeEnum: VectorComponentType.Canonical)
                VectorComponent('LpModel_RightHandSide_2', dimensionIndex: 2,
                    realValue: 2.0, componentTypeEnum: VectorComponentType.Canonical)
            }
        }

        data('LpModel_D_Sense',
            dataTypeEnum: MathModelDataType.Vector,
            purposeEnum: MathModelDataPurpose.Constraint,
            vectorId: 'LpModel_ConstraintSense', sequenceNum: 5) {
            Vector('LpModel_ConstraintSense', name: 'Constraint Sense', dimension: 3,
                componentArray: '["TtLessEqual","TtGreaterEqual","TtLessEqual"]') {
                VectorComponent('LpModel_ConstraintSense_0', dimensionIndex: 0,
                    symbolicValue: 'TtLessEqual', componentTypeEnum: VectorComponentType.Symbolic)
                VectorComponent('LpModel_ConstraintSense_1', dimensionIndex: 1,
                    symbolicValue: 'TtGreaterEqual', componentTypeEnum: VectorComponentType.Symbolic)
                VectorComponent('LpModel_ConstraintSense_2', dimensionIndex: 2,
                    symbolicValue: 'TtLessEqual', componentTypeEnum: VectorComponentType.Symbolic)
            }
        }
    }
}

Transformation('LpModel_Constraint_c0',
    transformationTypeEnum: TransformationType.LessEqual,
    name: 'c0')

Transformation('LpModel_Constraint_c1',
    transformationTypeEnum: TransformationType.GreaterEqual,
    name: 'c1')

Transformation('LpModel_Constraint_c2',
    transformationTypeEnum: TransformationType.LessEqual,
    name: 'c2')
