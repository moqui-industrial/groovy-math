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

The project currently supplies the generic model foundation and the first
executable DSL layer:

- schema definitions for fields, relationships and composite identifiers;
- map-backed `ModelValue` objects with schema validation;
- named containers and lazy providers inspired by Gradle DSL semantics;
- live filtered collections and ordered configuration actions;
- a structural reader used to verify compatibility with `MathEntities.xml`.
- closure-based declarations and evaluation of trusted `.groovy` DSL files.

## DSL

The DSL mirrors Moqui seed-data records while adding a Gradle-style named
object lifecycle:

```groovy
MathModelDef('NeuralNetwork') {
    description 'Neural network model definition'
}

MathModel('Classifier') {
    mathModelDefId 'NeuralNetwork'
    statusId 'MathModelDraft'
}

Transformation('DenseProduct') {
    transformationTypeEnumId 'TtMatrixProduct'
}

MathModelData('Classifier.DenseProduct') {
    mathModelId 'Classifier'
    transformationId 'DenseProduct'
}
```

Simple seed records may also be written entirely as named arguments:

```groovy
MathModel(mathModelId: 'Classifier', mathModelDefId: 'NeuralNetwork', statusId: 'MathModelDraft')
```

Each entity name is resolved against the loaded schema. Each field assignment
is validated, required values are checked on realization, and composite primary
keys are preserved.

Default expressions originating in Moqui, such as `ec.user.nowTimestamp`, are
preserved as schema metadata and resolved later by a provider; they are not
executed by the standalone DSL.

The resulting graph exposes containers such as:

```groovy
graph.MathModel.named('Classifier')
graph.MathModel.matching { it.mathModelDefId == 'NeuralNetwork' }
    .configureEach { description 'Selected model' }
graph.MathModel.remove('ObsoleteModel')
graph.validate()
```

The container lifecycle follows Gradle's domain-object collection contract:

```groovy
def models = graph.MathModel

models.register('Deferred') { /* lazy */ }
models.create('Immediate') { /* eager */ }
models.named('Deferred')                 // lazy provider
models.getByName('Deferred')             // eager value
models.names                             // does not realize values
models.configureEach { /* lazy action */ }
models.all { /* eager action */ }
models.disallowChanges()                 // freeze graph structure
```

The precise mapping and realization rules are documented in
[`docs/gradle-collection-semantics.md`](docs/gradle-collection-semantics.md).

Use `MathDsl.evaluate(schemaDefinition, dslFile)` or
`MathDsl.evaluate(mathEntitiesXml, dslFile)` to read a DSL file. DSL files are
trusted executable Groovy configuration, like `build.gradle`; this API is not a
sandbox for untrusted input.

Typed mathematical conveniences, generated enumeration types and the PyTorch
provider are subsequent milestones built on this layer.

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
