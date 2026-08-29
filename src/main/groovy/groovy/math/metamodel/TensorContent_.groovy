/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TensorContent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TensorContent

@CompileStatic
class TensorContent_ {
    public static final String ENTITY_NAME = 'TensorContent'
    public static final String FULL_NAME = 'moqui.math.TensorContent'

    public static final Attribute<TensorContent, String> tensorContentId = new Attribute<>('tensorContentId', TensorContent.class, String.class, true, true)
    public static final Attribute<TensorContent, String> tensorId = new Attribute<>('tensorId', TensorContent.class, String.class, false, true)
    public static final Attribute<TensorContent, String> contentLocation = new Attribute<>('contentLocation', TensorContent.class, String.class, false, false)
    public static final Attribute<TensorContent, String> contentTypeEnumId = new Attribute<>('contentTypeEnumId', TensorContent.class, String.class, false, false)
    public static final Attribute<TensorContent, java.sql.Timestamp> contentDate = new Attribute<>('contentDate', TensorContent.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<TensorContent, String> description = new Attribute<>('description', TensorContent.class, String.class, false, false)
    public static final Attribute<TensorContent, String> userId = new Attribute<>('userId', TensorContent.class, String.class, false, false)
}
