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
    void matrixVectorProductMapsToAffine() {
        String dsl = '''
            A = matrix('A', [[1, 2], [3, 4]])
            v = vector('v', [1, 2])
            res = A * v
        '''
        MathMeta meta = MathDsl.evaluate(dsl)
        org.moqui.math.entity.ModelValue res = meta.entity('Vector').findByName('res')
        assertNotNull(res)
        assertEquals(2, res.get('dimension'))

        org.moqui.math.entity.ModelValue trans = meta.entity('Transformation').findByName('T_res')
        assertNotNull(trans)
        assertEquals('TtAffine', trans.get('transformationTypeEnumId'))
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
    void tensorNegationMapsToTensorNeg() {
        String operatorDsl = '''
            X = tensor('X', [1, 2, 3, 4])
            Z = -X
        '''
        String functionalDsl = '''
            X = tensor('X', [1, 2, 3, 4])
            Z = tensorNeg(X)
        '''

        MathMeta actual = MathDsl.evaluate(operatorDsl)
        MathMeta expected = MathDsl.evaluate(functionalDsl)

        CanonicalDump.assertStructuralEquals(actual, expected)
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
