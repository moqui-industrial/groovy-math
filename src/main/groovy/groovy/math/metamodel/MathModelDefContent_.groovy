/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MathModelDefContent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MathModelDefContent

@CompileStatic
class MathModelDefContent_ {
    public static final String ENTITY_NAME = 'MathModelDefContent'
    public static final String FULL_NAME = 'moqui.math.MathModelDefContent'

    public static final Attribute<MathModelDefContent, String> mathModelContentId = new Attribute<>('mathModelContentId', MathModelDefContent.class, String.class, true, true)
    public static final Attribute<MathModelDefContent, String> mathModelDefId = new Attribute<>('mathModelDefId', MathModelDefContent.class, String.class, false, true)
    public static final Attribute<MathModelDefContent, String> contentLocation = new Attribute<>('contentLocation', MathModelDefContent.class, String.class, false, false)
    public static final Attribute<MathModelDefContent, String> contentTypeEnumId = new Attribute<>('contentTypeEnumId', MathModelDefContent.class, String.class, false, false)
    public static final Attribute<MathModelDefContent, String> purposeEnumId = new Attribute<>('purposeEnumId', MathModelDefContent.class, String.class, false, false)
    public static final Attribute<MathModelDefContent, java.sql.Timestamp> contentDate = new Attribute<>('contentDate', MathModelDefContent.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<MathModelDefContent, String> description = new Attribute<>('description', MathModelDefContent.class, String.class, false, false)
    public static final Attribute<MathModelDefContent, String> userId = new Attribute<>('userId', MathModelDefContent.class, String.class, false, false)
}
