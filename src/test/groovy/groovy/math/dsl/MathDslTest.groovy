/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package groovy.math.dsl

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import groovy.math.entity.EntityDefinition
import groovy.math.entity.FieldDefinition
import groovy.math.entity.ModelDefinition
import groovy.math.entity.ModelValue
import groovy.math.entity.RelationshipDefinition
import groovy.math.moqui.MoquiSchemaInspector

class MathDslTest {
    @TempDir
    File temporaryDirectory

    @Test
    void buildsSeedStyleDeclarationsFromClosure() {
        MathMeta mathMeta = MathDsl.math(modelDefinition()) {
            MathModelDef(
                mathModelDefId: 'NeuralNetwork',
                modelTypeEnum: MathModelType.LinearAlgebra,
                usageContextEnum: MathModelUsageContext.Inference,
                description: 'Definition')
            MathModel('Classifier') {
                mathModelDefId 'NeuralNetwork'
                sourceEnum MathModelSource.Manual
                description 'Configured through field methods'
            }
        }.validate()

        assert mathMeta.size() == 2
        assert mathMeta.MathModelDef.named('NeuralNetwork').get().description == 'Definition'
        assert mathMeta.MathModelDef.named('NeuralNetwork').get().modelTypeEnumId == 'MmtLinearAlgebra'
        assert mathMeta.MathModelDef.named('NeuralNetwork').get().usageContextEnumId == 'MmucInference'
        assert mathMeta.MathModel.named('Classifier').get().mathModelDefId == 'NeuralNetwork'
        assert mathMeta.MathModel.named('Classifier').get().sourceEnumId == 'MmsManual'
    }

    @Test
    void evaluatesGroovyMathFileAndExposesCrudStyleContainers() {
        File dslFile = new File(temporaryDirectory, 'classifier.groovy')
        dslFile.text = '''
MathModelDef('NeuralNetwork') {
    description 'Neural network definition'
    modelTypeEnum MathModelType.LinearAlgebra
    usageContextEnum MathModelUsageContext.Inference
}
MathModel('Classifier') {
    mathModelDefId 'NeuralNetwork'
    sourceEnum MathModelSource.Manual
}
MathModel('Simulator') {
    mathModelDefId 'PhysicalModel'
}
'''

        MathMeta mathMeta = MathDsl.evaluate(modelDefinition(), dslFile).validate()
        mathMeta.MathModel.matching { ModelValue value -> value.mathModelDefId == 'NeuralNetwork' }
            .configureEach { description 'Selected model' }

        assert mathMeta.MathModel.named('Classifier').get().description == 'Selected model'
        assert mathMeta.MathModelDef.named('NeuralNetwork').get().modelTypeEnumId == 'MmtLinearAlgebra'
        assert mathMeta.MathModel.named('Classifier').get().sourceEnumId == 'MmsManual'
        assert mathMeta.MathModel.named('Simulator').get().description == null
        assert mathMeta.MathModel.remove('Simulator')
        assert mathMeta.MathModel.size() == 1
    }

    @Test
    void derivesStableDslKeyForCompositeSeedRecord() {
        MathMeta mathMeta = MathDsl.math(modelDefinition()) {
            TransformationOperand(transformationId: 'DenseProduct', operandIndex: 0)
        }.validate()

        assert mathMeta.TransformationOperand
            .named('transformationId=DenseProduct|operandIndex=0')
            .get().entityKey.fields == [transformationId: 'DenseProduct', operandIndex: 0]
    }

    @Test
    void buildsNestedSeedRecordsAndInheritsRelationshipKeys() {
        MathMeta mathMeta = MathDsl.math(nestedModelDefinition()) {
            Category('AgentEntityModel') {
                categoryName 'Moqui Entity Model'
                objects('ObjectA', objectName: 'BillingAccount')
                morphisms('SchemaA', sourceObjectId: 'ObjectA', targetObjectId: 'ObjectA') {
                    morphismName 'schema::BillingAccount'
                    parameters('SchemaA.Source', parameterDefId: 'SourceDialect', symbolicValue: 'entity-definition')
                    Morphism('RelationshipA', sourceObjectId: 'ObjectA', targetObjectId: 'ObjectA',
                        morphismName: 'rel::Party')
                }
            }
        }.validate()

        assert mathMeta.size() == 5
        assert mathMeta.CategoryObject.named('ObjectA').get().categoryId == 'AgentEntityModel'
        assert mathMeta.Morphism.named('SchemaA').get().categoryId == 'AgentEntityModel'
        assert mathMeta.Parameter.named('SchemaA.Source').get().morphismId == 'SchemaA'
        assert mathMeta.Morphism.named('RelationshipA').get().parentMorphismId == 'SchemaA'
        assert mathMeta.Morphism.named('RelationshipA').get().categoryId == 'AgentEntityModel'
    }

