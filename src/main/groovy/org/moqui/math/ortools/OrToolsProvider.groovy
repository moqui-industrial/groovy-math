/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.ortools

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPConstraint
import com.google.ortools.linearsolver.MPObjective
import com.google.ortools.linearsolver.MPSolver
import com.google.ortools.linearsolver.MPVariable
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue
import org.moqui.math.spi.MathProvider

@CompileStatic
final class OrToolsProvider implements MathProvider<OrToolsPlan, OrToolsResult> {
    private static final String LINEAR_PROGRAM = 'MmtLp'
    private static final String SIMPLEX = 'MmsmSimplex'
    private static final String OBJECTIVE_SENSE = 'objectiveSense'
    private static final String MAXIMIZE = 'MAXIMIZE'
    private static final String MINIMIZE = 'MINIMIZE'

    final String mathModelId

    OrToolsProvider(final String mathModelId) {
        if (!mathModelId) throw new IllegalArgumentException('mathModelId must not be empty')
        this.mathModelId = mathModelId
    }

    @Override
    String getProviderId() { 'ortools' }

    @Override
    OrToolsPlan compile(final MathMeta mathMeta) {
        Objects.requireNonNull(mathMeta, 'Math metadata must not be null').freeze()
        ModelValue model = mathMeta.entity('MathModel').findByName(mathModelId)
        if (model == null) throw new IllegalArgumentException("Unknown MathModel '${mathModelId}'")
        validateModelType(mathMeta, model)

        List<ModelValue> modelData = mathMeta.entity('MathModelData').findAll {
            ModelValue value -> value.get('mathModelId') == mathModelId
        } as List<ModelValue>
        ModelValue decisions = requiredVector(mathMeta, modelData, 'MmdpDecisionVars')
        ModelValue costs = requiredVector(mathMeta, modelData, 'MmdpCostVector')
        ModelValue coefficients = requiredMatrix(mathMeta, modelData, 'MmdpConstraintMatrix')
        ModelValue rightHandSide = requiredVector(mathMeta, modelData, 'MmdpRhsVector')
        ModelValue bounds = optionalMatrix(mathMeta, modelData, 'MmdpVarBounds')

        List<String> variableNames = stringVector(decisions, 'decision variables')
        double[] objectiveCoefficients = numericVector(costs, 'cost vector')
        double[][] constraintCoefficients = numericMatrix(coefficients, 'constraint matrix')
        double[] constraintUpperBounds = numericVector(rightHandSide, 'right-hand side')
        validateDimensions(variableNames, objectiveCoefficients, constraintCoefficients, constraintUpperBounds)
        double[][] variableBounds = bounds == null ? defaultBounds(variableNames.size()) :
            numericMatrix(bounds, 'variable bounds')
        if (variableBounds.length != 2 || variableBounds[0].length != variableNames.size() ||
            variableBounds[1].length != variableNames.size()) {
            throw new IllegalStateException('Variable bounds must be a 2 x variable-count matrix: [lower, upper]')
        }

        String objectiveSense = objectiveSense(mathMeta)
        Loader.loadNativeLibraries()
        MPSolver solver = MPSolver.createSolver('GLOP')
        if (solver == null) throw new IllegalStateException('OR-Tools GLOP solver is not available')
        try {
            List<MPVariable> variables = []
            for (int column = 0; column < variableNames.size(); column++) {
                double lower = variableBounds[0][column]
                double upper = variableBounds[1][column]
                if (lower > upper) {
                    throw new IllegalStateException("Variable '${variableNames[column]}' has lower bound ${lower} greater than upper bound ${upper}")
                }
                variables.add(solver.makeNumVar(lower, upper, variableNames[column]))
            }

            for (int row = 0; row < constraintCoefficients.length; row++) {
                MPConstraint constraint = solver.makeConstraint(
                    Double.NEGATIVE_INFINITY, constraintUpperBounds[row], "constraint_${row}")
                for (int column = 0; column < variables.size(); column++) {
                    constraint.setCoefficient(variables[column], constraintCoefficients[row][column])
                }
            }

            MPObjective objective = solver.objective()
            for (int column = 0; column < variables.size(); column++) {
                objective.setCoefficient(variables[column], objectiveCoefficients[column])
            }
            if (objectiveSense == MAXIMIZE) objective.setMaximization()
            else objective.setMinimization()

            new OrToolsPlan(mathModelId, 'GLOP', objectiveSense, variableNames,
                constraintCoefficients.length, solver, variables)
        } catch (Throwable failure) {
            solver.delete()
            throw failure
        }
    }

    @Override
    OrToolsResult execute(final OrToolsPlan plan, final Map<String, ?> inputs) {
        Objects.requireNonNull(plan, 'OR-Tools plan must not be null')
        if (inputs != null && !inputs.isEmpty()) {
            throw new IllegalArgumentException('The initial OR-Tools LP provider does not accept runtime input overrides')
        }
        plan.solve()
    }

