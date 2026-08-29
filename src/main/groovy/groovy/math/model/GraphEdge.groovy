/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.GraphEdge
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
@EqualsAndHashCode(includes = ['graphEdgeId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class GraphEdge implements Serializable {
    private static final long serialVersionUID = 1L

    /** graphEdgeId */
    String graphEdgeId

    /** graphId */
    String graphId

    /** edgeTypeEnumId */
    String edgeTypeEnumId

    /** fromVertexId */
    String fromVertexId

    /** toVertexId */
    String toVertexId

    /** isDirected */
    String isDirected

    /** isLoop */
    String isLoop

    /** label */
    String label

    /** length */
    BigDecimal length

    /** weight */
    BigDecimal weight

    Graph graph

    GraphVertex fromVertex

    GraphVertex toVertex

    List<Parameter> parameters = new ArrayList<>()

    GraphEdge() {}

    GraphEdge(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId')?.toString()
            if (args.containsKey('graphId')) this.graphId = args.get('graphId')?.toString()
            if (args.containsKey('edgeTypeEnumId')) this.edgeTypeEnumId = args.get('edgeTypeEnumId')?.toString()
            if (args.containsKey('fromVertexId')) this.fromVertexId = args.get('fromVertexId')?.toString()
            if (args.containsKey('toVertexId')) this.toVertexId = args.get('toVertexId')?.toString()
            if (args.containsKey('isDirected')) this.isDirected = args.get('isDirected')?.toString()
            if (args.containsKey('isLoop')) this.isLoop = args.get('isLoop')?.toString()
            if (args.containsKey('label')) this.label = args.get('label')?.toString()
            if (args.containsKey('length')) this.length = args.get('length') != null ? (args.get('length') instanceof BigDecimal ? (BigDecimal) args.get('length') : new BigDecimal(args.get('length').toString())) : null
            if (args.containsKey('weight')) this.weight = args.get('weight') != null ? (args.get('weight') instanceof BigDecimal ? (BigDecimal) args.get('weight') : new BigDecimal(args.get('weight').toString())) : null
        }
    }

    GraphEdge graphEdgeId(String value) {
        this.graphEdgeId = value
        return this;
    }

    GraphEdge graphId(String value) {
        this.graphId = value
        return this;
    }

    GraphEdge edgeTypeEnumId(String value) {
        this.edgeTypeEnumId = value
        return this;
    }

    GraphEdge fromVertexId(String value) {
        this.fromVertexId = value
        return this;
    }

    GraphEdge toVertexId(String value) {
        this.toVertexId = value
        return this;
    }

    GraphEdge isDirected(String value) {
        this.isDirected = value
        return this;
    }

    GraphEdge isLoop(String value) {
        this.isLoop = value
        return this;
    }

    GraphEdge label(String value) {
        this.label = value
        return this;
    }

    GraphEdge length(BigDecimal value) {
        this.length = value
        return this;
    }

    GraphEdge weight(BigDecimal value) {
        this.weight = value
        return this;
    }

    GraphEdge graph(Graph item) {
        this.graph = item;
        return this;
    }

    GraphEdge fromVertex(GraphVertex item) {
        this.fromVertex = item;
        return this;
    }

    GraphEdge toVertex(GraphVertex item) {
        this.toVertex = item;
        return this;
    }

    GraphEdge parameters(List<Parameter> list) {
        this.parameters = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.graphEdgeId != null) map.put('graphEdgeId', this.graphEdgeId);
        if (this.graphId != null) map.put('graphId', this.graphId);
        if (this.edgeTypeEnumId != null) map.put('edgeTypeEnumId', this.edgeTypeEnumId);
        if (this.fromVertexId != null) map.put('fromVertexId', this.fromVertexId);
        if (this.toVertexId != null) map.put('toVertexId', this.toVertexId);
        if (this.isDirected != null) map.put('isDirected', this.isDirected);
        if (this.isLoop != null) map.put('isLoop', this.isLoop);
        if (this.label != null) map.put('label', this.label);
        if (this.length != null) map.put('length', this.length);
        if (this.weight != null) map.put('weight', this.weight);
        return map;
    }
}