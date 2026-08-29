/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.petsctao

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.math.dsl.MathMeta
import groovy.math.entity.ModelValue
import groovy.math.spi.MathProvider

@CompileStatic
final class PetscTaoProvider implements MathProvider<PetscTaoPlan, PetscTaoResult> {
    private static final String QUADRATIC_PROGRAM = 'MmtQp'
    private static final String INTERIOR_POINT = 'MmsmInteriorPoint'
    private static final String MINIMIZE = 'MINIMIZE'

    final String mathModelId
    private final PetscTaoBackend backend

    PetscTaoProvider(final String mathModelId) {
        this(mathModelId, PetscTaoPanama.INSTANCE)
    }

    PetscTaoProvider(final String mathModelId, final PetscTaoBackend backend) {
        if (!mathModelId) throw new IllegalArgumentException('mathModelId must not be empty')
        this.mathModelId = mathModelId
        this.backend = Objects.requireNonNull(backend, 'PETSc/TAO backend must not be null')
    }

    @Override
    String getProviderId() { 'petsc-tao' }

    @Override
    PetscTaoPlan compile(final MathMeta mathMeta) {
        Objects.requireNonNull(mathMeta, 'Math metadata must not be null').freeze()
        ModelValue model = mathMeta.entity('MathModel').findByName(mathModelId)
        if (model == null) throw new IllegalArgumentException("Unknown MathModel '${mathModelId}'")
        validateModel(mathMeta, model)

        List<ModelValue> modelData = mathMeta.entity('MathModelData').findAll {
            ModelValue value -> value.get('mathModelId') == mathModelId
        } as List<ModelValue>
        ModelValue decisions = requiredVector(mathMeta, modelData, 'MmdpDecisionVars')
        ModelValue hessian = requiredMatrix(mathMeta, modelData, 'MmdpHessian')
        ModelValue linear = requiredVector(mathMeta, modelData, 'MmdpCostVector')
        ModelValue bounds = requiredMatrix(mathMeta, modelData, 'MmdpVarBounds')
        ModelValue initial = requiredVector(mathMeta, modelData, 'MmdpInitialCondition')

        List<String> variableNames = stringVector(decisions, 'decision variables')
        double[][] hessianValues = numericMatrix(hessian, 'Hessian')
        double[] linearValues = numericVector(linear, 'linear cost vector')
        double[][] boundValues = numericMatrix(bounds, 'variable bounds')
        double[] initialValues = numericVector(initial, 'initial condition')
        validateDimensions(variableNames, hessianValues, linearValues, boundValues, initialValues)
        validateHessian(hessianValues)

        double[] flattenedHessian = new double[variableNames.size() * variableNames.size()]
        for (int row = 0; row < variableNames.size(); row++) {
            System.arraycopy(hessianValues[row], 0, flattenedHessian,
                row * variableNames.size(), variableNames.size())
        }
        long handle = backend.createBoundedQuadraticPlan(variableNames.size(), flattenedHessian,
            linearValues, boundValues[0], boundValues[1], initialValues)
        new PetscTaoPlan(mathModelId, 'bqpip', variableNames, backend, handle)
    }

    @Override
    PetscTaoResult execute(final PetscTaoPlan plan, final Map<String, ?> inputs) {
        Objects.requireNonNull(plan, 'PETSc/TAO plan must not be null')
        if (inputs != null && !inputs.isEmpty()) {
            throw new IllegalArgumentException(
                'The initial PETSc/TAO QP provider does not accept runtime input overrides')
        }
        plan.solve()
    }

