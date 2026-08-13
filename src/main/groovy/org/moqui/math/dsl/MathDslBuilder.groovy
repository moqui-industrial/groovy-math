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
import org.moqui.math.model.RelationshipDefinition

@CompileStatic
final class MathDslBuilder {
    final MathGraph graph

    MathDslBuilder(final MathGraph graph) {
        this.graph = Objects.requireNonNull(graph, 'Math graph must not be null')
    }

    ModelProvider entity(final String entityName, final String modelKey,
                         final Map<String, ?> values = Collections.emptyMap(),
                         final Closure<?> action = null) {
        EntityDefinition definition = graph.definition.entity(entityName)
        LinkedHashMap<String, Object> attributes = copyValues(values)
        declare(definition, modelKey, attributes, action, null, null).provider
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String entityName, final Object rawArguments) {
        List<Object> arguments = normalizeArguments(rawArguments)
        EntityDefinition entityDefinition = graph.definition.entity(entityName)
        ParsedDeclaration parsed = parseArguments(entityName, arguments)
        declare(entityDefinition, parsed.modelKey, parsed.values, parsed.action, null, null).provider
    }

    SeedRecord declareNested(final String entityName, final Object rawArguments,
                             final SeedRecord parent, final RelationshipDefinition relationship = null) {
        EntityDefinition entityDefinition = graph.definition.entity(entityName)
        ParsedDeclaration parsed = parseArguments(entityName, normalizeArguments(rawArguments))
        declare(entityDefinition, parsed.modelKey, parsed.values, parsed.action, parent, relationship)
    }

    private SeedRecord declare(final EntityDefinition entityDefinition, final String requestedKey,
                               final LinkedHashMap<String, Object> values, final Closure<?> action,
                               final SeedRecord parent, final RelationshipDefinition relationship) {
        if (parent != null) {
            inheritRelationshipKeys(parent, entityDefinition, values, relationship)
            inheritAncestorKeys(parent, entityDefinition, values)
        }
        String modelKey = requestedKey ?: keyFromValues(entityDefinition, values)
        addSinglePrimaryKey(entityDefinition, modelKey, values)

        ModelProvider provider = graph.declare(entityDefinition.fullName, modelKey, values)
        SeedRecord record = new SeedRecord(entityDefinition, modelKey, values, provider, parent)
        if (action != null) new SeedRecordBuilder(this, record).configure(action)
        record
    }

    RelationshipDefinition relationship(final SeedRecord parent, final String alias) {
        RelationshipDefinition relationship = parent.definition.relationships.get(alias)
        if (relationship == null) {
            throw new IllegalArgumentException("Unknown relationship ${parent.definition.fullName}.${alias}")
        }
        relationship
    }

    EntityDefinition relatedEntity(final RelationshipDefinition relationship) {
        graph.definition.entity(relationship.relatedEntityName)
    }

    private void inheritRelationshipKeys(final SeedRecord parent, final EntityDefinition child,
                                         final Map<String, Object> childValues,
                                         final RelationshipDefinition requestedRelationship) {
        RelationshipLink link = requestedRelationship != null ?
            linkForRequestedRelationship(parent.definition, child, requestedRelationship) :
            inferRelationship(parent.definition, child)

        link.parentToChildFields.each { String parentField, String childField ->
            Object inherited = parent.values.get(parentField)
            if (inherited == null) {
                throw new IllegalArgumentException(
                    "Cannot nest ${child.fullName} under ${parent.definition.fullName}: " +
                        "parent field ${parentField} has no declared value"
                )
            }
            Object explicit = childValues.get(childField)
            if (explicit != null && explicit != inherited) {
                throw new IllegalArgumentException(
                    "Nested ${child.fullName}.${childField} '${explicit}' conflicts with " +
                        "${parent.definition.fullName}.${parentField} '${inherited}'"
                )
            }
            childValues.put(childField, inherited)
        }
    }

    private static void inheritAncestorKeys(final SeedRecord parent, final EntityDefinition child,
                                            final Map<String, Object> childValues) {
        Set<String> childPrimaryKeys = child.primaryKeyFields.collect { FieldDefinition field -> field.name } as Set<String>
        SeedRecord ancestor = parent
        while (ancestor != null) {
            ancestor.definition.primaryKeyFields.each { FieldDefinition field ->
                if (!childPrimaryKeys.contains(field.name) && child.fields.containsKey(field.name) &&
                    !childValues.containsKey(field.name)) {
                    Object inherited = ancestor.values.get(field.name)
                    if (inherited != null) childValues.put(field.name, inherited)
                }
            }
            ancestor = ancestor.parent
        }
    }

    private static RelationshipLink linkForRequestedRelationship(final EntityDefinition parent,
                                                                  final EntityDefinition child,
                                                                  final RelationshipDefinition relationship) {
        if (relationship.relatedEntityName != child.fullName) {
            throw new IllegalArgumentException(
                "Relationship ${parent.fullName}.${relationship.name} expects " +
                    "${relationship.relatedEntityName}, not ${child.fullName}"
            )
        }
        parentRelationshipLink(parent, child, relationship)
    }

