/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.Matrix
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Matrix

@CompileStatic
class Matrix_ {
    public static final String ENTITY_NAME = 'Matrix'
    public static final String FULL_NAME = 'moqui.math.Matrix'

    public static final Attribute<Matrix, String> matrixId = new Attribute<>('matrixId', Matrix.class, String.class, true, true)
    public static final Attribute<Matrix, String> parentMatrixId = new Attribute<>('parentMatrixId', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, String> matrixTypeEnumId = new Attribute<>('matrixTypeEnumId', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, String> purposeEnumId = new Attribute<>('purposeEnumId', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, String> domainSpaceEnumId = new Attribute<>('domainSpaceEnumId', Matrix.class, String.class, false, true)
    public static final Attribute<Matrix, String> codomainSpaceEnumId = new Attribute<>('codomainSpaceEnumId', Matrix.class, String.class, false, true)
    public static final Attribute<Matrix, String> coordinateSystemId = new Attribute<>('coordinateSystemId', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, String> approximationMethodEnumId = new Attribute<>('approximationMethodEnumId', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, String> name = new Attribute<>('name', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, String> symbol = new Attribute<>('symbol', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, String> description = new Attribute<>('description', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, String> size = new Attribute<>('size', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, Long> rows = new Attribute<>('rows', Matrix.class, Long.class, false, true)
    public static final Attribute<Matrix, Long> cols = new Attribute<>('cols', Matrix.class, Long.class, false, true)
    public static final Attribute<Matrix, Long> rank = new Attribute<>('rank', Matrix.class, Long.class, false, false)
    public static final Attribute<Matrix, BigDecimal> determinant = new Attribute<>('determinant', Matrix.class, BigDecimal.class, false, false)
    public static final Attribute<Matrix, BigDecimal> trace = new Attribute<>('trace', Matrix.class, BigDecimal.class, false, false)
    public static final Attribute<Matrix, BigDecimal> conditionNumber = new Attribute<>('conditionNumber', Matrix.class, BigDecimal.class, false, false)
    public static final Attribute<Matrix, String> conditionNormEnumId = new Attribute<>('conditionNormEnumId', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, String> componentArray = new Attribute<>('componentArray', Matrix.class, String.class, false, false)
    public static final Attribute<Matrix, byte[]> componentBlob = new Attribute<>('componentBlob', Matrix.class, byte[].class, false, false)
}
