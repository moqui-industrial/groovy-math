/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.CoordinateSystemMetric
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
@EqualsAndHashCode(includes = ['coordinateSystemId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class CoordinateSystemMetric implements Serializable {
    private static final long serialVersionUID = 1L

    /** coordinateSystemId */
    String coordinateSystemId

    /** gramMatrixId */
    String gramMatrixId

    CoordinateSystem coordinateSystem

    Matrix G

    CoordinateSystemMetric() {}

    CoordinateSystemMetric(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId')?.toString()
            if (args.containsKey('gramMatrixId')) this.gramMatrixId = args.get('gramMatrixId')?.toString()
        }
    }

    CoordinateSystemMetric coordinateSystemId(String value) {
        this.coordinateSystemId = value
        return this;
    }

    CoordinateSystemMetric gramMatrixId(String value) {
        this.gramMatrixId = value
        return this;
    }

    CoordinateSystemMetric coordinateSystem(CoordinateSystem item) {
        this.coordinateSystem = item;
        return this;
    }

    CoordinateSystemMetric G(Matrix item) {
        this.G = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.coordinateSystemId != null) map.put('coordinateSystemId', this.coordinateSystemId);
        if (this.gramMatrixId != null) map.put('gramMatrixId', this.gramMatrixId);
        return map;
    }
}