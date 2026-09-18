/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('PyTorchLinearRelu',
    modelTypeEnum: MathModelType.DeepNeuralNetwork,
    usageContextEnum: MathModelUsageContext.Inference,
    modelName: 'PyTorch Linear Layer and ReLU Activation',
    description: 'Fully-connected linear transformation followed by rectified linear unit') {

    pipeline('LinearStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'LinearAffine', stepName: 'Linear Map',
        solvingMethodEnum: MathModelSolvingMethod.LibTorch) {
        Transformation('LinearAffine', transformationTypeEnum: TransformationType.Affine,
            name: 'Linear Map')
    }

    pipeline('ReluStep', stepSeqId: '02', sequenceNum: 20,
        transformationId: 'ReluActivation', stepName: 'ReLU Activation',
        solvingMethodEnum: MathModelSolvingMethod.LibTorch) {
        Transformation('ReluActivation', transformationTypeEnum: TransformationType.TensorReLu,
            name: 'ReLU Activation')
    }

    MathModel('LinearReluModel',
        modelAlias: 'linear_relu',
        sourceEnum: MathModelSource.Manual,
        statusId: 'MathModelDraft') {

        Tensor('Input', shape: '[1, 4]', rank: 2,
            dataTypeEnum: DataType.Float32, deviceTypeEnum: DeviceType.Cpu,
            componentArray: '[[1.0,-2.0,3.0,-4.0]]')

        Tensor('Weight', shape: '[2, 4]', rank: 2,
            dataTypeEnum: DataType.Float32, deviceTypeEnum: DeviceType.Cpu,
            componentArray: '[[0.5,-0.5,1.0,0.0],[0.0,1.0,-1.0,0.5]]')

        Tensor('Bias', shape: '[2]', rank: 1,
            dataTypeEnum: DataType.Float32, deviceTypeEnum: DeviceType.Cpu,
            componentArray: '[0.1,-0.2]')
    }
}
