/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCell
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
@EqualsAndHashCode(includes = ['meshKCellId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshKCell implements Serializable {
    private static final long serialVersionUID = 1L

    String meshKCellId
    String meshId // Required
    String kCellTypeEnumId
    Long dimension // Required
    String label
    String description
    BigDecimal measure
    String isBoundary
    String orientationEnumId
    String normalVectorId
    String centroidVectorId

    // --- Relationships (In-Memory Navigation) ---
    Mesh mesh
    Object kCellType
    Object orientation
    Vector normalVector
    Vector centroidVector
    List<MeshKCellVertex> vertices = []
    List<MeshKCellEdge> edges = []
    List<Parameter> parameters = []

    MeshKCell() { }

    MeshKCell(String meshKCellId) {
        this.meshKCellId = Objects.requireNonNull(meshKCellId, "MeshKCell.meshKCellId cannot be null")
    }

    MeshKCell(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId') as String
            if (args.containsKey('meshId')) this.meshId = args.get('meshId') as String
            if (args.containsKey('kCellTypeEnumId')) this.kCellTypeEnumId = args.get('kCellTypeEnumId') as String
            if (args.containsKey('dimension')) this.dimension = args.get('dimension') as Long
            if (args.containsKey('label')) this.label = args.get('label') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('measure')) this.measure = args.get('measure') as BigDecimal
            if (args.containsKey('isBoundary')) this.isBoundary = args.get('isBoundary') as String
            if (args.containsKey('orientationEnumId')) this.orientationEnumId = args.get('orientationEnumId') as String
            if (args.containsKey('normalVectorId')) this.normalVectorId = args.get('normalVectorId') as String
            if (args.containsKey('centroidVectorId')) this.centroidVectorId = args.get('centroidVectorId') as String
            if (args.containsKey('mesh')) this.mesh = args.get('mesh') as Mesh
            if (args.containsKey('kCellType')) this.kCellType = args.get('kCellType') as Object
            if (args.containsKey('orientation')) this.orientation = args.get('orientation') as Object
            if (args.containsKey('normalVector')) this.normalVector = args.get('normalVector') as Vector
            if (args.containsKey('centroidVector')) this.centroidVector = args.get('centroidVector') as Vector
            if (args.containsKey('vertices')) this.vertices = args.get('vertices') as List<MeshKCellVertex>
            if (args.containsKey('edges')) this.edges = args.get('edges') as List<MeshKCellEdge>
            if (args.containsKey('parameters')) this.parameters = args.get('parameters') as List<Parameter>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.meshId == null) throw new IllegalStateException("Required property missing: MeshKCell.meshId")
        if (this.dimension == null) throw new IllegalStateException("Required property missing: MeshKCell.dimension")
    }

    /**
     * Gradle-style closure configurator
     */
    MeshKCell configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Mesh mesh(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Mesh) Closure<?> action) {
        if (this.mesh == null) this.mesh = new Mesh()
        this.mesh.configure(action)
        this.mesh
    }

    Vector normalVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.normalVector == null) this.normalVector = new Vector()
        this.normalVector.configure(action)
        this.normalVector
    }

    Vector centroidVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.centroidVector == null) this.centroidVector = new Vector()
        this.centroidVector.configure(action)
        this.centroidVector
    }

    MeshKCellVertex vertices(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCellVertex) Closure<?> action) {
        MeshKCellVertex item = new MeshKCellVertex()
        item.configure(action)
        if (this.vertices == null) this.vertices = []
        this.vertices.add(item)
        item
    }

    MeshKCellEdge edges(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCellEdge) Closure<?> action) {
        MeshKCellEdge item = new MeshKCellEdge()
        item.configure(action)
        if (this.edges == null) this.edges = []
        this.edges.add(item)
        item
    }

    Parameter parameters(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Parameter) Closure<?> action) {
        Parameter item = new Parameter()
        item.configure(action)
        if (this.parameters == null) this.parameters = []
        this.parameters.add(item)
        item
    }
}
