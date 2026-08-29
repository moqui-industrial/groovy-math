/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshGroup
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshGroup

@CompileStatic
class MeshGroup_ {
    public static final String ENTITY_NAME = 'MeshGroup'
    public static final String FULL_NAME = 'moqui.math.MeshGroup'

    public static final Attribute<MeshGroup, String> meshGroupId = new Attribute<>('meshGroupId', MeshGroup.class, String.class, true, true)
    public static final Attribute<MeshGroup, String> meshId = new Attribute<>('meshId', MeshGroup.class, String.class, false, true)
    public static final Attribute<MeshGroup, String> groupTypeEnumId = new Attribute<>('groupTypeEnumId', MeshGroup.class, String.class, false, false)
    public static final Attribute<MeshGroup, String> purposeEnumId = new Attribute<>('purposeEnumId', MeshGroup.class, String.class, false, false)
    public static final Attribute<MeshGroup, String> mathModelId = new Attribute<>('mathModelId', MeshGroup.class, String.class, false, false)
    public static final Attribute<MeshGroup, String> groupName = new Attribute<>('groupName', MeshGroup.class, String.class, false, false)
    public static final Attribute<MeshGroup, String> description = new Attribute<>('description', MeshGroup.class, String.class, false, false)
}
