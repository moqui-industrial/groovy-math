/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParametricPath
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['approximatedFunctionId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ParametricPath implements Serializable {
    private static final long serialVersionUID = 1L

    String approximatedFunctionId
    String parentPathId
    String profileEnumId
    String isClosed
    String coordinateSystemId
    String coordTransformationId
    String compositionMethodEnumId
    Long compositionSequenceNum
    BigDecimal totalLength
    String boundingBoxMinVectorId
    String boundingBoxMaxVectorId

    // --- Relationships (In-Memory Navigation) ---
    ApproximatedFunction approxFunc
    ParametricPath parent
    Object profile
    CoordinateSystem coordSystem
    Transformation coordTransformation
    Object compositionMethod
    Vector boundingBoxMinVector
    Vector boundingBoxMaxVector
    List<ParametricPathPoint> pathPoints = []
    List<ParametricPathEvent> events = []
    Trajectory trajectory

    ParametricPath() { }

    ParametricPath(String approximatedFunctionId) {
        this.approximatedFunctionId = Objects.requireNonNull(approximatedFunctionId, "ParametricPath.approximatedFunctionId cannot be null")
    }

    ParametricPath(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('parentPathId')) this.parentPathId = args.get('parentPathId') as String
            if (args.containsKey('profileEnumId')) this.profileEnumId = args.get('profileEnumId') as String
            if (args.containsKey('isClosed')) this.isClosed = args.get('isClosed') as String
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId') as String
            if (args.containsKey('coordTransformationId')) this.coordTransformationId = args.get('coordTransformationId') as String
            if (args.containsKey('compositionMethodEnumId')) this.compositionMethodEnumId = args.get('compositionMethodEnumId') as String
            if (args.containsKey('compositionSequenceNum')) this.compositionSequenceNum = args.get('compositionSequenceNum') as Long
            if (args.containsKey('totalLength')) this.totalLength = args.get('totalLength') as BigDecimal
            if (args.containsKey('boundingBoxMinVectorId')) this.boundingBoxMinVectorId = args.get('boundingBoxMinVectorId') as String
            if (args.containsKey('boundingBoxMaxVectorId')) this.boundingBoxMaxVectorId = args.get('boundingBoxMaxVectorId') as String
            if (args.containsKey('approxFunc')) this.approxFunc = args.get('approxFunc') as ApproximatedFunction
            if (args.containsKey('parent')) this.parent = args.get('parent') as ParametricPath
            if (args.containsKey('profile')) this.profile = args.get('profile') as Object
            if (args.containsKey('coordSystem')) this.coordSystem = args.get('coordSystem') as CoordinateSystem
            if (args.containsKey('coordTransformation')) this.coordTransformation = args.get('coordTransformation') as Transformation
            if (args.containsKey('compositionMethod')) this.compositionMethod = args.get('compositionMethod') as Object
            if (args.containsKey('boundingBoxMinVector')) this.boundingBoxMinVector = args.get('boundingBoxMinVector') as Vector
            if (args.containsKey('boundingBoxMaxVector')) this.boundingBoxMaxVector = args.get('boundingBoxMaxVector') as Vector
            if (args.containsKey('pathPoints')) this.pathPoints = args.get('pathPoints') as List<ParametricPathPoint>
            if (args.containsKey('events')) this.events = args.get('events') as List<ParametricPathEvent>
            if (args.containsKey('trajectory')) this.trajectory = args.get('trajectory') as Trajectory
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
    }

    /**
     * Gradle-style closure configurator
     */
    ParametricPath configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPath) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ApproximatedFunction approxFunc(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunction) Closure<?> action) {
        if (this.approxFunc == null) this.approxFunc = new ApproximatedFunction()
        this.approxFunc.configure(action)
        this.approxFunc
    }

    ParametricPath parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPath) Closure<?> action) {
        if (this.parent == null) this.parent = new ParametricPath()
        this.parent.configure(action)
        this.parent
    }

    CoordinateSystem coordSystem(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (this.coordSystem == null) this.coordSystem = new CoordinateSystem()
        this.coordSystem.configure(action)
        this.coordSystem
    }

    Transformation coordTransformation(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (this.coordTransformation == null) this.coordTransformation = new Transformation()
        this.coordTransformation.configure(action)
        this.coordTransformation
    }

    Vector boundingBoxMinVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.boundingBoxMinVector == null) this.boundingBoxMinVector = new Vector()
        this.boundingBoxMinVector.configure(action)
        this.boundingBoxMinVector
    }

    Vector boundingBoxMaxVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.boundingBoxMaxVector == null) this.boundingBoxMaxVector = new Vector()
        this.boundingBoxMaxVector.configure(action)
        this.boundingBoxMaxVector
    }

    ParametricPathPoint pathPoints(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPathPoint) Closure<?> action) {
        ParametricPathPoint item = new ParametricPathPoint()
        item.configure(action)
        if (this.pathPoints == null) this.pathPoints = []
        this.pathPoints.add(item)
        item
    }

    ParametricPathEvent events(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPathEvent) Closure<?> action) {
        ParametricPathEvent item = new ParametricPathEvent()
        item.configure(action)
        if (this.events == null) this.events = []
        this.events.add(item)
        item
    }

    Trajectory trajectory(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Trajectory) Closure<?> action) {
        if (this.trajectory == null) this.trajectory = new Trajectory()
        this.trajectory.configure(action)
        this.trajectory
    }
}
