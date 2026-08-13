# PETSc/TAO provider

The PETSc/TAO provider directly lowers a Groovy Math `MmtQp` declaration to a
native TAO bounded quadratic program. It does not introduce a public
intermediate representation.

The first supported problem is:

```text
minimize 0.5 x^T Q x + c^T x
subject to lower <= x <= upper
```

The provider requires one `MathModelData` record for each semantic purpose:

| Purpose | Object | Meaning |
|---|---|---|
| `MmdpDecisionVars` | `Vector` | Unique variable names |
| `MmdpHessian` | `Matrix` | Symmetric quadratic coefficient matrix Q |
| `MmdpCostVector` | `Vector` | Linear coefficient vector c |
| `MmdpVarBounds` | `Matrix` | Two rows: lower and upper bounds |
| `MmdpInitialCondition` | `Vector` | Feasible initial point |

The `MathModelDef` must use `MmtQp`, the `MathModel` must use
`MmsmInteriorPoint`, and its `objectiveSense` parameter must be `MINIMIZE`.
The native implementation selects TAO `BQPIP`. The provider checks dimensions,
finite coefficients, bounds, the initial point and Hessian symmetry. Convexity
remains a mathematical precondition of the declared QP; TAO reports a divergent
termination reason when it cannot solve the supplied problem.

## Native boundary

The bridge is intentionally narrow:

```text
MathMeta -> PetscTaoProvider -> PetscTaoPlan -> JNI -> PETSc Vec/Mat + TAO BQPIP
```

The JVM sends contiguous `double[]` values only while constructing the native
plan. TAO owns its solver objects during a solve, and the Java plan owns the
native plan lifecycle through `AutoCloseable`.

This initial embedded implementation uses `PETSC_COMM_SELF`. It is a real TAO
execution but it is deliberately single-process. Calls into PETSc are serialized
inside the JNI library because an embedded JVM must not assume that an arbitrary
PETSc/MPI build is thread-safe.

A distributed implementation should preserve `PetscTaoProvider` and replace
`PetscTaoBackend` with a launcher or remote executor started under `mpiexec`.
That executor can reconstruct distributed `Vec` and `Mat` objects on
`PETSC_COMM_WORLD`; the Groovy DSL does not change. Running collective MPI work
directly from unrelated JVM threads is outside the contract of this backend.

PETSc is initialized once and is not finalized by the JNI library. Finalizing it
would invalidate PETSc for other components in the same JVM and PETSc/MPI cannot
generally be initialized again safely.

## Build and run

Install a real-scalar PETSc development package with TAO, plus CMake, Ninja,
`pkg-config`, and JDK 17. The build resolves PETSc through its `PETSc.pc` file.

```shell
MOQUI_MATH_ENTITIES=/path/to/moqui-math/entity/MathEntities.xml \
  ./gradlew petscTaoNativeTest

MOQUI_MATH_ENTITIES=/path/to/moqui-math/entity/MathEntities.xml \
  ./gradlew runPetscTaoEnergyDispatch
```

The bridge rejects PETSc builds configured with complex scalars. Supporting
complex QP models requires an explicit DSL and result contract and must not be
silently reduced to real values.

## Parallelism contract

| Backend | Parallel calls from Groovy | Native execution |
|---|---|---|
| Embedded JNI (current) | Safe, but globally serialized | One process on `PETSC_COMM_SELF` |
| Separate MPI executor (future) | Independent jobs may run concurrently | Collective work on `PETSC_COMM_WORLD` |

The global lock is in the native library, not in an individual plan. Two JVM
threads may call different plans safely, but their embedded TAO solves do not
overlap. This is the conservative contract needed for portability across
PETSc/MPI builds. Process-level parallelism is the intended scaling boundary.
