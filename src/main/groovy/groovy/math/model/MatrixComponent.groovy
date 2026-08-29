/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MatrixComponent
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
@EqualsAndHashCode(includes = ['matrixComponentId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MatrixComponent implements Serializable {
    private static final long serialVersionUID = 1L

    /** matrixComponentId */
    String matrixComponentId

    /** matrixId */
    String matrixId

    /** rowIndex */
    Long rowIndex

    /** colIndex */
    Long colIndex

    /** parentComponentId */
    String parentComponentId

    /** componentTypeEnumId */
    String componentTypeEnumId

    /** realValue */
    BigDecimal realValue

    /** imaginaryValue */
    BigDecimal imaginaryValue

    /** symbolicValue */
    String symbolicValue

    Matrix matrix

    MatrixComponent parent

    MatrixComponent() {}

    MatrixComponent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('matrixComponentId')) this.matrixComponentId = args.get('matrixComponentId')?.toString()
            if (args.containsKey('matrixId')) this.matrixId = args.get('matrixId')?.toString()
            if (args.containsKey('rowIndex')) this.rowIndex = args.get('rowIndex') != null ? ((Number) args.get('rowIndex')).longValue() : null
            if (args.containsKey('colIndex')) this.colIndex = args.get('colIndex') != null ? ((Number) args.get('colIndex')).longValue() : null
            if (args.containsKey('parentComponentId')) this.parentComponentId = args.get('parentComponentId')?.toString()
            if (args.containsKey('componentTypeEnumId')) this.componentTypeEnumId = args.get('componentTypeEnumId')?.toString()
            if (args.containsKey('realValue')) this.realValue = args.get('realValue') != null ? (args.get('realValue') instanceof BigDecimal ? (BigDecimal) args.get('realValue') : new BigDecimal(args.get('realValue').toString())) : null
            if (args.containsKey('imaginaryValue')) this.imaginaryValue = args.get('imaginaryValue') != null ? (args.get('imaginaryValue') instanceof BigDecimal ? (BigDecimal) args.get('imaginaryValue') : new BigDecimal(args.get('imaginaryValue').toString())) : null
            if (args.containsKey('symbolicValue')) this.symbolicValue = args.get('symbolicValue')?.toString()
        }
    }

    MatrixComponent matrixComponentId(String value) {
        this.matrixComponentId = value
        return this;
    }

    MatrixComponent matrixId(String value) {
        this.matrixId = value
        return this;
    }

    MatrixComponent rowIndex(Long value) {
        this.rowIndex = value
        return this;
    }

    MatrixComponent colIndex(Long value) {
        this.colIndex = value
        return this;
    }

    MatrixComponent parentComponentId(String value) {
        this.parentComponentId = value
        return this;
    }

    MatrixComponent componentTypeEnumId(String value) {
        this.componentTypeEnumId = value
        return this;
    }

    MatrixComponent realValue(BigDecimal value) {
        this.realValue = value
        return this;
    }

    MatrixComponent imaginaryValue(BigDecimal value) {
        this.imaginaryValue = value
        return this;
    }

    MatrixComponent symbolicValue(String value) {
        this.symbolicValue = value
        return this;
    }

    MatrixComponent matrix(Matrix item) {
        this.matrix = item;
        return this;
    }

    MatrixComponent parent(MatrixComponent item) {
        this.parent = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.matrixComponentId != null) map.put('matrixComponentId', this.matrixComponentId);
        if (this.matrixId != null) map.put('matrixId', this.matrixId);
        if (this.rowIndex != null) map.put('rowIndex', this.rowIndex);
        if (this.colIndex != null) map.put('colIndex', this.colIndex);
        if (this.parentComponentId != null) map.put('parentComponentId', this.parentComponentId);
        if (this.componentTypeEnumId != null) map.put('componentTypeEnumId', this.componentTypeEnumId);
        if (this.realValue != null) map.put('realValue', this.realValue);
        if (this.imaginaryValue != null) map.put('imaginaryValue', this.imaginaryValue);
        if (this.symbolicValue != null) map.put('symbolicValue', this.symbolicValue);
        return map;
    }
}