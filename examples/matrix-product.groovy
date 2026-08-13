/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('MatrixAlgebra',
    modelTypeEnum: MathModelType.LinearAlgebra,
    usageContextEnum: MathModelUsageContext.Inference,
    modelName: 'Matrix algebra transformations',
    description: 'Provider-neutral mathematical declarations') {

    MathModel('MatrixProduct',
        modelAlias: 'matrix_product',
        sourceEnum: MathModelSource.Manual,
        description: 'C = A x B, where A is supplied at execution time',
        statusId: 'MathModelDraft') {

        data('LeftMatrixData', dataTypeEnum: MathModelDataType.Matrix, matrixId: 'A', sequenceNum: 0) {
            Matrix('A', matrixTypeEnum: MatrixType.Dense, purposeEnum: MatrixPurpose.Original,
                domainSpaceEnum: MathSpace.R3, codomainSpaceEnum: MathSpace.R2,
                name: 'A', symbol: 'A', rows: 2, cols: 3)
        }

        data('RightMatrixData', dataTypeEnum: MathModelDataType.Matrix, matrixId: 'B', sequenceNum: 1) {
            Matrix('B', matrixTypeEnum: MatrixType.Dense, purposeEnum: MatrixPurpose.Original,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R3,
                name: 'B', symbol: 'B', rows: 3, cols: 2,
                componentArray: '[[7,8],[9,10],[11,12]]')
        }

        data('ResultMatrixData', dataTypeEnum: MathModelDataType.Matrix, matrixId: 'C', sequenceNum: 2) {
            Matrix('C', matrixTypeEnum: MatrixType.Dense,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'C', symbol: 'C', rows: 2, cols: 2)
        }

        data('ProductStep', dataTypeEnum: MathModelDataType.Transformation,
            transformationId: 'MultiplyAB', sequenceNum: 10) {
            Transformation('MultiplyAB', transformationTypeEnum: TransformationType.MatrixProduct,
                name: 'Matrix product A x B', resultMatrixId: 'C') {
                operands(operandIndex: 0, operandTypeEnum: TransformationOperandType.LeftMatrix, operandMatrixId: 'A')
                operands(operandIndex: 1, operandTypeEnum: TransformationOperandType.RightMatrix, operandMatrixId: 'B')
            }
        }
    }
}
