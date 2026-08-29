/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorElementIndex
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
@EqualsAndHashCode(includes = ['tensorElementId', 'axisIndex'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TensorElementIndex implements Serializable {
    private static final long serialVersionUID = 1L

    /** tensorElementId */
    String tensorElementId

    /** axisIndex */
    Long axisIndex

    /** dimensionValue */
    Long dimensionValue

    TensorElement element

    TensorElementIndex() {}

    TensorElementIndex(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('tensorElementId')) this.tensorElementId = args.get('tensorElementId')?.toString()
            if (args.containsKey('axisIndex')) this.axisIndex = args.get('axisIndex') != null ? ((Number) args.get('axisIndex')).longValue() : null
            if (args.containsKey('dimensionValue')) this.dimensionValue = args.get('dimensionValue') != null ? ((Number) args.get('dimensionValue')).longValue() : null
        }
    }

    TensorElementIndex tensorElementId(String value) {
        this.tensorElementId = value
        return this;
    }

    TensorElementIndex axisIndex(Long value) {
        this.axisIndex = value
        return this;
    }

    TensorElementIndex dimensionValue(Long value) {
        this.dimensionValue = value
        return this;
    }

    TensorElementIndex element(TensorElement item) {
        this.element = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.tensorElementId != null) map.put('tensorElementId', this.tensorElementId);
        if (this.axisIndex != null) map.put('axisIndex', this.axisIndex);
        if (this.dimensionValue != null) map.put('dimensionValue', this.dimensionValue);
        return map;
    }
}