/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.Morphism
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
@EqualsAndHashCode(includes = ['morphismId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Morphism implements Serializable {
    private static final long serialVersionUID = 1L

    String morphismId
    String parentMorphismId
    String categoryId // Required
    String morphismTypeEnumId
    String morphismPurposeEnumId
    String sourceObjectId // Required
    String targetObjectId // Required
    String morphismName // Required
    String morphismSymbol
    String description
    String transformationId
    String serviceName

    // --- Relationships (In-Memory Navigation) ---
    Morphism parent
    Category category
    Object type
    Object purpose
    CategoryObject sourceObj
    CategoryObject targetObj
    Transformation transformation
    List<Parameter> parameters = []

    Morphism() { }

    Morphism(String morphismId) {
        this.morphismId = Objects.requireNonNull(morphismId, "Morphism.morphismId cannot be null")
    }

    Morphism(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('morphismId')) this.morphismId = args.get('morphismId') as String
            if (args.containsKey('parentMorphismId')) this.parentMorphismId = args.get('parentMorphismId') as String
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId') as String
            if (args.containsKey('morphismTypeEnumId')) this.morphismTypeEnumId = args.get('morphismTypeEnumId') as String
            if (args.containsKey('morphismPurposeEnumId')) this.morphismPurposeEnumId = args.get('morphismPurposeEnumId') as String
            if (args.containsKey('sourceObjectId')) this.sourceObjectId = args.get('sourceObjectId') as String
            if (args.containsKey('targetObjectId')) this.targetObjectId = args.get('targetObjectId') as String
            if (args.containsKey('morphismName')) this.morphismName = args.get('morphismName') as String
            if (args.containsKey('morphismSymbol')) this.morphismSymbol = args.get('morphismSymbol') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('serviceName')) this.serviceName = args.get('serviceName') as String
            if (args.containsKey('parent')) this.parent = args.get('parent') as Morphism
            if (args.containsKey('category')) this.category = args.get('category') as Category
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('sourceObj')) this.sourceObj = args.get('sourceObj') as CategoryObject
            if (args.containsKey('targetObj')) this.targetObj = args.get('targetObj') as CategoryObject
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
            if (args.containsKey('parameters')) this.parameters = args.get('parameters') as List<Parameter>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.categoryId == null) throw new IllegalStateException("Required property missing: Morphism.categoryId")
        if (this.sourceObjectId == null) throw new IllegalStateException("Required property missing: Morphism.sourceObjectId")
        if (this.targetObjectId == null) throw new IllegalStateException("Required property missing: Morphism.targetObjectId")
        if (this.morphismName == null) throw new IllegalStateException("Required property missing: Morphism.morphismName")
    }

    /**
     * Gradle-style closure configurator
     */
    Morphism configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Morphism parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        if (this.parent == null) this.parent = new Morphism()
        this.parent.configure(action)
        this.parent
    }

    Category category(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Category) Closure<?> action) {
        if (this.category == null) this.category = new Category()
        this.category.configure(action)
        this.category
    }

    CategoryObject sourceObj(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        if (this.sourceObj == null) this.sourceObj = new CategoryObject()
        this.sourceObj.configure(action)
        this.sourceObj
    }

    CategoryObject targetObj(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        if (this.targetObj == null) this.targetObj = new CategoryObject()
        this.targetObj.configure(action)
        this.targetObj
    }

    Transformation transformation(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (this.transformation == null) this.transformation = new Transformation()
        this.transformation.configure(action)
        this.transformation
    }

    Parameter parameters(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Parameter) Closure<?> action) {
        Parameter item = new Parameter()
        item.configure(action)
        if (this.parameters == null) this.parameters = []
        this.parameters.add(item)
        item
    }
}
