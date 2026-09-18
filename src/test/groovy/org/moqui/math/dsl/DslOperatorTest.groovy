/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

class DslOperatorTest {

    @Test
    void matrixMultiplicationProducesMatrixProductTransformation() {
        String operatorDsl = '''
            A = matrix('A', [[1, 2], [3, 4]])
            B = matrix('B', [[5, 6], [7, 8]])
            C = A * B
        '''
        String functionalDsl = '''
            A = matrix('A', [[1, 2], [3, 4]])
            B = matrix('B', [[5, 6], [7, 8]])
            C = matrixProduct(A, B)
        '''

        MathMeta actual = MathDsl.evaluate(operatorDsl)
        MathMeta expected = MathDsl.evaluate(functionalDsl)

        CanonicalDump.assertStructuralEquals(actual, expected)
    }

    @Test
    void matrixVectorMultiplicationFailsBecauseTypeNotInSchema() {
        String dsl = '''
            A = matrix('A', [[1, 2], [3, 4]])
            v = vector('v', [1, 2])
            res = A * v
        '''

        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(dsl)
        }
        assertTrue(ex.message.contains('TtMatrixVectorProduct'), "Error message should mention TtMatrixVectorProduct: ${ex.message}")
        assertTrue(ex.message.contains('schema'), "Error message should mention schema: ${ex.message}")
    }

    @Test
    void tensorHadamardMultiplicationProducesTensorMulTransformation() {
        String operatorDsl = '''
            X = tensor('X', [1, 2, 3, 4])
            Y = tensor('Y', [5, 6, 7, 8])
            Z = X * Y
        '''
        String functionalDsl = '''
            X = tensor('X', [1, 2, 3, 4])
            Y = tensor('Y', [5, 6, 7, 8])
            Z = tensorMul(X, Y)
        '''

        MathMeta actual = MathDsl.evaluate(operatorDsl)
        MathMeta expected = MathDsl.evaluate(functionalDsl)

        CanonicalDump.assertStructuralEquals(actual, expected)
    }

    @Test
    void tensorArithmeticOperatorsProduceCorrespondingTransformations() {
        String operatorDsl = '''
            X = tensor('X', [1, 2, 3, 4])
            Y = tensor('Y', [5, 6, 7, 8])
            Z1 = X + Y
            Z2 = X - Y
            Z3 = X / Y
        '''
        String functionalDsl = '''
            X = tensor('X', [1, 2, 3, 4])
            Y = tensor('Y', [5, 6, 7, 8])
            Z1 = tensorAdd(X, Y)
            Z2 = tensorSub(X, Y)
            Z3 = tensorDiv(X, Y)
        '''

        MathMeta actual = MathDsl.evaluate(operatorDsl)
        MathMeta expected = MathDsl.evaluate(functionalDsl)

        CanonicalDump.assertStructuralEquals(actual, expected)
    }

    @Test
    void tensorPowerProducesTensorPowTransformation() {
        String operatorDsl = '''
            X = tensor('X', [1, 2, 3, 4])
            Z = X ** 2
        '''
        String functionalDsl = '''
            X = tensor('X', [1, 2, 3, 4])
            Z = tensorPow(X, 2)
        '''

        MathMeta actual = MathDsl.evaluate(operatorDsl)
        MathMeta expected = MathDsl.evaluate(functionalDsl)

        CanonicalDump.assertStructuralEquals(actual, expected)
    }

    @Test
    void tensorUnaryMinusFailsBecauseTypeNotInSchema() {
        String dsl = '''
            X = tensor('X', [1, 2, 3, 4])
            Z = -X
        '''

        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(dsl)
        }
        assertTrue(ex.message.contains('TtTensorNeg'), "Error message should mention TtTensorNeg: ${ex.message}")
        assertTrue(ex.message.contains('schema'), "Error message should mention schema: ${ex.message}")
    }

    @Test
    void unsupportedOperandCombinationThrowsInformativeException() {
        String dsl = '''
            A = matrix('A', [[1, 2], [3, 4]])
            v = vector('v', [1, 2])
            res = A + v
        '''

        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(dsl)
        }
        assertTrue(ex.message.contains('Unsupported operator'), "Error message should describe unsupported operator: ${ex.message}")
        assertTrue(ex.message.contains('Supported operations'), "Error message should list supported operations: ${ex.message}")
    }
}
