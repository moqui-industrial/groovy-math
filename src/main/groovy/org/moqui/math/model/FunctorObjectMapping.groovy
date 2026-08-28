/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.FunctorObjectMapping
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
@EqualsAndHashCode(includes = ['functorId', 'sourceObjectId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class FunctorObjectMapping implements Serializable {
    private static final long serialVersionUID = 1L

    String functorId
    String sourceObjectId
    String targetObjectId

    // --- Relationships (In-Memory Navigation) ---
    Functor functor
    CategoryObject sourceObject
    CategoryObject targetObject

    FunctorObjectMapping() { }

    FunctorObjectMapping(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('functorId')) this.functorId = args.get('functorId') as String
            if (args.containsKey('sourceObjectId')) this.sourceObjectId = args.get('sourceObjectId') as String
            if (args.containsKey('targetObjectId')) this.targetObjectId = args.get('targetObjectId') as String
            if (args.containsKey('functor')) this.functor = args.get('functor') as Functor
            if (args.containsKey('sourceObject')) this.sourceObject = args.get('sourceObject') as CategoryObject
            if (args.containsKey('targetObject')) this.targetObject = args.get('targetObject') as CategoryObject
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
    FunctorObjectMapping configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = FunctorObjectMapping) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Functor functor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Functor) Closure<?> action) {
        if (this.functor == null) this.functor = new Functor()
        this.functor.configure(action)
        this.functor
    }

    CategoryObject sourceObject(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        if (this.sourceObject == null) this.sourceObject = new CategoryObject()
        this.sourceObject.configure(action)
        this.sourceObject
    }

    CategoryObject targetObject(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        if (this.targetObject == null) this.targetObject = new CategoryObject()
        this.targetObject.configure(action)
        this.targetObject
    }
}
