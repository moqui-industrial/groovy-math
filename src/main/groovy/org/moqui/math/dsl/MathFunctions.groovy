/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
class MathFunctions {

    // --- Trigonometric & Hyperbolic ---
    static double sin(final Number x) { Math.sin(x.doubleValue()) }
    static double cos(final Number x) { Math.cos(x.doubleValue()) }
    static double tan(final Number x) { Math.tan(x.doubleValue()) }
    static double asin(final Number x) { Math.asin(x.doubleValue()) }
    static double acos(final Number x) { Math.acos(x.doubleValue()) }
    static double atan(final Number x) { Math.atan(x.doubleValue()) }
    static double atan2(final Number y, final Number x) { Math.atan2(y.doubleValue(), x.doubleValue()) }
    static double sinh(final Number x) { Math.sinh(x.doubleValue()) }
    static double cosh(final Number x) { Math.cosh(x.doubleValue()) }
    static double tanh(final Number x) { Math.tanh(x.doubleValue()) }

    // --- Exponential & Logarithmic ---
    static double exp(final Number x) { Math.exp(x.doubleValue()) }
    static double log(final Number x) { Math.log(x.doubleValue()) }
    static double ln(final Number x) { Math.log(x.doubleValue()) }
    static double log10(final Number x) { Math.log10(x.doubleValue()) }
    static double log2(final Number x) { Math.log(x.doubleValue()) / Math.log(2.0d) }
    static double sqrt(final Number x) { Math.sqrt(x.doubleValue()) }
    static double cbrt(final Number x) { Math.cbrt(x.doubleValue()) }
    static double abs(final Number x) { Math.abs(x.doubleValue()) }
    static double pow(final Number x, final Number y) { Math.pow(x.doubleValue(), y.doubleValue()) }

    // --- Rounding & Special ---
    static double ceil(final Number x) { Math.ceil(x.doubleValue()) }
    static double floor(final Number x) { Math.floor(x.doubleValue()) }
    static long round(final Number x) { Math.round(x.doubleValue()) }
    static double round(final Number x, final int scale) {
        BigDecimal bd = BigDecimal.valueOf(x.doubleValue())
        bd.setScale(scale, java.math.RoundingMode.HALF_UP).doubleValue()
    }
    static double signum(final Number x) { Math.signum(x.doubleValue()) }
    static double sign(final Number x) { Math.signum(x.doubleValue()) }

    // --- Statistical & Aggregate ---
    static double min(final Number a, final Number b) { Math.min(a.doubleValue(), b.doubleValue()) }
    static double min(final Collection<? extends Number> list) {
        if (list == null || list.isEmpty()) throw new IllegalArgumentException("Cannot compute min of empty collection")
        double m = Double.POSITIVE_INFINITY
        for (Number n : list) {
            if (n != null && n.doubleValue() < m) m = n.doubleValue()
        }
        m
    }

    static double max(final Number a, final Number b) { Math.max(a.doubleValue(), b.doubleValue()) }
    static double max(final Collection<? extends Number> list) {
        if (list == null || list.isEmpty()) throw new IllegalArgumentException("Cannot compute max of empty collection")
        double m = Double.NEGATIVE_INFINITY
        for (Number n : list) {
            if (n != null && n.doubleValue() > m) m = n.doubleValue()
        }
        m
    }

    static double sum(final Collection<? extends Number> list) {
        if (list == null || list.isEmpty()) return 0.0d
        double s = 0.0d
        for (Number n : list) {
            if (n != null) s += n.doubleValue()
        }
        s
    }

    static double mean(final Collection<? extends Number> list) {
        if (list == null || list.isEmpty()) throw new IllegalArgumentException("Cannot compute mean of empty collection")
        sum(list) / list.size()
    }
    static double avg(final Collection<? extends Number> list) { mean(list) }

