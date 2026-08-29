/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.GraphContent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.GraphContent

@CompileStatic
class GraphContent_ {
    public static final String ENTITY_NAME = 'GraphContent'
    public static final String FULL_NAME = 'moqui.math.GraphContent'

    public static final Attribute<GraphContent, String> graphContentId = new Attribute<>('graphContentId', GraphContent.class, String.class, true, true)
    public static final Attribute<GraphContent, String> graphId = new Attribute<>('graphId', GraphContent.class, String.class, false, true)
    public static final Attribute<GraphContent, String> contentLocation = new Attribute<>('contentLocation', GraphContent.class, String.class, false, false)
    public static final Attribute<GraphContent, String> contentTypeEnumId = new Attribute<>('contentTypeEnumId', GraphContent.class, String.class, false, false)
    public static final Attribute<GraphContent, java.sql.Timestamp> contentDate = new Attribute<>('contentDate', GraphContent.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<GraphContent, String> description = new Attribute<>('description', GraphContent.class, String.class, false, false)
    public static final Attribute<GraphContent, String> userId = new Attribute<>('userId', GraphContent.class, String.class, false, false)
}
