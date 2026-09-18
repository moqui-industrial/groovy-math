/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import org.moqui.math.dsl.MathDsl
import org.moqui.math.dsl.NormDomain
import org.moqui.math.dsl.NormOrder
import org.moqui.math.dsl.TensorDecompMethod
import org.moqui.math.dsl.TransformationType
import org.moqui.math.dsl.TriangularExtractionType

MathDsl.fluent {
    matrix('A', rows: 4, cols: 4, data: [
        [4.0, 1.0, 0.0, 0.0],
        [1.0, 4.0, 1.0, 0.0],
        [0.0, 1.0, 4.0, 1.0],
        [0.0, 0.0, 1.0, 4.0]
    ])

    // 1. Singular Value Decomposition (SVD): A = U * Sigma * V^T
    matrixDecomposition('Svd_A', type: TransformationType.Svd) {
        operandMatrix 'A'
        leftMatrix 'U'
        diagMatrix 'Sigma'
        rightMatrix 'Vt'
        rankApproximation 4
    }

    // 2. Diagonal Extraction (k = 0 main diagonal, +1 super-diagonal, -1 sub-diagonal)
    diagonalExtraction('MainDiag_A', axisOffset: 0) {
        operandMatrix 'A'
        resultVector 'd'
    }

    // 3. Triangular Extractions (Upper and Lower)
    triangularExtraction('Upper_A', type: TriangularExtractionType.Upper, extractionOffset: 0) {
        operandMatrix 'A'
        resultMatrix 'U_tri'
    }

    // 4. Tridiagonal Band Extraction
    bandExtraction('Tridiag_A', lowerBand: -1, upperBand: 1) {
        operandMatrix 'A'
        resultMatrix 'T'
    }

    // 5. Block Submatrix Extraction
    blockMatrixExtraction('Block_TopLeft', startRowBlock: 0, endRowBlock: 1, startColBlock: 0, endColBlock: 1) {
        operandMatrix 'A'
        resultMatrix 'A_2x2'
    }

    // 6. Matrix Frobenius Norm
    normResult('NormFrob_A', domain: NormDomain.Matrix, order: NormOrder.MatFrobenius) {
        operandMatrix 'A'
        resultParameter 'frob_norm'
        normValue 8.3666
    }

    // 7. Higher-Order Tensor Tucker Decomposition
    tensor('T_Sensor', rank: 3, shape: [10, 10, 10])
    tensorDecomposition('Tucker_T', sourceTensor: 'T_Sensor', method: TensorDecompMethod.Tucker, factors: ['U0', 'U1', 'U2'])

    // 8. Coordinate System Frame Transformation
    coordinateSystemTransformation('RobotFrameTransform', sourceCoordSystem: 'WorldFrame', targetCoordSystem: 'ToolCenterPoint', matrix: 'A')
}
