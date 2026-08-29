/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCell
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
@EqualsAndHashCode(includes = ['meshKCellId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MeshKCell implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshKCellId */
    String meshKCellId

    /** meshId */
    String meshId

    /** kCellTypeEnumId */
    String kCellTypeEnumId

    /** dimension */
    Long dimension

    /** label */
    String label

    /** description */
    String description

    /** measure */
    BigDecimal measure

    /** isBoundary */
    String isBoundary

    /** orientationEnumId */
    String orientationEnumId

    /** normalVectorId */
    String normalVectorId

    /** centroidVectorId */
    String centroidVectorId

    Mesh mesh

    Vector normalVector

    Vector centroidVector

    List<MeshKCellVertex> vertices = new ArrayList<>()

    List<MeshKCellEdge> edges = new ArrayList<>()

    List<Parameter> parameters = new ArrayList<>()

    MeshKCell() {}

    MeshKCell(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId')?.toString()
            if (args.containsKey('meshId')) this.meshId = args.get('meshId')?.toString()
            if (args.containsKey('kCellTypeEnumId')) this.kCellTypeEnumId = args.get('kCellTypeEnumId')?.toString()
            if (args.containsKey('dimension')) this.dimension = args.get('dimension') != null ? ((Number) args.get('dimension')).longValue() : null
            if (args.containsKey('label')) this.label = args.get('label')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('measure')) this.measure = args.get('measure') != null ? (args.get('measure') instanceof BigDecimal ? (BigDecimal) args.get('measure') : new BigDecimal(args.get('measure').toString())) : null
            if (args.containsKey('isBoundary')) this.isBoundary = args.get('isBoundary')?.toString()
            if (args.containsKey('orientationEnumId')) this.orientationEnumId = args.get('orientationEnumId')?.toString()
            if (args.containsKey('normalVectorId')) this.normalVectorId = args.get('normalVectorId')?.toString()
            if (args.containsKey('centroidVectorId')) this.centroidVectorId = args.get('centroidVectorId')?.toString()
        }
    }

    MeshKCell meshKCellId(String value) {
        this.meshKCellId = value
        return this;
    }

    MeshKCell meshId(String value) {
        this.meshId = value
        return this;
    }

    MeshKCell kCellTypeEnumId(String value) {
        this.kCellTypeEnumId = value
        return this;
    }

    MeshKCell dimension(Long value) {
        this.dimension = value
        return this;
    }

    MeshKCell label(String value) {
        this.label = value
        return this;
    }

    MeshKCell description(String value) {
        this.description = value
        return this;
    }

    MeshKCell measure(BigDecimal value) {
        this.measure = value
        return this;
    }

    MeshKCell isBoundary(String value) {
        this.isBoundary = value
        return this;
    }

    MeshKCell orientationEnumId(String value) {
        this.orientationEnumId = value
        return this;
    }

    MeshKCell normalVectorId(String value) {
        this.normalVectorId = value
        return this;
    }

    MeshKCell centroidVectorId(String value) {
        this.centroidVectorId = value
        return this;
    }

    MeshKCell mesh(Mesh item) {
        this.mesh = item;
        return this;
    }

    MeshKCell normalVector(Vector item) {
        this.normalVector = item;
        return this;
    }

    MeshKCell centroidVector(Vector item) {
        this.centroidVector = item;
        return this;
    }

    MeshKCell vertices(List<MeshKCellVertex> list) {
        this.vertices = list;
        return this;
    }

    MeshKCell edges(List<MeshKCellEdge> list) {
        this.edges = list;
        return this;
    }

    MeshKCell parameters(List<Parameter> list) {
        this.parameters = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshKCellId != null) map.put('meshKCellId', this.meshKCellId);
        if (this.meshId != null) map.put('meshId', this.meshId);
        if (this.kCellTypeEnumId != null) map.put('kCellTypeEnumId', this.kCellTypeEnumId);
        if (this.dimension != null) map.put('dimension', this.dimension);
        if (this.label != null) map.put('label', this.label);
        if (this.description != null) map.put('description', this.description);
        if (this.measure != null) map.put('measure', this.measure);
        if (this.isBoundary != null) map.put('isBoundary', this.isBoundary);
        if (this.orientationEnumId != null) map.put('orientationEnumId', this.orientationEnumId);
        if (this.normalVectorId != null) map.put('normalVectorId', this.normalVectorId);
        if (this.centroidVectorId != null) map.put('centroidVectorId', this.centroidVectorId);
        return map;
    }
}