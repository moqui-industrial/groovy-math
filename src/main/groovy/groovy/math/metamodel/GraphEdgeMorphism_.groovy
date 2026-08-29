/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.GraphEdgeMorphism
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.GraphEdgeMorphism

@CompileStatic
class GraphEdgeMorphism_ {
    public static final String ENTITY_NAME = 'GraphEdgeMorphism'
    public static final String FULL_NAME = 'moqui.math.GraphEdgeMorphism'

    public static final Attribute<GraphEdgeMorphism, String> graphEdgeMorphismId = new Attribute<>('graphEdgeMorphismId', GraphEdgeMorphism.class, String.class, true, true)
    public static final Attribute<GraphEdgeMorphism, String> graphCategoryId = new Attribute<>('graphCategoryId', GraphEdgeMorphism.class, String.class, false, true)
    public static final Attribute<GraphEdgeMorphism, String> graphEdgeId = new Attribute<>('graphEdgeId', GraphEdgeMorphism.class, String.class, false, true)
    public static final Attribute<GraphEdgeMorphism, String> morphismId = new Attribute<>('morphismId', GraphEdgeMorphism.class, String.class, false, true)
}
