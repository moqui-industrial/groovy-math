MathDsl.fluent {
    matrix('A', rows: 2, cols: 3, purpose: MatrixPurpose.Original,
           domainSpace: R3, codomainSpace: R2,
           name: 'A', symbol: 'A')

    matrix('B', rows: 3, cols: 2, purpose: MatrixPurpose.Original,
           domainSpace: R2, codomainSpace: R3,
           name: 'B', symbol: 'B',
           data: [[7, 8], [9, 10], [11, 12]])

    matrix('C', rows: 2, cols: 2,
           domainSpace: R2, codomainSpace: R2,
           name: 'C', symbol: 'C')

    transformation('MultiplyAB') {
        type MatrixProduct
        name 'Matrix product A x B'
        leftMatrix 'A'
        rightMatrix 'B'
        resultMatrix 'C'
    }
}
