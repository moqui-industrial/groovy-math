/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.GraphVertex
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.GraphVertex

@CompileStatic
class GraphVertex_ {
    public static final String ENTITY_NAME = 'GraphVertex'
    public static final String FULL_NAME = 'moqui.math.GraphVertex'

    public static final Attribute<GraphVertex, String> graphVertexId = new Attribute<>('graphVertexId', GraphVertex.class, String.class, true, true)
    public static final Attribute<GraphVertex, String> graphId = new Attribute<>('graphId', GraphVertex.class, String.class, false, true)
    public static final Attribute<GraphVertex, Long> degree = new Attribute<>('degree', GraphVertex.class, Long.class, false, false)
    public static final Attribute<GraphVertex, String> isCutVertex = new Attribute<>('isCutVertex', GraphVertex.class, String.class, false, false)
    public static final Attribute<GraphVertex, String> notBelongToEdge = new Attribute<>('notBelongToEdge', GraphVertex.class, String.class, false, false)
    public static final Attribute<GraphVertex, String> label = new Attribute<>('label', GraphVertex.class, String.class, false, false)
    public static final Attribute<GraphVertex, String> positionVectorId = new Attribute<>('positionVectorId', GraphVertex.class, String.class, false, false)
}
