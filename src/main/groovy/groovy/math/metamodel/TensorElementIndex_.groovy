/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TensorElementIndex
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TensorElementIndex

@CompileStatic
class TensorElementIndex_ {
    public static final String ENTITY_NAME = 'TensorElementIndex'
    public static final String FULL_NAME = 'moqui.math.TensorElementIndex'

    public static final Attribute<TensorElementIndex, String> tensorElementId = new Attribute<>('tensorElementId', TensorElementIndex.class, String.class, true, true)
    public static final Attribute<TensorElementIndex, Long> axisIndex = new Attribute<>('axisIndex', TensorElementIndex.class, Long.class, true, true)
    public static final Attribute<TensorElementIndex, Long> dimensionValue = new Attribute<>('dimensionValue', TensorElementIndex.class, Long.class, false, true)
}
