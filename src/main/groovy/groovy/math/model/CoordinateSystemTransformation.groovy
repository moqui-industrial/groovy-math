/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.CoordinateSystemTransformation
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
class CoordinateSystemTransformation implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** sourceCoordinateSystemId */
    String sourceCoordinateSystemId

    /** targetCoordinateSystemId */
    String targetCoordinateSystemId

    /** matrixId */
    String matrixId

    Transformation transformation

    CoordinateSystem sourceCoordSystem

    CoordinateSystem targetCoordSystem

    Matrix matrix

    CoordinateSystemTransformation() {}

    CoordinateSystemTransformation(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('sourceCoordinateSystemId')) this.sourceCoordinateSystemId = args.get('sourceCoordinateSystemId')?.toString()
            if (args.containsKey('targetCoordinateSystemId')) this.targetCoordinateSystemId = args.get('targetCoordinateSystemId')?.toString()
            if (args.containsKey('matrixId')) this.matrixId = args.get('matrixId')?.toString()
        }
    }

    CoordinateSystemTransformation transformationId(String value) {
        this.transformationId = value
        return this;
    }

    CoordinateSystemTransformation sourceCoordinateSystemId(String value) {
        this.sourceCoordinateSystemId = value
        return this;
    }

    CoordinateSystemTransformation targetCoordinateSystemId(String value) {
        this.targetCoordinateSystemId = value
        return this;
    }

    CoordinateSystemTransformation matrixId(String value) {
        this.matrixId = value
        return this;
    }

    CoordinateSystemTransformation transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    CoordinateSystemTransformation sourceCoordSystem(CoordinateSystem item) {
        this.sourceCoordSystem = item;
        return this;
    }

    CoordinateSystemTransformation targetCoordSystem(CoordinateSystem item) {
        this.targetCoordSystem = item;
        return this;
    }

    CoordinateSystemTransformation matrix(Matrix item) {
        this.matrix = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.sourceCoordinateSystemId != null) map.put('sourceCoordinateSystemId', this.sourceCoordinateSystemId);
        if (this.targetCoordinateSystemId != null) map.put('targetCoordinateSystemId', this.targetCoordinateSystemId);
        if (this.matrixId != null) map.put('matrixId', this.matrixId);
        return map;
    }
}