/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

ParameterDef('QuadraticObjectiveSense', type: TextShort, purpose: MathModel,
    code: 'objectiveSense', name: 'Quadratic optimization objective sense')

MathModelDef('QuadraticEnergyDispatch', type: QuadraticProgram, usage: Optimisation,
    modelName: 'Bounded quadratic energy dispatch',
    description: 'Allocate two energy sources by minimizing convex operating cost') {

    pipeline('DispatchSolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'BqpipSolve', method: InteriorPoint)

    MathModel('EnergyDispatch', alias: 'energy_dispatch', source: Manual,
        description: 'Bounded convex QP solved by PETSc/TAO BQPIP', status: Draft) {

        parameters {
            objectiveSense = Minimize
        }

        vector('EnergySourceVariables', ['GridPower', 'StoredEnergy'], purpose: DecisionVariables, name: 'Energy source outputs')
        matrix('DispatchHessian', [[2, 0], [0, 4]], purpose: Hessian, matrixType: Symmetric, name: 'Quadratic operating cost')
        vector('DispatchLinearCost', [-8, -8], purpose: CostVector, name: 'Linear operating cost')
        matrix('DispatchBounds', [[0, 0], [5, 3]], purpose: VariableBounds, type: Rectangular, name: 'Lower and upper dispatch bounds')
        vector('DispatchInitialCondition', [1, 1], purpose: InitialCondition, name: 'Initial dispatch')
    }
}
