/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('MatrixAlgebra', type: LinearAlgebra, usage: Inference,
    modelName: 'Matrix algebra transformations',
    description: 'Provider-neutral mathematical declarations') {

    pipeline('ProductStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'MultiplyAB', stepName: 'Matrix product A x B') {
        Transformation('MultiplyAB', type: MatrixProduct,
            name: 'Matrix product A x B', resultMatrixId: 'C') {
            leftMatrix 'A'
            rightMatrix 'B'
        }
    }

    MathModel('MatrixProduct', alias: 'matrix_product', source: Manual,
        description: 'C = A x B, where A is supplied at execution time',
        status: Draft) {

        A = matrix(rows: 2, cols: 3, purpose: Original)
        B = matrix([[7, 8], [9, 10], [11, 12]], purpose: Original)
        C = matrix()
    }
}
