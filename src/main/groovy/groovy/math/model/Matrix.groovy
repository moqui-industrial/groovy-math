/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Matrix
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
@EqualsAndHashCode(includes = ['matrixId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Matrix implements Serializable {
    private static final long serialVersionUID = 1L

    /** matrixId */
    String matrixId

    /** parentMatrixId */
    String parentMatrixId

    /** matrixTypeEnumId */
    String matrixTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** domainSpaceEnumId */
    String domainSpaceEnumId

    /** codomainSpaceEnumId */
    String codomainSpaceEnumId

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
    String size

    /** rows */
    Long rows

    /** cols */
    Long cols

    /** rank */
    Long rank

    /** determinant */
    BigDecimal determinant

    /** trace */
    BigDecimal trace

    /** conditionNumber */
    BigDecimal conditionNumber

    /** conditionNormEnumId */
    String conditionNormEnumId

    /** componentArray */
    String componentArray

    /** componentBlob */
    byte[] componentBlob

    Matrix parent

    CoordinateSystem coordSystem

    List<MatrixComponent> components = new ArrayList<>()

    Matrix() {}

    Matrix(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('matrixId')) this.matrixId = args.get('matrixId')?.toString()
            if (args.containsKey('parentMatrixId')) this.parentMatrixId = args.get('parentMatrixId')?.toString()
            if (args.containsKey('matrixTypeEnumId')) this.matrixTypeEnumId = args.get('matrixTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('domainSpaceEnumId')) this.domainSpaceEnumId = args.get('domainSpaceEnumId')?.toString()
            if (args.containsKey('codomainSpaceEnumId')) this.codomainSpaceEnumId = args.get('codomainSpaceEnumId')?.toString()
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId')?.toString()
            if (args.containsKey('approximationMethodEnumId')) this.approximationMethodEnumId = args.get('approximationMethodEnumId')?.toString()
            if (args.containsKey('name')) this.name = args.get('name')?.toString()
            if (args.containsKey('symbol')) this.symbol = args.get('symbol')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('size')) this.size = args.get('size')?.toString()
            if (args.containsKey('rows')) this.rows = args.get('rows') != null ? ((Number) args.get('rows')).longValue() : null
            if (args.containsKey('cols')) this.cols = args.get('cols') != null ? ((Number) args.get('cols')).longValue() : null
            if (args.containsKey('rank')) this.rank = args.get('rank') != null ? ((Number) args.get('rank')).longValue() : null
            if (args.containsKey('determinant')) this.determinant = args.get('determinant') != null ? (args.get('determinant') instanceof BigDecimal ? (BigDecimal) args.get('determinant') : new BigDecimal(args.get('determinant').toString())) : null
            if (args.containsKey('trace')) this.trace = args.get('trace') != null ? (args.get('trace') instanceof BigDecimal ? (BigDecimal) args.get('trace') : new BigDecimal(args.get('trace').toString())) : null
            if (args.containsKey('conditionNumber')) this.conditionNumber = args.get('conditionNumber') != null ? (args.get('conditionNumber') instanceof BigDecimal ? (BigDecimal) args.get('conditionNumber') : new BigDecimal(args.get('conditionNumber').toString())) : null
            if (args.containsKey('conditionNormEnumId')) this.conditionNormEnumId = args.get('conditionNormEnumId')?.toString()
            if (args.containsKey('componentArray')) this.componentArray = args.get('componentArray')?.toString()
            if (args.containsKey('componentBlob')) this.componentBlob = (byte[]) args.get('componentBlob')
        }
    }

    Matrix matrixId(String value) {
        this.matrixId = value
        return this;
    }

    Matrix parentMatrixId(String value) {
        this.parentMatrixId = value
        return this;
    }

    Matrix matrixTypeEnumId(String value) {
        this.matrixTypeEnumId = value
        return this;
    }

    Matrix purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    Matrix domainSpaceEnumId(String value) {
        this.domainSpaceEnumId = value
        return this;
    }

    Matrix codomainSpaceEnumId(String value) {
        this.codomainSpaceEnumId = value
        return this;
    }

    Matrix coordinateSystemId(String value) {
        this.coordinateSystemId = value
        return this;
    }

    Matrix approximationMethodEnumId(String value) {
        this.approximationMethodEnumId = value
        return this;
    }

    Matrix name(String value) {
        this.name = value
        return this;
    }

    Matrix symbol(String value) {
        this.symbol = value
        return this;
    }

    Matrix description(String value) {
        this.description = value
        return this;
    }

    Matrix size(String value) {
        this.size = value
        return this;
    }

    Matrix rows(Long value) {
        this.rows = value
        return this;
    }

    Matrix cols(Long value) {
        this.cols = value
        return this;
    }

    Matrix rank(Long value) {
        this.rank = value
        return this;
    }

    Matrix determinant(BigDecimal value) {
        this.determinant = value
        return this;
    }

    Matrix trace(BigDecimal value) {
        this.trace = value
        return this;
    }

    Matrix conditionNumber(BigDecimal value) {
        this.conditionNumber = value
        return this;
    }

    Matrix conditionNormEnumId(String value) {
        this.conditionNormEnumId = value
        return this;
    }

    Matrix componentArray(String value) {
        this.componentArray = value
        return this;
    }

    Matrix componentBlob(byte[] value) {
        this.componentBlob = value
        return this;
    }

    Matrix parent(Matrix item) {
        this.parent = item;
        return this;
    }

    Matrix coordSystem(CoordinateSystem item) {
        this.coordSystem = item;
        return this;
    }

    Matrix components(List<MatrixComponent> list) {
        this.components = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.matrixId != null) map.put('matrixId', this.matrixId);
        if (this.parentMatrixId != null) map.put('parentMatrixId', this.parentMatrixId);
        if (this.matrixTypeEnumId != null) map.put('matrixTypeEnumId', this.matrixTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.domainSpaceEnumId != null) map.put('domainSpaceEnumId', this.domainSpaceEnumId);
        if (this.codomainSpaceEnumId != null) map.put('codomainSpaceEnumId', this.codomainSpaceEnumId);
        if (this.coordinateSystemId != null) map.put('coordinateSystemId', this.coordinateSystemId);
        if (this.approximationMethodEnumId != null) map.put('approximationMethodEnumId', this.approximationMethodEnumId);
        if (this.name != null) map.put('name', this.name);
        if (this.symbol != null) map.put('symbol', this.symbol);
        if (this.description != null) map.put('description', this.description);
        if (this.size != null) map.put('size', this.size);
        if (this.rows != null) map.put('rows', this.rows);
        if (this.cols != null) map.put('cols', this.cols);
        if (this.rank != null) map.put('rank', this.rank);
        if (this.determinant != null) map.put('determinant', this.determinant);
        if (this.trace != null) map.put('trace', this.trace);
        if (this.conditionNumber != null) map.put('conditionNumber', this.conditionNumber);
        if (this.conditionNormEnumId != null) map.put('conditionNormEnumId', this.conditionNormEnumId);
        if (this.componentArray != null) map.put('componentArray', this.componentArray);
        if (this.componentBlob != null) map.put('componentBlob', this.componentBlob);
        return map;
    }
}