/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.GraphCategory
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.GraphCategory

@CompileStatic
class GraphCategory_ {
    public static final String ENTITY_NAME = 'GraphCategory'
    public static final String FULL_NAME = 'moqui.math.GraphCategory'

    public static final Attribute<GraphCategory, String> graphCategoryId = new Attribute<>('graphCategoryId', GraphCategory.class, String.class, true, true)
    public static final Attribute<GraphCategory, String> graphId = new Attribute<>('graphId', GraphCategory.class, String.class, false, true)
    public static final Attribute<GraphCategory, String> categoryId = new Attribute<>('categoryId', GraphCategory.class, String.class, false, true)
    public static final Attribute<GraphCategory, String> purposeEnumId = new Attribute<>('purposeEnumId', GraphCategory.class, String.class, false, true)
}
