# ADR 0003 — Overloaded Operators in Groovy Math DSL

- **Status**: Accepted
- **Date**: 2026-09-19
- **Context**: Groovy-Math DSL enables mathematical declarations that mirror algebraic expressions while building the underlying Moqui-Math metamodel (`Transformation`, `TransformationOperand`, etc.). Mathematical operator overloading (`*`, `+`, `-`, `/`, `**`, unary `-`) allows concise syntax in models and pipelines.

## Operator Mapping Table

| Espressione | Operandi | Tipo di trasformazione | Note sullo schema e implementazione |
|---|---|---|---|
| `A * B` | Matrix × Matrix | `TtMatrixProduct` | Presente in `TransformationType` (`MatrixProduct`) |
| `A * v` | Matrix × Vector | `TtAffine` | Mappato su `TtAffine` ($y = A \cdot x + b$ con termine noto $b$ assente / nullo). Produce un `Vector` risultato di dimensione pari alle righe di $A$. |
| `X * Y` | Tensor × Tensor | `TtTensorMul` (elemento per elemento) | Presente in `TransformationType` (`TensorMul`), con NumPy broadcasting |
| `X + Y` | Tensor × Tensor | `TtTensorAdd` | Presente in `TransformationType` (`TensorAdd`), con NumPy broadcasting |
| `X - Y` | Tensor × Tensor | `TtTensorSub` | Presente in `TransformationType` (`TensorSub`), con NumPy broadcasting |
| `X / Y` | Tensor × Tensor | `TtTensorDiv` | Presente in `TransformationType` (`TensorDiv`), con NumPy broadcasting |
| `X ** k` | Tensor × scalare | `TtTensorPow` | Presente in `TransformationType` (`TensorPow`) |
| `-X` | Tensor | `TtTensorNeg` | Presente in `TransformationType` (`TensorNeg`, negazione unaria elemento per elemento) |

## Semantics and Constraints

1. **Schema Integrity**: Every operator mapping must target a real enumeration value in `TransformationType`. If an operation is not directly in the metamodel, the DSL refuses the operation at runtime with an explicit diagnostic error until mapped or approved upstream.
2. **Groovy Language Constraints**:
   - Commutative scalar multiplication `Number * Handle` requires an Extension Module (`META-INF/groovy/org.codehaus.groovy.runtime.ExtensionModule`) extending `java.lang.Number`, which must be included in the sandbox allowlist.
   - Relational operators `<`, `<=`, `==`, `>=`, `>` cannot be overloaded for AST transformation without breaking Groovy's hardwired `Comparable`/`equals` semantics. Therefore, optimization constraints use explicit builder methods (`.le()`, `.ge()`, `.eq()`).
3. **Invalid Combinations**: Unsupported operand combinations (e.g., Matrix + Vector, Tensor * String) throw an `IllegalArgumentException` listing the valid operator combinations for the given operand types.
4. **Structural Equivalence**: Any operator expression `A * B` produces a `Transformation` record structurally identical to `matrixProduct(A, B)`.

