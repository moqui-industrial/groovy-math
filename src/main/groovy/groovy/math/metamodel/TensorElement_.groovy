/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TensorElement
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TensorElement

@CompileStatic
class TensorElement_ {
    public static final String ENTITY_NAME = 'TensorElement'
    public static final String FULL_NAME = 'moqui.math.TensorElement'

    public static final Attribute<TensorElement, String> tensorElementId = new Attribute<>('tensorElementId', TensorElement.class, String.class, true, true)
    public static final Attribute<TensorElement, String> tensorId = new Attribute<>('tensorId', TensorElement.class, String.class, false, true)
    public static final Attribute<TensorElement, String> parentElementId = new Attribute<>('parentElementId', TensorElement.class, String.class, false, false)
    public static final Attribute<TensorElement, String> elementTypeEnumId = new Attribute<>('elementTypeEnumId', TensorElement.class, String.class, false, false)
    public static final Attribute<TensorElement, Long> linearIndex = new Attribute<>('linearIndex', TensorElement.class, Long.class, false, true)
    public static final Attribute<TensorElement, String> indicesJson = new Attribute<>('indicesJson', TensorElement.class, String.class, false, false)
    public static final Attribute<TensorElement, BigDecimal> realValue = new Attribute<>('realValue', TensorElement.class, BigDecimal.class, false, false)
    public static final Attribute<TensorElement, BigDecimal> imaginaryValue = new Attribute<>('imaginaryValue', TensorElement.class, BigDecimal.class, false, false)
    public static final Attribute<TensorElement, String> symbolicValue = new Attribute<>('symbolicValue', TensorElement.class, String.class, false, false)
}