    @Test
    void supportsGradleStyleRelationshipContainerBlocks() {
        MathMeta mathMeta = MathDsl.math(nestedModelDefinition()) {
            Category('AgentEntityModel') {
                categoryName 'Moqui Entity Model'
                objects {
                    CategoryObject('ObjectA') { objectName 'BillingAccount' }
                    ObjectB(objectName: 'Party')
                }
            }
        }.validate()

        assert mathMeta.CategoryObject.named('ObjectA').get().categoryId == 'AgentEntityModel'
        assert mathMeta.CategoryObject.named('ObjectB').get().objectName == 'Party'
    }

    @Test
    void evaluatesMatrixProductExampleAgainstCurrentMoquiMathCheckoutWhenConfigured() {
        String external = System.getenv('MOQUI_MATH_ENTITIES')
        if (!external) return

        ModelDefinition definition = MoquiSchemaInspector.inspect(new File(external))
        File example = new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy')
        MathMeta mathMeta = MathDsl.evaluate(definition, example).validate()

        assert mathMeta.MathModel.named('MatrixProduct').get().mathModelDefId == 'MatrixAlgebra'
        assert mathMeta.Matrix.named('A').get().cols == 3
        assert mathMeta.Matrix.named('B').get().componentArray == '[[7,8],[9,10],[11,12]]'
        assert mathMeta.Transformation.named('MultiplyAB').get().transformationTypeEnumId == 'TtMatrixProduct'
        assert mathMeta.Transformation.named('MultiplyAB').get().resultMatrixId == 'C'
        assert mathMeta.TransformationOperand.size() == 2
    }

    @Test
    void evaluatesNestedEntityMorphismExampleAgainstCurrentMoquiMathCheckoutWhenConfigured() {
        String external = System.getenv('MOQUI_MATH_ENTITIES')
        if (!external) return

        ModelDefinition definition = MoquiSchemaInspector.inspect(new File(external))
        File example = new File(System.getProperty('user.dir'), 'examples/entity-morphism.groovy')
        MathMeta mathMeta = MathDsl.evaluate(definition, example).validate()

        assert mathMeta.CategoryObject.named('AgEntObj_BillingAccount').get().categoryId == 'AgentEntityModel'
        assert mathMeta.Morphism.named('AgEntSchema_BillingAccount').get().categoryId == 'AgentEntityModel'
        assert mathMeta.Parameter.named('AgEntSchema_BillingAccount_001').get().morphismId ==
            'AgEntSchema_BillingAccount'
    }

    @Test
    void evaluatesProductionPlanExampleAgainstCurrentMoquiMathCheckoutWhenConfigured() {
        String external = System.getenv('MOQUI_MATH_ENTITIES')
        if (!external) return

        ModelDefinition definition = MoquiSchemaInspector.inspect(new File(external))
        File example = new File(System.getProperty('user.dir'), 'examples/production-plan.groovy')
        MathMeta mathMeta = MathDsl.evaluate(definition, example).validate()

        assert mathMeta.MathModelDef.named('LinearProductionPlanning').get().modelTypeEnumId == 'MmtLp'
        assert mathMeta.MathModel.named('ProductionPlan').get().solvingMethodEnumId == 'MmsmSimplex'
        assert mathMeta.Parameter.named('ProductionPlan.ObjectiveSense').get().symbolicValue == 'MAXIMIZE'
        assert mathMeta.Matrix.named('MachineCapacityCoefficients').get().componentArray == '[[2,1],[1,2]]'
        assert mathMeta.MathModelData.size() == 5
    }

    @Test
    void evaluatesEnergyDispatchExampleAgainstCurrentMoquiMathCheckoutWhenConfigured() {
        String external = System.getenv('MOQUI_MATH_ENTITIES')
        if (!external) return

        ModelDefinition definition = MoquiSchemaInspector.inspect(new File(external))
        File example = new File(System.getProperty('user.dir'), 'examples/energy-dispatch.groovy')
        MathMeta mathMeta = MathDsl.evaluate(definition, example).validate()

        assert mathMeta.MathModelDef.named('QuadraticEnergyDispatch').get().modelTypeEnumId == 'MmtQp'
        assert mathMeta.MathModel.named('EnergyDispatch').get().solvingMethodEnumId == 'MmsmInteriorPoint'
        assert mathMeta.Parameter.named('EnergyDispatch.ObjectiveSense').get().symbolicValue == 'MINIMIZE'
        assert mathMeta.Matrix.named('DispatchHessian').get().componentArray == '[[2,0],[0,4]]'
        assert mathMeta.MathModelData.matching { ModelValue value ->
            value.mathModelId == 'EnergyDispatch'
        }.size() == 5
    }

