/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParametricPathContent
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
@EqualsAndHashCode(includes = ['parametricPathContentId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ParametricPathContent implements Serializable {
    private static final long serialVersionUID = 1L

    String parametricPathContentId
    String approximatedFunctionId // Required
    String contentLocation
    String contentTypeEnumId
    java.sql.Timestamp contentDate
    String description
    String userId

    // --- Relationships (In-Memory Navigation) ---
    ParametricPath parametricPath
    Object type
    Object userAccount

    ParametricPathContent() { }

    ParametricPathContent(String parametricPathContentId) {
        this.parametricPathContentId = Objects.requireNonNull(parametricPathContentId, "ParametricPathContent.parametricPathContentId cannot be null")
    }

    ParametricPathContent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('parametricPathContentId')) this.parametricPathContentId = args.get('parametricPathContentId') as String
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation') as String
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId') as String
            if (args.containsKey('contentDate')) this.contentDate = args.get('contentDate') as java.sql.Timestamp
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('userId')) this.userId = args.get('userId') as String
            if (args.containsKey('parametricPath')) this.parametricPath = args.get('parametricPath') as ParametricPath
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('userAccount')) this.userAccount = args.get('userAccount') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.approximatedFunctionId == null) throw new IllegalStateException("Required property missing: ParametricPathContent.approximatedFunctionId")
    }

    /**
     * Gradle-style closure configurator
     */
    ParametricPathContent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPathContent) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ParametricPath parametricPath(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParametricPath) Closure<?> action) {
        if (this.parametricPath == null) this.parametricPath = new ParametricPath()
        this.parametricPath.configure(action)
        this.parametricPath
    }
}
