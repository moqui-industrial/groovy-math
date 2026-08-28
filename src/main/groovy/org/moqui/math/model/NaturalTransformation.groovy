/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.NaturalTransformation
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
@EqualsAndHashCode(includes = ['naturalTransformationId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class NaturalTransformation implements Serializable {
    private static final long serialVersionUID = 1L

    String naturalTransformationId
    String parentTransformationId
    String naturalTransformationTypeEnumId
    String sourceFunctorId
    String targetFunctorId
    String description

    // --- Relationships (In-Memory Navigation) ---
    NaturalTransformation parent
    Object type
    Functor sourceFunctor
    Functor targetFunctor

    NaturalTransformation() { }

    NaturalTransformation(String naturalTransformationId) {
        this.naturalTransformationId = Objects.requireNonNull(naturalTransformationId, "NaturalTransformation.naturalTransformationId cannot be null")
    }

    NaturalTransformation(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('naturalTransformationId')) this.naturalTransformationId = args.get('naturalTransformationId') as String
            if (args.containsKey('parentTransformationId')) this.parentTransformationId = args.get('parentTransformationId') as String
            if (args.containsKey('naturalTransformationTypeEnumId')) this.naturalTransformationTypeEnumId = args.get('naturalTransformationTypeEnumId') as String
            if (args.containsKey('sourceFunctorId')) this.sourceFunctorId = args.get('sourceFunctorId') as String
            if (args.containsKey('targetFunctorId')) this.targetFunctorId = args.get('targetFunctorId') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('parent')) this.parent = args.get('parent') as NaturalTransformation
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('sourceFunctor')) this.sourceFunctor = args.get('sourceFunctor') as Functor
            if (args.containsKey('targetFunctor')) this.targetFunctor = args.get('targetFunctor') as Functor
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
    NaturalTransformation configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = NaturalTransformation) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    NaturalTransformation parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = NaturalTransformation) Closure<?> action) {
        if (this.parent == null) this.parent = new NaturalTransformation()
        this.parent.configure(action)
        this.parent
    }

    Functor sourceFunctor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Functor) Closure<?> action) {
        if (this.sourceFunctor == null) this.sourceFunctor = new Functor()
        this.sourceFunctor.configure(action)
        this.sourceFunctor
    }

    Functor targetFunctor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Functor) Closure<?> action) {
        if (this.targetFunctor == null) this.targetFunctor = new Functor()
        this.targetFunctor.configure(action)
        this.targetFunctor
    }
}
