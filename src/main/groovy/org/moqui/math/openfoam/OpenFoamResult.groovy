/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.openfoam

import groovy.transform.CompileStatic

@CompileStatic
class OpenFoamResult {
    final String mathModelId
    final String solver
    final String status
    final double finalTime
    final int iterations
    final Map<String, Double> residuals
    final int cellCount
    final Map<String, Object> fields
    final double executionTimeMs

    OpenFoamResult(String mathModelId, String solver, String status, double finalTime,
                   int iterations, Map<String, Double> residuals, int cellCount,
                   Map<String, Object> fields, double executionTimeMs) {
        this.mathModelId = mathModelId
        this.solver = solver
        this.status = status
        this.finalTime = finalTime
        this.iterations = iterations
        this.residuals = residuals ?: Collections.emptyMap()
        this.cellCount = cellCount
        this.fields = fields ?: Collections.emptyMap()
        this.executionTimeMs = executionTimeMs
    }

    @SuppressWarnings('unchecked')
    List<List<Double>> getVelocityField() {
        (List<List<Double>>) fields.get('U')
    }

    @SuppressWarnings('unchecked')
    List<Double> getPressureField() {
        (List<Double>) fields.get('p')
    }

    @Override
    String toString() {
        "OpenFoamResult[model=${mathModelId}, solver=${solver}, status=${status}, " +
        "time=${finalTime}s, iters=${iterations}, cells=${cellCount}, timeMs=${String.format('%.2f', executionTimeMs)}ms]"
    }
}
