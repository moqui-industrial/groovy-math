/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.ortools

import org.junit.jupiter.api.Test
import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathModelDataPurpose
import groovy.math.dsl.MathModelSolvingMethod
import groovy.math.dsl.MathModelType
import groovy.math.dsl.MathMeta
import groovy.math.entity.EntityDefinition
import groovy.math.entity.FieldDefinition
import groovy.math.entity.ModelDefinition

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

    private static MathMeta productionPlan() {
        MathDsl.math(modelDefinition()) {
            MathModelDef('LinearProductionPlanning', modelTypeEnum: MathModelType.LinearProgram)
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
                purposeEnum: MathModelDataPurpose.DecisionVariables, vectorId: 'ProductionVariables')
            MathModelData('costs', mathModelId: 'ProductionPlan',
                purposeEnum: MathModelDataPurpose.CostVector, vectorId: 'UnitMargin')
            MathModelData('constraints', mathModelId: 'ProductionPlan',
                purposeEnum: MathModelDataPurpose.ConstraintMatrix, matrixId: 'MachineCapacityCoefficients')
            MathModelData('rhs', mathModelId: 'ProductionPlan',
                purposeEnum: MathModelDataPurpose.RightHandSide, vectorId: 'MachineCapacity')
            MathModelData('bounds', mathModelId: 'ProductionPlan',
                purposeEnum: MathModelDataPurpose.VariableBounds, matrixId: 'ProductionBounds')
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
