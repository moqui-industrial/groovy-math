/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.NaturalTransformationComposition
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.NaturalTransformationComposition

@CompileStatic
class NaturalTransformationComposition_ {
    public static final String ENTITY_NAME = 'NaturalTransformationComposition'
    public static final String FULL_NAME = 'moqui.math.ct.NaturalTransformationComposition'

    public static final Attribute<NaturalTransformationComposition, String> natTransfCompositionId = new Attribute<>('natTransfCompositionId', NaturalTransformationComposition.class, String.class, true, true)
    public static final Attribute<NaturalTransformationComposition, String> compositionTypeEnumId = new Attribute<>('compositionTypeEnumId', NaturalTransformationComposition.class, String.class, false, false)
    public static final Attribute<NaturalTransformationComposition, String> resultNatTransfId = new Attribute<>('resultNatTransfId', NaturalTransformationComposition.class, String.class, false, true)
    public static final Attribute<NaturalTransformationComposition, String> operandNatTransfId = new Attribute<>('operandNatTransfId', NaturalTransformationComposition.class, String.class, false, true)
    public static final Attribute<NaturalTransformationComposition, Long> operandIndex = new Attribute<>('operandIndex', NaturalTransformationComposition.class, Long.class, false, true)
}
