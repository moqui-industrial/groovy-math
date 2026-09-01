/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.EndofunctorAlgebra
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.EndofunctorAlgebra

@CompileStatic
class EndofunctorAlgebra_ {
    public static final String ENTITY_NAME = 'EndofunctorAlgebra'
    public static final String FULL_NAME = 'moqui.math.ct.EndofunctorAlgebra'

    public static final Attribute<EndofunctorAlgebra, String> endofunctorAlgebraId = new Attribute<>('endofunctorAlgebraId', EndofunctorAlgebra.class, String.class, true, true)
    public static final Attribute<EndofunctorAlgebra, String> algebraTypeEnumId = new Attribute<>('algebraTypeEnumId', EndofunctorAlgebra.class, String.class, false, true)
    public static final Attribute<EndofunctorAlgebra, String> endofunctorId = new Attribute<>('endofunctorId', EndofunctorAlgebra.class, String.class, false, true)
    public static final Attribute<EndofunctorAlgebra, String> monadId = new Attribute<>('monadId', EndofunctorAlgebra.class, String.class, false, false)
    public static final Attribute<EndofunctorAlgebra, String> comonadId = new Attribute<>('comonadId', EndofunctorAlgebra.class, String.class, false, false)
    public static final Attribute<EndofunctorAlgebra, String> carrierObjectId = new Attribute<>('carrierObjectId', EndofunctorAlgebra.class, String.class, false, true)
    public static final Attribute<EndofunctorAlgebra, String> structureMorphismId = new Attribute<>('structureMorphismId', EndofunctorAlgebra.class, String.class, false, true)
}
