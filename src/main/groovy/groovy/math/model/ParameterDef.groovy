/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParameterDef
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
@EqualsAndHashCode(includes = ['parameterDefId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class ParameterDef implements Serializable {
    private static final long serialVersionUID = 1L

    /** parameterDefId */
    String parameterDefId

    /** parentParameterDefId */
    String parentParameterDefId

    /** parameterTypeEnumId */
    String parameterTypeEnumId

    /** declaredTypeObjectId */
    String declaredTypeObjectId

    /** purposeEnumId */
    String purposeEnumId

    /** groupEnumId */
    String groupEnumId

    /** classEnumId */
    String classEnumId

    /** permissionEnumId */
    String permissionEnumId

    /** userId */
    String userId

    /** uomTypeEnumId */
    String uomTypeEnumId

    /** externalId */
    String externalId

    /** isRequired */
    String isRequired

    /** priority */
    Long priority

    /** parameterCode */
    String parameterCode

    /** parameterName */
    String parameterName

    /** description */
    String description

    /** hasValue */
    String hasValue

    /** minValue */
    BigDecimal minValue

    /** maxValue */
    BigDecimal maxValue

    /** defaultValue */
    BigDecimal defaultValue

    ParameterDef parent

    CategoryObject declaredType

    ParameterDef() {}

    ParameterDef(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('parameterDefId')) this.parameterDefId = args.get('parameterDefId')?.toString()
            if (args.containsKey('parentParameterDefId')) this.parentParameterDefId = args.get('parentParameterDefId')?.toString()
            if (args.containsKey('parameterTypeEnumId')) this.parameterTypeEnumId = args.get('parameterTypeEnumId')?.toString()
            if (args.containsKey('declaredTypeObjectId')) this.declaredTypeObjectId = args.get('declaredTypeObjectId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('groupEnumId')) this.groupEnumId = args.get('groupEnumId')?.toString()
            if (args.containsKey('classEnumId')) this.classEnumId = args.get('classEnumId')?.toString()
            if (args.containsKey('permissionEnumId')) this.permissionEnumId = args.get('permissionEnumId')?.toString()
            if (args.containsKey('userId')) this.userId = args.get('userId')?.toString()
            if (args.containsKey('uomTypeEnumId')) this.uomTypeEnumId = args.get('uomTypeEnumId')?.toString()
            if (args.containsKey('externalId')) this.externalId = args.get('externalId')?.toString()
            if (args.containsKey('isRequired')) this.isRequired = args.get('isRequired')?.toString()
            if (args.containsKey('priority')) this.priority = args.get('priority') != null ? ((Number) args.get('priority')).longValue() : null
            if (args.containsKey('parameterCode')) this.parameterCode = args.get('parameterCode')?.toString()
            if (args.containsKey('parameterName')) this.parameterName = args.get('parameterName')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('hasValue')) this.hasValue = args.get('hasValue')?.toString()
            if (args.containsKey('minValue')) this.minValue = args.get('minValue') != null ? (args.get('minValue') instanceof BigDecimal ? (BigDecimal) args.get('minValue') : new BigDecimal(args.get('minValue').toString())) : null
            if (args.containsKey('maxValue')) this.maxValue = args.get('maxValue') != null ? (args.get('maxValue') instanceof BigDecimal ? (BigDecimal) args.get('maxValue') : new BigDecimal(args.get('maxValue').toString())) : null
            if (args.containsKey('defaultValue')) this.defaultValue = args.get('defaultValue') != null ? (args.get('defaultValue') instanceof BigDecimal ? (BigDecimal) args.get('defaultValue') : new BigDecimal(args.get('defaultValue').toString())) : null
        }
    }

    ParameterDef parameterDefId(String value) {
        this.parameterDefId = value
        return this;
    }

    ParameterDef parentParameterDefId(String value) {
        this.parentParameterDefId = value
        return this;
    }

    ParameterDef parameterTypeEnumId(String value) {
        this.parameterTypeEnumId = value
        return this;
    }

    ParameterDef declaredTypeObjectId(String value) {
        this.declaredTypeObjectId = value
        return this;
    }

    ParameterDef purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    ParameterDef groupEnumId(String value) {
        this.groupEnumId = value
        return this;
    }

    ParameterDef classEnumId(String value) {
        this.classEnumId = value
        return this;
    }

    ParameterDef permissionEnumId(String value) {
        this.permissionEnumId = value
        return this;
    }

    ParameterDef userId(String value) {
        this.userId = value
        return this;
    }

    ParameterDef uomTypeEnumId(String value) {
        this.uomTypeEnumId = value
        return this;
    }

    ParameterDef externalId(String value) {
        this.externalId = value
        return this;
    }

    ParameterDef isRequired(String value) {
        this.isRequired = value
        return this;
    }

    ParameterDef priority(Long value) {
        this.priority = value
        return this;
    }

    ParameterDef parameterCode(String value) {
        this.parameterCode = value
        return this;
    }

    ParameterDef parameterName(String value) {
        this.parameterName = value
        return this;
    }

    ParameterDef description(String value) {
        this.description = value
        return this;
    }

    ParameterDef hasValue(String value) {
        this.hasValue = value
        return this;
    }

    ParameterDef minValue(BigDecimal value) {
        this.minValue = value
        return this;
    }

    ParameterDef maxValue(BigDecimal value) {
        this.maxValue = value
        return this;
    }

    ParameterDef defaultValue(BigDecimal value) {
        this.defaultValue = value
        return this;
    }

    ParameterDef parent(ParameterDef item) {
        this.parent = item;
        return this;
    }

    ParameterDef declaredType(CategoryObject item) {
        this.declaredType = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.parameterDefId != null) map.put('parameterDefId', this.parameterDefId);
        if (this.parentParameterDefId != null) map.put('parentParameterDefId', this.parentParameterDefId);
        if (this.parameterTypeEnumId != null) map.put('parameterTypeEnumId', this.parameterTypeEnumId);
        if (this.declaredTypeObjectId != null) map.put('declaredTypeObjectId', this.declaredTypeObjectId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.groupEnumId != null) map.put('groupEnumId', this.groupEnumId);
        if (this.classEnumId != null) map.put('classEnumId', this.classEnumId);
        if (this.permissionEnumId != null) map.put('permissionEnumId', this.permissionEnumId);
        if (this.userId != null) map.put('userId', this.userId);
        if (this.uomTypeEnumId != null) map.put('uomTypeEnumId', this.uomTypeEnumId);
        if (this.externalId != null) map.put('externalId', this.externalId);
        if (this.isRequired != null) map.put('isRequired', this.isRequired);
        if (this.priority != null) map.put('priority', this.priority);
        if (this.parameterCode != null) map.put('parameterCode', this.parameterCode);
        if (this.parameterName != null) map.put('parameterName', this.parameterName);
        if (this.description != null) map.put('description', this.description);
        if (this.hasValue != null) map.put('hasValue', this.hasValue);
        if (this.minValue != null) map.put('minValue', this.minValue);
        if (this.maxValue != null) map.put('maxValue', this.maxValue);
        if (this.defaultValue != null) map.put('defaultValue', this.defaultValue);
        return map;
    }
}