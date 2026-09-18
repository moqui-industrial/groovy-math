/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.ortools

import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathModelDataPurpose
import org.moqui.math.dsl.MathModelSolvingMethod
import org.moqui.math.dsl.MathModelType
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.FieldDefinition
import org.moqui.math.entity.ModelDefinition

import static org.junit.jupiter.api.Assertions.assertThrows

class OrToolsProviderTest {
    @Test
    void compilesAndSolvesLinearProductionPlan() {
        MathMeta mathMeta = productionPlan()
        OrToolsProvider provider = new OrToolsProvider('ProductionPlan')

        OrToolsPlan plan = provider.compile(mathMeta)
        try {
            assert plan.solverId == 'GLOP'
            assert plan.objectiveSense == 'MAXIMIZE'
            assert plan.variableNames == ['Standard', 'Premium']
            assert plan.constraintCount == 2

            OrToolsResult result = provider.execute(plan, [:])
            assert result.success
            assert result.status == 'OPTIMAL'
            assert Math.abs(result.objectiveValue - 2200d) < 1e-7
            assert Math.abs(result.variableValues.Standard - 40d) < 1e-7
            assert Math.abs(result.variableValues.Premium - 20d) < 1e-7
        } finally {
            plan.close()
        }
        assert plan.closed
    }

    @Test
    void rejectsRuntimeOverridesInsteadOfIgnoringThem() {
        OrToolsProvider provider = new OrToolsProvider('ProductionPlan')
        OrToolsPlan plan = provider.compile(productionPlan())
        try {
            IllegalArgumentException failure = assertThrows(IllegalArgumentException) {
                provider.execute(plan, [MachineCapacity: [90, 70]])
            }
            assert failure.message.contains('does not accept runtime input overrides')
        } finally {
            plan.close()
        }
    }

    @Test
    void solvesWithGreaterEqualConstraint() {
        String script = '''
            MathModelDef('GeModelDef', type: LinearProgram, usage: Optimisation, modelName: 'GE Model') {
                MathModel('GeModel', status: Draft) {
                    x1 = variable(0, 100)
                    x2 = variable(0, 100)
                    minimize x1 + x2
                    subjectTo('C1', x1 + x2 * 2).ge(10)
                    subjectTo('C2', x1 * 2 + x2).ge(10)
                }
            }
        '''
        MathMeta meta = MathDsl.evaluate(script)
        OrToolsResult res = OrTools.solve(meta, 'GeModel')
        assert res.success
        assert res.status == 'OPTIMAL'
        assert Math.abs(res.objectiveValue - (20.0d / 3.0d)) < 1e-5
        assert Math.abs(res.variableValues.x1 - (10.0d / 3.0d)) < 1e-5
        assert Math.abs(res.variableValues.x2 - (10.0d / 3.0d)) < 1e-5
    }

    @Test
    void solvesWithEqualityConstraint() {
        String script = '''
            MathModelDef('EqModelDef', type: LinearProgram, usage: Optimisation, modelName: 'EQ Model') {
                MathModel('EqModel', status: Draft) {
                    x1 = variable(0, 4)
                    x2 = variable(0, 10)
                    maximize x1 * 3 + x2 * 2
                    subjectTo('C1', x1 + x2).eq(10)
                }
            }
        '''
        MathMeta meta = MathDsl.evaluate(script)
        OrToolsResult res = OrTools.solve(meta, 'EqModel')
        assert res.success
        assert res.status == 'OPTIMAL'
        assert Math.abs(res.objectiveValue - 24.0d) < 1e-5
        assert Math.abs(res.variableValues.x1 - 4.0d) < 1e-5
        assert Math.abs(res.variableValues.x2 - 6.0d) < 1e-5
    }

