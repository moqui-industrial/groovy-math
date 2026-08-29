/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshGroup
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
@EqualsAndHashCode(includes = ['meshGroupId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MeshGroup implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshGroupId */
    String meshGroupId

    /** meshId */
    String meshId

    /** groupTypeEnumId */
    String groupTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** mathModelId */
    String mathModelId

    /** groupName */
    String groupName

    /** description */
    String description

    Mesh mesh

    MathModel mathModel

    List<MeshGroupMember> members = new ArrayList<>()

    MeshGroup() {}

    MeshGroup(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshGroupId')) this.meshGroupId = args.get('meshGroupId')?.toString()
            if (args.containsKey('meshId')) this.meshId = args.get('meshId')?.toString()
            if (args.containsKey('groupTypeEnumId')) this.groupTypeEnumId = args.get('groupTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId')?.toString()
            if (args.containsKey('groupName')) this.groupName = args.get('groupName')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    MeshGroup meshGroupId(String value) {
        this.meshGroupId = value
        return this;
    }

    MeshGroup meshId(String value) {
        this.meshId = value
        return this;
    }

    MeshGroup groupTypeEnumId(String value) {
        this.groupTypeEnumId = value
        return this;
    }

    MeshGroup purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    MeshGroup mathModelId(String value) {
        this.mathModelId = value
        return this;
    }

    MeshGroup groupName(String value) {
        this.groupName = value
        return this;
    }

    MeshGroup description(String value) {
        this.description = value
        return this;
    }

    MeshGroup mesh(Mesh item) {
        this.mesh = item;
        return this;
    }

    MeshGroup mathModel(MathModel item) {
        this.mathModel = item;
        return this;
    }

    MeshGroup members(List<MeshGroupMember> list) {
        this.members = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshGroupId != null) map.put('meshGroupId', this.meshGroupId);
        if (this.meshId != null) map.put('meshId', this.meshId);
        if (this.groupTypeEnumId != null) map.put('groupTypeEnumId', this.groupTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.mathModelId != null) map.put('mathModelId', this.mathModelId);
        if (this.groupName != null) map.put('groupName', this.groupName);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}