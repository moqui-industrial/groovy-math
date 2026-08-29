/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.TensorAxis
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.TensorAxis

@CompileStatic
class TensorAxis_ {
    public static final String ENTITY_NAME = 'TensorAxis'
    public static final String FULL_NAME = 'moqui.math.TensorAxis'

    public static final Attribute<TensorAxis, String> tensorId = new Attribute<>('tensorId', TensorAxis.class, String.class, true, true)
    public static final Attribute<TensorAxis, Long> axisIndex = new Attribute<>('axisIndex', TensorAxis.class, Long.class, true, true)
    public static final Attribute<TensorAxis, Long> axisSize = new Attribute<>('axisSize', TensorAxis.class, Long.class, false, true)
    public static final Attribute<TensorAxis, Long> axisStride = new Attribute<>('axisStride', TensorAxis.class, Long.class, false, true)
    public static final Attribute<TensorAxis, String> axisTypeEnumId = new Attribute<>('axisTypeEnumId', TensorAxis.class, String.class, false, true)
    public static final Attribute<TensorAxis, String> purposeEnumId = new Attribute<>('purposeEnumId', TensorAxis.class, String.class, false, true)
    public static final Attribute<TensorAxis, String> refEntityName = new Attribute<>('refEntityName', TensorAxis.class, String.class, false, false)
    public static final Attribute<TensorAxis, String> refPkPrimaryValue = new Attribute<>('refPkPrimaryValue', TensorAxis.class, String.class, false, false)
    public static final Attribute<TensorAxis, String> refPkSecondaryValue = new Attribute<>('refPkSecondaryValue', TensorAxis.class, String.class, false, false)
    public static final Attribute<TensorAxis, String> label = new Attribute<>('label', TensorAxis.class, String.class, false, false)
    public static final Attribute<TensorAxis, BigDecimal> domainMin = new Attribute<>('domainMin', TensorAxis.class, BigDecimal.class, false, false)
    public static final Attribute<TensorAxis, BigDecimal> domainMax = new Attribute<>('domainMax', TensorAxis.class, BigDecimal.class, false, false)
    public static final Attribute<TensorAxis, String> uomId = new Attribute<>('uomId', TensorAxis.class, String.class, false, false)
}
