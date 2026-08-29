/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshContent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshContent

@CompileStatic
class MeshContent_ {
    public static final String ENTITY_NAME = 'MeshContent'
    public static final String FULL_NAME = 'moqui.math.MeshContent'

    public static final Attribute<MeshContent, String> meshContentId = new Attribute<>('meshContentId', MeshContent.class, String.class, true, true)
    public static final Attribute<MeshContent, String> meshId = new Attribute<>('meshId', MeshContent.class, String.class, false, true)
    public static final Attribute<MeshContent, String> contentLocation = new Attribute<>('contentLocation', MeshContent.class, String.class, false, false)
    public static final Attribute<MeshContent, String> contentTypeEnumId = new Attribute<>('contentTypeEnumId', MeshContent.class, String.class, false, false)
    public static final Attribute<MeshContent, java.sql.Timestamp> contentDate = new Attribute<>('contentDate', MeshContent.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<MeshContent, String> description = new Attribute<>('description', MeshContent.class, String.class, false, false)
    public static final Attribute<MeshContent, String> userId = new Attribute<>('userId', MeshContent.class, String.class, false, false)
}
