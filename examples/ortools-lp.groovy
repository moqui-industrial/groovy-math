/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

// Source: Google OR-Tools - Solving an LP Problem (GLOP)
// URL: https://developers.google.com/optimization/lp/lp_example
// Framework Version: OR-Tools 9.10+ | License: Apache-2.0

MathModelDef('OrToolsLinearProgram', type: LinearProgram, usage: Optimisation,
    modelName: 'OR-Tools Linear Programming Example',
    description: 'Linear programming with GLOP solver based on Google OR-Tools guide') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'GlopSolve', method: Simplex)

    MathModel('LpModel', alias: 'ortools_lp', source: Manual,
        description: 'Maximize 3x + 4y under linear constraints', status: Draft) {

        x = variable(0, Double.POSITIVE_INFINITY)
        y = variable(0, Double.POSITIVE_INFINITY)

        maximize x * 3 + y * 4

        subjectTo('c0', x + y * 2).le(14)
        subjectTo('c1', x * 3 - y).ge(0)
        subjectTo('c2', x - y).le(2)
    }
}
