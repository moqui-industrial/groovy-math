/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*
import static org.moqui.math.dsl.MathFunctions.*

class DslFunctionTest {

    private static final double EPS = 1e-9d

    @Test
    void testTrigonometricAndHyperbolicFunctions() {
        assertEquals(0.0d, sin(0), EPS)
        assertEquals(1.0d, sin(Math.PI / 2), EPS)
        assertEquals(1.0d, cos(0), EPS)
        assertEquals(0.0d, cos(Math.PI / 2), EPS)
        assertEquals(1.0d, tan(Math.PI / 4), EPS)

        assertEquals(Math.PI / 2, asin(1), EPS)
        assertEquals(0.0d, acos(1), EPS)
        assertEquals(Math.PI / 4, atan(1), EPS)
        assertEquals(Math.PI / 4, atan2(1, 1), EPS)

        assertEquals(0.0d, sinh(0), EPS)
        assertEquals(1.0d, cosh(0), EPS)
        assertEquals(0.0d, tanh(0), EPS)
        assertTrue(cosh(2.0) > sinh(2.0))
    }

    @Test
    void testExponentialAndLogarithmicFunctions() {
        assertEquals(1.0d, exp(0), EPS)
        assertEquals(Math.E, exp(1), EPS)

        assertEquals(1.0d, log(Math.E), EPS)
        assertEquals(1.0d, ln(Math.E), EPS)
        assertEquals(2.0d, log10(100), EPS)
        assertEquals(3.0d, log2(8), EPS)

        assertEquals(3.0d, sqrt(9), EPS)
        assertEquals(3.0d, cbrt(27), EPS)
        assertEquals(5.5d, abs(-5.5d), EPS)
        assertEquals(8.0d, pow(2, 3), EPS)
    }

    @Test
    void testRoundingAndSpecialFunctions() {
        assertEquals(3.0d, ceil(2.1d), EPS)
        assertEquals(2.0d, floor(2.9d), EPS)
        assertEquals(3L, round(2.6d))
        assertEquals(2L, round(2.4d))
        assertEquals(3.14d, round(3.14159d, 2), EPS)

        assertEquals(1.0d, signum(42.5d), EPS)
        assertEquals(-1.0d, signum(-42.5d), EPS)
        assertEquals(0.0d, signum(0), EPS)
        assertEquals(1.0d, sign(99), EPS)
        assertEquals(-1.0d, sign(-99), EPS)
    }

    @Test
    void testLinearAlgebraFunctions() {
        // 2x2 Matrix
        List<List<Double>> A = [
            [4.0d, 7.0d],
            [2.0d, 6.0d]
        ]
        assertEquals(10.0d, det(A), EPS)
        assertEquals(10.0d, determinant(A), EPS)
        assertEquals(10.0d, trace(A), EPS)

        List<List<Double>> invA = inv(A)
        assertEquals(0.6d, invA[0][0], EPS)
        assertEquals(-0.7d, invA[0][1], EPS)
        assertEquals(-0.2d, invA[1][0], EPS)
        assertEquals(0.4d, invA[1][1], EPS)

        List<List<Double>> transA = transpose(A)
        assertEquals(4.0d, transA[0][0], EPS)
        assertEquals(2.0d, transA[0][1], EPS)
        assertEquals(7.0d, transA[1][0], EPS)
        assertEquals(6.0d, transA[1][1], EPS)

        List<Double> diagA = (List<Double>) diag(A)
        assertEquals([4.0d, 6.0d], diagA)

        List<List<Double>> diagMat = (List<List<Double>>) diag([3.0d, 5.0d])
        assertEquals([[3.0d, 0.0d], [0.0d, 5.0d]], diagMat)

        assertEquals(2, rank(A))
        assertTrue(cond(A) > 1.0d)

        List<Double> eigA = eig(A)
        assertEquals(2, eigA.size())
        assertTrue(eigA[0] > eigA[1])

        List<List<Double>> pinvA = pinv(A)
        assertEquals(invA[0][0], pinvA[0][0], EPS)
    }

