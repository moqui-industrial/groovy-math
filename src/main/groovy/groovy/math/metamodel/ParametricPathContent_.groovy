/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ParametricPathContent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ParametricPathContent

@CompileStatic
class ParametricPathContent_ {
    public static final String ENTITY_NAME = 'ParametricPathContent'
    public static final String FULL_NAME = 'moqui.math.ParametricPathContent'

    public static final Attribute<ParametricPathContent, String> parametricPathContentId = new Attribute<>('parametricPathContentId', ParametricPathContent.class, String.class, true, true)
    public static final Attribute<ParametricPathContent, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', ParametricPathContent.class, String.class, false, true)
    public static final Attribute<ParametricPathContent, String> contentLocation = new Attribute<>('contentLocation', ParametricPathContent.class, String.class, false, false)
    public static final Attribute<ParametricPathContent, String> contentTypeEnumId = new Attribute<>('contentTypeEnumId', ParametricPathContent.class, String.class, false, false)
    public static final Attribute<ParametricPathContent, java.sql.Timestamp> contentDate = new Attribute<>('contentDate', ParametricPathContent.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<ParametricPathContent, String> description = new Attribute<>('description', ParametricPathContent.class, String.class, false, false)
    public static final Attribute<ParametricPathContent, String> userId = new Attribute<>('userId', ParametricPathContent.class, String.class, false, false)
}
