/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MatrixContent
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
@EqualsAndHashCode(includes = ['matrixContentId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MatrixContent implements Serializable {
    private static final long serialVersionUID = 1L

    /** matrixContentId */
    String matrixContentId

    /** matrixId */
    String matrixId

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

    Matrix matrix

    MatrixContent() {}

    MatrixContent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('matrixContentId')) this.matrixContentId = args.get('matrixContentId')?.toString()
            if (args.containsKey('matrixId')) this.matrixId = args.get('matrixId')?.toString()
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

    MatrixContent matrixContentId(String value) {
        this.matrixContentId = value
        return this;
    }

    MatrixContent matrixId(String value) {
        this.matrixId = value
        return this;
    }

    MatrixContent contentLocation(String value) {
        this.contentLocation = value
        return this;
    }

    MatrixContent contentKey(String value) {
        this.contentKey = value
        return this;
    }

    MatrixContent contentTypeEnumId(String value) {
        this.contentTypeEnumId = value
        return this;
    }

    MatrixContent arrayChecksum(String value) {
        this.arrayChecksum = value
        return this;
    }

    MatrixContent arrayEncodingEnumId(String value) {
        this.arrayEncodingEnumId = value
        return this;
    }

    MatrixContent contentDate(java.sql.Timestamp value) {
        this.contentDate = value
        return this;
    }

    MatrixContent description(String value) {
        this.description = value
        return this;
    }

    MatrixContent userId(String value) {
        this.userId = value
        return this;
    }

    MatrixContent matrix(Matrix item) {
        this.matrix = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.matrixContentId != null) map.put('matrixContentId', this.matrixContentId);
        if (this.matrixId != null) map.put('matrixId', this.matrixId);
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