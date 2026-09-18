# PyTorch to Groovy-Math DSL Mapping

This document provides the side-by-side comparison between the native PyTorch Python / LibTorch C++ neural network API and the Groovy-Math DSL.

---

## 1. Native Python Formulation (PyTorch)

```python
# Source: PyTorch Tutorials - Build the Neural Network
# URL: https://pytorch.org/tutorials/beginner/basics/buildmodel_tutorial.html
# Framework: PyTorch 2.4+ / LibTorch (BSD-3-Clause)

import torch
from torch import nn

class NeuralNetwork(nn.Module):
    def __init__(self):
        super().__init__()
        self.linear_relu_stack = nn.Sequential(
            nn.Linear(4, 2),
            nn.ReLU()
        )

    def forward(self, x):
        return self.linear_relu_stack(x)

# Example Execution
model = NeuralNetwork()
x = torch.tensor([[1.0, -2.0, 3.0, -4.0]], dtype=torch.float32)
output = model(x)
```

---

## 2. Groovy-Math DSL Formulation

```groovy
MathModelDef('PyTorchLinearRelu', type: DeepNeuralNetwork, usage: Inference,
    modelName: 'PyTorch Linear Layer and ReLU Activation',
    description: 'Fully-connected linear transformation followed by rectified linear unit') {

    pipeline('LinearStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'LinearAffine', stepName: 'Linear Map', method: LibTorch) {
        Transformation('LinearAffine', type: Affine, name: 'Linear Map')
    }

    pipeline('ReluStep', stepSeqId: '02', sequenceNum: 20,
        transformationId: 'ReluActivation', stepName: 'ReLU Activation', method: LibTorch) {
        Transformation('ReluActivation', type: TensorReLu, name: 'ReLU Activation')
    }

    MathModel('LinearReluModel', alias: 'linear_relu', source: Manual, status: Draft) {
        tensor('Input', [[1.0, -2.0, 3.0, -4.0]], shape: [1, 4], rank: 2, dataType: Float32, device: Cpu)
        tensor('Weight', [[0.5, -0.5, 1.0, 0.0], [0.0, 1.0, -1.0, 0.5]], shape: [2, 4], rank: 2, dataType: Float32, device: Cpu)
        tensor('Bias', [0.1, -0.2], shape: [2], rank: 1, dataType: Float32, device: Cpu)
    }
}
```

---

## 3. Native Concept to Metamodel Entity Mapping

| PyTorch Native Concept | Groovy-Math Metamodel Entity | Description |
|---|---|---|
| `torch.Tensor` | `moqui.math.Tensor` | Multi-dimensional array container with shape, rank, dtype, and device attributes |
| `nn.Linear(in, out)` | `Transformation` (`TtAffine`) + `TransformationOperand` | Affine linear combination ($y = W x + b$) |
| `nn.ReLU()` | `Transformation` (`TtTensorReLu`) | Non-linear element-wise rectified linear activation |
| `nn.Sequential` | `MathModelDefPipeline` (`MathModelDefPipelineStep`) | Ordered computational sequence executed across native providers |
| `torch.float32` | `DataType` (`Float32` / `MmdtFloat32`) | Metamodel floating point scalar data type |
| `device='cpu'` / `'cuda'` | `DeviceType` (`Cpu` / `Gpu` / `DevCpu`) | Target execution hardware device |

---

## 4. Provider Capabilities & Limitations

- **Supported Runtimes**: PyTorch C++ via `LibTorchProvider` (Panama FFM direct native memory binding).
- **Dynamic Graph & Autograd**: Inference forward-pass graph compilation is production-ready; dynamic reverse-mode automatic differentiation training passes are managed through native LibTorch C++ tensors.

---

## 5. Provenance & License

- **Source**: [PyTorch Tutorials - Build the Neural Network](https://pytorch.org/tutorials/beginner/basics/buildmodel_tutorial.html)
- **Framework Version**: PyTorch 2.4+ / LibTorch C++ API
- **Consultation Date**: 2026-09-18
- **Original License**: BSD-3-Clause
- **Notice**: Native code snippets are reproduced in minimal form solely for comparative and educational purposes.
