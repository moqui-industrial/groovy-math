/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.Graph
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Graph

@CompileStatic
class Graph_ {
    public static final String ENTITY_NAME = 'Graph'
    public static final String FULL_NAME = 'moqui.math.Graph'

    public static final Attribute<Graph, String> graphId = new Attribute<>('graphId', Graph.class, String.class, true, true)
    public static final Attribute<Graph, String> parentGraphId = new Attribute<>('parentGraphId', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> graphTypeEnumId = new Attribute<>('graphTypeEnumId', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> vectorSpaceEnumId = new Attribute<>('vectorSpaceEnumId', Graph.class, String.class, false, false)
    public static final Attribute<Graph, Long> graphOrder = new Attribute<>('graphOrder', Graph.class, Long.class, false, false)
    public static final Attribute<Graph, Long> size = new Attribute<>('size', Graph.class, Long.class, false, false)
    public static final Attribute<Graph, Long> maxDegree = new Attribute<>('maxDegree', Graph.class, Long.class, false, false)
    public static final Attribute<Graph, String> incidenceFunctionServiceName = new Attribute<>('incidenceFunctionServiceName', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> loopsAllowed = new Attribute<>('loopsAllowed', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> isTrivial = new Attribute<>('isTrivial', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> isRegular = new Attribute<>('isRegular', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> isEdgeless = new Attribute<>('isEdgeless', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> isEmpty = new Attribute<>('isEmpty', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> isVertexTransitive = new Attribute<>('isVertexTransitive', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> isConnected = new Attribute<>('isConnected', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> isPlanar = new Attribute<>('isPlanar', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> isWeighted = new Attribute<>('isWeighted', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> adjacencyMatrixId = new Attribute<>('adjacencyMatrixId', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> incidenceMatrixId = new Attribute<>('incidenceMatrixId', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> degreeMatrixId = new Attribute<>('degreeMatrixId', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> laplacianMatrixId = new Attribute<>('laplacianMatrixId', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> name = new Attribute<>('name', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> description = new Attribute<>('description', Graph.class, String.class, false, false)
    public static final Attribute<Graph, String> topology = new Attribute<>('topology', Graph.class, String.class, false, false)
    public static final Attribute<Graph, byte[]> topologyBlob = new Attribute<>('topologyBlob', Graph.class, byte[].class, false, false)
}
