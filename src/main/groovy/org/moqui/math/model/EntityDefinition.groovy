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

@CompileStatic
final class EntityDefinition {
    final String name
    final String packageName
    final String fullName
    final String shortAlias
    final LinkedHashMap<String, FieldDefinition> fields = new LinkedHashMap<>()
    final LinkedHashMap<String, RelationshipDefinition> relationships = new LinkedHashMap<>()

    EntityDefinition(final String packageName, final String name, final String shortAlias = null) {
        if (!name) throw new IllegalArgumentException('Entity name must not be empty')
        this.name = name
        this.packageName = packageName ?: ''
        this.fullName = this.packageName ? "${this.packageName}.${name}" : name
        this.shortAlias = shortAlias
    }

    void addField(final FieldDefinition field) {
        Objects.requireNonNull(field, 'Field definition must not be null')
        if (fields.putIfAbsent(field.name, field) != null) {
            throw new IllegalArgumentException("Duplicate field ${fullName}.${field.name}")
        }
    }

    void replaceOrAddField(final FieldDefinition field) {
        Objects.requireNonNull(field, 'Field definition must not be null')
        fields.put(field.name, field)
    }

    void addRelationship(final RelationshipDefinition relationship) {
        Objects.requireNonNull(relationship, 'Relationship definition must not be null')
        if (relationships.putIfAbsent(relationship.name, relationship) != null) {
            throw new IllegalArgumentException("Duplicate relationship ${fullName}.${relationship.name}")
        }
    }

    void replaceOrAddRelationship(final RelationshipDefinition relationship) {
        Objects.requireNonNull(relationship, 'Relationship definition must not be null')
        relationships.put(relationship.name, relationship)
    }

    List<FieldDefinition> getPrimaryKeyFields() {
        new ArrayList<FieldDefinition>(fields.values().findAll { FieldDefinition field -> field.primaryKey })
    }
}
