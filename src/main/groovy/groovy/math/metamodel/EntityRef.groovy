/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.metamodel

import groovy.transform.CompileStatic

@CompileStatic
class EntityRef<T> {
    final String id
    final Class<T> entityType
    final Map<String, Object> attributes = new LinkedHashMap<>()

    EntityRef(final String id, final Class<T> entityType, final Map<String, Object> attributes = null) {
        this.id = Objects.requireNonNull(id, 'Entity ID must not be null')
        this.entityType = Objects.requireNonNull(entityType, 'Entity type must not be null')
        if (attributes) this.attributes.putAll(attributes)
    }

    String getId() {
        id
    }

    @Override
    String toString() {
        id
    }

    @Override
    boolean equals(Object o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.getClass()) return false
        EntityRef<?> entityRef = (EntityRef<?>) o
        return id == entityRef.id && entityType == entityRef.entityType
    }

    @Override
    int hashCode() {
        return Objects.hash(id, entityType)
    }
}
