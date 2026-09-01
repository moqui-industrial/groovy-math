/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.NaturalTransformation
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.NaturalTransformation

@CompileStatic
class NaturalTransformation_ {
    public static final String ENTITY_NAME = 'NaturalTransformation'
    public static final String FULL_NAME = 'moqui.math.ct.NaturalTransformation'

    public static final Attribute<NaturalTransformation, String> naturalTransformationId = new Attribute<>('naturalTransformationId', NaturalTransformation.class, String.class, true, true)
    public static final Attribute<NaturalTransformation, String> parentTransformationId = new Attribute<>('parentTransformationId', NaturalTransformation.class, String.class, false, false)
    public static final Attribute<NaturalTransformation, String> naturalTransformationTypeEnumId = new Attribute<>('naturalTransformationTypeEnumId', NaturalTransformation.class, String.class, false, false)
    public static final Attribute<NaturalTransformation, String> sourceFunctorId = new Attribute<>('sourceFunctorId', NaturalTransformation.class, String.class, false, false)
    public static final Attribute<NaturalTransformation, String> targetFunctorId = new Attribute<>('targetFunctorId', NaturalTransformation.class, String.class, false, false)
    public static final Attribute<NaturalTransformation, String> categoryMorphismId = new Attribute<>('categoryMorphismId', NaturalTransformation.class, String.class, false, false)
    public static final Attribute<NaturalTransformation, String> description = new Attribute<>('description', NaturalTransformation.class, String.class, false, false)
}
