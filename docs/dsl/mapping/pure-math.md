# Pure Mathematics & Linear Algebra to Groovy-Math DSL Mapping

This document details the representation of pure declarative mathematical plans, matrix operations, and decompositions in Groovy-Math DSL without wrapping boilerplate.

---

## 1. Minimal Pure Mathematical Plan: Matrix Product ($C = A \times B$)

### Mathematical Formulation
$$A \in \mathbb{R}^{2 \times 3}, \quad B \in \mathbb{R}^{3 \times 2}, \quad C = A \cdot B \in \mathbb{R}^{2 \times 2}$$

### Groovy-Math DSL Formulation
```groovy
A = matrix(rows: 2, cols: 3, purpose: Original)
B = matrix([[7, 8], [9, 10], [11, 12]], purpose: Original)
C = A * B
```

### Metamodel Entity Mapping

| DSL Construct | Metamodel Entity | Description |
|---|---|---|
| `A = matrix(...)` | `moqui.math.Matrix` | Matrix entity with inferred dimensions ($2 \times 3$) and domain/codomain spaces |
| `B = matrix([[...]])` | `moqui.math.Matrix` | Matrix entity with inferred shape ($3 \times 2$) and JSON component array |
| `C = A * B` | `moqui.math.Transformation` (`TtMatrixProduct`) + `TransformationOperand` | Binary matrix product transformation with left and right operands |

---

## 2. Satellite Decompositions & Extractions

### Groovy-Math DSL Formulation
```groovy
MathDsl.fluent {
    matrix('A', [
        [4.0, 1.0, 0.0, 0.0],
        [1.0, 4.0, 1.0, 0.0],
        [0.0, 1.0, 4.0, 1.0],
        [0.0, 0.0, 1.0, 4.0]
    ])

    matrixDecomposition('Svd_A', type: Svd) {
        operandMatrix 'A'
        leftMatrix 'U'
        diagMatrix 'Sigma'
        rightMatrix 'Vt'
        rankApproximation 4
    }

    diagonalExtraction('MainDiag_A', axisOffset: 0) {
        operandMatrix 'A'
        resultVector 'd'
    }

    triangularExtraction('Upper_A', type: Upper, extractionOffset: 0) {
        operandMatrix 'A'
        resultMatrix 'U_tri'
    }

    normResult('NormFrob_A', domain: Matrix, order: MatFrobenius) {
        operandMatrix 'A'
        resultParameter 'frob_norm'
        normValue 8.3666
    }
}
```

### Metamodel Entity Mapping

| DSL Satellite Function | Metamodel Entity | Description |
|---|---|---|
| `matrixDecomposition` | `moqui.math.MatrixDecomposition` | SVD / QR / Cholesky / LU / Eigendecomposition entity |
| `diagonalExtraction` | `moqui.math.DiagonalExtraction` | Main, sub-, or super-diagonal extraction into a `Vector` |
| `triangularExtraction` | `moqui.math.TriangularExtraction` | Upper or Lower triangular extraction |
| `normResult` | `moqui.math.NormResult` | Vector or matrix norm evaluation result |

---

## 3. Provider Capabilities & Limitations

- **Pure Mathematical Plans**: Pure plans do not require `MathModelDef` or `MathModel` metadata when versioning and parameterization are not needed.
- **Algebraic Kernels**: Evaluated via JVM / Apache Commons Math / EJML or lowered to LibTorch / LAPACK.

---

## 4. Provenance & License

- **Source**: Canonical Pure Linear Algebra & Numerical Analysis
- **Framework Version**: Pure Metamodel Specification
- **Consultation Date**: 2026-09-18
- **License**: CC0 1.0 Universal (Public Domain)
