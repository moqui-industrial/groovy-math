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

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

@CompileStatic
final class ModelValue extends LinkedHashMap<String, Object> {
    final EntityDefinition definition
    final String modelKey
    private EntityKey entityKey
    private boolean identityLocked

    ModelValue(final EntityDefinition definition, final String modelKey) {
        if (definition == null) throw new IllegalArgumentException('Entity definition is required')
        if (!modelKey) throw new IllegalArgumentException('Model key must not be empty')
        this.definition = definition
        this.modelKey = modelKey

        List<FieldDefinition> primaryKeyFields = definition.primaryKeyFields
        if (primaryKeyFields.size() == 1) {
            super.put(primaryKeyFields.first().name, modelKey)
        }
    }

    @Override
    Object put(final String fieldName, final Object value) {
        FieldDefinition field = definition.fields.get(fieldName)
        if (field == null) throw new IllegalArgumentException("Unknown field ${definition.fullName}.${fieldName}")
        if (identityLocked && field.primaryKey && get(fieldName) != value) {
            throw new IllegalStateException("Primary key field ${definition.fullName}.${fieldName} is immutable")
        }
        super.put(fieldName, value)
    }

    @Override
    void putAll(final Map<? extends String, ? extends Object> values) {
        values.each { String name, Object value -> put(name, value) }
    }

    ModelValue configure(final Closure<?> action) {
        if (action == null) throw new IllegalArgumentException('Configuration action is required')
        Closure<?> configured = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_FIRST
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(this)
        this
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String fieldName, final Object rawArguments) {
        Object[] arguments = rawArguments instanceof Object[] ? (Object[]) rawArguments : [rawArguments] as Object[]
        if (arguments.length != 1 || !definition.fields.containsKey(fieldName)) {
            throw new MissingMethodException(fieldName, getClass(), arguments)
        }
        put(fieldName, arguments[0])
        this
    }

    EntityKey lockIdentity() {
        LinkedHashMap<String, Object> keyFields = new LinkedHashMap<>()
        definition.primaryKeyFields.each { FieldDefinition field ->
            Object value = get(field.name)
            if (value == null) throw new IllegalStateException("Missing primary key ${definition.fullName}.${field.name}")
            keyFields.put(field.name, value)
        }
        if (keyFields.empty) keyFields.put('modelKey', modelKey)
        entityKey = new EntityKey(definition.fullName, keyFields)
        identityLocked = true
        entityKey
    }

    EntityKey getEntityKey() {
        entityKey ?: lockIdentity()
    }

    boolean isIdentityLocked() {
        identityLocked
    }

    ModelValue validate() {
        definition.fields.values().each { FieldDefinition field ->
            if (field.required && field.defaultExpression == null && get(field.name) == null) {
                throw new IllegalStateException("Missing required field ${definition.fullName}.${field.name}")
            }
        }
        this
    }
}
