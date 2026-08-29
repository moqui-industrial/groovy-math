/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Graph
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
@EqualsAndHashCode(includes = ['graphId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Graph implements Serializable {
    private static final long serialVersionUID = 1L

    /** graphId */
    String graphId

    /** parentGraphId */
    String parentGraphId

    /** graphTypeEnumId */
    String graphTypeEnumId

    /** vectorSpaceEnumId */
    String vectorSpaceEnumId

    /** graphOrder */
    Long graphOrder

    /** size */
    Long size

    /** maxDegree */
    Long maxDegree

    /** incidenceFunctionServiceName */
    String incidenceFunctionServiceName

    /** loopsAllowed */
    String loopsAllowed

    /** isTrivial */
    String isTrivial

    /** isRegular */
    String isRegular

    /** isEdgeless */
    String isEdgeless

    /** isEmpty */
    String isEmpty

    /** isVertexTransitive */
    String isVertexTransitive

    /** isConnected */
    String isConnected

    /** isPlanar */
    String isPlanar

    /** isWeighted */
    String isWeighted

    /** adjacencyMatrixId */
    String adjacencyMatrixId

    /** incidenceMatrixId */
    String incidenceMatrixId

    /** degreeMatrixId */
    String degreeMatrixId

    /** laplacianMatrixId */
    String laplacianMatrixId

    /** name */
    String name

    /** description */
    String description

    /** topology */
    String topology

    /** topologyBlob */
    byte[] topologyBlob

    Graph parent

    Matrix adjacencyMatrix

    Matrix incidenceMatrix

    Matrix degreeMatrix

    Matrix laplacianMatrix

    List<GraphVertex> vertices = new ArrayList<>()

    List<GraphEdge> edges = new ArrayList<>()

    Graph() {}

    Graph(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('graphId')) this.graphId = args.get('graphId')?.toString()
            if (args.containsKey('parentGraphId')) this.parentGraphId = args.get('parentGraphId')?.toString()
            if (args.containsKey('graphTypeEnumId')) this.graphTypeEnumId = args.get('graphTypeEnumId')?.toString()
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId')?.toString()
            if (args.containsKey('graphOrder')) this.graphOrder = args.get('graphOrder') != null ? ((Number) args.get('graphOrder')).longValue() : null
            if (args.containsKey('size')) this.size = args.get('size') != null ? ((Number) args.get('size')).longValue() : null
            if (args.containsKey('maxDegree')) this.maxDegree = args.get('maxDegree') != null ? ((Number) args.get('maxDegree')).longValue() : null
            if (args.containsKey('incidenceFunctionServiceName')) this.incidenceFunctionServiceName = args.get('incidenceFunctionServiceName')?.toString()
            if (args.containsKey('loopsAllowed')) this.loopsAllowed = args.get('loopsAllowed')?.toString()
            if (args.containsKey('isTrivial')) this.isTrivial = args.get('isTrivial')?.toString()
            if (args.containsKey('isRegular')) this.isRegular = args.get('isRegular')?.toString()
            if (args.containsKey('isEdgeless')) this.isEdgeless = args.get('isEdgeless')?.toString()
            if (args.containsKey('isEmpty')) this.isEmpty = args.get('isEmpty')?.toString()
            if (args.containsKey('isVertexTransitive')) this.isVertexTransitive = args.get('isVertexTransitive')?.toString()
            if (args.containsKey('isConnected')) this.isConnected = args.get('isConnected')?.toString()
            if (args.containsKey('isPlanar')) this.isPlanar = args.get('isPlanar')?.toString()
            if (args.containsKey('isWeighted')) this.isWeighted = args.get('isWeighted')?.toString()
            if (args.containsKey('adjacencyMatrixId')) this.adjacencyMatrixId = args.get('adjacencyMatrixId')?.toString()
            if (args.containsKey('incidenceMatrixId')) this.incidenceMatrixId = args.get('incidenceMatrixId')?.toString()
            if (args.containsKey('degreeMatrixId')) this.degreeMatrixId = args.get('degreeMatrixId')?.toString()
            if (args.containsKey('laplacianMatrixId')) this.laplacianMatrixId = args.get('laplacianMatrixId')?.toString()
            if (args.containsKey('name')) this.name = args.get('name')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('topology')) this.topology = args.get('topology')?.toString()
            if (args.containsKey('topologyBlob')) this.topologyBlob = (byte[]) args.get('topologyBlob')
        }
    }

    Graph graphId(String value) {
        this.graphId = value
        return this;
    }

    Graph parentGraphId(String value) {
        this.parentGraphId = value
        return this;
    }

    Graph graphTypeEnumId(String value) {
        this.graphTypeEnumId = value
        return this;
    }

    Graph vectorSpaceEnumId(String value) {
        this.vectorSpaceEnumId = value
        return this;
    }

    Graph graphOrder(Long value) {
        this.graphOrder = value
        return this;
    }

    Graph size(Long value) {
        this.size = value
        return this;
    }

    Graph maxDegree(Long value) {
        this.maxDegree = value
        return this;
    }

    Graph incidenceFunctionServiceName(String value) {
        this.incidenceFunctionServiceName = value
        return this;
    }

    Graph loopsAllowed(String value) {
        this.loopsAllowed = value
        return this;
    }

    Graph isTrivial(String value) {
        this.isTrivial = value
        return this;
    }

    Graph isRegular(String value) {
        this.isRegular = value
        return this;
    }

    Graph isEdgeless(String value) {
        this.isEdgeless = value
        return this;
    }

    Graph isEmpty(String value) {
        this.isEmpty = value
        return this;
    }

    Graph isVertexTransitive(String value) {
        this.isVertexTransitive = value
        return this;
    }

    Graph isConnected(String value) {
        this.isConnected = value
        return this;
    }

    Graph isPlanar(String value) {
        this.isPlanar = value
        return this;
    }

    Graph isWeighted(String value) {
        this.isWeighted = value
        return this;
    }

    Graph adjacencyMatrixId(String value) {
        this.adjacencyMatrixId = value
        return this;
    }

    Graph incidenceMatrixId(String value) {
        this.incidenceMatrixId = value
        return this;
    }

    Graph degreeMatrixId(String value) {
        this.degreeMatrixId = value
        return this;
    }

    Graph laplacianMatrixId(String value) {
        this.laplacianMatrixId = value
        return this;
    }

    Graph name(String value) {
        this.name = value
        return this;
    }

    Graph description(String value) {
        this.description = value
        return this;
    }

    Graph topology(String value) {
        this.topology = value
        return this;
    }

    Graph topologyBlob(byte[] value) {
        this.topologyBlob = value
        return this;
    }

    Graph parent(Graph item) {
        this.parent = item;
        return this;
    }

    Graph adjacencyMatrix(Matrix item) {
        this.adjacencyMatrix = item;
        return this;
    }

    Graph incidenceMatrix(Matrix item) {
        this.incidenceMatrix = item;
        return this;
    }

    Graph degreeMatrix(Matrix item) {
        this.degreeMatrix = item;
        return this;
    }

    Graph laplacianMatrix(Matrix item) {
        this.laplacianMatrix = item;
        return this;
    }

    Graph vertices(List<GraphVertex> list) {
        this.vertices = list;
        return this;
    }

    Graph edges(List<GraphEdge> list) {
        this.edges = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.graphId != null) map.put('graphId', this.graphId);
        if (this.parentGraphId != null) map.put('parentGraphId', this.parentGraphId);
        if (this.graphTypeEnumId != null) map.put('graphTypeEnumId', this.graphTypeEnumId);
        if (this.vectorSpaceEnumId != null) map.put('vectorSpaceEnumId', this.vectorSpaceEnumId);
        if (this.graphOrder != null) map.put('graphOrder', this.graphOrder);
        if (this.size != null) map.put('size', this.size);
        if (this.maxDegree != null) map.put('maxDegree', this.maxDegree);
        if (this.incidenceFunctionServiceName != null) map.put('incidenceFunctionServiceName', this.incidenceFunctionServiceName);
        if (this.loopsAllowed != null) map.put('loopsAllowed', this.loopsAllowed);
        if (this.isTrivial != null) map.put('isTrivial', this.isTrivial);
        if (this.isRegular != null) map.put('isRegular', this.isRegular);
        if (this.isEdgeless != null) map.put('isEdgeless', this.isEdgeless);
        if (this.isEmpty != null) map.put('isEmpty', this.isEmpty);
        if (this.isVertexTransitive != null) map.put('isVertexTransitive', this.isVertexTransitive);
        if (this.isConnected != null) map.put('isConnected', this.isConnected);
        if (this.isPlanar != null) map.put('isPlanar', this.isPlanar);
        if (this.isWeighted != null) map.put('isWeighted', this.isWeighted);
        if (this.adjacencyMatrixId != null) map.put('adjacencyMatrixId', this.adjacencyMatrixId);
        if (this.incidenceMatrixId != null) map.put('incidenceMatrixId', this.incidenceMatrixId);
        if (this.degreeMatrixId != null) map.put('degreeMatrixId', this.degreeMatrixId);
        if (this.laplacianMatrixId != null) map.put('laplacianMatrixId', this.laplacianMatrixId);
        if (this.name != null) map.put('name', this.name);
        if (this.description != null) map.put('description', this.description);
        if (this.topology != null) map.put('topology', this.topology);
        if (this.topologyBlob != null) map.put('topologyBlob', this.topologyBlob);
        return map;
    }
}