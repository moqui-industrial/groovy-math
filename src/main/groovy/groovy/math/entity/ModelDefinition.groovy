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

package groovy.math.entity

import groovy.transform.CompileStatic

@CompileStatic
final class ModelDefinition {
    final LinkedHashMap<String, EntityDefinition> entities = new LinkedHashMap<>()
    int extensionCount
    int enumerationCount

    void addEntity(final EntityDefinition entity) {
        Objects.requireNonNull(entity, 'Entity definition must not be null')
        if (entities.putIfAbsent(entity.fullName, entity) != null) {
            throw new IllegalArgumentException("Duplicate entity ${entity.fullName}")
        }
    }

    EntityDefinition entity(final String name) {
        EntityDefinition exact = entities.get(name)
        if (exact != null) return exact

        List<EntityDefinition> matches = new ArrayList<EntityDefinition>(entities.values().findAll {
            EntityDefinition entity -> entity.name == name || entity.shortAlias == name
        })
        if (matches.size() == 1) return matches.first()
        if (matches.empty) throw new IllegalArgumentException("Unknown entity ${name}")
        throw new IllegalArgumentException("Ambiguous entity ${name}: ${matches*.fullName.join(', ')}")
    }
}
