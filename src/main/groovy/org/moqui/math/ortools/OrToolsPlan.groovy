/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.ortools

import com.google.ortools.linearsolver.MPSolver
import com.google.ortools.linearsolver.MPVariable
import groovy.transform.CompileStatic

@CompileStatic
final class OrToolsPlan implements AutoCloseable {
    final String mathModelId
    final String solverId
    final String objectiveSense
    final List<String> variableNames
    final int constraintCount
    private MPSolver solver
    private final List<MPVariable> variables
    private final List<com.google.ortools.linearsolver.MPConstraint> constraints
    private final List<String> constraintNames

    OrToolsPlan(final String mathModelId, final String solverId, final String objectiveSense,
                final List<String> variableNames, final int constraintCount,
                final MPSolver solver, final List<MPVariable> variables,
                final List<com.google.ortools.linearsolver.MPConstraint> constraints = Collections.emptyList(),
                final List<String> constraintNames = Collections.emptyList()) {
        this.mathModelId = mathModelId
        this.solverId = solverId
        this.objectiveSense = objectiveSense
        this.variableNames = Collections.unmodifiableList(new ArrayList<>(variableNames))
        this.constraintCount = constraintCount
        this.solver = Objects.requireNonNull(solver, 'OR-Tools solver must not be null')
        this.variables = Collections.unmodifiableList(new ArrayList<>(variables))
        this.constraints = Collections.unmodifiableList(new ArrayList<>(constraints))
        this.constraintNames = Collections.unmodifiableList(new ArrayList<>(constraintNames))
    }

    synchronized OrToolsResult solve() {
        if (solver == null) throw new IllegalStateException('OR-Tools plan is closed')
        MPSolver.ResultStatus status = solver.solve()
        LinkedHashMap<String, Double> values = new LinkedHashMap<>()
        LinkedHashMap<String, Double> reducedCosts = new LinkedHashMap<>()
        LinkedHashMap<String, Double> duals = new LinkedHashMap<>()
        boolean solved = status == MPSolver.ResultStatus.OPTIMAL || status == MPSolver.ResultStatus.FEASIBLE
        if (solved) {
            for (int index = 0; index < variables.size(); index++) {
                values.put(variableNames[index], variables[index].solutionValue())
                try {
                    reducedCosts.put(variableNames[index], variables[index].reducedCost())
                } catch (Throwable ignored) {
                }
            }
            for (int index = 0; index < constraints.size(); index++) {
                String name = index < constraintNames.size() ? constraintNames[index] : "constraint_${index}"
                try {
                    duals.put(name, constraints[index].dualValue())
                } catch (Throwable ignored) {
                }
            }
        }
        double objectiveValue = solved ? solver.objective().value() : Double.NaN
        new OrToolsResult(mathModelId, status.name(), objectiveValue, values,
            solver.wallTime(), solver.iterations(), reducedCosts, duals)
    }

    @Override
    synchronized void close() {
        if (solver != null) {
            solver.delete()
            solver = null
        }
    }

    synchronized boolean isClosed() { solver == null }
}
