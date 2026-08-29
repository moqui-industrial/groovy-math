/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.Tensor
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.Tensor

@CompileStatic
class Tensor_ {
    public static final String ENTITY_NAME = 'Tensor'
    public static final String FULL_NAME = 'moqui.math.Tensor'

    public static final Attribute<Tensor, String> tensorId = new Attribute<>('tensorId', Tensor.class, String.class, true, true)
    public static final Attribute<Tensor, String> parentTensorId = new Attribute<>('parentTensorId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> tensorTypeEnumId = new Attribute<>('tensorTypeEnumId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> purposeEnumId = new Attribute<>('purposeEnumId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> vectorSpaceEnumId = new Attribute<>('vectorSpaceEnumId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> coordinateSystemId = new Attribute<>('coordinateSystemId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> approximationMethodEnumId = new Attribute<>('approximationMethodEnumId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> name = new Attribute<>('name', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> symbol = new Attribute<>('symbol', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> description = new Attribute<>('description', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, Long> size = new Attribute<>('size', Tensor.class, Long.class, false, false)
    public static final Attribute<Tensor, Long> rank = new Attribute<>('rank', Tensor.class, Long.class, false, true)
    public static final Attribute<Tensor, String> shape = new Attribute<>('shape', Tensor.class, String.class, false, true)
    public static final Attribute<Tensor, String> strides = new Attribute<>('strides', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, Long> nnz = new Attribute<>('nnz', Tensor.class, Long.class, false, false)
    public static final Attribute<Tensor, BigDecimal> frobeniusNorm = new Attribute<>('frobeniusNorm', Tensor.class, BigDecimal.class, false, false)
    public static final Attribute<Tensor, BigDecimal> fillValueReal = new Attribute<>('fillValueReal', Tensor.class, BigDecimal.class, false, false)
    public static final Attribute<Tensor, String> fillValueSymbolic = new Attribute<>('fillValueSymbolic', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> storageTypeEnumId = new Attribute<>('storageTypeEnumId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> memoryFormatEnumId = new Attribute<>('memoryFormatEnumId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> isPinned = new Attribute<>('isPinned', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> quantSchemeEnumId = new Attribute<>('quantSchemeEnumId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> quantScaleArray = new Attribute<>('quantScaleArray', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> quantZeroPointArray = new Attribute<>('quantZeroPointArray', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, Long> quantAxis = new Attribute<>('quantAxis', Tensor.class, Long.class, false, false)
    public static final Attribute<Tensor, String> elementArray = new Attribute<>('elementArray', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, byte[]> elementBlob = new Attribute<>('elementBlob', Tensor.class, byte[].class, false, false)
    public static final Attribute<Tensor, String> arrayEncodingEnumId = new Attribute<>('arrayEncodingEnumId', Tensor.class, String.class, false, false)
    public static final Attribute<Tensor, String> arrayChecksum = new Attribute<>('arrayChecksum', Tensor.class, String.class, false, false)
}
