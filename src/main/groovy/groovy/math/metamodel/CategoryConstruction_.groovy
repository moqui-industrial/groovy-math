/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.CategoryConstruction
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.CategoryConstruction

@CompileStatic
class CategoryConstruction_ {
    public static final String ENTITY_NAME = 'CategoryConstruction'
    public static final String FULL_NAME = 'moqui.math.ct.CategoryConstruction'

    public static final Attribute<CategoryConstruction, String> categoryConstructionId = new Attribute<>('categoryConstructionId', CategoryConstruction.class, String.class, true, true)
    public static final Attribute<CategoryConstruction, String> resultCategoryId = new Attribute<>('resultCategoryId', CategoryConstruction.class, String.class, false, true)
    public static final Attribute<CategoryConstruction, String> constructionTypeEnumId = new Attribute<>('constructionTypeEnumId', CategoryConstruction.class, String.class, false, true)
    public static final Attribute<CategoryConstruction, String> baseObjectId = new Attribute<>('baseObjectId', CategoryConstruction.class, String.class, false, false)
    public static final Attribute<CategoryConstruction, String> description = new Attribute<>('description', CategoryConstruction.class, String.class, false, false)
}
