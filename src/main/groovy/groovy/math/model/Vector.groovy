/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Vector
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
@EqualsAndHashCode(includes = ['vectorId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Vector implements Serializable {
    private static final long serialVersionUID = 1L

    /** vectorId */
    String vectorId

    /** parentVectorId */
    String parentVectorId

    /** vectorTypeEnumId */
    String vectorTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** vectorSpaceEnumId */
    String vectorSpaceEnumId

    /** coordinateSystemId */
    String coordinateSystemId

    /** name */
    String name

    /** symbol */
    String symbol

    /** description */
    String description

    /** dimension */
    Long dimension

    /** magnitude */
    BigDecimal magnitude

    /** componentArray */
    String componentArray

    /** componentBlob */
    byte[] componentBlob

    Vector parent

    CoordinateSystem coordSystem

    List<VectorComponent> components = new ArrayList<>()

    Vector() {}

    Vector(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('vectorId')) this.vectorId = args.get('vectorId')?.toString()
            if (args.containsKey('parentVectorId')) this.parentVectorId = args.get('parentVectorId')?.toString()
            if (args.containsKey('vectorTypeEnumId')) this.vectorTypeEnumId = args.get('vectorTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId')?.toString()
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId')?.toString()
            if (args.containsKey('name')) this.name = args.get('name')?.toString()
            if (args.containsKey('symbol')) this.symbol = args.get('symbol')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('dimension')) this.dimension = args.get('dimension') != null ? ((Number) args.get('dimension')).longValue() : null
            if (args.containsKey('magnitude')) this.magnitude = args.get('magnitude') != null ? (args.get('magnitude') instanceof BigDecimal ? (BigDecimal) args.get('magnitude') : new BigDecimal(args.get('magnitude').toString())) : null
            if (args.containsKey('componentArray')) this.componentArray = args.get('componentArray')?.toString()
            if (args.containsKey('componentBlob')) this.componentBlob = (byte[]) args.get('componentBlob')
        }
    }

    Vector vectorId(String value) {
        this.vectorId = value
        return this;
    }

    Vector parentVectorId(String value) {
        this.parentVectorId = value
        return this;
    }

    Vector vectorTypeEnumId(String value) {
        this.vectorTypeEnumId = value
        return this;
    }

    Vector purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    Vector vectorSpaceEnumId(String value) {
        this.vectorSpaceEnumId = value
        return this;
    }

    Vector coordinateSystemId(String value) {
        this.coordinateSystemId = value
        return this;
    }

    Vector name(String value) {
        this.name = value
        return this;
    }

    Vector symbol(String value) {
        this.symbol = value
        return this;
    }

    Vector description(String value) {
        this.description = value
        return this;
    }

    Vector dimension(Long value) {
        this.dimension = value
        return this;
    }

    Vector magnitude(BigDecimal value) {
        this.magnitude = value
        return this;
    }

    Vector componentArray(String value) {
        this.componentArray = value
        return this;
    }

    Vector componentBlob(byte[] value) {
        this.componentBlob = value
        return this;
    }

    Vector parent(Vector item) {
        this.parent = item;
        return this;
    }

    Vector coordSystem(CoordinateSystem item) {
        this.coordSystem = item;
        return this;
    }

    Vector components(List<VectorComponent> list) {
        this.components = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.vectorId != null) map.put('vectorId', this.vectorId);
        if (this.parentVectorId != null) map.put('parentVectorId', this.parentVectorId);
        if (this.vectorTypeEnumId != null) map.put('vectorTypeEnumId', this.vectorTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.vectorSpaceEnumId != null) map.put('vectorSpaceEnumId', this.vectorSpaceEnumId);
        if (this.coordinateSystemId != null) map.put('coordinateSystemId', this.coordinateSystemId);
        if (this.name != null) map.put('name', this.name);
        if (this.symbol != null) map.put('symbol', this.symbol);
        if (this.description != null) map.put('description', this.description);
        if (this.dimension != null) map.put('dimension', this.dimension);
        if (this.magnitude != null) map.put('magnitude', this.magnitude);
        if (this.componentArray != null) map.put('componentArray', this.componentArray);
        if (this.componentBlob != null) map.put('componentBlob', this.componentBlob);
        return map;
    }
}