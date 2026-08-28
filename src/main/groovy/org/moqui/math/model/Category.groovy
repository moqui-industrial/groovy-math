/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.Category
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
@EqualsAndHashCode(includes = ['categoryId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Category implements Serializable {
    private static final long serialVersionUID = 1L

    String categoryId
    String parentCategoryId
    String categoryTypeEnumId
    String categoryName
    String description
    java.sql.Timestamp fromDate
    java.sql.Timestamp thruDate

    // --- Relationships (In-Memory Navigation) ---
    Category parent
    Object categoryType
    List<CategoryObject> objects = []
    List<Morphism> morphisms = []

    Category() { }

    Category(String categoryId) {
        this.categoryId = Objects.requireNonNull(categoryId, "Category.categoryId cannot be null")
    }

    Category(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId') as String
            if (args.containsKey('parentCategoryId')) this.parentCategoryId = args.get('parentCategoryId') as String
            if (args.containsKey('categoryTypeEnumId')) this.categoryTypeEnumId = args.get('categoryTypeEnumId') as String
            if (args.containsKey('categoryName')) this.categoryName = args.get('categoryName') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('fromDate')) this.fromDate = args.get('fromDate') as java.sql.Timestamp
            if (args.containsKey('thruDate')) this.thruDate = args.get('thruDate') as java.sql.Timestamp
            if (args.containsKey('parent')) this.parent = args.get('parent') as Category
            if (args.containsKey('categoryType')) this.categoryType = args.get('categoryType') as Object
            if (args.containsKey('objects')) this.objects = args.get('objects') as List<CategoryObject>
            if (args.containsKey('morphisms')) this.morphisms = args.get('morphisms') as List<Morphism>
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
    Category configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Category) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Category parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Category) Closure<?> action) {
        if (this.parent == null) this.parent = new Category()
        this.parent.configure(action)
        this.parent
    }

    CategoryObject objects(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        CategoryObject item = new CategoryObject()
        item.configure(action)
        if (this.objects == null) this.objects = []
        this.objects.add(item)
        item
    }

    Morphism morphisms(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Morphism) Closure<?> action) {
        Morphism item = new Morphism()
        item.configure(action)
        if (this.morphisms == null) this.morphisms = []
        this.morphisms.add(item)
        item
    }
}
