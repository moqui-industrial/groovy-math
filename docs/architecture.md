# Architecture

## Boundary

Groovy Math owns the declarative model, its in-memory lifecycle and the provider
contract. It does not own tensor kernels, device scheduling or distributed
transport.

The DSL source is executable configuration for the JVM-side control plane. Its
resulting `MathMeta` is the provider-neutral executable specification. A
provider may compile this metadata into an internal platform plan, but that plan is
an implementation detail and is not a second public intermediate language.

## Dependency direction

```text
domain DSL -> model core <- provider SPI <- provider implementation
```

The model core has no dependency on Moqui, Gradle, PyTorch, JNI, Panama, Arrow,
Spark, Flink or an in-memory data grid. Integrations depend on the core, never
the reverse.

The provider contract is generic in its plan and result types. There is no
provider-neutral JSON plan after `MathMeta`: the metadata is the declarative
specification, and the next representation belongs to the selected provider.

## Object lifecycle

1. A named object is registered without being created.
2. Configuration actions are collected in declaration order.
3. A provider realizes the object when its value is requested.
4. The schema validates field writes and required fields.
5. The primary identity becomes immutable after realization.
6. Filtered views can configure matching existing and future objects.
7. `MathMeta.freeze()` realizes, validates and locks all containers before a
   provider retains or compiles the metadata.

## Seed-style nesting

Nested DSL declarations are resolved through the relationships loaded from the
Moqui Math schema. A relationship short alias acts as a child factory. The DSL
copies the relationship key map from the parent declaration to the child, then
stores both as independent normalized values in their entity containers.
Primary-key values from enclosing records are also available as context for
deeper descendants with matching fields. This is what allows a grandchild to
inherit, for example, both its immediate `parentMorphismId` and the enclosing
Category's `categoryId`.

If the parent has no declared `many` relationship, the builder can use the
child's `one` relationship back to the parent. This supports records such as a
Morphism with nested Parameter values even though only Parameter declares the
relationship. Ambiguous relationships must be selected with an explicit
relationship block; the builder never guesses between multiple key mappings.

This lifecycle follows the useful part of Gradle's DSL model without embedding
the Gradle runtime or its build-engine concerns.

## Moqui Math compatibility

`MathEntities.xml` remains the source model used to characterize domain names,
fields, relationships, primary keys and enumerations. The runtime representation
is Groovy-native and independent of Moqui Entity Engine persistence.

The compatibility baseline is inspected at Moqui Math commit
`c36705c6dd05745aa145d26fbc3cf987bcbfe39d`:

- 89 entity definitions;
- 4 entity extensions;
- simple and composite primary keys;
- one-to-one and one-to-many relationships;
- 954 enumeration seed records.

`MathViewEntities.xml` defines 43 relational SQL views used by Moqui's Entity
Engine for reporting queries. Groovy Math has no relational execution layer, so
these views are out of scope by the boundary above and are not mirrored as
model or metamodel classes; only `MathEntities.xml` is consumed by
`generateModels`.

Persistence is a future adapter and is not part of object identity or DSL
execution.
