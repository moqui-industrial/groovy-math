/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ParameterDef
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ParameterDef

@CompileStatic
class ParameterDef_ {
    public static final String ENTITY_NAME = 'ParameterDef'
    public static final String FULL_NAME = 'moqui.math.ParameterDef'

    public static final Attribute<ParameterDef, String> parameterDefId = new Attribute<>('parameterDefId', ParameterDef.class, String.class, true, true)
    public static final Attribute<ParameterDef, String> parentParameterDefId = new Attribute<>('parentParameterDefId', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> parameterTypeEnumId = new Attribute<>('parameterTypeEnumId', ParameterDef.class, String.class, false, true)
    public static final Attribute<ParameterDef, String> declaredTypeObjectId = new Attribute<>('declaredTypeObjectId', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> purposeEnumId = new Attribute<>('purposeEnumId', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> groupEnumId = new Attribute<>('groupEnumId', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> classEnumId = new Attribute<>('classEnumId', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> permissionEnumId = new Attribute<>('permissionEnumId', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> userId = new Attribute<>('userId', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> uomTypeEnumId = new Attribute<>('uomTypeEnumId', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> externalId = new Attribute<>('externalId', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> isRequired = new Attribute<>('isRequired', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, Long> priority = new Attribute<>('priority', ParameterDef.class, Long.class, false, false)
    public static final Attribute<ParameterDef, String> parameterCode = new Attribute<>('parameterCode', ParameterDef.class, String.class, false, true)
    public static final Attribute<ParameterDef, String> parameterName = new Attribute<>('parameterName', ParameterDef.class, String.class, false, true)
    public static final Attribute<ParameterDef, String> description = new Attribute<>('description', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, String> hasValue = new Attribute<>('hasValue', ParameterDef.class, String.class, false, false)
    public static final Attribute<ParameterDef, BigDecimal> minValue = new Attribute<>('minValue', ParameterDef.class, BigDecimal.class, false, false)
    public static final Attribute<ParameterDef, BigDecimal> maxValue = new Attribute<>('maxValue', ParameterDef.class, BigDecimal.class, false, false)
    public static final Attribute<ParameterDef, BigDecimal> defaultValue = new Attribute<>('defaultValue', ParameterDef.class, BigDecimal.class, false, false)
}
