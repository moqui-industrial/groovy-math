/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.FunctorMorphismMapping
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
@EqualsAndHashCode(includes = ['functorId', 'sourceMorphismId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class FunctorMorphismMapping implements Serializable {
    private static final long serialVersionUID = 1L

    String functorId
    String sourceMorphismId
    String targetMorphismId // Required

    // --- Relationships (In-Memory Navigation) ---
    Functor functor
    Morphism sourceMorphism
    Morphism targetMorphism

    FunctorMorphismMapping() { }

    FunctorMorphismMapping(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('functorId')) this.functorId = args.get('functorId') as String
            if (args.containsKey('sourceMorphismId')) this.sourceMorphismId = args.get('sourceMorphismId') as String
            if (args.containsKey('targetMorphismId')) this.targetMorphismId = args.get('targetMorphismId') as String
            if (args.containsKey('functor')) this.functor = args.get('functor') as Functor
            if (args.containsKey('sourceMorphism')) this.sourceMorphism = args.get('sourceMorphism') as Morphism
            if (args.containsKey('targetMorphism')) this.targetMorphism = args.get('targetMorphism') as Morphism
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.targetMorphismId == null) throw new IllegalStateException("Required property missing: FunctorMorphismMapping.targetMorphismId")
    }

    /**
     * Gradle-style closure configurator
     */
    FunctorMorphismMapping configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = FunctorMorphismMapping) Closure<?> action) {
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

    Morphism sourceMorphism(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (this.sourceMorphism == null) this.sourceMorphism = new Morphism()
        this.sourceMorphism.configure(action)
        this.sourceMorphism
    }

    Morphism targetMorphism(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (this.targetMorphism == null) this.targetMorphism = new Morphism()
        this.targetMorphism.configure(action)
        this.targetMorphism
    }
}
