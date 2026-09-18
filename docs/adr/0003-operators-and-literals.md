# ADR 0003 — Overloaded Operators and Literal Constructors in Groovy-Math DSL

- **Status**: Decided
- **Date**: 2026-09-17
- **Context**: Groovy Math provides mathematical modeling capabilities in pure Groovy DSL. While structural declarations derive directly from the Moqui-Math metamodel schema, mathematical expressions benefit from standard mathematical notation (`A * B`, `X + Y`, `zeros(m, n)`, etc.).

## 1. Overloaded Operators Table

| Operator | Left Operand | Right Operand | Inferred Metamodel Transformation / Operation | Result |
| :--- | :--- | :--- | :--- | :--- |
| `*` | `Matrix` | `Matrix` | `TransformationType.MatrixProduct` (`TtMatrixProduct`) | `Matrix` ($m \times p$) |
| `*` | `Tensor` | `Tensor` | `TransformationType.TensorMul` (Elementwise / Hadamard or Contraction) | `Tensor` |
| `+` | `Tensor` / `Matrix` | `Tensor` / `Matrix` | `TransformationType.TensorAdd` (Elementwise addition) | `Tensor` / `Matrix` |
| `-` | `Tensor` / `Matrix` | `Tensor` / `Matrix` | `TransformationType.TensorSub` (Elementwise subtraction) | `Tensor` / `Matrix` |
| `/` | `Tensor` / `Matrix` | `Tensor` / `Matrix` | `TransformationType.TensorDiv` (Elementwise division) | `Tensor` / `Matrix` |
| `**` | `Tensor` / `Matrix` | `Number` | `TransformationType.TensorPow` (Power operation) | `Tensor` / `Matrix` |
| `*` | `Number` | `Matrix` / `Tensor` | Scalar multiplication (via GDK extension) | `Matrix` / `Tensor` |
| `*` | `Matrix` / `Tensor` | `Number` | Scalar multiplication (via `multiply(Number)`) | `Matrix` / `Tensor` |

### Comparison Operators (`<`, `<=`, `==`, `>=`, `>`)
Groovy hardwires relational operators to `compareTo` / `equals` returning boolean/int. Consequently, constraint relationships in algebraic optimization DSL use explicit methods (`.le(val)`, `.ge(val)`, `.eq(val)`) rather than `<`/`<=`/`==`.

## 2. Literal Constructors

The following convenience literal constructors create purely static data declarations without introducing ad-hoc metamodel entities:
- `eye(int n)`: Identity matrix of size $n \times n$.
- `zeros(int rows, int cols)`: Zero matrix of size $rows \times cols$.
- `ones(int rows, int cols)`: Ones matrix of size $rows \times cols$.
- `diag(List<Number> diagonal)`: Diagonal matrix with specified diagonal elements.

## 3. Conformance and Security
All operator methods and extension modules are registered in the sandbox allowlist (`MathDslSandboxCustomizer`). Any invalid operand combination throws an explicit `IllegalArgumentException` listing valid operations.
