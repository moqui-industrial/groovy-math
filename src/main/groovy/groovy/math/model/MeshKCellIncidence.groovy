/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellIncidence
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
@EqualsAndHashCode(includes = ['higherCellId', 'lowerCellId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class MeshKCellIncidence implements Serializable {
    private static final long serialVersionUID = 1L

    /** higherCellId */
    String higherCellId

    /** lowerCellId */
    String lowerCellId

    /** sequenceNum */
    Long sequenceNum

    /** orientation */
    String orientation

    MeshKCell higherCell

    MeshKCell lowerCell

    MeshKCellIncidence() {}

    MeshKCellIncidence(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('higherCellId')) this.higherCellId = args.get('higherCellId')?.toString()
            if (args.containsKey('lowerCellId')) this.lowerCellId = args.get('lowerCellId')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('orientation')) this.orientation = args.get('orientation')?.toString()
        }
    }

    MeshKCellIncidence higherCellId(String value) {
        this.higherCellId = value
        return this;
    }

    MeshKCellIncidence lowerCellId(String value) {
        this.lowerCellId = value
        return this;
    }

    MeshKCellIncidence sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    MeshKCellIncidence orientation(String value) {
        this.orientation = value
        return this;
    }

    MeshKCellIncidence higherCell(MeshKCell item) {
        this.higherCell = item;
        return this;
    }

    MeshKCellIncidence lowerCell(MeshKCell item) {
        this.lowerCell = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.higherCellId != null) map.put('higherCellId', this.higherCellId);
        if (this.lowerCellId != null) map.put('lowerCellId', this.lowerCellId);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.orientation != null) map.put('orientation', this.orientation);
        return map;
    }
}