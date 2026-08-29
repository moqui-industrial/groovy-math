/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.MorphismComposition
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MorphismComposition

@CompileStatic
class MorphismComposition_ {
    public static final String ENTITY_NAME = 'MorphismComposition'
    public static final String FULL_NAME = 'moqui.math.ct.MorphismComposition'

    public static final Attribute<MorphismComposition, String> morphismId = new Attribute<>('morphismId', MorphismComposition.class, String.class, true, true)
    public static final Attribute<MorphismComposition, String> compositionTypeEnumId = new Attribute<>('compositionTypeEnumId', MorphismComposition.class, String.class, false, false)
}
