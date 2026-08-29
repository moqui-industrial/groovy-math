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
import groovy.math.entity.RelationshipDefinition

@CompileStatic
@PackageScope
final class DslRelationshipDelegate {
    private final MathDslBuilder root
    private final DslDeclaration parent
    private final RelationshipDefinition relationship

    DslRelationshipDelegate(final MathDslBuilder root, final DslDeclaration parent,
                            final RelationshipDefinition relationship) {
        this.root = root
        this.parent = parent
        this.relationship = relationship
    }

    DslRelationshipDelegate configure(final Closure<?> action) {
        Closure<?> configured = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_ONLY
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(this)
        this
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String name, final Object rawArguments) {
        EntityDefinition child = root.mathMeta.definition.entity(relationship.relatedEntityName)
        if (name == child.name || name == child.fullName) {
            return root.declareNested(child.fullName, rawArguments, parent, relationship)
        }
        List<Object> arguments = normalizeArguments(rawArguments)
        arguments.add(0, name)
        root.declareNested(child.fullName, arguments as Object[], parent, relationship)
    }

    private static List<Object> normalizeArguments(final Object rawArguments) {
        if (rawArguments == null) return []
        if (rawArguments instanceof Object[]) {
            return new ArrayList<Object>(Arrays.asList((Object[]) rawArguments))
        }
        [rawArguments]
    }
}
