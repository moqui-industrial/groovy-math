/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.ortools

import groovy.transform.CompileStatic

@CompileStatic
final class OrToolsResult {
    final String mathModelId
    final String status
    final double objectiveValue
    final Map<String, Double> variableValues
    final long wallTimeMillis
    final long iterations

    OrToolsResult(final String mathModelId, final String status, final double objectiveValue,
                  final Map<String, Double> variableValues, final long wallTimeMillis,
                  final long iterations) {
        this.mathModelId = mathModelId
        this.status = status
        this.objectiveValue = objectiveValue
        this.variableValues = Collections.unmodifiableMap(new LinkedHashMap<>(variableValues))
        this.wallTimeMillis = wallTimeMillis
        this.iterations = iterations
    }

    boolean isSuccess() { status == 'OPTIMAL' || status == 'FEASIBLE' }
}
