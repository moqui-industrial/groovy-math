/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.Adjunction
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Adjunction

@CompileStatic
class Adjunction_ {
    public static final String ENTITY_NAME = 'Adjunction'
    public static final String FULL_NAME = 'moqui.math.ct.Adjunction'

    public static final Attribute<Adjunction, String> adjunctionId = new Attribute<>('adjunctionId', Adjunction.class, String.class, true, true)
    public static final Attribute<Adjunction, String> leftFunctorId = new Attribute<>('leftFunctorId', Adjunction.class, String.class, false, true)
    public static final Attribute<Adjunction, String> rightFunctorId = new Attribute<>('rightFunctorId', Adjunction.class, String.class, false, true)
    public static final Attribute<Adjunction, String> unitNaturalTransformationId = new Attribute<>('unitNaturalTransformationId', Adjunction.class, String.class, false, true)
    public static final Attribute<Adjunction, String> counitNaturalTransformationId = new Attribute<>('counitNaturalTransformationId', Adjunction.class, String.class, false, true)
    public static final Attribute<Adjunction, String> description = new Attribute<>('description', Adjunction.class, String.class, false, false)
}
