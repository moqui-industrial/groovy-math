/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

// Pure declarative mathematical plan without MathModelDef or MathModel overhead

MathDsl.fluent {
    matrix('A', rows: 2, cols: 3, purpose: MatrixPurpose.Original,
           domainSpace: MathSpace.R3, codomainSpace: MathSpace.R2,
           name: 'A', symbol: 'A')

    matrix('B', rows: 3, cols: 2, purpose: MatrixPurpose.Original,
           domainSpace: MathSpace.R2, codomainSpace: MathSpace.R3,
           name: 'B', symbol: 'B',
           data: [[7, 8], [9, 10], [11, 12]])

    matrix('C', rows: 2, cols: 2,
           domainSpace: MathSpace.R2, codomainSpace: MathSpace.R2,
           name: 'C', symbol: 'C')

    transformation('MultiplyAB') {
        type TransformationType.MatrixProduct
        name 'Matrix product A x B'
        leftMatrix 'A'
        rightMatrix 'B'
        resultMatrix 'C'
    }
}