    @Test
    void testStatisticalAndAggregateFunctions() {
        List<Double> vals = [2.0d, 4.0d, 4.0d, 4.0d, 5.0d, 5.0d, 7.0d, 9.0d]

        assertEquals(2.0d, min(vals), EPS)
        assertEquals(9.0d, max(vals), EPS)
        assertEquals(40.0d, sum(vals), EPS)
        assertEquals(5.0d, mean(vals), EPS)
        assertEquals(5.0d, avg(vals), EPS)

        // Sample variance of [2, 4, 4, 4, 5, 5, 7, 9]: sum((x - 5)^2) / 7 = 32 / 7 ≈ 4.571428
        assertEquals(32.0d / 7.0d, variance(vals), EPS)
        assertEquals(32.0d / 7.0d, var(vals), EPS)
        assertEquals(Math.sqrt(32.0d / 7.0d), stddev(vals), EPS)
        assertEquals(Math.sqrt(32.0d / 7.0d), std(vals), EPS)

        // Vector dot & outer
        List<Double> u = [1.0d, 2.0d, 3.0d]
        List<Double> v = [4.0d, 5.0d, 6.0d]
        assertEquals(32.0d, dot(u, v), EPS)
        assertEquals(32.0d, inner(u, v), EPS)

        List<List<Double>> out = outer(u, v)
        assertEquals([[4.0d, 5.0d, 6.0d], [8.0d, 10.0d, 12.0d], [12.0d, 15.0d, 18.0d]], out)
    }

    @Test
    void testNormFunctions() {
        List<Double> v = [3.0d, -4.0d]
        assertEquals(5.0d, norm(v), EPS)
        assertEquals(5.0d, norm(v, 'L2'), EPS)
        assertEquals(5.0d, norm(v, 2), EPS)
        assertEquals(7.0d, norm(v, 'L1'), EPS)
        assertEquals(7.0d, norm(v, 1), EPS)
        assertEquals(4.0d, norm(v, 'Linf'), EPS)

        List<List<Double>> M = [
            [1.0d, 2.0d],
            [3.0d, 4.0d]
        ]
        // Frobenius: sqrt(1 + 4 + 9 + 16) = sqrt(30) ≈ 5.477225575
        assertEquals(Math.sqrt(30.0d), norm(M), EPS)
        assertEquals(Math.sqrt(30.0d), norm(M, 'Frobenius'), EPS)
        assertEquals(Math.sqrt(30.0d), norm(M, 'fro'), EPS)

        // L1 (max column sum): max(1+3, 2+4) = 6
        assertEquals(6.0d, norm(M, 'L1'), EPS)
        // Linf (max row sum): max(1+2, 3+4) = 7
        assertEquals(7.0d, norm(M, 'Linf'), EPS)
        assertTrue(norm(M, 'nuclear') > 0)
    }

    @Test
    void testFunctionAliasesAndCaseInsensitivity() {
        assertEquals(log(Math.E), ln(Math.E), EPS)
        assertEquals(signum(-5), sign(-5), EPS)
        assertEquals(mean([1, 2, 3]), avg([1, 2, 3]), EPS)
        assertEquals(stddev([2, 4, 6]), std([2, 4, 6]), EPS)
        assertEquals(variance([2, 4, 6]), var([2, 4, 6]), EPS)

        List<List<Double>> M = [[2.0d, 1.0d], [1.0d, 2.0d]]
        assertEquals(determinant(M), det(M), EPS)
        assertEquals(inverse(M), inv(M))
        assertEquals(pseudoInverse(M), pinv(M))
        assertEquals(conditionNumber(M), cond(M), EPS)
        assertEquals(diagonal(M), diag(M))
        assertEquals(eigenvalues(M), eig(M))
        assertEquals(dot([1, 2], [3, 4]), inner([1, 2], [3, 4]), EPS)
    }

    @Test
    void unknownFunctionIsRejectedWithSuggestion() {
        String script = '''
            A = matrx([[1, 2], [3, 4]])
        '''
        IllegalArgumentException ex = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(script)
        }
        assertTrue(ex.message.contains("Unknown function or entity 'matrx'"))
        assertTrue(ex.message.toLowerCase().contains("matrix"))
    }
}