    private void validateModel(final MathMeta mathMeta, final ModelValue model) {
        ModelValue definition = mathMeta.entity('MathModelDef').findByName(model.get('mathModelDefId') as String)
        if (definition == null) {
            throw new IllegalStateException("Missing MathModelDef '${model.get('mathModelDefId')}'")
        }
        if (definition.get('modelTypeEnumId') != QUADRATIC_PROGRAM) {
            throw new UnsupportedOperationException(
                "PETSc/TAO provider currently supports only ${QUADRATIC_PROGRAM}; found ${definition.get('modelTypeEnumId')}")
        }
        if (model.get('solvingMethodEnumId') != INTERIOR_POINT) {
            throw new UnsupportedOperationException(
                "PETSc/TAO BQPIP requires solving method ${INTERIOR_POINT}; found ${model.get('solvingMethodEnumId')}")
        }
        List<ModelValue> senses = mathMeta.entity('Parameter').findAll { ModelValue value ->
            value.get('mathModelId') == mathModelId && value.get('parameterAlias') == 'objectiveSense'
        } as List<ModelValue>
        if (senses.size() != 1 || senses.first().get('symbolicValue') != MINIMIZE) {
            throw new IllegalStateException(
                "MathModel '${mathModelId}' must declare objectiveSense=${MINIMIZE}")
        }
    }

    private static ModelValue requiredVector(final MathMeta mathMeta, final List<ModelValue> modelData,
                                             final String purpose) {
        ModelValue data = exactlyOne(modelData, purpose)
        String vectorId = data.get('vectorId') as String
        if (!vectorId) throw new IllegalStateException("${purpose} must reference a Vector")
        ModelValue vector = mathMeta.entity('Vector').findByName(vectorId)
        if (vector == null) throw new IllegalStateException("Missing Vector '${vectorId}'")
        vector
    }

    private static ModelValue requiredMatrix(final MathMeta mathMeta, final List<ModelValue> modelData,
                                             final String purpose) {
        ModelValue data = exactlyOne(modelData, purpose)
        String matrixId = data.get('matrixId') as String
        if (!matrixId) throw new IllegalStateException("${purpose} must reference a Matrix")
        ModelValue matrix = mathMeta.entity('Matrix').findByName(matrixId)
        if (matrix == null) throw new IllegalStateException("Missing Matrix '${matrixId}'")
        matrix
    }

    private static ModelValue exactlyOne(final List<ModelValue> modelData, final String purpose) {
        List<ModelValue> matches = modelData.findAll {
            ModelValue value -> value.get('purposeEnumId') == purpose
        } as List<ModelValue>
        if (matches.size() != 1) {
            throw new IllegalStateException("Expected exactly one ${purpose}; found ${matches.size()}")
        }
        matches.first()
    }

    private static List<String> stringVector(final ModelValue vector, final String label) {
        Object parsed = parseArray(vector.get('componentArray'), label)
        if (!(parsed instanceof List) || ((List<?>) parsed).any {
            Object value -> !(value instanceof CharSequence)
        }) throw new IllegalStateException("${label} must be a JSON array of strings")
        List<String> values = ((List<?>) parsed).collect { Object value -> value.toString() }
        if (values.isEmpty() || values.any { String value -> !value } || values.toSet().size() != values.size()) {
            throw new IllegalStateException('Decision variable names must be non-empty and unique')
        }
        values
    }

    private static double[] numericVector(final ModelValue vector, final String label) {
        Object parsed = parseArray(vector.get('componentArray'), label)
        if (!(parsed instanceof List) || ((List<?>) parsed).any {
            Object value -> !(value instanceof Number)
        }) throw new IllegalStateException("${label} must be a JSON array of numbers")
        List<?> values = (List<?>) parsed
        double[] result = new double[values.size()]
        for (int index = 0; index < values.size(); index++) {
            result[index] = ((Number) values[index]).doubleValue()
        }
        result
    }

