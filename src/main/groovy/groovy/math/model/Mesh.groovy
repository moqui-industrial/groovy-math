/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.Mesh
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
@EqualsAndHashCode(includes = ['meshId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Mesh implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshId */
    String meshId

    /** graphId */
    String graphId

    /** parentMeshId */
    String parentMeshId

    /** meshTypeEnumId */
    String meshTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** adaptationTypeEnumId */
    String adaptationTypeEnumId

    /** sourcePathId */
    String sourcePathId

    /** orientationEnumId */
    String orientationEnumId

    /** name */
    String name

    /** description */
    String description

    /** boundingBoxMinVectorId */
    String boundingBoxMinVectorId

    /** boundingBoxMaxVectorId */
    String boundingBoxMaxVectorId

    /** centroidVectorId */
    String centroidVectorId

    /** totalCellMeasure */
    BigDecimal totalCellMeasure

    /** characteristicSize */
    BigDecimal characteristicSize

    /** topology */
    String topology

    /** topologyBlob */
    byte[] topologyBlob

    Graph graph

    Mesh parent

    ParametricPath sourcePath

    Vector boundingBoxMinVector

    Vector boundingBoxMaxVector

    Vector centroidVector

    List<MeshKCell> cells = new ArrayList<>()

    Mesh() {}

    Mesh(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshId')) this.meshId = args.get('meshId')?.toString()
            if (args.containsKey('graphId')) this.graphId = args.get('graphId')?.toString()
            if (args.containsKey('parentMeshId')) this.parentMeshId = args.get('parentMeshId')?.toString()
            if (args.containsKey('meshTypeEnumId')) this.meshTypeEnumId = args.get('meshTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('adaptationTypeEnumId')) this.adaptationTypeEnumId = args.get('adaptationTypeEnumId')?.toString()
            if (args.containsKey('sourcePathId')) this.sourcePathId = args.get('sourcePathId')?.toString()
            if (args.containsKey('orientationEnumId')) this.orientationEnumId = args.get('orientationEnumId')?.toString()
            if (args.containsKey('name')) this.name = args.get('name')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('boundingBoxMinVectorId')) this.boundingBoxMinVectorId = args.get('boundingBoxMinVectorId')?.toString()
            if (args.containsKey('boundingBoxMaxVectorId')) this.boundingBoxMaxVectorId = args.get('boundingBoxMaxVectorId')?.toString()
            if (args.containsKey('centroidVectorId')) this.centroidVectorId = args.get('centroidVectorId')?.toString()
            if (args.containsKey('totalCellMeasure')) this.totalCellMeasure = args.get('totalCellMeasure') != null ? (args.get('totalCellMeasure') instanceof BigDecimal ? (BigDecimal) args.get('totalCellMeasure') : new BigDecimal(args.get('totalCellMeasure').toString())) : null
            if (args.containsKey('characteristicSize')) this.characteristicSize = args.get('characteristicSize') != null ? (args.get('characteristicSize') instanceof BigDecimal ? (BigDecimal) args.get('characteristicSize') : new BigDecimal(args.get('characteristicSize').toString())) : null
            if (args.containsKey('topology')) this.topology = args.get('topology')?.toString()
            if (args.containsKey('topologyBlob')) this.topologyBlob = (byte[]) args.get('topologyBlob')
        }
    }

    Mesh meshId(String value) {
        this.meshId = value
        return this;
    }

    Mesh graphId(String value) {
        this.graphId = value
        return this;
    }

    Mesh parentMeshId(String value) {
        this.parentMeshId = value
        return this;
    }

    Mesh meshTypeEnumId(String value) {
        this.meshTypeEnumId = value
        return this;
    }

    Mesh purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    Mesh adaptationTypeEnumId(String value) {
        this.adaptationTypeEnumId = value
        return this;
    }

    Mesh sourcePathId(String value) {
        this.sourcePathId = value
        return this;
    }

    Mesh orientationEnumId(String value) {
        this.orientationEnumId = value
        return this;
    }

    Mesh name(String value) {
        this.name = value
        return this;
    }

    Mesh description(String value) {
        this.description = value
        return this;
    }

    Mesh boundingBoxMinVectorId(String value) {
        this.boundingBoxMinVectorId = value
        return this;
    }

    Mesh boundingBoxMaxVectorId(String value) {
        this.boundingBoxMaxVectorId = value
        return this;
    }

    Mesh centroidVectorId(String value) {
        this.centroidVectorId = value
        return this;
    }

    Mesh totalCellMeasure(BigDecimal value) {
        this.totalCellMeasure = value
        return this;
    }

    Mesh characteristicSize(BigDecimal value) {
        this.characteristicSize = value
        return this;
    }

    Mesh topology(String value) {
        this.topology = value
        return this;
    }

    Mesh topologyBlob(byte[] value) {
        this.topologyBlob = value
        return this;
    }

    Mesh graph(Graph item) {
        this.graph = item;
        return this;
    }

    Mesh parent(Mesh item) {
        this.parent = item;
        return this;
    }

    Mesh sourcePath(ParametricPath item) {
        this.sourcePath = item;
        return this;
    }

    Mesh boundingBoxMinVector(Vector item) {
        this.boundingBoxMinVector = item;
        return this;
    }

    Mesh boundingBoxMaxVector(Vector item) {
        this.boundingBoxMaxVector = item;
        return this;
    }

    Mesh centroidVector(Vector item) {
        this.centroidVector = item;
        return this;
    }

    Mesh cells(List<MeshKCell> list) {
        this.cells = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshId != null) map.put('meshId', this.meshId);
        if (this.graphId != null) map.put('graphId', this.graphId);
        if (this.parentMeshId != null) map.put('parentMeshId', this.parentMeshId);
        if (this.meshTypeEnumId != null) map.put('meshTypeEnumId', this.meshTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.adaptationTypeEnumId != null) map.put('adaptationTypeEnumId', this.adaptationTypeEnumId);
        if (this.sourcePathId != null) map.put('sourcePathId', this.sourcePathId);
        if (this.orientationEnumId != null) map.put('orientationEnumId', this.orientationEnumId);
        if (this.name != null) map.put('name', this.name);
        if (this.description != null) map.put('description', this.description);
        if (this.boundingBoxMinVectorId != null) map.put('boundingBoxMinVectorId', this.boundingBoxMinVectorId);
        if (this.boundingBoxMaxVectorId != null) map.put('boundingBoxMaxVectorId', this.boundingBoxMaxVectorId);
        if (this.centroidVectorId != null) map.put('centroidVectorId', this.centroidVectorId);
        if (this.totalCellMeasure != null) map.put('totalCellMeasure', this.totalCellMeasure);
        if (this.characteristicSize != null) map.put('characteristicSize', this.characteristicSize);
        if (this.topology != null) map.put('topology', this.topology);
        if (this.topologyBlob != null) map.put('topologyBlob', this.topologyBlob);
        return map;
    }
}