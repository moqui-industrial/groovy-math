/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.moqui.math.entity.ModelProvider
import org.moqui.math.entity.ModelValue

@CompileStatic
class DslShapeInference {

    private static final Map<String, Set<String>> DERIVABLE_FIELDS = [
        'Tensor': ['shape', 'rank', 'size'] as Set,
        'moqui.math.Tensor': ['shape', 'rank', 'size'] as Set,
        'Matrix': ['rows', 'cols', 'domainSpaceEnumId', 'codomainSpaceEnumId'] as Set,
        'moqui.math.Matrix': ['rows', 'cols', 'domainSpaceEnumId', 'codomainSpaceEnumId'] as Set,
        'Vector': ['dimension', 'spaceEnumId'] as Set,
        'moqui.math.Vector': ['dimension', 'spaceEnumId'] as Set
    ]

    static boolean isDerivableField(final String entityName, final String fieldName) {
        Set<String> fields = DERIVABLE_FIELDS.get(entityName)
        fields != null && fields.contains(fieldName)
    }

    static Set<String> getDerivableFields(final String entityName) {
        DERIVABLE_FIELDS.get(entityName) ?: Collections.emptySet()
    }

    static class InferredShape {
        List<Object> shape
        Integer rank
        Integer rows
        Integer cols
        Integer dimension
        String resultType = 'Tensor'
    }