    static double variance(final Collection<? extends Number> list, final boolean sample = true) {
        if (list == null || list.size() < (sample ? 2 : 1)) {
            throw new IllegalArgumentException("Variance requires at least ${sample ? 2 : 1} elements")
        }
        double m = mean(list)
        double sumSq = 0.0d
        for (Number n : list) {
            double diff = n.doubleValue() - m
            sumSq += diff * diff
        }
        sumSq / (sample ? (list.size() - 1) : list.size())
    }
    static double var(final Collection<? extends Number> list, final boolean sample = true) { variance(list, sample) }

    static double stddev(final Collection<? extends Number> list, final boolean sample = true) {
        Math.sqrt(variance(list, sample))
    }
    static double std(final Collection<? extends Number> list, final boolean sample = true) { stddev(list, sample) }

    static double dot(final List<? extends Number> u, final List<? extends Number> v) {
        if (u == null || v == null || u.size() != v.size()) {
            throw new IllegalArgumentException("Vector dot product requires non-null vectors of equal length")
        }
        double s = 0.0d
        for (int i = 0; i < u.size(); i++) {
            s += u.get(i).doubleValue() * v.get(i).doubleValue()
        }
        s
    }
    static double inner(final List<? extends Number> u, final List<? extends Number> v) { dot(u, v) }

