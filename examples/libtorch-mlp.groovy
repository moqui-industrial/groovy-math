/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

MathModelDef('LibTorchMlp',
    modelTypeEnumId: 'MmtDlFeedforward',
    usageContextEnumId: 'MmucInference',
    modelName: 'Two-layer multilayer perceptron',
    description: 'Provider-neutral declaration intended for a LibTorch inference provider') {

    MathModel('IrisClassifier',
        modelAlias: 'iris_mlp',
        sourceEnumId: 'MmsManual',
        description: 'Four inputs, eight hidden units and three output logits',
        statusId: 'MathModelDraft') {

        data('IrisInputData', dataTypeEnumId: 'MmdtTensor', tensorId: 'IrisInput', sequenceNum: 0) {
            Tensor('IrisInput', tensorTypeEnumId: 'TtDense', purposeEnumId: 'TpOriginal',
                name: 'input', rank: 2, shape: '[-1,4]', memoryFormatEnumId: 'TmfContig')
        }

        data('Dense1WeightData', dataTypeEnumId: 'MmdtTensor', tensorId: 'Dense1Weight', sequenceNum: 1) {
            Tensor('Dense1Weight', tensorTypeEnumId: 'TtDense', purposeEnumId: 'TpModelParams',
                name: 'dense1.weight', rank: 2, shape: '[8,4]', storageTypeEnumId: 'TstSafeTensor')
        }
        data('Dense1BiasData', dataTypeEnumId: 'MmdtTensor', tensorId: 'Dense1Bias', sequenceNum: 2) {
            Tensor('Dense1Bias', tensorTypeEnumId: 'TtDense', purposeEnumId: 'TpModelParams',
                name: 'dense1.bias', rank: 1, shape: '[8]', storageTypeEnumId: 'TstSafeTensor')
        }
        data('HiddenPreActivationData', dataTypeEnumId: 'MmdtTensor', tensorId: 'HiddenPreActivation',
            sequenceNum: 3) {
            Tensor('HiddenPreActivation', tensorTypeEnumId: 'TtDense',
                name: 'hidden.pre_activation', rank: 2, shape: '[-1,8]')
        }
        data('HiddenActivationData', dataTypeEnumId: 'MmdtTensor', tensorId: 'HiddenActivation',
            sequenceNum: 4) {
            Tensor('HiddenActivation', tensorTypeEnumId: 'TtDense',
                name: 'hidden.activation', rank: 2, shape: '[-1,8]')
        }

        data('Dense2WeightData', dataTypeEnumId: 'MmdtTensor', tensorId: 'Dense2Weight', sequenceNum: 5) {
            Tensor('Dense2Weight', tensorTypeEnumId: 'TtDense', purposeEnumId: 'TpModelParams',
                name: 'dense2.weight', rank: 2, shape: '[3,8]', storageTypeEnumId: 'TstSafeTensor')
        }
        data('Dense2BiasData', dataTypeEnumId: 'MmdtTensor', tensorId: 'Dense2Bias', sequenceNum: 6) {
            Tensor('Dense2Bias', tensorTypeEnumId: 'TtDense', purposeEnumId: 'TpModelParams',
                name: 'dense2.bias', rank: 1, shape: '[3]', storageTypeEnumId: 'TstSafeTensor')
        }
        data('LogitsData', dataTypeEnumId: 'MmdtTensor', tensorId: 'IrisLogits', sequenceNum: 7) {
            Tensor('IrisLogits', tensorTypeEnumId: 'TtDense',
                name: 'logits', rank: 2, shape: '[-1,3]')
        }

        data('Dense1Step', dataTypeEnumId: 'MmdtTransformation', transformationId: 'Dense1', sequenceNum: 10) {
            Transformation('Dense1', transformationTypeEnumId: 'TtAffine',
                name: 'First affine layer', resultTensorId: 'HiddenPreActivation') {
                operands(operandIndex: 0, operandTypeEnumId: 'TotLeftTensor', operandTensorId: 'IrisInput')
                operands(operandIndex: 1, operandTypeEnumId: 'TotKernelTensor', operandTensorId: 'Dense1Weight')
                operands(operandIndex: 2, operandTypeEnumId: 'TotBiasTensor', operandTensorId: 'Dense1Bias')
            }
        }
        data('ReluStep', dataTypeEnumId: 'MmdtTransformation', transformationId: 'HiddenRelu', sequenceNum: 11) {
            Transformation('HiddenRelu', transformationTypeEnumId: 'TtTensorReLu',
                name: 'Hidden ReLU', resultTensorId: 'HiddenActivation') {
                operands(operandIndex: 0, operandTypeEnumId: 'TotSingle', operandTensorId: 'HiddenPreActivation')
            }
        }
        data('Dense2Step', dataTypeEnumId: 'MmdtTransformation', transformationId: 'Dense2', sequenceNum: 12) {
            Transformation('Dense2', transformationTypeEnumId: 'TtAffine',
                name: 'Output affine layer', resultTensorId: 'IrisLogits') {
                operands(operandIndex: 0, operandTypeEnumId: 'TotLeftTensor', operandTensorId: 'HiddenActivation')
                operands(operandIndex: 1, operandTypeEnumId: 'TotKernelTensor', operandTensorId: 'Dense2Weight')
                operands(operandIndex: 2, operandTypeEnumId: 'TotBiasTensor', operandTensorId: 'Dense2Bias')
            }
        }
    }
}
