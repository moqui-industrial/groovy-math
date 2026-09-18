# ADR 0004 — Representation of Constraint Senses in Linear and Quadratic Programming

- **Status**: Decided (Option B implemented as transitional bridge; Option C proposed upstream)
- **Date**: 2026-09-17
- **Context**: Linear Programming (LP) and Quadratic Programming (QP) solvers (e.g. Google OR-Tools, PETSc/TAO) require specification of constraint senses ($\le, \ge, =$) alongside constraint matrices and right-hand side vectors.

## Evaluation of Options

### Option A: Standard Form Negation
- Senses are forced to $\le$ by negating $\ge$ rows and RHS values ($Ax \ge b \iff -Ax \le -b$).
- **Pros**: Fits existing schema without any extra data structures.
- **Cons**: Audit trails and human inspections see negated numbers rather than the original domain constraints.

### Option B: Constraint Sense Vector (Adopted Bridge)
- A `Vector` entity associated with `MathModelData` (`purpose: MmdpConstraint` or `purposeEnumId: 'MmdpConstraint'`) containing the list of `LogicalOperator` enum codes (`['LE', 'GE', 'EQ']`).
- **Pros**: Fully expressible within the current schema, preserves original positive formulation and enables exact per-row audit.
- **Cons**: Encodes discrete logical operator symbols inside a model vector container.

### Option C: Explicit Metamodel Attribute / `ConstraintRow` (Upstream Proposal)
- Add `operatorEnumId` directly to `MathModelData` or introduce a dedicated `moqui.math.opt.ConstraintRow` entity.
- **Pros**: Cleanest relational modeling, perfectly typed and queryable per constraint row.
- **Cons**: Requires upstream migration in `moqui-framework`/`moqui-math`.

## Decision & Execution
1. Adopt **Option B** as the active bridge implementation in `groovy-math` and configure `OrToolsProvider` to read and respect explicit row operators (`LE`, `GE`, `EQ`).
2. Propose **Option C** upstream for future Moqui-Math releases.
3. Support the algebraic DSL syntax:
   ```groovy
   MathModel('ProductionPlan', solvingMethod: Simplex) {
       Standard = variable(0, 40)
       Premium  = variable(0, 50)
       maximize Standard * 40 + Premium * 30
       subjectTo('MachineA', Standard * 2 + Premium).le(100)
       subjectTo('MachineB', Standard + Premium * 2).le(80)
   }
   ```