    private static RelationshipLink inferRelationship(final EntityDefinition parent,
                                                       final EntityDefinition child) {
        List<RelationshipLink> parentMany = parent.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.type == 'many' && rel.relatedEntityName == child.fullName }
            .collect { RelationshipDefinition rel -> parentRelationshipLink(parent, child, rel) }
        if (parentMany.size() == 1) return parentMany.first()
        if (parentMany.size() > 1) throw ambiguousRelationship(parent, child, parentMany)

        List<RelationshipLink> childToParent = child.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.type == 'one' && rel.relatedEntityName == parent.fullName }
            .collect { RelationshipDefinition rel -> childRelationshipLink(parent, child, rel) }
        if (childToParent.size() == 1) return childToParent.first()
        if (childToParent.size() > 1) throw ambiguousRelationship(parent, child, childToParent)

        List<RelationshipLink> remaining = parent.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.relatedEntityName == child.fullName }
            .collect { RelationshipDefinition rel -> parentRelationshipLink(parent, child, rel) }
        if (remaining.size() == 1) return remaining.first()
        if (remaining.size() > 1) throw ambiguousRelationship(parent, child, remaining)
        throw new IllegalArgumentException("No relationship connects ${parent.fullName} to ${child.fullName}")
    }

    private static RelationshipLink parentRelationshipLink(final EntityDefinition parent,
                                                             final EntityDefinition child,
                                                             final RelationshipDefinition relationship) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>()
        if (relationship.keyMap.isEmpty()) {
            parent.primaryKeyFields.each { FieldDefinition field ->
                if (child.fields.containsKey(field.name)) fields.put(field.name, field.name)
            }
        } else {
            fields.putAll(relationship.keyMap)
        }
        requireKeyMap(parent, child, relationship, fields)
        new RelationshipLink(relationship.name, fields)
    }

    private static RelationshipLink childRelationshipLink(final EntityDefinition parent,
                                                            final EntityDefinition child,
                                                            final RelationshipDefinition relationship) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>()
        if (relationship.keyMap.isEmpty()) {
            parent.primaryKeyFields.each { FieldDefinition field ->
                if (child.fields.containsKey(field.name)) fields.put(field.name, field.name)
            }
        } else {
            relationship.keyMap.each { String childField, String parentField ->
                fields.put(parentField, childField)
            }
        }
        requireKeyMap(parent, child, relationship, fields)
        new RelationshipLink(relationship.name, fields)
    }

    private static void requireKeyMap(final EntityDefinition parent, final EntityDefinition child,
                                      final RelationshipDefinition relationship,
                                      final Map<String, String> fields) {
        if (fields.isEmpty()) {
            throw new IllegalArgumentException(
                "Relationship ${relationship.name} between ${parent.fullName} and ${child.fullName} " +
                    'has no usable key mapping'
            )
        }
    }

    private static IllegalArgumentException ambiguousRelationship(final EntityDefinition parent,
                                                                   final EntityDefinition child,
                                                                   final List<RelationshipLink> links) {
        new IllegalArgumentException(
            "Multiple relationships connect ${parent.fullName} to ${child.fullName}: " +
                "${links*.name.join(', ')}; use a relationship block to disambiguate"
        )
    }

    private static ParsedDeclaration parseArguments(final String entityName, final List<Object> arguments) {
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
        new ParsedDeclaration(modelKey, values, action)
    }

    private static LinkedHashMap<String, Object> copyValues(final Map<String, ?> source) {
        LinkedHashMap<String, Object> values = new LinkedHashMap<>()
        source.each { String name, Object value -> values.put(name, value) }
        values
    }

    private static void addSinglePrimaryKey(final EntityDefinition definition, final String modelKey,
                                            final Map<String, Object> values) {
        List<FieldDefinition> primaryKeys = definition.primaryKeyFields
        if (primaryKeys.size() == 1) {
            String fieldName = primaryKeys.first().name
            Object explicit = values.get(fieldName)
            if (explicit != null && explicit.toString() != modelKey) {
                throw new IllegalArgumentException(
                    "DSL key '${modelKey}' conflicts with ${definition.fullName}.${fieldName} '${explicit}'"
                )
            }
            values.put(fieldName, modelKey)
        }
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

    @CompileStatic
    private static final class ParsedDeclaration {
        final String modelKey
        final LinkedHashMap<String, Object> values
        final Closure<?> action

        ParsedDeclaration(final String modelKey, final LinkedHashMap<String, Object> values,
                          final Closure<?> action) {
            this.modelKey = modelKey
            this.values = values
            this.action = action
        }
    }

    @CompileStatic
    private static final class RelationshipLink {
        final String name
        final LinkedHashMap<String, String> parentToChildFields

        RelationshipLink(final String name, final LinkedHashMap<String, String> fields) {
            this.name = name
            this.parentToChildFields = fields
        }
    }
}
