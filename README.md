# Groovy Math

[![license](http://img.shields.io/badge/license-CC0%201.0%20Universal-blue.svg)](https://github.com/moqui/moqui-math/blob/master/LICENSE.md)

**A Model-Driven, In-Memory Mathematical DSL and Execution Layer for Enterprise JVM Platforms.**

Groovy Math is a declarative, in-memory mathematical model and domain-specific language (DSL) for Groovy and Java. It realizes the semantics of the **[Moqui Math](https://github.com/moqui/moqui-math)** relational metamodel without requiring a running Moqui framework instance or database.

---

## Purpose and Architectural Positioning

### The Challenge in Enterprise Data Science and AI
The Python ecosystem (NumPy, PyTorch, JAX, Scikit-learn, HuggingFace) is the undisputed industry standard for **exploratory research, statistical experimentation, and rapid ad-hoc prototyping**. Its vast open-source library collection and interactive notebook workflows empower data scientists to explore ideas with unmatched speed.

However, moving mathematical models from exploratory notebooks into **mission-critical enterprise production, industrial automation, and regulated environments** presents distinct architectural challenges:
* **Procedural Script Coupling**: Traditional data science scripts tightly couple business logic, memory allocation, hardware orchestration, and library dependencies within opaque imperative code.
* **Auditability & Regulatory Compliance**: Under modern regulatory frameworks (such as the **EU AI Act Reg. 2024/1689**, industrial safety standards, and financial audit rules), models driving physical devices or critical decisions must maintain verifiable records of *what the model was, why it changed, its exact formulation, and proof of conformance* across its entire lifecycle.
* **Opaque Data Lakes vs Governed Facts**: Saving unstructured binary weights or workflow execution logs records the *action* or the *blob*, but loses the *mathematical specification* in a typed, inspectable, and governable form.

### How Groovy Math Solves This
Groovy Math does not aim to replace Python's role in exploratory research. Instead, it provides the **enterprise production and governance bridge**:

1. **Model-Driven & Declarative**: The mathematical formulation (tensors, matrices, graph topologies, categorical morphisms, optimization constraints, computer vision pipelines) is declared as **structured, typed metadata (`MathMeta`)** rather than arbitrary procedural code.
2. **PLM for Mathematical Models (The Model as a Product)**: Inheriting the philosophy of `moqui-math`, every model, parameter, graph vertex, and transformation has a defined lifecycle, change history, and evidence trail that can be audited, validated, and persisted into enterprise ledgers.
3. **Multi-Engine Neutrality**: The same declared model can be lowered to different computational backends (**PyTorch/LibTorch, Google JAX/OpenXLA, OpenCV, PETSc/TAO, Google OR-Tools, or Apache Jena**) without rewriting business logic.
4. **Zero-Overhead Java 21 Foreign Function & Memory API (Project Panama)**: Bypasses JNI and Python GIL bottlenecks by utilizing zero-copy off-heap native memory segments (`MemorySegment`) and direct C ABI dispatch.

---

## Core Principles (from Moqui Math)

* **Discrete & Finite Metamodel**: One unified relational foundation that cleanly represents:
  * **Set-Theoretic Structures**: Finite enumerations, records, components, and parameter definitions.
  * **Category Theory**: `CategoryObject`, `Morphism` ($f: A \to B$ contract), `Functor`, and `NaturalTransformation`.
  * **Type Theory**: Type judgments, typing contexts, and mathematical term classifications.
* **Math–Device Duality**: Mathematical intent is decoupled from the execution hardware (CPU, GPU, PLC, edge accelerator).
* **Exact Realization, Numerical Approximation, and Proof**:
  * A `Morphism` defines the abstract specification.
  * A `Transformation` defines the exact algebraic/relational realization.
  * An `ApproximatedFunction` or `ParametricPath` defines the numerical discretization.
  * A `MathModelRun` captures the empirical execution metrics and measured error against tolerances.
* **Neuro-Symbolic Convergence**: Unifies connectionist tensor computation with formal semantic knowledge graphs (RDF, OWL ontologies, and SPARQL 1.1 reasoning via Apache Jena).

---

## Architecture

```text
Groovy Math DSL (Dynamic Seed or Type-Safe Fluent)
                    |
                    v
          MathMeta (Typed In-Memory Metamodel)
                    |
                    v
            MathProvider SPI
                    |
  +-----------------+-----------------+-----------------+-----------------+
  |                 |                 |                 |                 |
  v                 v                 v                 v                 v
LibTorch (C++)   JAX / OpenXLA    OpenCV (C++)     PETSc / TAO      Google OR-Tools
(Tensors/Math)   (Panama XLA)    (Vision/Panama) (Quadratic/PDE)   (Linear/GLOP)
                                      |
                                      +-- Apache Jena (RDF/OWL/SPARQL)
```

---

## DSL Styles: Dynamic & Type-Safe Fluent API

Groovy Math provides two complementary DSL paradigms:

### 1. Dynamic Seed-Style DSL
Mirrors Moqui seed-data records, using relationship-driven nested blocks and Gradle-style object lifecycles:

```groovy
MathModelDef('MatrixAlgebra', modelTypeEnum: MathModelType.LinearAlgebra) {
    MathModel('MatrixProduct', statusId: 'MathModelDraft') {
        data('LeftMatrixData', dataTypeEnum: MathModelDataType.Matrix, matrixId: 'A') {
            Matrix('A', rows: 2, cols: 3, purposeEnum: MatrixPurpose.Original)
        }
        data('ProductStep', dataTypeEnum: MathModelDataType.Transformation, transformationId: 'MultiplyAB') {
            Transformation('MultiplyAB', transformationTypeEnum: TransformationType.MatrixProduct, resultMatrixId: 'C') {
                operands(operandIndex: 0, operandTypeEnum: TransformationOperandType.LeftMatrix, operandMatrixId: 'A')
                operands(operandIndex: 1, operandTypeEnum: TransformationOperandType.RightMatrix, operandMatrixId: 'B')
            }
        }
    }
}
```

### 2. Type-Safe Fluent API (JPA Criteria Metamodel Style)
Provides full compile-time static type checking (`@CompileStatic`), IDE autocompletion, static attributes (`Matrix_`, `Graph_`, `GraphVertex_`), and object handles (`EntityRef<T>`):

```groovy
MathMeta mathMeta = MathDsl.fluent(schemaFile) {
    graph('ResearchLabGraph') {
        name 'AI Research Institute Graph'

        // Strongly-typed vertices stored in EntityRef handles
        def alice = vertex('Alice') {
            label 'Alice Cooper'
            parameter('jobTitle', 'Principal AI Scientist')
        }
        def bob = vertex('Bob') { label 'Bob Martin' }
        def aiDept = vertex('AI_Department') { label 'Neuro-Symbolic Lab' }

        // Connect edges type-safely via EntityRef (zero string IDs)
        connect(alice, aiDept, 'leads')
        connect(bob, aiDept, 'memberOf')
        connect(alice, bob, 'supervises')
    }
}
```

---

## Separated Models and Pipeline Runners

Declarations in `examples/` are cleanly decoupled into **declarative model files** and **runtime execution runners**:

| Model File | Execution Runner | Backend Engine |
| :--- | :--- | :--- |
| [`examples/matrix-product.groovy`](examples/matrix-product.groovy) | [`examples/run-pytorch-matrix-product.groovy`](examples/run-pytorch-matrix-product.groovy) | **LibTorch Panama C++** |
| [`examples/matrix-product.groovy`](examples/matrix-product.groovy) | [`examples/run-jax-pipeline.groovy`](examples/run-jax-pipeline.groovy) | **LibTorch vs Google JAX Panama** |
| [`examples/opencv-vision-pipeline.groovy`](examples/opencv-vision-pipeline.groovy) | [`examples/run-opencv-pipeline.groovy`](examples/run-opencv-pipeline.groovy) | **OpenCV Panama C++** |
| [`examples/energy-dispatch.groovy`](examples/energy-dispatch.groovy) | [`examples/run-petsctao-energy-dispatch.groovy`](examples/run-petsctao-energy-dispatch.groovy) | **PETSc / TAO BQPIP Panama** |
| [`examples/production-plan.groovy`](examples/production-plan.groovy) | [`examples/run-ortools-production-plan.groovy`](examples/run-ortools-production-plan.groovy) | **Google OR-Tools GLOP** |
| [`examples/jena-knowledge-graph.groovy`](examples/jena-knowledge-graph.groovy) | [`examples/run-jena-graph-sparql.groovy`](examples/run-jena-graph-sparql.groovy) | **Apache Jena RDF / OWL / SPARQL** |

---

## Building and Verification

### Prerequisites
* Java 21+ with Panama Foreign Function & Memory API enabled.
* C++ toolchain (GCC/Clang) and CMake (for native backends).

### Running All Tests & Pipeline Examples
```bash
./gradlew check nativeTest petscTaoNativeTest jaxNativeTest openCvNativeTest \
          runJaxPipeline runOpenCvPipeline runOrToolsProductionPlan \
          runPetscTaoEnergyDispatch runJenaGraphSparql
```
