/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshKCellVertex
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshKCellVertex

@CompileStatic
class MeshKCellVertex_ {
    public static final String ENTITY_NAME = 'MeshKCellVertex'
    public static final String FULL_NAME = 'moqui.math.MeshKCellVertex'

    public static final Attribute<MeshKCellVertex, String> meshKCellId = new Attribute<>('meshKCellId', MeshKCellVertex.class, String.class, true, true)
    public static final Attribute<MeshKCellVertex, String> graphVertexId = new Attribute<>('graphVertexId', MeshKCellVertex.class, String.class, true, true)
    public static final Attribute<MeshKCellVertex, Long> sequenceNum = new Attribute<>('sequenceNum', MeshKCellVertex.class, Long.class, false, true)
    public static final Attribute<MeshKCellVertex, String> isUniqueReference = new Attribute<>('isUniqueReference', MeshKCellVertex.class, String.class, false, false)
}
