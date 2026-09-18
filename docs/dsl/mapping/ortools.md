# Google OR-Tools to Groovy-Math DSL Mapping

This document provides the side-by-side comparison between the Google OR-Tools C++/Python Linear and Integer Programming API and the Groovy-Math DSL.

---

## 1. Native Python Formulation (Google OR-Tools)

```python
# Source: Google OR-Tools - Solving an LP Problem (GLOP)
# URL: https://developers.google.com/optimization/lp/lp_example
# Framework: Google OR-Tools 9.10+ (Apache-2.0)

from ortools.linear_solver import pywraplp

def main():
    solver = pywraplp.Solver.CreateSolver("GLOP")
    
    # 1. Variables: x >= 0, y >= 0
    x = solver.NumVar(0.0, solver.infinity(), "x")
    y = solver.NumVar(0.0, solver.infinity(), "y")
    
    # 2. Constraints:
    #    x + 2*y <= 14
    #    3*x - y >= 0
    #    x - y <= 2
    solver.Add(x + 2 * y <= 14.0)
    solver.Add(3 * x - y >= 0.0)
    solver.Add(x - y <= 2.0)
    
    # 3. Objective: Maximize 3*x + 4*y
    solver.Maximize(3 * x + 4 * y)
    
    status = solver.Solve()
    # Optimal Solution: objective = 34.0, x = 6.0, y = 4.0
```

---

## 2. Groovy-Math DSL Formulation

```groovy
MathModelDef('OrToolsLinearProgram', type: LinearProgram, usage: Optimisation,
    modelName: 'OR-Tools Linear Programming Example',
    description: 'Linear programming with GLOP solver based on Google OR-Tools guide') {

    pipeline('SolveStep', stepSeqId: '01', sequenceNum: 1, stepName: 'GlopSolve', method: Simplex)

    MathModel('LpModel', alias: 'ortools_lp', source: Manual,
        description: 'Maximize 3x + 4y under linear constraints', status: Draft) {

        x = variable(0, Double.POSITIVE_INFINITY)
        y = variable(0, Double.POSITIVE_INFINITY)

        maximize x * 3 + y * 4

        subjectTo('c0', x + y * 2).le(14)
        subjectTo('c1', x * 3 - y).ge(0)
        subjectTo('c2', x - y).le(2)
    }
}
```

---

## 3. Native Concept to Metamodel Entity Mapping

| OR-Tools Native Concept | Groovy-Math Metamodel Entity | Description |
|---|---|---|
| `solver.NumVar(0, inf, 'x')` | `Vector` (`MmdpDecisionVars`) + `VectorComponent` | Symbolic variable identifier and position in decision vector |
| Variable lower / upper bounds | `Matrix` (`MmdpVarBounds`) | $2 \times N$ matrix of lower and upper variable bounds |
| `solver.Maximize(3*x + 4*y)` | `Vector` (`MmdpCostVector`) + `Parameter` (`ObjectiveSense`) | Cost vector coefficients and optimization sense parameter |
| `solver.Add(x + 2*y <= 14)` | `Transformation` (`TtLessEqual`) + `Matrix` (`MmdpConstraintMatrix`) | Relational transformation entity and row coefficients matrix |
| Constraint RHS ($14, 0, 2$) | `Vector` (`MmdpRhsVector`) | Numerical right-hand side vector |
| Constraint Sense | `Vector` (`MmdpConstraint`) | Auxiliary sense vector for linear solvers (`TtLessEqual`, `TtGreaterEqual`) |
| `Solver.CreateSolver('GLOP')` | `MathModelDefPipeline` (`MmsmSimplex`) | Solver execution step and method in model definition pipeline |

---

## 4. Provider Capabilities & Limitations

- **Supported Solvers**: Google OR-Tools Java API via `MPSolver` (GLOP for LP, SCIP/CBC for MIP).
- **Quadratic Programming**: OR-Tools LP/MIP solvers do not support non-linear / quadratic Hessian formulations (use PETSc/TAO for QP).

---

## 5. Provenance & License

- **Source**: [Google OR-Tools LP Example Guide](https://developers.google.com/optimization/lp/lp_example)
- **Framework Version**: Google OR-Tools 9.10+
- **Consultation Date**: 2026-09-18
- **Original License**: Apache-2.0
- **Notice**: Native code snippets are reproduced in minimal form solely for comparative and educational purposes.
