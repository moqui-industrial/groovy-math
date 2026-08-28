/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParameterLog
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['parameterLogId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ParameterLog implements Serializable {
    private static final long serialVersionUID = 1L

    String parameterLogId
    String parameterId // Required
    Long sequenceNum // Required
    java.sql.Timestamp observedDate // Required
    BigDecimal numericValue
    String symbolicValue
    String parameterEnumId

    // --- Relationships (In-Memory Navigation) ---
    Parameter parameter

    ParameterLog() { }

    ParameterLog(String parameterLogId) {
        this.parameterLogId = Objects.requireNonNull(parameterLogId, "ParameterLog.parameterLogId cannot be null")
    }

    ParameterLog(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('parameterLogId')) this.parameterLogId = args.get('parameterLogId') as String
            if (args.containsKey('parameterId')) this.parameterId = args.get('parameterId') as String
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') as Long
            if (args.containsKey('observedDate')) this.observedDate = args.get('observedDate') as java.sql.Timestamp
            if (args.containsKey('numericValue')) this.numericValue = args.get('numericValue') as BigDecimal
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue') as String
            if (args.containsKey('parameterEnumId')) this.parameterEnumId = args.get('parameterEnumId') as String
            if (args.containsKey('parameter')) this.parameter = args.get('parameter') as Parameter
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.parameterId == null) throw new IllegalStateException("Required property missing: ParameterLog.parameterId")
        if (this.sequenceNum == null) throw new IllegalStateException("Required property missing: ParameterLog.sequenceNum")
        if (this.observedDate == null) throw new IllegalStateException("Required property missing: ParameterLog.observedDate")
    }

    /**
     * Gradle-style closure configurator
     */
    ParameterLog configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParameterLog) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Parameter parameter(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Parameter) Closure<?> action) {
        if (this.parameter == null) this.parameter = new Parameter()
        this.parameter.configure(action)
        this.parameter
    }
}
