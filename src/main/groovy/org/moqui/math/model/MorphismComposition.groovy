/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.MorphismComposition
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
@EqualsAndHashCode(includes = ['compositeId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MorphismComposition implements Serializable {
    private static final long serialVersionUID = 1L

    String compositeId
    String categoryId // Required
    String compositionTypeEnumId
    String firstMorphismId
    String secondMorphismId
    String resultMorphismId
    String description

    // --- Relationships (In-Memory Navigation) ---
    Category category
    Object type
    Morphism firstMorphism
    Morphism secondMorphism
    Morphism resultMorphism

    MorphismComposition() { }

    MorphismComposition(String compositeId) {
        this.compositeId = Objects.requireNonNull(compositeId, "MorphismComposition.compositeId cannot be null")
    }

    MorphismComposition(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('compositeId')) this.compositeId = args.get('compositeId') as String
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId') as String
            if (args.containsKey('compositionTypeEnumId')) this.compositionTypeEnumId = args.get('compositionTypeEnumId') as String
            if (args.containsKey('firstMorphismId')) this.firstMorphismId = args.get('firstMorphismId') as String
            if (args.containsKey('secondMorphismId')) this.secondMorphismId = args.get('secondMorphismId') as String
            if (args.containsKey('resultMorphismId')) this.resultMorphismId = args.get('resultMorphismId') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('category')) this.category = args.get('category') as Category
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('firstMorphism')) this.firstMorphism = args.get('firstMorphism') as Morphism
            if (args.containsKey('secondMorphism')) this.secondMorphism = args.get('secondMorphism') as Morphism
            if (args.containsKey('resultMorphism')) this.resultMorphism = args.get('resultMorphism') as Morphism
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.categoryId == null) throw new IllegalStateException("Required property missing: MorphismComposition.categoryId")
    }

    /**
     * Gradle-style closure configurator
     */
    MorphismComposition configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MorphismComposition) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Category category(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Category) Closure<?> action) {
        if (this.category == null) this.category = new Category()
        this.category.configure(action)
        this.category
    }

    Morphism firstMorphism(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (this.firstMorphism == null) this.firstMorphism = new Morphism()
        this.firstMorphism.configure(action)
        this.firstMorphism
    }

    Morphism secondMorphism(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (this.secondMorphism == null) this.secondMorphism = new Morphism()
        this.secondMorphism.configure(action)
        this.secondMorphism
    }

    Morphism resultMorphism(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (this.resultMorphism == null) this.resultMorphism = new Morphism()
        this.resultMorphism.configure(action)
        this.resultMorphism
    }
}
