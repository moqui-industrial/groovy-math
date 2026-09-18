# Identifier Conventions in groovy-math DSL

This document describes the identifier derivation rules and syntax conventions for entities in the `groovy-math` DSL.

## 1. Top-Level Identifiers
- Top-level definitions and models declared via assignment (e.g. `A = matrix(...)` or `ProductionPlan = MathModel(...)`) inherit the variable name as their primary key `id` and set `symbol: 'A'` (without modifying the human-readable `name` attribute unless explicitly provided).
- Explicit keys can also be supplied as the first positional argument: `MathModel('ProductionPlan', ...)` or `matrix('A', ...)`.

## 2. Nested and Child Identifiers
- Children declared nested within a parent entity (such as `MathModelData`, nested parameters, or pipeline steps) receive derived identifiers in hierarchical dot-notation or prefixed format:
  `<parentId>.<childName>` or `<parentId>_Data_<childKey>`.
- Derived identifiers are validated against Moqui entity id length limits (maximum 250 characters).

## 3. Reference and Symbol Resolution
- Bare identifiers assigned to typed enumeration fields are contextually resolved against the field's target `enumTypeId`.
- Local variables bound in the DSL script are accessible to subsequent declarations.
- Forward references within a model or file are resolved at the completion of script evaluation.
