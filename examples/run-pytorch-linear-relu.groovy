/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.MathMeta
import org.moqui.math.libtorch.LibTorchProvider

MathMeta mathMeta = MathDsl.evaluate(new File('examples/pytorch-linear-relu.groovy'))
assert mathMeta.hasEntity('MathModelDef')
assert mathMeta.hasEntity('MathModel')
assert mathMeta.hasEntity('Tensor')
println "PyTorch Linear+ReLU Model successfully loaded: Tensors=${mathMeta.entity('Tensor').size()}, PipelineSteps=${mathMeta.entity('MathModelDefPipeline').size()}"
