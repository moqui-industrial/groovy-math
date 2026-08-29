/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.NaturalTransformationComponent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.NaturalTransformationComponent

@CompileStatic
class NaturalTransformationComponent_ {
    public static final String ENTITY_NAME = 'NaturalTransformationComponent'
    public static final String FULL_NAME = 'moqui.math.ct.NaturalTransformationComponent'

    public static final Attribute<NaturalTransformationComponent, String> natTransfId = new Attribute<>('natTransfId', NaturalTransformationComponent.class, String.class, true, true)
    public static final Attribute<NaturalTransformationComponent, String> categoryObjectId = new Attribute<>('categoryObjectId', NaturalTransformationComponent.class, String.class, true, true)
    public static final Attribute<NaturalTransformationComponent, String> componentMorphismId = new Attribute<>('componentMorphismId', NaturalTransformationComponent.class, String.class, false, true)
}
