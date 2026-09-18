/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

Matrix('A', symbol: 'A', rows: 2, cols: 3, purposeEnum: MatrixPurpose.Original,
    domainSpaceEnum: MathSpace.R3, codomainSpaceEnum: MathSpace.R2)

Matrix('B', symbol: 'B', rows: 3, cols: 2, purposeEnum: MatrixPurpose.Original,
    domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R3,
    componentArray: '[[7,8],[9,10],[11,12]]')

Matrix('C', symbol: 'C', rows: 2, cols: 2,
    matrixTypeEnum: MatrixType.Dense,
    domainSpaceEnum: MathSpace.R2,
    codomainSpaceEnum: MathSpace.R2)

Transformation('MultiplyAB', transformationTypeEnum: TransformationType.MatrixProduct,
    resultMatrixId: 'C') {
    TransformationOperand('Op_0', operandIndex: 0, operandTypeEnum: TransformationOperandType.Left, operandMatrixId: 'A')
    TransformationOperand('Op_1', operandIndex: 1, operandTypeEnum: TransformationOperandType.Right, operandMatrixId: 'B')
}