    private static ModelDefinition modelDefinition() {
        ModelDefinition model = new ModelDefinition()

        EntityDefinition mathModelDef = new EntityDefinition('moqui.math', 'MathModelDef')
        mathModelDef.addField(new FieldDefinition('mathModelDefId', 'id', true, true, null))
        mathModelDef.addField(new FieldDefinition('modelTypeEnumId', 'id', false, false, null))
        mathModelDef.addField(new FieldDefinition('usageContextEnumId', 'id', false, false, null))
        mathModelDef.addField(new FieldDefinition('description', 'text-medium', false, false, null))
        model.addEntity(mathModelDef)

        EntityDefinition mathModel = new EntityDefinition('moqui.math', 'MathModel')
        mathModel.addField(new FieldDefinition('mathModelId', 'id', true, true, null))
        mathModel.addField(new FieldDefinition('mathModelDefId', 'id', false, true, null))
        mathModel.addField(new FieldDefinition('sourceEnumId', 'id', false, false, null))
        mathModel.addField(new FieldDefinition('description', 'text-medium', false, false, null))
        model.addEntity(mathModel)

        EntityDefinition operand = new EntityDefinition('moqui.math', 'TransformationOperand')
        operand.addField(new FieldDefinition('transformationId', 'id', true, true, null))
        operand.addField(new FieldDefinition('operandIndex', 'number-integer', true, true, null))
        model.addEntity(operand)
        model
    }

    private static ModelDefinition nestedModelDefinition() {
        ModelDefinition model = new ModelDefinition()

        EntityDefinition category = new EntityDefinition('moqui.math.ct', 'Category')
        category.addField(new FieldDefinition('categoryId', 'id', true, true, null))
        category.addField(new FieldDefinition('categoryName', 'text-short', false, true, null))
        category.addRelationship(new RelationshipDefinition(
            'objects', 'many', 'moqui.math.ct.CategoryObject', [categoryId: 'categoryId']
        ))
        category.addRelationship(new RelationshipDefinition(
            'morphisms', 'many', 'moqui.math.ct.Morphism', [categoryId: 'categoryId']
        ))
        model.addEntity(category)

        EntityDefinition object = new EntityDefinition('moqui.math.ct', 'CategoryObject')
        object.addField(new FieldDefinition('categoryObjectId', 'id', true, true, null))
        object.addField(new FieldDefinition('categoryId', 'id', false, true, null))
        object.addField(new FieldDefinition('objectName', 'text-short', false, true, null))
        object.addRelationship(new RelationshipDefinition(
            'category', 'one', 'moqui.math.ct.Category', Collections.emptyMap()
        ))
        model.addEntity(object)

        EntityDefinition morphism = new EntityDefinition('moqui.math.ct', 'Morphism')
        morphism.addField(new FieldDefinition('morphismId', 'id', true, true, null))
        morphism.addField(new FieldDefinition('parentMorphismId', 'id', false, false, null))
        morphism.addField(new FieldDefinition('categoryId', 'id', false, true, null))
        morphism.addField(new FieldDefinition('sourceObjectId', 'id', false, true, null))
        morphism.addField(new FieldDefinition('targetObjectId', 'id', false, true, null))
        morphism.addField(new FieldDefinition('morphismName', 'text-short', false, true, null))
        morphism.addRelationship(new RelationshipDefinition(
            'parent', 'one', 'moqui.math.ct.Morphism', [parentMorphismId: 'morphismId']
        ))
        model.addEntity(morphism)

        EntityDefinition parameter = new EntityDefinition('moqui.math', 'Parameter', 'parameters')
        parameter.addField(new FieldDefinition('parameterId', 'id', true, true, null))
        parameter.addField(new FieldDefinition('parameterDefId', 'id', false, true, null))
        parameter.addField(new FieldDefinition('morphismId', 'id', false, false, null))
        parameter.addField(new FieldDefinition('symbolicValue', 'text-short', false, false, null))
        parameter.addRelationship(new RelationshipDefinition(
            'morphism', 'one', 'moqui.math.ct.Morphism', Collections.emptyMap()
        ))
        model.addEntity(parameter)
        model
    }
}
