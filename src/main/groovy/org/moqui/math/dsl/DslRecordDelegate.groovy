/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.transform.TypeCheckingMode
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.FieldDefinition
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.ModelProvider
import org.moqui.math.entity.ModelValue
import org.moqui.math.entity.RelationshipDefinition

@CompileStatic
@PackageScope
final class DslRecordDelegate {
    final MathDslBuilder root
    final DslDeclaration record

    private final Map<String, Object> localVariables = new LinkedHashMap<>()
    private final List<DslVariable> declaredVariables = new ArrayList<>()
    private final List<DslConstraint> declaredConstraints = new ArrayList<>()
    private DslExpression objectiveExpression = null
    private String objectiveSense = null

    DslRecordDelegate(final MathDslBuilder root, final DslDeclaration record) {
        this.root = root
        this.record = record
    }

    DslRecordDelegate configure(final Closure<?> action) {
        Closure<?> configured = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_ONLY
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(this)

        finalizeAlgebraicOptimization()
        this
    }

    private void finalizeAlgebraicOptimization() {
        if (declaredVariables.isEmpty() && declaredConstraints.isEmpty() && objectiveExpression == null) {
            return
        }

        int nVars = declaredVariables.size()
        List<String> varNames = declaredVariables.collect { it.name }
        Map<String, Integer> varIndex = new LinkedHashMap<>()
        for (int i = 0; i < nVars; i++) varIndex.put(varNames.get(i), i)

        // 1. Decision Variables Vector
        String varVectorId = "${record.modelKey}_Variables"
        root.mathMeta.declare('moqui.math.Vector', varVectorId, [
            vectorId: varVectorId,
            name: 'Decision Variables',
            dimension: nVars,
            componentArray: groovy.json.JsonOutput.toJson(varNames)
        ])
        long dataSeq = 0
        root.mathMeta.declare('moqui.math.MathModelData', "${record.modelKey}_Data_${varVectorId}", [
            mathModelDataId: "${record.modelKey}_Data_${varVectorId}",
            mathModelId: record.modelKey,
            dataTypeEnumId: 'MmdtVector',
            purposeEnumId: 'MmdpDecisionVars',
            vectorId: varVectorId,
            sequenceNum: dataSeq++
        ])

        // 2. Objective / Cost Vector & Hessian (for QP)
        if (objectiveExpression != null) {
            double[] costs = new double[nVars]
            objectiveExpression.linearTerms.each { String vname, Double coeff ->
                Integer idx = varIndex.get(vname)
                if (idx != null) costs[idx] = coeff
            }
            String costVectorId = "${record.modelKey}_CostVector"
            root.mathMeta.declare('moqui.math.Vector', costVectorId, [
                vectorId: costVectorId,
                name: 'Cost Vector',
                dimension: nVars,
                componentArray: groovy.json.JsonOutput.toJson(costs)
            ])
            root.mathMeta.declare('moqui.math.MathModelData', "${record.modelKey}_Data_${costVectorId}", [
                mathModelDataId: "${record.modelKey}_Data_${costVectorId}",
                mathModelId: record.modelKey,
                dataTypeEnumId: 'MmdtVector',
                purposeEnumId: 'MmdpCostVector',
                vectorId: costVectorId,
                sequenceNum: dataSeq++
            ])

            if (!objectiveExpression.quadraticTerms.isEmpty()) {
                double[][] H = new double[nVars][nVars]
                objectiveExpression.quadraticTerms.each { String pair, Double coeff ->
                    String[] parts = pair.split(/\|/)
                    Integer i1 = varIndex.get(parts[0])
                    Integer i2 = varIndex.get(parts[1])
                    if (i1 != null && i2 != null) {
                        if (i1 == i2) {
                            // PETSc/TAO convention: 1/2 x^T H x -> coeff * x^2 requires H[i][i] = 2 * coeff
                            H[i1][i2] += 2.0d * coeff
                        } else {
                            H[i1][i2] += coeff
                            H[i2][i1] += coeff
                        }
                    }
                }
                String hessianId = "${record.modelKey}_Hessian"
                List<List<Double>> hList = new ArrayList<>()
                for (int i = 0; i < nVars; i++) {
                    List<Double> row = new ArrayList<>()
                    for (int j = 0; j < nVars; j++) row.add(H[i][j])
                    hList.add(row)
                }
                root.mathMeta.declare('moqui.math.Matrix', hessianId, [
                    matrixId: hessianId,
                    name: 'Hessian Matrix',
                    matrixTypeEnumId: 'MtSymmetric',
                    rows: nVars,
                    cols: nVars,
                    componentArray: groovy.json.JsonOutput.toJson(hList)
                ])
                root.mathMeta.declare('moqui.math.MathModelData', "${record.modelKey}_Data_${hessianId}", [
                    mathModelDataId: "${record.modelKey}_Data_${hessianId}",
                    mathModelId: record.modelKey,
                    dataTypeEnumId: 'MmdtMatrix',
                    purposeEnumId: 'MmdpHessian',
                    matrixId: hessianId,
                    sequenceNum: dataSeq++
                ])
            }
        }

        // 3. Constraints (Matrix, RHS, Senses)
        if (!declaredConstraints.isEmpty()) {
            int nCons = declaredConstraints.size()
            List<List<Double>> aRows = new ArrayList<>()
            List<Double> rhsList = new ArrayList<>()
            List<String> sensesList = new ArrayList<>()

            for (DslConstraint c : declaredConstraints) {
                List<Double> row = new ArrayList<>()
                for (int j = 0; j < nVars; j++) row.add(0.0d)
                c.expression.linearTerms.each { String vname, Double coeff ->
                    Integer idx = varIndex.get(vname)
                    if (idx != null) row.set(idx, coeff)
                }
                aRows.add(row)
                rhsList.add(c.rhs)
                sensesList.add(c.operator)
            }

            String aMatrixId = "${record.modelKey}_ConstraintMatrix"
            root.mathMeta.declare('moqui.math.Matrix', aMatrixId, [
                matrixId: aMatrixId,
                name: 'Constraint Matrix',
                matrixTypeEnumId: 'MtRectangular',
                rows: nCons,
                cols: nVars,
                componentArray: groovy.json.JsonOutput.toJson(aRows)
            ])
            root.mathMeta.declare('moqui.math.MathModelData', "${record.modelKey}_Data_${aMatrixId}", [
                mathModelDataId: "${record.modelKey}_Data_${aMatrixId}",
                mathModelId: record.modelKey,
                dataTypeEnumId: 'MmdtMatrix',
                purposeEnumId: 'MmdpConstraintMatrix',
                matrixId: aMatrixId,
                sequenceNum: dataSeq++
            ])

            String rhsId = "${record.modelKey}_RightHandSide"
            root.mathMeta.declare('moqui.math.Vector', rhsId, [
                vectorId: rhsId,
                name: 'Right Hand Side',
                dimension: nCons,
                componentArray: groovy.json.JsonOutput.toJson(rhsList)
            ])
            root.mathMeta.declare('moqui.math.MathModelData', "${record.modelKey}_Data_${rhsId}", [
                mathModelDataId: "${record.modelKey}_Data_${rhsId}",
                mathModelId: record.modelKey,
                dataTypeEnumId: 'MmdtVector',
                purposeEnumId: 'MmdpRhsVector',
                vectorId: rhsId,
                sequenceNum: dataSeq++
            ])

            String senseId = "${record.modelKey}_ConstraintSense"
            root.mathMeta.declare('moqui.math.Vector', senseId, [
                vectorId: senseId,
                name: 'Constraint Sense',
                dimension: nCons,
                componentArray: groovy.json.JsonOutput.toJson(sensesList)
            ])
            root.mathMeta.declare('moqui.math.MathModelData', "${record.modelKey}_Data_${senseId}", [
                mathModelDataId: "${record.modelKey}_Data_${senseId}",
                mathModelId: record.modelKey,
                dataTypeEnumId: 'MmdtVector',
                purposeEnumId: 'MmdpConstraint',
                vectorId: senseId,
                sequenceNum: dataSeq++
            ])
        }

        // 4. Variable Bounds Matrix
        List<List<Double>> boundsRows = new ArrayList<>()
        for (DslVariable v : declaredVariables) {
            boundsRows.add([v.lowerBound, v.upperBound])
        }
        String boundsId = "${record.modelKey}_VariableBounds"
        root.mathMeta.declare('moqui.math.Matrix', boundsId, [
            matrixId: boundsId,
            name: 'Variable Bounds',
            matrixTypeEnumId: 'MtRectangular',
            rows: nVars,
            cols: 2,
            componentArray: groovy.json.JsonOutput.toJson(boundsRows)
        ])
        root.mathMeta.declare('moqui.math.MathModelData', "${record.modelKey}_Data_${boundsId}", [
            mathModelDataId: "${record.modelKey}_Data_${boundsId}",
            mathModelId: record.modelKey,
            dataTypeEnumId: 'MmdtMatrix',
            purposeEnumId: 'MmdpVarBounds',
            matrixId: boundsId,
            sequenceNum: dataSeq++
        ])
    }

