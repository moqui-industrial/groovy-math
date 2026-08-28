/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Mesh
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
@EqualsAndHashCode(includes = ['meshId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Mesh implements Serializable {
    private static final long serialVersionUID = 1L

    String meshId
    String graphId // Required
    String parentMeshId
    String meshTypeEnumId
    String purposeEnumId
    String adaptationTypeEnumId
    String sourcePathId
    String orientationEnumId
    String name
    String description
    String boundingBoxMinVectorId
    String boundingBoxMaxVectorId
    String centroidVectorId
    BigDecimal totalCellMeasure
    BigDecimal characteristicSize
    String topology
    byte[] topologyBlob

    // --- Relationships (In-Memory Navigation) ---
    Graph graph
    Mesh parent
    Object type
    Object purpose
    Object adaptationType
    ParametricPath sourcePath
    Object orientation
    Vector boundingBoxMinVector
    Vector boundingBoxMaxVector
    Vector centroidVector
    List<MeshKCell> cells = []

    Mesh() { }

    Mesh(String meshId) {
        this.meshId = Objects.requireNonNull(meshId, "Mesh.meshId cannot be null")
    }

    Mesh(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshId')) this.meshId = args.get('meshId') as String
            if (args.containsKey('graphId')) this.graphId = args.get('graphId') as String
            if (args.containsKey('parentMeshId')) this.parentMeshId = args.get('parentMeshId') as String
            if (args.containsKey('meshTypeEnumId')) this.meshTypeEnumId = args.get('meshTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('adaptationTypeEnumId')) this.adaptationTypeEnumId = args.get('adaptationTypeEnumId') as String
            if (args.containsKey('sourcePathId')) this.sourcePathId = args.get('sourcePathId') as String
            if (args.containsKey('orientationEnumId')) this.orientationEnumId = args.get('orientationEnumId') as String
            if (args.containsKey('name')) this.name = args.get('name') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('boundingBoxMinVectorId')) this.boundingBoxMinVectorId = args.get('boundingBoxMinVectorId') as String
            if (args.containsKey('boundingBoxMaxVectorId')) this.boundingBoxMaxVectorId = args.get('boundingBoxMaxVectorId') as String
            if (args.containsKey('centroidVectorId')) this.centroidVectorId = args.get('centroidVectorId') as String
            if (args.containsKey('totalCellMeasure')) this.totalCellMeasure = args.get('totalCellMeasure') as BigDecimal
            if (args.containsKey('characteristicSize')) this.characteristicSize = args.get('characteristicSize') as BigDecimal
            if (args.containsKey('topology')) this.topology = args.get('topology') as String
            if (args.containsKey('topologyBlob')) this.topologyBlob = args.get('topologyBlob') as byte[]
            if (args.containsKey('graph')) this.graph = args.get('graph') as Graph
            if (args.containsKey('parent')) this.parent = args.get('parent') as Mesh
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('adaptationType')) this.adaptationType = args.get('adaptationType') as Object
            if (args.containsKey('sourcePath')) this.sourcePath = args.get('sourcePath') as ParametricPath
            if (args.containsKey('orientation')) this.orientation = args.get('orientation') as Object
            if (args.containsKey('boundingBoxMinVector')) this.boundingBoxMinVector = args.get('boundingBoxMinVector') as Vector
            if (args.containsKey('boundingBoxMaxVector')) this.boundingBoxMaxVector = args.get('boundingBoxMaxVector') as Vector
            if (args.containsKey('centroidVector')) this.centroidVector = args.get('centroidVector') as Vector
            if (args.containsKey('cells')) this.cells = args.get('cells') as List<MeshKCell>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.graphId == null) throw new IllegalStateException("Required property missing: Mesh.graphId")
    }

    /**
     * Gradle-style closure configurator
     */
    Mesh configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Mesh) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Graph graph(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Graph) Closure<?> action) {
        if (this.graph == null) this.graph = new Graph()
        this.graph.configure(action)
        this.graph
    }

    Mesh parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Mesh) Closure<?> action) {
        if (this.parent == null) this.parent = new Mesh()
        this.parent.configure(action)
        this.parent
    }

    ParametricPath sourcePath(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPath) Closure<?> action) {
        if (this.sourcePath == null) this.sourcePath = new ParametricPath()
        this.sourcePath.configure(action)
        this.sourcePath
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

    Vector centroidVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.centroidVector == null) this.centroidVector = new Vector()
        this.centroidVector.configure(action)
        this.centroidVector
    }

    MeshKCell cells(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        MeshKCell item = new MeshKCell()
        item.configure(action)
        if (this.cells == null) this.cells = []
        this.cells.add(item)
        item
    }
}