    static List<List<Double>> outer(final List<? extends Number> u, final List<? extends Number> v) {
        if (u == null || v == null) throw new IllegalArgumentException("Outer product vectors must not be null")
        List<List<Double>> result = new ArrayList<>()
        for (int i = 0; i < u.size(); i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < v.size(); j++) {
                row.add(u.get(i).doubleValue() * v.get(j).doubleValue())
            }
            result.add(row)
        }
        result
    }

    // --- Norm Functions ---
    static double norm(final List<?> target, final Object order = 'L2') {
        if (target == null || target.isEmpty()) return 0.0d
        if (target.get(0) instanceof List) {
            return matrixNorm((List<List<? extends Number>>) target, order)
        }
        return vectorNorm((List<? extends Number>) target, order)
    }

    static double vectorNorm(final List<? extends Number> v, final Object order = 'L2') {
        if (v == null || v.isEmpty()) return 0.0d
        String ordStr = order != null ? order.toString().trim().toUpperCase() : 'L2'
        if (ordStr == '1' || ordStr == 'L1' || ordStr == 'MANHATTAN') {
            double s = 0.0d
            for (Number n : v) s += Math.abs(n.doubleValue())
            return s
        }
        if (ordStr == '2' || ordStr == 'L2' || ordStr == 'EUCLIDEAN') {
            double s = 0.0d
            for (Number n : v) {
                double val = n.doubleValue()
                s += val * val
            }
            return Math.sqrt(s)
        }
        if (ordStr == 'INF' || ordStr == 'LINF' || ordStr == 'INFINITY') {
            double m = 0.0d
            for (Number n : v) {
                double val = Math.abs(n.doubleValue())
                if (val > m) m = val
            }
            return m
        }
        if (order instanceof Number) {
            double p = ((Number) order).doubleValue()
            double s = 0.0d
            for (Number n : v) s += Math.pow(Math.abs(n.doubleValue()), p)
            return Math.pow(s, 1.0d / p)
        }
        return vectorNorm(v, 'L2')
    }

    static double matrixNorm(final List<List<? extends Number>> M, final Object order = 'Frobenius') {
        if (M == null || M.isEmpty()) return 0.0d
        String ordStr = order != null ? order.toString().trim().toUpperCase() : 'FROBENIUS'
        if (ordStr == 'FROBENIUS' || ordStr == 'FRO') {
            double s = 0.0d
            for (List<? extends Number> row : M) {
                for (Number n : row) {
                    double val = n.doubleValue()
                    s += val * val
                }
            }
            return Math.sqrt(s)
        }
        if (ordStr == '1' || ordStr == 'L1') {
            int cols = M.get(0).size()
            double maxColSum = 0.0d
            for (int j = 0; j < cols; j++) {
                double colSum = 0.0d
                for (int i = 0; i < M.size(); i++) {
                    colSum += Math.abs(M.get(i).get(j).doubleValue())
                }
                if (colSum > maxColSum) maxColSum = colSum
            }
            return maxColSum
        }
        if (ordStr == 'INF' || ordStr == 'LINF' || ordStr == 'INFINITY') {
            double maxRowSum = 0.0d
            for (List<? extends Number> row : M) {
                double rowSum = 0.0d
                for (Number n : row) rowSum += Math.abs(n.doubleValue())
                if (rowSum > maxRowSum) maxRowSum = rowSum
            }
            return maxRowSum
        }
        if (ordStr == '2' || ordStr == 'SPECTRAL') {
            List<Double> sv = singularValues(toDoubleMatrix(M))
            return sv.isEmpty() ? 0.0d : sv.get(0)
        }
        if (ordStr == 'NUCLEAR' || ordStr == 'NUC') {
            List<Double> sv = singularValues(toDoubleMatrix(M))
            double s = 0.0d
            for (Double v : sv) s += v
            return s
        }
        return matrixNorm(M, 'FROBENIUS')
    }

    // --- Linear Algebra Functions ---
    static double determinant(final List<List<? extends Number>> M) {
        double[][] a = toDoubleMatrix(M)
        int n = a.length
        if (n == 0 || a[0].length != n) throw new IllegalArgumentException("Determinant requires a square matrix")
        if (n == 1) return a[0][0]
        if (n == 2) return a[0][0] * a[1][1] - a[0][1] * a[1][0]
        if (n == 3) {
            return a[0][0] * (a[1][1] * a[2][2] - a[1][2] * a[2][1]) -
                   a[0][1] * (a[1][0] * a[2][2] - a[1][2] * a[2][0]) +
                   a[0][2] * (a[1][0] * a[2][1] - a[1][1] * a[2][0])
        }
        double det = 1.0d
        for (int i = 0; i < n; i++) {
            int pivot = i
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(a[j][i]) > Math.abs(a[pivot][i])) pivot = j
            }
            if (Math.abs(a[pivot][i]) < 1e-12) return 0.0d
            if (pivot != i) {
                double[] temp = a[i]
                a[i] = a[pivot]
                a[pivot] = temp
                det = -det
            }
            det *= a[i][i]
            for (int j = i + 1; j < n; j++) {
                double factor = a[j][i] / a[i][i]
                for (int k = i + 1; k < n; k++) {
                    a[j][k] -= factor * a[i][k]
                }
            }
        }
        det
    }
    static double det(final List<List<? extends Number>> M) { determinant(M) }

    static double trace(final List<List<? extends Number>> M) {
        if (M == null || M.isEmpty()) return 0.0d
        int n = Math.min(M.size(), M.get(0).size())
        double tr = 0.0d
        for (int i = 0; i < n; i++) {
            tr += M.get(i).get(i).doubleValue()
        }
        tr
    }

    static List<List<Double>> inverse(final List<List<? extends Number>> M) {
        double[][] a = toDoubleMatrix(M)
        int n = a.length
        if (n == 0 || a[0].length != n) throw new IllegalArgumentException("Inverse requires a square matrix")
        double[][] inv = new double[n][n]
        for (int i = 0; i < n; i++) inv[i][i] = 1.0d

        for (int i = 0; i < n; i++) {
            int pivot = i
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(a[j][i]) > Math.abs(a[pivot][i])) pivot = j
            }
            if (Math.abs(a[pivot][i]) < 1e-12) throw new ArithmeticException("Matrix is singular and cannot be inverted")
            if (pivot != i) {
                double[] tmpA = a[i]; a[i] = a[pivot]; a[pivot] = tmpA
                double[] tmpI = inv[i]; inv[i] = inv[pivot]; inv[pivot] = tmpI
            }
            double div = a[i][i]
            for (int k = 0; k < n; k++) {
                a[i][k] /= div
                inv[i][k] /= div
            }
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double factor = a[j][i]
                    for (int k = 0; k < n; k++) {
                        a[j][k] -= factor * a[i][k]
                        inv[j][k] -= factor * inv[i][k]
                    }
                }
            }
        }
        toListMatrix(inv)
    }
    static List<List<Double>> inv(final List<List<? extends Number>> M) { inverse(M) }

    static List<List<Double>> transpose(final List<?> target) {
        if (target == null || target.isEmpty()) return new ArrayList<>()
        if (!(target.get(0) instanceof List)) {
            List<List<Double>> col = new ArrayList<>()
            for (Object item : target) {
                col.add([((Number) item).doubleValue()])
            }
            return col
        }
        List<List<? extends Number>> M = (List<List<? extends Number>>) target
        int rows = M.size()
        int cols = M.get(0).size()
        List<List<Double>> res = new ArrayList<>()
        for (int j = 0; j < cols; j++) {
            List<Double> row = new ArrayList<>()
            for (int i = 0; i < rows; i++) {
                row.add(M.get(i).get(j).doubleValue())
            }
            res.add(row)
        }
        res
    }

    static Object diagonal(final List<?> target) {
        if (target == null || target.isEmpty()) return new ArrayList<>()
        if (target.get(0) instanceof List) {
            List<List<? extends Number>> M = (List<List<? extends Number>>) target
            int n = Math.min(M.size(), M.get(0).size())
            List<Double> diag = new ArrayList<>()
            for (int i = 0; i < n; i++) diag.add(M.get(i).get(i).doubleValue())
            return diag
        }
        List<? extends Number> v = (List<? extends Number>) target
        int n = v.size()
        List<List<Double>> M = new ArrayList<>()
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < n; j++) {
                row.add(i == j ? v.get(i).doubleValue() : 0.0d)
            }
            M.add(row)
        }
        return M
    }
    static Object diag(final List<?> target) { diagonal(target) }

    static int rank(final List<List<? extends Number>> M) {
        double[][] a = toDoubleMatrix(M)
        List<Double> sv = singularValues(a)
        double tol = 1e-10 * (sv.isEmpty() ? 1.0d : sv.get(0))
        int r = 0
        for (Double val : sv) {
            if (val > tol) r++
        }
        r
    }

    static double conditionNumber(final List<List<? extends Number>> M) {
        double[][] a = toDoubleMatrix(M)
        List<Double> sv = singularValues(a)
        if (sv.isEmpty() || sv.last() < 1e-14) return Double.POSITIVE_INFINITY
        sv.first() / sv.last()
    }
    static double cond(final List<List<? extends Number>> M) { conditionNumber(M) }

    static List<Double> eigenvalues(final List<List<? extends Number>> M) {
        double[][] a = toDoubleMatrix(M)
        int n = a.length
        if (n == 0 || a[0].length != n) throw new IllegalArgumentException("Eigenvalues require a square matrix")
        if (n == 1) return [a[0][0]]
        if (n == 2) {
            double tr = a[0][0] + a[1][1]
            double det = a[0][0] * a[1][1] - a[0][1] * a[1][0]
            double disc = tr * tr - 4 * det
            if (disc >= 0) {
                double sq = Math.sqrt(disc)
                List<Double> res = [(tr + sq) / 2.0d, (tr - sq) / 2.0d]
                res.sort { Double x, Double y -> y <=> x }
                return res
            }
        }
        return singularValues(a)
    }
    static List<Double> eig(final List<List<? extends Number>> M) { eigenvalues(M) }

    static List<List<Double>> pseudoInverse(final List<List<? extends Number>> M) {
        double[][] a = toDoubleMatrix(M)
        int m = a.length
        int n = a[0].length
        if (m == n) {
            try {
                return inverse(M)
            } catch (Exception ignored) {}
        }
        if (m >= n) {
            double[][] at = transposeArray(a)
            double[][] ata = matMul(at, a)
            double[][] ataInv = toDoubleMatrix(inverse(toListMatrix(ata)))
            double[][] pinv = matMul(ataInv, at)
            return toListMatrix(pinv)
        } else {
            double[][] at = transposeArray(a)
            double[][] aat = matMul(a, at)
            double[][] aatInv = toDoubleMatrix(inverse(toListMatrix(aat)))
            double[][] pinv = matMul(at, aatInv)
            return toListMatrix(pinv)
        }
    }
    static List<List<Double>> pinv(final List<List<? extends Number>> M) { pseudoInverse(M) }

    // --- Helper Methods ---
    private static double[][] toDoubleMatrix(final List<List<? extends Number>> M) {
        if (M == null || M.isEmpty()) return new double[0][0]
        int rows = M.size()
        int cols = M.get(0).size()
        double[][] res = new double[rows][cols]
        for (int i = 0; i < rows; i++) {
            List<? extends Number> row = M.get(i)
            for (int j = 0; j < cols; j++) {
                res[i][j] = row.get(j).doubleValue()
            }
        }
        res
    }

    private static List<List<Double>> toListMatrix(final double[][] a) {
        List<List<Double>> res = new ArrayList<>()
        for (int i = 0; i < a.length; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < a[i].length; j++) {
                row.add(a[i][j])
            }
            res.add(row)
        }
        res
    }

    private static double[][] transposeArray(final double[][] a) {
        int m = a.length
        int n = a[0].length
        double[][] res = new double[n][m]
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) res[j][i] = a[i][j]
        }
        res
    }

    private static double[][] matMul(final double[][] a, final double[][] b) {
        int m = a.length
        int k = a[0].length
        int n = b[0].length
        double[][] c = new double[m][n]
        for (int i = 0; i < m; i++) {
            for (int p = 0; p < k; p++) {
                for (int j = 0; j < n; j++) {
                    c[i][j] += a[i][p] * b[p][j]
                }
            }
        }
        c
    }

    private static List<Double> singularValues(final double[][] a) {
        int m = a.length
        if (m == 0) return Collections.emptyList()
        int n = a[0].length
        if (n == 0) return Collections.emptyList()
        double[][] ata = (m >= n) ? matMul(transposeArray(a), a) : matMul(a, transposeArray(a))
        double[] ev = jacobiEigenvalues(ata)
        List<Double> sv = new ArrayList<>()
        for (double val : ev) {
            sv.add(Math.sqrt(Math.max(0.0d, val)))
        }
        sv.sort { Double x, Double y -> y <=> x }
        sv
    }

    private static double[] jacobiEigenvalues(final double[][] aIn) {
        int n = aIn.length
        double[][] a = new double[n][n]
        for (int i = 0; i < n; i++) System.arraycopy(aIn[i], 0, a[i], 0, n)
        int maxIter = 100
        for (int iter = 0; iter < maxIter; iter++) {
            double maxOff = 0.0d
            int p = 0, q = 1
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double absVal = Math.abs(a[i][j])
                    if (absVal > maxOff) {
                        maxOff = absVal
                        p = i
                        q = j
                    }
                }
            }
            if (maxOff < 1e-12) break
            double diff = a[q][q] - a[p][p]
            double t
            if (Math.abs(a[p][q]) < Math.abs(diff) * 1e-15) {
                t = a[p][q] / diff
            } else {
                double phi = diff / (2.0d * a[p][q])
                t = 1.0d / (Math.abs(phi) + Math.sqrt(phi * phi + 1.0d))
                if (phi < 0) t = -t
            }
            double c = 1.0d / Math.sqrt(t * t + 1.0d)
            double s = t * c
            double tau = s / (1.0d + c)
            double app = a[p][p]
            double aqq = a[q][q]
            double apq = a[p][q]
            a[p][p] = app - t * apq
            a[q][q] = aqq + t * apq
            a[p][q] = 0.0d
            a[q][p] = 0.0d
            for (int j = 0; j < n; j++) {
                if (j != p && j != q) {
                    double ajp = a[j][p]
                    double ajq = a[j][q]
                    a[j][p] = ajp - s * (ajq + tau * ajp)
                    a[p][j] = a[j][p]
                    a[j][q] = ajq + s * (ajp - tau * ajq)
                    a[q][j] = a[j][q]
                }
            }
        }
        double[] ev = new double[n]
        for (int i = 0; i < n; i++) ev[i] = a[i][i]
        ev
    }
}
