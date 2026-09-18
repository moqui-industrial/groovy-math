/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MatrixContent
 * JPA Criteria-style Metamodel Descriptor
 */
package org.moqui.math.metamodel

import groovy.transform.CompileStatic
import org.moqui.math.model.MatrixContent

@CompileStatic
class MatrixContent_ {
    public static final String ENTITY_NAME = 'MatrixContent'
    public static final String FULL_NAME = 'moqui.math.MatrixContent'

    public static final Attribute<MatrixContent, String> matrixContentId = new Attribute<>('matrixContentId', MatrixContent.class, String.class, true, true)
    public static final Attribute<MatrixContent, String> matrixId = new Attribute<>('matrixId', MatrixContent.class, String.class, false, true)
    public static final Attribute<MatrixContent, String> contentLocation = new Attribute<>('contentLocation', MatrixContent.class, String.class, false, false)
    public static final Attribute<MatrixContent, String> contentTypeEnumId = new Attribute<>('contentTypeEnumId', MatrixContent.class, String.class, false, false)
    public static final Attribute<MatrixContent, String> arrayChecksum = new Attribute<>('arrayChecksum', MatrixContent.class, String.class, false, false)
    public static final Attribute<MatrixContent, String> arrayEncodingEnumId = new Attribute<>('arrayEncodingEnumId', MatrixContent.class, String.class, false, false)
    public static final Attribute<MatrixContent, java.sql.Timestamp> contentDate = new Attribute<>('contentDate', MatrixContent.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<MatrixContent, String> description = new Attribute<>('description', MatrixContent.class, String.class, false, false)
    public static final Attribute<MatrixContent, String> userId = new Attribute<>('userId', MatrixContent.class, String.class, false, false)
}
