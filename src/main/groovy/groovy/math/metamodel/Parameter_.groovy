/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.Parameter
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Parameter

@CompileStatic
class Parameter_ {
    public static final String ENTITY_NAME = 'Parameter'
    public static final String FULL_NAME = 'moqui.math.Parameter'

    public static final Attribute<Parameter, String> parameterId = new Attribute<>('parameterId', Parameter.class, String.class, true, true)
    public static final Attribute<Parameter, String> parameterDefId = new Attribute<>('parameterDefId', Parameter.class, String.class, false, true)
    public static final Attribute<Parameter, String> parameterAlias = new Attribute<>('parameterAlias', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, Long> sequenceNum = new Attribute<>('sequenceNum', Parameter.class, Long.class, false, false)
    public static final Attribute<Parameter, String> parameterUomId = new Attribute<>('parameterUomId', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, BigDecimal> numericValue = new Attribute<>('numericValue', Parameter.class, BigDecimal.class, false, false)
    public static final Attribute<Parameter, String> symbolicValue = new Attribute<>('symbolicValue', Parameter.class, String.class, false, false)
    public static final Attribute<Parameter, String> parameterEnumId = new Attribute<>('parameterEnumId', Parameter.class, String.class, false, false)
}
