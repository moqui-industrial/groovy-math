/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ParameterLog
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ParameterLog

@CompileStatic
class ParameterLog_ {
    public static final String ENTITY_NAME = 'ParameterLog'
    public static final String FULL_NAME = 'moqui.math.ParameterLog'

    public static final Attribute<ParameterLog, String> parameterLogId = new Attribute<>('parameterLogId', ParameterLog.class, String.class, true, true)
    public static final Attribute<ParameterLog, String> parameterId = new Attribute<>('parameterId', ParameterLog.class, String.class, false, true)
    public static final Attribute<ParameterLog, Long> sequenceNum = new Attribute<>('sequenceNum', ParameterLog.class, Long.class, false, true)
    public static final Attribute<ParameterLog, java.sql.Timestamp> observedDate = new Attribute<>('observedDate', ParameterLog.class, java.sql.Timestamp.class, false, true)
    public static final Attribute<ParameterLog, BigDecimal> numericValue = new Attribute<>('numericValue', ParameterLog.class, BigDecimal.class, false, false)
    public static final Attribute<ParameterLog, String> symbolicValue = new Attribute<>('symbolicValue', ParameterLog.class, String.class, false, false)
    public static final Attribute<ParameterLog, String> parameterEnumId = new Attribute<>('parameterEnumId', ParameterLog.class, String.class, false, false)
}
