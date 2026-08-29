/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.CoordinateSystem
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
@EqualsAndHashCode(includes = ['coordinateSystemId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class CoordinateSystem implements Serializable {
    private static final long serialVersionUID = 1L

    /** coordinateSystemId */
    String coordinateSystemId

    /** parentSystemId */
    String parentSystemId

    /** transformationToParentSystemId */
    String transformationToParentSystemId

    /** vectorSpaceEnumId */
    String vectorSpaceEnumId

    /** coordinateSystemTypeEnumId */
    String coordinateSystemTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** name */
    String name

    /** symbol */
    String symbol

    /** description */
    String description

    /** originVectorId */
    String originVectorId

    CoordinateSystem parent

    CoordinateSystemTransformation transformationToParent

    CoordinateSystem() {}

    CoordinateSystem(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('coordinateSystemId')) this.coordinateSystemId = args.get('coordinateSystemId')?.toString()
            if (args.containsKey('parentSystemId')) this.parentSystemId = args.get('parentSystemId')?.toString()
            if (args.containsKey('transformationToParentSystemId')) this.transformationToParentSystemId = args.get('transformationToParentSystemId')?.toString()
            if (args.containsKey('vectorSpaceEnumId')) this.vectorSpaceEnumId = args.get('vectorSpaceEnumId')?.toString()
            if (args.containsKey('coordinateSystemTypeEnumId')) this.coordinateSystemTypeEnumId = args.get('coordinateSystemTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('name')) this.name = args.get('name')?.toString()
            if (args.containsKey('symbol')) this.symbol = args.get('symbol')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('originVectorId')) this.originVectorId = args.get('originVectorId')?.toString()
        }
    }

    CoordinateSystem coordinateSystemId(String value) {
        this.coordinateSystemId = value
        return this;
    }

    CoordinateSystem parentSystemId(String value) {
        this.parentSystemId = value
        return this;
    }

    CoordinateSystem transformationToParentSystemId(String value) {
        this.transformationToParentSystemId = value
        return this;
    }

    CoordinateSystem vectorSpaceEnumId(String value) {
        this.vectorSpaceEnumId = value
        return this;
    }

    CoordinateSystem coordinateSystemTypeEnumId(String value) {
        this.coordinateSystemTypeEnumId = value
        return this;
    }

    CoordinateSystem purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    CoordinateSystem name(String value) {
        this.name = value
        return this;
    }

    CoordinateSystem symbol(String value) {
        this.symbol = value
        return this;
    }

    CoordinateSystem description(String value) {
        this.description = value
        return this;
    }

    CoordinateSystem originVectorId(String value) {
        this.originVectorId = value
        return this;
    }

    CoordinateSystem parent(CoordinateSystem item) {
        this.parent = item;
        return this;
    }

    CoordinateSystem transformationToParent(CoordinateSystemTransformation item) {
        this.transformationToParent = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.coordinateSystemId != null) map.put('coordinateSystemId', this.coordinateSystemId);
        if (this.parentSystemId != null) map.put('parentSystemId', this.parentSystemId);
        if (this.transformationToParentSystemId != null) map.put('transformationToParentSystemId', this.transformationToParentSystemId);
        if (this.vectorSpaceEnumId != null) map.put('vectorSpaceEnumId', this.vectorSpaceEnumId);
        if (this.coordinateSystemTypeEnumId != null) map.put('coordinateSystemTypeEnumId', this.coordinateSystemTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.name != null) map.put('name', this.name);
        if (this.symbol != null) map.put('symbol', this.symbol);
        if (this.description != null) map.put('description', this.description);
        if (this.originVectorId != null) map.put('originVectorId', this.originVectorId);
        return map;
    }
}