    static InferredShape inferShape(final String typeEnumId, final List<Object> operands,
                                    final Map<String, Object> options,
                                    final String callerFile, final int callerLine) {
        InferredShape res = new InferredShape()
        if (typeEnumId == null) return res

        String normType = typeEnumId.startsWith('Tt') ? typeEnumId.substring(2) : typeEnumId

        switch (normType) {
            // 1. Identical shape propagation
            case 'LayerNorm':
            case 'RMSNorm':
            case 'BatchNorm':
            case 'GroupNorm':
            case 'TensorGelu':
            case 'TensorSilu':
            case 'TensorReLu':
            case 'TensorLeakyReLu':
            case 'TensorSigmoid':
            case 'TensorTanh':
            case 'TensorElu':
            case 'TensorSoftmax':
            case 'TensorLogSoftmax':
            case 'TensorExp':
            case 'TensorLog':
            case 'TensorSqrt':
            case 'TensorInv':
            case 'TensorNeg':
            case 'Dropout':
            case 'PositionalEncoding':
            case 'RotaryEmbedding':
                res.resultType = 'Tensor'
                if (operands.size() >= 1) {
                    List<Object> sx = extractShape(operands.get(0))
                    if (sx != null) {
                        res.shape = sx
                        res.rank = sx.size()
                    }
                }
                break

            // 2. Matrix Product: (m, k) x (k, n) -> (m, n)
            case 'MatrixProduct':
                res.resultType = 'Matrix'
                if (operands.size() >= 2) {
                    List<Object> sA = extractShape(operands.get(0))
                    List<Object> sB = extractShape(operands.get(1))
                    if (sA != null && sB != null && sA.size() >= 2 && sB.size() >= 2) {
                        Object rA = sA.get(0)
                        Object cA = sA.get(1)
                        Object rB = sB.get(0)
                        Object cB = sB.get(1)

                        if (isNumber(cA) && isNumber(rB) && toInt(cA) != toInt(rB)) {
                            throwShapeError(callerFile, callerLine,
                                "Incompatible shapes for matrix product: ${sA} and ${sB} (inner dimensions ${cA} != ${rB})")
                        }
                        if (isNumber(rA)) res.rows = toInt(rA)
                        if (isNumber(cB)) res.cols = toInt(cB)
                        res.shape = [res.rows, res.cols]
                        res.rank = 2
                    }
                }
                break

            // 3. Affine / Linear Map: (..., n) x (p, n) -> (..., p) or Matrix x Vector -> Vector
            case 'Affine':
            case 'LinearMap':
            case 'WarpAffine':
                if (operands.size() >= 2) {
                    boolean op0IsVector = isVectorOperand(operands.get(0))
                    boolean op1IsVector = isVectorOperand(operands.get(1))
                    boolean op0IsMatrix = isMatrixOperand(operands.get(0))
                    boolean op1IsMatrix = isMatrixOperand(operands.get(1))

                    if ((op0IsMatrix && op1IsVector) || (op0IsVector && op1IsMatrix)) {
                        res.resultType = 'Vector'
                        List<Object> sMat = op0IsMatrix ? extractShape(operands.get(0)) : extractShape(operands.get(1))
                        List<Object> sVec = op0IsVector ? extractShape(operands.get(0)) : extractShape(operands.get(1))
                        if (sMat != null && sVec != null && sMat.size() >= 2 && !sVec.empty) {
                            Object vDim = sVec.get(0)
                            Object mRows = sMat.get(0)
                            Object mCols = sMat.get(1)
                            if (isNumber(vDim) && isNumber(mCols) && toInt(vDim) == toInt(mCols)) {
                                res.dimension = toInt(mRows)
                            } else if (isNumber(vDim) && isNumber(mRows) && toInt(vDim) == toInt(mRows)) {
                                res.dimension = toInt(mCols)
                            } else if (isNumber(vDim) && isNumber(mCols) && isNumber(mRows)) {
                                throwShapeError(callerFile, callerLine,
                                    "Incompatible shapes for Affine matrix-vector product: Matrix ${sMat} and Vector ${sVec}")
                            } else {
                                res.dimension = toInt(mRows)
                            }
                        }
                    } else {
                        res.resultType = 'Tensor'
                        List<Object> sx = extractShape(operands.get(0))
                        List<Object> sW = extractShape(operands.get(1))
                        if (sx != null && sW != null && !sx.empty && sW.size() >= 2) {
                            Object inDim = sx.get(sx.size() - 1)
                            Object w0 = sW.get(0)
                            Object w1 = sW.get(1)
                            Object outDim = null

                            if (w1 == inDim || (isNumber(w1) && isNumber(inDim) && toInt(w1) == toInt(inDim))) {
                                outDim = w0
                            } else if (w0 == inDim || (isNumber(w0) && isNumber(inDim) && toInt(w0) == toInt(inDim))) {
                                outDim = w1
                            } else if (isNumber(w0) && isNumber(w1) && isNumber(inDim)) {
                                throwShapeError(callerFile, callerLine,
                                    "Incompatible shapes for Affine linear map: input ${sx} (in_dim ${inDim}) and weight ${sW}")
                            } else {
                                outDim = w0
                            }

                            List<Object> outShape = new ArrayList<>(sx.subList(0, sx.size() - 1))
                            outShape.add(outDim)
                            res.shape = outShape
                            res.rank = outShape.size()
                        }
                    }
                }
                break

            // 4. Tensor Contraction
            case 'TensorContract':
                res.resultType = 'Tensor'
                if (operands.size() >= 2) {
                    List<Object> sA = extractShape(operands.get(0))
                    List<Object> sB = extractShape(operands.get(1))
                    if (sA != null && sB != null) {
                        List<Object> out = new ArrayList<>()
                        if (sA.size() > 1) out.addAll(sA.subList(0, sA.size() - 1))
                        if (sB.size() > 1) out.addAll(sB.subList(1, sB.size()))
                        res.shape = out
                        res.rank = out.size()
                    }
                }
                break

            // 5. Element-wise arithmetic with NumPy broadcasting
            case 'TensorAdd':
            case 'TensorSub':
            case 'TensorMul':
            case 'TensorDiv':
            case 'TensorPow':
                res.resultType = 'Tensor'
                if (operands.size() >= 2) {
                    List<Object> sA = extractShape(operands.get(0))
                    List<Object> sB = extractShape(operands.get(1))
                    if (sA != null && sB != null) {
                        List<Object> bc = broadcastShapes(sA, sB, callerFile, callerLine, normType)
                        res.shape = bc
                        res.rank = bc.size()
                    } else if (sA != null) {
                        res.shape = sA
                        res.rank = sA.size()
                    } else if (sB != null) {
                        res.shape = sB
                        res.rank = sB.size()
                    }
                }
                break

            // 6. Reshape
            case 'TensorReshape':
                res.resultType = 'Tensor'
                Object targetShapeObj = options.get('shape') ?: options.get('targetShape')
                List<Object> targetShape = parseShapeList(targetShapeObj)
                if (operands.size() >= 1 && targetShape != null) {
                    List<Object> inShape = extractShape(operands.get(0))
                    if (inShape != null && allNumbers(inShape) && allNumbers(targetShape)) {
                        long inCount = product(inShape)
                        long outCount = product(targetShape)
                        if (inCount != outCount) {
                            throwShapeError(callerFile, callerLine,
                                "Reshape element count mismatch: input shape ${inShape} (${inCount} elements) != target shape ${targetShape} (${outCount} elements)")
                        }
                    }
                    res.shape = targetShape
                    res.rank = targetShape.size()
                }
                break

            // 7. Permute / Transpose
            case 'TensorPermute':
            case 'MatrixTranspose':
                if (operands.size() >= 1) {
                    List<Object> inShape = extractShape(operands.get(0))
                    if (inShape != null) {
                        Object dimsObj = options.get('dims') ?: options.get('axes') ?: options.get('permutation')
                        List<Object> dims = parseShapeList(dimsObj)
                        if (dims != null && dims.size() == inShape.size()) {
                            List<Object> outShape = new ArrayList<>()
                            for (Object d : dims) {
                                int idx = toInt(d)
                                if (idx >= 0 && idx < inShape.size()) {
                                    outShape.add(inShape.get(idx))
                                }
                            }
                            res.shape = outShape
                            res.rank = outShape.size()
                        } else if (normType == 'MatrixTranspose' && inShape.size() == 2) {
                            res.shape = [inShape.get(1), inShape.get(0)]
                            res.rows = toInt(inShape.get(1))
                            res.cols = toInt(inShape.get(0))
                            res.rank = 2
                            res.resultType = 'Matrix'
                        }
                    }
                }
                break

            // 8. Scaled Dot-Product Attention: (B, H, T, d) -> (B, H, T, d)
            case 'ScaledDotProductAttention':
                res.resultType = 'Tensor'
                if (operands.size() >= 1) {
                    List<Object> sQ = extractShape(operands.get(0))
                    if (sQ != null) {
                        res.shape = sQ
                        res.rank = sQ.size()
                    }
                }
                break

            // 9. Multi-Head Attention: (B, T, D) -> (B, T, D) with D % numHeads == 0
            case 'MultiHeadAttention':
                res.resultType = 'Tensor'
                if (operands.size() >= 1) {
                    List<Object> sx = extractShape(operands.get(0))
                    Object numHeadsObj = options.get('numHeads')
                    Object embedDimObj = options.get('embedDim') ?: (sx != null && !sx.empty ? sx.get(sx.size() - 1) : null)

                    if (numHeadsObj != null && embedDimObj != null && isNumber(numHeadsObj) && isNumber(embedDimObj)) {
                        int h = toInt(numHeadsObj)
                        int d = toInt(embedDimObj)
                        if (h > 0 && d % h != 0) {
                            throwShapeError(callerFile, callerLine,
                                "Embedding dimension ${d} is not divisible by numHeads ${h} (D % numHeads != 0)")
                        }
                    }
                    if (sx != null) {
                        res.shape = sx
                        res.rank = sx.size()
                    }
                }
                break

            // 10. Embedding: (B, T) + (V, D) -> (B, T, D)
            case 'Embedding':
            case 'TensorEmbedding':
                res.resultType = 'Tensor'
                if (operands.size() >= 2) {
                    List<Object> sIdx = extractShape(operands.get(0))
                    List<Object> sW = extractShape(operands.get(1))
                    if (sIdx != null && sW != null && sW.size() >= 2) {
                        List<Object> out = new ArrayList<>(sIdx)
                        out.add(sW.get(sW.size() - 1))
                        res.shape = out
                        res.rank = out.size()
                    }
                }
                break

            // 11. Reductions: TensorSum, TensorMean, TensorMax, TensorMin
            case 'TensorSum':
            case 'TensorMean':
            case 'TensorMax':
            case 'TensorMin':
                res.resultType = 'Tensor'
                if (operands.size() >= 1) {
                    List<Object> sx = extractShape(operands.get(0))
                    if (sx != null) {
                        Object axisObj = options.containsKey('axis') ? options.get('axis') : options.get('dim')
                        boolean keepdim = Boolean.TRUE.equals(options.get('keepdim')) || Boolean.TRUE.equals(options.get('keepDim'))
                        if (axisObj != null && isNumber(axisObj)) {
                            int ax = toInt(axisObj)
                            if (ax < 0) ax += sx.size()
                            List<Object> out = new ArrayList<>(sx)
                            if (ax >= 0 && ax < out.size()) {
                                if (keepdim) {
                                    out.set(ax, 1)
                                } else {
                                    out.remove(ax)
                                    if (out.empty) out.add(1)
                                }
                            }
                            res.shape = out
                            res.rank = out.size()
                        } else {
                            if (keepdim) {
                                res.shape = (List<Object>) sx.collect { 1 }
                            } else {
                                res.shape = [1]
                            }
                            res.rank = res.shape.size()
                        }
                    }
                }
                break
        }

        res
    }

