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
    void evaluatesExampleAgainstCurrentMoquiMathCheckoutWhenConfigured() {
        String external = System.getenv('MOQUI_MATH_ENTITIES')
        if (!external) return

        ModelDefinition definition = MoquiSchemaInspector.inspect(new File(external))
        File example = new File(System.getProperty('user.dir'), 'examples/basic-model.groovy')
        MathGraph graph = MathDsl.evaluate(definition, example).validate()

        assert graph.MathModelDef.named('NeuralNetwork').get().description == 'Neural network model definition'
        assert graph.MathModel.named('Classifier').get().mathModelDefId == 'NeuralNetwork'
        assert graph.Transformation.named('DenseProduct').get().transformationTypeEnumId == 'TtMatrixProduct'
        assert graph.MathModelData.named('Classifier.DenseProduct').get().transformationId == 'DenseProduct'
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
}
