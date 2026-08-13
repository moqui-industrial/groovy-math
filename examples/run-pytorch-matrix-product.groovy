/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.libtorch.LibTorchResult
import org.moqui.math.libtorch.PyTorch

String schemaPath = System.getenv('MOQUI_MATH_ENTITIES')
if (!schemaPath) throw new IllegalStateException('MOQUI_MATH_ENTITIES must point to MathEntities.xml')
File schema = new File(schemaPath)
File declaration = new File('examples/matrix-product.groovy')
MathMeta mathMeta = MathDsl.evaluate(schema, declaration)

LibTorchResult product = PyTorch.execute(mathMeta, 'MatrixProduct') {
    threads intraOp: 1, interOp: 1
    input 'A', [[1, 2, 3], [4, 5, 6]]
}

assert product.tensorName == 'C'
assert product.batchSize == 2
assert product.width == 2
assert product.values.toList() == [58f, 64f, 139f, 154f]
println product.values.toList()
