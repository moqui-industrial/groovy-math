# Provider Architecture: Providers as Computational Devices

In Groovy Math, backend engines (LibTorch, OR-Tools, PETSc/TAO, ONNX Runtime, OpenCV, OpenFOAM, Apache Jena) are modeled as **computational devices** that execute declarative mathematical models.

## 1. Core Principles

1. **Declarative Neutrality**: Models are expressed exclusively in terms of the Moqui Math Metamodel (`moqui.math.*`), free from provider-specific APIs or idioms.
2. **Compilation vs. Execution**:
   - `P compile(MathMeta mathMeta)`: Lowers a declarative AST / semantic graph into a provider-owned, native execution plan `P`.
   - `R execute(P plan, Map<String, ?> inputs)`: Evaluates the plan against runtime input tensors or vectors without recompilation.
3. **Provider Capabilities**: Every provider implements `Set<String> capabilities()`, declaring which metamodel entities, solving methods, and transformation types it can execute.

## 2. Supported Core Providers

| Provider | ID | Backend Technology | Primary Domains & Capabilities |
| :--- | :--- | :--- | :--- |
| **LibTorch** | `libtorch` | PyTorch C++ via Panama FFM | Deep learning, dense tensor algebra, convolutions, attention, activations |
| **OR-Tools** | `ortools` | Google OR-Tools C++ / Java | Linear programming (Simplex), Integer/Binary programming, dual values & sensitivity |
| **PETSc / TAO** | `petsc-tao` | PETSc/TAO C API via Panama FFM | Large-scale numerical optimization, bounded quadratic programming (BQPIP) |
| **ONNX Runtime** | `onnx` | ONNX Runtime C API | Portable neural network inference, transformer evaluation |

## 3. Mathematical Conventions

### PETSc/TAO Quadratic Programming
The standard quadratic objective minimized by `PetscTaoProvider` follows the canonical second-order Taylor expansion:

$$\min_{x} \frac{1}{2} x^T H x + c^T x \quad \text{subject to} \quad l \le x \le u$$

- $H$ is the symmetric Hessian matrix (`MmdpHessian`)
- $c$ is the linear cost vector (`MmdpCostVector`)
- $l, u$ are variable lower and upper bounds (`MmdpVarBounds`)
- $x_0$ is the initial search point (`MmdpInitialCondition`)

### OR-Tools Linear & Mixed-Integer Programming
The linear programming model solved by `OrToolsProvider` follows the standard form:

$$\min_{x} \text{ or } \max_{x} \quad c^T x \quad \text{subject to} \quad A x \sim b, \quad l \le x \le u$$

where $\sim \in \{\le, \ge, =\}$ corresponds to `TtLessEqual`, `TtGreaterEqual`, `TtEquality`.
Variables may have continuous (`VdContinuous`), integer (`VdInteger`), or binary (`VdBinary`) domains.
On solution, dual values (shadow prices $y$) and reduced costs ($rc$) are directly accessible on `OrToolsResult`.
