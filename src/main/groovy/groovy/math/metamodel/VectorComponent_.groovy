/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.VectorComponent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.VectorComponent

@CompileStatic
class VectorComponent_ {
    public static final String ENTITY_NAME = 'VectorComponent'
    public static final String FULL_NAME = 'moqui.math.VectorComponent'

    public static final Attribute<VectorComponent, String> vectorComponentId = new Attribute<>('vectorComponentId', VectorComponent.class, String.class, true, true)
    public static final Attribute<VectorComponent, String> vectorId = new Attribute<>('vectorId', VectorComponent.class, String.class, false, true)
    public static final Attribute<VectorComponent, Long> dimensionIndex = new Attribute<>('dimensionIndex', VectorComponent.class, Long.class, false, true)
    public static final Attribute<VectorComponent, String> parentComponentId = new Attribute<>('parentComponentId', VectorComponent.class, String.class, false, false)
    public static final Attribute<VectorComponent, String> componentTypeEnumId = new Attribute<>('componentTypeEnumId', VectorComponent.class, String.class, false, false)
    public static final Attribute<VectorComponent, BigDecimal> projection = new Attribute<>('projection', VectorComponent.class, BigDecimal.class, false, false)
    public static final Attribute<VectorComponent, BigDecimal> rejection = new Attribute<>('rejection', VectorComponent.class, BigDecimal.class, false, false)
    public static final Attribute<VectorComponent, BigDecimal> realValue = new Attribute<>('realValue', VectorComponent.class, BigDecimal.class, false, false)
    public static final Attribute<VectorComponent, BigDecimal> imaginaryValue = new Attribute<>('imaginaryValue', VectorComponent.class, BigDecimal.class, false, false)
    public static final Attribute<VectorComponent, String> symbolicValue = new Attribute<>('symbolicValue', VectorComponent.class, String.class, false, false)
}
