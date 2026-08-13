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

package org.moqui.math.model

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class NamedModelContainerTest {
    @Test
    void registersConfiguresAndRealizesLazily() {
        NamedModelContainer models = new NamedModelContainer(modelDefinition())
        int configured = 0
        ModelProvider provider = models.register('Classifier') {
            configured++
            mathModelDefId = 'DenseDef'
        }

        assert !provider.realized
        models.named('Classifier') { description = 'Configured later' }
        assert configured == 0

        ModelValue value = provider.get()
        assert configured == 1
        assert value.mathModelId == 'Classifier'
        assert value.mathModelDefId == 'DenseDef'
        assert value.description == 'Configured later'
        assert provider.get().is(value)
        assert configured == 1
    }

    @Test
    void liveMatchingConfiguresPresentAndFutureValues() {
        NamedModelContainer models = new NamedModelContainer(modelDefinition())
        models.register('First') { mathModelDefId = 'DenseDef' }

        models.matching { ModelValue value -> value.mathModelDefId == 'DenseDef' }
            .configureEach { enabled = 'Y' }

        models.register('Second') { mathModelDefId = 'DenseDef' }
        models.register('Other') { mathModelDefId = 'OtherDef' }

        assert models.named('First').get().enabled == 'Y'
        assert models.named('Second').get().enabled == 'Y'
        assert models.named('Other').get().enabled == null
    }

    @Test
    void duplicateAndUnknownNamesFail() {
        NamedModelContainer models = new NamedModelContainer(modelDefinition())
        models.register('Classifier')

        assertThrows(IllegalArgumentException) { models.register('Classifier') }
        assertThrows(NoSuchElementException) { models.named('Missing') }
    }

    @Test
    void cyclicRealizationFailsAndCanBeRetried() {
        NamedModelContainer models = new NamedModelContainer(modelDefinition())
        ModelProvider provider
        provider = models.register('Classifier') { provider.get() }

        assertThrows(IllegalStateException) { provider.get() }
        assert !provider.realized
    }

    private static EntityDefinition modelDefinition() {
        EntityDefinition definition = new EntityDefinition('moqui.math', 'MathModel')
        definition.addField(new FieldDefinition('mathModelId', 'id', true, true, null))
        definition.addField(new FieldDefinition('mathModelDefId', 'id', false, true, null))
        definition.addField(new FieldDefinition('description', 'text-medium', false, false, null))
        definition.addField(new FieldDefinition('enabled', 'text-indicator', false, false, null))
        definition
    }
}
