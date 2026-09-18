/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.moqui.math.entity.ModelValue
import org.moqui.math.ortools.OrTools
import org.moqui.math.ortools.OrToolsResult

import static org.junit.jupiter.api.Assertions.*

class DslOptimizationTest {

    @Test
    void algebraicFormMatchesMatrixForm() {
        String algebraicScript = '''
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
        '''

        String matrixScript = '''
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
        '''

        MathMeta algMeta = MathDsl.evaluate(algebraicScript)
        MathMeta matMeta = MathDsl.evaluate(matrixScript)

        OrToolsResult algResult = OrTools.solve(algMeta, 'ProductionPlan')
        OrToolsResult matResult = OrTools.solve(matMeta, 'ProductionPlan')

        assertEquals('OPTIMAL', algResult.status)
        assertEquals('OPTIMAL', matResult.status)
        assertEquals(matResult.objectiveValue, algResult.objectiveValue, 1e-7)
        assertEquals(matResult.variableValues.Standard, algResult.variableValues.Standard, 1e-7)
        assertEquals(matResult.variableValues.Premium, algResult.variableValues.Premium, 1e-7)

        // Verify metamodel representation
        ModelValue decVars = algMeta.entity('Vector').find { it.get('vectorId')?.toString()?.contains('Variables') }
        assertNotNull(decVars)
        assertEquals('["Standard","Premium"]', decVars.get('componentArray'))

        ModelValue costVec = algMeta.entity('Vector').find { it.get('vectorId')?.toString()?.contains('CostVector') }
        assertNotNull(costVec)
        assertEquals('[40.0,30.0]', costVec.get('componentArray'))

        ModelValue constMat = algMeta.entity('Matrix').find { it.get('matrixId')?.toString()?.contains('ConstraintMatrix') }
        assertNotNull(constMat)
        assertEquals('[[2.0,1.0],[1.0,2.0]]', constMat.get('componentArray'))
    }

    @Test
    void algebraicQuadraticFormCompilesHessianWithPetscTaoConvention() {
        String script = '''
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
        '''
        MathMeta meta = MathDsl.evaluate(script)

        ModelValue hessian = meta.entity('Matrix').find { it.get('matrixId')?.toString()?.contains('Hessian') }
        assertNotNull(hessian)
        // PETSc/TAO convention: 1/2 x^T H x. For x1^2 + 2 x2^2, H is [[2.0, 0.0], [0.0, 4.0]]
        assertEquals('[[2.0,0.0],[0.0,4.0]]', hessian.get('componentArray'))

        ModelValue linear = meta.entity('Vector').find { it.get('vectorId')?.toString()?.contains('CostVector') }
        assertNotNull(linear)
        assertEquals('[-8.0,-8.0]', linear.get('componentArray'))
    }
}
