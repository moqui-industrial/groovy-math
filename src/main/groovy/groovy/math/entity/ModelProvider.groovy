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
final class ModelProvider {
    private enum State { REGISTERED, REALIZING, REALIZED }

    final String name
    private final EntityDefinition definition
    private final List<Closure<?>> actions = []
    private State state = State.REGISTERED
    private ModelValue value

    ModelProvider(final String name, final EntityDefinition definition, final Closure<?> action = null) {
        if (!name) throw new IllegalArgumentException('Provider name must not be empty')
        this.name = name
        this.definition = Objects.requireNonNull(definition, 'Entity definition must not be null')
        if (action != null) actions.add(action)
    }

    synchronized ModelProvider configure(final Closure<?> action) {
        if (action == null) throw new IllegalArgumentException('Configuration action is required')
        if (state == State.REALIZED) value.configure(action)
        else actions.add(action)
        this
    }

    synchronized ModelValue get() {
        if (state == State.REALIZED) return value
        if (state == State.REALIZING) {
            throw new IllegalStateException("Cyclic realization of ${definition.fullName} '${name}'")
        }

        state = State.REALIZING
        try {
            value = new ModelValue(definition, name)
            actions.each { Closure<?> action -> value.configure(action) }
            value.validate().lockIdentity()
            state = State.REALIZED
            value
        } catch (Throwable failure) {
            value = null
            state = State.REGISTERED
            throw failure
        }
    }

    boolean isRealized() {
        state == State.REALIZED
    }
}
