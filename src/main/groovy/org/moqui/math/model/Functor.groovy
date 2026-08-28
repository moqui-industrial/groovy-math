/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.Functor
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
@EqualsAndHashCode(includes = ['functorId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Functor implements Serializable {
    private static final long serialVersionUID = 1L

    String functorId
    String parentFunctorId
    String functorTypeEnumId
    String functorName // Required
    String functorSymbol
    String description
    String sourceCategoryId
    String targetCategoryId

    // --- Relationships (In-Memory Navigation) ---
    Functor parent
    Object type
    Category sourceCategory
    Category targetCategory
    List<FunctorObjectMapping> objMap = []
    List<FunctorMorphismMapping> morphismMap = []

    Functor() { }

    Functor(String functorId) {
        this.functorId = Objects.requireNonNull(functorId, "Functor.functorId cannot be null")
    }

    Functor(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('functorId')) this.functorId = args.get('functorId') as String
            if (args.containsKey('parentFunctorId')) this.parentFunctorId = args.get('parentFunctorId') as String
            if (args.containsKey('functorTypeEnumId')) this.functorTypeEnumId = args.get('functorTypeEnumId') as String
            if (args.containsKey('functorName')) this.functorName = args.get('functorName') as String
            if (args.containsKey('functorSymbol')) this.functorSymbol = args.get('functorSymbol') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('sourceCategoryId')) this.sourceCategoryId = args.get('sourceCategoryId') as String
            if (args.containsKey('targetCategoryId')) this.targetCategoryId = args.get('targetCategoryId') as String
            if (args.containsKey('parent')) this.parent = args.get('parent') as Functor
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('sourceCategory')) this.sourceCategory = args.get('sourceCategory') as Category
            if (args.containsKey('targetCategory')) this.targetCategory = args.get('targetCategory') as Category
            if (args.containsKey('objMap')) this.objMap = args.get('objMap') as List<FunctorObjectMapping>
            if (args.containsKey('morphismMap')) this.morphismMap = args.get('morphismMap') as List<FunctorMorphismMapping>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.functorName == null) throw new IllegalStateException("Required property missing: Functor.functorName")
    }

    /**
     * Gradle-style closure configurator
     */
    Functor configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Functor) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Functor parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Functor) Closure<?> action) {
        if (this.parent == null) this.parent = new Functor()
        this.parent.configure(action)
        this.parent
    }

    Category sourceCategory(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Category) Closure<?> action) {
        if (this.sourceCategory == null) this.sourceCategory = new Category()
        this.sourceCategory.configure(action)
        this.sourceCategory
    }

    Category targetCategory(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Category) Closure<?> action) {
        if (this.targetCategory == null) this.targetCategory = new Category()
        this.targetCategory.configure(action)
        this.targetCategory
    }

    FunctorObjectMapping objMap(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = FunctorObjectMapping) Closure<?> action) {
        FunctorObjectMapping item = new FunctorObjectMapping()
        item.configure(action)
        if (this.objMap == null) this.objMap = []
        this.objMap.add(item)
        item
    }

    FunctorMorphismMapping morphismMap(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = FunctorMorphismMapping) Closure<?> action) {
        FunctorMorphismMapping item = new FunctorMorphismMapping()
        item.configure(action)
        if (this.morphismMap == null) this.morphismMap = []
        this.morphismMap.add(item)
        item
    }
}
