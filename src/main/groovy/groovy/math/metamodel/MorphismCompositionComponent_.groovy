/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.MorphismCompositionComponent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MorphismCompositionComponent

@CompileStatic
class MorphismCompositionComponent_ {
    public static final String ENTITY_NAME = 'MorphismCompositionComponent'
    public static final String FULL_NAME = 'moqui.math.ct.MorphismCompositionComponent'

    public static final Attribute<MorphismCompositionComponent, String> morphismId = new Attribute<>('morphismId', MorphismCompositionComponent.class, String.class, true, true)
    public static final Attribute<MorphismCompositionComponent, String> componentMorphismId = new Attribute<>('componentMorphismId', MorphismCompositionComponent.class, String.class, true, true)
    public static final Attribute<MorphismCompositionComponent, Long> sequenceNum = new Attribute<>('sequenceNum', MorphismCompositionComponent.class, Long.class, false, true)
    public static final Attribute<MorphismCompositionComponent, String> description = new Attribute<>('description', MorphismCompositionComponent.class, String.class, false, false)
}
