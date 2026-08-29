/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TensorSlice
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TensorSlice

@CompileStatic
class TensorSlice_ {
    public static final String ENTITY_NAME = 'TensorSlice'
    public static final String FULL_NAME = 'moqui.math.TensorSlice'

    public static final Attribute<TensorSlice, String> transformationId = new Attribute<>('transformationId', TensorSlice.class, String.class, true, true)
    public static final Attribute<TensorSlice, String> sliceDefinitionJson = new Attribute<>('sliceDefinitionJson', TensorSlice.class, String.class, false, false)
}
