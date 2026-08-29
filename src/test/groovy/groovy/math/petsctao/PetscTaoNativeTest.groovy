/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.petsctao

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta

import java.util.concurrent.CompletableFuture

@Tag('petsctao-native')
class PetscTaoNativeTest {
    @Test
    void solvesBoundedQuadraticEnergyDispatchWithNativeTao() {
        MathMeta mathMeta = energyDispatch()
        if (mathMeta == null) return

        PetscTaoResult result = PetscTao.minimize(mathMeta, 'EnergyDispatch')

        assert result.success
        assert Math.abs(result.objectiveValue + 24d) < 1e-5
        assert Math.abs(result.variableValues.GridPower - 4d) < 1e-4
        assert Math.abs(result.variableValues.StoredEnergy - 2d) < 1e-4
    }

    @Test
    void safelyAcceptsCallsFromTwoJvmThreads() {
        MathMeta mathMeta = energyDispatch()
        if (mathMeta == null) return
        PetscTaoPlan firstPlan = new PetscTaoProvider('EnergyDispatch').compile(mathMeta)
        PetscTaoPlan secondPlan = new PetscTaoProvider('EnergyDispatch').compile(mathMeta)
        try {
            CompletableFuture<PetscTaoResult> first = CompletableFuture.supplyAsync {
                firstPlan.solve()
            }
            CompletableFuture<PetscTaoResult> second = CompletableFuture.supplyAsync {
                secondPlan.solve()
            }
            [first.join(), second.join()].each { PetscTaoResult result ->
                assert result.success
                assert Math.abs(result.objectiveValue + 24d) < 1e-5
            }
        } finally {
            firstPlan.close()
            secondPlan.close()
        }
    }

    private static MathMeta energyDispatch() {
        String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
        if (!schemaPath) return null
        MathDsl.evaluate(new File(schemaPath),
            new File(System.getProperty('user.dir'), 'examples/energy-dispatch.groovy'))
    }
}
