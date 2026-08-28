/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphVertex
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
@EqualsAndHashCode(includes = ['graphVertexId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class GraphVertex implements Serializable {
    private static final long serialVersionUID = 1L

    String graphVertexId
    String graphId // Required
    Long degree
    String isCutVertex
    String notBelongToEdge
    String label
    String positionVectorId

    // --- Relationships (In-Memory Navigation) ---
    Graph graph
    Vector positionVector
    List<GraphEdge> outgoingEdges = []
    List<GraphEdge> incomingEdges = []
    List<Parameter> parameters = []

    GraphVertex() { }

    GraphVertex(String graphVertexId) {
        this.graphVertexId = Objects.requireNonNull(graphVertexId, "GraphVertex.graphVertexId cannot be null")
    }

    GraphVertex(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId') as String
            if (args.containsKey('graphId')) this.graphId = args.get('graphId') as String
            if (args.containsKey('degree')) this.degree = args.get('degree') as Long
            if (args.containsKey('isCutVertex')) this.isCutVertex = args.get('isCutVertex') as String
            if (args.containsKey('notBelongToEdge')) this.notBelongToEdge = args.get('notBelongToEdge') as String
            if (args.containsKey('label')) this.label = args.get('label') as String
            if (args.containsKey('positionVectorId')) this.positionVectorId = args.get('positionVectorId') as String
            if (args.containsKey('graph')) this.graph = args.get('graph') as Graph
            if (args.containsKey('positionVector')) this.positionVector = args.get('positionVector') as Vector
            if (args.containsKey('outgoingEdges')) this.outgoingEdges = args.get('outgoingEdges') as List<GraphEdge>
            if (args.containsKey('incomingEdges')) this.incomingEdges = args.get('incomingEdges') as List<GraphEdge>
            if (args.containsKey('parameters')) this.parameters = args.get('parameters') as List<Parameter>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.graphId == null) throw new IllegalStateException("Required property missing: GraphVertex.graphId")
    }

    /**
     * Gradle-style closure configurator
     */
    GraphVertex configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertex) Closure<?> action) {
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

    Vector positionVector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.positionVector == null) this.positionVector = new Vector()
        this.positionVector.configure(action)
        this.positionVector
    }

    GraphEdge outgoingEdges(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdge) Closure<?> action) {
        GraphEdge item = new GraphEdge()
        item.configure(action)
        if (this.outgoingEdges == null) this.outgoingEdges = []
        this.outgoingEdges.add(item)
        item
    }

    GraphEdge incomingEdges(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdge) Closure<?> action) {
        GraphEdge item = new GraphEdge()
        item.configure(action)
        if (this.incomingEdges == null) this.incomingEdges = []
        this.incomingEdges.add(item)
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
