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
import groovy.transform.TypeCheckingMode

@CompileStatic
final class NamedModelContainer extends AbstractSet<ModelValue>
        implements NamedModelObjectContainer<ModelValue> {
    final EntityDefinition definition
    private final LinkedHashMap<String, ModelProvider> entries = new LinkedHashMap<>()
    private final List<Closure<?>> futureProviderActions = []
    private final List<Closure<?>> removeActions = []
    private boolean realizeFutureObjects
    private boolean changesDisallowed

    NamedModelContainer(final EntityDefinition definition) {
        this.definition = Objects.requireNonNull(definition, 'Entity definition must not be null')
    }

    @Override
    NamedModelContainer configure(final Closure<?> action) {
        if (action == null) throw new IllegalArgumentException('Configuration action is required')
        Closure<?> configured = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_FIRST
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(this)
        this
    }

    @Override
    synchronized ModelProvider register(final String name) {
        register(name, null)
    }

    @Override
    synchronized ModelProvider register(final String name, final Closure<?> action) {
        assertCanMutate('register')
        if (entries.containsKey(name)) {
            throw new IllegalArgumentException("Duplicate ${definition.fullName} '${name}'")
        }
        ModelProvider provider = new ModelProvider(name, definition)
        if (action != null) provider.configure(action)
        futureProviderActions.each { Closure<?> providerAction -> providerAction.call(provider) }
        entries.put(name, provider)
        if (realizeFutureObjects) provider.get()
        provider
    }

    @Override
    ModelValue create(final String name) {
        create(name, null)
    }

    @Override
    ModelValue create(final String name, final Closure<?> action) {
        register(name, action).get()
    }

    @Override
    synchronized ModelValue maybeCreate(final String name) {
        ModelProvider provider = entries.get(name)
        provider != null ? provider.get() : create(name)
    }

    @Override
    ModelProvider named(final String name) {
        ModelProvider provider = entries.get(name)
        if (provider == null) throw unknown(name)
        provider
    }

    @Override
    ModelProvider named(final String name, final Closure<?> action) {
        named(name).configure(action)
    }

    @Override
    ModelValue findByName(final String name) {
        ModelProvider provider = entries.get(name)
        provider == null ? null : provider.get()
    }

    @Override
    ModelValue getByName(final String name) {
        named(name).get()
    }

    @Override
    ModelValue getAt(final String name) {
        getByName(name)
    }

    @Override
    SortedSet<String> getNames() {
        Collections.unmodifiableSortedSet(new TreeSet<String>(entries.keySet()))
    }

    @Override
    SortedMap<String, ModelValue> getAsMap() {
        TreeMap<String, ModelValue> result = new TreeMap<>()
        names.each { String name -> result.put(name, getByName(name)) }
        Collections.unmodifiableSortedMap(result)
    }

    List<ModelProvider> getProviders() {
        Collections.unmodifiableList(new ArrayList<ModelProvider>(entries.values()))
    }

    @Override
    LiveModelView matching(final Closure<Boolean> predicate) {
        new LiveModelView(this, null, Objects.requireNonNull(predicate, 'Predicate must not be null'))
    }

    @Override
    LiveModelView named(final Closure<Boolean> namePredicate) {
        new LiveModelView(this, Objects.requireNonNull(namePredicate, 'Name predicate must not be null'), null)
    }

    @Override
    <S extends ModelValue> ModelObjectSet<S> withType(final Class<S> type) {
        Closure<Boolean> predicate = { ModelValue value -> type.isInstance(value) }
        (ModelObjectSet<S>) new LiveModelView(this, null, predicate)
    }

    @Override
    synchronized void configureEach(final Closure<?> action) {
        Closure<?> providerAction = { ModelProvider provider -> provider.configure(action) }
        futureProviderActions.add(providerAction)
        providers.each { ModelProvider provider -> providerAction.call(provider) }
    }

    void configureEachFiltered(final Closure<Boolean> namePredicate, final Closure<Boolean> valuePredicate,
                               final Closure<?> action) {
        Closure<?> conditional = { ModelValue value ->
            if ((namePredicate == null || namePredicate.call(value.modelKey)) &&
                (valuePredicate == null || valuePredicate.call(value))) value.configure(action)
        }
        configureEach(conditional)
    }

    @Override
    synchronized void all(final Closure<?> action) {
        allFiltered(null, null, action)
    }

    synchronized void allFiltered(final Closure<Boolean> namePredicate, final Closure<Boolean> valuePredicate,
                                  final Closure<?> action) {
        Closure<?> conditional = { ModelValue value ->
            if ((namePredicate == null || namePredicate.call(value.modelKey)) &&
                (valuePredicate == null || valuePredicate.call(value))) value.configure(action)
        }
        providers.each { ModelProvider provider ->
            ModelValue value = provider.get()
            conditional.call(value)
        }
        Closure<?> providerAction = { ModelProvider provider -> provider.configure(conditional) }
        futureProviderActions.add(providerAction)
        realizeFutureObjects = true
    }

    @Override
    void whenObjectAdded(final Closure<?> action) {
        all(action)
    }

    @Override
    synchronized void whenObjectRemoved(final Closure<?> action) {
        whenObjectRemovedFiltered(null, null, action)
    }

    synchronized void whenObjectRemovedFiltered(final Closure<Boolean> namePredicate,
                                                final Closure<Boolean> valuePredicate,
                                                final Closure<?> action) {
        removeActions.add { ModelValue value ->
            if ((namePredicate == null || namePredicate.call(value.modelKey)) &&
                (valuePredicate == null || valuePredicate.call(value))) value.configure(action)
        }
    }

    @Override
    synchronized void disallowChanges() {
        changesDisallowed = true
    }

    @Override
    synchronized boolean add(final ModelValue value) {
        assertCanMutate('add')
        if (value.definition.fullName != definition.fullName) {
            throw new IllegalArgumentException("Cannot add ${value.definition.fullName} to ${definition.fullName}")
        }
        if (entries.containsKey(value.modelKey)) return false
        ModelProvider provider = new ModelProvider(value.modelKey, definition)
        provider.configure { ModelValue target -> target.putAll(value) }
        futureProviderActions.each { Closure<?> providerAction -> providerAction.call(provider) }
        entries.put(value.modelKey, provider)
        provider.get()
        true
    }

    @Override
    synchronized boolean remove(final Object target) {
        assertCanMutate('remove')
        String name
        if (target instanceof CharSequence) name = target.toString()
        else if (target instanceof ModelValue) name = ((ModelValue) target).modelKey
        else if (target instanceof ModelProvider) name = ((ModelProvider) target).name
        else return false

        ModelProvider provider = entries.remove(name)
        if (provider == null) return false
        if (provider.realized) {
            ModelValue value = provider.get()
            removeActions.each { Closure<?> action -> action.call(value) }
        }
        true
    }

    @Override
    synchronized void clear() {
        assertCanMutate('clear')
        new ArrayList<String>(entries.keySet()).each { String name -> remove(name) }
    }

    @Override
    int size() {
        entries.size()
    }

    @Override
    Iterator<ModelValue> iterator() {
        providers.collect { ModelProvider provider -> provider.get() }.iterator()
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
            ModelProvider provider = entries.get(name)
            return provider != null ? provider.get().configure((Closure<?>) arguments[0]) :
                create(name, (Closure<?>) arguments[0])
        }
        throw new MissingMethodException(name, getClass(), arguments)
    }

    private void assertCanMutate(final String operation) {
        if (changesDisallowed) {
            throw new IllegalStateException("Cannot ${operation} ${definition.fullName}; collection changes are disallowed")
        }
    }

    private NoSuchElementException unknown(final String name) {
        new NoSuchElementException("Unknown ${definition.fullName} '${name}'")
    }
}
