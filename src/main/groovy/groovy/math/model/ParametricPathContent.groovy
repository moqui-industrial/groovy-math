/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParametricPathContent
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
@EqualsAndHashCode(includes = ['parametricPathContentId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class ParametricPathContent implements Serializable {
    private static final long serialVersionUID = 1L

    /** parametricPathContentId */
    String parametricPathContentId

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** contentLocation */
    String contentLocation

    /** contentTypeEnumId */
    String contentTypeEnumId

    /** contentDate */
    java.sql.Timestamp contentDate

    /** description */
    String description

    /** userId */
    String userId

    ParametricPath parametricPath

    ParametricPathContent() {}

    ParametricPathContent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('parametricPathContentId')) this.parametricPathContentId = args.get('parametricPathContentId')?.toString()
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation')?.toString()
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId')?.toString()
            if (args.containsKey('contentDate')) this.contentDate = (java.sql.Timestamp) args.get('contentDate')
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('userId')) this.userId = args.get('userId')?.toString()
        }
    }

    ParametricPathContent parametricPathContentId(String value) {
        this.parametricPathContentId = value
        return this;
    }

    ParametricPathContent approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    ParametricPathContent contentLocation(String value) {
        this.contentLocation = value
        return this;
    }

    ParametricPathContent contentTypeEnumId(String value) {
        this.contentTypeEnumId = value
        return this;
    }

    ParametricPathContent contentDate(java.sql.Timestamp value) {
        this.contentDate = value
        return this;
    }

    ParametricPathContent description(String value) {
        this.description = value
        return this;
    }

    ParametricPathContent userId(String value) {
        this.userId = value
        return this;
    }

    ParametricPathContent parametricPath(ParametricPath item) {
        this.parametricPath = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.parametricPathContentId != null) map.put('parametricPathContentId', this.parametricPathContentId);
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.contentLocation != null) map.put('contentLocation', this.contentLocation);
        if (this.contentTypeEnumId != null) map.put('contentTypeEnumId', this.contentTypeEnumId);
        if (this.contentDate != null) map.put('contentDate', this.contentDate);
        if (this.description != null) map.put('description', this.description);
        if (this.userId != null) map.put('userId', this.userId);
        return map;
    }
}