    static List<Object> extractShape(final Object op) {
        if (op == null) return null
        if (op instanceof ModelProvider) {
            ModelValue mv = ((ModelProvider) op).get()
            if (mv == null) return null
            String entityName = ((ModelProvider) op).definition.name
            if (entityName == 'Matrix') {
                Object r = mv.get('rows')
                Object c = mv.get('cols')
                if (r != null && c != null) {
                    return [toDim(r), toDim(c)]
                }
            } else if (entityName == 'Vector') {
                Object d = mv.get('dimension')
                if (d != null) return [toDim(d)]
            } else if (entityName == 'Tensor') {
                Object s = mv.get('shape')
                return parseShapeList(s)
            }
        } else if (op instanceof ModelValue) {
            ModelValue mv = (ModelValue) op
            String entityName = mv.definition.name
            if (entityName == 'Matrix') {
                Object r = mv.get('rows')
                Object c = mv.get('cols')
                if (r != null && c != null) return [toDim(r), toDim(c)]
            } else if (entityName == 'Vector') {
                Object d = mv.get('dimension')
                if (d != null) return [toDim(d)]
            } else if (entityName == 'Tensor') {
                Object s = mv.get('shape')
                return parseShapeList(s)
            }
        } else if (op instanceof List) {
            return inferListShape((List<?>) op)
        }
        null
    }

