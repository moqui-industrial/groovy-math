/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.VectorContent
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['vectorContentId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class VectorContent implements Serializable {
    private static final long serialVersionUID = 1L

    /** vectorContentId */
    String vectorContentId

    /** vectorId */
    String vectorId

    /** contentLocation */
    String contentLocation

    /** contentKey */
    String contentKey

    /** contentTypeEnumId */
    String contentTypeEnumId

    /** arrayChecksum */
    String arrayChecksum

    /** arrayEncodingEnumId */
    String arrayEncodingEnumId

    /** contentDate */
    java.sql.Timestamp contentDate

    /** description */
    String description

    /** userId */
    String userId

    Vector vector

    VectorContent() {}

    VectorContent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('vectorContentId')) this.vectorContentId = args.get('vectorContentId')?.toString()
            if (args.containsKey('vectorId')) this.vectorId = args.get('vectorId')?.toString()
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation')?.toString()
            if (args.containsKey('contentKey')) this.contentKey = args.get('contentKey')?.toString()
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId')?.toString()
            if (args.containsKey('arrayChecksum')) this.arrayChecksum = args.get('arrayChecksum')?.toString()
            if (args.containsKey('arrayEncodingEnumId')) this.arrayEncodingEnumId = args.get('arrayEncodingEnumId')?.toString()
            if (args.containsKey('contentDate')) this.contentDate = (java.sql.Timestamp) args.get('contentDate')
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('userId')) this.userId = args.get('userId')?.toString()
        }
    }

    VectorContent vectorContentId(String value) {
        this.vectorContentId = value
        return this;
    }

    VectorContent vectorId(String value) {
        this.vectorId = value
        return this;
    }

    VectorContent contentLocation(String value) {
        this.contentLocation = value
        return this;
    }

    VectorContent contentKey(String value) {
        this.contentKey = value
        return this;
    }

    VectorContent contentTypeEnumId(String value) {
        this.contentTypeEnumId = value
        return this;
    }

    VectorContent arrayChecksum(String value) {
        this.arrayChecksum = value
        return this;
    }

    VectorContent arrayEncodingEnumId(String value) {
        this.arrayEncodingEnumId = value
        return this;
    }

    VectorContent contentDate(java.sql.Timestamp value) {
        this.contentDate = value
        return this;
    }

    VectorContent description(String value) {
        this.description = value
        return this;
    }

    VectorContent userId(String value) {
        this.userId = value
        return this;
    }

    VectorContent vector(Vector item) {
        this.vector = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.vectorContentId != null) map.put('vectorContentId', this.vectorContentId);
        if (this.vectorId != null) map.put('vectorId', this.vectorId);
        if (this.contentLocation != null) map.put('contentLocation', this.contentLocation);
        if (this.contentKey != null) map.put('contentKey', this.contentKey);
        if (this.contentTypeEnumId != null) map.put('contentTypeEnumId', this.contentTypeEnumId);
        if (this.arrayChecksum != null) map.put('arrayChecksum', this.arrayChecksum);
        if (this.arrayEncodingEnumId != null) map.put('arrayEncodingEnumId', this.arrayEncodingEnumId);
        if (this.contentDate != null) map.put('contentDate', this.contentDate);
        if (this.description != null) map.put('description', this.description);
        if (this.userId != null) map.put('userId', this.userId);
        return map;
    }
}