/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorContent
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
@EqualsAndHashCode(includes = ['tensorContentId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class TensorContent implements Serializable {
    private static final long serialVersionUID = 1L

    String tensorContentId
    String tensorId // Required
    String contentLocation
    String contentTypeEnumId
    java.sql.Timestamp contentDate
    String description
    String userId

    // --- Relationships (In-Memory Navigation) ---
    Tensor tensor
    Object type
    Object userAccount

    TensorContent() { }

    TensorContent(String tensorContentId) {
        this.tensorContentId = Objects.requireNonNull(tensorContentId, "TensorContent.tensorContentId cannot be null")
    }

    TensorContent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('tensorContentId')) this.tensorContentId = args.get('tensorContentId') as String
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId') as String
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation') as String
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId') as String
            if (args.containsKey('contentDate')) this.contentDate = args.get('contentDate') as java.sql.Timestamp
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('userId')) this.userId = args.get('userId') as String
            if (args.containsKey('tensor')) this.tensor = args.get('tensor') as Tensor
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('userAccount')) this.userAccount = args.get('userAccount') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.tensorId == null) throw new IllegalStateException("Required property missing: TensorContent.tensorId")
    }

    /**
     * Gradle-style closure configurator
     */
    TensorContent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorContent) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Tensor tensor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.tensor == null) this.tensor = new Tensor()
        this.tensor.configure(action)
        this.tensor
    }
}
