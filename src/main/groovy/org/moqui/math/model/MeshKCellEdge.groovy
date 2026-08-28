/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellEdge
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
@EqualsAndHashCode(includes = ['meshKCellId', 'graphEdgeId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MeshKCellEdge implements Serializable {
    private static final long serialVersionUID = 1L

    String meshKCellId
    String graphEdgeId

    // --- Relationships (In-Memory Navigation) ---
    MeshKCell kCell
    GraphEdge edge

    MeshKCellEdge() { }

    MeshKCellEdge(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId') as String
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId') as String
            if (args.containsKey('kCell')) this.kCell = args.get('kCell') as MeshKCell
            if (args.containsKey('edge')) this.edge = args.get('edge') as GraphEdge
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
    MeshKCellEdge configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCellEdge) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MeshKCell kCell(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        if (this.kCell == null) this.kCell = new MeshKCell()
        this.kCell.configure(action)
        this.kCell
    }

    GraphEdge edge(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdge) Closure<?> action) {
        if (this.edge == null) this.edge = new GraphEdge()
        this.edge.configure(action)
        this.edge
    }
}
