/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Matrix
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
@EqualsAndHashCode(includes = ['matrixId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class Matrix implements Serializable {
    private static final long serialVersionUID = 1L

    String matrixId
    String parentMatrixId
    String matrixTypeEnumId
    String purposeEnumId
    String domainSpaceEnumId // Required
    String codomainSpaceEnumId // Required
    String coordinateSystemId
    String approximationMethodEnumId
    String name
    String symbol
    String description
    String size
    Long rows // Required
    Long cols // Required
    Long rank
    BigDecimal determinant
    BigDecimal trace
    BigDecimal conditionNumber
    String conditionNormEnumId
    String componentArray
    byte[] componentBlob

    // --- Relationships (In-Memory Navigation) ---
    Matrix parent
    Object type
    Object purpose
    Object domainVectorSpace
    Object codomainVectorSpace
    CoordinateSystem coordSystem
    Object approximationMethod
    Object conditionNorm
    List<MatrixComponent> components = []

    Matrix() { }

    Matrix(String matrixId) {
        this.matrixId = Objects.requireNonNull(matrixId, "Matrix.matrixId cannot be null")
    }

    Matrix(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('matrixId')) this.matrixId = args.get('matrixId') as String
            if (args.containsKey('parentMatrixId')) this.parentMatrixId = args.get('parentMatrixId') as String
            if (args.containsKey('matrixTypeEnumId')) this.matrixTypeEnumId = args.get('matrixTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('domainSpaceEnumId')) this.domainSpaceEnumId = args.get('domainSpaceEnumId') as String
            if (args.containsKey('codomainSpaceEnumId')) this.codomainSpaceEnumId = args.get('codomainSpaceEnumId') as String
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId') as String
            if (args.containsKey('approximationMethodEnumId')) this.approximationMethodEnumId = args.get('approximationMethodEnumId') as String
            if (args.containsKey('name')) this.name = args.get('name') as String
            if (args.containsKey('symbol')) this.symbol = args.get('symbol') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('size')) this.size = args.get('size') as String
            if (args.containsKey('rows')) this.rows = args.get('rows') as Long
            if (args.containsKey('cols')) this.cols = args.get('cols') as Long
            if (args.containsKey('rank')) this.rank = args.get('rank') as Long
            if (args.containsKey('determinant')) this.determinant = args.get('determinant') as BigDecimal
            if (args.containsKey('trace')) this.trace = args.get('trace') as BigDecimal
            if (args.containsKey('conditionNumber')) this.conditionNumber = args.get('conditionNumber') as BigDecimal
            if (args.containsKey('conditionNormEnumId')) this.conditionNormEnumId = args.get('conditionNormEnumId') as String
            if (args.containsKey('componentArray')) this.componentArray = args.get('componentArray') as String
            if (args.containsKey('componentBlob')) this.componentBlob = args.get('componentBlob') as byte[]
            if (args.containsKey('parent')) this.parent = args.get('parent') as Matrix
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('domainVectorSpace')) this.domainVectorSpace = args.get('domainVectorSpace') as Object
            if (args.containsKey('codomainVectorSpace')) this.codomainVectorSpace = args.get('codomainVectorSpace') as Object
            if (args.containsKey('coordSystem')) this.coordSystem = args.get('coordSystem') as CoordinateSystem
            if (args.containsKey('approximationMethod')) this.approximationMethod = args.get('approximationMethod') as Object
            if (args.containsKey('conditionNorm')) this.conditionNorm = args.get('conditionNorm') as Object
            if (args.containsKey('components')) this.components = args.get('components') as List<MatrixComponent>
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.domainSpaceEnumId == null) throw new IllegalStateException("Required property missing: Matrix.domainSpaceEnumId")
        if (this.codomainSpaceEnumId == null) throw new IllegalStateException("Required property missing: Matrix.codomainSpaceEnumId")
        if (this.rows == null) throw new IllegalStateException("Required property missing: Matrix.rows")
        if (this.cols == null) throw new IllegalStateException("Required property missing: Matrix.cols")
    }

    /**
     * Gradle-style closure configurator
     */
    Matrix configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Matrix parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.parent == null) this.parent = new Matrix()
        this.parent.configure(action)
        this.parent
    }

    CoordinateSystem coordSystem(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CoordinateSystem) Closure<?> action) {
        if (this.coordSystem == null) this.coordSystem = new CoordinateSystem()
        this.coordSystem.configure(action)
        this.coordSystem
    }

    MatrixComponent components(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MatrixComponent) Closure<?> action) {
        MatrixComponent item = new MatrixComponent()
        item.configure(action)
        if (this.components == null) this.components = []
        this.components.add(item)
        item
    }
}
