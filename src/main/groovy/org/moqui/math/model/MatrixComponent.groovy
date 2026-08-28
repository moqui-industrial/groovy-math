/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MatrixComponent
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
@EqualsAndHashCode(includes = ['matrixComponentId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MatrixComponent implements Serializable {
    private static final long serialVersionUID = 1L

    String matrixComponentId
    String matrixId // Required
    Long rowIndex // Required
    Long colIndex // Required
    String parentComponentId
    String componentTypeEnumId
    BigDecimal realValue
    BigDecimal imaginaryValue
    String symbolicValue

    // --- Relationships (In-Memory Navigation) ---
    Matrix matrix
    MatrixComponent parent
    Object type

    MatrixComponent() { }

    MatrixComponent(String matrixComponentId) {
        this.matrixComponentId = Objects.requireNonNull(matrixComponentId, "MatrixComponent.matrixComponentId cannot be null")
    }

    MatrixComponent(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('matrixComponentId')) this.matrixComponentId = args.get('matrixComponentId') as String
            if (args.containsKey('matrixId')) this.matrixId = args.get('matrixId') as String
            if (args.containsKey('rowIndex')) this.rowIndex = args.get('rowIndex') as Long
            if (args.containsKey('colIndex')) this.colIndex = args.get('colIndex') as Long
            if (args.containsKey('parentComponentId')) this.parentComponentId = args.get('parentComponentId') as String
            if (args.containsKey('componentTypeEnumId')) this.componentTypeEnumId = args.get('componentTypeEnumId') as String
            if (args.containsKey('realValue')) this.realValue = args.get('realValue') as BigDecimal
            if (args.containsKey('imaginaryValue')) this.imaginaryValue = args.get('imaginaryValue') as BigDecimal
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue') as String
            if (args.containsKey('matrix')) this.matrix = args.get('matrix') as Matrix
            if (args.containsKey('parent')) this.parent = args.get('parent') as MatrixComponent
            if (args.containsKey('type')) this.type = args.get('type') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.matrixId == null) throw new IllegalStateException("Required property missing: MatrixComponent.matrixId")
        if (this.rowIndex == null) throw new IllegalStateException("Required property missing: MatrixComponent.rowIndex")
        if (this.colIndex == null) throw new IllegalStateException("Required property missing: MatrixComponent.colIndex")
    }

    /**
     * Gradle-style closure configurator
     */
    MatrixComponent configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MatrixComponent) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    Matrix matrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.matrix == null) this.matrix = new Matrix()
        this.matrix.configure(action)
        this.matrix
    }

    MatrixComponent parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MatrixComponent) Closure<?> action) {
        if (this.parent == null) this.parent = new MatrixComponent()
        this.parent.configure(action)
        this.parent
    }
}
