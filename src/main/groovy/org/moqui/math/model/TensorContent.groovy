/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorContent
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
@EqualsAndHashCode(includes = ['tensorContentId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TensorContent implements Serializable {
    private static final long serialVersionUID = 1L

    /** tensorContentId */
    String tensorContentId

    /** tensorId */
    String tensorId

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

    Tensor tensor

    TensorContent() {}

    TensorContent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('tensorContentId')) this.tensorContentId = args.get('tensorContentId')?.toString()
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId')?.toString()
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

    TensorContent tensorContentId(String value) {
        this.tensorContentId = value
        return this;
    }

    TensorContent tensorId(String value) {
        this.tensorId = value
        return this;
    }

    TensorContent contentLocation(String value) {
        this.contentLocation = value
        return this;
    }

    TensorContent contentKey(String value) {
        this.contentKey = value
        return this;
    }

    TensorContent contentTypeEnumId(String value) {
        this.contentTypeEnumId = value
        return this;
    }

    TensorContent arrayChecksum(String value) {
        this.arrayChecksum = value
        return this;
    }

    TensorContent arrayEncodingEnumId(String value) {
        this.arrayEncodingEnumId = value
        return this;
    }

    TensorContent contentDate(java.sql.Timestamp value) {
        this.contentDate = value
        return this;
    }

    TensorContent description(String value) {
        this.description = value
        return this;
    }

    TensorContent userId(String value) {
        this.userId = value
        return this;
    }

    TensorContent tensor(Tensor item) {
        this.tensor = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.tensorContentId != null) map.put('tensorContentId', this.tensorContentId);
        if (this.tensorId != null) map.put('tensorId', this.tensorId);
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