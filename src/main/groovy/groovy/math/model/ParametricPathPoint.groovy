/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParametricPathPoint
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
@EqualsAndHashCode(includes = ['approximatedFunctionId', 'approximatedFunctionSampleId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class ParametricPathPoint implements Serializable {
    private static final long serialVersionUID = 1L

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** approximatedFunctionSampleId */
    String approximatedFunctionSampleId

    /** isCriticalPoint */
    String isCriticalPoint

    /** tolerance */
    BigDecimal tolerance

    /** arcLength */
    BigDecimal arcLength

    /** weight */
    BigDecimal weight

    ParametricPath parametricPath

    ApproximatedFunctionSample sample

    ParametricPathPoint() {}

    ParametricPathPoint(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('approximatedFunctionSampleId')) this.approximatedFunctionSampleId = args.get('approximatedFunctionSampleId')?.toString()
            if (args.containsKey('isCriticalPoint')) this.isCriticalPoint = args.get('isCriticalPoint')?.toString()
            if (args.containsKey('tolerance')) this.tolerance = args.get('tolerance') != null ? (args.get('tolerance') instanceof BigDecimal ? (BigDecimal) args.get('tolerance') : new BigDecimal(args.get('tolerance').toString())) : null
            if (args.containsKey('arcLength')) this.arcLength = args.get('arcLength') != null ? (args.get('arcLength') instanceof BigDecimal ? (BigDecimal) args.get('arcLength') : new BigDecimal(args.get('arcLength').toString())) : null
            if (args.containsKey('weight')) this.weight = args.get('weight') != null ? (args.get('weight') instanceof BigDecimal ? (BigDecimal) args.get('weight') : new BigDecimal(args.get('weight').toString())) : null
        }
    }

    ParametricPathPoint approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    ParametricPathPoint approximatedFunctionSampleId(String value) {
        this.approximatedFunctionSampleId = value
        return this;
    }

    ParametricPathPoint isCriticalPoint(String value) {
        this.isCriticalPoint = value
        return this;
    }

    ParametricPathPoint tolerance(BigDecimal value) {
        this.tolerance = value
        return this;
    }

    ParametricPathPoint arcLength(BigDecimal value) {
        this.arcLength = value
        return this;
    }

    ParametricPathPoint weight(BigDecimal value) {
        this.weight = value
        return this;
    }

    ParametricPathPoint parametricPath(ParametricPath item) {
        this.parametricPath = item;
        return this;
    }

    ParametricPathPoint sample(ApproximatedFunctionSample item) {
        this.sample = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.approximatedFunctionSampleId != null) map.put('approximatedFunctionSampleId', this.approximatedFunctionSampleId);
        if (this.isCriticalPoint != null) map.put('isCriticalPoint', this.isCriticalPoint);
        if (this.tolerance != null) map.put('tolerance', this.tolerance);
        if (this.arcLength != null) map.put('arcLength', this.arcLength);
        if (this.weight != null) map.put('weight', this.weight);
        return map;
    }
}