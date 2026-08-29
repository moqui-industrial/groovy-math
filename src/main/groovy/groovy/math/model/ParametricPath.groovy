/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParametricPath
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
@EqualsAndHashCode(includes = ['approximatedFunctionId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class ParametricPath implements Serializable {
    private static final long serialVersionUID = 1L

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** parentPathId */
    String parentPathId

    /** profileEnumId */
    String profileEnumId

    /** isClosed */
    String isClosed

    /** coordinateSystemId */
    String coordinateSystemId

    /** coordTransformationId */
    String coordTransformationId

    /** compositionMethodEnumId */
    String compositionMethodEnumId

    /** compositionSequenceNum */
    Long compositionSequenceNum

    /** totalLength */
    BigDecimal totalLength

    /** boundingBoxMinVectorId */
    String boundingBoxMinVectorId

    /** boundingBoxMaxVectorId */
    String boundingBoxMaxVectorId

    ApproximatedFunction approxFunc

    ParametricPath parent

    CoordinateSystem coordSystem

    Transformation coordTransformation

    Vector BoundingBoxMinVector

    Vector BoundingBoxMaxVector

    List<ParametricPathPoint> pathPoints = new ArrayList<>()

    List<ParametricPathEvent> events = new ArrayList<>()

    Trajectory trajectory

    ParametricPath() {}

    ParametricPath(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('parentPathId')) this.parentPathId = args.get('parentPathId')?.toString()
            if (args.containsKey('profileEnumId')) this.profileEnumId = args.get('profileEnumId')?.toString()
            if (args.containsKey('isClosed')) this.isClosed = args.get('isClosed')?.toString()
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId')?.toString()
            if (args.containsKey('coordTransformationId')) this.coordTransformationId = args.get('coordTransformationId')?.toString()
            if (args.containsKey('compositionMethodEnumId')) this.compositionMethodEnumId = args.get('compositionMethodEnumId')?.toString()
            if (args.containsKey('compositionSequenceNum')) this.compositionSequenceNum = args.get('compositionSequenceNum') != null ? ((Number) args.get('compositionSequenceNum')).longValue() : null
            if (args.containsKey('totalLength')) this.totalLength = args.get('totalLength') != null ? (args.get('totalLength') instanceof BigDecimal ? (BigDecimal) args.get('totalLength') : new BigDecimal(args.get('totalLength').toString())) : null
            if (args.containsKey('boundingBoxMinVectorId')) this.boundingBoxMinVectorId = args.get('boundingBoxMinVectorId')?.toString()
            if (args.containsKey('boundingBoxMaxVectorId')) this.boundingBoxMaxVectorId = args.get('boundingBoxMaxVectorId')?.toString()
        }
    }

    ParametricPath approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    ParametricPath parentPathId(String value) {
        this.parentPathId = value
        return this;
    }

    ParametricPath profileEnumId(String value) {
        this.profileEnumId = value
        return this;
    }

    ParametricPath isClosed(String value) {
        this.isClosed = value
        return this;
    }

    ParametricPath coordinateSystemId(String value) {
        this.coordinateSystemId = value
        return this;
    }

    ParametricPath coordTransformationId(String value) {
        this.coordTransformationId = value
        return this;
    }

    ParametricPath compositionMethodEnumId(String value) {
        this.compositionMethodEnumId = value
        return this;
    }

    ParametricPath compositionSequenceNum(Long value) {
        this.compositionSequenceNum = value
        return this;
    }

    ParametricPath totalLength(BigDecimal value) {
        this.totalLength = value
        return this;
    }

    ParametricPath boundingBoxMinVectorId(String value) {
        this.boundingBoxMinVectorId = value
        return this;
    }

    ParametricPath boundingBoxMaxVectorId(String value) {
        this.boundingBoxMaxVectorId = value
        return this;
    }

    ParametricPath approxFunc(ApproximatedFunction item) {
        this.approxFunc = item;
        return this;
    }

    ParametricPath parent(ParametricPath item) {
        this.parent = item;
        return this;
    }

    ParametricPath coordSystem(CoordinateSystem item) {
        this.coordSystem = item;
        return this;
    }

    ParametricPath coordTransformation(Transformation item) {
        this.coordTransformation = item;
        return this;
    }

    ParametricPath BoundingBoxMinVector(Vector item) {
        this.BoundingBoxMinVector = item;
        return this;
    }

    ParametricPath BoundingBoxMaxVector(Vector item) {
        this.BoundingBoxMaxVector = item;
        return this;
    }

    ParametricPath pathPoints(List<ParametricPathPoint> list) {
        this.pathPoints = list;
        return this;
    }

    ParametricPath events(List<ParametricPathEvent> list) {
        this.events = list;
        return this;
    }

    ParametricPath trajectory(Trajectory item) {
        this.trajectory = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.parentPathId != null) map.put('parentPathId', this.parentPathId);
        if (this.profileEnumId != null) map.put('profileEnumId', this.profileEnumId);
        if (this.isClosed != null) map.put('isClosed', this.isClosed);
        if (this.coordinateSystemId != null) map.put('coordinateSystemId', this.coordinateSystemId);
        if (this.coordTransformationId != null) map.put('coordTransformationId', this.coordTransformationId);
        if (this.compositionMethodEnumId != null) map.put('compositionMethodEnumId', this.compositionMethodEnumId);
        if (this.compositionSequenceNum != null) map.put('compositionSequenceNum', this.compositionSequenceNum);
        if (this.totalLength != null) map.put('totalLength', this.totalLength);
        if (this.boundingBoxMinVectorId != null) map.put('boundingBoxMinVectorId', this.boundingBoxMinVectorId);
        if (this.boundingBoxMaxVectorId != null) map.put('boundingBoxMaxVectorId', this.boundingBoxMaxVectorId);
        return map;
    }
}