    private void validateModelType(final MathMeta mathMeta, final ModelValue model) {
        ModelValue definition = mathMeta.entity('MathModelDef').findByName(model.get('mathModelDefId') as String)
        if (definition == null) {
            throw new IllegalStateException("Missing MathModelDef '${model.get('mathModelDefId')}'")
        }
        if (definition.get('modelTypeEnumId') != LINEAR_PROGRAM) {
            throw new UnsupportedOperationException(
                "OR-Tools provider currently supports only ${LINEAR_PROGRAM}; found ${definition.get('modelTypeEnumId')}")
        }
        Object solvingMethod = model.get('solvingMethodEnumId')
        if (solvingMethod != null && solvingMethod != SIMPLEX) {
            throw new UnsupportedOperationException(
                "OR-Tools LP provider supports solving method ${SIMPLEX}; found ${solvingMethod}")
        }
    }

    private String objectiveSense(final MathMeta mathMeta) {
        List<ModelValue> matches = mathMeta.entity('Parameter').findAll { ModelValue value ->
            value.get('mathModelId') == mathModelId && value.get('parameterAlias') == OBJECTIVE_SENSE
        } as List<ModelValue>
        if (matches.size() != 1) {
            throw new IllegalStateException(
                "MathModel '${mathModelId}' must declare exactly one '${OBJECTIVE_SENSE}' parameter")
        }
        String sense = matches.first().get('symbolicValue') as String
        if (sense != MAXIMIZE && sense != MINIMIZE) {
            throw new IllegalStateException("Objective sense must be ${MAXIMIZE} or ${MINIMIZE}; found ${sense}")
        }
        sense
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

    private static ModelValue optionalMatrix(final MathMeta mathMeta, final List<ModelValue> modelData,
                                             final String purpose) {
        List<ModelValue> matches = byPurpose(modelData, purpose)
        if (matches.empty) return null
        if (matches.size() != 1) throw new IllegalStateException("Expected at most one ${purpose}; found ${matches.size()}")
        String matrixId = matches.first().get('matrixId') as String
        if (!matrixId) throw new IllegalStateException("${purpose} must reference a Matrix")
        ModelValue matrix = mathMeta.entity('Matrix').findByName(matrixId)
        if (matrix == null) throw new IllegalStateException("Missing Matrix '${matrixId}'")
        matrix
    }

    private static ModelValue exactlyOne(final List<ModelValue> modelData, final String purpose) {
        List<ModelValue> matches = byPurpose(modelData, purpose)
        if (matches.size() != 1) throw new IllegalStateException("Expected exactly one ${purpose}; found ${matches.size()}")
        matches.first()
    }

    private static List<ModelValue> byPurpose(final List<ModelValue> modelData, final String purpose) {
        modelData.findAll { ModelValue value -> value.get('purposeEnumId') == purpose } as List<ModelValue>
    }

    private static List<String> stringVector(final ModelValue vector, final String label) {
        Object parsed = parseArray(vector.get('componentArray'), label)
        if (!(parsed instanceof List) || ((List<?>) parsed).any { Object value -> !(value instanceof CharSequence) }) {
            throw new IllegalStateException("${label} must be a JSON array of strings")
        }
        List<String> values = ((List<?>) parsed).collect { Object value -> value.toString() }
        if (values.empty || values.any { String value -> !value } || values.toSet().size() != values.size()) {
            throw new IllegalStateException('Decision variable names must be non-empty and unique')
        }
        values
    }

    private static double[] numericVector(final ModelValue vector, final String label) {
        Object parsed = parseArray(vector.get('componentArray'), label)
        if (!(parsed instanceof List) || ((List<?>) parsed).any { Object value -> !(value instanceof Number) }) {
            throw new IllegalStateException("${label} must be a JSON array of numbers")
        }
        List<?> values = (List<?>) parsed
        double[] result = new double[values.size()]
        for (int index = 0; index < values.size(); index++) result[index] = ((Number) values[index]).doubleValue()
        result
    }

    private static double[][] numericMatrix(final ModelValue matrix, final String label) {
        Object parsed = parseArray(matrix.get('componentArray'), label)
        if (!(parsed instanceof List) || ((List<?>) parsed).empty) {
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

    private static double[][] defaultBounds(final int variableCount) {
        double[][] bounds = new double[2][variableCount]
        Arrays.fill(bounds[0], 0d)
        Arrays.fill(bounds[1], Double.POSITIVE_INFINITY)
        bounds
    }

    private static void validateDimensions(final List<String> variables, final double[] costs,
                                           final double[][] coefficients, final double[] rhs) {
        if (costs.length != variables.size()) {
            throw new IllegalStateException("Cost vector has ${costs.length} values for ${variables.size()} variables")
        }
        if (coefficients.length != rhs.length) {
            throw new IllegalStateException("Constraint matrix has ${coefficients.length} rows but RHS has ${rhs.length} values")
        }
        for (int row = 0; row < coefficients.length; row++) {
            if (coefficients[row].length != variables.size()) {
                throw new IllegalStateException("Constraint row ${row} has ${coefficients[row].length} values for ${variables.size()} variables")
            }
        }
    }
}
