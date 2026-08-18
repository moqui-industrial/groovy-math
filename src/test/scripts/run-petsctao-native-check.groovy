/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.petsctao.PetscTao
import org.moqui.math.petsctao.PetscTaoPlan
import org.moqui.math.petsctao.PetscTaoProvider
import org.moqui.math.petsctao.PetscTaoResult

import java.util.concurrent.CompletableFuture

String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
if (!schemaPath) {
    throw new IllegalStateException('MOQUI_MATH_ENTITIES must point to MathEntities.xml')
}

MathMeta mathMeta = MathDsl.evaluate(
    new File(schemaPath),
    new File(System.getProperty('user.dir'), 'examples/energy-dispatch.groovy')
)

PetscTaoResult directResult = PetscTao.minimize(mathMeta, 'EnergyDispatch')
assert directResult.success
assert Math.abs(directResult.objectiveValue + 24d) < 1e-5
assert Math.abs(directResult.variableValues.GridPower - 4d) < 1e-4
assert Math.abs(directResult.variableValues.StoredEnergy - 2d) < 1e-4

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

println 'PETSc/TAO native checks passed.'
