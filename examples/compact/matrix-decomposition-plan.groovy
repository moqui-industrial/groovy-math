MathDsl.fluent {
    matrix('A', rows: 4, cols: 4, data: [
        [4.0, 1.0, 0.0, 0.0],
        [1.0, 4.0, 1.0, 0.0],
        [0.0, 1.0, 4.0, 1.0],
        [0.0, 0.0, 1.0, 4.0]
    ])

    matrixDecomposition('Svd_A', type: Svd) {
        operandMatrix 'A'
        leftMatrix 'U'
        diagMatrix 'Sigma'
        rightMatrix 'Vt'
        rankApproximation 4
    }

    diagonalExtraction('MainDiag_A', axisOffset: 0) {
        operandMatrix 'A'
        resultVector 'd'
    }

    triangularExtraction('Upper_A', type: Upper, extractionOffset: 0) {
        operandMatrix 'A'
        resultMatrix 'U_tri'
    }

    bandExtraction('Tridiag_A', lowerBand: -1, upperBand: 1) {
        operandMatrix 'A'
        resultMatrix 'T'
    }

    blockMatrixExtraction('Block_TopLeft', startRowBlock: 0, endRowBlock: 1, startColBlock: 0, endColBlock: 1) {
        operandMatrix 'A'
        resultMatrix 'A_2x2'
    }

    normResult('NormFrob_A', domain: NormDomain.Matrix, order: MatFrobenius) {
        operandMatrix 'A'
        resultParameter 'frob_norm'
        normValue 8.3666
    }

    tensor('T_Sensor', rank: 3, shape: [10, 10, 10])
    tensorDecomposition('Tucker_T', sourceTensor: 'T_Sensor', method: TensorDecompMethod.Tucker, factors: ['U0', 'U1', 'U2'])

    coordinateSystemTransformation('RobotFrameTransform', sourceCoordSystem: 'WorldFrame', targetCoordSystem: 'ToolCenterPoint', matrix: 'A')
}
