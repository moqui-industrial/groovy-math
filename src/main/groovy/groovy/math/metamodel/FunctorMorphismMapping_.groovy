/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.FunctorMorphismMapping
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.FunctorMorphismMapping

@CompileStatic
class FunctorMorphismMapping_ {
    public static final String ENTITY_NAME = 'FunctorMorphismMapping'
    public static final String FULL_NAME = 'moqui.math.ct.FunctorMorphismMapping'

    public static final Attribute<FunctorMorphismMapping, String> functorId = new Attribute<>('functorId', FunctorMorphismMapping.class, String.class, true, true)
    public static final Attribute<FunctorMorphismMapping, String> sourceMorphismId = new Attribute<>('sourceMorphismId', FunctorMorphismMapping.class, String.class, true, true)
    public static final Attribute<FunctorMorphismMapping, String> targetMorphismId = new Attribute<>('targetMorphismId', FunctorMorphismMapping.class, String.class, false, true)
}
