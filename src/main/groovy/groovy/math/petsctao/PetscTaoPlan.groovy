/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.petsctao

import groovy.transform.CompileStatic

@CompileStatic
final class PetscTaoPlan implements AutoCloseable {
    final String mathModelId
    final String solverType
    final List<String> variableNames
    final int dimension
    private final PetscTaoBackend backend
    private long handle

    PetscTaoPlan(final String mathModelId, final String solverType,
                 final List<String> variableNames, final PetscTaoBackend backend,
                 final long handle) {
        this.mathModelId = mathModelId
        this.solverType = solverType
        this.variableNames = Collections.unmodifiableList(new ArrayList<>(variableNames))
        this.dimension = variableNames.size()
        this.backend = Objects.requireNonNull(backend, 'PETSc/TAO backend must not be null')
        if (handle == 0L) throw new IllegalArgumentException('PETSc/TAO native plan handle is zero')
        this.handle = handle
    }

    synchronized PetscTaoResult solve() {
        assertOpen()
        PetscTaoNativeResult nativeResult = Objects.requireNonNull(
            backend.solve(handle, dimension), 'PETSc/TAO backend result must not be null')
        if (nativeResult.solution.length != dimension) {
            throw new IllegalStateException(
                "PETSc/TAO returned ${nativeResult.solution.length} values for ${dimension} variables")
        }
        LinkedHashMap<String, Double> values = new LinkedHashMap<>()
        for (int index = 0; index < dimension; index++) {
            values.put(variableNames[index], nativeResult.solution[index])
        }
        new PetscTaoResult(mathModelId, solverType, nativeResult.reasonCode,
            nativeResult.objectiveValue, nativeResult.gradientNorm,
            nativeResult.iterations, values)
    }

    @Override
    synchronized void close() {
        if (handle != 0L) {
            backend.destroy(handle)
            handle = 0L
        }
    }

    synchronized boolean isClosed() { handle == 0L }

    private void assertOpen() {
        if (handle == 0L) throw new IllegalStateException('PETSc/TAO plan is closed')
    }
}
