/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphEdge
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
@EqualsAndHashCode(includes = ['graphEdgeId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class GraphEdge implements Serializable {
    private static final long serialVersionUID = 1L

    String graphEdgeId
    String graphId // Required
    String edgeTypeEnumId
    String fromVertexId // Required
    String toVertexId // Required
    String isDirected
    String isLoop
    String label
    BigDecimal length
    BigDecimal weight

    // --- Relationships (In-Memory Navigation) ---
    Graph graph
    Object edgeType
    GraphVertex fromVertex
    GraphVertex toVertex
    List<Parameter> parameters = []

    GraphEdge() { }

    GraphEdge(String graphEdgeId) {
        this.graphEdgeId = Objects.requireNonNull(graphEdgeId, "GraphEdge.graphEdgeId cannot be null")
    }

    GraphEdge(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId') as String
            if (args.containsKey('graphId')) this.graphId = args.get('graphId') as String
            if (args.containsKey('edgeTypeEnumId')) this.edgeTypeEnumId = args.get('edgeTypeEnumId') as String
            if (args.containsKey('fromVertexId')) this.fromVertexId = args.get('fromVertexId') as String
            if (args.containsKey('toVertexId')) this.toVertexId = args.get('toVertexId') as String
            if (args.containsKey('isDirected')) this.isDirected = args.get('isDirected') as String
            if (args.containsKey('isLoop')) this.isLoop = args.get('isLoop') as String
            if (args.containsKey('label')) this.label = args.get('label') as String
            if (args.containsKey('length')) this.length = args.get('length') as BigDecimal
            if (args.containsKey('weight')) this.weight = args.get('weight') as BigDecimal
            if (args.containsKey('graph')) this.graph = args.get('graph') as Graph
            if (args.containsKey('edgeType')) this.edgeType = args.get('edgeType') as Object
            if (args.containsKey('fromVertex')) this.fromVertex = args.get('fromVertex') as GraphVertex
            if (args.containsKey('toVertex')) this.toVertex = args.get('toVertex') as GraphVertex
            if (args.containsKey('parameters')) this.parameters = args.get('parameters') as List<Parameter>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.graphId == null) throw new IllegalStateException("Required property missing: GraphEdge.graphId")
        if (this.fromVertexId == null) throw new IllegalStateException("Required property missing: GraphEdge.fromVertexId")
        if (this.toVertexId == null) throw new IllegalStateException("Required property missing: GraphEdge.toVertexId")
    }

    /**
     * Gradle-style closure configurator
     */
    GraphEdge configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdge) Closure<?> action) {
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

    GraphVertex fromVertex(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertex) Closure<?> action) {
        if (this.fromVertex == null) this.fromVertex = new GraphVertex()
        this.fromVertex.configure(action)
        this.fromVertex
    }

    GraphVertex toVertex(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertex) Closure<?> action) {
        if (this.toVertex == null) this.toVertex = new GraphVertex()
        this.toVertex.configure(action)
        this.toVertex
    }

    Parameter parameters(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Parameter) Closure<?> action) {
        Parameter item = new Parameter()
        item.configure(action)
        if (this.parameters == null) this.parameters = []
        this.parameters.add(item)
        item
    }
}
