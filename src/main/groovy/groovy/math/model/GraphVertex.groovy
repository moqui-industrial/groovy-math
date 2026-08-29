/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphVertex
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
@EqualsAndHashCode(includes = ['graphVertexId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class GraphVertex implements Serializable {
    private static final long serialVersionUID = 1L

    /** graphVertexId */
    String graphVertexId

    /** graphId */
    String graphId

    /** degree */
    Long degree

    /** isCutVertex */
    String isCutVertex

    /** notBelongToEdge */
    String notBelongToEdge

    /** label */
    String label

    /** positionVectorId */
    String positionVectorId

    Graph graph

    Vector positionVector

    List<GraphEdge> outgoingEdges = new ArrayList<>()

    List<GraphEdge> incomingEdges = new ArrayList<>()

    List<Parameter> parameters = new ArrayList<>()

    GraphVertex() {}

    GraphVertex(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId')?.toString()
            if (args.containsKey('graphId')) this.graphId = args.get('graphId')?.toString()
            if (args.containsKey('degree')) this.degree = args.get('degree') != null ? ((Number) args.get('degree')).longValue() : null
            if (args.containsKey('isCutVertex')) this.isCutVertex = args.get('isCutVertex')?.toString()
            if (args.containsKey('notBelongToEdge')) this.notBelongToEdge = args.get('notBelongToEdge')?.toString()
            if (args.containsKey('label')) this.label = args.get('label')?.toString()
            if (args.containsKey('positionVectorId')) this.positionVectorId = args.get('positionVectorId')?.toString()
        }
    }

    GraphVertex graphVertexId(String value) {
        this.graphVertexId = value
        return this;
    }

    GraphVertex graphId(String value) {
        this.graphId = value
        return this;
    }

    GraphVertex degree(Long value) {
        this.degree = value
        return this;
    }

    GraphVertex isCutVertex(String value) {
        this.isCutVertex = value
        return this;
    }

    GraphVertex notBelongToEdge(String value) {
        this.notBelongToEdge = value
        return this;
    }

    GraphVertex label(String value) {
        this.label = value
        return this;
    }

    GraphVertex positionVectorId(String value) {
        this.positionVectorId = value
        return this;
    }

    GraphVertex graph(Graph item) {
        this.graph = item;
        return this;
    }

    GraphVertex positionVector(Vector item) {
        this.positionVector = item;
        return this;
    }

    GraphVertex outgoingEdges(List<GraphEdge> list) {
        this.outgoingEdges = list;
        return this;
    }

    GraphVertex incomingEdges(List<GraphEdge> list) {
        this.incomingEdges = list;
        return this;
    }

    GraphVertex parameters(List<Parameter> list) {
        this.parameters = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.graphVertexId != null) map.put('graphVertexId', this.graphVertexId);
        if (this.graphId != null) map.put('graphId', this.graphId);
        if (this.degree != null) map.put('degree', this.degree);
        if (this.isCutVertex != null) map.put('isCutVertex', this.isCutVertex);
        if (this.notBelongToEdge != null) map.put('notBelongToEdge', this.notBelongToEdge);
        if (this.label != null) map.put('label', this.label);
        if (this.positionVectorId != null) map.put('positionVectorId', this.positionVectorId);
        return map;
    }
}