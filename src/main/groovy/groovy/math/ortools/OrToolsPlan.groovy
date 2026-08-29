/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.ortools

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

    OrToolsPlan(final String mathModelId, final String solverId, final String objectiveSense,
                final List<String> variableNames, final int constraintCount,
                final MPSolver solver, final List<MPVariable> variables) {
        this.mathModelId = mathModelId
        this.solverId = solverId
        this.objectiveSense = objectiveSense
        this.variableNames = Collections.unmodifiableList(new ArrayList<>(variableNames))
        this.constraintCount = constraintCount
        this.solver = Objects.requireNonNull(solver, 'OR-Tools solver must not be null')
        this.variables = Collections.unmodifiableList(new ArrayList<>(variables))
    }

    synchronized OrToolsResult solve() {
        if (solver == null) throw new IllegalStateException('OR-Tools plan is closed')
        MPSolver.ResultStatus status = solver.solve()
        LinkedHashMap<String, Double> values = new LinkedHashMap<>()
        boolean solved = status == MPSolver.ResultStatus.OPTIMAL || status == MPSolver.ResultStatus.FEASIBLE
        if (solved) {
            for (int index = 0; index < variables.size(); index++) {
                values.put(variableNames[index], variables[index].solutionValue())
            }
        }
        double objectiveValue = solved ? solver.objective().value() : Double.NaN
        new OrToolsResult(mathModelId, status.name(), objectiveValue, values,
            solver.wallTime(), solver.iterations())
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
