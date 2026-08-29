/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.Morphism
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Morphism

@CompileStatic
class Morphism_ {
    public static final String ENTITY_NAME = 'Morphism'
    public static final String FULL_NAME = 'moqui.math.ct.Morphism'

    public static final Attribute<Morphism, String> morphismId = new Attribute<>('morphismId', Morphism.class, String.class, true, true)
    public static final Attribute<Morphism, String> parentMorphismId = new Attribute<>('parentMorphismId', Morphism.class, String.class, false, false)
    public static final Attribute<Morphism, String> categoryId = new Attribute<>('categoryId', Morphism.class, String.class, false, true)
    public static final Attribute<Morphism, String> morphismTypeEnumId = new Attribute<>('morphismTypeEnumId', Morphism.class, String.class, false, false)
    public static final Attribute<Morphism, String> sourceObjectId = new Attribute<>('sourceObjectId', Morphism.class, String.class, false, true)
    public static final Attribute<Morphism, String> targetObjectId = new Attribute<>('targetObjectId', Morphism.class, String.class, false, true)
    public static final Attribute<Morphism, String> morphismName = new Attribute<>('morphismName', Morphism.class, String.class, false, true)
    public static final Attribute<Morphism, String> morphismSymbol = new Attribute<>('morphismSymbol', Morphism.class, String.class, false, false)
    public static final Attribute<Morphism, String> description = new Attribute<>('description', Morphism.class, String.class, false, false)
    public static final Attribute<Morphism, String> transformationId = new Attribute<>('transformationId', Morphism.class, String.class, false, false)
    public static final Attribute<Morphism, String> serviceName = new Attribute<>('serviceName', Morphism.class, String.class, false, false)
}
