/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ApproximatedFunctionDerivative
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ApproximatedFunctionDerivative

@CompileStatic
class ApproximatedFunctionDerivative_ {
    public static final String ENTITY_NAME = 'ApproximatedFunctionDerivative'
    public static final String FULL_NAME = 'moqui.math.ApproximatedFunctionDerivative'

    public static final Attribute<ApproximatedFunctionDerivative, String> approximatedFunctionDerivativeId = new Attribute<>('approximatedFunctionDerivativeId', ApproximatedFunctionDerivative.class, String.class, true, true)
    public static final Attribute<ApproximatedFunctionDerivative, String> approximatedFunctionId = new Attribute<>('approximatedFunctionId', ApproximatedFunctionDerivative.class, String.class, false, true)
    public static final Attribute<ApproximatedFunctionDerivative, String> approximatedFunctionSampleId = new Attribute<>('approximatedFunctionSampleId', ApproximatedFunctionDerivative.class, String.class, false, true)
    public static final Attribute<ApproximatedFunctionDerivative, String> purposeEnumId = new Attribute<>('purposeEnumId', ApproximatedFunctionDerivative.class, String.class, false, false)
    public static final Attribute<ApproximatedFunctionDerivative, Long> derivativeOrder = new Attribute<>('derivativeOrder', ApproximatedFunctionDerivative.class, Long.class, false, false)
    public static final Attribute<ApproximatedFunctionDerivative, String> derivativeMatrixId = new Attribute<>('derivativeMatrixId', ApproximatedFunctionDerivative.class, String.class, false, false)
    public static final Attribute<ApproximatedFunctionDerivative, String> derivativeTensorId = new Attribute<>('derivativeTensorId', ApproximatedFunctionDerivative.class, String.class, false, false)
    public static final Attribute<ApproximatedFunctionDerivative, String> derivativeVectorId = new Attribute<>('derivativeVectorId', ApproximatedFunctionDerivative.class, String.class, false, false)
    public static final Attribute<ApproximatedFunctionDerivative, String> errorVectorId = new Attribute<>('errorVectorId', ApproximatedFunctionDerivative.class, String.class, false, false)
}
