/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.metamodel

import groovy.transform.CompileStatic

@CompileStatic
class Attribute<E, T> {
    final String name
    final Class<E> entityClass
    final Class<T> attributeType
    final boolean primaryKey
    final boolean required

    Attribute(final String name, final Class<E> entityClass, final Class<T> attributeType,
              final boolean primaryKey = false, final boolean required = false) {
        this.name = name
        this.entityClass = entityClass
        this.attributeType = attributeType
        this.primaryKey = primaryKey
        this.required = required
    }

    @Override
    String toString() {
        "${entityClass.simpleName}.${name}"
    }
}
