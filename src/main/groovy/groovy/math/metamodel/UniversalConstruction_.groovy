/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.UniversalConstruction
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.UniversalConstruction

@CompileStatic
class UniversalConstruction_ {
    public static final String ENTITY_NAME = 'UniversalConstruction'
    public static final String FULL_NAME = 'moqui.math.ct.UniversalConstruction'

    public static final Attribute<UniversalConstruction, String> universalConstructionId = new Attribute<>('universalConstructionId', UniversalConstruction.class, String.class, true, true)
    public static final Attribute<UniversalConstruction, String> categoryId = new Attribute<>('categoryId', UniversalConstruction.class, String.class, false, true)
    public static final Attribute<UniversalConstruction, String> constructionTypeEnumId = new Attribute<>('constructionTypeEnumId', UniversalConstruction.class, String.class, false, true)
    public static final Attribute<UniversalConstruction, String> diagramFunctorId = new Attribute<>('diagramFunctorId', UniversalConstruction.class, String.class, false, false)
    public static final Attribute<UniversalConstruction, String> universalObjectId = new Attribute<>('universalObjectId', UniversalConstruction.class, String.class, false, true)
    public static final Attribute<UniversalConstruction, String> universalNaturalTransformationId = new Attribute<>('universalNaturalTransformationId', UniversalConstruction.class, String.class, false, false)
    public static final Attribute<UniversalConstruction, String> description = new Attribute<>('description', UniversalConstruction.class, String.class, false, false)
}
