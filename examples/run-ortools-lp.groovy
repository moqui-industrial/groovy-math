/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.ortools.OrTools
import org.moqui.math.ortools.OrToolsResult

MathMeta mathMeta = MathDsl.evaluate(new File('examples/ortools-lp.groovy'))
OrToolsResult result = OrTools.solve(mathMeta, 'LpModel')

println "OR-Tools LP Solution: Status=${result.status}, Objective=${result.objectiveValue}, Variables=${result.variableValues}"
assert result.status == 'OPTIMAL'
assert Math.abs(result.objectiveValue - 34.0d) < 1e-6
assert Math.abs(result.variableValues.x - 6.0d) < 1e-6
assert Math.abs(result.variableValues.y - 4.0d) < 1e-6
println "SUCCESS: OR-Tools LP verified against official GLOP guide (34.0, x=6.0, y=4.0)!"
