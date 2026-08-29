/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Tensor
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['tensorId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Tensor implements Serializable {
    private static final long serialVersionUID = 1L

    /** tensorId */
    String tensorId

    /** parentTensorId */
    String parentTensorId

    /** tensorTypeEnumId */
    String tensorTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** vectorSpaceEnumId */
    String vectorSpaceEnumId

    /** coordinateSystemId */
    String coordinateSystemId

    /** approximationMethodEnumId */
    String approximationMethodEnumId

    /** name */
    String name

    /** symbol */
    String symbol

    /** description */
    String description

    /** size */
    Long size

    /** rank */
    Long rank

    /** shape */
    String shape

    /** strides */
    String strides

    /** nnz */
    Long nnz

    /** frobeniusNorm */
    BigDecimal frobeniusNorm

    /** fillValueReal */
    BigDecimal fillValueReal

    /** fillValueSymbolic */
    String fillValueSymbolic

    /** storageTypeEnumId */
    String storageTypeEnumId

    /** memoryFormatEnumId */
    String memoryFormatEnumId

    /** isPinned */
    String isPinned

    /** quantSchemeEnumId */
    String quantSchemeEnumId

    /** quantScaleArray */
    String quantScaleArray

    /** quantZeroPointArray */
    String quantZeroPointArray

    /** quantAxis */
    Long quantAxis

    /** elementArray */
    String elementArray

    /** elementBlob */
    byte[] elementBlob

    /** arrayEncodingEnumId */
    String arrayEncodingEnumId

    /** arrayChecksum */
    String arrayChecksum

    Tensor parent

    CoordinateSystem coordSystem

    List<TensorElement> elements = new ArrayList<>()

    Tensor() {}

    Tensor(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId')?.toString()
            if (args.containsKey('parentTensorId')) this.parentTensorId = args.get('parentTensorId')?.toString()
            if (args.containsKey('tensorTypeEnumId')) this.tensorTypeEnumId = args.get('tensorTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId')?.toString()
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId')?.toString()
            if (args.containsKey('approximationMethodEnumId')) this.approximationMethodEnumId = args.get('approximationMethodEnumId')?.toString()
            if (args.containsKey('name')) this.name = args.get('name')?.toString()
            if (args.containsKey('symbol')) this.symbol = args.get('symbol')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('size')) this.size = args.get('size') != null ? ((Number) args.get('size')).longValue() : null
            if (args.containsKey('rank')) this.rank = args.get('rank') != null ? ((Number) args.get('rank')).longValue() : null
            if (args.containsKey('shape')) this.shape = args.get('shape')?.toString()
            if (args.containsKey('strides')) this.strides = args.get('strides')?.toString()
            if (args.containsKey('nnz')) this.nnz = args.get('nnz') != null ? ((Number) args.get('nnz')).longValue() : null
            if (args.containsKey('frobeniusNorm')) this.frobeniusNorm = args.get('frobeniusNorm') != null ? (args.get('frobeniusNorm') instanceof BigDecimal ? (BigDecimal) args.get('frobeniusNorm') : new BigDecimal(args.get('frobeniusNorm').toString())) : null
            if (args.containsKey('fillValueReal')) this.fillValueReal = args.get('fillValueReal') != null ? (args.get('fillValueReal') instanceof BigDecimal ? (BigDecimal) args.get('fillValueReal') : new BigDecimal(args.get('fillValueReal').toString())) : null
            if (args.containsKey('fillValueSymbolic')) this.fillValueSymbolic = args.get('fillValueSymbolic')?.toString()
            if (args.containsKey('storageTypeEnumId')) this.storageTypeEnumId = args.get('storageTypeEnumId')?.toString()
            if (args.containsKey('memoryFormatEnumId')) this.memoryFormatEnumId = args.get('memoryFormatEnumId')?.toString()
            if (args.containsKey('isPinned')) this.isPinned = args.get('isPinned')?.toString()
            if (args.containsKey('quantSchemeEnumId')) this.quantSchemeEnumId = args.get('quantSchemeEnumId')?.toString()
            if (args.containsKey('quantScaleArray')) this.quantScaleArray = args.get('quantScaleArray')?.toString()
            if (args.containsKey('quantZeroPointArray')) this.quantZeroPointArray = args.get('quantZeroPointArray')?.toString()
            if (args.containsKey('quantAxis')) this.quantAxis = args.get('quantAxis') != null ? ((Number) args.get('quantAxis')).longValue() : null
            if (args.containsKey('elementArray')) this.elementArray = args.get('elementArray')?.toString()
            if (args.containsKey('elementBlob')) this.elementBlob = (byte[]) args.get('elementBlob')
            if (args.containsKey('arrayEncodingEnumId')) this.arrayEncodingEnumId = args.get('arrayEncodingEnumId')?.toString()
            if (args.containsKey('arrayChecksum')) this.arrayChecksum = args.get('arrayChecksum')?.toString()
        }
    }

    Tensor tensorId(String value) {
        this.tensorId = value
        return this;
    }

    Tensor parentTensorId(String value) {
        this.parentTensorId = value
        return this;
    }

    Tensor tensorTypeEnumId(String value) {
        this.tensorTypeEnumId = value
        return this;
    }

    Tensor purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    Tensor vectorSpaceEnumId(String value) {
        this.vectorSpaceEnumId = value
        return this;
    }

    Tensor coordinateSystemId(String value) {
        this.coordinateSystemId = value
        return this;
    }

    Tensor approximationMethodEnumId(String value) {
        this.approximationMethodEnumId = value
        return this;
    }

    Tensor name(String value) {
        this.name = value
        return this;
    }

    Tensor symbol(String value) {
        this.symbol = value
        return this;
    }

    Tensor description(String value) {
        this.description = value
        return this;
    }

    Tensor size(Long value) {
        this.size = value
        return this;
    }

    Tensor rank(Long value) {
        this.rank = value
        return this;
    }

    Tensor shape(String value) {
        this.shape = value
        return this;
    }

    Tensor strides(String value) {
        this.strides = value
        return this;
    }

    Tensor nnz(Long value) {
        this.nnz = value
        return this;
    }

    Tensor frobeniusNorm(BigDecimal value) {
        this.frobeniusNorm = value
        return this;
    }

    Tensor fillValueReal(BigDecimal value) {
        this.fillValueReal = value
        return this;
    }

    Tensor fillValueSymbolic(String value) {
        this.fillValueSymbolic = value
        return this;
    }

    Tensor storageTypeEnumId(String value) {
        this.storageTypeEnumId = value
        return this;
    }

    Tensor memoryFormatEnumId(String value) {
        this.memoryFormatEnumId = value
        return this;
    }

    Tensor isPinned(String value) {
        this.isPinned = value
        return this;
    }

    Tensor quantSchemeEnumId(String value) {
        this.quantSchemeEnumId = value
        return this;
    }

    Tensor quantScaleArray(String value) {
        this.quantScaleArray = value
        return this;
    }

    Tensor quantZeroPointArray(String value) {
        this.quantZeroPointArray = value
        return this;
    }

    Tensor quantAxis(Long value) {
        this.quantAxis = value
        return this;
    }

    Tensor elementArray(String value) {
        this.elementArray = value
        return this;
    }

    Tensor elementBlob(byte[] value) {
        this.elementBlob = value
        return this;
    }

    Tensor arrayEncodingEnumId(String value) {
        this.arrayEncodingEnumId = value
        return this;
    }

    Tensor arrayChecksum(String value) {
        this.arrayChecksum = value
        return this;
    }

    Tensor parent(Tensor item) {
        this.parent = item;
        return this;
    }

    Tensor coordSystem(CoordinateSystem item) {
        this.coordSystem = item;
        return this;
    }

    Tensor elements(List<TensorElement> list) {
        this.elements = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.tensorId != null) map.put('tensorId', this.tensorId);
        if (this.parentTensorId != null) map.put('parentTensorId', this.parentTensorId);
        if (this.tensorTypeEnumId != null) map.put('tensorTypeEnumId', this.tensorTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.vectorSpaceEnumId != null) map.put('vectorSpaceEnumId', this.vectorSpaceEnumId);
        if (this.coordinateSystemId != null) map.put('coordinateSystemId', this.coordinateSystemId);
        if (this.approximationMethodEnumId != null) map.put('approximationMethodEnumId', this.approximationMethodEnumId);
        if (this.name != null) map.put('name', this.name);
        if (this.symbol != null) map.put('symbol', this.symbol);
        if (this.description != null) map.put('description', this.description);
        if (this.size != null) map.put('size', this.size);
        if (this.rank != null) map.put('rank', this.rank);
        if (this.shape != null) map.put('shape', this.shape);
        if (this.strides != null) map.put('strides', this.strides);
        if (this.nnz != null) map.put('nnz', this.nnz);
        if (this.frobeniusNorm != null) map.put('frobeniusNorm', this.frobeniusNorm);
        if (this.fillValueReal != null) map.put('fillValueReal', this.fillValueReal);
        if (this.fillValueSymbolic != null) map.put('fillValueSymbolic', this.fillValueSymbolic);
        if (this.storageTypeEnumId != null) map.put('storageTypeEnumId', this.storageTypeEnumId);
        if (this.memoryFormatEnumId != null) map.put('memoryFormatEnumId', this.memoryFormatEnumId);
        if (this.isPinned != null) map.put('isPinned', this.isPinned);
        if (this.quantSchemeEnumId != null) map.put('quantSchemeEnumId', this.quantSchemeEnumId);
        if (this.quantScaleArray != null) map.put('quantScaleArray', this.quantScaleArray);
        if (this.quantZeroPointArray != null) map.put('quantZeroPointArray', this.quantZeroPointArray);
        if (this.quantAxis != null) map.put('quantAxis', this.quantAxis);
        if (this.elementArray != null) map.put('elementArray', this.elementArray);
        if (this.elementBlob != null) map.put('elementBlob', this.elementBlob);
        if (this.arrayEncodingEnumId != null) map.put('arrayEncodingEnumId', this.arrayEncodingEnumId);
        if (this.arrayChecksum != null) map.put('arrayChecksum', this.arrayChecksum);
        return map;
    }
}