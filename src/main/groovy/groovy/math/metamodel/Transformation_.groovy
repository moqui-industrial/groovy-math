/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.Transformation
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Transformation

@CompileStatic
class Transformation_ {
    public static final String ENTITY_NAME = 'Transformation'
    public static final String FULL_NAME = 'moqui.math.Transformation'

    public static final Attribute<Transformation, String> transformationId = new Attribute<>('transformationId', Transformation.class, String.class, true, true)
    public static final Attribute<Transformation, String> parentTransformationId = new Attribute<>('parentTransformationId', Transformation.class, String.class, false, false)
    public static final Attribute<Transformation, String> transformationTypeEnumId = new Attribute<>('transformationTypeEnumId', Transformation.class, String.class, false, true)
    public static final Attribute<Transformation, String> purposeEnumId = new Attribute<>('purposeEnumId', Transformation.class, String.class, false, false)
    public static final Attribute<Transformation, String> name = new Attribute<>('name', Transformation.class, String.class, false, false)
    public static final Attribute<Transformation, String> symbol = new Attribute<>('symbol', Transformation.class, String.class, false, false)
    public static final Attribute<Transformation, String> description = new Attribute<>('description', Transformation.class, String.class, false, false)
    public static final Attribute<Transformation, java.sql.Timestamp> lastApplicationDate = new Attribute<>('lastApplicationDate', Transformation.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<Transformation, String> resultVectorId = new Attribute<>('resultVectorId', Transformation.class, String.class, false, false)
    public static final Attribute<Transformation, String> resultMatrixId = new Attribute<>('resultMatrixId', Transformation.class, String.class, false, false)
    public static final Attribute<Transformation, String> resultTensorId = new Attribute<>('resultTensorId', Transformation.class, String.class, false, false)
    public static final Attribute<Transformation, String> resultFunctionId = new Attribute<>('resultFunctionId', Transformation.class, String.class, false, false)
    public static final Attribute<Transformation, String> resultParameterId = new Attribute<>('resultParameterId', Transformation.class, String.class, false, false)
}
