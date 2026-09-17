# Groovy Math

[![license](http://img.shields.io/badge/license-CC0%201.0%20Universal-blue.svg)](https://github.com/moqui/moqui-math/blob/master/LICENSE.md)

**A Model-Driven, In-Memory Mathematical DSL and Execution Layer for Enterprise JVM Platforms.**

Groovy Math is a declarative, in-memory mathematical model and domain-specific language (DSL) for Groovy and Java. It realizes the semantics of the **[Moqui Math](https://github.com/moqui/moqui-math)** relational metamodel without requiring a running Moqui framework instance or database.

---

## Purpose and Architectural Positioning

### The Challenge in Enterprise Data Science, AI & Engineering
The Python ecosystem (NumPy, PyTorch, JAX, Scikit-learn, HuggingFace) is the undisputed industry standard for **exploratory research, statistical experimentation, and rapid ad-hoc prototyping**. Its vast open-source library collection and interactive notebook workflows empower data scientists to explore ideas with unmatched speed.

However, moving mathematical models from exploratory notebooks into **mission-critical enterprise production, industrial automation, and regulated environments** presents distinct architectural challenges:
* **Procedural Script Coupling**: Traditional data science scripts tightly couple business logic, memory allocation, hardware orchestration, and library dependencies within opaque imperative code.
* **Auditability & Regulatory Compliance**: Under modern regulatory frameworks (such as the **EU AI Act Reg. 2024/1689**, industrial safety standards, and financial audit rules), models driving physical devices or critical decisions must maintain verifiable records of *what the model was, why it changed, its exact formulation, and proof of conformance* across its entire lifecycle.
* **Opaque Data Lakes vs Governed Facts**: Saving unstructured binary weights or workflow execution logs records the *action* or the *blob*, but loses the *mathematical specification* in a typed, inspectable, and governable form.

### How Groovy Math Solves This
Groovy Math does not aim to replace Python's role in exploratory research. Instead, it provides the **enterprise production and governance bridge**:

1. **Model-Driven & Declarative**: The mathematical formulation (tensors, matrices, discrete state-space controls, OpenFOAM CFD meshes, graph topologies, categorical morphisms, optimization constraints) is declared as **structured, typed metadata (`MathMeta`)** rather than arbitrary procedural code.
2. **PLM for Mathematical Models (The Model as a Product)**: Inheriting the philosophy of `moqui-math`, every model, parameter, graph vertex, and transformation has a defined lifecycle, change history, and evidence trail that can be audited, validated, and persisted into enterprise ledgers.
   - Large tensor payloads are referenced versioned via `TensorContent` (SafeTensors, NPY, Zarr, Arrow IPC).
   - Discrete state-space matrices ($A, B, C, D$) and state/control vectors ($x, u$) are stored structured in `TensorElement`.
3. **Multi-Engine Neutrality**: The same declared model can be lowered to different computational backends (**PyTorch/LibTorch, Google JAX/OpenXLA, OpenFOAM CFD, OpenCV, PETSc/TAO, Google OR-Tools, or Apache Jena**) without rewriting business logic.
4. **Off-Heap Java Foreign Function & Memory API (Project Panama)**: Direct C ABI dispatch utilizing off-heap native memory segments (`MemorySegment`) without legacy JNI boilerplate.
5. **Guard-Rail Pre-Validation (`TensorValidator`)**: Validates rank, shape, strides, and buffer byte allocations in Java *before* dispatching across the FFM boundary to prevent buffer overflows and fatal signals.
6. **Concurrent Engine Pool (`PanamaEnginePool`)**: Multi-threaded native session management using `AutoCloseable` (`try-with-resources`).

---

## Architecture

```text
Groovy Math DSL (Dynamic Seed or Type-Safe Fluent)
                    |
                    v
          MathMeta (Typed In-Memory Metamodel)
                    |
                    v
     [Guard-Rail Validator: TensorValidator]
                    |
                    v
     [Concurrent Pool: PanamaEnginePool]
                    |
  +-----------------+-----------------+-----------------+-----------------+-----------------+
  |                 |                 |                 |                 |                 |
  v                 v                 v                 v                 v                 v
LibTorch (C++)   ONNX Runtime     OpenFOAM (C++)   OpenCV (C++)     PETSc / TAO      Google OR-Tools
(Deep Learning)  (Panama FFM)     (FVM CFD Solver) (Vision/Panama) (Quadratic/PDE)   (Linear/GLOP)
  |                 |                                                                       |
  +-- JAX / OpenXLA +-----------------------------------------------------------------------+-- Apache Jena (RDF/OWL/SPARQL)
```

---

## Provider Matrix: Real Status & System Prerequisites

### Provider Status Definitions

* **Production**: Complete, Panama FFM native C++ ABI binding (or JVM in-process pure engine), verified in automated test suites and CI.
* **Preview**: Functional implementation with execution capabilities, but relies on external bridge runtimes (e.g., embedded CPython) or has partial feature coverage.
* **Stub**: Metamodel integration, declared schema verification, and reference implementation in Groovy/JVM, with external native solver backend remaining a stub pending full C++ binary link.

| Provider / Engine | Domain | Integration Type | Status | CI / Verification Task | System Prerequisites |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **LibTorch** | Deep Learning, Tensors, Autograd | Panama FFM (C++ direct) | **Production** | `./gradlew buildLibTorchNative nativeTest` | LibTorch 2.7.1+ C++ distribution (auto-downloaded by Gradle task `downloadLibTorch`) |
| **ONNX Runtime** | Cross-Platform Neural Inference | Panama FFM (C++ direct) | **Production** | `./gradlew buildOnnxNative onnxNativeTest` | `libonnxruntime` (`.so`, `.dylib`, or `.dll`) on system path |
| **OpenCV** | Vision, Filters, Affine Transforms | Panama FFM via embedded CPython (`cv2`) | **Production** | `./gradlew buildOpenCvNative openCvNativeTest` | Python 3 with `numpy` and `opencv-python` / `cv2` |
| **PETSc / TAO** | Bounded Quadratic & PDE Optimization | Panama FFM (C++ direct) | **Production** | `./gradlew buildPetscTaoNative petscTaoNativeTest` | PETSc 3.x real-scalar build + OpenMPI |
| **Google JAX** | Accelerated Linear Algebra | Panama FFM via embedded CPython (`jax.numpy`) | **Preview** | `./gradlew buildJaxNative jaxNativeTest` | Python 3 with `jax`, `jaxlib`, `numpy` |
| **Google OR-Tools** | Linear & Mixed Integer Programming (LP/MIP) | In-process GLOP (`MmtLp`, via OR-Tools Java bindings) | **Production** | `./gradlew test --tests "*OrTools*"` | None (managed automatically by Maven dependencies) |
| **Apache Jena** | Graph & Categorical RDF/OWL/SPARQL | JVM In-Process (Pure Java) | **Production** | `./gradlew test --tests "*Jena*"` | None (managed automatically by Maven dependencies) |
| **OpenFOAM** | Finite Volume Method (FVM) Fluid Dynamics | Reference Groovy FVM solver + Native Panama Stub | **Stub (Native) / Preview (Groovy)** | `./gradlew buildOpenFoamNative openFoamNativeTest` | CMake & Ninja (native stub); zero dependencies for Groovy FVM solver |



---

## Separated Models and Pipeline Runners

Declarations in `examples/` are cleanly decoupled into **declarative model files** and **runtime execution runners**:

| Model File | Execution Runner | Backend Engine |
| :--- | :--- | :--- |
| [`examples/openfoam-cavity.groovy`](examples/openfoam-cavity.groovy) | [`examples/run-openfoam-cavity.groovy`](examples/run-openfoam-cavity.groovy) | **OpenFOAM Finite Volume Method (FVM) CFD** |
| [`examples/matrix-product.groovy`](examples/matrix-product.groovy) | [`examples/run-pytorch-matrix-product.groovy`](examples/run-pytorch-matrix-product.groovy) | **LibTorch Panama C++** |
| [`examples/matrix-product.groovy`](examples/matrix-product.groovy) | [`examples/run-jax-pipeline.groovy`](examples/run-jax-pipeline.groovy) | **LibTorch vs Google JAX Panama** |
| [`examples/opencv-vision-pipeline.groovy`](examples/opencv-vision-pipeline.groovy) | [`examples/run-opencv-pipeline.groovy`](examples/run-opencv-pipeline.groovy) | **OpenCV Panama C++** |
| [`examples/energy-dispatch.groovy`](examples/energy-dispatch.groovy) | [`examples/run-petsctao-energy-dispatch.groovy`](examples/run-petsctao-energy-dispatch.groovy) | **PETSc / TAO BQPIP Panama** |
| [`examples/production-plan.groovy`](examples/production-plan.groovy) | [`examples/run-ortools-production-plan.groovy`](examples/run-ortools-production-plan.groovy) | **Google OR-Tools GLOP** |
| [`examples/jena-knowledge-graph.groovy`](examples/jena-knowledge-graph.groovy) | [`examples/run-jena-graph-sparql.groovy`](examples/run-jena-graph-sparql.groovy) | **Apache Jena RDF / OWL / SPARQL** |
| [`examples/product-catalog-graph.groovy`](examples/product-catalog-graph.groovy) | [`examples/run-jena-product-catalog.groovy`](examples/run-jena-product-catalog.groovy) | **Apache Jena Rules Reasoner & SPARQL** |
| [`examples/matrix-product-plan.groovy`](examples/matrix-product-plan.groovy) | [`examples/run-pytorch-matrix-product.groovy`](examples/run-pytorch-matrix-product.groovy) | **Pure Declarative Mathematical Plan** |
| [`examples/matrix-decomposition-plan.groovy`](examples/matrix-decomposition-plan.groovy) | Standard JVM Test Suite | **Satellite Entities & Decompositions** |

---

## Declarative Mathematical DSL & Fluent API

Groovy Math provides a dual-interface model declaration layer:
1. **Dynamic Model-Driven DSL** (`MathDsl.evaluate` / `MathDsl.math`): Zero-import, schema-inspected declarative syntax matching Moqui relational definitions directly.
2. **Type-Safe Fluent API** (`MathDsl.fluent`): Compile-time safe, IDE auto-completable builder API with static metamodel references and 1:1 entity-level correspondence.

### Metamodel-to-DSL Derivation Rules

The DSL is **strictly derived from the Moqui metamodel** (`MathEntities.xml` and `MathData.xml`). There are no arbitrary or synthetic keywords:

| Concept / Metamodel Entity | DSL Method / Element | Key Operands & Results | Moqui Relational Entity & Relationship |
| :--- | :--- | :--- | :--- |
| **Linear Operator** | `matrix('Id', rows: M, cols: N)` | `data: [...]`, `domainSpace`, `codomainSpace` | `moqui.math.Matrix` |
| **State / Coordinate Vector** | `vector('Id', size: N)` | `data: [...]`, `domainSpace` | `moqui.math.Vector` |
| **Multi-dimensional Array** | `tensor('Id', rank: R, shape: S)` | `data: [...]`, `dataType`, `device` | `moqui.math.Tensor` |
| **General Transformation** | `transformation('Id') { ... }` | `leftMatrix`, `rightMatrix`, `operandVector`, `resultMatrix` | `moqui.math.Transformation` & `moqui.math.TransformationOperand` |
| **Matrix Decomposition** | `matrixDecomposition('Id') { ... }` | `operandMatrix`, `leftMatrix`, `diagMatrix`, `rightMatrix` | `moqui.math.MatrixDecomposition` (shared PK `transformationId`) |
| **Diagonal Extraction** | `diagonalExtraction('Id') { ... }` | `operandMatrix`, `resultVector`, `axisOffset` | `moqui.math.DiagonalExtraction` (shared PK `transformationId`) |
| **Triangular Extraction** | `triangularExtraction('Id') { ... }` | `operandMatrix`, `resultMatrix`, `type` (`Upper`/`Lower`) | `moqui.math.TriangularExtraction` (shared PK `transformationId`) |
| **Band Extraction** | `bandExtraction('Id') { ... }` | `operandMatrix`, `resultMatrix`, `lowerBand`, `upperBand` | `moqui.math.BandExtraction` (shared PK `transformationId`) |
| **Submatrix Block** | `blockMatrixExtraction('Id') { ... }` | `operandMatrix`, `resultMatrix`, `startRowBlock`, `endRowBlock` | `moqui.math.BlockMatrixExtraction` (shared PK `transformationId`) |
| **Tensor Slice** | `tensorSlice('Id') { ... }` | `operandTensor`, `resultTensor`, `slice: [...]` | `moqui.math.TensorSlice` (shared PK `transformationId`) |
| **Tensor Decomposition** | `tensorDecomposition('Id') { ... }` | `sourceTensor`, `coreTensor`, `method`, `factors: [...]` | `moqui.math.TensorDecomposition` (shared PK `transformationId`) |
| **Matrix/Tensor Norm** | `normResult('Id') { ... }` | `operandMatrix`, `domain`, `order`, `normValue` | `moqui.math.NormResult` (shared PK `transformationId`) |
| **Frame Transformation** | `coordinateSystemTransformation('Id')` | `sourceCoordSystem`, `targetCoordSystem`, `matrix` | `moqui.math.CoordinateSystemTransformation` (shared PK `transformationId`) |
| **Algorithmic Def & Lifecycle** | `modelDef('DefId') { ... }` | `name`, `modelType`, `usageContext` | `moqui.math.MathModelDef` (Routing template) |
| **Def Pipeline Sequence** | nested `transformation('Step')` | `sequenceNum`, `stepName` | `moqui.math.MathModelDefPipeline` (Task routing) |
| **Concrete Model Instance** | `model('ModelId') { ... }` | `alias`, `solvingMethod`, `statusId` | `moqui.math.MathModel` |

### Standalone Plans vs Governed Enterprise Models

`MathModelDef` and `MathModel` are completely **optional**. If you only need to declare a mathematical operation or pipeline (such as a matrix multiplication, an SVD decomposition, or an affine coordinate frame change), you can declare and **directly execute** a standalone mathematical plan:

```groovy
import org.moqui.math.MathEngine
import org.moqui.math.dsl.*

// 1. Standalone mathematical plan: zero overhead, pure mathematics
MathMeta plan = MathDsl.fluent {
    matrix('A', rows: 2, cols: 3, data: [
        [1.0, 2.0, 3.0],
        [4.0, 5.0, 6.0]
    ])
    matrix('B', rows: 3, cols: 2, data: [
        [7.0, 8.0],
        [9.0, 10.0],
        [11.0, 12.0]
    ])
    matrix('C', rows: 2, cols: 2)

    transformation('MultiplyAB') {
        type TransformationType.MatrixProduct
        leftMatrix 'A'
        rightMatrix 'B'
        resultMatrix 'C'
    }
}

// 2. Direct execution (like Python torch.mm):
// The data declared in the DSL matrices is automatically used as default inputs!
Map<String, Object> result = plan.execute('MultiplyAB')
println "Result C = " + result.C
// Output: [[58.0, 64.0], [139.0, 154.0]]

// Or override inputs dynamically at runtime:
Map<String, Object> dynamicResult = MathEngine.execute(plan, 'MultiplyAB', [
    A: [[2.0, 0.0, 0.0], [0.0, 2.0, 0.0]],
    B: [[1.0, 2.0], [3.0, 4.0], [5.0, 6.0]]
])
```

When enterprise governance, auditability, and lifecycle management (Draft -> Approved -> Production -> Retired) are required, wrap the definition in `modelDef` and `model`:

```groovy
MathMeta enterpriseModel = MathDsl.fluent {
    modelDef('VisionPipelineDef') {
        name 'Vision Processing Pipeline'
        modelType MathModelType.ComputerVision

        model('EdgeDetectionModel') {
            solvingMethod MathModelSolvingMethod.OpenCv

            matrix('InputImage', rows: 8, cols: 8, purpose: MatrixPurpose.Original)
            transformation('BlurStep', type: TransformationType.GaussianBlur)
            transformation('SobelStep', type: TransformationType.Sobel)
        }
    }
}
```

### Satellite Entities and Advanced Linear Algebra

Decompositions and extractions are modeled as 1:1 satellite entities sharing `transformationId` with their parent `Transformation`. The DSL allows declaring them seamlessly:

```groovy
MathDsl.fluent {
    matrix('A', rows: 4, cols: 4, data: [
        [4.0, 1.0, 0.0, 0.0],
        [1.0, 4.0, 1.0, 0.0],
        [0.0, 1.0, 4.0, 1.0],
        [0.0, 0.0, 1.0, 4.0]
    ])

    // SVD Decomposition: A = U * Sigma * V^T
    matrixDecomposition('Svd_A', type: TransformationType.Svd) {
        operandMatrix 'A'
        leftMatrix 'U'
        diagMatrix 'Sigma'
        rightMatrix 'Vt'
        rankApproximation 4
    }

    // Main Diagonal Extraction
    diagonalExtraction('MainDiag_A', axisOffset: 0) {
        operandMatrix 'A'
        resultVector 'd'
    }

    // Triangular Extractions (Upper)
    triangularExtraction('Upper_A', type: TriangularExtractionType.Upper) {
        operandMatrix 'A'
        resultMatrix 'U_tri'
    }

    // Matrix Frobenius Norm
    normResult('NormFrob_A', domain: NormDomain.Matrix, order: NormOrder.MatFrobenius) {
        operandMatrix 'A'
        resultParameter 'frob_norm'
        normValue 8.3666
    }
}
```

---

## Getting Started: Installation and Testing Guide

This guide provides simple, step-by-step instructions so that any Java or Groovy developer can immediately build, test, and run the project.

### 1. System Requirements

* **Java Development Kit (JDK)**: Java 21 (LTS) specifically.
  The build declares a Gradle Java 21 toolchain with `--enable-preview` and `--enable-native-access=ALL-UNNAMED`
  for Project Panama Foreign Function & Memory (FFM) API access. Note that JVM preview bytecode compiled on JDK 21
  is strictly rejected by newer JVM versions (Java 22+), so JDK 21 LTS is strictly required.
  Verify with:
  ```bash
  java -version
  ```
* **Operating System**: Linux (x86_64, aarch64), macOS (Apple Silicon / Intel), or Windows (x86_64).
* **Git**: To clone the repository.

---

### 2. Pure JVM Mode (Zero C++ / Zero Native Compilers Needed)

If you only want to work with the Groovy DSL, metamodels, linear programming (OR-Tools), knowledge graphs (Apache Jena), and the Guard-Rail contract validation, **you do not need any C++ compiler, CMake, or Python installed**.

Run the standard JVM test suite:
```bash
./gradlew test
```
Or run the full JVM validation and checkstyle/coverage verification:
```bash
./gradlew check
```

---

### 3. Native Engines Mode (LibTorch, JAX, OpenFOAM, OpenCV)
 
 Groovy Math acts as the **high-performance native C/C++ frontend for the Groovy community**, fulfilling the same role PyTorch and OpenCV-Python play in the Python ecosystem.
 
 #### Python Wheel & Pip Philosophy for the JVM
 In the Python ecosystem, users run `pip install torch` and receive pre-compiled native binaries (Wheels) for their specific OS and architecture (`linux-x86_64`, `macos-aarch64`, etc.), without needing a C++ compiler.
 
 `groovy-math` brings this exact experience to Groovy and JVM developers:
 * **Automated Native Provisioning (`downloadLibTorch`)**: If the official LibTorch C++ distribution is not present locally (`~/.local/opt/libtorch*` or `$LIBTORCH_HOME`), Gradle automatically downloads the official release for your OS/architecture directly from PyTorch servers.
 * **Platform-Aware Packaging (`packageNativeLibs`)**: Native shared libraries (`.so`, `.dylib`, `.dll`) are bundled inside the JAR under platform-qualified paths (e.g. `native/linux-x86_64/`).
 * **Automatic Runtime Unpacking (`NativeLibraryLoader`)**: At runtime, `NativeLibraryLoader` identifies the host OS/arch, unpacks the matching native library to a temporary cache, and loads it via `System.load()`.
 * **Protected Panama Initialization**: The Java FFM classes (`LibTorchPanama`, `OpenCvPanama`) verify availability with `isAvailable()` before linking symbols, preventing fatal JVM startup crashes (`ExceptionInInitializerError` or `UnsatisfiedLinkError`) if an optional backend is missing.
 
 #### Step 3.1: Build Native Bridges (Optional for C++ Contributors)
 If you are developing or modifying the C++ bridge code:
 * Ensure `cmake` and `ninja` are installed (e.g. `sudo apt install cmake ninja-build` on Ubuntu/Debian).
 * To build the native LibTorch bridge (automatically discovers or downloads LibTorch):
   ```bash
   ./gradlew buildLibTorchNative
   ```
 * To build the native OpenFOAM FVM CFD bridge:
   ```bash
   ./gradlew buildOpenFoamNative
   ```
 * To build the native JAX bridge (uses Python 3 + JAX + NumPy):
   ```bash
   ./gradlew buildJaxNative
   ```

#### Step 3.2: Setting up Isolated Python Virtual Environment for JAX (Optional)
Following the `moqui-jep` pattern, an isolated virtual environment can be created automatically:
```bash
./gradlew setupPythonVenv
./gradlew installPythonRequirements
```

---

### 4. Running Individual Test Suites

You can execute targeted test tasks depending on the component you want to verify:

| Command | Description | What it Tests |
| :--- | :--- | :--- |
| `./gradlew test` | **Pure JVM Suite** | DSL, Metamodels, OR-Tools, Jena RDF/OWL, `TensorValidatorTest` |
| `./gradlew openFoamNativeTest` | **CFD Simulation** | OpenFOAM FVM Navier-Stokes solver, mesh grading, cavity flow |
| `./gradlew nativeTest` | **LibTorch Engine** | LibTorch 2.7.1 FFM, GEMM, LayerNorm, RMSNorm, AdamW, Backward Autograd |
| `./gradlew jaxNativeTest` | **JAX Engine** | JAX / NumPy FFM via embedded CPython, cross-entropy loss |
| `./gradlew dlParityTest` | **Numerical Parity** | Strict numerical parity between LibTorch C++ and JAX C++ ($\Delta < 10^{-4}$) |
| `./gradlew test --tests "org.moqui.math.pool.ConcurrentEnginePoolTest"` | **Multi-Core Concurrency** | 16 parallel threads running concurrent native inference through the engine pool |

---

### 5. Running the Complete Verification in One Command

To run the entire end-to-end regression across all backends:
```bash
./gradlew check nativeTest jaxNativeTest openFoamNativeTest dlParityTest
```

---

### 6. Executing Pipeline Examples

Run real-world demonstration pipelines directly from the command line:

* **Simulate Lid-Driven Cavity CFD Flow (OpenFOAM)**:
  ```bash
  ./gradlew runOpenFoamCavity
  ```
* **Run Deep Learning Vision Pipeline (OpenCV)**:
  ```bash
  ./gradlew runOpenCvPipeline
  ```
* **Run Industrial Linear Programming Production Optimization (Google OR-Tools)**:
  ```bash
  ./gradlew runOrToolsProductionPlan
  ```
* **Run Quadratic Energy Dispatch Optimization (PETSc / TAO)**:
  ```bash
  ./gradlew runPetscTaoEnergyDispatch
  ```
* **Query Semantic Knowledge Graphs via SPARQL 1.1 (Apache Jena)**:
  ```bash
  ./gradlew runJenaGraphSparql
  ./gradlew runJenaProductCatalog
  ```

---

### 7. Packaging for Production Distribution

To generate the self-contained library JAR containing all pre-compiled native engines (`.so` libraries bundled under `native/`):
```bash
./gradlew jar
```
The resulting artifact is created in:
```text
build/libs/groovy-math-0.1.0-SNAPSHOT.jar
```
Downstream applications (such as **Moqui Framework**, Spring Boot, or Quarkus) can simply declare a dependency on this JAR: `NativeLibraryLoader` will automatically extract and load the native libraries at runtime without any manual setup on the target machine.

---

### 8. Troubleshooting Tips

* **Native access**: Project Panama Foreign Function & Memory API requires `--enable-preview` on JDK 21 (preview JEP 442) and `--enable-native-access=ALL-UNNAMED` to load native shared libraries. These flags are already pre-configured in `build.gradle` for all compilation, execution, and test tasks. Using the DSL in pure JVM mode without native execution requires no extra flags.
* **Gradle Daemon / File Locks**: When developing inside IDEs (like VS Code or IntelliJ), background indexing can occasionally lock Gradle files. Pass `--no-daemon` if you need to run in completely isolated environments:
  ```bash
  ./gradlew --no-daemon check
  ```
* **LibTorch Location**: If running `nativeTest` on a custom LibTorch installation, set the environment variable:
  ```bash
  export LIBTORCH_HOME=/path/to/libtorch
  ```
