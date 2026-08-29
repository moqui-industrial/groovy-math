/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.jax.Jax
import groovy.math.libtorch.LibTorchResult
import groovy.math.libtorch.PyTorch

// 1. Locate schema and model DSL
String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?: '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
File schemaFile = new File(schemaPath)
File dslFile = new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy')

println "==================================================================="
println " Moqui-Math: Dual Backend Execution Pipeline (LibTorch vs Google JAX)"
println "==================================================================="
println "Schema : ${schemaFile.absolutePath}"
println "Model  : ${dslFile.name}"

MathMeta mathMeta = MathDsl.evaluate(schemaFile, dslFile)

def inputMatrixA = [[1, 2, 3], [4, 5, 6]]

// 2. Execute on PyTorch (LibTorch C++ via Panama)
long t0 = System.nanoTime()
LibTorchResult torchResult = PyTorch.execute(mathMeta, 'MatrixProduct') {
    input 'A', inputMatrixA
}
long torchTimeNs = System.nanoTime() - t0

// 3. Execute on Google JAX (XLA via Panama)
long t1 = System.nanoTime()
LibTorchResult jaxResult = Jax.execute(mathMeta, 'MatrixProduct') {
    input 'A', inputMatrixA
}
long jaxTimeNs = System.nanoTime() - t1

// 4. Verification and Output
println "\n--- [Backend 1: PyTorch / LibTorch Panama] ---"
println "Result Tensor : ${torchResult.tensorName} (${torchResult.batchSize}x${torchResult.width})"
println "Values        : ${torchResult.values.toList()}"
println "Execution Time: ${String.format('%.2f', torchTimeNs / 1_000_000.0)} ms"

println "\n--- [Backend 2: Google JAX / OpenXLA Panama] ---"
println "Result Tensor : ${jaxResult.tensorName} (${jaxResult.batchSize}x${jaxResult.width})"
println "Values        : ${jaxResult.values.toList()}"
println "Execution Time: ${String.format('%.2f', jaxTimeNs / 1_000_000.0)} ms"

assert torchResult.tensorName == jaxResult.tensorName
assert torchResult.batchSize == jaxResult.batchSize
assert torchResult.width == jaxResult.width
assert torchResult.values.toList() == jaxResult.values.toList()

println "\n==================================================================="
println " SUCCESS: Both LibTorch and Google JAX computed identical tensors!"
println " Matmul Verification: [1,2,3; 4,5,6] x [7,8; 9,10; 11,12]"
println " Result = ${jaxResult.values.toList()}"
println "==================================================================="
