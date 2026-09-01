/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.Comonad
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Comonad

@CompileStatic
class Comonad_ {
    public static final String ENTITY_NAME = 'Comonad'
    public static final String FULL_NAME = 'moqui.math.ct.Comonad'

    public static final Attribute<Comonad, String> comonadId = new Attribute<>('comonadId', Comonad.class, String.class, true, true)
    public static final Attribute<Comonad, String> categoryId = new Attribute<>('categoryId', Comonad.class, String.class, false, true)
    public static final Attribute<Comonad, String> endofunctorId = new Attribute<>('endofunctorId', Comonad.class, String.class, false, true)
    public static final Attribute<Comonad, String> counitNaturalTransformationId = new Attribute<>('counitNaturalTransformationId', Comonad.class, String.class, false, true)
    public static final Attribute<Comonad, String> comultiplicationNaturalTransformationId = new Attribute<>('comultiplicationNaturalTransformationId', Comonad.class, String.class, false, true)
    public static final Attribute<Comonad, String> inducingAdjunctionId = new Attribute<>('inducingAdjunctionId', Comonad.class, String.class, false, false)
}
