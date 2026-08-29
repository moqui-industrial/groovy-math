/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MathModelDef
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MathModelDef

@CompileStatic
class MathModelDef_ {
    public static final String ENTITY_NAME = 'MathModelDef'
    public static final String FULL_NAME = 'moqui.math.MathModelDef'

    public static final Attribute<MathModelDef, String> mathModelDefId = new Attribute<>('mathModelDefId', MathModelDef.class, String.class, true, true)
    public static final Attribute<MathModelDef, String> parentModelDefId = new Attribute<>('parentModelDefId', MathModelDef.class, String.class, false, false)
    public static final Attribute<MathModelDef, String> modelTypeEnumId = new Attribute<>('modelTypeEnumId', MathModelDef.class, String.class, false, false)
    public static final Attribute<MathModelDef, String> usageContextEnumId = new Attribute<>('usageContextEnumId', MathModelDef.class, String.class, false, false)
    public static final Attribute<MathModelDef, String> domainEnumId = new Attribute<>('domainEnumId', MathModelDef.class, String.class, false, false)
    public static final Attribute<MathModelDef, String> serviceName = new Attribute<>('serviceName', MathModelDef.class, String.class, false, false)
    public static final Attribute<MathModelDef, String> modelName = new Attribute<>('modelName', MathModelDef.class, String.class, false, false)
    public static final Attribute<MathModelDef, String> description = new Attribute<>('description', MathModelDef.class, String.class, false, false)
    public static final Attribute<MathModelDef, Long> versionNumber = new Attribute<>('versionNumber', MathModelDef.class, Long.class, false, false)
    public static final Attribute<MathModelDef, String> releaseStatusId = new Attribute<>('releaseStatusId', MathModelDef.class, String.class, false, false)
    public static final Attribute<MathModelDef, java.sql.Timestamp> fromDate = new Attribute<>('fromDate', MathModelDef.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<MathModelDef, java.sql.Timestamp> thruDate = new Attribute<>('thruDate', MathModelDef.class, java.sql.Timestamp.class, false, false)
}
