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
import org.moqui.math.model.RelationshipDefinition

@CompileStatic
final class RelationshipBlockBuilder {
    private final MathDslBuilder root
    private final SeedRecord parent
    final RelationshipDefinition relationship

    RelationshipBlockBuilder(final MathDslBuilder root, final SeedRecord parent,
                             final RelationshipDefinition relationship) {
        this.root = root
        this.parent = parent
        this.relationship = relationship
    }

    RelationshipBlockBuilder configure(final Closure<?> action) {
        Closure<?> configured = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_ONLY
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(this)
        this
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String name, final Object rawArguments) {
        EntityDefinition child = root.relatedEntity(relationship)
        if (name == child.name || name == child.fullName) {
            return root.declareNested(child.fullName, rawArguments, parent, relationship)
        }

        List<Object> arguments = normalizeArguments(rawArguments)
        arguments.add(0, name)
        root.declareNested(child.fullName, arguments as Object[], parent, relationship)
    }

    private static List<Object> normalizeArguments(final Object rawArguments) {
        if (rawArguments == null) return []
        if (rawArguments instanceof Object[]) return new ArrayList<Object>(Arrays.asList((Object[]) rawArguments))
        [rawArguments]
    }
}
