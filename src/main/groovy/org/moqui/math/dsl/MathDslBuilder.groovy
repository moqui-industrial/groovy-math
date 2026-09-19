/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.transform.TypeCheckingMode
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.FieldDefinition
import org.moqui.math.entity.ModelProvider
import org.moqui.math.entity.ModelValue
import org.moqui.math.entity.NamedModelContainer
import org.moqui.math.entity.RelationshipDefinition

@CompileStatic
final class MathDslBuilder {
    final MathMeta mathMeta
    final DslVocabulary vocabulary

    private final Map<String, Object> localVariables = new LinkedHashMap<>()

    private static final ThreadLocal<MathDslBuilder> ACTIVE_BUILDER = new ThreadLocal<>()

    static MathDslBuilder getActiveBuilder() {
        ACTIVE_BUILDER.get()
    }

    MathDslBuilder(final MathMeta mathMeta) {
        this.mathMeta = Objects.requireNonNull(mathMeta, 'Math metadata must not be null')
        this.vocabulary = DslVocabulary.of(mathMeta.definition)
        ACTIVE_BUILDER.set(this)
    }

    ModelProvider entity(final String entityName, final String modelKey,
                         final Map<String, ?> values = Collections.emptyMap(),
                         final Closure<?> action = null) {
        EntityDefinition definition = vocabulary.findEntity(entityName)
        LinkedHashMap<String, Object> attributes = copyValues(values)
        declare(definition, modelKey, attributes, action, null, null).provider
    }

    // Top-level literal & data constructors
    ModelProvider matrix(final Object... args) {
        Map<String, Object> options = new LinkedHashMap<>()
        String key = null
        List<Number> positionalNumbers = new ArrayList<>()
        for (Object arg : args) {
            if (arg instanceof CharSequence && key == null) {
                key = arg.toString()
            } else if (arg instanceof Map) {
                options.putAll((Map<String, Object>) arg)
            } else if (arg instanceof List) {
                options.put('componentArray', arg)
            } else if (arg instanceof Number) {
                positionalNumbers.add((Number) arg)
            }
        }
        if (positionalNumbers.size() >= 2) {
            options.putIfAbsent('rows', positionalNumbers.get(0).intValue())
            options.putIfAbsent('cols', positionalNumbers.get(1).intValue())
        }
        if (key == null) {
            key = options.remove('_key')?.toString() ?: options.get('name')?.toString() ?: "Matrix_${System.identityHashCode(options)}"
        }
        inferMatrixPipelineDimensions(key, options)
        ModelProvider p = declare(vocabulary.findEntity('Matrix'), key, options, null, null, null).provider
        localVariables.put(key, p)
        p
    }

    private void inferMatrixPipelineDimensions(final String key, final Map<String, Object> options) {
        if ((!options.containsKey('rows') || !options.containsKey('cols')) && key != null && mathMeta.hasEntity('Transformation')) {
            ModelValue tf = (ModelValue) mathMeta.entity('Transformation').find { Object v ->
                v instanceof ModelValue && ((ModelValue) v).get('resultMatrixId') == key
            }
            if (tf != null) {
                String type = tf.get('transformationTypeEnumId')?.toString()
                if (type == 'TtMatrixProduct' || type == 'MatrixProduct') {
                    String leftId = null
                    String rightId = null
                    if (mathMeta.hasEntity('TransformationOperand')) {
                        mathMeta.entity('TransformationOperand').each { Object op ->
                            if (op instanceof ModelValue && op.get('transformationId') == tf.modelKey) {
                                String opType = op.get('operandTypeEnumId')?.toString()
                                if (opType == 'TotLeft' || opType == 'TotLeftMatrix' || op.get('operandIndex') == 0L) {
                                    leftId = op.get('operandMatrixId')?.toString()
                                } else if (opType == 'TotRight' || opType == 'TotRightMatrix' || op.get('operandIndex') == 1L) {
                                    rightId = op.get('operandMatrixId')?.toString()
                                }
                            }
                        }
                    }
                    ModelValue leftMat = leftId ? mathMeta.entity('Matrix')?.findByName(leftId) : null
                    ModelValue rightMat = rightId ? mathMeta.entity('Matrix')?.findByName(rightId) : null
                    Object r = leftMat?.get('rows')
                    Object c = rightMat?.get('cols')
                    if (r != null) options.putIfAbsent('rows', r)
                    if (c != null) options.putIfAbsent('cols', c)
                    if (rightMat?.get('domainSpaceEnumId') != null) {
                        options.putIfAbsent('domainSpaceEnumId', rightMat.get('domainSpaceEnumId'))
                    } else if (c != null) {
                        options.putIfAbsent('domainSpaceEnumId', c == 3 ? 'Eng3DEuclideanSpace' : 'Eng2DEuclideanSpace')
                    }
                    if (leftMat?.get('codomainSpaceEnumId') != null) {
                        options.putIfAbsent('codomainSpaceEnumId', leftMat.get('codomainSpaceEnumId'))
                    } else if (r != null) {
                        options.putIfAbsent('codomainSpaceEnumId', r == 3 ? 'Eng3DEuclideanSpace' : 'Eng2DEuclideanSpace')
                    }
                }
            }
        }
    }

    ModelProvider vector(final Object... args) {
        Map<String, Object> options = new LinkedHashMap<>()
        String key = null
        List<Number> positionalNumbers = new ArrayList<>()
        for (Object arg : args) {
            if (arg instanceof CharSequence && key == null) {
                key = arg.toString()
            } else if (arg instanceof Map) {
                options.putAll((Map<String, Object>) arg)
            } else if (arg instanceof List) {
                options.put('componentArray', arg)
            } else if (arg instanceof Number) {
                positionalNumbers.add((Number) arg)
            }
        }
        if (positionalNumbers.size() >= 1) {
            options.putIfAbsent('dimension', positionalNumbers.get(0).intValue())
        }
        if (key == null) {
            key = options.remove('_key')?.toString() ?: options.get('name')?.toString() ?: "Vector_${System.identityHashCode(options)}"
        }
        ModelProvider p = declare(vocabulary.findEntity('Vector'), key, options, null, null, null).provider
        localVariables.put(key, p)
        p
    }

    ModelProvider tensor(final Object... args) {
        Map<String, Object> options = new LinkedHashMap<>()
        String key = null
        for (Object arg : args) {
            if (arg instanceof CharSequence && key == null) {
                key = arg.toString()
            } else if (arg instanceof Map) {
                options.putAll((Map<String, Object>) arg)
            } else if (arg instanceof List) {
                options.put('componentArray', arg)
            }
        }
        if (key == null) {
            key = options.remove('_key')?.toString() ?: options.get('name')?.toString() ?: "Tensor_${System.identityHashCode(options)}"
        }
        ModelProvider p = declare(vocabulary.findEntity('Tensor'), key, options, null, null, null).provider
        localVariables.put(key, p)
        p
    }

