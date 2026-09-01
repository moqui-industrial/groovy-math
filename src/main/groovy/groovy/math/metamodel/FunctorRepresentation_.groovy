/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.FunctorRepresentation
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.FunctorRepresentation

@CompileStatic
class FunctorRepresentation_ {
    public static final String ENTITY_NAME = 'FunctorRepresentation'
    public static final String FULL_NAME = 'moqui.math.ct.FunctorRepresentation'

    public static final Attribute<FunctorRepresentation, String> functorRepresentationId = new Attribute<>('functorRepresentationId', FunctorRepresentation.class, String.class, true, true)
    public static final Attribute<FunctorRepresentation, String> functorId = new Attribute<>('functorId', FunctorRepresentation.class, String.class, false, true)
    public static final Attribute<FunctorRepresentation, String> representingObjectId = new Attribute<>('representingObjectId', FunctorRepresentation.class, String.class, false, true)
    public static final Attribute<FunctorRepresentation, String> homFunctorId = new Attribute<>('homFunctorId', FunctorRepresentation.class, String.class, false, true)
    public static final Attribute<FunctorRepresentation, String> naturalIsomorphismId = new Attribute<>('naturalIsomorphismId', FunctorRepresentation.class, String.class, false, true)
}
