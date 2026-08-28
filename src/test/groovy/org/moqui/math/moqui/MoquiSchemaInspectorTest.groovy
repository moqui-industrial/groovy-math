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

package org.moqui.math.moqui

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.ModelDefinition

class MoquiSchemaInspectorTest {
    @TempDir
    File temporaryDirectory

    @Test
    void readsEntitiesRelationshipsAndExtensions() {
        File schema = schemaFile()
        ModelDefinition model = MoquiSchemaInspector.inspect(schema)

        EntityDefinition transformation = model.entity('moqui.math.Transformation')
        assert transformation.primaryKeyFields*.name == ['transformationId']
        assert transformation.fields.transformationTypeEnumId.required
        assert transformation.relationships.operands.type == 'many'
        assert transformation.fields.description

        EntityDefinition operand = model.entity('TransformationOperand')
        assert operand.primaryKeyFields*.name == ['transformationId', 'operandIndex']
        assert model.extensionCount == 1
        assert model.enumerationCount == 1
    }

    @Test
    void readsCurrentMoquiMathCheckoutWhenConfigured() {
        String external = System.getenv('MOQUI_MATH_ENTITIES')
        if (!external) return

        ModelDefinition model = MoquiSchemaInspector.inspect(new File(external))

        assert model.entities.size() == 72
        assert model.extensionCount == 4
        assert model.enumerationCount == 885
        assert model.entity('MathModel').relationships.data.relatedEntityName == 'moqui.math.MathModelData'
        assert model.entity('MathModelData').relationships.transformation.relatedEntityName == 'moqui.math.Transformation'
        assert model.entity('Morphism').fields.transformationId
    }

    private File schemaFile() {
        File fixture = new File(temporaryDirectory, 'MathEntities.xml')
        fixture.text = '''<?xml version="1.0" encoding="UTF-8"?>
<entities>
  <entity entity-name="Transformation" package="moqui.math">
    <field name="transformationId" type="id" is-pk="true"/>
    <field name="transformationTypeEnumId" type="id" not-null="true"/>
    <relationship type="many" related="moqui.math.TransformationOperand" short-alias="operands">
      <key-map field-name="transformationId"/>
    </relationship>
    <seed-data>
      <moqui.basic.Enumeration enumId="TtLinear" enumTypeId="TransformationType"/>
    </seed-data>
  </entity>
  <entity entity-name="TransformationOperand" package="moqui.math">
    <field name="transformationId" type="id" is-pk="true"/>
    <field name="operandIndex" type="number-integer" is-pk="true"/>
  </entity>
  <extend-entity entity-name="Transformation" package="moqui.math">
    <field name="description" type="text-medium"/>
  </extend-entity>
</entities>'''
        fixture
    }
}
