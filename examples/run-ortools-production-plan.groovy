/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.ortools.OrTools
import groovy.math.ortools.OrToolsResult

String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
if (!schemaPath) throw new IllegalStateException('MOQUI_MATH_ENTITIES must point to MathEntities.xml')
MathMeta mathMeta = MathDsl.evaluate(
    new File(schemaPath), new File('examples/production-plan.groovy'))

OrToolsResult result = OrTools.solve(mathMeta, 'ProductionPlan')

assert result.status == 'OPTIMAL'
assert Math.abs(result.objectiveValue - 2200d) < 1e-7
assert Math.abs(result.variableValues.Standard - 40d) < 1e-7
assert Math.abs(result.variableValues.Premium - 20d) < 1e-7
println "${result.status}: objective=${result.objectiveValue}, variables=${result.variableValues}"
