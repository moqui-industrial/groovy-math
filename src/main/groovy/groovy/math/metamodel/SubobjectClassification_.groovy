/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.SubobjectClassification
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.SubobjectClassification

@CompileStatic
class SubobjectClassification_ {
    public static final String ENTITY_NAME = 'SubobjectClassification'
    public static final String FULL_NAME = 'moqui.math.ct.SubobjectClassification'

    public static final Attribute<SubobjectClassification, String> subobjectMorphismId = new Attribute<>('subobjectMorphismId', SubobjectClassification.class, String.class, true, true)
    public static final Attribute<SubobjectClassification, String> classifierCategoryId = new Attribute<>('classifierCategoryId', SubobjectClassification.class, String.class, false, true)
    public static final Attribute<SubobjectClassification, String> characteristicMorphismId = new Attribute<>('characteristicMorphismId', SubobjectClassification.class, String.class, false, true)
    public static final Attribute<SubobjectClassification, String> pullbackConstructionId = new Attribute<>('pullbackConstructionId', SubobjectClassification.class, String.class, false, true)
}
