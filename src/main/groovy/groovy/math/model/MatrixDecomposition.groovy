/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MatrixDecomposition
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['transformationId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MatrixDecomposition implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** leftMatrixId */
    String leftMatrixId

    /** diagMatrixId */
    String diagMatrixId

    /** rightMatrixId */
    String rightMatrixId

    /** rankApproximation */
    Long rankApproximation

    /** explainedVariance */
    BigDecimal explainedVariance

    /** fitError */
    BigDecimal fitError

    Transformation transformation

    Matrix leftMatrix

    Matrix diagMatrix

    Matrix rightMatrix

    MatrixDecomposition() {}

    MatrixDecomposition(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('leftMatrixId')) this.leftMatrixId = args.get('leftMatrixId')?.toString()
            if (args.containsKey('diagMatrixId')) this.diagMatrixId = args.get('diagMatrixId')?.toString()
            if (args.containsKey('rightMatrixId')) this.rightMatrixId = args.get('rightMatrixId')?.toString()
            if (args.containsKey('rankApproximation')) this.rankApproximation = args.get('rankApproximation') != null ? ((Number) args.get('rankApproximation')).longValue() : null
            if (args.containsKey('explainedVariance')) this.explainedVariance = args.get('explainedVariance') != null ? (args.get('explainedVariance') instanceof BigDecimal ? (BigDecimal) args.get('explainedVariance') : new BigDecimal(args.get('explainedVariance').toString())) : null
            if (args.containsKey('fitError')) this.fitError = args.get('fitError') != null ? (args.get('fitError') instanceof BigDecimal ? (BigDecimal) args.get('fitError') : new BigDecimal(args.get('fitError').toString())) : null
        }
    }

    MatrixDecomposition transformationId(String value) {
        this.transformationId = value
        return this;
    }

    MatrixDecomposition leftMatrixId(String value) {
        this.leftMatrixId = value
        return this;
    }

    MatrixDecomposition diagMatrixId(String value) {
        this.diagMatrixId = value
        return this;
    }

    MatrixDecomposition rightMatrixId(String value) {
        this.rightMatrixId = value
        return this;
    }

    MatrixDecomposition rankApproximation(Long value) {
        this.rankApproximation = value
        return this;
    }

    MatrixDecomposition explainedVariance(BigDecimal value) {
        this.explainedVariance = value
        return this;
    }

    MatrixDecomposition fitError(BigDecimal value) {
        this.fitError = value
        return this;
    }

    MatrixDecomposition transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    MatrixDecomposition leftMatrix(Matrix item) {
        this.leftMatrix = item;
        return this;
    }

    MatrixDecomposition diagMatrix(Matrix item) {
        this.diagMatrix = item;
        return this;
    }

    MatrixDecomposition rightMatrix(Matrix item) {
        this.rightMatrix = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.leftMatrixId != null) map.put('leftMatrixId', this.leftMatrixId);
        if (this.diagMatrixId != null) map.put('diagMatrixId', this.diagMatrixId);
        if (this.rightMatrixId != null) map.put('rightMatrixId', this.rightMatrixId);
        if (this.rankApproximation != null) map.put('rankApproximation', this.rankApproximation);
        if (this.explainedVariance != null) map.put('explainedVariance', this.explainedVariance);
        if (this.fitError != null) map.put('fitError', this.fitError);
        return map;
    }
}