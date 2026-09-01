/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.Monad
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Monad

@CompileStatic
class Monad_ {
    public static final String ENTITY_NAME = 'Monad'
    public static final String FULL_NAME = 'moqui.math.ct.Monad'

    public static final Attribute<Monad, String> monadId = new Attribute<>('monadId', Monad.class, String.class, true, true)
    public static final Attribute<Monad, String> categoryId = new Attribute<>('categoryId', Monad.class, String.class, false, true)
    public static final Attribute<Monad, String> endofunctorId = new Attribute<>('endofunctorId', Monad.class, String.class, false, true)
    public static final Attribute<Monad, String> unitNaturalTransformationId = new Attribute<>('unitNaturalTransformationId', Monad.class, String.class, false, true)
    public static final Attribute<Monad, String> multiplicationNaturalTransformationId = new Attribute<>('multiplicationNaturalTransformationId', Monad.class, String.class, false, true)
    public static final Attribute<Monad, String> inducingAdjunctionId = new Attribute<>('inducingAdjunctionId', Monad.class, String.class, false, false)
}
