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

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.moqui.math.model.EntityDefinition
import org.moqui.math.model.FieldDefinition
import org.moqui.math.model.ModelDefinition
import org.moqui.math.model.ModelValue
import org.moqui.math.model.RelationshipDefinition
import org.moqui.math.moqui.MoquiSchemaInspector

class MathDslTest {
    @TempDir
    File temporaryDirectory

    @Test
    void buildsSeedStyleDeclarationsFromClosure() {
        MathGraph graph = MathDsl.math(modelDefinition()) {
            MathModelDef(mathModelDefId: 'NeuralNetwork', description: 'Definition')
            MathModel('Classifier') {
                mathModelDefId 'NeuralNetwork'
                description 'Configured through field methods'
            }
        }.validate()

        assert graph.size() == 2
        assert graph.MathModelDef.named('NeuralNetwork').get().description == 'Definition'
        assert graph.MathModel.named('Classifier').get().mathModelDefId == 'NeuralNetwork'
    }

    @Test
    void evaluatesGroovyMathFileAndExposesCrudStyleContainers() {
        File dslFile = new File(temporaryDirectory, 'classifier.groovy')
        dslFile.text = '''
MathModelDef('NeuralNetwork') {
    description 'Neural network definition'
}
MathModel('Classifier') {
    mathModelDefId 'NeuralNetwork'
}
MathModel('Simulator') {
    mathModelDefId 'PhysicalModel'
}
'''

        MathGraph graph = MathDsl.evaluate(modelDefinition(), dslFile).validate()
        graph.MathModel.matching { ModelValue value -> value.mathModelDefId == 'NeuralNetwork' }
            .configureEach { description 'Selected model' }

        assert graph.MathModel.named('Classifier').get().description == 'Selected model'
        assert graph.MathModel.named('Simulator').get().description == null
        assert graph.MathModel.remove('Simulator')
        assert graph.MathModel.size() == 1
    }

    @Test
    void derivesStableDslKeyForCompositeSeedRecord() {
        MathGraph graph = MathDsl.math(modelDefinition()) {
            TransformationOperand(transformationId: 'DenseProduct', operandIndex: 0)
        }.validate()

        assert graph.TransformationOperand
            .named('transformationId=DenseProduct|operandIndex=0')
            .get().entityKey.fields == [transformationId: 'DenseProduct', operandIndex: 0]
    }

    @Test
    void buildsNestedSeedRecordsAndInheritsRelationshipKeys() {
        MathGraph graph = MathDsl.math(nestedModelDefinition()) {
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

        assert graph.size() == 5
        assert graph.CategoryObject.named('ObjectA').get().categoryId == 'AgentEntityModel'
        assert graph.Morphism.named('SchemaA').get().categoryId == 'AgentEntityModel'
        assert graph.Parameter.named('SchemaA.Source').get().morphismId == 'SchemaA'
        assert graph.Morphism.named('RelationshipA').get().parentMorphismId == 'SchemaA'
        assert graph.Morphism.named('RelationshipA').get().categoryId == 'AgentEntityModel'
    }

    @Test
    void supportsGradleStyleRelationshipContainerBlocks() {
        MathGraph graph = MathDsl.math(nestedModelDefinition()) {
            Category('AgentEntityModel') {
                categoryName 'Moqui Entity Model'
                objects {
                    CategoryObject('ObjectA') { objectName 'BillingAccount' }
                    ObjectB(objectName: 'Party')
                }
            }
        }.validate()

        assert graph.CategoryObject.named('ObjectA').get().categoryId == 'AgentEntityModel'
        assert graph.CategoryObject.named('ObjectB').get().objectName == 'Party'
    }

    @Test
    void evaluatesLibTorchPlanExampleAgainstCurrentMoquiMathCheckoutWhenConfigured() {
        String external = System.getenv('MOQUI_MATH_ENTITIES')
        if (!external) return

        ModelDefinition definition = MoquiSchemaInspector.inspect(new File(external))
        File example = new File(System.getProperty('user.dir'), 'examples/libtorch-mlp.groovy')
        MathGraph graph = MathDsl.evaluate(definition, example).validate()

        assert graph.MathModel.named('IrisClassifier').get().mathModelDefId == 'LibTorchMlp'
        assert graph.Tensor.named('Dense1Weight').get().shape == '[8,4]'
        assert graph.Transformation.named('Dense1').get().transformationTypeEnumId == 'TtAffine'
        assert graph.Transformation.named('HiddenRelu').get().resultTensorId == 'HiddenActivation'
        assert graph.TransformationOperand.size() == 7
        assert graph.MathModelData.named('Dense2Step').get().mathModelId == 'IrisClassifier'
    }

    @Test
    void evaluatesNestedEntityMorphismExampleAgainstCurrentMoquiMathCheckoutWhenConfigured() {
        String external = System.getenv('MOQUI_MATH_ENTITIES')
        if (!external) return

        ModelDefinition definition = MoquiSchemaInspector.inspect(new File(external))
        File example = new File(System.getProperty('user.dir'), 'examples/entity-morphism.groovy')
        MathGraph graph = MathDsl.evaluate(definition, example).validate()

        assert graph.CategoryObject.named('AgEntObj_BillingAccount').get().categoryId == 'AgentEntityModel'
        assert graph.Morphism.named('AgEntSchema_BillingAccount').get().categoryId == 'AgentEntityModel'
        assert graph.Parameter.named('AgEntSchema_BillingAccount_001').get().morphismId ==
            'AgEntSchema_BillingAccount'
    }

    private static ModelDefinition modelDefinition() {
        ModelDefinition model = new ModelDefinition()

        EntityDefinition mathModelDef = new EntityDefinition('moqui.math', 'MathModelDef')
        mathModelDef.addField(new FieldDefinition('mathModelDefId', 'id', true, true, null))
        mathModelDef.addField(new FieldDefinition('description', 'text-medium', false, false, null))
        model.addEntity(mathModelDef)

        EntityDefinition mathModel = new EntityDefinition('moqui.math', 'MathModel')
        mathModel.addField(new FieldDefinition('mathModelId', 'id', true, true, null))
        mathModel.addField(new FieldDefinition('mathModelDefId', 'id', false, true, null))
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
