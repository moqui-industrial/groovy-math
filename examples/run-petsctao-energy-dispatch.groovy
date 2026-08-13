/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.petsctao.PetscTao
import org.moqui.math.petsctao.PetscTaoResult

String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
if (!schemaPath) throw new IllegalStateException('MOQUI_MATH_ENTITIES must point to MathEntities.xml')
MathMeta mathMeta = MathDsl.evaluate(
    new File(schemaPath), new File('examples/energy-dispatch.groovy'))

PetscTaoResult result = PetscTao.minimize(mathMeta, 'EnergyDispatch')

assert result.success
assert Math.abs(result.objectiveValue + 24d) < 1e-5
assert Math.abs(result.variableValues.GridPower - 4d) < 1e-4
assert Math.abs(result.variableValues.StoredEnergy - 2d) < 1e-4
println "${result.reason}: objective=${result.objectiveValue}, variables=${result.variableValues}"
