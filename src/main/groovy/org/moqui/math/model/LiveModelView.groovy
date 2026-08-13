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
import groovy.transform.TypeCheckingMode

@CompileStatic
final class LiveModelView extends AbstractSet<ModelValue> implements NamedModelObjectCollection<ModelValue> {
    private final NamedModelContainer container
    private final Closure<Boolean> namePredicate
    private final Closure<Boolean> valuePredicate

    LiveModelView(final NamedModelContainer container, final Closure<Boolean> namePredicate,
                  final Closure<Boolean> valuePredicate) {
        this.container = Objects.requireNonNull(container, 'Container must not be null')
        this.namePredicate = namePredicate
        this.valuePredicate = valuePredicate
    }

    @Override
    void configureEach(final Closure<?> action) {
        container.configureEachFiltered(namePredicate, valuePredicate, action)
    }

    @Override
    void all(final Closure<?> action) {
        container.allFiltered(namePredicate, valuePredicate, action)
    }

    @Override
    void whenObjectAdded(final Closure<?> action) {
        all(action)
    }

    @Override
    void whenObjectRemoved(final Closure<?> action) {
        container.whenObjectRemovedFiltered(namePredicate, valuePredicate, action)
    }

    @Override
    LiveModelView matching(final Closure<Boolean> predicate) {
        new LiveModelView(container, namePredicate, combine(valuePredicate, predicate))
    }

    @Override
    LiveModelView named(final Closure<Boolean> predicate) {
        new LiveModelView(container, combine(namePredicate, predicate), valuePredicate)
    }

    @Override
    <S extends ModelValue> ModelObjectSet<S> withType(final Class<S> type) {
        Closure<Boolean> typePredicate = { ModelValue value -> type.isInstance(value) }
        (ModelObjectSet<S>) new LiveModelView(container, namePredicate, combine(valuePredicate, typePredicate))
    }

    @Override
    ModelProvider named(final String name) {
        assertNameMatches(name)
        ModelProvider provider = container.named(name)
        if (valuePredicate != null && !valuePredicate.call(provider.get())) throw unknown(name)
        provider
    }

    @Override
    ModelProvider named(final String name, final Closure<?> action) {
        ModelProvider provider = named(name)
        provider.configure(action)
    }

    @Override
    ModelValue findByName(final String name) {
        if (!matchesName(name)) return null
        ModelValue value = container.findByName(name)
        value != null && matchesValue(value) ? value : null
    }

    @Override
    ModelValue getByName(final String name) {
        ModelValue value = findByName(name)
        if (value == null) throw unknown(name)
        value
    }

    @Override
    ModelValue getAt(final String name) {
        getByName(name)
    }

    @Override
    SortedSet<String> getNames() {
        TreeSet<String> result = new TreeSet<>()
        container.names.each { String name ->
            if (matchesName(name) && (valuePredicate == null || matchesValue(container.getByName(name)))) result.add(name)
        }
        Collections.unmodifiableSortedSet(result)
    }

    @Override
    SortedMap<String, ModelValue> getAsMap() {
        TreeMap<String, ModelValue> result = new TreeMap<>()
        names.each { String name -> result.put(name, getByName(name)) }
        Collections.unmodifiableSortedMap(result)
    }

    @Override
    void disallowChanges() {
        container.disallowChanges()
    }

    @Override
    int size() {
        names.size()
    }

    @Override
    Iterator<ModelValue> iterator() {
        asMap.values().iterator()
    }

    @Override
    boolean add(final ModelValue ignored) {
        throw new UnsupportedOperationException('Cannot add directly to a filtered model view')
    }

    @Override
    boolean remove(final Object value) {
        if (value instanceof ModelValue && contains(value)) return container.remove(value)
        false
    }

    @Override
    void clear() {
        new ArrayList<ModelValue>(this).each { ModelValue value -> container.remove(value) }
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object getProperty(final String name) {
        MetaProperty property = getMetaClass().getMetaProperty(name)
        if (property != null) return property.getProperty(this)
        getByName(name)
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String name, final Object rawArguments) {
        Object[] arguments = rawArguments instanceof Object[] ? (Object[]) rawArguments : [rawArguments] as Object[]
        if (arguments.length == 1 && arguments[0] instanceof Closure) {
            return getByName(name).configure((Closure<?>) arguments[0])
        }
        throw new MissingMethodException(name, getClass(), arguments)
    }

    private boolean matchesName(final String name) {
        namePredicate == null || namePredicate.call(name)
    }

    private boolean matchesValue(final ModelValue value) {
        valuePredicate == null || valuePredicate.call(value)
    }

    private void assertNameMatches(final String name) {
        if (!matchesName(name)) throw unknown(name)
    }

    private NoSuchElementException unknown(final String name) {
        new NoSuchElementException("Unknown ${container.definition.fullName} '${name}' in filtered collection")
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private static Closure<Boolean> combine(final Closure<Boolean> left, final Closure<Boolean> right) {
        if (left == null) return right
        if (right == null) return left
        { Object value -> left.call(value) && right.call(value) }
    }
}
