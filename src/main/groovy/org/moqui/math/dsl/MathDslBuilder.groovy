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
import groovy.transform.TypeCheckingMode
import org.moqui.math.model.EntityDefinition
import org.moqui.math.model.FieldDefinition
import org.moqui.math.model.ModelProvider

@CompileStatic
final class MathDslBuilder {
    final MathGraph graph

    MathDslBuilder(final MathGraph graph) {
        this.graph = Objects.requireNonNull(graph, 'Math graph must not be null')
    }

    ModelProvider entity(final String entityName, final String modelKey,
                         final Map<String, ?> values = Collections.emptyMap(),
                         final Closure<?> action = null) {
        graph.declare(entityName, modelKey, values, action)
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String entityName, final Object rawArguments) {
        List<Object> arguments = normalizeArguments(rawArguments)
        LinkedHashMap<String, Object> values = new LinkedHashMap<>()
        Closure<?> action
        String modelKey

        arguments.each { Object argument ->
            if (argument instanceof Map) values.putAll((Map<String, Object>) argument)
            else if (argument instanceof Closure) action = (Closure<?>) argument
            else if (argument instanceof CharSequence) modelKey = argument.toString()
            else throw new IllegalArgumentException(
                "Unsupported argument ${argument?.class?.name} for ${entityName} declaration"
            )
        }

        EntityDefinition entityDefinition = graph.definition.entity(entityName)
        modelKey = modelKey ?: keyFromValues(entityDefinition, values)
        graph.declare(entityDefinition.fullName, modelKey, values, action)
    }

    private static List<Object> normalizeArguments(final Object rawArguments) {
        if (rawArguments == null) return []
        if (rawArguments instanceof Object[]) return new ArrayList<Object>(Arrays.asList((Object[]) rawArguments))
        [rawArguments]
    }

    private static String keyFromValues(final EntityDefinition definition, final Map<String, Object> values) {
        Object explicitKey = values.remove('_key')
        if (explicitKey != null) return explicitKey.toString()

        List<FieldDefinition> primaryKeys = definition.primaryKeyFields
        if (primaryKeys.empty) {
            throw new IllegalArgumentException("${definition.fullName} requires an explicit DSL _key")
        }
        if (!primaryKeys.every { FieldDefinition field -> values.containsKey(field.name) }) {
            throw new IllegalArgumentException(
                "${definition.fullName} requires a name, _key, or all primary-key fields"
            )
        }
        if (primaryKeys.size() == 1) return values.get(primaryKeys.first().name).toString()
        primaryKeys.collect { FieldDefinition field -> "${field.name}=${values.get(field.name)}" }.join('|')
    }
}
