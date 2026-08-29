/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelDefIdentification
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
@EqualsAndHashCode(includes = ['mathModelDefId', 'externalSystemEnumId', 'fromDate'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MathModelDefIdentification implements Serializable {
    private static final long serialVersionUID = 1L

    /** mathModelDefId */
    String mathModelDefId

    /** externalSystemEnumId */
    String externalSystemEnumId

    /** fromDate */
    java.sql.Timestamp fromDate

    /** thruDate */
    java.sql.Timestamp thruDate

    /** externalId */
    String externalId

    /** externalVersion */
    String externalVersion

    /** externalUri */
    String externalUri

    /** isPrimary */
    String isPrimary

    /** description */
    String description

    MathModelDef modelDef

    MathModelDefIdentification() {}

    MathModelDefIdentification(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('mathModelDefId')) this.mathModelDefId = args.get('mathModelDefId')?.toString()
            if (args.containsKey('externalSystemEnumId')) this.externalSystemEnumId = args.get('externalSystemEnumId')?.toString()
            if (args.containsKey('fromDate')) this.fromDate = (java.sql.Timestamp) args.get('fromDate')
            if (args.containsKey('thruDate')) this.thruDate = (java.sql.Timestamp) args.get('thruDate')
            if (args.containsKey('externalId')) this.externalId = args.get('externalId')?.toString()
            if (args.containsKey('externalVersion')) this.externalVersion = args.get('externalVersion')?.toString()
            if (args.containsKey('externalUri')) this.externalUri = args.get('externalUri')?.toString()
            if (args.containsKey('isPrimary')) this.isPrimary = args.get('isPrimary')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    MathModelDefIdentification mathModelDefId(String value) {
        this.mathModelDefId = value
        return this;
    }

    MathModelDefIdentification externalSystemEnumId(String value) {
        this.externalSystemEnumId = value
        return this;
    }

    MathModelDefIdentification fromDate(java.sql.Timestamp value) {
        this.fromDate = value
        return this;
    }

    MathModelDefIdentification thruDate(java.sql.Timestamp value) {
        this.thruDate = value
        return this;
    }

    MathModelDefIdentification externalId(String value) {
        this.externalId = value
        return this;
    }

    MathModelDefIdentification externalVersion(String value) {
        this.externalVersion = value
        return this;
    }

    MathModelDefIdentification externalUri(String value) {
        this.externalUri = value
        return this;
    }

    MathModelDefIdentification isPrimary(String value) {
        this.isPrimary = value
        return this;
    }

    MathModelDefIdentification description(String value) {
        this.description = value
        return this;
    }

    MathModelDefIdentification modelDef(MathModelDef item) {
        this.modelDef = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.mathModelDefId != null) map.put('mathModelDefId', this.mathModelDefId);
        if (this.externalSystemEnumId != null) map.put('externalSystemEnumId', this.externalSystemEnumId);
        if (this.fromDate != null) map.put('fromDate', this.fromDate);
        if (this.thruDate != null) map.put('thruDate', this.thruDate);
        if (this.externalId != null) map.put('externalId', this.externalId);
        if (this.externalVersion != null) map.put('externalVersion', this.externalVersion);
        if (this.externalUri != null) map.put('externalUri', this.externalUri);
        if (this.isPrimary != null) map.put('isPrimary', this.isPrimary);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}