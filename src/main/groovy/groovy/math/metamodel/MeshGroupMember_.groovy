/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshGroupMember
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshGroupMember

@CompileStatic
class MeshGroupMember_ {
    public static final String ENTITY_NAME = 'MeshGroupMember'
    public static final String FULL_NAME = 'moqui.math.MeshGroupMember'

    public static final Attribute<MeshGroupMember, String> meshGroupId = new Attribute<>('meshGroupId', MeshGroupMember.class, String.class, true, true)
    public static final Attribute<MeshGroupMember, String> meshKCellId = new Attribute<>('meshKCellId', MeshGroupMember.class, String.class, true, true)
    public static final Attribute<MeshGroupMember, String> purposeEnumId = new Attribute<>('purposeEnumId', MeshGroupMember.class, String.class, false, false)
    public static final Attribute<MeshGroupMember, Long> sequenceNum = new Attribute<>('sequenceNum', MeshGroupMember.class, Long.class, false, false)
    public static final Attribute<MeshGroupMember, String> description = new Attribute<>('description', MeshGroupMember.class, String.class, false, false)
}
