/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MathModelDefIdentification
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MathModelDefIdentification

@CompileStatic
class MathModelDefIdentification_ {
    public static final String ENTITY_NAME = 'MathModelDefIdentification'
    public static final String FULL_NAME = 'moqui.math.MathModelDefIdentification'

    public static final Attribute<MathModelDefIdentification, String> mathModelDefId = new Attribute<>('mathModelDefId', MathModelDefIdentification.class, String.class, true, true)
    public static final Attribute<MathModelDefIdentification, String> externalSystemEnumId = new Attribute<>('externalSystemEnumId', MathModelDefIdentification.class, String.class, true, true)
    public static final Attribute<MathModelDefIdentification, java.sql.Timestamp> fromDate = new Attribute<>('fromDate', MathModelDefIdentification.class, java.sql.Timestamp.class, true, true)
    public static final Attribute<MathModelDefIdentification, java.sql.Timestamp> thruDate = new Attribute<>('thruDate', MathModelDefIdentification.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<MathModelDefIdentification, String> externalId = new Attribute<>('externalId', MathModelDefIdentification.class, String.class, false, true)
    public static final Attribute<MathModelDefIdentification, String> externalVersion = new Attribute<>('externalVersion', MathModelDefIdentification.class, String.class, false, false)
    public static final Attribute<MathModelDefIdentification, String> externalUri = new Attribute<>('externalUri', MathModelDefIdentification.class, String.class, false, false)
    public static final Attribute<MathModelDefIdentification, String> isPrimary = new Attribute<>('isPrimary', MathModelDefIdentification.class, String.class, false, false)
    public static final Attribute<MathModelDefIdentification, String> description = new Attribute<>('description', MathModelDefIdentification.class, String.class, false, false)
}
