/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelDefIdentification
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['mathModelDefId', 'externalSystemEnumId', 'fromDate'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MathModelDefIdentification implements Serializable {
    private static final long serialVersionUID = 1L

    String mathModelDefId
    String externalSystemEnumId
    java.sql.Timestamp fromDate
    java.sql.Timestamp thruDate
    String externalId // Required
    String externalVersion
    String externalUri
    String isPrimary
    String description

    // --- Relationships (In-Memory Navigation) ---
    MathModelDef modelDef
    Object externalSystem

    MathModelDefIdentification() { }

    MathModelDefIdentification(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('mathModelDefId')) this.mathModelDefId = args.get('mathModelDefId') as String
            if (args.containsKey('externalSystemEnumId')) this.externalSystemEnumId = args.get('externalSystemEnumId') as String
            if (args.containsKey('fromDate')) this.fromDate = args.get('fromDate') as java.sql.Timestamp
            if (args.containsKey('thruDate')) this.thruDate = args.get('thruDate') as java.sql.Timestamp
            if (args.containsKey('externalId')) this.externalId = args.get('externalId') as String
            if (args.containsKey('externalVersion')) this.externalVersion = args.get('externalVersion') as String
            if (args.containsKey('externalUri')) this.externalUri = args.get('externalUri') as String
            if (args.containsKey('isPrimary')) this.isPrimary = args.get('isPrimary') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('modelDef')) this.modelDef = args.get('modelDef') as MathModelDef
            if (args.containsKey('externalSystem')) this.externalSystem = args.get('externalSystem') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.externalId == null) throw new IllegalStateException("Required property missing: MathModelDefIdentification.externalId")
    }

    /**
     * Gradle-style closure configurator
     */
    MathModelDefIdentification configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelDefIdentification) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MathModelDef modelDef(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelDef) Closure<?> action) {
        if (this.modelDef == null) this.modelDef = new MathModelDef()
        this.modelDef.configure(action)
        this.modelDef
    }
}
