# Gradle collection semantics

Groovy Math follows the public behavioral contract of Gradle domain-object
collections without depending on the Gradle runtime or copying its internal
implementation. The reference baseline is Gradle commit
`b2fba803641a71f9680b704d0bb88175ff39f5e9`.

## Type mapping

| Gradle contract | Groovy Math contract |
| --- | --- |
| `DomainObjectCollection<T>` | `ModelObjectCollection<T>` |
| `DomainObjectSet<T>` | `ModelObjectSet<T>` |
| `NamedDomainObjectCollection<T>` | `NamedModelObjectCollection<T>` |
| `NamedDomainObjectContainer<T>` | `NamedModelObjectContainer<T>` |
| `NamedDomainObjectProvider<T>` | `ModelProvider` |

## Realization contract

| Operation | Behavior |
| --- | --- |
| `register(name)` | Registers a pending object and returns its provider. |
| `create(name)` | Creates and realizes an object immediately. |
| `named(name)` | Returns the provider without realizing it. |
| `getByName(name)` | Realizes and returns the object. |
| `names` | Returns registered names without realizing objects. |
| `configureEach(action)` | Attaches a lazy ordered action to present and future objects. |
| `all(action)` | Realizes present objects and eagerly observes future additions. |
| `configure(closure)` | Configures the container; a named closure creates an absent object eagerly. |
| `matching(predicate)` | Returns a live filtered view evaluated as values are observed. |
| `named(namePredicate)` | Returns a live name-filtered view without realizing values. |
| `disallowChanges()` | Freezes structure without realizing pending objects. |

A filtered `configureEach` attaches a conditional action to providers and does
not realize them merely to evaluate the predicate.
