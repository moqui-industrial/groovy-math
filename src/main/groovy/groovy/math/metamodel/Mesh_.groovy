/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.Mesh
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Mesh

@CompileStatic
class Mesh_ {
    public static final String ENTITY_NAME = 'Mesh'
    public static final String FULL_NAME = 'moqui.math.Mesh'

    public static final Attribute<Mesh, String> meshId = new Attribute<>('meshId', Mesh.class, String.class, true, true)
    public static final Attribute<Mesh, String> graphId = new Attribute<>('graphId', Mesh.class, String.class, false, true)
    public static final Attribute<Mesh, String> parentMeshId = new Attribute<>('parentMeshId', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> meshTypeEnumId = new Attribute<>('meshTypeEnumId', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> purposeEnumId = new Attribute<>('purposeEnumId', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> adaptationTypeEnumId = new Attribute<>('adaptationTypeEnumId', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> sourcePathId = new Attribute<>('sourcePathId', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> orientationEnumId = new Attribute<>('orientationEnumId', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> name = new Attribute<>('name', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> description = new Attribute<>('description', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> boundingBoxMinVectorId = new Attribute<>('boundingBoxMinVectorId', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> boundingBoxMaxVectorId = new Attribute<>('boundingBoxMaxVectorId', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, String> centroidVectorId = new Attribute<>('centroidVectorId', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, BigDecimal> totalCellMeasure = new Attribute<>('totalCellMeasure', Mesh.class, BigDecimal.class, false, false)
    public static final Attribute<Mesh, BigDecimal> characteristicSize = new Attribute<>('characteristicSize', Mesh.class, BigDecimal.class, false, false)
    public static final Attribute<Mesh, String> topology = new Attribute<>('topology', Mesh.class, String.class, false, false)
    public static final Attribute<Mesh, byte[]> topologyBlob = new Attribute<>('topologyBlob', Mesh.class, byte[].class, false, false)
}
