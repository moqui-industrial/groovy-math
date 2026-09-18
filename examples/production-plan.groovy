/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

ParameterDef('OptimizationObjectiveSense', type: TextShort, purpose: MathModel,
    code: 'objectiveSense', name: 'Optimization objective sense')

MathModelDef('LinearProductionPlanning', type: LinearProgram, usage: Optimisation,
    modelName: 'Linear production planning',
    description: 'Maximize production margin under machine-capacity constraints') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'SimplexSolve', method: Simplex)

    MathModel('ProductionPlan', alias: 'production_plan', source: Manual,
        description: 'Choose Standard and Premium production quantities', status: Draft) {

        parameters {
            objectiveSense = Maximize
        }

        vector('ProductionVariables', ['Standard', 'Premium'], purpose: DecisionVariables, name: 'Production quantities')
        vector('UnitMargin', [40, 30], purpose: CostVector, name: 'Unit contribution margin')
        matrix('MachineCapacityCoefficients', [[2, 1], [1, 2]], purpose: ConstraintMatrix, type: Rectangular, name: 'Machine capacity coefficients')
        vector('MachineCapacity', [100, 80], purpose: RightHandSide, name: 'Available machine capacity')
        matrix('ProductionBounds', [[0, 0], [40, 50]], purpose: VariableBounds, type: Rectangular, name: 'Lower and upper production bounds')
    }
}