    static List<Object> parseShapeList(final Object s) {
        if (s == null) return null
        if (s instanceof List) {
            List<Object> res = new ArrayList<>()
            for (Object item : (List<?>) s) res.add(toDim(item))
            return res
        }
        if (s instanceof CharSequence) {
            String str = s.toString().trim()
            try {
                Object parsed = new JsonSlurper().parseText(str)
                if (parsed instanceof List) {
                    List<Object> res = new ArrayList<>()
                    for (Object item : (List<?>) parsed) res.add(toDim(item))
                    return res
                }
            } catch (Exception ignored) {
            }
        }
        null
    }

    static List<Object> broadcastShapes(final List<Object> s1, final List<Object> s2,
                                        final String callerFile, final int callerLine, final String opName) {
        if (s1 == null || s1.empty) return s2
        if (s2 == null || s2.empty) return s1

        int len1 = s1.size()
        int len2 = s2.size()
        int maxLen = Math.max(len1, len2)
        List<Object> result = new ArrayList<>(maxLen)

        for (int i = 0; i < maxLen; i++) {
            Object d1 = (i < maxLen - len1) ? 1 : s1.get(i - (maxLen - len1))
            Object d2 = (i < maxLen - len2) ? 1 : s2.get(i - (maxLen - len2))

            if (d1 == d2 || d1?.toString() == d2?.toString()) {
                result.add(d1)
            } else if (isOne(d1)) {
                result.add(d2)
            } else if (isOne(d2)) {
                result.add(d1)
            } else if (isSymbol(d1) && isSymbol(d2)) {
                result.add(d1)
            } else if (isSymbol(d1) || isSymbol(d2)) {
                result.add(isSymbol(d1) ? d1 : d2)
            } else if (isNumber(d1) && isNumber(d2)) {
                int n1 = toInt(d1)
                int n2 = toInt(d2)
                if (n1 != n2) {
                    throwShapeError(callerFile, callerLine,
                        "Incompatible shapes for ${opName} broadcasting: ${s1} and ${s2} (dimension mismatch ${n1} != ${n2})")
                }
                result.add(n1)
            } else {
                throwShapeError(callerFile, callerLine,
                    "Incompatible shapes for ${opName} broadcasting: ${s1} and ${s2}")
            }
        }
        result
    }

