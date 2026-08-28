/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Tensor
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['tensorId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Tensor implements Serializable {
    private static final long serialVersionUID = 1L

    String tensorId
    String parentTensorId
    String tensorTypeEnumId
    String purposeEnumId
    String vectorSpaceEnumId
    String coordinateSystemId
    String approximationMethodEnumId
    String name
    String symbol
    String description
    Long size
    Long rank // Required
    String shape // Required
    String strides
    Long nnz
    BigDecimal frobeniusNorm
    BigDecimal fillValueReal
    String fillValueSymbolic
    String storageTypeEnumId
    String memoryFormatEnumId
    String isPinned
    String quantSchemeEnumId
    String quantScaleArray
    String quantZeroPointArray
    Long quantAxis
    String elementArray
    byte[] elementBlob
    String arrayEncodingEnumId
    String arrayChecksum

    // --- Relationships (In-Memory Navigation) ---
    Tensor parent
    Object type
    Object purpose
    Object vectorSpace
    CoordinateSystem coordSystem
    Object approximationMethod
    Object storageType
    Object memoryFormat
    Object quantScheme
    Object arrayEncoding
    List<TensorElement> elements = []

    Tensor() { }

    Tensor(String tensorId) {
        this.tensorId = Objects.requireNonNull(tensorId, "Tensor.tensorId cannot be null")
    }

    Tensor(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId') as String
            if (args.containsKey('parentTensorId')) this.parentTensorId = args.get('parentTensorId') as String
            if (args.containsKey('tensorTypeEnumId')) this.tensorTypeEnumId = args.get('tensorTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId') as String
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId') as String
            if (args.containsKey('approximationMethodEnumId')) this.approximationMethodEnumId = args.get('approximationMethodEnumId') as String
            if (args.containsKey('name')) this.name = args.get('name') as String
            if (args.containsKey('symbol')) this.symbol = args.get('symbol') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('size')) this.size = args.get('size') as Long
            if (args.containsKey('rank')) this.rank = args.get('rank') as Long
            if (args.containsKey('shape')) this.shape = args.get('shape') as String
            if (args.containsKey('strides')) this.strides = args.get('strides') as String
            if (args.containsKey('nnz')) this.nnz = args.get('nnz') as Long
            if (args.containsKey('frobeniusNorm')) this.frobeniusNorm = args.get('frobeniusNorm') as BigDecimal
            if (args.containsKey('fillValueReal')) this.fillValueReal = args.get('fillValueReal') as BigDecimal
            if (args.containsKey('fillValueSymbolic')) this.fillValueSymbolic = args.get('fillValueSymbolic') as String
            if (args.containsKey('storageTypeEnumId')) this.storageTypeEnumId = args.get('storageTypeEnumId') as String
            if (args.containsKey('memoryFormatEnumId')) this.memoryFormatEnumId = args.get('memoryFormatEnumId') as String
            if (args.containsKey('isPinned')) this.isPinned = args.get('isPinned') as String
            if (args.containsKey('quantSchemeEnumId')) this.quantSchemeEnumId = args.get('quantSchemeEnumId') as String
            if (args.containsKey('quantScaleArray')) this.quantScaleArray = args.get('quantScaleArray') as String
            if (args.containsKey('quantZeroPointArray')) this.quantZeroPointArray = args.get('quantZeroPointArray') as String
            if (args.containsKey('quantAxis')) this.quantAxis = args.get('quantAxis') as Long
            if (args.containsKey('elementArray')) this.elementArray = args.get('elementArray') as String
            if (args.containsKey('elementBlob')) this.elementBlob = args.get('elementBlob') as byte[]
            if (args.containsKey('arrayEncodingEnumId')) this.arrayEncodingEnumId = args.get('arrayEncodingEnumId') as String
            if (args.containsKey('arrayChecksum')) this.arrayChecksum = args.get('arrayChecksum') as String
            if (args.containsKey('parent')) this.parent = args.get('parent') as Tensor
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('vectorSpace')) this.vectorSpace = args.get('vectorSpace') as Object
            if (args.containsKey('coordSystem')) this.coordSystem = args.get('coordSystem') as CoordinateSystem
            if (args.containsKey('approximationMethod')) this.approximationMethod = args.get('approximationMethod') as Object
            if (args.containsKey('storageType')) this.storageType = args.get('storageType') as Object
            if (args.containsKey('memoryFormat')) this.memoryFormat = args.get('memoryFormat') as Object
            if (args.containsKey('quantScheme')) this.quantScheme = args.get('quantScheme') as Object
            if (args.containsKey('arrayEncoding')) this.arrayEncoding = args.get('arrayEncoding') as Object
            if (args.containsKey('elements')) this.elements = args.get('elements') as List<TensorElement>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.rank == null) throw new IllegalStateException("Required property missing: Tensor.rank")
        if (this.shape == null) throw new IllegalStateException("Required property missing: Tensor.shape")
    }

    /**
     * Gradle-style closure configurator
     */
    Tensor configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Tensor parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.parent == null) this.parent = new Tensor()
        this.parent.configure(action)
        this.parent
    }

    CoordinateSystem coordSystem(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (this.coordSystem == null) this.coordSystem = new CoordinateSystem()
        this.coordSystem.configure(action)
        this.coordSystem
    }

    TensorElement elements(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = TensorElement) Closure<?> action) {
        TensorElement item = new TensorElement()
        item.configure(action)
        if (this.elements == null) this.elements = []
        this.elements.add(item)
        item
    }
}
