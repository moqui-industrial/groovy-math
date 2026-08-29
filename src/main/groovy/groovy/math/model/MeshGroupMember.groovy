/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshGroupMember
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['meshGroupId', 'meshKCellId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class MeshGroupMember implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshGroupId */
    String meshGroupId

    /** meshKCellId */
    String meshKCellId

    /** purposeEnumId */
    String purposeEnumId

    /** sequenceNum */
    Long sequenceNum

    /** description */
    String description

    MeshGroup group

    MeshKCell cell

    MeshGroupMember() {}

    MeshGroupMember(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshGroupId')) this.meshGroupId = args.get('meshGroupId')?.toString()
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    MeshGroupMember meshGroupId(String value) {
        this.meshGroupId = value
        return this;
    }

    MeshGroupMember meshKCellId(String value) {
        this.meshKCellId = value
        return this;
    }

    MeshGroupMember purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    MeshGroupMember sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    MeshGroupMember description(String value) {
        this.description = value
        return this;
    }

    MeshGroupMember group(MeshGroup item) {
        this.group = item;
        return this;
    }

    MeshGroupMember cell(MeshKCell item) {
        this.cell = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshGroupId != null) map.put('meshGroupId', this.meshGroupId);
        if (this.meshKCellId != null) map.put('meshKCellId', this.meshKCellId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}