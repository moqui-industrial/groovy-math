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
final class LiveModelView implements Iterable<ModelValue> {
    private final NamedModelContainer container
    private final Closure<Boolean> predicate
    private final List<Closure<?>> actions = []

    LiveModelView(final NamedModelContainer container, final Closure<Boolean> predicate) {
        this.container = Objects.requireNonNull(container, 'Container must not be null')
        this.predicate = Objects.requireNonNull(predicate, 'Predicate must not be null')
    }

    synchronized LiveModelView configureEach(final Closure<?> action) {
        if (action == null) throw new IllegalArgumentException('Configuration action is required')
        actions.add(action)
        container.providers.each { ModelProvider provider -> apply(provider, action) }
        this
    }

    synchronized void registered(final ModelProvider provider) {
        actions.each { Closure<?> action -> apply(provider, action) }
    }

    private void apply(final ModelProvider provider, final Closure<?> action) {
        ModelValue value = provider.get()
        if (predicate.call(value)) provider.configure(action)
    }

    @Override
    Iterator<ModelValue> iterator() {
        container.providers.collect { ModelProvider provider -> provider.get() }
            .findAll { ModelValue value -> predicate.call(value) }
            .iterator()
    }
}
