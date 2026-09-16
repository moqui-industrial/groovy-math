/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.jax.Jax
import org.moqui.math.libtorch.LibTorchResult
import org.moqui.math.libtorch.PyTorch

// 1. Locate schema and model DSL
File dslFile = new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy')

println "==================================================================="
println " Moqui-Math: Dual Backend Execution Pipeline (LibTorch vs Google JAX)"
println "==================================================================="
println 'Schema : embedded moqui-math'
println "Model  : ${dslFile.name}"

MathMeta mathMeta = MathDsl.evaluate(dslFile)

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
