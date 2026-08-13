# LibTorch provider mapping

The file [`examples/libtorch-mlp.groovy`](../examples/libtorch-mlp.groovy)
declares a complete two-layer feed-forward inference graph using only Moqui Math
objects. It is the acceptance example for the first native PyTorch provider.
[`examples/libtorch-mlp-plan.cpp`](../examples/libtorch-mlp-plan.cpp) shows the
small LibTorch module that this particular graph is expected to produce.

## Compilation boundary

`MathProvider<P, R>` deliberately leaves `P` and `R` to the provider. Groovy
Math therefore does not impose JSON, an additional graph schema or a universal
execution-plan format:

```groovy
final class LibTorchProvider
        implements MathProvider<LibTorchPlan, LibTorchResult> {
    LibTorchPlan compile(MathGraph graph) { /* provider-specific lowering */ }
    LibTorchResult execute(LibTorchPlan plan, Map<String, ?> inputs) { /* native call */ }
}
```

`compile` must call `graph.freeze()` before retaining references. The resulting
`LibTorchPlan` may contain native handles, provider operation objects or an
immutable list of calls. None of these becomes part of the Groovy Math public
model.

## Example lowering

The example has three ordered transformations:

| Groovy Math declaration | Operand roles | LibTorch operation |
|---|---|---|
| `Dense1 : TtAffine` | left, kernel, bias | `torch::nn::Linear::forward` |
| `HiddenRelu : TtTensorReLu` | single | `torch::relu` |
| `Dense2 : TtAffine` | left, kernel, bias | `torch::nn::Linear::forward` |

The provider resolves roles from `TransformationOperand.operandTypeEnumId`, not
from declaration order alone. `MathModelData.sequenceNum` gives the initial
ordering, while tensor producer/consumer references allow the provider to
validate and topologically order the final plan.

Tensor declarations provide logical shape, layout, purpose and storage. A
provider should bind tensors with `TpModelParams` and `TstSafeTensor` to model
weights, bind the input tensor from the execution request, and allocate result
tensors owned by the native plan.

## Native boundary

The SPI does not prescribe JNI, the Java Foreign Function and Memory API or a
remote transport. For an in-process implementation, keep the Java/native
surface narrow and put a stable C ABI shim in front of LibTorch's C++ API. The
shim should expose lifecycle operations such as compile, execute and release;
the C++ side should own `torch::Tensor` and plan objects. This avoids exposing
LibTorch C++ types or ABI details to Groovy code and permits the bridge mechanism
to change without changing the DSL or provider SPI.
