/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ApproximatedFunction
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ApproximatedFunction

@CompileStatic
class ApproximatedFunction_ {
    public static final String ENTITY_NAME = 'ApproximatedFunction'
    public static final String FULL_NAME = 'moqui.math.ApproximatedFunction'

    public static final Attribute<ApproximatedFunction, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', ApproximatedFunction.class, String.class, true, true)
    public static final Attribute<ApproximatedFunction, String> interpolationEnumId = new Attribute<>('interpolationEnumId', ApproximatedFunction.class, String.class, false, false)
    public static final Attribute<ApproximatedFunction, String> purposeEnumId = new Attribute<>('purposeEnumId', ApproximatedFunction.class, String.class, false, false)
    public static final Attribute<ApproximatedFunction, String> vectorSpaceEnumId = new Attribute<>('vectorSpaceEnumId', ApproximatedFunction.class, String.class, false, true)
    public static final Attribute<ApproximatedFunction, String> codomainTypeEnumId = new Attribute<>('codomainTypeEnumId', ApproximatedFunction.class, String.class, false, true)
    public static final Attribute<ApproximatedFunction, Long> parametrizationDegree = new Attribute<>('parametrizationDegree', ApproximatedFunction.class, Long.class, false, false)
    public static final Attribute<ApproximatedFunction, String> dataStorageEnumId = new Attribute<>('dataStorageEnumId', ApproximatedFunction.class, String.class, false, true)
    public static final Attribute<ApproximatedFunction, String> sampleTensorId = new Attribute<>('sampleTensorId', ApproximatedFunction.class, String.class, false, false)
    public static final Attribute<ApproximatedFunction, String> modeledTransformationId = new Attribute<>('modeledTransformationId', ApproximatedFunction.class, String.class, false, false)
    public static final Attribute<ApproximatedFunction, BigDecimal> maxRelativeError = new Attribute<>('maxRelativeError', ApproximatedFunction.class, BigDecimal.class, false, false)
    public static final Attribute<ApproximatedFunction, String> errorMatrixId = new Attribute<>('errorMatrixId', ApproximatedFunction.class, String.class, false, false)
    public static final Attribute<ApproximatedFunction, String> name = new Attribute<>('name', ApproximatedFunction.class, String.class, false, false)
    public static final Attribute<ApproximatedFunction, String> description = new Attribute<>('description', ApproximatedFunction.class, String.class, false, false)
    public static final Attribute<ApproximatedFunction, java.sql.Timestamp> fromDate = new Attribute<>('fromDate', ApproximatedFunction.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<ApproximatedFunction, java.sql.Timestamp> thruDate = new Attribute<>('thruDate', ApproximatedFunction.class, java.sql.Timestamp.class, false, false)
}
