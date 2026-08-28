/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ParameterDef
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
@EqualsAndHashCode(includes = ['parameterDefId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class ParameterDef implements Serializable {
    private static final long serialVersionUID = 1L

    String parameterDefId
    String parentParameterDefId
    String parameterTypeEnumId // Required
    String declaredTypeObjectId
    String purposeEnumId
    String groupEnumId
    String classEnumId
    String permissionEnumId
    String userId
    String uomTypeEnumId
    String externalId
    String isRequired
    Long priority
    String parameterCode // Required
    String parameterName // Required
    String description
    String hasValue
    BigDecimal minValue
    BigDecimal maxValue
    BigDecimal defaultValue

    // --- Relationships (In-Memory Navigation) ---
    ParameterDef parent
    Object type
    CategoryObject declaredType
    Object purpose
    Object group
    Object classRef
    Object permission
    Object userAccount
    Object uomType

    ParameterDef() { }

    ParameterDef(String parameterDefId) {
        this.parameterDefId = Objects.requireNonNull(parameterDefId, "ParameterDef.parameterDefId cannot be null")
    }

    ParameterDef(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('parameterDefId')) this.parameterDefId = args.get('parameterDefId') as String
            if (args.containsKey('parentParameterDefId')) this.parentParameterDefId = args.get('parentParameterDefId') as String
            if (args.containsKey('parameterTypeEnumId')) this.parameterTypeEnumId = args.get('parameterTypeEnumId') as String
            if (args.containsKey('declaredTypeObjectId')) this.declaredTypeObjectId = args.get('declaredTypeObjectId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('groupEnumId')) this.groupEnumId = args.get('groupEnumId') as String
            if (args.containsKey('classEnumId')) this.classEnumId = args.get('classEnumId') as String
            if (args.containsKey('permissionEnumId')) this.permissionEnumId = args.get('permissionEnumId') as String
            if (args.containsKey('userId')) this.userId = args.get('userId') as String
            if (args.containsKey('uomTypeEnumId')) this.uomTypeEnumId = args.get('uomTypeEnumId') as String
            if (args.containsKey('externalId')) this.externalId = args.get('externalId') as String
            if (args.containsKey('isRequired')) this.isRequired = args.get('isRequired') as String
            if (args.containsKey('priority')) this.priority = args.get('priority') as Long
            if (args.containsKey('parameterCode')) this.parameterCode = args.get('parameterCode') as String
            if (args.containsKey('parameterName')) this.parameterName = args.get('parameterName') as String
            if (args.containsKey('description')) this.description = args.get('description') as String
            if (args.containsKey('hasValue')) this.hasValue = args.get('hasValue') as String
            if (args.containsKey('minValue')) this.minValue = args.get('minValue') as BigDecimal
            if (args.containsKey('maxValue')) this.maxValue = args.get('maxValue') as BigDecimal
            if (args.containsKey('defaultValue')) this.defaultValue = args.get('defaultValue') as BigDecimal
            if (args.containsKey('parent')) this.parent = args.get('parent') as ParameterDef
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('declaredType')) this.declaredType = args.get('declaredType') as CategoryObject
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('group')) this.group = args.get('group') as Object
            if (args.containsKey('classRef')) this.classRef = args.get('classRef') as Object
            else if (args.containsKey('class')) this.classRef = args.get('class') as Object
            if (args.containsKey('permission')) this.permission = args.get('permission') as Object
            if (args.containsKey('userAccount')) this.userAccount = args.get('userAccount') as Object
            if (args.containsKey('uomType')) this.uomType = args.get('uomType') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.parameterTypeEnumId == null) throw new IllegalStateException("Required property missing: ParameterDef.parameterTypeEnumId")
        if (this.parameterCode == null) throw new IllegalStateException("Required property missing: ParameterDef.parameterCode")
        if (this.parameterName == null) throw new IllegalStateException("Required property missing: ParameterDef.parameterName")
    }

    /**
     * Gradle-style closure configurator
     */
    ParameterDef configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParameterDef) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    ParameterDef parent(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ParameterDef) Closure<?> action) {
        if (this.parent == null) this.parent = new ParameterDef()
        this.parent.configure(action)
        this.parent
    }

    CategoryObject declaredType(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = CategoryObject) Closure<?> action) {
        if (this.declaredType == null) this.declaredType = new CategoryObject()
        this.declaredType.configure(action)
        this.declaredType
    }
}
