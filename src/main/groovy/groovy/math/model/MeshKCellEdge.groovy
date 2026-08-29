/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellEdge
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
@EqualsAndHashCode(includes = ['meshKCellId', 'graphEdgeId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MeshKCellEdge implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshKCellId */
    String meshKCellId

    /** graphEdgeId */
    String graphEdgeId

    MeshKCell kCell

    GraphEdge edge

    MeshKCellEdge() {}

    MeshKCellEdge(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId')?.toString()
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId')?.toString()
        }
    }

    MeshKCellEdge meshKCellId(String value) {
        this.meshKCellId = value
        return this;
    }

    MeshKCellEdge graphEdgeId(String value) {
        this.graphEdgeId = value
        return this;
    }

    MeshKCellEdge kCell(MeshKCell item) {
        this.kCell = item;
        return this;
    }

    MeshKCellEdge edge(GraphEdge item) {
        this.edge = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshKCellId != null) map.put('meshKCellId', this.meshKCellId);
        if (this.graphEdgeId != null) map.put('graphEdgeId', this.graphEdgeId);
        return map;
    }
}