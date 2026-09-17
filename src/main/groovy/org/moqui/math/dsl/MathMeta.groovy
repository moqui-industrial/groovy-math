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
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.ModelProvider
import org.moqui.math.entity.ModelValue
import org.moqui.math.entity.EnumerationDefinition
import org.moqui.math.entity.FieldDefinition
import org.moqui.math.entity.NamedModelContainer

@CompileStatic
final class MathMeta implements Iterable<ModelValue> {
    final ModelDefinition definition
    private final LinkedHashMap<String, NamedModelContainer> containers = new LinkedHashMap<>()

    MathMeta(final ModelDefinition definition) {
        this.definition = Objects.requireNonNull(definition, 'Model definition must not be null')
    }

    boolean hasEntity(final String entityName) {
        definition.hasEntity(entityName)
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

    /**
     * Every declared enumeration value that the schema does not back, in declaration order.
     *
     * <p>A field whose relationship names an enumeration type may only hold ids of that type.
     * Nothing enforced this before, so a DSL enum could name a value that no seed data declares
     * and the model would be accepted here and rejected by the database later, or silently
     * ignored. This reports rather than throws so a whole model can be inspected at once.
     */
    List<String> enumViolations() {
        List<String> violations = []
        containers.values().each { NamedModelContainer container ->
            container.each { ModelValue value ->
                container.definition.enumFields.each { String fieldName, String enumTypeId ->
                    // Only check fields whose declared type the schema actually defines; see
                    // ModelDefinition.isDeclaredEnumerationType for why the rest are skipped.
                    if (!definition.isDeclaredEnumerationType(enumTypeId)) return
                    Object declared = value.get(fieldName)
                    if (declared == null) return
                    String enumId = declared.toString()
                    EnumerationDefinition known = definition.enumeration(enumId)
                    if (known == null) {
                        violations.add(("${container.definition.fullName}[${value.modelKey}].${fieldName} = " +
                            "'${enumId}' is not a declared Enumeration (expected type ${enumTypeId})").toString())
                    } else if (known.enumTypeId != null && known.enumTypeId != enumTypeId) {
                        violations.add(("${container.definition.fullName}[${value.modelKey}].${fieldName} = " +
                            "'${enumId}' is of type ${known.enumTypeId}, not ${enumTypeId}").toString())
                    }
                }
            }
        }
        violations
    }

    /**
     * Values on enumeration fields whose type cannot be determined, that are not a declared
     * Enumeration under any type.
     *
     * <p>Weaker than {@link #enumViolations} and reported separately: without a type there is
     * nothing to check membership against, but an id that matches no Enumeration at all is
     * wrong regardless. Matrix.domainSpaceEnumId is the case that motivates this: the field
     * takes an enum-group id such as Eng3DEuclideanSpace, and a model naming something the
     * schema has never heard of is a defect even though the expected type is unstated.
     */
    List<String> unknownEnumReferences() {
        List<String> unknown = []
        containers.values().each { NamedModelContainer container ->
            container.each { ModelValue value ->
                container.definition.enumFields.each { String fieldName, String enumTypeId ->
                    if (definition.isDeclaredEnumerationType(enumTypeId)) return
                    Object declared = value.get(fieldName)
                    if (declared == null) return
                    if (definition.enumeration(declared.toString()) == null) {
                        unknown.add(("${container.definition.fullName}[${value.modelKey}].${fieldName} = " +
                            "'${declared}' matches no declared Enumeration").toString())
                    }
                }
            }
        }
        unknown
    }

    /**
     * Enumeration fields this model uses whose type the schema never declares, so nothing can
     * validate them. These are gaps in the schema, not defects in the model.
     */
    List<String> unvalidatableEnumFields() {
        Set<String> gaps = new LinkedHashSet<>()
        containers.values().each { NamedModelContainer container ->
            container.definition.enumFields.each { String fieldName, String enumTypeId ->
                if (!definition.isDeclaredEnumerationType(enumTypeId)) {
                    gaps.add("${container.definition.fullName}.${fieldName} (declared type ${enumTypeId} does not exist)".toString())
                }
            }
        }
        new ArrayList<String>(gaps)
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

    Object execute(final String targetId = null,
                   @DelegatesTo(value = org.moqui.math.MathEngine.ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> request = null) {
        org.moqui.math.MathEngine.execute(this, targetId, request)
    }

    Object execute(@DelegatesTo(value = org.moqui.math.MathEngine.ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> request) {
        org.moqui.math.MathEngine.execute(this, null, request)
    }
}
