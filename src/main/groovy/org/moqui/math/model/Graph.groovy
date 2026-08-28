/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Graph
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
@EqualsAndHashCode(includes = ['graphId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Graph implements Serializable {
    private static final long serialVersionUID = 1L

    String graphId
    String parentGraphId
    String graphTypeEnumId
    String vectorSpaceEnumId
    Long graphOrder
    Long size
    Long maxDegree
    String incidenceFunctionServiceName
    String loopsAllowed
    String isTrivial
    String isRegular
    String isEdgeless
    String isEmpty
    String isVertexTransitive
    String isConnected
    String isPlanar
    String isWeighted
    String adjacencyMatrixId
    String incidenceMatrixId
    String degreeMatrixId
    String laplacianMatrixId
    String name
    String description
    String topology
    byte[] topologyBlob

    // --- Relationships (In-Memory Navigation) ---
    Graph parent
    Object type
    Object vectorSpace
    Matrix adjacencyMatrix
    Matrix incidenceMatrix
    Matrix degreeMatrix
    Matrix laplacianMatrix
    List<GraphVertex> vertices = []
    List<GraphEdge> edges = []

    Graph() { }

    Graph(String graphId) {
        this.graphId = Objects.requireNonNull(graphId, "Graph.graphId cannot be null")
    }

    Graph(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('graphId')) this.graphId = args.get('graphId') as String
            if (args.containsKey('parentGraphId')) this.parentGraphId = args.get('parentGraphId') as String
            if (args.containsKey('graphTypeEnumId')) this.graphTypeEnumId = args.get('graphTypeEnumId') as String
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId') as String
            if (args.containsKey('graphOrder')) this.graphOrder = args.get('graphOrder') as Long
            if (args.containsKey('size')) this.size = args.get('size') as Long
            if (args.containsKey('maxDegree')) this.maxDegree = args.get('maxDegree') as Long
            if (args.containsKey('incidenceFunctionServiceName')) this.incidenceFunctionServiceName = args.get('incidenceFunctionServiceName') as String
            if (args.containsKey('loopsAllowed')) this.loopsAllowed = args.get('loopsAllowed') as String
            if (args.containsKey('isTrivial')) this.isTrivial = args.get('isTrivial') as String
            if (args.containsKey('isRegular')) this.isRegular = args.get('isRegular') as String
            if (args.containsKey('isEdgeless')) this.isEdgeless = args.get('isEdgeless') as String
            if (args.containsKey('isEmpty')) this.isEmpty = args.get('isEmpty') as String
            if (args.containsKey('isVertexTransitive')) this.isVertexTransitive = args.get('isVertexTransitive') as String
            if (args.containsKey('isConnected')) this.isConnected = args.get('isConnected') as String
            if (args.containsKey('isPlanar')) this.isPlanar = args.get('isPlanar') as String
            if (args.containsKey('isWeighted')) this.isWeighted = args.get('isWeighted') as String
            if (args.containsKey('adjacencyMatrixId')) this.adjacencyMatrixId = args.get('adjacencyMatrixId') as String
            if (args.containsKey('incidenceMatrixId')) this.incidenceMatrixId = args.get('incidenceMatrixId') as String
            if (args.containsKey('degreeMatrixId')) this.degreeMatrixId = args.get('degreeMatrixId') as String
            if (args.containsKey('laplacianMatrixId')) this.laplacianMatrixId = args.get('laplacianMatrixId') as String
            if (args.containsKey('name')) this.name = args.get('name') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('topology')) this.topology = args.get('topology') as String
            if (args.containsKey('topologyBlob')) this.topologyBlob = args.get('topologyBlob') as byte[]
            if (args.containsKey('parent')) this.parent = args.get('parent') as Graph
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('vectorSpace')) this.vectorSpace = args.get('vectorSpace') as Object
            if (args.containsKey('adjacencyMatrix')) this.adjacencyMatrix = args.get('adjacencyMatrix') as Matrix
            if (args.containsKey('incidenceMatrix')) this.incidenceMatrix = args.get('incidenceMatrix') as Matrix
            if (args.containsKey('degreeMatrix')) this.degreeMatrix = args.get('degreeMatrix') as Matrix
            if (args.containsKey('laplacianMatrix')) this.laplacianMatrix = args.get('laplacianMatrix') as Matrix
            if (args.containsKey('vertices')) this.vertices = args.get('vertices') as List<GraphVertex>
            if (args.containsKey('edges')) this.edges = args.get('edges') as List<GraphEdge>
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
    Graph configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Graph) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Graph parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Graph) Closure<?> action) {
        if (this.parent == null) this.parent = new Graph()
        this.parent.configure(action)
        this.parent
    }

    Matrix adjacencyMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.adjacencyMatrix == null) this.adjacencyMatrix = new Matrix()
        this.adjacencyMatrix.configure(action)
        this.adjacencyMatrix
    }

    Matrix incidenceMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.incidenceMatrix == null) this.incidenceMatrix = new Matrix()
        this.incidenceMatrix.configure(action)
        this.incidenceMatrix
    }

    Matrix degreeMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.degreeMatrix == null) this.degreeMatrix = new Matrix()
        this.degreeMatrix.configure(action)
        this.degreeMatrix
    }

    Matrix laplacianMatrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.laplacianMatrix == null) this.laplacianMatrix = new Matrix()
        this.laplacianMatrix.configure(action)
        this.laplacianMatrix
    }

    GraphVertex vertices(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertex) Closure<?> action) {
        GraphVertex item = new GraphVertex()
        item.configure(action)
        if (this.vertices == null) this.vertices = []
        this.vertices.add(item)
        item
    }

    GraphEdge edges(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdge) Closure<?> action) {
        GraphEdge item = new GraphEdge()
        item.configure(action)
        if (this.edges == null) this.edges = []
        this.edges.add(item)
        item
    }
}
