/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: TransformationOperandType
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum TransformationOperandType implements DslEnumValue {
    Left('TotLeft', '', 'Left / First Operand', ''),
    Right('TotRight', '', 'Right / Second Operand', ''),
    Kernel('TotKernel', '', 'Convolution / Correlation Kernel / Filter / Bias', ''),
    Bias('TotBias', '', 'Bias / Translation Vector (affine map)', ''),
    Single('TotSingle', '', 'Single operand', ''),
    Nth('TotNth', '', 'Nth Operand', ''),
    Enum('TotEnum', '', 'Enumeration / Enumeration Group Operand', ''),
    Matrix('TotMatrix', '', 'Generic matrix operand (type marker)', ''),
    Vector('TotVector', '', 'Generic vector operand (type marker)', ''),
    Tensor('TotTensor', '', 'Generic tensor operand (type marker)', ''),
    Scalar('TotScalar', '', 'Scalar / hyper-parameter operand', ''),
    Transformation('TotTransformation', '', 'Nested Transformation Operand', ''),
    Parameter('TotParameter', '', 'Scalar, Symbolic or Typed Parameter Operand', ''),
    LeftMatrix('TotLeftMatrix', '', 'Left / first operand - Matrix', 'TotLeft'),
    RightMatrix('TotRightMatrix', '', 'Right / second operand - Matrix', 'TotRight'),
    KernelMatrix('TotKernelMatrix', '', 'Kernel / filter - Matrix', 'TotKernel'),
    BiasMatrix('TotBiasMatrix', '', 'Bias term - Matrix', 'TotBias'),
    LeftVector('TotLeftVector', '', 'Left / first operand - Vector', 'TotLeft'),
    RightVector('TotRightVector', '', 'Right / second operand - Vector', 'TotRight'),
    KernelVector('TotKernelVector', '', 'Kernel / filter - Vector', 'TotKernel'),
    BiasVector('TotBiasVector', '', 'Bias term - Vector', 'TotBias'),
    LeftTensor('TotLeftTensor', '', 'Left / first operand – Tensor', 'TotLeft'),
    RightTensor('TotRightTensor', '', 'Right / second operand – Tensor', 'TotRight'),
    KernelTensor('TotKernelTensor', '', 'Kernel / filter – Tensor', 'TotKernel'),
    BiasTensor('TotBiasTensor', '', 'Bias term - Tensor', 'TotBias'),
    EnumValue('TotEnumValue', '', 'Enumeration value operand (configuration, mode, etc.)', 'TotEnum');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    TransformationOperandType(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static TransformationOperandType fromId(final String id) {
        if (id == null) return null
        for (TransformationOperandType val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static TransformationOperandType fromCode(final String code) {
        if (code == null) return null
        for (TransformationOperandType val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }

    static TransformationOperandType fromName(final String name) {
        if (name == null) return null
        for (TransformationOperandType val : values()) {
            if (val.name().equalsIgnoreCase(name) || val.id.equalsIgnoreCase(name) || (val.enumCode != null && val.enumCode.equalsIgnoreCase(name))) return val
        }
        if ('LeftFirstOperand'.equalsIgnoreCase(name)) return Left
        if ('RightSecondOperand'.equalsIgnoreCase(name)) return Right
        if ('ConvolutionCorrelationKernelFilterBias'.equalsIgnoreCase(name)) return Kernel
        if ('BiasTranslationVector'.equalsIgnoreCase(name)) return Bias
        if ('SingleOperand'.equalsIgnoreCase(name)) return Single
        if ('NthOperand'.equalsIgnoreCase(name)) return Nth
        if ('EnumerationEnumerationGroupOperand'.equalsIgnoreCase(name)) return Enum
        if ('GenericMatrixOperand'.equalsIgnoreCase(name)) return Matrix
        if ('GenericVectorOperand'.equalsIgnoreCase(name)) return Vector
        if ('GenericTensorOperand'.equalsIgnoreCase(name)) return Tensor
        if ('ScalarHyperParameterOperand'.equalsIgnoreCase(name)) return Scalar
        if ('NestedTransformationOperand'.equalsIgnoreCase(name)) return Transformation
        if ('ScalarSymbolicOrTypedParameterOperand'.equalsIgnoreCase(name)) return Parameter
        if ('LeftFirstOperandMatrix'.equalsIgnoreCase(name)) return LeftMatrix
        if ('RightSecondOperandMatrix'.equalsIgnoreCase(name)) return RightMatrix
        if ('KernelFilterMatrix'.equalsIgnoreCase(name)) return KernelMatrix
        if ('BiasTermMatrix'.equalsIgnoreCase(name)) return BiasMatrix
        if ('LeftFirstOperandVector'.equalsIgnoreCase(name)) return LeftVector
        if ('RightSecondOperandVector'.equalsIgnoreCase(name)) return RightVector
        if ('KernelFilterVector'.equalsIgnoreCase(name)) return KernelVector
        if ('BiasTermVector'.equalsIgnoreCase(name)) return BiasVector
        if ('LeftFirstOperandTensor'.equalsIgnoreCase(name)) return LeftTensor
        if ('RightSecondOperandTensor'.equalsIgnoreCase(name)) return RightTensor
        if ('KernelFilterTensor'.equalsIgnoreCase(name)) return KernelTensor
        if ('BiasTermTensor'.equalsIgnoreCase(name)) return BiasTensor
        if ('EnumerationValueOperand'.equalsIgnoreCase(name)) return EnumValue
        null
    }
}
