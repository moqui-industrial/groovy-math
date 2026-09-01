/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.ct.ExponentialObject
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.ExponentialObject

@CompileStatic
class ExponentialObject_ {
    public static final String ENTITY_NAME = 'ExponentialObject'
    public static final String FULL_NAME = 'moqui.math.ct.ExponentialObject'

    public static final Attribute<ExponentialObject, String> categoryId = new Attribute<>('categoryId', ExponentialObject.class, String.class, true, true)
    public static final Attribute<ExponentialObject, String> argumentObjectId = new Attribute<>('argumentObjectId', ExponentialObject.class, String.class, true, true)
    public static final Attribute<ExponentialObject, String> valueObjectId = new Attribute<>('valueObjectId', ExponentialObject.class, String.class, true, true)
    public static final Attribute<ExponentialObject, String> exponentialObjectId = new Attribute<>('exponentialObjectId', ExponentialObject.class, String.class, false, true)
    public static final Attribute<ExponentialObject, String> productObjectId = new Attribute<>('productObjectId', ExponentialObject.class, String.class, false, true)
    public static final Attribute<ExponentialObject, String> evaluationMorphismId = new Attribute<>('evaluationMorphismId', ExponentialObject.class, String.class, false, true)
}
