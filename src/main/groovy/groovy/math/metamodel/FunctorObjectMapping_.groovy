/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.FunctorObjectMapping
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.FunctorObjectMapping

@CompileStatic
class FunctorObjectMapping_ {
    public static final String ENTITY_NAME = 'FunctorObjectMapping'
    public static final String FULL_NAME = 'moqui.math.ct.FunctorObjectMapping'

    public static final Attribute<FunctorObjectMapping, String> functorId = new Attribute<>('functorId', FunctorObjectMapping.class, String.class, true, true)
    public static final Attribute<FunctorObjectMapping, String> sourceObjectId = new Attribute<>('sourceObjectId', FunctorObjectMapping.class, String.class, true, true)
    public static final Attribute<FunctorObjectMapping, String> targetObjectId = new Attribute<>('targetObjectId', FunctorObjectMapping.class, String.class, false, false)
}