    // Algebraic Optimization DSL helpers
    DslVariable variable(final Number lowerBound, final Number upperBound, final Map<String, Object> options = Collections.emptyMap()) {
        Double initial = options.containsKey('initial') ? ((Number) options.get('initial')).doubleValue() : null
        String name = options.containsKey('name') ? options.get('name').toString() : "var_${declaredVariables.size() + 1}"
        DslVariable v = new DslVariable(name, lowerBound.doubleValue(), upperBound.doubleValue(), initial)
        declaredVariables.add(v)
        localVariables.put(name, v)
        v
    }

    void maximize(final DslExpression expr) {
        this.objectiveSense = 'MAXIMIZE'
        this.objectiveExpression = expr
        registerObjectiveSenseParameter('OosMaximize')
    }

    void minimize(final DslExpression expr) {
        this.objectiveSense = 'MINIMIZE'
        this.objectiveExpression = expr
        registerObjectiveSenseParameter('OosMinimize')
    }

    private void registerObjectiveSenseParameter(final String senseEnumId) {
        String paramId = "${record.modelKey}.ObjectiveSense"
        root.mathMeta.declare('moqui.math.Parameter', paramId, [
            parameterId: paramId,
            parameterAlias: 'objectiveSense',
            parameterDefId: 'OptimizationObjectiveSense',
            symbolicValue: senseEnumId
        ])
    }

