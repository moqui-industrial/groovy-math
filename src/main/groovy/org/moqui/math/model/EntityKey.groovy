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
final class EntityKey implements Serializable {
    final String entityName
    final Map<String, Object> fields

    EntityKey(final String entityName, final Map<String, ?> fields) {
        if (!entityName) throw new IllegalArgumentException('Entity name must not be empty')
        if (!fields) throw new IllegalArgumentException('Entity key fields must not be empty')
        this.entityName = entityName
        this.fields = Collections.unmodifiableMap(new LinkedHashMap<String, Object>(fields))
    }

    @Override
    boolean equals(final Object other) {
        this.is(other) || other instanceof EntityKey &&
            entityName == ((EntityKey) other).entityName && fields == ((EntityKey) other).fields
    }

    @Override
    int hashCode() {
        31 * entityName.hashCode() + fields.hashCode()
    }

    @Override
    String toString() {
        "${entityName}${fields}"
    }
}
