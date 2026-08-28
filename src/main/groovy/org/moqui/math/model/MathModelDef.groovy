/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelDef
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
@EqualsAndHashCode(includes = ['mathModelDefId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MathModelDef implements Serializable {
    private static final long serialVersionUID = 1L

    String mathModelDefId
    String parentModelDefId
    String modelTypeEnumId
    String usageContextEnumId
    String domainEnumId
    String serviceName
    String modelName
    String description
    Long versionNumber
    String releaseStatusId
    java.sql.Timestamp fromDate
    java.sql.Timestamp thruDate

    // --- Relationships (In-Memory Navigation) ---
    MathModelDef parent
    Object type
    Object usage
    Object domain
    Object status

    MathModelDef() { }

    MathModelDef(String mathModelDefId) {
        this.mathModelDefId = Objects.requireNonNull(mathModelDefId, "MathModelDef.mathModelDefId cannot be null")
    }

    MathModelDef(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('mathModelDefId')) this.mathModelDefId = args.get('mathModelDefId') as String
            if (args.containsKey('parentModelDefId')) this.parentModelDefId = args.get('parentModelDefId') as String
            if (args.containsKey('modelTypeEnumId')) this.modelTypeEnumId = args.get('modelTypeEnumId') as String
            if (args.containsKey('usageContextEnumId')) this.usageContextEnumId = args.get('usageContextEnumId') as String
            if (args.containsKey('domainEnumId')) this.domainEnumId = args.get('domainEnumId') as String
            if (args.containsKey('serviceName')) this.serviceName = args.get('serviceName') as String
            if (args.containsKey('modelName')) this.modelName = args.get('modelName') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('versionNumber')) this.versionNumber = args.get('versionNumber') as Long
            if (args.containsKey('releaseStatusId')) this.releaseStatusId = args.get('releaseStatusId') as String
            if (args.containsKey('fromDate')) this.fromDate = args.get('fromDate') as java.sql.Timestamp
            if (args.containsKey('thruDate')) this.thruDate = args.get('thruDate') as java.sql.Timestamp
            if (args.containsKey('parent')) this.parent = args.get('parent') as MathModelDef
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('usage')) this.usage = args.get('usage') as Object
            if (args.containsKey('domain')) this.domain = args.get('domain') as Object
            if (args.containsKey('status')) this.status = args.get('status') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
    }

    /**
     * Gradle-style closure configurator
     */
    MathModelDef configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelDef) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MathModelDef parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelDef) Closure<?> action) {
        if (this.parent == null) this.parent = new MathModelDef()
        this.parent.configure(action)
        this.parent
    }
}
