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

package groovy.math.dsl

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.transform.TypeCheckingMode
import groovy.math.entity.EntityDefinition
import groovy.math.entity.FieldDefinition
import groovy.math.entity.ModelProvider
import groovy.math.entity.RelationshipDefinition

@CompileStatic
final class MathDslBuilder {
    final MathMeta mathMeta

    MathDslBuilder(final MathMeta mathMeta) {
        this.mathMeta = Objects.requireNonNull(mathMeta, 'Math metadata must not be null')
    }

    ModelProvider entity(final String entityName, final String modelKey,
                         final Map<String, ?> values = Collections.emptyMap(),
                         final Closure<?> action = null) {
        EntityDefinition definition = mathMeta.definition.entity(entityName)
        LinkedHashMap<String, Object> attributes = copyValues(values)
        declare(definition, modelKey, attributes, action, null, null).provider
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String entityName, final Object rawArguments) {
        List<Object> arguments = normalizeArguments(rawArguments)
        EntityDefinition entityDefinition = mathMeta.definition.entity(entityName)
        ParsedDeclaration parsed = parseArguments(entityName, arguments)
        declare(entityDefinition, parsed.modelKey, parsed.values, parsed.action, null, null).provider
    }

    @PackageScope
    DslDeclaration declareNested(final String entityName, final Object rawArguments,
                                 final DslDeclaration parent,
                                 final RelationshipDefinition relationship = null) {
        EntityDefinition entityDefinition = mathMeta.definition.entity(entityName)
        ParsedDeclaration parsed = parseArguments(entityName, normalizeArguments(rawArguments))
        declare(entityDefinition, parsed.modelKey, parsed.values, parsed.action, parent, relationship)
    }

    private DslDeclaration declare(final EntityDefinition entityDefinition, final String requestedKey,
                                   final LinkedHashMap<String, Object> values, final Closure<?> action,
                                   final DslDeclaration parent,
                                   final RelationshipDefinition relationship) {
        LinkedHashMap<String, Object> normalizedValues = normalizeFieldAliases(entityDefinition, values)
        if (parent != null) {
            inheritRelationshipKeys(parent, entityDefinition, normalizedValues, relationship)
            inheritAncestorKeys(parent, entityDefinition, normalizedValues)
        }
        String modelKey = requestedKey ?: keyFromValues(entityDefinition, normalizedValues)
        addSinglePrimaryKey(entityDefinition, modelKey, normalizedValues)

        ModelProvider provider = mathMeta.declare(entityDefinition.fullName, modelKey, normalizedValues)
        DslDeclaration record = new DslDeclaration(entityDefinition, modelKey, normalizedValues, provider, parent)
        if (action != null) new DslRecordDelegate(this, record).configure(action)
        record
    }

    private void inheritRelationshipKeys(final DslDeclaration parent, final EntityDefinition child,
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

    private static void inheritAncestorKeys(final DslDeclaration parent, final EntityDefinition child,
                                            final Map<String, Object> childValues) {
        Set<String> childPrimaryKeys = child.primaryKeyFields.collect { FieldDefinition field -> field.name } as Set<String>
        DslDeclaration ancestor = parent
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
            List<FieldDefinition> sourceFields = relationship.type == 'many' ?
                parent.primaryKeyFields : child.primaryKeyFields
            sourceFields.each { FieldDefinition field ->
                if (parent.fields.containsKey(field.name) && child.fields.containsKey(field.name)) {
                    fields.put(field.name, field.name)
                }
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
        source.each { String name, Object value -> values.put(name, normalizeValue(value)) }
        values
    }

    @PackageScope
    static Object normalizeValue(final Object value) {
        if (value instanceof DslEnumValue) return ((DslEnumValue) value).id
        if (value instanceof Map) {
            LinkedHashMap<Object, Object> normalized = new LinkedHashMap<>()
            ((Map<?, ?>) value).each { Object key, Object nestedValue -> normalized.put(key, normalizeValue(nestedValue)) }
            return normalized
        }
        if (value instanceof Collection) {
            List<Object> normalized = []
            ((Collection<?>) value).each { Object nestedValue -> normalized.add(normalizeValue(nestedValue)) }
            return normalized
        }
        if (value instanceof Object[]) {
            List<Object> normalized = []
            ((Object[]) value).each { Object nestedValue -> normalized.add(normalizeValue(nestedValue)) }
            return normalized
        }
        value
    }

    @PackageScope
    static String resolveFieldName(final EntityDefinition definition, final String requestedName) {
        if (definition.fields.containsKey(requestedName)) return requestedName
        if (requestedName.endsWith('Enum')) {
            String alias = requestedName + 'Id'
            if (definition.fields.containsKey(alias)) return alias
        }
        requestedName
    }

    private static LinkedHashMap<String, Object> normalizeFieldAliases(final EntityDefinition definition,
                                                                       final LinkedHashMap<String, Object> source) {
        LinkedHashMap<String, Object> normalized = new LinkedHashMap<>()
        source.each { String name, Object value ->
            String resolved = resolveFieldName(definition, name)
            if (normalized.containsKey(resolved) && resolved != name) {
                throw new IllegalArgumentException(
                    "Duplicate DSL values for ${definition.fullName}.${resolved} via '${name}' alias")
            }
            normalized.put(resolved, normalizeValue(value))
        }
        normalized
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
