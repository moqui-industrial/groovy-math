/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelDefContent
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
@EqualsAndHashCode(includes = ['mathModelContentId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MathModelDefContent implements Serializable {
    private static final long serialVersionUID = 1L

    String mathModelContentId
    String mathModelDefId // Required
    String contentLocation
    String contentTypeEnumId
    String purposeEnumId
    java.sql.Timestamp contentDate
    String description
    String userId

    // --- Relationships (In-Memory Navigation) ---
    MathModelDef model
    Object type
    Object userAccount

    MathModelDefContent() { }

    MathModelDefContent(String mathModelContentId) {
        this.mathModelContentId = Objects.requireNonNull(mathModelContentId, "MathModelDefContent.mathModelContentId cannot be null")
    }

    MathModelDefContent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('mathModelContentId')) this.mathModelContentId = args.get('mathModelContentId') as String
            if (args.containsKey('mathModelDefId')) this.mathModelDefId = args.get('mathModelDefId') as String
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation') as String
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('contentDate')) this.contentDate = args.get('contentDate') as java.sql.Timestamp
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('userId')) this.userId = args.get('userId') as String
            if (args.containsKey('model')) this.model = args.get('model') as MathModelDef
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('userAccount')) this.userAccount = args.get('userAccount') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.mathModelDefId == null) throw new IllegalStateException("Required property missing: MathModelDefContent.mathModelDefId")
    }

    /**
     * Gradle-style closure configurator
     */
    MathModelDefContent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelDefContent) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MathModelDef model(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelDef) Closure<?> action) {
        if (this.model == null) this.model = new MathModelDef()
        this.model.configure(action)
        this.model
    }
}
