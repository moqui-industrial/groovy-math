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

package groovy.math.entity

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class ModelValueTest {
    @Test
    void schemaAwareMapAndImmutableIdentity() {
        EntityDefinition definition = transformationDefinition()
        ModelValue value = new ModelValue(definition, 'DenseProduct')

        value.transformationTypeEnumId = 'TtMatrixProduct'
        value.lockIdentity()

        assert value.transformationId == 'DenseProduct'
        assert value.entityKey.fields == [transformationId: 'DenseProduct']
        assertThrows(IllegalArgumentException) { value.unknownField = 'x' }
        assertThrows(IllegalStateException) { value.transformationId = 'Other' }
    }

    @Test
    void validatesRequiredFields() {
        ModelValue value = new ModelValue(transformationDefinition(), 'DenseProduct')
        IllegalStateException failure = assertThrows(IllegalStateException) { value.validate() }
        assert failure.message.contains('transformationTypeEnumId')
    }

    @Test
    void supportsCompositeIdentity() {
        EntityDefinition definition = new EntityDefinition('moqui.math', 'TransformationOperand')
        definition.addField(new FieldDefinition('transformationId', 'id', true, true, null))
        definition.addField(new FieldDefinition('operandIndex', 'number-integer', true, true, null))

        ModelValue value = new ModelValue(definition, 'product.left')
        value.putAll(transformationId: 'DenseProduct', operandIndex: 0)

        assert value.validate().lockIdentity().fields == [transformationId: 'DenseProduct', operandIndex: 0]
    }

    private static EntityDefinition transformationDefinition() {
        EntityDefinition definition = new EntityDefinition('moqui.math', 'Transformation')
        definition.addField(new FieldDefinition('transformationId', 'id', true, true, null))
        definition.addField(new FieldDefinition('transformationTypeEnumId', 'id', false, true, null))
        definition
    }
}
