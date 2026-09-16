/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.petsctao

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@CompileStatic
@Tag('petsctao-native')
class PetscTaoPanamaTest {

    @Test
    void testDirectBoundedQuadraticOptimization() {
        PetscTaoPanama panama = PetscTaoPanama.INSTANCE

        // Minimize 1/2 * (2*x1^2 + 4*x2^2) - 8*x1 - 8*x2
        // Bounds: 0 <= x1 <= 5, 0 <= x2 <= 3
        // Optimal: x1 = 4, x2 = 2, objective = -24.0
        double[] hessian = [2.0, 0.0, 0.0, 4.0] as double[]
        double[] linear = [-8.0, -8.0] as double[]
        double[] lower = [0.0, 0.0] as double[]
        double[] upper = [5.0, 3.0] as double[]
        double[] initial = [1.0, 1.0] as double[]

        long handle = panama.createBoundedQuadraticPlan(2, hessian, linear, lower, upper, initial)
        assert handle != 0L

        try {
            PetscTaoNativeResult result = panama.solve(handle, 2)
            assert result != null
            assert result.reasonCode > 0
            assert Math.abs(result.solution[0] - 4.0) < 1e-4
            assert Math.abs(result.solution[1] - 2.0) < 1e-4
            assert Math.abs(result.objectiveValue - (-24.0)) < 1e-4
        } finally {
            panama.destroy(handle)
        }
    }

    @Test
    void testActiveBoundaryConstraints() {
        PetscTaoPanama panama = PetscTaoPanama.INSTANCE

        // Minimize 1/2 * (2*x1^2 + 4*x2^2) - 20*x1 - 20*x2
        // Unconstrained min: x1 = 10, x2 = 5
        // Box Bounds: 0 <= x1 <= 5, 0 <= x2 <= 3
        // Constrained optimal: x1 = 5, x2 = 3
        double[] hessian = [2.0, 0.0, 0.0, 4.0] as double[]
        double[] linear = [-20.0, -20.0] as double[]
        double[] lower = [0.0, 0.0] as double[]
        double[] upper = [5.0, 3.0] as double[]
        double[] initial = [0.0, 0.0] as double[]

        long handle = panama.createBoundedQuadraticPlan(2, hessian, linear, lower, upper, initial)
        assert handle != 0L

        try {
            PetscTaoNativeResult result = panama.solve(handle, 2)
            assert result != null
            assert result.reasonCode > 0
            assert Math.abs(result.solution[0] - 5.0) < 1e-4
            assert Math.abs(result.solution[1] - 3.0) < 1e-4
        } finally {
            panama.destroy(handle)
        }
    }
}
