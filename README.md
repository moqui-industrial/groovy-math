# Groovy Math

Groovy Math is a declarative, in-memory mathematical model and DSL for Groovy.
It carries the semantics of the Moqui Math model without requiring the Moqui
runtime or a database.

The project is not a numerical execution engine. A Groovy DSL builds and
configures `MathMeta`, a typed in-memory metadata set; provider implementations
validate and lower it to platform-specific execution, initially PyTorch/LibTorch.

## Architecture

```text
Groovy Math DSL
      |
      v
MathMeta
      |
      v
MathProvider SPI
      |
      +-- PyTorch provider -> LibTorch
      +-- future local or distributed providers
```

The executor never parses arbitrary Groovy source. Groovy evaluates the DSL in
a controlled JVM environment and produces validated `MathMeta`. A
`MathProvider<P, R>` compiles that metadata directly into its own plan type `P` and
returns its own execution result type `R`.

## Modules and API

The library contains four boundaries:

- `model`: schema definitions, map-backed values, named containers and lazy
  providers;
- `dsl`: seed-style Groovy evaluation and the normalized `MathMeta` root;
- `moqui`: structural loading of `MathEntities.xml`, without a Moqui runtime;
- `spi`: the provider compilation and execution boundary.

The collection contracts follow Gradle's domain-object model: `register` and
`named` are lazy, `create` and `getByName` are eager, filtered collections are
live, and `configureEach` applies to existing and future matching objects.

## DSL

The DSL mirrors Moqui seed-data records, including relationship-driven nested
records, while adding a Gradle-style named object lifecycle. The complete
native-execution example declares a true matrix product and nests its matrices,
transformation and operands:

```groovy
MathModelDef('MatrixAlgebra', modelTypeEnumId: 'MmtLinearAlgebra') {
    MathModel('MatrixProduct', statusId: 'MathModelDraft') {
        data('LeftMatrixData', dataTypeEnumId: 'MmdtMatrix', matrixId: 'A') {
            Matrix('A', rows: 2, cols: 3, purposeEnumId: 'MpOriginal')
        }
        data('ProductStep', dataTypeEnumId: 'MmdtTransformation',
            transformationId: 'MultiplyAB', sequenceNum: 10) {
            Transformation('MultiplyAB', transformationTypeEnumId: 'TtMatrixProduct',
                resultMatrixId: 'C') {
                operands(operandIndex: 0, operandTypeEnumId: 'TotLeftMatrix', operandMatrixId: 'A')
                operands(operandIndex: 1, operandTypeEnumId: 'TotRightMatrix', operandMatrixId: 'B')
            }
        }
    }
}
```

See [`examples/matrix-product.groovy`](examples/matrix-product.groovy) for the
complete declaration and
[`examples/run-pytorch-matrix-product.groovy`](examples/run-pytorch-matrix-product.groovy)
for the explicit PyTorch execution block.

The nested form follows the same convention as Moqui data files: a relationship
short alias names a child record and the schema supplies foreign-key values.

```groovy
Category('AgentEntityModel', categoryTypeEnumId: 'CtSmall') {
    objects('BillingAccountObject', objectName: 'BillingAccount')
    morphisms('BillingAccountSchema',
        sourceObjectId: 'BillingAccountObject',
        targetObjectId: 'BillingAccountObject',
        morphismName: 'schema::BillingAccount') {
        parameters('SourceDialect',
            parameterDefId: 'AgMorphSourceDialectDef',
            symbolicValue: 'entity-definition')
    }
}
```

Here `categoryId` is inherited by `objects` and `morphisms`, while `morphismId`
is inherited by `parameters`. Records are stored in their normal entity
containers; nesting is the declarative authoring form, not a denormalized
runtime representation. A relationship may alternatively be used as a
Gradle-style container:

```groovy
Category('AgentEntityModel') {
    objects {
        CategoryObject('BillingAccountObject') { objectName 'BillingAccount' }
        PartyObject(objectName: 'Party')
    }
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
preserved as schema metadata. They are not executed by the standalone DSL.

The resulting `MathMeta` exposes containers such as:

```groovy
mathMeta.MathModel.named('Classifier')
mathMeta.MathModel.matching { it.mathModelDefId == 'NeuralNetwork' }
    .configureEach { description 'Selected model' }
mathMeta.MathModel.remove('ObsoleteModel')
mathMeta.validate()
mathMeta.freeze() // validate, realize and prohibit structural mutation
```

The container lifecycle follows Gradle's domain-object collection contract:

```groovy
def models = mathMeta.MathModel

models.register('Deferred') { /* lazy */ }
models.create('Immediate') { /* eager */ }
models.named('Deferred')                 // lazy provider
models.getByName('Deferred')             // eager value
models.names                             // does not realize values
models.configureEach { /* lazy action */ }
models.all { /* eager action */ }
models.disallowChanges()                 // freeze metadata structure
models.configure { NamedModel { /* create if missing, like Gradle */ } }
```

The precise mapping and realization rules are documented in
[`docs/gradle-collection-semantics.md`](docs/gradle-collection-semantics.md).

Use `MathDsl.evaluate(schemaDefinition, dslFile)` or
`MathDsl.evaluate(mathEntitiesXml, dslFile)` to read a DSL file. DSL files are
trusted executable Groovy configuration, like `build.gradle`; this API is not a
sandbox for untrusted input.

## Provider SPI

The SPI does not add another public IR:

```groovy
interface MathProvider<P, R> {
    String getProviderId()
    P compile(MathMeta mathMeta)
    R execute(P plan, Map<String, ?> inputs)
}
```

The included LibTorch provider owns `LibTorchPlan` and a narrow JNI bridge. It
currently lowers `TtMatrixProduct`, `TtAffine` and `TtTensorReLu`, accepts Java arrays or reusable
direct buffers, and permits concurrent inference on one immutable native plan.
Other providers may compile the same metadata to Spark, Flink, a remote service or
a distributed runtime without changing the DSL.

## Build

Requires JDK 17 or newer.

```shell
./gradlew check
```

Native LibTorch verification is optional and requires CMake, Ninja and an
unpacked LibTorch distribution:

```shell
JAVA_HOME=/path/to/jdk-17 LIBTORCH_HOME=/path/to/libtorch ./gradlew nativeTest
```

To run the compatibility test against a local checkout of Moqui Math:

```shell
MOQUI_MATH_ENTITIES=/path/to/moqui-math/entity/MathEntities.xml ./gradlew test
```

## License

CC0 1.0 Universal with the additional Grant of Patent License in
[`LICENSE.md`](LICENSE.md), matching Moqui public-domain projects.