    ModelProvider eye(final int n) {
        List<List<Double>> data = new ArrayList<>()
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < n; j++) row.add(i == j ? 1.0d : 0.0d)
            data.add(row)
        }
        matrix(data, [name: "Eye_${n}", matrixType: 'Dense'])
    }

    ModelProvider zeros(final int rows, final int cols) {
        List<List<Double>> data = new ArrayList<>()
        for (int i = 0; i < rows; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < cols; j++) row.add(0.0d)
            data.add(row)
        }
        matrix(data, [name: "Zeros_${rows}x${cols}", matrixType: 'Dense'])
    }

    ModelProvider ones(final int rows, final int cols) {
        List<List<Double>> data = new ArrayList<>()
        for (int i = 0; i < rows; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < cols; j++) row.add(1.0d)
            data.add(row)
        }
        matrix(data, [name: "Ones_${rows}x${cols}", matrixType: 'Dense'])
    }

    ModelProvider diag(final List<Number> diagonal) {
        int n = diagonal.size()
        List<List<Double>> data = new ArrayList<>()
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>()
            for (int j = 0; j < n; j++) row.add(i == j ? diagonal.get(i).doubleValue() : 0.0d)
            data.add(row)
        }
        matrix(data, [name: "Diag_${n}", matrixType: 'Diagonal'])
    }

    // Top-level functions
    DecompositionResult svd(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String baseKey = options.get('name')?.toString() ?: "Svd_${System.identityHashCode(operand)}"
        String uKey = "${baseKey}_U"
        String sigmaKey = "${baseKey}_Sigma"
        String vtKey = "${baseKey}_Vt"

        ModelProvider u = matrix([name: uKey, matrixType: 'Dense'])
        ModelProvider sigma = vector([name: sigmaKey])
        ModelProvider vt = matrix([name: vtKey, matrixType: 'Dense'])

        String tKey = "Decomp_${baseKey}"
        mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtSvd',
            name: "SVD Decomposition of ${operand}"
        ])
        mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: (operand instanceof ModelProvider ? ((ModelProvider) operand).name : operand?.toString())
        ])
        mathMeta.declare('moqui.math.MatrixDecomposition', baseKey, [
            decompositionId: baseKey,
            transformationId: tKey,
            decompMethodEnumId: 'TdmSvd',
            uMatrixId: uKey,
            sigmaVectorId: sigmaKey,
            vMatrixId: vtKey,
            rankApproximation: options.get('rankApproximation')
        ])

        new DecompositionResult(u, sigma, vt)
    }

    ModelProvider diagonalExtraction(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "DiagExt_${System.identityHashCode(operand)}"
        ModelProvider res = vector([name: key])
        String tKey = "T_${key}"
        mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtDiagonalExtraction',
            name: key,
            resultVectorId: key
        ])
        mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: (operand instanceof ModelProvider ? ((ModelProvider) operand).name : operand?.toString())
        ])
        mathMeta.declare('moqui.math.DiagonalExtraction', key, [
            diagonalExtractionId: key,
            transformationId: tKey,
            axisOffset: options.get('axisOffset') ?: 0
        ])
        res
    }

    ModelProvider triangularExtraction(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "TriExt_${System.identityHashCode(operand)}"
        ModelProvider res = matrix([name: key, matrixType: 'Dense'])
        String tKey = "T_${key}"
        mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtTriangularExtraction',
            name: key,
            resultMatrixId: key
        ])
        mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: (operand instanceof ModelProvider ? ((ModelProvider) operand).name : operand?.toString())
        ])
        Object typeVal = options.get('type')
        String tetId = typeVal == 'Lower' || typeVal == TriangularExtractionType.Lower ? 'TetLower' : 'TetUpper'
        mathMeta.declare('moqui.math.TriangularExtraction', key, [
            triangularExtractionId: key,
            transformationId: tKey,
            triangularTypeEnumId: tetId
        ])
        res
    }

    ModelProvider normResult(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "Norm_${System.identityHashCode(operand)}"
        ModelProvider res = vector([name: key, dimension: 1])
        String tKey = "T_${key}"
        mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtNorm',
            name: key,
            resultVectorId: key
        ])
        mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: (operand instanceof ModelProvider ? ((ModelProvider) operand).name : operand?.toString())
        ])
        String domainEnumId = vocabulary.resolveSymbolForField(options.get('domain')?.toString(), 'NormDomain')?.id ?: 'NdMatrix'
        String orderEnumId = vocabulary.resolveSymbolForField(options.get('order')?.toString(), 'NormOrder')?.id ?: 'NoMatFrobenius'
        mathMeta.declare('moqui.math.NormResult', key, [
            normResultId: key,
            transformationId: tKey,
            domainEnumId: domainEnumId,
            orderEnumId: orderEnumId,
            normValue: options.get('normValue') ?: 0.0d
        ])
        res
    }

    ModelProvider matrixProduct(final Object left, final Object right, final Map<String, Object> options = Collections.emptyMap()) {
        createTransformation('TtMatrixProduct', [left, right], options)
    }

    ModelProvider tensorMul(final Object left, final Object right, final Map<String, Object> options = Collections.emptyMap()) {
        createTransformation('TtTensorMul', [left, right], options)
    }

    ModelProvider tensorAdd(final Object left, final Object right, final Map<String, Object> options = Collections.emptyMap()) {
        createTransformation('TtTensorAdd', [left, right], options)
    }

    ModelProvider tensorSub(final Object left, final Object right, final Map<String, Object> options = Collections.emptyMap()) {
        createTransformation('TtTensorSub', [left, right], options)
    }

    ModelProvider tensorDiv(final Object left, final Object right, final Map<String, Object> options = Collections.emptyMap()) {
        createTransformation('TtTensorDiv', [left, right], options)
    }

    ModelProvider tensorPow(final Object operand, final Object exponent, final Map<String, Object> options = Collections.emptyMap()) {
        createTransformation('TtTensorPow', [operand, exponent], options)
    }

    ModelProvider multiplyOp(final String resultName, final Object left, final Object right) {
        applyMultiply(left, right, [resultId: resultName, transformationId: "T_${resultName}"])
    }

    ModelProvider plusOp(final String resultName, final Object left, final Object right) {
        applyPlus(left, right, [resultId: resultName, transformationId: "T_${resultName}"])
    }

    ModelProvider minusOp(final String resultName, final Object left, final Object right) {
        applyMinus(left, right, [resultId: resultName, transformationId: "T_${resultName}"])
    }

    ModelProvider divOp(final String resultName, final Object left, final Object right) {
        applyDiv(left, right, [resultId: resultName, transformationId: "T_${resultName}"])
    }

    ModelProvider powerOp(final String resultName, final Object left, final Object right) {
        applyPower(left, right, [resultId: resultName, transformationId: "T_${resultName}"])
    }

    ModelProvider applyMultiply(final Object left, final Object right, final Map<String, Object> options = Collections.emptyMap()) {
        String leftType = left instanceof ModelProvider ? ((ModelProvider) left).definition.name : (left instanceof ModelValue ? ((ModelValue) left).definition.name : null)
        if (leftType == 'Matrix') {
            if (right instanceof ModelProvider || right instanceof ModelValue) {
                String rightType = right instanceof ModelProvider ? ((ModelProvider) right).definition.name : ((ModelValue) right).definition.name
                if (rightType == 'Matrix') {
                    return matrixProduct(left, right, options)
                } else if (rightType == 'Vector') {
                    throw new IllegalArgumentException(
                        "TransformationType 'TtMatrixVectorProduct' does not exist in schema; cannot multiply Matrix by Vector"
                    )
                }
            } else if (right instanceof Number) {
                return matrixProduct(left, right, options)
            }
            throw new IllegalArgumentException(
                "Unsupported operator '*' for operands [Matrix, ${right instanceof ModelProvider ? ((ModelProvider) right).definition.name : right?.class?.simpleName}]. Supported operations: Matrix * Matrix (TtMatrixProduct), Tensor * Tensor (TtTensorMul)"
            )
        } else if (leftType == 'Tensor') {
            if (right instanceof ModelProvider || right instanceof ModelValue) {
                String rightType = right instanceof ModelProvider ? ((ModelProvider) right).definition.name : ((ModelValue) right).definition.name
                if (rightType == 'Tensor' || rightType == 'Matrix') {
                    return tensorMul(left, right, options)
                }
            } else if (right instanceof Number) {
                return tensorMul(left, right, options)
            }
            throw new IllegalArgumentException(
                "Unsupported operator '*' for operands [Tensor, ${right instanceof ModelProvider ? ((ModelProvider) right).definition.name : right?.class?.simpleName}]. Supported operations: Tensor * Tensor (TtTensorMul), Matrix * Matrix (TtMatrixProduct)"
            )
        }
        throw new IllegalArgumentException(
            "Unsupported operator '*' for operands [${leftType ?: left?.class?.simpleName}, ${right instanceof ModelProvider ? ((ModelProvider) right).definition.name : right?.class?.simpleName}]. Supported operations: Matrix * Matrix (TtMatrixProduct), Tensor * Tensor (TtTensorMul)"
        )
    }

    ModelProvider applyPlus(final Object left, final Object right, final Map<String, Object> options = Collections.emptyMap()) {
        String leftType = left instanceof ModelProvider ? ((ModelProvider) left).definition.name : (left instanceof ModelValue ? ((ModelValue) left).definition.name : null)
        if (leftType == 'Tensor' || leftType == 'Matrix') {
            if (right instanceof ModelProvider || right instanceof ModelValue) {
                String rightType = right instanceof ModelProvider ? ((ModelProvider) right).definition.name : ((ModelValue) right).definition.name
                if (rightType == 'Tensor' || rightType == 'Matrix') {
                    return tensorAdd(left, right, options)
                }
            }
        }
        throw new IllegalArgumentException(
            "Unsupported operator '+' for operands [${leftType ?: left?.class?.simpleName}, ${right instanceof ModelProvider ? ((ModelProvider) right).definition.name : right?.class?.simpleName}]. Supported operations: Tensor + Tensor (TtTensorAdd)"
        )
    }

    ModelProvider applyMinus(final Object left, final Object right, final Map<String, Object> options = Collections.emptyMap()) {
        String leftType = left instanceof ModelProvider ? ((ModelProvider) left).definition.name : (left instanceof ModelValue ? ((ModelValue) left).definition.name : null)
        if (leftType == 'Tensor' || leftType == 'Matrix') {
            if (right instanceof ModelProvider || right instanceof ModelValue) {
                String rightType = right instanceof ModelProvider ? ((ModelProvider) right).definition.name : ((ModelValue) right).definition.name
                if (rightType == 'Tensor' || rightType == 'Matrix') {
                    return tensorSub(left, right, options)
                }
            }
        }
        throw new IllegalArgumentException(
            "Unsupported operator '-' for operands [${leftType ?: left?.class?.simpleName}, ${right instanceof ModelProvider ? ((ModelProvider) right).definition.name : right?.class?.simpleName}]. Supported operations: Tensor - Tensor (TtTensorSub)"
        )
    }

    ModelProvider applyDiv(final Object left, final Object right, final Map<String, Object> options = Collections.emptyMap()) {
        String leftType = left instanceof ModelProvider ? ((ModelProvider) left).definition.name : (left instanceof ModelValue ? ((ModelValue) left).definition.name : null)
        if (leftType == 'Tensor' || leftType == 'Matrix') {
            if (right instanceof ModelProvider || right instanceof ModelValue) {
                String rightType = right instanceof ModelProvider ? ((ModelProvider) right).definition.name : ((ModelValue) right).definition.name
                if (rightType == 'Tensor' || rightType == 'Matrix') {
                    return tensorDiv(left, right, options)
                }
            }
        }
        throw new IllegalArgumentException(
            "Unsupported operator '/' for operands [${leftType ?: left?.class?.simpleName}, ${right instanceof ModelProvider ? ((ModelProvider) right).definition.name : right?.class?.simpleName}]. Supported operations: Tensor / Tensor (TtTensorDiv)"
        )
    }

    ModelProvider applyPower(final Object left, final Object exponent, final Map<String, Object> options = Collections.emptyMap()) {
        String leftType = left instanceof ModelProvider ? ((ModelProvider) left).definition.name : (left instanceof ModelValue ? ((ModelValue) left).definition.name : null)
        if (leftType == 'Tensor' || leftType == 'Matrix') {
            if (exponent instanceof Number) {
                return tensorPow(left, (Number) exponent, options)
            }
        }
        throw new IllegalArgumentException(
            "Unsupported operator '**' for operands [${leftType ?: left?.class?.simpleName}, ${exponent?.class?.simpleName}]. Supported operations: Tensor ** Number (TtTensorPow)"
        )
    }

    ModelProvider applyNegative(final Object operand) {
        throw new IllegalArgumentException(
            "TransformationType 'TtTensorNeg' does not exist in schema; cannot negate Tensor with unary '-'"
        )
    }

    ModelProvider createTransformation(final String typeEnumId, final List<Object> operands, final Map<String, Object> options = Collections.emptyMap()) {
        String baseName = options.get('name')?.toString() ?: "${typeEnumId.replaceFirst('^Tt', '')}_${System.identityHashCode(operands)}"
        String tKey = options.get('transformationId')?.toString() ?: "T_${baseName}"

        DslShapeInference.InferredShape inferred = DslShapeInference.inferShape(
            typeEnumId, operands, options, getCallerFile(), getCallerLine()
        )

        String resultType = inferred.resultType ?: 'Matrix'
        if (options.containsKey('resultType')) {
            resultType = options.get('resultType').toString()
        }

        String resKey = options.get('resultId')?.toString() ?: baseName
        ModelProvider resultProvider
        if (resultType == 'Tensor') {
            Map<String, Object> tensOptions = new LinkedHashMap<>()
            tensOptions.put('_key', resKey)
            if (options.containsKey('name')) tensOptions.put('name', options.get('name'))
            Object explicitShape = options.get('shape')
            if (explicitShape != null) {
                tensOptions.put('shape', explicitShape)
            } else if (inferred.shape != null) {
                tensOptions.put('shape', groovy.json.JsonOutput.toJson(inferred.shape))
            }
            if (options.containsKey('rank')) {
                tensOptions.put('rank', options.get('rank'))
            } else if (inferred.rank != null) {
                tensOptions.put('rank', inferred.rank)
            }
            if (options.containsKey('size')) tensOptions.put('size', options.get('size'))
            resultProvider = tensor(tensOptions)
        } else if (resultType == 'Vector') {
            Map<String, Object> vecOptions = new LinkedHashMap<>()
            vecOptions.put('_key', resKey)
            if (options.containsKey('name')) vecOptions.put('name', options.get('name'))
            Object dim = options.get('dimension') ?: inferred.dimension
            if (dim != null) vecOptions.put('dimension', dim)
            resultProvider = vector(vecOptions)
        } else {
            Map<String, Object> matOptions = new LinkedHashMap<>()
            matOptions.put('_key', resKey)
            if (options.containsKey('name')) matOptions.put('name', options.get('name'))
            matOptions.put('matrixTypeEnumId', 'MtDense')
            Object r = options.get('rows') ?: inferred.rows
            Object c = options.get('cols') ?: inferred.cols
            if (r != null) matOptions.put('rows', r)
            if (c != null) matOptions.put('cols', c)
            if (c != null) matOptions.put('domainSpaceEnumId', c == 3 ? 'Eng3DEuclideanSpace' : 'Eng2DEuclideanSpace')
            if (r != null) matOptions.put('codomainSpaceEnumId', r == 3 ? 'Eng3DEuclideanSpace' : 'Eng2DEuclideanSpace')
            resultProvider = matrix(matOptions)
        }

        Map<String, Object> transValues = new LinkedHashMap<>()
        transValues.put('transformationId', tKey)
        transValues.put('transformationTypeEnumId', typeEnumId)
        if (options.containsKey('name')) transValues.put('name', options.get('name'))
        if (resultType == 'Matrix') transValues.put('resultMatrixId', resKey)
        else if (resultType == 'Vector') transValues.put('resultVectorId', resKey)
        else if (resultType == 'Tensor') transValues.put('resultTensorId', resKey)

        mathMeta.declare('moqui.math.Transformation', tKey, transValues)

        if (operands.size() == 1) {
            Object op = operands.get(0)
            String opKey = extractOperandKey(op)
            String opType = extractOperandType(op, 'TotSingle')
            Map<String, Object> opValues = new LinkedHashMap<>()
            opValues.put('transformationId', tKey)
            opValues.put('operandIndex', 0L)
            opValues.put('operandTypeEnumId', opType)
            attachOperandTarget(opValues, op, opKey, tKey, 0L)
            mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", opValues)
        } else if (operands.size() == 2) {
            Object op0 = operands.get(0)
            String op0Key = extractOperandKey(op0)
            Map<String, Object> op0Values = new LinkedHashMap<>()
            op0Values.put('transformationId', tKey)
            op0Values.put('operandIndex', 0L)
            op0Values.put('operandTypeEnumId', 'TotLeft')
            attachOperandTarget(op0Values, op0, op0Key, tKey, 0L)
            mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", op0Values)

            Object op1 = operands.get(1)
            String op1Key = extractOperandKey(op1)
            Map<String, Object> op1Values = new LinkedHashMap<>()
            op1Values.put('transformationId', tKey)
            op1Values.put('operandIndex', 1L)
            op1Values.put('operandTypeEnumId', 'TotRight')
            attachOperandTarget(op1Values, op1, op1Key, tKey, 1L)
            mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_1", op1Values)
        } else {
            for (int i = 0; i < operands.size(); i++) {
                Object op = operands.get(i)
                String opKey = extractOperandKey(op)
                Map<String, Object> opValues = new LinkedHashMap<>()
                opValues.put('transformationId', tKey)
                opValues.put('operandIndex', (long) i)
                opValues.put('operandTypeEnumId', 'TotNth')
                attachOperandTarget(opValues, op, opKey, tKey, (long) i)
                mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_${i}", opValues)
            }
        }

        resultProvider
    }

    private static String extractOperandKey(final Object op) {
        if (op instanceof ModelProvider) return ((ModelProvider) op).name
        if (op instanceof ModelValue) return ((ModelValue) op).modelKey
        if (op instanceof CharSequence) return op.toString()
        if (op != null) return op.toString()
        null
    }

    private static String extractOperandType(final Object op, final String defaultType) {
        if (op instanceof ModelProvider) {
            String eName = ((ModelProvider) op).definition.name
            if (eName == 'Matrix') return 'TotMatrix'
            if (eName == 'Vector') return 'TotVector'
            if (eName == 'Tensor') return 'TotTensor'
            if (eName == 'Transformation') return 'TotTransformation'
            if (eName == 'Parameter') return 'TotParameter'
        }
        defaultType
    }

    private void attachOperandTarget(final Map<String, Object> opValues, final Object op, final String opKey, final String tKey, final long opIdx) {
        if (op instanceof ModelProvider) {
            String eName = ((ModelProvider) op).definition.name
            if (eName == 'Matrix') opValues.put('operandMatrixId', opKey)
            else if (eName == 'Vector') opValues.put('operandVectorId', opKey)
            else if (eName == 'Tensor') opValues.put('operandTensorId', opKey)
            else if (eName == 'Transformation') opValues.put('operandTransformationId', opKey)
            else if (eName == 'Parameter') opValues.put('operandParameterId', opKey)
            else opValues.put('operandMatrixId', opKey)
        } else if (op instanceof Number) {
            String pDefId = 'ScalarParameter'
            if (!mathMeta.hasEntity('moqui.math.ParameterDef') || mathMeta.entity('moqui.math.ParameterDef').find { it.modelKey == pDefId } == null) {
                mathMeta.declare('moqui.math.ParameterDef', pDefId, [
                    parameterDefId: pDefId,
                    parameterTypeEnumId: 'PtNumberDecimal',
                    parameterCode: 'Scalar',
                    parameterName: 'Scalar Parameter'
                ])
            }
            String pKey = "Param_${tKey}_Op_${opIdx}"
            mathMeta.declare('moqui.math.Parameter', pKey, [
                parameterId: pKey,
                parameterDefId: pDefId,
                numericValue: ((Number) op).doubleValue()
            ])
            opValues.put('operandParameterId', pKey)
            opValues.put('operandTypeEnumId', 'TotScalar')
        } else if (op instanceof CharSequence) {
            opValues.put('operandMatrixId', op.toString())
        }
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String entityName, final Object rawArguments) {
        List<Object> arguments = normalizeArguments(rawArguments)
        if (vocabulary.hasEntity(entityName)) {
            EntityDefinition entityDefinition = vocabulary.findEntity(entityName)
            ParsedDeclaration parsed = parseArguments(entityName, arguments)
            return declare(entityDefinition, parsed.modelKey, parsed.values, parsed.action, null, null).provider
        }

        TransformationType tt = TransformationType.fromName(entityName)
        String ttId = null
        if (tt != null) {
            ttId = tt.id
        } else {
            DslSymbol sym = vocabulary.resolveSymbol(entityName, 'TransformationType')
            if (sym != null) ttId = sym.id
            else if (entityName.equalsIgnoreCase('dropout')) ttId = 'TtDropout'
            else if (entityName.equalsIgnoreCase('multiHeadAttention')) ttId = 'TtMultiHeadAttention'
            else if (entityName.equalsIgnoreCase('positionalEncoding')) ttId = 'TtPositionalEncoding'
            else if (entityName.equalsIgnoreCase('rotaryEmbedding')) ttId = 'TtRotaryEmbedding'
        }
        if (ttId != null) {
            List<Object> operands = new ArrayList<>()
            Map<String, Object> options = new LinkedHashMap<>()
            String explicitName = null
            List<Object> nonMapArgs = new ArrayList<>()
            for (Object arg : arguments) {
                if (arg instanceof Map) {
                    options.putAll((Map<String, Object>) arg)
                } else {
                    nonMapArgs.add(arg)
                }
            }
            if (!nonMapArgs.empty) {
                Object firstNonMap = nonMapArgs.get(0)
                if ((firstNonMap instanceof CharSequence || firstNonMap instanceof DslDeferredSymbol) && nonMapArgs.size() > 1) {
                    explicitName = firstNonMap.toString()
                    for (int i = 1; i < nonMapArgs.size(); i++) {
                        operands.add(nonMapArgs.get(i))
                    }
                } else {
                    operands.addAll(nonMapArgs)
                }
            }
            if (explicitName != null) {
                options.putIfAbsent('resultId', explicitName)
                options.putIfAbsent('transformationId', "T_${explicitName}")
            }
            return createTransformation(ttId, operands, options)
        }

        EntityDefinition entityDefinition = vocabulary.findEntity(entityName)
        ParsedDeclaration parsed = parseArguments(entityName, arguments)
        declare(entityDefinition, parsed.modelKey, parsed.values, parsed.action, null, null).provider
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object propertyMissing(final String name) {
        if (localVariables.containsKey(name)) return localVariables.get(name)
        new DslDeferredSymbol(name, getCallerFile(), getCallerLine())
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void propertyMissing(final String name, final Object value) {
        localVariables.put(name, value)
        if (value instanceof ModelProvider) {
            ModelProvider mp = (ModelProvider) value
            ModelValue mv = mp.get()
            if (mv != null && (mv.get('symbol') == null || mv.get('symbol') == '')) {
                mv.put('symbol', name)
            }
        }
    }

    @PackageScope
    DslDeclaration declareNested(final String entityName, final Object rawArguments,
                                 final DslDeclaration parent,
                                 final RelationshipDefinition relationship = null) {
        EntityDefinition entityDefinition = vocabulary.findEntity(entityName)
        ParsedDeclaration parsed = parseArguments(entityName, normalizeArguments(rawArguments))
        declare(entityDefinition, parsed.modelKey, parsed.values, parsed.action, parent, relationship)
    }

    private DslDeclaration declare(final EntityDefinition entityDefinition, final String requestedKey,
                                   final LinkedHashMap<String, Object> values, final Closure<?> action,
                                   final DslDeclaration parent,
                                   final RelationshipDefinition relationship) {
        LinkedHashMap<String, Object> normalizedValues = normalizeFieldAliases(entityDefinition, values)
        if (parent != null) {
            inheritRelationshipKeys(parent, entityDefinition, normalizedValues, relationship)
            inheritAncestorKeys(parent, entityDefinition, normalizedValues)
        }
        String modelKey = requestedKey ?: keyFromValues(entityDefinition, normalizedValues)
        if (entityDefinition.name == 'Matrix') {
            inferMatrixPipelineDimensions(modelKey, normalizedValues)
        }
        addSinglePrimaryKey(entityDefinition, modelKey, normalizedValues)

        ModelProvider provider = mathMeta.declare(entityDefinition.fullName, modelKey, normalizedValues)
        DslDeclaration record = new DslDeclaration(entityDefinition, modelKey, normalizedValues, provider, parent)
        if (action != null) new DslRecordDelegate(this, record).configure(action)
        record
    }

    private void inheritRelationshipKeys(final DslDeclaration parent, final EntityDefinition child,
                                         final Map<String, Object> childValues,
                                         final RelationshipDefinition requestedRelationship) {
        RelationshipLink link = requestedRelationship != null ?
            linkForRequestedRelationship(parent.definition, child, requestedRelationship) :
            inferRelationship(parent.definition, child)

        link.parentToChildFields.each { String parentField, String childField ->
            Object inherited = parent.values.get(parentField)
            if (inherited == null) {
                throw new IllegalArgumentException(
                    "Cannot nest ${child.fullName} under ${parent.definition.fullName}: " +
                        "parent field ${parentField} has no declared value"
                )
            }
            Object explicit = childValues.get(childField)
            if (explicit != null && explicit != inherited) {
                throw new IllegalArgumentException(
                    "Nested ${child.fullName}.${childField} '${explicit}' conflicts with " +
                        "${parent.definition.fullName}.${parentField} '${inherited}'"
                )
            }
            childValues.put(childField, inherited)
        }
    }

    private static void inheritAncestorKeys(final DslDeclaration parent, final EntityDefinition child,
                                            final Map<String, Object> childValues) {
        Set<String> childPrimaryKeys = child.primaryKeyFields.collect { FieldDefinition field -> field.name } as Set<String>
        DslDeclaration ancestor = parent
        while (ancestor != null) {
            ancestor.definition.primaryKeyFields.each { FieldDefinition field ->
                if (!childPrimaryKeys.contains(field.name) && child.fields.containsKey(field.name) &&
                    !childValues.containsKey(field.name)) {
                    Object inherited = ancestor.values.get(field.name)
                    if (inherited != null) childValues.put(field.name, inherited)
                }
            }
            ancestor = ancestor.parent
        }
    }

    private static RelationshipLink linkForRequestedRelationship(final EntityDefinition parent,
                                                                  final EntityDefinition child,
                                                                  final RelationshipDefinition relationship) {
        if (relationship.relatedEntityName != child.fullName) {
            throw new IllegalArgumentException(
                "Relationship ${parent.fullName}.${relationship.name} expects " +
                    "${relationship.relatedEntityName}, not ${child.fullName}"
            )
        }
        parentRelationshipLink(parent, child, relationship)
    }

    private static RelationshipLink inferRelationship(final EntityDefinition parent,
                                                       final EntityDefinition child) {
        List<RelationshipLink> parentMany = parent.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.type == 'many' && rel.relatedEntityName == child.fullName }
            .collect { RelationshipDefinition rel -> parentRelationshipLink(parent, child, rel) }
        if (parentMany.size() == 1) return parentMany.first()
        if (parentMany.size() > 1) throw ambiguousRelationship(parent, child, parentMany)

        List<RelationshipLink> childToParent = child.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.type == 'one' && rel.relatedEntityName == parent.fullName }
            .collect { RelationshipDefinition rel -> childRelationshipLink(parent, child, rel) }
        if (childToParent.size() == 1) return childToParent.first()
        if (childToParent.size() > 1) throw ambiguousRelationship(parent, child, childToParent)

        List<RelationshipLink> parentOne = parent.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.type == 'one' && rel.relatedEntityName == child.fullName }
            .collect { RelationshipDefinition rel -> parentRelationshipLink(parent, child, rel) }
        if (parentOne.size() == 1) return parentOne.first()
        if (parentOne.size() > 1) throw ambiguousRelationship(parent, child, parentOne)

        List<RelationshipLink> anyRel = parent.relationships.values()
            .findAll { RelationshipDefinition rel -> rel.relatedEntityName == child.fullName }
            .collect { RelationshipDefinition rel -> parentRelationshipLink(parent, child, rel) }
        if (anyRel.size() == 1) return anyRel.first()
        if (anyRel.size() > 1) throw ambiguousRelationship(parent, child, anyRel)

        throw new IllegalArgumentException("No declared relationship connects ${parent.fullName} to ${child.fullName}")
    }

    private static RelationshipLink parentRelationshipLink(final EntityDefinition parent,
                                                           final EntityDefinition child,
                                                           final RelationshipDefinition relationship) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>()
        if (relationship.keyMap.isEmpty()) {
            String targetPk = child.primaryKeyFields.empty ? null : child.primaryKeyFields.first().name
            if (targetPk != null && parent.fields.containsKey(targetPk)) {
                fields.put(targetPk, targetPk)
            }
        } else {
            relationship.keyMap.each { String parentField, String childField ->
                if (parent.fields.containsKey(parentField) && child.fields.containsKey(childField)) {
                    fields.put(parentField, childField)
                }
            }
        }
        validateRelationshipFields(parent, child, relationship, fields)
        new RelationshipLink(relationship.name, fields)
    }

    private static RelationshipLink childRelationshipLink(final EntityDefinition parent,
                                                          final EntityDefinition child,
                                                          final RelationshipDefinition relationship) {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>()
        if (relationship.keyMap.isEmpty()) {
            String parentPk = parent.primaryKeyFields.empty ? null : parent.primaryKeyFields.first().name
            if (parentPk != null && child.fields.containsKey(parentPk)) {
                fields.put(parentPk, parentPk)
            }
        } else {
            relationship.keyMap.each { String childField, String parentField ->
                if (parent.fields.containsKey(parentField) && child.fields.containsKey(childField)) {
                    fields.put(parentField, childField)
                }
            }
        }
        validateRelationshipFields(parent, child, relationship, fields)
        new RelationshipLink(relationship.name, fields)
    }

    private static void validateRelationshipFields(final EntityDefinition parent, final EntityDefinition child,
                                                   final RelationshipDefinition relationship,
                                                   final Map<String, String> fields) {
        if (fields.isEmpty() && !relationship.keyMap.isEmpty()) {
            throw new IllegalArgumentException(
                "Relationship ${relationship.name} between ${parent.fullName} and ${child.fullName} " +
                    'has no usable key mapping'
            )
        }
    }

    private static IllegalArgumentException ambiguousRelationship(final EntityDefinition parent,
                                                                   final EntityDefinition child,
                                                                   final List<RelationshipLink> links) {
        new IllegalArgumentException(
            "Multiple relationships connect ${parent.fullName} to ${child.fullName}: " +
                "${links*.name.join(', ')}; use a relationship block to disambiguate"
        )
    }

    private static ParsedDeclaration parseArguments(final String entityName, final List<Object> arguments) {
        LinkedHashMap<String, Object> values = new LinkedHashMap<>()
        Closure<?> action
        String modelKey
        arguments.each { Object argument ->
            if (argument instanceof Map) values.putAll((Map<String, Object>) argument)
            else if (argument instanceof Closure) action = (Closure<?>) argument
            else if (argument instanceof CharSequence) modelKey = argument.toString()
            else if (argument instanceof List) values.put('componentArray', argument)
            else throw new IllegalArgumentException(
                "Unsupported argument ${argument?.class?.name} for ${entityName} declaration"
            )
        }
        new ParsedDeclaration(modelKey, values, action)
    }

    private LinkedHashMap<String, Object> copyValues(final Map<String, ?> source) {
        LinkedHashMap<String, Object> values = new LinkedHashMap<>()
        source.each { String name, Object value -> values.put(name, normalizeValue(value)) }
        values
    }

    private static boolean isComputedField(final EntityDefinition definition, final String name) {
        if (definition == null) return false
        if (definition.fullName == 'moqui.math.Matrix' || definition.name == 'Matrix' ||
            definition.fullName == 'moqui.math.Vector' || definition.name == 'Vector') {
            return ['determinant', 'trace', 'rank', 'conditionNumber', 'frobeniusNorm', 'nnz'].contains(name)
        }
        if (definition.fullName == 'moqui.math.Tensor' || definition.name == 'Tensor') {
            return ['determinant', 'trace', 'conditionNumber', 'frobeniusNorm', 'nnz'].contains(name)
        }
        return false
    }

    static int getCallerLine() {
        for (StackTraceElement ste : Thread.currentThread().stackTrace) {
            if (ste.fileName != null && (ste.fileName.endsWith('.groovy') || ste.fileName.endsWith('.gvy')) &&
                !ste.className.startsWith('org.moqui.math.dsl.') &&
                !ste.className.startsWith('org.codehaus.groovy.') &&
                !ste.className.startsWith('groovy.lang.')) {
                return ste.lineNumber
            }
        }
        -1
    }

    static String getCallerFile() {
        for (StackTraceElement ste : Thread.currentThread().stackTrace) {
            if (ste.fileName != null && (ste.fileName.endsWith('.groovy') || ste.fileName.endsWith('.gvy')) &&
                !ste.className.startsWith('org.moqui.math.dsl.') &&
                !ste.className.startsWith('org.codehaus.groovy.') &&
                !ste.className.startsWith('groovy.lang.')) {
                return ste.fileName
            }
        }
        null
    }

    void resolvePendingReferences(final String fileName = null) {
        mathMeta.definition.entities.values().each { EntityDefinition ed ->
            if (mathMeta.hasEntity(ed.fullName)) {
                NamedModelContainer container = mathMeta.entity(ed.fullName)
                container.each { ModelValue mv ->
                    for (String fieldName : new ArrayList<String>(mv.keySet())) {
                        Object val = mv.get(fieldName)
                        if (val instanceof DslDeferredSymbol) {
                            DslDeferredSymbol defSym = (DslDeferredSymbol) val
                            String symName = defSym.name
                            if (localVariables.containsKey(symName)) {
                                Object local = localVariables.get(symName)
                                mv.put(fieldName, local instanceof ModelProvider ? ((ModelProvider) local).name : local)
                            } else {
                                String enumTypeId = ed.enumTypeFor(fieldName) ?: (fieldName == 'statusId' ? 'Status' : null)
                                if (enumTypeId != null) {
                                    DslSymbol resolved = vocabulary.resolveSymbolForField(symName, enumTypeId, ed, fieldName, defSym.sourceFile ?: fileName, defSym.line)
                                    mv.put(fieldName, resolved.id)
                                } else if (fieldName.endsWith('Id') || fieldName.endsWith('Key') || fieldName.endsWith('Ref')) {
                                    mv.put(fieldName, symName)
                                } else {
                                    DslSymbol resolved = vocabulary.resolveSymbolForField(symName, null, ed, fieldName, defSym.sourceFile ?: fileName, defSym.line)
                                    mv.put(fieldName, resolved.id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @PackageScope
    Object normalizeValue(final Object value, final EntityDefinition entity = null, final String fieldName = null) {
        if (value instanceof DslDeferredSymbol) {
            DslDeferredSymbol defSym = (DslDeferredSymbol) value
            String symName = defSym.name
            if (localVariables.containsKey(symName)) {
                Object local = localVariables.get(symName)
                return local instanceof ModelProvider ? ((ModelProvider) local).name : local
            }
            String enumTypeId = entity != null && fieldName != null ? (entity.enumTypeFor(fieldName) ?: (fieldName == 'statusId' ? 'Status' : null)) : null
            if (enumTypeId != null) {
                DslSymbol resolved = vocabulary.resolveSymbolForField(symName, enumTypeId, entity, fieldName, defSym.sourceFile, defSym.line)
                if (resolved != null) return resolved.id
                return symName
            }
            DslSymbol sym = vocabulary.resolveSymbol(symName)
            if (sym != null) return sym.id
            if (fieldName != null && (fieldName.endsWith('Id') || fieldName.endsWith('Key') || fieldName.endsWith('Ref') || fieldName.endsWith('Matrix') || fieldName.endsWith('Vector') || fieldName.endsWith('Tensor'))) {
                return defSym
            }
            DslSymbol resolved = vocabulary.resolveSymbolForField(symName, null, entity, fieldName, defSym.sourceFile, defSym.line)
            if (resolved != null) return resolved.id
            return symName
        }
        if (value instanceof DslSymbol) return ((DslSymbol) value).id
        if (value instanceof DslEnumValue) return ((DslEnumValue) value).id
        if (value instanceof DslSymbolicValue) return ((DslSymbolicValue) value).id
        if (value instanceof Enum<?>) return ((Enum<?>) value).name()
        if (value instanceof org.moqui.math.metamodel.EntityRef) return ((org.moqui.math.metamodel.EntityRef<?>) value).id
        if (value instanceof ModelProvider) return ((ModelProvider) value).name
        if (value instanceof Map) {
            LinkedHashMap<Object, Object> normalized = new LinkedHashMap<>()
            ((Map<?, ?>) value).each { Object key, Object nestedValue -> normalized.put(key, normalizeValue(nestedValue, entity, fieldName)) }
            return normalized
        }
        if (value instanceof Collection) {
            List<Object> normalized = []
            ((Collection<?>) value).each { Object nestedValue -> normalized.add(normalizeValue(nestedValue, entity, fieldName)) }
            return normalized
        }
        if (value instanceof Object[]) {
            List<Object> normalized = []
            ((Object[]) value).each { Object nestedValue -> normalized.add(normalizeValue(nestedValue, entity, fieldName)) }
            return normalized
        }
        value
    }

    @PackageScope
    static String resolveFieldName(final EntityDefinition definition, final String requestedName) {
        if (definition.fields.containsKey(requestedName)) return requestedName
        if (requestedName == 'from' && definition.fields.containsKey('fromVertexId')) return 'fromVertexId'
        if (requestedName == 'to' && definition.fields.containsKey('toVertexId')) return 'toVertexId'
        if (requestedName == 'fromVertex' && definition.fields.containsKey('fromVertexId')) return 'fromVertexId'
        if (requestedName == 'toVertex' && definition.fields.containsKey('toVertexId')) return 'toVertexId'
        if (requestedName == 'source' && definition.fields.containsKey('sourceObjectId')) return 'sourceObjectId'
        if (requestedName == 'target' && definition.fields.containsKey('targetObjectId')) return 'targetObjectId'
        if (requestedName == 'def' && definition.fields.containsKey('parameterDefId')) return 'parameterDefId'
        if (requestedName == 'text' && definition.fields.containsKey('textValue')) return 'textValue'
        if (requestedName == 'symbolic' && definition.fields.containsKey('symbolicValue')) return 'symbolicValue'
        if (requestedName == 'value' && definition.fields.containsKey('numericValue')) return 'numericValue'
        if (requestedName == 'code' && definition.fields.containsKey('parameterCode')) return 'parameterCode'
        if (requestedName == 'alias' && definition.fields.containsKey('modelAlias')) return 'modelAlias'
        if (requestedName == 'alias' && definition.fields.containsKey('parameterAlias')) return 'parameterAlias'
        if (requestedName == 'status' && definition.fields.containsKey('statusId')) return 'statusId'
        if (requestedName == 'type' && definition.fields.containsKey('parameterTypeEnumId')) return 'parameterTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('modelTypeEnumId')) return 'modelTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('transformationTypeEnumId')) return 'transformationTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('matrixTypeEnumId')) return 'matrixTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('vectorTypeEnumId')) return 'vectorTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('tensorTypeEnumId')) return 'tensorTypeEnumId'
        if (requestedName == 'matrixType' && definition.fields.containsKey('matrixTypeEnumId')) return 'matrixTypeEnumId'
        if (requestedName == 'vectorType' && definition.fields.containsKey('vectorTypeEnumId')) return 'vectorTypeEnumId'
        if (requestedName == 'tensorType' && definition.fields.containsKey('tensorTypeEnumId')) return 'tensorTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('meshTypeEnumId')) return 'meshTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('categoryTypeEnumId')) return 'categoryTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('objectTypeEnumId')) return 'objectTypeEnumId'
        if (requestedName == 'type' && definition.fields.containsKey('morphismTypeEnumId')) return 'morphismTypeEnumId'
        if (requestedName == 'usage' && definition.fields.containsKey('usageContextEnumId')) return 'usageContextEnumId'
        if (requestedName == 'purpose' && definition.fields.containsKey('purposeEnumId')) return 'purposeEnumId'
        if (requestedName == 'adaptation' && definition.fields.containsKey('adaptationTypeEnumId')) return 'adaptationTypeEnumId'
        if (requestedName == 'method' && definition.fields.containsKey('solvingMethodEnumId')) return 'solvingMethodEnumId'
        if (requestedName == 'name' && definition.fields.containsKey('parameterName')) return 'parameterName'
        if (requestedName == 'name' && definition.fields.containsKey('categoryName')) return 'categoryName'
        if (requestedName == 'name' && definition.fields.containsKey('objectName')) return 'objectName'
        if (requestedName == 'name' && definition.fields.containsKey('morphismName')) return 'morphismName'
        if (requestedName == 'symbol' && definition.fields.containsKey('objectSymbol')) return 'objectSymbol'
        if (requestedName == 'symbol' && definition.fields.containsKey('morphismSymbol')) return 'morphismSymbol'
        if (requestedName == 'pk' && definition.fields.containsKey('objectPkPrimaryValue')) return 'objectPkPrimaryValue'
        if (requestedName == 'entityName' && definition.fields.containsKey('objectEntityName')) return 'objectEntityName'
        if (requestedName.endsWith('Enum')) {
            String alias = requestedName + 'Id'
            if (definition.fields.containsKey(alias)) return alias
        }
        if (definition.fields.containsKey(requestedName + 'EnumId')) {
            return requestedName + 'EnumId'
        }
        if (definition.fields.containsKey(requestedName + 'Id')) {
            return requestedName + 'Id'
        }
        requestedName
    }

    private LinkedHashMap<String, Object> normalizeFieldAliases(final EntityDefinition definition,
                                                                 final LinkedHashMap<String, Object> source) {
        LinkedHashMap<String, Object> normalized = new LinkedHashMap<>()
        source.each { String name, Object rawValue ->
            if (isComputedField(definition, name)) {
                throw new IllegalArgumentException("campo calcolato: lo popola il provider o la regola entity-eca in Moqui")
            }
            String resolved = resolveFieldName(definition, name)
            if (isComputedField(definition, resolved)) {
                throw new IllegalArgumentException("campo calcolato: lo popola il provider o la regola entity-eca in Moqui")
            }
            if (normalized.containsKey(resolved) && resolved != name) {
                throw new IllegalArgumentException(
                    "Duplicate DSL values for ${definition.fullName}.${resolved} via '${name}' alias")
            }
            normalized.put(resolved, normalizeValue(rawValue, definition, resolved))
        }
        inferStructuralProperties(definition, normalized)
        normalized
    }

    private static void validateComponentArray(final Object comp) {
        if (comp instanceof List) {
            boolean hasNumber = false
            boolean hasString = false
            boolean hasNestedList = false
            for (Object item : (List<?>) comp) {
                if (item instanceof List) {
                    hasNestedList = true
                    validateComponentArray(item)
                } else if (item instanceof Number) {
                    hasNumber = true
                } else if (item instanceof CharSequence) {
                    hasString = true
                }
            }
            if (!hasNestedList && hasNumber && hasString) {
                throw new IllegalArgumentException("Non sono ammessi array con elementi misti di tipo stringa e numero")
            }
        }
    }

    private static List<Integer> inferShape(final Object comp) {
        if (!(comp instanceof List)) return []
        List<?> list = (List<?>) comp
        List<Integer> shape = [list.size()]
        if (!list.isEmpty() && list.get(0) instanceof List) {
            shape.addAll(inferShape(list.get(0)))
        }
        shape
    }

    private static void inferStructuralProperties(final EntityDefinition definition, final LinkedHashMap<String, Object> normalized) {
        if (definition.fields.containsKey('statusId') && !normalized.containsKey('statusId')) {
            if (definition.fullName == 'moqui.math.MathModel' || definition.name == 'MathModel') {
                Object flowId = normalized.get('statusFlowId')
                if (flowId == 'MathModelStatusFlow') normalized.put('statusId', 'MathModelDraft')
            } else if (definition.fullName == 'moqui.math.MathModelRun' || definition.name == 'MathModelRun') {
                Object flowId = normalized.get('statusFlowId')
                if (flowId == 'MathModelRun') normalized.put('statusId', 'MmrQueued')
            }
        }
        if (definition.fullName == 'moqui.math.Matrix' || definition.name == 'Matrix') {
            if (definition.fields.containsKey('matrixTypeEnumId') && !normalized.containsKey('matrixTypeEnumId')) {
                normalized.put('matrixTypeEnumId', 'MtDense')
            }
            Object comp = normalized.get('componentArray')
            if (comp != null) {
                validateComponentArray(comp)
                if (comp instanceof List) {
                    List<?> list = (List<?>) comp
                    if (!list.isEmpty() && list.get(0) instanceof List) {
                        int infRows = list.size()
                        int infCols = ((List<?>) list.get(0)).size()
                        if (normalized.containsKey('rows')) {
                            int decRows = ((Number) normalized.get('rows')).intValue()
                            if (decRows != infRows) {
                                throw new IllegalArgumentException("Declared rows (${decRows}) does not match literal rows (${infRows})")
                            }
                        } else if (definition.fields.containsKey('rows')) {
                            normalized.put('rows', infRows)
                        }
                        if (normalized.containsKey('cols')) {
                            int decCols = ((Number) normalized.get('cols')).intValue()
                            if (decCols != infCols) {
                                throw new IllegalArgumentException("Declared cols (${decCols}) does not match literal cols (${infCols})")
                            }
                        } else if (definition.fields.containsKey('cols')) {
                            normalized.put('cols', infCols)
                        }
                    }
                    normalized.put('componentArray', groovy.json.JsonOutput.toJson(comp))
                }
            }
            if (definition.fields.containsKey('domainSpaceEnumId') && !normalized.containsKey('domainSpaceEnumId')) {
                if (normalized.containsKey('cols') && ((Number) normalized.get('cols')).intValue() == 3) {
                    normalized.put('domainSpaceEnumId', 'Eng3DEuclideanSpace')
                } else {
                    normalized.put('domainSpaceEnumId', 'Eng2DEuclideanSpace')
                }
            }
            if (definition.fields.containsKey('codomainSpaceEnumId') && !normalized.containsKey('codomainSpaceEnumId')) {
                if (normalized.containsKey('rows') && ((Number) normalized.get('rows')).intValue() == 3) {
                    normalized.put('codomainSpaceEnumId', 'Eng3DEuclideanSpace')
                } else {
                    normalized.put('codomainSpaceEnumId', 'Eng2DEuclideanSpace')
                }
            }
        } else if (definition.fullName == 'moqui.math.Vector' || definition.name == 'Vector') {
            Object comp = normalized.get('componentArray')
            if (comp != null) {
                validateComponentArray(comp)
                if (comp instanceof List) {
                    List<?> list = (List<?>) comp
                    int infDim = list.size()
                    if (normalized.containsKey('dimension')) {
                        int decDim = ((Number) normalized.get('dimension')).intValue()
                        if (decDim != infDim) {
                            throw new IllegalArgumentException("Declared dimension (${decDim}) does not match literal dimension (${infDim})")
                        }
                    } else if (definition.fields.containsKey('dimension')) {
                        normalized.put('dimension', infDim)
                    }
                    normalized.put('componentArray', groovy.json.JsonOutput.toJson(comp))
                }
            }
        } else if (definition.fullName == 'moqui.math.Tensor' || definition.name == 'Tensor') {
            Object comp = normalized.remove('componentArray')
            if (comp != null) {
                validateComponentArray(comp)
                if (comp instanceof List) {
                    List<Integer> infShape = inferShape(comp)
                    if (normalized.containsKey('shape')) {
                        Object decShape = normalized.get('shape')
                        List<Integer> decList = null
                        if (decShape instanceof List) decList = (List<Integer>) decShape
                        else if (decShape instanceof CharSequence) {
                            try {
                                Object parsed = new groovy.json.JsonSlurper().parseText(decShape.toString())
                                if (parsed instanceof List) decList = (List<Integer>) parsed
                            } catch (Exception ignored) {}
                        }
                        if (decList != null && decList != infShape) {
                            throw new IllegalArgumentException("Declared shape (${decList}) does not match literal shape (${infShape})")
                        }
                    } else if (definition.fields.containsKey('shape')) {
                        normalized.put('shape', groovy.json.JsonOutput.toJson(infShape))
                    }
                    if (!normalized.containsKey('rank') && definition.fields.containsKey('rank')) {
                        normalized.put('rank', infShape.size())
                    }
                }
            } else if (normalized.containsKey('shape') && !normalized.containsKey('rank') && definition.fields.containsKey('rank')) {
                Object decShape = normalized.get('shape')
                List<Object> decList = null
                if (decShape instanceof List) decList = (List<Object>) decShape
                else if (decShape instanceof CharSequence) {
                    try {
                        Object parsed = new groovy.json.JsonSlurper().parseText(decShape.toString())
                        if (parsed instanceof List) decList = (List<Object>) parsed
                    } catch (Exception ignored) {}
                }
                if (decList != null) normalized.put('rank', decList.size())
                else normalized.put('rank', 1)
            } else if (!normalized.containsKey('rank') && definition.fields.containsKey('rank')) {
                normalized.put('rank', 1)
            }
        }
    }

    private static void addSinglePrimaryKey(final EntityDefinition definition, final String modelKey,
                                             final Map<String, Object> values) {
        List<FieldDefinition> primaryKeys = definition.primaryKeyFields
        if (primaryKeys.size() == 1) {
            FieldDefinition pkField = primaryKeys.first()
            int maxLen = pkField.type == 'id' ? 40 : (pkField.type == 'id-long' ? 255 : 255)
            if (modelKey != null && modelKey.length() > maxLen) {
                throw new IllegalArgumentException("L'identificatore '${modelKey}' per il campo '${pkField.name}' (tipo ${pkField.type}) supera la lunghezza massima di ${maxLen} caratteri")
            }
            String fieldName = pkField.name
            Object explicit = values.get(fieldName)
            if (explicit != null && explicit.toString() != modelKey) {
                throw new IllegalArgumentException(
                    "DSL key '${modelKey}' conflicts with ${definition.fullName}.${fieldName} '${explicit}'"
                )
            }
            values.put(fieldName, modelKey)
        } else if (modelKey != null && modelKey.length() > 255) {
            throw new IllegalArgumentException("L'identificatore '${modelKey}' per l'entità '${definition.fullName}' supera la lunghezza massima di 255 caratteri")
        }
    }

    private static List<Object> normalizeArguments(final Object rawArguments) {
        if (rawArguments == null) return []
        if (rawArguments instanceof Object[]) return new ArrayList<Object>(Arrays.asList((Object[]) rawArguments))
        if (rawArguments instanceof Collection) return new ArrayList<Object>((Collection<?>) rawArguments)
        [rawArguments]
    }

    private static String keyFromValues(final EntityDefinition definition, final Map<String, Object> values) {
        Object explicitKey = values.remove('_key')
        if (explicitKey != null) return explicitKey.toString()

        List<FieldDefinition> primaryKeys = definition.primaryKeyFields
        if (primaryKeys.empty) {
            throw new IllegalArgumentException("${definition.fullName} requires an explicit DSL _key")
        }
        if (!primaryKeys.every { FieldDefinition field -> values.containsKey(field.name) }) {
            throw new IllegalArgumentException(
                "${definition.fullName} requires a name, _key, or all primary-key fields"
            )
        }
        if (primaryKeys.size() == 1) return values.get(primaryKeys.first().name).toString()
        primaryKeys.collect { FieldDefinition field -> "${field.name}=${values.get(field.name)}" }.join('|')
    }

    @CompileStatic
    private static final class ParsedDeclaration {
        final String modelKey
        final LinkedHashMap<String, Object> values
        final Closure<?> action

        ParsedDeclaration(final String modelKey, final LinkedHashMap<String, Object> values,
                          final Closure<?> action) {
            this.modelKey = modelKey
            this.values = values
            this.action = action
        }
    }

    @CompileStatic
    private static final class RelationshipLink {
        final String name
        final LinkedHashMap<String, String> parentToChildFields

        RelationshipLink(final String name, final LinkedHashMap<String, String> fields) {
            this.name = name
            this.parentToChildFields = fields
        }
    }
}
