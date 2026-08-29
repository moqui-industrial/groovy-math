/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelDef
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
@EqualsAndHashCode(includes = ['mathModelDefId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MathModelDef implements Serializable {
    private static final long serialVersionUID = 1L

    /** mathModelDefId */
    String mathModelDefId

    /** parentModelDefId */
    String parentModelDefId

    /** modelTypeEnumId */
    String modelTypeEnumId

    /** usageContextEnumId */
    String usageContextEnumId

    /** domainEnumId */
    String domainEnumId

    /** serviceName */
    String serviceName

    /** modelName */
    String modelName

    /** description */
    String description

    /** versionNumber */
    Long versionNumber

    /** releaseStatusId */
    String releaseStatusId

    /** fromDate */
    java.sql.Timestamp fromDate

    /** thruDate */
    java.sql.Timestamp thruDate

    MathModelDef parent

    List<MathModel> models = new ArrayList<>()

    MathModelDef() {}

    MathModelDef(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('mathModelDefId')) this.mathModelDefId = args.get('mathModelDefId')?.toString()
            if (args.containsKey('parentModelDefId')) this.parentModelDefId = args.get('parentModelDefId')?.toString()
            if (args.containsKey('modelTypeEnumId')) this.modelTypeEnumId = args.get('modelTypeEnumId')?.toString()
            if (args.containsKey('usageContextEnumId')) this.usageContextEnumId = args.get('usageContextEnumId')?.toString()
            if (args.containsKey('domainEnumId')) this.domainEnumId = args.get('domainEnumId')?.toString()
            if (args.containsKey('serviceName')) this.serviceName = args.get('serviceName')?.toString()
            if (args.containsKey('modelName')) this.modelName = args.get('modelName')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('versionNumber')) this.versionNumber = args.get('versionNumber') != null ? ((Number) args.get('versionNumber')).longValue() : null
            if (args.containsKey('releaseStatusId')) this.releaseStatusId = args.get('releaseStatusId')?.toString()
            if (args.containsKey('fromDate')) this.fromDate = (java.sql.Timestamp) args.get('fromDate')
            if (args.containsKey('thruDate')) this.thruDate = (java.sql.Timestamp) args.get('thruDate')
        }
    }

    MathModelDef mathModelDefId(String value) {
        this.mathModelDefId = value
        return this;
    }

    MathModelDef parentModelDefId(String value) {
        this.parentModelDefId = value
        return this;
    }

    MathModelDef modelTypeEnumId(String value) {
        this.modelTypeEnumId = value
        return this;
    }

    MathModelDef usageContextEnumId(String value) {
        this.usageContextEnumId = value
        return this;
    }

    MathModelDef domainEnumId(String value) {
        this.domainEnumId = value
        return this;
    }

    MathModelDef serviceName(String value) {
        this.serviceName = value
        return this;
    }

    MathModelDef modelName(String value) {
        this.modelName = value
        return this;
    }

    MathModelDef description(String value) {
        this.description = value
        return this;
    }

    MathModelDef versionNumber(Long value) {
        this.versionNumber = value
        return this;
    }

    MathModelDef releaseStatusId(String value) {
        this.releaseStatusId = value
        return this;
    }

    MathModelDef fromDate(java.sql.Timestamp value) {
        this.fromDate = value
        return this;
    }

    MathModelDef thruDate(java.sql.Timestamp value) {
        this.thruDate = value
        return this;
    }

    MathModelDef parent(MathModelDef item) {
        this.parent = item;
        return this;
    }

    MathModelDef models(List<MathModel> list) {
        this.models = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.mathModelDefId != null) map.put('mathModelDefId', this.mathModelDefId);
        if (this.parentModelDefId != null) map.put('parentModelDefId', this.parentModelDefId);
        if (this.modelTypeEnumId != null) map.put('modelTypeEnumId', this.modelTypeEnumId);
        if (this.usageContextEnumId != null) map.put('usageContextEnumId', this.usageContextEnumId);
        if (this.domainEnumId != null) map.put('domainEnumId', this.domainEnumId);
        if (this.serviceName != null) map.put('serviceName', this.serviceName);
        if (this.modelName != null) map.put('modelName', this.modelName);
        if (this.description != null) map.put('description', this.description);
        if (this.versionNumber != null) map.put('versionNumber', this.versionNumber);
        if (this.releaseStatusId != null) map.put('releaseStatusId', this.releaseStatusId);
        if (this.fromDate != null) map.put('fromDate', this.fromDate);
        if (this.thruDate != null) map.put('thruDate', this.thruDate);
        return map;
    }
}