/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelData
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
@EqualsAndHashCode(includes = ['mathModelDataId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
class MathModelData implements Serializable {
    private static final long serialVersionUID = 1L

    /** mathModelDataId */
    String mathModelDataId

    /** mathModelId */
    String mathModelId

    /** dataTypeEnumId */
    String dataTypeEnumId

    /** purposeEnumId */
    String purposeEnumId

    /** generatedByRunId */
    String generatedByRunId

    /** vectorId */
    String vectorId

    /** matrixId */
    String matrixId

    /** tensorId */
    String tensorId

    /** transformationId */
    String transformationId

    /** approximatedFunctionId */
    String approximatedFunctionId

    /** graphVertexId */
    String graphVertexId

    /** graphEdgeId */
    String graphEdgeId

    /** meshKCellId */
    String meshKCellId

    /** meshGroupId */
    String meshGroupId

    /** fromDate */
    java.sql.Timestamp fromDate

    /** thruDate */
    java.sql.Timestamp thruDate

    /** sequenceNum */
    Long sequenceNum

    /** uomId */
    String uomId

    MathModel model

    MathModelRun genRun

    Vector vector

    Matrix matrix

    Tensor tensor

    Transformation transformation

    ApproximatedFunction approxFunc

    GraphVertex graphVertex

    GraphEdge graphEdge

    MeshKCell meshKCell

    MeshGroup meshGroup

    MathModelData() {}

    MathModelData(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('mathModelDataId')) this.mathModelDataId = args.get('mathModelDataId')?.toString()
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId')?.toString()
            if (args.containsKey('dataTypeEnumId')) this.dataTypeEnumId = args.get('dataTypeEnumId')?.toString()
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId')?.toString()
            if (args.containsKey('generatedByRunId')) this.generatedByRunId = args.get('generatedByRunId')?.toString()
            if (args.containsKey('vectorId')) this.vectorId = args.get('vectorId')?.toString()
            if (args.containsKey('matrixId')) this.matrixId = args.get('matrixId')?.toString()
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId')?.toString()
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId')?.toString()
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId')?.toString()
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId')?.toString()
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId')?.toString()
            if (args.containsKey('meshGroupId')) this.meshGroupId = args.get('meshGroupId')?.toString()
            if (args.containsKey('fromDate')) this.fromDate = (java.sql.Timestamp) args.get('fromDate')
            if (args.containsKey('thruDate')) this.thruDate = (java.sql.Timestamp) args.get('thruDate')
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') != null ? ((Number) args.get('sequenceNum')).longValue() : null
            if (args.containsKey('uomId')) this.uomId = args.get('uomId')?.toString()
        }
    }

    MathModelData mathModelDataId(String value) {
        this.mathModelDataId = value
        return this;
    }

    MathModelData mathModelId(String value) {
        this.mathModelId = value
        return this;
    }

    MathModelData dataTypeEnumId(String value) {
        this.dataTypeEnumId = value
        return this;
    }

    MathModelData purposeEnumId(String value) {
        this.purposeEnumId = value
        return this;
    }

    MathModelData generatedByRunId(String value) {
        this.generatedByRunId = value
        return this;
    }

    MathModelData vectorId(String value) {
        this.vectorId = value
        return this;
    }

    MathModelData matrixId(String value) {
        this.matrixId = value
        return this;
    }

    MathModelData tensorId(String value) {
        this.tensorId = value
        return this;
    }

    MathModelData transformationId(String value) {
        this.transformationId = value
        return this;
    }

    MathModelData approximatedFunctionId(String value) {
        this.approximatedFunctionId = value
        return this;
    }

    MathModelData graphVertexId(String value) {
        this.graphVertexId = value
        return this;
    }

    MathModelData graphEdgeId(String value) {
        this.graphEdgeId = value
        return this;
    }

    MathModelData meshKCellId(String value) {
        this.meshKCellId = value
        return this;
    }

    MathModelData meshGroupId(String value) {
        this.meshGroupId = value
        return this;
    }

    MathModelData fromDate(java.sql.Timestamp value) {
        this.fromDate = value
        return this;
    }

    MathModelData thruDate(java.sql.Timestamp value) {
        this.thruDate = value
        return this;
    }

    MathModelData sequenceNum(Long value) {
        this.sequenceNum = value
        return this;
    }

    MathModelData uomId(String value) {
        this.uomId = value
        return this;
    }

    MathModelData model(MathModel item) {
        this.model = item;
        return this;
    }

    MathModelData genRun(MathModelRun item) {
        this.genRun = item;
        return this;
    }

    MathModelData vector(Vector item) {
        this.vector = item;
        return this;
    }

    MathModelData matrix(Matrix item) {
        this.matrix = item;
        return this;
    }

    MathModelData tensor(Tensor item) {
        this.tensor = item;
        return this;
    }

    MathModelData transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    MathModelData approxFunc(ApproximatedFunction item) {
        this.approxFunc = item;
        return this;
    }

    MathModelData graphVertex(GraphVertex item) {
        this.graphVertex = item;
        return this;
    }

    MathModelData graphEdge(GraphEdge item) {
        this.graphEdge = item;
        return this;
    }

    MathModelData meshKCell(MeshKCell item) {
        this.meshKCell = item;
        return this;
    }

    MathModelData meshGroup(MeshGroup item) {
        this.meshGroup = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.mathModelDataId != null) map.put('mathModelDataId', this.mathModelDataId);
        if (this.mathModelId != null) map.put('mathModelId', this.mathModelId);
        if (this.dataTypeEnumId != null) map.put('dataTypeEnumId', this.dataTypeEnumId);
        if (this.purposeEnumId != null) map.put('purposeEnumId', this.purposeEnumId);
        if (this.generatedByRunId != null) map.put('generatedByRunId', this.generatedByRunId);
        if (this.vectorId != null) map.put('vectorId', this.vectorId);
        if (this.matrixId != null) map.put('matrixId', this.matrixId);
        if (this.tensorId != null) map.put('tensorId', this.tensorId);
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.approximatedFunctionId != null) map.put('approximatedFunctionId', this.approximatedFunctionId);
        if (this.graphVertexId != null) map.put('graphVertexId', this.graphVertexId);
        if (this.graphEdgeId != null) map.put('graphEdgeId', this.graphEdgeId);
        if (this.meshKCellId != null) map.put('meshKCellId', this.meshKCellId);
        if (this.meshGroupId != null) map.put('meshGroupId', this.meshGroupId);
        if (this.fromDate != null) map.put('fromDate', this.fromDate);
        if (this.thruDate != null) map.put('thruDate', this.thruDate);
        if (this.sequenceNum != null) map.put('sequenceNum', this.sequenceNum);
        if (this.uomId != null) map.put('uomId', this.uomId);
        return map;
    }
}