/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

// Source: PyTorch Tutorials - Build the Neural Network (nn.Linear & nn.ReLU)
// URL: https://pytorch.org/tutorials/beginner/basics/buildmodel_tutorial.html
// Framework Version: PyTorch 2.4+ / LibTorch C++ | License: BSD-3-Clause

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
