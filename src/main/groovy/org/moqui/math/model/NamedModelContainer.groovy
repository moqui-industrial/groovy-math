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
final class NamedModelContainer implements Iterable<ModelValue> {
    final EntityDefinition definition
    private final LinkedHashMap<String, ModelProvider> entries = new LinkedHashMap<>()
    private final List<LiveModelView> views = []

    NamedModelContainer(final EntityDefinition definition) {
        this.definition = Objects.requireNonNull(definition, 'Entity definition must not be null')
    }

    synchronized ModelProvider register(final String name, final Closure<?> action = null) {
        if (entries.containsKey(name)) {
            throw new IllegalArgumentException("Duplicate ${definition.fullName} '${name}'")
        }
        ModelProvider provider = new ModelProvider(name, definition, action)
        entries.put(name, provider)
        views.each { LiveModelView view -> view.registered(provider) }
        provider
    }

    ModelProvider named(final String name) {
        ModelProvider provider = entries.get(name)
        if (provider == null) throw new NoSuchElementException("Unknown ${definition.fullName} '${name}'")
        provider
    }

    ModelProvider named(final String name, final Closure<?> action) {
        named(name).configure(action)
    }

    synchronized LiveModelView matching(final Closure<Boolean> predicate) {
        LiveModelView view = new LiveModelView(this, predicate)
        views.add(view)
        view
    }

    synchronized boolean remove(final String name) {
        entries.remove(name) != null
    }

    List<ModelProvider> getProviders() {
        Collections.unmodifiableList(new ArrayList<ModelProvider>(entries.values()))
    }

    int size() {
        entries.size()
    }

    @Override
    Iterator<ModelValue> iterator() {
        providers.collect { ModelProvider provider -> provider.get() }.iterator()
    }
}
