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
}
