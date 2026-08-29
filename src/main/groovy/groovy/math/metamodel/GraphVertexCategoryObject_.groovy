/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.GraphVertexCategoryObject
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.GraphVertexCategoryObject

@CompileStatic
class GraphVertexCategoryObject_ {
    public static final String ENTITY_NAME = 'GraphVertexCategoryObject'
    public static final String FULL_NAME = 'moqui.math.GraphVertexCategoryObject'

    public static final Attribute<GraphVertexCategoryObject, String> graphVertexCategoryObjectId = new Attribute<>('graphVertexCategoryObjectId', GraphVertexCategoryObject.class, String.class, true, true)
    public static final Attribute<GraphVertexCategoryObject, String> graphCategoryId = new Attribute<>('graphCategoryId', GraphVertexCategoryObject.class, String.class, false, true)
    public static final Attribute<GraphVertexCategoryObject, String> graphVertexId = new Attribute<>('graphVertexId', GraphVertexCategoryObject.class, String.class, false, true)
    public static final Attribute<GraphVertexCategoryObject, String> categoryObjectId = new Attribute<>('categoryObjectId', GraphVertexCategoryObject.class, String.class, false, true)
}
