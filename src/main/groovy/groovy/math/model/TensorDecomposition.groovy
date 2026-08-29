/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorDecomposition
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
class TensorDecomposition implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** decompositionMethodEnumId */
    String decompositionMethodEnumId

    /** coreTensorId */
    String coreTensorId

    /** sourceTensorId */
    String sourceTensorId

    /** targetRank */
    Long targetRank

    /** fitError */
    BigDecimal fitError

    /** explainedVariance */
    BigDecimal explainedVariance

    /** description */
    String description

    Transformation transformation

    Tensor coreTensor

    Tensor sourceTensor

    TensorDecomposition() {}

    TensorDecomposition(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('decompositionMethodEnumId')) this.decompositionMethodEnumId = args.get('decompositionMethodEnumId')?.toString()
            if (args.containsKey('coreTensorId')) this.coreTensorId = args.get('coreTensorId')?.toString()
            if (args.containsKey('sourceTensorId')) this.sourceTensorId = args.get('sourceTensorId')?.toString()
            if (args.containsKey('targetRank')) this.targetRank = args.get('targetRank') != null ? ((Number) args.get('targetRank')).longValue() : null
            if (args.containsKey('fitError')) this.fitError = args.get('fitError') != null ? (args.get('fitError') instanceof BigDecimal ? (BigDecimal) args.get('fitError') : new BigDecimal(args.get('fitError').toString())) : null
            if (args.containsKey('explainedVariance')) this.explainedVariance = args.get('explainedVariance') != null ? (args.get('explainedVariance') instanceof BigDecimal ? (BigDecimal) args.get('explainedVariance') : new BigDecimal(args.get('explainedVariance').toString())) : null
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    TensorDecomposition transformationId(String value) {
        this.transformationId = value
        return this;
    }

    TensorDecomposition decompositionMethodEnumId(String value) {
        this.decompositionMethodEnumId = value
        return this;
    }

    TensorDecomposition coreTensorId(String value) {
        this.coreTensorId = value
        return this;
    }

    TensorDecomposition sourceTensorId(String value) {
        this.sourceTensorId = value
        return this;
    }

    TensorDecomposition targetRank(Long value) {
        this.targetRank = value
        return this;
    }

    TensorDecomposition fitError(BigDecimal value) {
        this.fitError = value
        return this;
    }

    TensorDecomposition explainedVariance(BigDecimal value) {
        this.explainedVariance = value
        return this;
    }

    TensorDecomposition description(String value) {
        this.description = value
        return this;
    }

    TensorDecomposition transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    TensorDecomposition coreTensor(Tensor item) {
        this.coreTensor = item;
        return this;
    }

    TensorDecomposition sourceTensor(Tensor item) {
        this.sourceTensor = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.decompositionMethodEnumId != null) map.put('decompositionMethodEnumId', this.decompositionMethodEnumId);
        if (this.coreTensorId != null) map.put('coreTensorId', this.coreTensorId);
        if (this.sourceTensorId != null) map.put('sourceTensorId', this.sourceTensorId);
        if (this.targetRank != null) map.put('targetRank', this.targetRank);
        if (this.fitError != null) map.put('fitError', this.fitError);
        if (this.explainedVariance != null) map.put('explainedVariance', this.explainedVariance);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}