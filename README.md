# Groovy Math

Groovy Math is a declarative, in-memory mathematical model and DSL for Groovy.
It carries the semantics of the Moqui Math model without requiring the Moqui
runtime or a database.

The project is not a numerical execution engine. A Groovy DSL builds and
configures a typed object graph; provider implementations will validate and
lower that graph to platform-specific execution, initially PyTorch/LibTorch.

## Architecture

```text
Groovy Math DSL
      |
      v
in-memory model graph
      |
      v
MathProvider SPI
      |
      +-- PyTorch provider -> LibTorch
      +-- future local or distributed providers
```

The executor never parses arbitrary Groovy source. Groovy evaluates the DSL in
a controlled JVM environment and produces a validated model graph. Providers
consume that graph through the SPI introduced in a later milestone.

## Current milestone

The first milestone supplies the generic model foundation:

- schema definitions for fields, relationships and composite identifiers;
- map-backed `ModelValue` objects with schema validation;
- named containers and lazy providers inspired by Gradle DSL semantics;
- live filtered collections and ordered configuration actions;
- a structural reader used to verify compatibility with `MathEntities.xml`.

The mathematical domain DSL and the PyTorch provider are intentionally separate
milestones built on this foundation.

## Build

Requires JDK 17 or newer.

```shell
./gradlew check
```

To run the compatibility test against a local checkout of Moqui Math:

```shell
MOQUI_MATH_ENTITIES=/path/to/moqui-math/entity/MathEntities.xml ./gradlew test
```

## License

CC0 1.0 Universal with the additional Grant of Patent License in
[`LICENSE.md`](LICENSE.md), matching Moqui public-domain projects.
