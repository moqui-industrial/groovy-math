/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.DiagonalExtraction
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
class DiagonalExtraction implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** axis1 */
    Long axis1

    /** axis2 */
    Long axis2

    /** axisOffset */
    Long axisOffset

    Transformation transformation

    DiagonalExtraction() {}

    DiagonalExtraction(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('axis1')) this.axis1 = args.get('axis1') != null ? ((Number) args.get('axis1')).longValue() : null
            if (args.containsKey('axis2')) this.axis2 = args.get('axis2') != null ? ((Number) args.get('axis2')).longValue() : null
            if (args.containsKey('axisOffset')) this.axisOffset = args.get('axisOffset') != null ? ((Number) args.get('axisOffset')).longValue() : null
        }
    }

    DiagonalExtraction transformationId(String value) {
        this.transformationId = value
        return this;
    }

    DiagonalExtraction axis1(Long value) {
        this.axis1 = value
        return this;
    }

    DiagonalExtraction axis2(Long value) {
        this.axis2 = value
        return this;
    }

    DiagonalExtraction axisOffset(Long value) {
        this.axisOffset = value
        return this;
    }

    DiagonalExtraction transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.axis1 != null) map.put('axis1', this.axis1);
        if (this.axis2 != null) map.put('axis2', this.axis2);
        if (this.axisOffset != null) map.put('axisOffset', this.axisOffset);
        return map;
    }
}