    DslConstraint subjectTo(final String name, final Object expr) {
        DslExpression expression
        if (expr instanceof DslExpression) expression = (DslExpression) expr
        else if (expr instanceof DslVariable) expression = ((DslVariable) expr).multiply(1.0d)
        else throw new IllegalArgumentException("Unsupported expression type in subjectTo: ${expr?.class?.name}")

        DslConstraint constraint = new DslConstraint(expression, 'LE', 0.0d).withName(name)
        declaredConstraints.add(constraint)
        constraint
    }

    // Parameters block
    void parameters(final Closure<?> cl) {
        Closure<?> configured = (Closure<?>) cl.rehydrate(new ParameterBlockDelegate(this), cl.owner, cl.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_ONLY
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(this)
    }

    // Data constructors
    ModelProvider matrix(final Object... args) {
        Map<String, Object> options = new LinkedHashMap<>()
        String key = null
        List<Number> dims = new ArrayList<>()
        for (Object arg : args) {
            if (arg instanceof Map) {
                options.putAll((Map<String, Object>) arg)
            } else if (arg instanceof CharSequence) {
                if (key == null) key = arg.toString()
            } else if (arg instanceof List) {
                options.put('componentArray', arg)
            } else if (arg instanceof Number) {
                dims.add((Number) arg)
            }
        }
        if (dims.size() >= 2) {
            options.put('rows', dims.get(0).intValue())
            options.put('cols', dims.get(1).intValue())
        }
        if (options.get('componentArray') instanceof List) {
            List<?> list = (List<?>) options.get('componentArray')
            if (!list.isEmpty() && list.get(0) instanceof List) {
                if (!options.containsKey('rows')) options.put('rows', list.size())
                if (!options.containsKey('cols')) options.put('cols', ((List<?>) list.get(0)).size())
            }
        }
        if (key == null) {
            key = options.remove('_key')?.toString() ?: options.get('name')?.toString() ?: "Matrix_${System.identityHashCode(options)}"
        }
        declareDataEntity('Matrix', key, options)
    }

    ModelProvider vector(final Object... args) {
        Map<String, Object> options = new LinkedHashMap<>()
        String key = null
        List<Number> dims = new ArrayList<>()
        for (Object arg : args) {
            if (arg instanceof Map) {
                options.putAll((Map<String, Object>) arg)
            } else if (arg instanceof CharSequence) {
                if (key == null) key = arg.toString()
            } else if (arg instanceof List) {
                options.put('componentArray', arg)
            } else if (arg instanceof Number) {
                dims.add((Number) arg)
            }
        }
        if (!dims.isEmpty()) {
            options.put('dimension', dims.get(0).intValue())
        }
        if (options.get('componentArray') instanceof List) {
            List<?> list = (List<?>) options.get('componentArray')
            if (!options.containsKey('dimension')) options.put('dimension', list.size())
        }
        if (key == null) {
            key = options.remove('_key')?.toString() ?: options.get('name')?.toString() ?: "Vector_${System.identityHashCode(options)}"
        }
        declareDataEntity('Vector', key, options)
    }

    ModelProvider tensor(final Object... args) {
        Map<String, Object> options = new LinkedHashMap<>()
        String key = null
        for (Object arg : args) {
            if (arg instanceof Map) {
                options.putAll((Map<String, Object>) arg)
            } else if (arg instanceof CharSequence) {
                if (key == null) key = arg.toString()
            } else if (arg instanceof List) {
                options.put('componentArray', arg)
            }
        }
        if (key == null) {
            key = options.remove('_key')?.toString() ?: options.get('name')?.toString() ?: "Tensor_${System.identityHashCode(options)}"
        }
        declareDataEntity('Tensor', key, options)
    }

    // Literal constructors
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

    // Function transformations
    DecompositionResult svd(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String baseKey = options.get('name')?.toString() ?: "Svd_${System.identityHashCode(operand)}"
        String uKey = "${baseKey}_U"
        String sigmaKey = "${baseKey}_Sigma"
        String vtKey = "${baseKey}_Vt"

        ModelProvider u = declareDataEntity('Matrix', uKey, [name: uKey, matrixType: 'Dense'])
        ModelProvider sigma = declareDataEntity('Vector', sigmaKey, [name: sigmaKey])
        ModelProvider vt = declareDataEntity('Matrix', vtKey, [name: vtKey, matrixType: 'Dense'])

        String tKey = "Decomp_${baseKey}"
        root.mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtSvd',
            name: "SVD Decomposition of ${operand}"
        ])
        root.mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: operandId(operand)
        ])
        root.mathMeta.declare('moqui.math.MatrixDecomposition', baseKey, [
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

    ModelProvider gaussianBlur(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "GaussianBlur_${System.identityHashCode(operand)}"
        ModelProvider res = declareDataEntity('Matrix', key, [name: key, matrixType: 'Dense'])
        String tKey = "T_${key}"
        root.mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtGaussianBlur',
            name: key,
            resultMatrixId: key
        ])
        root.mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: operandId(operand)
        ])
        options.each { k, v ->
            if (k != 'name') {
                root.mathMeta.declare('moqui.math.Parameter', "${tKey}.${k}", [
                    parameterId: "${tKey}.${k}",
                    transformationId: tKey,
                    parameterCode: k,
                    parameterAlias: k,
                    numericValue: (v instanceof Number ? ((Number) v).doubleValue() : null),
                    textValue: (v instanceof CharSequence ? v.toString() : null)
                ])
            }
        }
        res
    }

    ModelProvider sobel(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "Sobel_${System.identityHashCode(operand)}"
        ModelProvider res = declareDataEntity('Matrix', key, [name: key, matrixType: 'Dense'])
        String tKey = "T_${key}"
        root.mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtSobel',
            name: key,
            resultMatrixId: key
        ])
        root.mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: operandId(operand)
        ])
        options.each { k, v ->
            if (k != 'name') {
                root.mathMeta.declare('moqui.math.Parameter', "${tKey}.${k}", [
                    parameterId: "${tKey}.${k}",
                    transformationId: tKey,
                    parameterCode: k,
                    parameterAlias: k,
                    numericValue: (v instanceof Number ? ((Number) v).doubleValue() : null),
                    textValue: (v instanceof CharSequence ? v.toString() : null)
                ])
            }
        }
        res
    }

    ModelProvider diagonalExtraction(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "DiagExt_${System.identityHashCode(operand)}"
        ModelProvider res = declareDataEntity('Vector', key, [name: key])
        String tKey = "T_${key}"
        root.mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtDiagonalExtraction',
            name: key,
            resultVectorId: key
        ])
        root.mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: operandId(operand)
        ])
        root.mathMeta.declare('moqui.math.DiagonalExtraction', key, [
            diagonalExtractionId: key,
            transformationId: tKey,
            axisOffset: options.get('axisOffset') ?: 0
        ])
        res
    }

    ModelProvider triangularExtraction(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "TriExt_${System.identityHashCode(operand)}"
        ModelProvider res = declareDataEntity('Matrix', key, [name: key, matrixType: 'Dense'])
        String tKey = "T_${key}"
        root.mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtTriangularExtraction',
            name: key,
            resultMatrixId: key
        ])
        root.mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: operandId(operand)
        ])
        Object typeVal = options.get('type')
        String tetId = typeVal == 'Lower' || typeVal == TriangularExtractionType.Lower ? 'TetLower' : 'TetUpper'
        root.mathMeta.declare('moqui.math.TriangularExtraction', key, [
            triangularExtractionId: key,
            transformationId: tKey,
            triangularTypeEnumId: tetId
        ])
        res
    }

    ModelProvider normResult(final Object operand, final Map<String, Object> options = Collections.emptyMap()) {
        String key = options.get('name')?.toString() ?: "Norm_${System.identityHashCode(operand)}"
        ModelProvider res = declareDataEntity('Vector', key, [name: key, dimension: 1])
        String tKey = "T_${key}"
        root.mathMeta.declare('moqui.math.Transformation', tKey, [
            transformationId: tKey,
            transformationTypeEnumId: 'TtNorm',
            name: key,
            resultVectorId: key
        ])
        root.mathMeta.declare('moqui.math.TransformationOperand', "${tKey}_Op_0", [
            transformationId: tKey,
            operandIndex: 0L,
            operandTypeEnumId: 'TotMatrix',
            operandMatrixId: operandId(operand)
        ])
        String domainEnumId = root.vocabulary.resolveSymbolForField(options.get('domain')?.toString(), 'NormDomain')?.id ?: 'NdMatrix'
        String orderEnumId = root.vocabulary.resolveSymbolForField(options.get('order')?.toString(), 'NormOrder')?.id ?: 'NoMatFrobenius'
        root.mathMeta.declare('moqui.math.NormResult', key, [
            normResultId: key,
            transformationId: tKey,
            domainEnumId: domainEnumId,
            orderEnumId: orderEnumId,
            normValue: options.get('normValue') ?: 0.0d
        ])
        res
    }

    // Graph & Category DSL methods
    ModelProvider vertex(final Object... args) {
        (ModelProvider) methodMissing('vertex', args)
    }

    ModelProvider edge(final Object... args) {
        (ModelProvider) methodMissing('edge', args)
    }

    ModelProvider object(final Object... args) {
        (ModelProvider) methodMissing('object', args)
    }

    ModelProvider morphism(final Object... args) {
        (ModelProvider) methodMissing('morphism', args)
    }

    private static String operandId(final Object op) {
        if (op instanceof ModelProvider) return ((ModelProvider) op).name
        if (op instanceof org.moqui.math.metamodel.EntityRef) return ((org.moqui.math.metamodel.EntityRef<?>) op).id
        op?.toString()
    }

    private ModelProvider declareDataEntity(final String entityName, final String key, final Map<String, Object> values) {
        // Purpose routing
        Object purpose = values.remove('purpose')
        String dataPurposeEnumId = null
        String entityPurposeEnumId = null
        if (purpose != null) {
            String pName = purpose.toString()
            DslSymbol dataSym = root.vocabulary.symbolsByEnumType.get('MathModelDataPurpose')?.get(pName)
            DslSymbol matrixSym = root.vocabulary.symbolsByEnumType.get('MatrixPurpose')?.get(pName)
            if (dataSym != null && matrixSym != null) {
                throw new IllegalArgumentException("Ambiguous purpose '${purpose}': present in both MathModelDataPurpose and MatrixPurpose; qualify explicitly")
            }
            if (dataSym != null) dataPurposeEnumId = dataSym.id
            else if (matrixSym != null) entityPurposeEnumId = matrixSym.id
            else {
                // Fallback lookup
                DslSymbol sym = root.vocabulary.resolveSymbol(pName)
                if (sym?.enumTypeId == 'MathModelDataPurpose') dataPurposeEnumId = sym.id
                else if (sym?.enumTypeId == 'MatrixPurpose') entityPurposeEnumId = sym.id
            }
        }
        if (entityPurposeEnumId != null) values.put('purposeEnumId', entityPurposeEnumId)

        DslDeclaration childDecl = root.declareNested(entityName, [key, values], null, null)

        if (record.definition.fullName == 'moqui.math.MathModel') {
            long seq = root.mathMeta.hasEntity('MathModelData') ?
                (long) root.mathMeta.entity('MathModelData').count { Object v ->
                    (v instanceof ModelValue ? ((ModelValue) v).get('mathModelId') : null) == record.modelKey
                } : 0L
            String dataId = "${record.modelKey}_Data_${key}"
            Map<String, Object> dataValues = [
                mathModelDataId: dataId,
                mathModelId: record.modelKey,
                dataTypeEnumId: "Mmdt${entityName}",
                sequenceNum: seq
            ]
            if (dataPurposeEnumId != null) dataValues.put('purposeEnumId', dataPurposeEnumId)
            if (entityName == 'Matrix') dataValues.put('matrixId', key)
            else if (entityName == 'Vector') dataValues.put('vectorId', key)
            else if (entityName == 'Tensor') dataValues.put('tensorId', key)
            root.mathMeta.declare('moqui.math.MathModelData', dataId, dataValues)
        }

        localVariables.put(key, childDecl.provider)
        childDecl.provider
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String name, final Object rawArguments) {
        Object[] arguments = rawArguments instanceof Object[] ?
            (Object[]) rawArguments : [rawArguments] as Object[]
        String fieldName = MathDslBuilder.resolveFieldName(record.definition, name)
        if (record.definition.fields.containsKey(fieldName)) {
            if (arguments.length == 1 && !(arguments[0] instanceof Closure)) {
                Object fieldValue = root.normalizeValue(arguments[0], record.definition, fieldName)
                record.values.put(fieldName, fieldValue)
                record.provider.configure { value -> value.put(fieldName, fieldValue) }
                return this
            }
        }

        if (root.vocabulary.isTransformationEntity(record.definition.fullName)) {
            if (record.definition.fullName != 'moqui.math.Transformation') {
                if (!root.mathMeta.hasEntity('Transformation') || root.mathMeta.entity('Transformation').findByName(record.modelKey) == null) {
                    Map<String, Object> tVals = [transformationId: record.modelKey, transformationTypeEnumId: 'TtMeta']
                    root.mathMeta.declare('moqui.math.Transformation', record.modelKey, tVals)
                }
            }

            if (['resultMatrix', 'resultVector', 'resultTensor', 'resultParameter', 'resultFunction'].contains(name)) {
                Object targetId = arguments.length > 0 ? (arguments[0] instanceof org.moqui.math.metamodel.EntityRef ? ((org.moqui.math.metamodel.EntityRef<?>) arguments[0]).id : (arguments[0] instanceof ModelProvider ? ((ModelProvider) arguments[0]).name : arguments[0]?.toString())) : null
                String fieldIdName = "${name}Id"
                if (root.mathMeta.hasEntity('Transformation') && root.mathMeta.entity('Transformation').findByName(record.modelKey) != null) {
                    root.mathMeta.entity('Transformation').findByName(record.modelKey).configure { ModelValue val -> val.put(fieldIdName, targetId) }
                }
                return this
            }

            String operandTypeEnumId = root.vocabulary.getOperandTypeEnumId(name)
            if (operandTypeEnumId != null && arguments.length == 1 && !(arguments[0] instanceof Map) && !(arguments[0] instanceof Closure)) {
                Object targetId = arguments.length > 0 ? (arguments[0] instanceof org.moqui.math.metamodel.EntityRef ? ((org.moqui.math.metamodel.EntityRef<?>) arguments[0]).id : (arguments[0] instanceof ModelProvider ? ((ModelProvider) arguments[0]).name : arguments[0]?.toString())) : null
                Map<String, Object> opValues = new LinkedHashMap<>()
                opValues.put('transformationId', record.modelKey)
                opValues.put('operandTypeEnumId', operandTypeEnumId)
                if (name.endsWith('Matrix')) opValues.put('operandMatrixId', targetId)
                else if (name.endsWith('Vector')) opValues.put('operandVectorId', targetId)
                else if (name.endsWith('Tensor')) opValues.put('operandTensorId', targetId)
                else if (name.endsWith('Transformation')) opValues.put('operandTransformationId', targetId)
                else if (name.endsWith('Parameter')) opValues.put('operandParameterId', targetId)

                long opIndex = root.mathMeta.hasEntity('TransformationOperand') ?
                    (long) root.mathMeta.entity('TransformationOperand').count { Object v ->
                        (v instanceof ModelValue ? ((ModelValue) v).get('transformationId') : null) == record.modelKey
                    } : 0L
                opValues.put('operandIndex', opIndex)
                String opKey = "${record.modelKey}_Op_${opIndex}"
                root.mathMeta.declare('moqui.math.TransformationOperand', opKey, opValues)
                return this
            }
        }

        String capName = name ? name.capitalize() : name
        if (record.definition.fullName == 'moqui.math.MathModel' && ['Matrix', 'Vector', 'Tensor'].contains(capName)) {
            DslDeclaration childDecl = root.declareNested(capName, rawArguments, null, null)
            String targetKey = childDecl.modelKey
            long seq = root.mathMeta.hasEntity('MathModelData') ?
                (long) root.mathMeta.entity('MathModelData').count { Object v ->
                    (v instanceof ModelValue ? ((ModelValue) v).get('mathModelId') : null) == record.modelKey
                } : 0L
            String dataId = "${record.modelKey}_Data_${targetKey}"
            Map<String, Object> dataValues = [
                mathModelDataId: dataId,
                mathModelId: record.modelKey,
                dataTypeEnumId: "Mmdt${capName}",
                sequenceNum: seq
            ]
            if (capName == 'Matrix') dataValues.put('matrixId', targetKey)
            else if (capName == 'Vector') dataValues.put('vectorId', targetKey)
            else if (capName == 'Tensor') dataValues.put('tensorId', targetKey)
            root.mathMeta.declare('moqui.math.MathModelData', dataId, dataValues)
            return childDecl.provider
        }

        String resolvedFieldName = MathDslBuilder.resolveFieldName(record.definition, name)
        if (record.definition.fields.containsKey(resolvedFieldName)) {
            Object val = arguments.length > 0 ? arguments[0] : null
            Object normVal = root.normalizeValue(val, record.definition, resolvedFieldName)
            record.values.put(resolvedFieldName, normVal)
            record.provider.configure { ModelValue mv -> mv.put(resolvedFieldName, normVal) }
            return this
        }

        RelationshipDefinition relationship = record.definition.relationships.get(name)
        if (relationship == null) {
            if (name == 'vertex') relationship = record.definition.relationships.get('vertices')
            else if (name == 'edge') relationship = record.definition.relationships.get('edges')
            else if (name == 'object') relationship = record.definition.relationships.get('objects')
            else if (name == 'morphism') relationship = record.definition.relationships.get('morphisms')
        }
        if (relationship != null) {
            if (arguments.length == 1 && arguments[0] instanceof Closure) {
                return new DslRelationshipDelegate(root, record, relationship)
                    .configure((Closure<?>) arguments[0])
            }
            return root.declareNested(relationship.relatedEntityName, arguments, record, relationship).provider
        }
        root.declareNested(name, arguments, record).provider
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object propertyMissing(final String name) {
        if (localVariables.containsKey(name)) return localVariables.get(name)
        new DslDeferredSymbol(name, MathDslBuilder.getCallerFile(), MathDslBuilder.getCallerLine())
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void propertyMissing(final String name, final Object value) {
        localVariables.put(name, value)
    }
}
