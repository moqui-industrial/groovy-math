/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.Category
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Category

@CompileStatic
class Category_ {
    public static final String ENTITY_NAME = 'Category'
    public static final String FULL_NAME = 'moqui.math.ct.Category'

    public static final Attribute<Category, String> categoryId = new Attribute<>('categoryId', Category.class, String.class, true, true)
    public static final Attribute<Category, String> parentCategoryId = new Attribute<>('parentCategoryId', Category.class, String.class, false, false)
    public static final Attribute<Category, String> categoryTypeEnumId = new Attribute<>('categoryTypeEnumId', Category.class, String.class, false, false)
    public static final Attribute<Category, String> categoryName = new Attribute<>('categoryName', Category.class, String.class, false, false)
    public static final Attribute<Category, String> description = new Attribute<>('description', Category.class, String.class, false, false)
    public static final Attribute<Category, java.sql.Timestamp> fromDate = new Attribute<>('fromDate', Category.class, java.sql.Timestamp.class, false, false)
    public static final Attribute<Category, java.sql.Timestamp> thruDate = new Attribute<>('thruDate', Category.class, java.sql.Timestamp.class, false, false)
}
