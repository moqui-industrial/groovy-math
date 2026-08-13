# LibTorch provider mapping

The file [`examples/matrix-product.groovy`](../examples/matrix-product.groovy)
declares `C = A x B` entirely as Moqui Math objects. The execution example calls
PyTorch explicitly and produces `[58, 64, 139, 154]`.

## Compilation boundary

`MathProvider<P, R>` deliberately leaves `P` and `R` to the provider. Groovy
Math therefore does not impose JSON, an additional graph schema or a universal
execution-plan format:

```groovy
final class LibTorchProvider
        implements MathProvider<LibTorchPlan, LibTorchResult> {
    LibTorchPlan compile(MathMeta mathMeta) { /* provider-specific lowering */ }
    LibTorchResult execute(LibTorchPlan plan, Map<String, ?> inputs) { /* native call */ }
}
```

`LibTorchProvider.compile` freezes `MathMeta`, selects one `MathModel`, follows
its ordered `MathModelData`, resolves operand roles and emits calls directly to
an opaque native plan. There is no public intermediate representation. The
initial implementation supports `TtMatrixProduct`, `TtAffine` and
`TtTensorReLu` and rejects every
unknown transformation explicitly.

## Example lowering

The example has one declared transformation:

| Groovy Math declaration | Operand roles | LibTorch operation |
|---|---|---|
| `MultiplyAB : TtMatrixProduct` | left matrix, right matrix | `at::matmul(A, B)` |

The provider resolves roles from `TransformationOperand.operandTypeEnumId`, not
from declaration order alone. `MathModelData.sequenceNum` gives the initial
ordering, while tensor producer/consumer references allow the provider to
validate and topologically order the final plan.

The left matrix is supplied at execution time. The right matrix is declared in
`Matrix.componentArray` and becomes a native constant owned by the compiled
plan. Tensor affine/ReLU plans remain supported.

The provider choice is outside the mathematical declaration:

```groovy
LibTorchResult product = PyTorch.execute(mathMeta, 'MatrixProduct') {
    threads intraOp: 1, interOp: 1
    input 'A', [[1, 2, 3], [4, 5, 6]]
}
```

## Native boundary

This provider uses a narrow JNI bridge because Java 17 is the current baseline.
The C++ side owns all `at::Tensor` parameters and the immutable operation list.
Java owns an opaque handle through `LibTorchPlan`, whose read/write lock permits
concurrent execution while making `close()` exclusive. Execution uses
`c10::InferenceMode`.

Two input boundaries are available:

- `float[]` copies Java input into native memory and native output back to Java;
- direct `ByteBuffer` lets LibTorch view the input in place and writes the
  result into reusable off-heap output storage with one native copy, avoiding
  JNI array marshalling and per-call allocation.

## Build and verification

The normal JVM build does not require LibTorch. Native tasks require JDK 17,
CMake, Ninja and an unpacked CPU or accelerator LibTorch distribution:

```shell
export JAVA_HOME=/path/to/jdk-17
export LIBTORCH_HOME=/path/to/libtorch
./gradlew nativeTest
./gradlew benchmarkLibTorch
./gradlew benchmarkLibTorchCompute
```

Set `CMAKE_COMMAND` if CMake is not on `PATH`. `nativeTest` verifies array and
direct-buffer results plus 64 concurrent calls on one plan.

## Parallelism

There are two independent CPU axes:

- caller parallelism: the number of JVM requests executing the same plan;
- intra-op parallelism: the native threads used inside each tensor operation.

Inter-op threads are process-global in PyTorch and may be configured only once,
before inter-op work begins. Intra-op threads may be changed, but changing them
while requests are active is unsupported by this provider. A deployment must
configure threads during startup.

On a 9-core Xeon test machine, the synthetic plan `1024 -> 1024 -> ReLU ->
1024` with batch 64 showed the useful boundary clearly. One caller improved
from about 11.4k samples/s at one intra-op thread to 45.6k at four. At eight
callers, one or two intra-op threads reached about 114k/103k samples/s, while
four and eight intra-op threads fell to about 74k/55k because the product of
caller threads and native threads oversubscribed the CPU. These are environment
measurements, not portable guarantees.

The practical CPU policy is therefore:

- many independent requests: keep intra-op threads at 1 or 2 and scale callers;
- one large batch: use several intra-op threads, normally no more than physical
  cores;
- never multiply both axes up to the core count;
- benchmark real tensor sizes, because the tiny example is dominated by JNI,
  allocation and scheduler overhead.
