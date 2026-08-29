/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellVertex
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
@EqualsAndHashCode(includes = ['meshKCellId', 'graphVertexId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class MeshKCellVertex implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshKCellId */
    String meshKCellId

    /** graphVertexId */
    String graphVertexId

    /** sequenceNum */
    Long sequenceNum

    /** isUniqueReference */
    String isUniqueReference

    MeshKCell cell

    GraphVertex vertex

    MeshKCellVertex() {}

    MeshKCellVertex(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId')?.toString()
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId')?.toString()
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('isUniqueReference')) this.isUniqueReference = args.get('isUniqueReference')?.toString()
        }
    }

    MeshKCellVertex meshKCellId(String value) {
        this.meshKCellId = value
        return this;
    }

    MeshKCellVertex graphVertexId(String value) {
        this.graphVertexId = value
        return this;
    }

    MeshKCellVertex sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    MeshKCellVertex isUniqueReference(String value) {
        this.isUniqueReference = value
        return this;
    }

    MeshKCellVertex cell(MeshKCell item) {
        this.cell = item;
        return this;
    }

    MeshKCellVertex vertex(GraphVertex item) {
        this.vertex = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshKCellId != null) map.put('meshKCellId', this.meshKCellId);
        if (this.graphVertexId != null) map.put('graphVertexId', this.graphVertexId);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.isUniqueReference != null) map.put('isUniqueReference', this.isUniqueReference);
        return map;
    }
}