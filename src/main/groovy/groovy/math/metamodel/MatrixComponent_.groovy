/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MatrixComponent
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MatrixComponent

@CompileStatic
class MatrixComponent_ {
    public static final String ENTITY_NAME = 'MatrixComponent'
    public static final String FULL_NAME = 'moqui.math.MatrixComponent'

    public static final Attribute<MatrixComponent, String> matrixComponentId = new Attribute<>('matrixComponentId', MatrixComponent.class, String.class, true, true)
    public static final Attribute<MatrixComponent, String> matrixId = new Attribute<>('matrixId', MatrixComponent.class, String.class, false, true)
    public static final Attribute<MatrixComponent, Long> rowIndex = new Attribute<>('rowIndex', MatrixComponent.class, Long.class, false, true)
    public static final Attribute<MatrixComponent, Long> colIndex = new Attribute<>('colIndex', MatrixComponent.class, Long.class, false, true)
    public static final Attribute<MatrixComponent, String> parentComponentId = new Attribute<>('parentComponentId', MatrixComponent.class, String.class, false, false)
    public static final Attribute<MatrixComponent, String> componentTypeEnumId = new Attribute<>('componentTypeEnumId', MatrixComponent.class, String.class, false, false)
    public static final Attribute<MatrixComponent, BigDecimal> realValue = new Attribute<>('realValue', MatrixComponent.class, BigDecimal.class, false, false)
    public static final Attribute<MatrixComponent, BigDecimal> imaginaryValue = new Attribute<>('imaginaryValue', MatrixComponent.class, BigDecimal.class, false, false)
    public static final Attribute<MatrixComponent, String> symbolicValue = new Attribute<>('symbolicValue', MatrixComponent.class, String.class, false, false)
}