    @Test
    void solvesWithIntegerVariables() {
        String script = '''
            MathModelDef('IntModelDef', type: LinearProgram, usage: Optimisation, modelName: 'Int Model') {
                MathModel('IntModel', status: Draft) {
                    x1 = variable(0, 10, domain: 'Integer')
                    x2 = variable(0, 10, domain: 'Integer')
                    maximize x1 + x2 * 2
                    subjectTo('C1', x1 + x2).le(3.5)
                }
            }
        '''
        MathMeta meta = MathDsl.evaluate(script)
        OrToolsResult res = OrTools.solve(meta, 'IntModel')
        assert res.success
        assert res.status == 'OPTIMAL'
        assert Math.abs(res.objectiveValue - 6.0d) < 1e-5
        assert Math.abs(res.variableValues.x1 - 0.0d) < 1e-5
        assert Math.abs(res.variableValues.x2 - 3.0d) < 1e-5
    }

    private static MathMeta productionPlan() {
        MathDsl.math(modelDefinition()) {
            MathModelDef('LinearProductionPlanning', modelTypeEnum: MathModelType.Lp)
            MathModel('ProductionPlan', mathModelDefId: 'LinearProductionPlanning',
                solvingMethodEnum: MathModelSolvingMethod.Simplex)
            Parameter('ObjectiveSense', mathModelId: 'ProductionPlan',
                parameterAlias: 'objectiveSense', symbolicValue: 'MAXIMIZE')

            Vector('ProductionVariables', componentArray: '["Standard","Premium"]')
            Vector('UnitMargin', componentArray: '[40,30]')
            Matrix('MachineCapacityCoefficients', rows: 2, cols: 2,
                componentArray: '[[2,1],[1,2]]')
            Vector('MachineCapacity', componentArray: '[100,80]')
            Matrix('ProductionBounds', rows: 2, cols: 2,
                componentArray: '[[0,0],[40,50]]')

            MathModelData('variables', mathModelId: 'ProductionPlan',
                purposeEnum: MathModelDataPurpose.DecisionVars, vectorId: 'ProductionVariables')
            MathModelData('costs', mathModelId: 'ProductionPlan',
                purposeEnum: MathModelDataPurpose.CostVector, vectorId: 'UnitMargin')
            MathModelData('constraints', mathModelId: 'ProductionPlan',
                purposeEnum: MathModelDataPurpose.ConstraintMatrix, matrixId: 'MachineCapacityCoefficients')
            MathModelData('rhs', mathModelId: 'ProductionPlan',
                purposeEnum: MathModelDataPurpose.RhsVector, vectorId: 'MachineCapacity')
            MathModelData('bounds', mathModelId: 'ProductionPlan',
                purposeEnum: MathModelDataPurpose.VarBounds, matrixId: 'ProductionBounds')
        }
    }

    private static ModelDefinition modelDefinition() {
        ModelDefinition model = new ModelDefinition()
        model.addEntity(entity('MathModelDef', [
            mathModelDefId: true, modelTypeEnumId: false
        ]))
        model.addEntity(entity('MathModel', [
            mathModelId: true, mathModelDefId: false, solvingMethodEnumId: false
        ]))
        model.addEntity(entity('Parameter', [
            parameterId: true, mathModelId: false, parameterAlias: false, symbolicValue: false
        ]))
        model.addEntity(entity('Vector', [vectorId: true, componentArray: false]))
        model.addEntity(entity('Matrix', [
            matrixId: true, rows: false, cols: false, componentArray: false
        ]))
        model.addEntity(entity('MathModelData', [
            mathModelDataId: true, mathModelId: false, purposeEnumId: false,
            vectorId: false, matrixId: false
        ]))
        model
    }

    private static EntityDefinition entity(final String name, final Map<String, Boolean> fields) {
        EntityDefinition entity = new EntityDefinition('moqui.math', name)
        fields.each { String fieldName, Boolean primaryKey ->
            entity.addField(new FieldDefinition(fieldName, 'text', primaryKey, false, null))
        }
        entity
    }
}
