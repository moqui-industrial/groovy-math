/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.BlockMatrixExtraction
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
@EqualsAndHashCode(includes = ['transformationId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class BlockMatrixExtraction implements Serializable {
    private static final long serialVersionUID = 1L

    String transformationId
    String blockLabel
    Long startRowBlock
    Long endRowBlock
    Long startColBlock
    Long endColBlock

    // --- Relationships (In-Memory Navigation) ---
    Transformation transformation

    BlockMatrixExtraction() { }

    BlockMatrixExtraction(String transformationId) {
        this.transformationId = Objects.requireNonNull(transformationId, "BlockMatrixExtraction.transformationId cannot be null")
    }

    BlockMatrixExtraction(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('blockLabel')) this.blockLabel = args.get('blockLabel') as String
            if (args.containsKey('startRowBlock')) this.startRowBlock = args.get('startRowBlock') as Long
            if (args.containsKey('endRowBlock')) this.endRowBlock = args.get('endRowBlock') as Long
            if (args.containsKey('startColBlock')) this.startColBlock = args.get('startColBlock') as Long
            if (args.containsKey('endColBlock')) this.endColBlock = args.get('endColBlock') as Long
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
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
    BlockMatrixExtraction configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = BlockMatrixExtraction) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Transformation transformation(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (this.transformation == null) this.transformation = new Transformation()
        this.transformation.configure(action)
        this.transformation
    }
}