    private static List<Object> inferListShape(final List<?> list) {
        if (list == null || list.empty) return []
        List<Object> shape = [list.size()]
        if (list.get(0) instanceof List) {
            shape.addAll(inferListShape((List<?>) list.get(0)))
        }
        shape
    }

    private static boolean isVectorOperand(final Object op) {
        if (op instanceof ModelProvider) {
            return ((ModelProvider) op).definition.name == 'Vector'
        }
        if (op instanceof ModelValue) {
            return ((ModelValue) op).definition.name == 'Vector'
        }
        false
    }

    private static boolean isMatrixOperand(final Object op) {
        if (op instanceof ModelProvider) {
            return ((ModelProvider) op).definition.name == 'Matrix'
        }
        if (op instanceof ModelValue) {
            return ((ModelValue) op).definition.name == 'Matrix'
        }
        false
    }

    private static boolean isOne(final Object d) {
        if (d instanceof Number) return ((Number) d).intValue() == 1
        if (d instanceof CharSequence) return d.toString() == '1'
        false
    }

    private static boolean isSymbol(final Object d) {
        if (d instanceof CharSequence) {
            String s = d.toString().trim()
            return !s.isInteger()
        }
        !(d instanceof Number)
    }

    private static boolean isNumber(final Object d) {
        if (d instanceof Number) return true
        if (d instanceof CharSequence) return d.toString().trim().isInteger()
        false
    }

    private static int toInt(final Object d) {
        if (d instanceof Number) return ((Number) d).intValue()
        if (d instanceof CharSequence) return Integer.parseInt(d.toString().trim())
        0
    }

    private static Object toDim(final Object val) {
        if (val instanceof Number) return ((Number) val).intValue()
        if (val instanceof CharSequence) {
            String str = val.toString().trim()
            if (str.isInteger()) return Integer.parseInt(str)
            return str
        }
        val
    }

    private static boolean allNumbers(final List<Object> shape) {
        for (Object d : shape) {
            if (!isNumber(d)) return false
        }
        true
    }

    private static long product(final List<Object> shape) {
        long prod = 1L
        for (Object d : shape) {
            prod *= toInt(d)
        }
        prod
    }

    static void throwShapeError(final String file, final int line, final String reason) {
        String loc = (file != null && line > 0) ? "${file}:${line}: " : ""
        throw new IllegalArgumentException("${loc}${reason}".toString())
    }
}
