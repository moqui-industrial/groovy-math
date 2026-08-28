/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellVertex
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['meshKCellId', 'graphVertexId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshKCellVertex implements Serializable {
    private static final long serialVersionUID = 1L

    String meshKCellId
    String graphVertexId
    Long sequenceNum // Required
    String isUniqueReference

    // --- Relationships (In-Memory Navigation) ---
    MeshKCell cell
    GraphVertex vertex

    MeshKCellVertex() { }

    MeshKCellVertex(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId') as String
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId') as String
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') as Long
            if (args.containsKey('isUniqueReference')) this.isUniqueReference = args.get('isUniqueReference') as String
            if (args.containsKey('cell')) this.cell = args.get('cell') as MeshKCell
            if (args.containsKey('vertex')) this.vertex = args.get('vertex') as GraphVertex
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.sequenceNum == null) throw new IllegalStateException("Required property missing: MeshKCellVertex.sequenceNum")
    }

    /**
     * Gradle-style closure configurator
     */
    MeshKCellVertex configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCellVertex) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MeshKCell cell(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        if (this.cell == null) this.cell = new MeshKCell()
        this.cell.configure(action)
        this.cell
    }

    GraphVertex vertex(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertex) Closure<?> action) {
        if (this.vertex == null) this.vertex = new GraphVertex()
        this.vertex.configure(action)
        this.vertex
    }
}
