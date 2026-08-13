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

import groovy.transform.CompileStatic
import org.moqui.math.model.EntityDefinition
import org.moqui.math.model.ModelDefinition
import org.moqui.math.model.ModelProvider
import org.moqui.math.model.ModelValue
import org.moqui.math.model.NamedModelContainer

@CompileStatic
final class MathMeta implements Iterable<ModelValue> {
    final ModelDefinition definition
    private final LinkedHashMap<String, NamedModelContainer> containers = new LinkedHashMap<>()

    MathMeta(final ModelDefinition definition) {
        this.definition = Objects.requireNonNull(definition, 'Model definition must not be null')
    }

    NamedModelContainer entity(final String entityName) {
        EntityDefinition entityDefinition = definition.entity(entityName)
        NamedModelContainer container = containers.get(entityDefinition.fullName)
        if (container == null) {
            container = new NamedModelContainer(entityDefinition)
            containers.put(entityDefinition.fullName, container)
        }
        container
    }

    ModelProvider declare(final String entityName, final String modelKey,
                          final Map<String, ?> values = Collections.emptyMap(),
                          final Closure<?> action = null) {
        LinkedHashMap<String, Object> attributes = new LinkedHashMap<>()
        values.each { String name, Object value -> attributes.put(name, value) }
        entity(entityName).register(modelKey) { ModelValue value ->
            value.putAll(attributes)
            if (action != null) value.configure(action)
        }
    }

    NamedModelContainer propertyMissing(final String entityName) {
        entity(entityName)
    }

    MathMeta validate() {
        containers.values().each { NamedModelContainer container ->
            container.providers.each { ModelProvider provider -> provider.get() }
        }
        this
    }

    MathMeta freeze() {
        validate()
        containers.values().each { NamedModelContainer container -> container.disallowChanges() }
        this
    }

    int size() {
        int count = 0
        containers.values().each { NamedModelContainer container -> count += container.size() }
        count
    }

    @Override
    Iterator<ModelValue> iterator() {
        List<ModelValue> values = []
        containers.values().each { NamedModelContainer container ->
            container.each { ModelValue value -> values.add(value) }
        }
        values.iterator()
    }
}
