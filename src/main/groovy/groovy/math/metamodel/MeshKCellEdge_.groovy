/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshKCellEdge
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshKCellEdge

@CompileStatic
class MeshKCellEdge_ {
    public static final String ENTITY_NAME = 'MeshKCellEdge'
    public static final String FULL_NAME = 'moqui.math.MeshKCellEdge'

    public static final Attribute<MeshKCellEdge, String> meshKCellId = new Attribute<>('meshKCellId', MeshKCellEdge.class, String.class, true, true)
    public static final Attribute<MeshKCellEdge, String> graphEdgeId = new Attribute<>('graphEdgeId', MeshKCellEdge.class, String.class, true, true)
}
