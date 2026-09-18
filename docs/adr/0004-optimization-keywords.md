# ADR 0004 — Algebraic Optimization Keywords and Parameter Bindings

- **Status**: Proposed
- **Date**: 2026-09-18
- **Context**: Mathematical programming formulations (LP, QP, MILP) in standard mathematical modelling practice use algebraic expressions (`maximize c^T x`, `subject to a_i^T x <= b_i`, `x in [l, u]`) rather than manually encoding matrices and vectors. The keywords `variable`, `maximize`, `minimize`, `subjectTo`, `.le()`, `.ge()`, `.eq()`, `initial`, and `domain` do not derive directly from the Moqui-Math relational schema, but compile cleanly into metamodel entities (`Vector`, `VectorComponent`, `Transformation`, `MathModelData`, `Parameter`).

## 1. Syntax Specification

```groovy
Standard = variable(0, 40)
Premium  = variable(0, 50)
maximize Standard * 40 + Premium * 30
subjectTo('MachineA', Standard * 2 + Premium).le(100)
subjectTo('MachineB', Standard + Premium * 2).le(80)
```

## 2. Metamodel Compilation Mapping

1. **Variables**:
   - `var = variable(lowerBound, upperBound, initial = null, domain = 'Continuous')` creates decision variable components.
   - Compiles to:
     - `Vector` with purpose `MmdpDecisionVars` containing `VectorComponent` rows with `sequenceNum` and variable names.
     - `Matrix` with purpose `MmdpVarBounds` (2 x N or N x 2) storing lower and upper bounds.
     - `Vector` with purpose `MmdpVariableDomain` (if domain is declared) storing `VdContinuous`, `VdInteger`, `VdBinary`.
     - `Vector` with purpose `MmdpInitialPoint` (if initial value is provided).

2. **Objective Function**:
   - `maximize expr` or `minimize expr`:
     - Compiles linear terms to `Vector` with purpose `MmdpCostVector`.
     - Compiles quadratic terms $x^T H x$ to `Matrix` with purpose `MmdpHessian`.
     - **PETSc/TAO Quadratic Form Convention**: TAO quadratic objective evaluates $f(x) = \frac{1}{2} x^T H x + c^T x$. The DSL multiplies quadratic terms by 2 when assembling $H$ to preserve canonical mathematical notation $\sum q_{ij} x_i x_j$.
     - Declares `Parameter` with `parameterDefId: 'ObjectiveSense'` (`MAXIMIZE` or `MINIMIZE`).

3. **Constraints**:
   - `subjectTo(name, expr).le(rhs)`, `.ge(rhs)`, `.eq(rhs)`:
     - Each constraint compiles into a `Transformation` with relational type:
       - `TtLessEqual` for `.le()`
       - `TtGreaterEqual` for `.ge()`
       - `TtEquality` for `.eq()`
     - Row coefficients collected in `Matrix` with purpose `MmdpConstraintMatrix`.
     - Right-hand side values collected in `Vector` with purpose `MmdpRhsVector`.
     - Constraint relation types collected in `Vector` with purpose `MmdpConstraint`.

4. **Parameters & UOM Dimensions**:
   - `parameters { ... }` block method names map to `ParameterDef.parameterCode`.
   - Values are stored in `numericValue`, `symbolicValue`, or `parameterEnumId` based on `ParameterDef.parameterTypeEnumId`.
   - `uom:` specifies `parameterUomId` from `moqui.basic.Uom`.
   - Dimension checking verifies compatibility against `ParameterDef.uomTypeEnumId` via `UomDimensionType` / `UomDimTypeGroupMember` and `uomConvert`.
