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
    static final File VENDORED_MATH_ENTITIES = new File('src/main/resources/moqui-math/MathEntities.xml')

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
    void readsVendoredMoquiMathSchema() {
        // Reads the schema vendored under src/main/resources, not whatever Moqui checkout
        // happens to sit on the developer's disk: these counts are only meaningful if the
        // file under test is the one that ships in the jar. Refresh both together with
        // `./gradlew syncMoquiSchema`.
        ModelDefinition model = MoquiSchemaInspector.inspect(VENDORED_MATH_ENTITIES)

        assert model.entities.size() == 92
        assert model.extensionCount == 1
        assert model.entity('MathModel').relationships.data.relatedEntityName == 'moqui.math.MathModelData'
        assert model.entity('MathModelDefPipeline').relationships.transformation.relatedEntityName == 'moqui.math.Transformation'
        assert model.entity('MathModelDef').relationships.pipeline.relatedEntityName == 'moqui.math.MathModelDefPipeline'
        assert model.entity('Morphism').fields.transformationId
    }

    @Test
    void loadsEmbeddedSchemaFromClasspath() {
        // No file path, no MOQUI_MATH_ENTITIES: this is what a plain consumer of the jar gets.
        ModelDefinition model = MoquiSchemaInspector.embedded()

        assert model.entities.size() == 105
        assert model.entities.keySet().count { String name -> name.startsWith('moqui.math') } == 92
        assert model.extensionCount == 1
        // Raw element count across the three sources; unique ids are fewer because MathData.xml
        // is allowed to redeclare a value from MathEntities.xml.
        assert model.enumerationCount == 1218
        assert model.enumerations.size() == 1177

        // The moqui.basic allowlist: the enum catalogue, the status-flow family and units.
        assert model.entities.keySet().findAll { String name -> name.startsWith('moqui.basic') } as Set ==
            MoquiSchemaInspector.EMBEDDED_BASIC_ENTITIES
        // ...and nothing else from BasicEntities.xml leaked into the DSL namespace.
        assert !model.entities.containsKey('moqui.basic.email.EmailMessage')
        assert !model.entities.containsKey('moqui.basic.Geo')

        assert model.entity('moqui.basic.Enumeration').fields.containsKey('enumCode')
        assert model.entity('StatusFlowTransition').primaryKeyFields*.name ==
            ['statusFlowId', 'statusId', 'toStatusId']
        assert model.entity('MathModel').relationships.data.relatedEntityName == 'moqui.math.MathModelData'
    }

    @Test
    void embeddedSchemaIsCached() {
        assert MoquiSchemaInspector.embedded().is(MoquiSchemaInspector.embedded())
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
