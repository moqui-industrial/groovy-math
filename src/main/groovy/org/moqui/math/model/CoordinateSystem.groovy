/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.CoordinateSystem
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
@EqualsAndHashCode(includes = ['coordinateSystemId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class CoordinateSystem implements Serializable {
    private static final long serialVersionUID = 1L

    String coordinateSystemId
    String parentSystemId
    String transformationToParentSystemId
    String vectorSpaceEnumId // Required
    String coordinateSystemTypeEnumId
    String purposeEnumId
    String name // Required
    String symbol
    String description
    String originVectorId

    // --- Relationships (In-Memory Navigation) ---
    CoordinateSystem parent
    CoordinateSystemTransformation transformationToParent
    Object vectorSpace
    Object type
    Object purpose

    CoordinateSystem() { }

    CoordinateSystem(String coordinateSystemId) {
        this.coordinateSystemId = Objects.requireNonNull(coordinateSystemId, "CoordinateSystem.coordinateSystemId cannot be null")
    }

    CoordinateSystem(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId') as String
            if (args.containsKey('parentSystemId')) this.parentSystemId = args.get('parentSystemId') as String
            if (args.containsKey('transformationToParentSystemId')) this.transformationToParentSystemId = args.get('transformationToParentSystemId') as String
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId') as String
            if (args.containsKey('coordinateSystemTypeEnumId')) this.coordinateSystemTypeEnumId = args.get('coordinateSystemTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('name')) this.name = args.get('name') as String
            if (args.containsKey('symbol')) this.symbol = args.get('symbol') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('originVectorId')) this.originVectorId = args.get('originVectorId') as String
            if (args.containsKey('parent')) this.parent = args.get('parent') as CoordinateSystem
            if (args.containsKey('transformationToParent')) this.transformationToParent = args.get('transformationToParent') as CoordinateSystemTransformation
            if (args.containsKey('vectorSpace')) this.vectorSpace = args.get('vectorSpace') as Object
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.vectorSpaceEnumId == null) throw new IllegalStateException("Required property missing: CoordinateSystem.vectorSpaceEnumId")
        if (this.name == null) throw new IllegalStateException("Required property missing: CoordinateSystem.name")
    }

    /**
     * Gradle-style closure configurator
     */
    CoordinateSystem configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    CoordinateSystem parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (this.parent == null) this.parent = new CoordinateSystem()
        this.parent.configure(action)
        this.parent
    }

    CoordinateSystemTransformation transformationToParent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystemTransformation) Closure<?> action) {
        if (this.transformationToParent == null) this.transformationToParent = new CoordinateSystemTransformation()
        this.transformationToParent.configure(action)
        this.transformationToParent
    }
}
