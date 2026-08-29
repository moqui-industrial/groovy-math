/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelDefContent
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
@EqualsAndHashCode(includes = ['mathModelContentId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MathModelDefContent implements Serializable {
    private static final long serialVersionUID = 1L

    /** mathModelContentId */
    String mathModelContentId

    /** mathModelDefId */
    String mathModelDefId

    /** contentLocation */
    String contentLocation

    /** contentTypeEnumId */
    String contentTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** contentDate */
    java.sql.Timestamp contentDate

    /** description */
    String description

    /** userId */
    String userId

    MathModelDef model

    MathModelDefContent() {}

    MathModelDefContent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('mathModelContentId')) this.mathModelContentId = args.get('mathModelContentId')?.toString()
            if (args.containsKey('mathModelDefId')) this.mathModelDefId = args.get('mathModelDefId')?.toString()
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation')?.toString()
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('contentDate')) this.contentDate = (java.sql.Timestamp) args.get('contentDate')
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('userId')) this.userId = args.get('userId')?.toString()
        }
    }

    MathModelDefContent mathModelContentId(String value) {
        this.mathModelContentId = value
        return this;
    }

    MathModelDefContent mathModelDefId(String value) {
        this.mathModelDefId = value
        return this;
    }

    MathModelDefContent contentLocation(String value) {
        this.contentLocation = value
        return this;
    }

    MathModelDefContent contentTypeEnumId(String value) {
        this.contentTypeEnumId = value
        return this;
    }

    MathModelDefContent purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    MathModelDefContent contentDate(java.sql.Timestamp value) {
        this.contentDate = value
        return this;
    }

    MathModelDefContent description(String value) {
        this.description = value
        return this;
    }

    MathModelDefContent userId(String value) {
        this.userId = value
        return this;
    }

    MathModelDefContent model(MathModelDef item) {
        this.model = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.mathModelContentId != null) map.put('mathModelContentId', this.mathModelContentId);
        if (this.mathModelDefId != null) map.put('mathModelDefId', this.mathModelDefId);
        if (this.contentLocation != null) map.put('contentLocation', this.contentLocation);
        if (this.contentTypeEnumId != null) map.put('contentTypeEnumId', this.contentTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.contentDate != null) map.put('contentDate', this.contentDate);
        if (this.description != null) map.put('description', this.description);
        if (this.userId != null) map.put('userId', this.userId);
        return map;
    }
}