    private static double[][] numericMatrix(final ModelValue matrix, final String label) {
        Object parsed = parseArray(matrix.get('componentArray'), label)
        if (!(parsed instanceof List) || ((List<?>) parsed).isEmpty()) {
            throw new IllegalStateException("${label} must be a non-empty JSON array of rows")
        }
        List<?> rows = (List<?>) parsed
        int expectedRows = ((Number) matrix.get('rows')).intValue()
        int expectedColumns = ((Number) matrix.get('cols')).intValue()
        if (rows.size() != expectedRows) {
            throw new IllegalStateException("${label} declares ${expectedRows} rows but contains ${rows.size()}")
        }
        double[][] result = new double[expectedRows][expectedColumns]
        for (int row = 0; row < expectedRows; row++) {
            if (!(rows[row] instanceof List) || ((List<?>) rows[row]).size() != expectedColumns ||
                ((List<?>) rows[row]).any { Object value -> !(value instanceof Number) }) {
                throw new IllegalStateException("${label} row ${row} must contain ${expectedColumns} numbers")
            }
            for (int column = 0; column < expectedColumns; column++) {
                result[row][column] = ((Number) ((List<?>) rows[row])[column]).doubleValue()
            }
        }
        result
    }

    private static Object parseArray(final Object encoded, final String label) {
        if (!(encoded instanceof CharSequence) || !encoded.toString().trim()) {
            throw new IllegalStateException("${label} has no componentArray")
        }
        try {
            new JsonSlurper().parseText(encoded.toString())
        } catch (RuntimeException failure) {
            throw new IllegalStateException("${label} componentArray is not valid JSON", failure)
        }
    }

    private static void validateDimensions(final List<String> variables, final double[][] hessian,
                                           final double[] linear, final double[][] bounds,
                                           final double[] initial) {
        int dimension = variables.size()
        boolean invalidHessian = hessian.length != dimension
        if (!invalidHessian) {
            for (double[] row : hessian) {
                if (row.length != dimension) {
                    invalidHessian = true
                    break
                }
            }
        }
        if (invalidHessian) {
            throw new IllegalStateException("Hessian must be ${dimension} x ${dimension}")
        }
        if (linear.length != dimension) {
            throw new IllegalStateException("Linear cost vector has ${linear.length} values for ${dimension} variables")
        }
        if (bounds.length != 2 || bounds[0].length != dimension || bounds[1].length != dimension) {
            throw new IllegalStateException("Variable bounds must be 2 x ${dimension}: [lower, upper]")
        }
        if (initial.length != dimension) {
            throw new IllegalStateException("Initial condition has ${initial.length} values for ${dimension} variables")
        }
        for (int index = 0; index < dimension; index++) {
            if (!Double.isFinite(linear[index]) || !Double.isFinite(bounds[0][index]) ||
                !Double.isFinite(bounds[1][index]) || !Double.isFinite(initial[index])) {
                throw new IllegalStateException("Variable '${variables[index]}' has a non-finite coefficient, bound or initial value")
            }
            if (bounds[0][index] > bounds[1][index]) {
                throw new IllegalStateException("Variable '${variables[index]}' has inverted bounds")
            }
            if (initial[index] < bounds[0][index] || initial[index] > bounds[1][index]) {
                throw new IllegalStateException("Initial value for '${variables[index]}' is outside its bounds")
            }
        }
    }

    private static void validateHessian(final double[][] hessian) {
        for (int row = 0; row < hessian.length; row++) {
            if (!Double.isFinite(hessian[row][row])) {
                throw new IllegalStateException('Hessian must contain only finite values')
            }
            for (int column = row + 1; column < hessian.length; column++) {
                if (!Double.isFinite(hessian[row][column]) ||
                    !Double.isFinite(hessian[column][row])) {
                    throw new IllegalStateException('Hessian must contain only finite values')
                }
                double scale = Math.max(1d, Math.max(Math.abs(hessian[row][column]),
                    Math.abs(hessian[column][row])))
                if (Math.abs(hessian[row][column] - hessian[column][row]) > 1e-12d * scale) {
                    throw new IllegalStateException('Hessian must be symmetric')
                }
            }
        }
    }
}
