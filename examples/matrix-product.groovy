/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('MatrixAlgebra',
    modelTypeEnumId: 'MmtLinearAlgebra',
    usageContextEnumId: 'MmucInference',
    modelName: 'Matrix algebra transformations',
    description: 'Provider-neutral mathematical declarations') {

    MathModel('MatrixProduct',
        modelAlias: 'matrix_product',
        sourceEnumId: 'MmsManual',
        description: 'C = A x B, where A is supplied at execution time',
        statusId: 'MathModelDraft') {

        data('LeftMatrixData', dataTypeEnumId: 'MmdtMatrix', matrixId: 'A', sequenceNum: 0) {
            Matrix('A', matrixTypeEnumId: 'MtDense', purposeEnumId: 'MpOriginal',
                domainSpaceEnumId: 'R3', codomainSpaceEnumId: 'R2',
                name: 'A', symbol: 'A', rows: 2, cols: 3)
        }

        data('RightMatrixData', dataTypeEnumId: 'MmdtMatrix', matrixId: 'B', sequenceNum: 1) {
            Matrix('B', matrixTypeEnumId: 'MtDense', purposeEnumId: 'MpOriginal',
                domainSpaceEnumId: 'R2', codomainSpaceEnumId: 'R3',
                name: 'B', symbol: 'B', rows: 3, cols: 2,
                componentArray: '[[7,8],[9,10],[11,12]]')
        }

        data('ResultMatrixData', dataTypeEnumId: 'MmdtMatrix', matrixId: 'C', sequenceNum: 2) {
            Matrix('C', matrixTypeEnumId: 'MtDense',
                domainSpaceEnumId: 'R2', codomainSpaceEnumId: 'R2',
                name: 'C', symbol: 'C', rows: 2, cols: 2)
        }

        data('ProductStep', dataTypeEnumId: 'MmdtTransformation',
            transformationId: 'MultiplyAB', sequenceNum: 10) {
            Transformation('MultiplyAB', transformationTypeEnumId: 'TtMatrixProduct',
                name: 'Matrix product A x B', resultMatrixId: 'C') {
                operands(operandIndex: 0, operandTypeEnumId: 'TotLeftMatrix', operandMatrixId: 'A')
                operands(operandIndex: 1, operandTypeEnumId: 'TotRightMatrix', operandMatrixId: 'B')
            }
        }
    }
}
