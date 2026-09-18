/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.VectorContent
 * JPA Criteria-style Metamodel Descriptor
 */
package org.moqui.math.metamodel

import groovy.transform.CompileStatic
import org.moqui.math.model.VectorContent

@CompileStatic
class VectorContent_ {
    public static final String ENTITY_NAME = 'VectorContent'
    public static final String FULL_NAME = 'moqui.math.VectorContent'

    public static final Attribute<VectorContent, String> vectorContentId = new Attribute<>('vectorContentId', VectorContent.class, String.class, true, true)
    public static final Attribute<VectorContent, String> vectorId = new Attribute<>('vectorId', VectorContent.class, String.class, false, true)
    public static final Attribute<VectorContent, String> contentLocation = new Attribute<>('contentLocation', VectorContent.class, String.class, false, false)
    public static final Attribute<VectorContent, String> contentTypeEnumId = new Attribute<>('contentTypeEnumId', VectorContent.class, String.class, false, false)
    public static final Attribute<VectorContent, String> arrayChecksum = new Attribute<>('arrayChecksum', VectorContent.class, String.class, false, false)
    public static final Attribute<VectorContent, String> arrayEncodingEnumId = new Attribute<>('arrayEncodingEnumId', VectorContent.class, String.class, false, false)
    public static final Attribute<VectorContent, java.sql.Timestamp> contentDate = new Attribute<>('contentDate', VectorContent.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<VectorContent, String> description = new Attribute<>('description', VectorContent.class, String.class, false, false)
    public static final Attribute<VectorContent, String> userId = new Attribute<>('userId', VectorContent.class, String.class, false, false)
}
