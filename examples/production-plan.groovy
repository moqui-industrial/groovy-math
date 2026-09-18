/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('LinearProductionPlanning', type: LinearProgram, usage: Optimisation,
    modelName: 'Linear production planning',
    description: 'Maximize production margin under machine-capacity constraints') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'SimplexSolve', method: Simplex)

    MathModel('ProductionPlan', alias: 'production_plan', source: Manual,
        description: 'Choose Standard and Premium production quantities', status: Draft) {

        Standard = variable(0, 40)
        Premium  = variable(0, 50)
        maximize Standard * 40 + Premium * 30
        subjectTo('MachineA', Standard * 2 + Premium).le(100)
        subjectTo('MachineB', Standard + Premium * 2).le(80)
    }
}
