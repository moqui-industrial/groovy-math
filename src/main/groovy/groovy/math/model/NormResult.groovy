/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.NormResult
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
class NormResult implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** domainEnumId */
    String domainEnumId

    /** orderEnumId */
    String orderEnumId

    /** reductionDimensions */
    String reductionDimensions

    /** keepDimensions */
    String keepDimensions

    /** normValue */
    BigDecimal normValue

    Transformation transformation

    NormResult() {}

    NormResult(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('domainEnumId')) this.domainEnumId = args.get('domainEnumId')?.toString()
            if (args.containsKey('orderEnumId')) this.orderEnumId = args.get('orderEnumId')?.toString()
            if (args.containsKey('reductionDimensions')) this.reductionDimensions = args.get('reductionDimensions')?.toString()
            if (args.containsKey('keepDimensions')) this.keepDimensions = args.get('keepDimensions')?.toString()
            if (args.containsKey('normValue')) this.normValue = args.get('normValue') != null ? (args.get('normValue') instanceof BigDecimal ? (BigDecimal) args.get('normValue') : new BigDecimal(args.get('normValue').toString())) : null
        }
    }

    NormResult transformationId(String value) {
        this.transformationId = value
        return this;
    }

    NormResult domainEnumId(String value) {
        this.domainEnumId = value
        return this;
    }

    NormResult orderEnumId(String value) {
        this.orderEnumId = value
        return this;
    }

    NormResult reductionDimensions(String value) {
        this.reductionDimensions = value
        return this;
    }

    NormResult keepDimensions(String value) {
        this.keepDimensions = value
        return this;
    }

    NormResult normValue(BigDecimal value) {
        this.normValue = value
        return this;
    }

    NormResult transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.domainEnumId != null) map.put('domainEnumId', this.domainEnumId);
        if (this.orderEnumId != null) map.put('orderEnumId', this.orderEnumId);
        if (this.reductionDimensions != null) map.put('reductionDimensions', this.reductionDimensions);
        if (this.keepDimensions != null) map.put('keepDimensions', this.keepDimensions);
        if (this.normValue != null) map.put('normValue', this.normValue);
        return map;
    }
}