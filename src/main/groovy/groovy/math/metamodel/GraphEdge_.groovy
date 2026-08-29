/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.GraphEdge
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.GraphEdge

@CompileStatic
class GraphEdge_ {
    public static final String ENTITY_NAME = 'GraphEdge'
    public static final String FULL_NAME = 'moqui.math.GraphEdge'

    public static final Attribute<GraphEdge, String> graphEdgeId = new Attribute<>('graphEdgeId', GraphEdge.class, String.class, true, true)
    public static final Attribute<GraphEdge, String> graphId = new Attribute<>('graphId', GraphEdge.class, String.class, false, true)
    public static final Attribute<GraphEdge, String> edgeTypeEnumId = new Attribute<>('edgeTypeEnumId', GraphEdge.class, String.class, false, false)
    public static final Attribute<GraphEdge, String> fromVertexId = new Attribute<>('fromVertexId', GraphEdge.class, String.class, false, true)
    public static final Attribute<GraphEdge, String> toVertexId = new Attribute<>('toVertexId', GraphEdge.class, String.class, false, true)
    public static final Attribute<GraphEdge, String> isDirected = new Attribute<>('isDirected', GraphEdge.class, String.class, false, false)
    public static final Attribute<GraphEdge, String> isLoop = new Attribute<>('isLoop', GraphEdge.class, String.class, false, false)
    public static final Attribute<GraphEdge, String> label = new Attribute<>('label', GraphEdge.class, String.class, false, false)
    public static final Attribute<GraphEdge, BigDecimal> length = new Attribute<>('length', GraphEdge.class, BigDecimal.class, false, false)
    public static final Attribute<GraphEdge, BigDecimal> weight = new Attribute<>('weight', GraphEdge.class, BigDecimal.class, false, false)
}
