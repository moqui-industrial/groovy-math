/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.CoordinateSystemBaseVector
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
@EqualsAndHashCode(includes = ['coordinateSystemId', 'vectorId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class CoordinateSystemBaseVector implements Serializable {
    private static final long serialVersionUID = 1L

    /** coordinateSystemId */
    String coordinateSystemId

    /** vectorId */
    String vectorId

    /** vectorPurposeEnumId */
    String vectorPurposeEnumId

    /** baseIndex */
    Long baseIndex

    /** dimensionEnumId */
    String dimensionEnumId

    /** axisName */
    String axisName

    CoordinateSystem coordSystem

    Vector vector

    CoordinateSystemBaseVector() {}

    CoordinateSystemBaseVector(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId')?.toString()
            if (args.containsKey('vectorId')) this.vectorId = args.get('vectorId')?.toString()
            if (args.containsKey('vectorPurposeEnumId')) this.vectorPurposeEnumId = args.get('vectorPurposeEnumId')?.toString()
            if (args.containsKey('baseIndex')) this.baseIndex = args.get('baseIndex') != null ? ((Number) args.get('baseIndex')).longValue() : null
            if (args.containsKey('dimensionEnumId')) this.dimensionEnumId = args.get('dimensionEnumId')?.toString()
            if (args.containsKey('axisName')) this.axisName = args.get('axisName')?.toString()
        }
    }

    CoordinateSystemBaseVector coordinateSystemId(String value) {
        this.coordinateSystemId = value
        return this;
    }

    CoordinateSystemBaseVector vectorId(String value) {
        this.vectorId = value
        return this;
    }

    CoordinateSystemBaseVector vectorPurposeEnumId(String value) {
        this.vectorPurposeEnumId = value
        return this;
    }

    CoordinateSystemBaseVector baseIndex(Long value) {
        this.baseIndex = value
        return this;
    }

    CoordinateSystemBaseVector dimensionEnumId(String value) {
        this.dimensionEnumId = value
        return this;
    }

    CoordinateSystemBaseVector axisName(String value) {
        this.axisName = value
        return this;
    }

    CoordinateSystemBaseVector coordSystem(CoordinateSystem item) {
        this.coordSystem = item;
        return this;
    }

    CoordinateSystemBaseVector vector(Vector item) {
        this.vector = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.coordinateSystemId != null) map.put('coordinateSystemId', this.coordinateSystemId);
        if (this.vectorId != null) map.put('vectorId', this.vectorId);
        if (this.vectorPurposeEnumId != null) map.put('vectorPurposeEnumId', this.vectorPurposeEnumId);
        if (this.baseIndex != null) map.put('baseIndex', this.baseIndex);
        if (this.dimensionEnumId != null) map.put('dimensionEnumId', this.dimensionEnumId);
        if (this.axisName != null) map.put('axisName', this.axisName);
        return map;
    }
}