/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshContent
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
@EqualsAndHashCode(includes = ['meshContentId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MeshContent implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshContentId */
    String meshContentId

    /** meshId */
    String meshId

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

    Mesh mesh

    MeshContent() {}

    MeshContent(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshContentId')) this.meshContentId = args.get('meshContentId')?.toString()
            if (args.containsKey('meshId')) this.meshId = args.get('meshId')?.toString()
            if (args.containsKey('contentLocation')) this.contentLocation = args.get('contentLocation')?.toString()
            if (args.containsKey('contentTypeEnumId')) this.contentTypeEnumId = args.get('contentTypeEnumId')?.toString()
            if (args.containsKey('contentDate')) this.contentDate = (java.sql.Timestamp) args.get('contentDate')
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('userId')) this.userId = args.get('userId')?.toString()
        }
    }

    MeshContent meshContentId(String value) {
        this.meshContentId = value
        return this;
    }

    MeshContent meshId(String value) {
        this.meshId = value
        return this;
    }

    MeshContent contentLocation(String value) {
        this.contentLocation = value
        return this;
    }

    MeshContent contentTypeEnumId(String value) {
        this.contentTypeEnumId = value
        return this;
    }

    MeshContent contentDate(java.sql.Timestamp value) {
        this.contentDate = value
        return this;
    }

    MeshContent description(String value) {
        this.description = value
        return this;
    }

    MeshContent userId(String value) {
        this.userId = value
        return this;
    }

    MeshContent mesh(Mesh item) {
        this.mesh = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshContentId != null) map.put('meshContentId', this.meshContentId);
        if (this.meshId != null) map.put('meshId', this.meshId);
        if (this.contentLocation != null) map.put('contentLocation', this.contentLocation);
        if (this.contentTypeEnumId != null) map.put('contentTypeEnumId', this.contentTypeEnumId);
        if (this.contentDate != null) map.put('contentDate', this.contentDate);
        if (this.description != null) map.put('description', this.description);
        if (this.userId != null) map.put('userId', this.userId);
        return map;
    }
}