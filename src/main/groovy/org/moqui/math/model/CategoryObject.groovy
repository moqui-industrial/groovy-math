/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.CategoryObject
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
@EqualsAndHashCode(includes = ['categoryObjectId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class CategoryObject implements Serializable {
    private static final long serialVersionUID = 1L

    String categoryObjectId
    String parentObjectId
    String categoryId // Required
    String objectEntityName
    String objectPkPrimaryValue
    String objectPkSecondaryValue
    String objectPkRestCombinedValue
    String objectTypeEnumId
    String objectName
    String objectSymbol
    String description

    // --- Relationships (In-Memory Navigation) ---
    CategoryObject parent
    Category category
    Object type

    CategoryObject() { }

    CategoryObject(String categoryObjectId) {
        this.categoryObjectId = Objects.requireNonNull(categoryObjectId, "CategoryObject.categoryObjectId cannot be null")
    }

    CategoryObject(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('categoryObjectId')) this.categoryObjectId = args.get('categoryObjectId') as String
            if (args.containsKey('parentObjectId')) this.parentObjectId = args.get('parentObjectId') as String
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId') as String
            if (args.containsKey('objectEntityName')) this.objectEntityName = args.get('objectEntityName') as String
            if (args.containsKey('objectPkPrimaryValue')) this.objectPkPrimaryValue = args.get('objectPkPrimaryValue') as String
            if (args.containsKey('objectPkSecondaryValue')) this.objectPkSecondaryValue = args.get('objectPkSecondaryValue') as String
            if (args.containsKey('objectPkRestCombinedValue')) this.objectPkRestCombinedValue = args.get('objectPkRestCombinedValue') as String
            if (args.containsKey('objectTypeEnumId')) this.objectTypeEnumId = args.get('objectTypeEnumId') as String
            if (args.containsKey('objectName')) this.objectName = args.get('objectName') as String
            if (args.containsKey('objectSymbol')) this.objectSymbol = args.get('objectSymbol') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('parent')) this.parent = args.get('parent') as CategoryObject
            if (args.containsKey('category')) this.category = args.get('category') as Category
            if (args.containsKey('type')) this.type = args.get('type') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.categoryId == null) throw new IllegalStateException("Required property missing: CategoryObject.categoryId")
    }

    /**
     * Gradle-style closure configurator
     */
    CategoryObject configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    CategoryObject parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        if (this.parent == null) this.parent = new CategoryObject()
        this.parent.configure(action)
        this.parent
    }

    Category category(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Category) Closure<?> action) {
        if (this.category == null) this.category = new Category()
        this.category.configure(action)
        this.category
    }
}
