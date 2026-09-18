/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathDsl.fluent {
    matrix('A', rows: 2, cols: 3, purpose: Original, symbol: 'A')
    matrix('B', [[7, 8], [9, 10], [11, 12]], purpose: Original, symbol: 'B')
    matrix('C', rows: 2, cols: 2, symbol: 'C')

    transformation('MultiplyAB', type: MatrixProduct, name: 'Matrix product A x B') {
        leftMatrix 'A'
        rightMatrix 'B'
        resultMatrix 'C'
    }
}
