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
import groovy.transform.PackageScope
import groovy.transform.TypeCheckingMode
import org.moqui.math.model.RelationshipDefinition

@CompileStatic
@PackageScope
final class DslRecordDelegate {
    private final MathDslBuilder root
    private final DslDeclaration record

    DslRecordDelegate(final MathDslBuilder root, final DslDeclaration record) {
        this.root = root
        this.record = record
    }

    DslRecordDelegate configure(final Closure<?> action) {
        Closure<?> configured = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_ONLY
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(this)
        this
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String name, final Object rawArguments) {
        Object[] arguments = rawArguments instanceof Object[] ?
            (Object[]) rawArguments : [rawArguments] as Object[]
        if (record.definition.fields.containsKey(name)) {
            if (arguments.length != 1 || arguments[0] instanceof Closure) {
                throw new MissingMethodException(name, getClass(), arguments)
            }
            Object fieldValue = arguments[0]
            record.values.put(name, fieldValue)
            record.provider.configure { value -> value.put(name, fieldValue) }
            return this
        }

        RelationshipDefinition relationship = record.definition.relationships.get(name)
        if (relationship != null) {
            if (arguments.length == 1 && arguments[0] instanceof Closure) {
                return new DslRelationshipDelegate(root, record, relationship)
                    .configure((Closure<?>) arguments[0])
            }
            return root.declareNested(relationship.relatedEntityName, arguments, record, relationship)
        }
        root.declareNested(name, arguments, record)
    }
}
