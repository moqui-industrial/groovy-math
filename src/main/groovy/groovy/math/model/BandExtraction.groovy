/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.BandExtraction
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
class BandExtraction implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** lowerBand */
    Long lowerBand

    /** upperBand */
    Long upperBand

    Transformation transformation

    BandExtraction() {}

    BandExtraction(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('lowerBand')) this.lowerBand = args.get('lowerBand') != null ? ((Number) args.get('lowerBand')).longValue() : null
            if (args.containsKey('upperBand')) this.upperBand = args.get('upperBand') != null ? ((Number) args.get('upperBand')).longValue() : null
        }
    }

    BandExtraction transformationId(String value) {
        this.transformationId = value
        return this;
    }

    BandExtraction lowerBand(Long value) {
        this.lowerBand = value
        return this;
    }

    BandExtraction upperBand(Long value) {
        this.upperBand = value
        return this;
    }

    BandExtraction transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.lowerBand != null) map.put('lowerBand', this.lowerBand);
        if (this.upperBand != null) map.put('upperBand', this.upperBand);
        return map;
    }
}