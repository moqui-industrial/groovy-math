# Data Representation Rules in groovy-math DSL

This document describes the canonical rules for representing numerical, symbolic, and external data in the `groovy-math` DSL and Moqui entity metamodel.

## 1. Numerical Arrays
- Dense numeric vectors, matrices, and tensors defined inline via lists (e.g., `[1.0, 2.0, 3.0]` or `[[1, 2], [3, 4]]`) are normalized and serialized into canonical JSON string format stored in the `componentArray` field.
- Homogeneous numeric values are stored compactly in this representation without creating individual row/element entities.

## 2. Symbolic and Component Entities
- When vectors, matrices, or tensors contain symbolic expressions, non-numeric labels, or sparse elements that require relational representation, individual component entities (`VectorComponent`, `MatrixComponent`, `TensorComponent`) are used.
- Mixed numeric and unquoted string literals within a single numerical literal are rejected to avoid silent type corruption.

## 3. External and Heavy Content
- External files, memory-mapped buffers, binary blobs, and remote URIs are not stored as properties on `Matrix`, `Vector`, or `Tensor`.
- Instead, they are referenced via dedicated content entities (`MatrixContent`, `VectorContent`, `TensorContent`, `MathModelDefContent`) with `contentLocation` and `contentTypeEnumId`.

## 4. Computed and Derived Fields
- Fields that represent analytical properties of matrices/vectors (such as `determinant`, `trace`, `rank`, `conditionNumber`, `frobeniusNorm`, `nnz`) are computed fields.
- They must not be set declaratively in DSL definitions; they are populated by providers or Moqui entity-eca rules during execution.
