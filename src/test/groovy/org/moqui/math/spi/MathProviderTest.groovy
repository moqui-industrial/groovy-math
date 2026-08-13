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

package org.moqui.math.spi

import org.junit.jupiter.api.Test
import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.model.EntityDefinition
import org.moqui.math.model.FieldDefinition
import org.moqui.math.model.ModelDefinition

import static org.junit.jupiter.api.Assertions.assertThrows

class MathProviderTest {
    @Test
    void providerOwnsItsCompiledPlanType() {
        ModelDefinition definition = new ModelDefinition()
        EntityDefinition transformation = new EntityDefinition('moqui.math', 'Transformation')
        transformation.addField(new FieldDefinition('transformationId', 'id', true, true, null))
        transformation.addField(new FieldDefinition('transformationTypeEnumId', 'id', false, true, null))
        definition.addEntity(transformation)

        MathMeta mathMeta = MathDsl.math(definition) {
            Transformation('relu', transformationTypeEnumId: 'TtTensorReLu')
        }
        RecordingProvider provider = new RecordingProvider()

        RecordingPlan plan = provider.compile(mathMeta)
        Map<String, Object> result = provider.execute(plan, [input: 'tensor-handle'])

        assert provider.providerId == 'recording'
        assert plan.operations == ['TtTensorReLu']
        assert result == [output: 'tensor-handle', operations: 1]
        assertThrows(IllegalStateException) { mathMeta.Transformation.remove('relu') }
    }

    private static final class RecordingPlan {
        final List<String> operations

        RecordingPlan(final List<String> operations) {
            this.operations = operations
        }
    }

    private static final class RecordingProvider implements MathProvider<RecordingPlan, Map<String, Object>> {
        @Override
        String getProviderId() { 'recording' }

        @Override
        RecordingPlan compile(final MathMeta mathMeta) {
            mathMeta.freeze()
            new RecordingPlan(mathMeta.Transformation.collect { it.transformationTypeEnumId as String })
        }

        @Override
        Map<String, Object> execute(final RecordingPlan plan, final Map<String, ?> inputs) {
            [output: inputs.input, operations: plan.operations.size()]
        }
    }
}
