/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.CategoryObject
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.CategoryObject

@CompileStatic
class CategoryObject_ {
    public static final String ENTITY_NAME = 'CategoryObject'
    public static final String FULL_NAME = 'moqui.math.ct.CategoryObject'

    public static final Attribute<CategoryObject, String> categoryObjectId = new Attribute<>('categoryObjectId', CategoryObject.class, String.class, true, true)
    public static final Attribute<CategoryObject, String> parentObjectId = new Attribute<>('parentObjectId', CategoryObject.class, String.class, false, false)
    public static final Attribute<CategoryObject, String> categoryId = new Attribute<>('categoryId', CategoryObject.class, String.class, false, true)
    public static final Attribute<CategoryObject, String> objectEntityName = new Attribute<>('objectEntityName', CategoryObject.class, String.class, false, false)
    public static final Attribute<CategoryObject, String> objectPkPrimaryValue = new Attribute<>('objectPkPrimaryValue', CategoryObject.class, String.class, false, false)
    public static final Attribute<CategoryObject, String> objectPkSecondaryValue = new Attribute<>('objectPkSecondaryValue', CategoryObject.class, String.class, false, false)
    public static final Attribute<CategoryObject, String> objectPkRestCombinedValue = new Attribute<>('objectPkRestCombinedValue', CategoryObject.class, String.class, false, false)
    public static final Attribute<CategoryObject, String> objectTypeEnumId = new Attribute<>('objectTypeEnumId', CategoryObject.class, String.class, false, false)
    public static final Attribute<CategoryObject, String> objectName = new Attribute<>('objectName', CategoryObject.class, String.class, false, false)
    public static final Attribute<CategoryObject, String> objectSymbol = new Attribute<>('objectSymbol', CategoryObject.class, String.class, false, false)
    public static final Attribute<CategoryObject, String> description = new Attribute<>('description', CategoryObject.class, String.class, false, false)
}
