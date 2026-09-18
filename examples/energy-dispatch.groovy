/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('QuadraticEnergyDispatch', type: QuadraticProgram, usage: Optimisation,
    modelName: 'Bounded quadratic energy dispatch',
    description: 'Allocate two energy sources by minimizing convex operating cost') {

    pipeline('DispatchSolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'BqpipSolve', method: InteriorPoint)

    MathModel('EnergyDispatch', alias: 'energy_dispatch', source: Manual,
        description: 'Bounded convex QP solved by PETSc/TAO BQPIP', status: Draft) {

        GridPower    = variable(0, 5, initial: 1.0)
        StoredEnergy = variable(0, 3, initial: 1.0)
        minimize GridPower ** 2 + StoredEnergy ** 2 * 2 - GridPower * 8 - StoredEnergy * 8
    }
}
