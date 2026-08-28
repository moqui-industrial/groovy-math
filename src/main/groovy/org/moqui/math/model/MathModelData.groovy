/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MathModelData
 */
package org.moqui.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import groovy.transform.Sortable
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import groovy.lang.Closure
import groovy.lang.DelegatesTo

@CompileStatic
@EqualsAndHashCode(includes = ['mathModelDataId'])
@ToString(includeNames = true, ignoreNulls = true)
@AutoClone
@Sortable(includes = ['sequenceNum'])
@Builder(builderStrategy = SimpleStrategy, prefix = '')
class MathModelData implements Serializable {
    private static final long serialVersionUID = 1L

    String mathModelDataId
    String mathModelId // Required
    String dataTypeEnumId
    String purposeEnumId
    String generatedByRunId
    String vectorId
    String matrixId
    String tensorId
    String transformationId
    String approximatedFunctionId
    String graphVertexId
    String graphEdgeId
    String meshKCellId
    String meshGroupId
    java.sql.Timestamp fromDate // Required
    java.sql.Timestamp thruDate
    Long sequenceNum
    String uomId

    // --- Relationships (In-Memory Navigation) ---
    MathModel model
    Object type
    Object purpose
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
    Object uom

    MathModelData() { }

    MathModelData(String mathModelDataId) {
        this.mathModelDataId = Objects.requireNonNull(mathModelDataId, "MathModelData.mathModelDataId cannot be null")
    }

    MathModelData(Map<String, ?> args) {
        if (args != null) {
            if (args.containsKey('mathModelDataId')) this.mathModelDataId = args.get('mathModelDataId') as String
            if (args.containsKey('mathModelId')) this.mathModelId = args.get('mathModelId') as String
            if (args.containsKey('dataTypeEnumId')) this.dataTypeEnumId = args.get('dataTypeEnumId') as String
            if (args.containsKey('purposeEnumId')) this.purposeEnumId = args.get('purposeEnumId') as String
            if (args.containsKey('generatedByRunId')) this.generatedByRunId = args.get('generatedByRunId') as String
            if (args.containsKey('vectorId')) this.vectorId = args.get('vectorId') as String
            if (args.containsKey('matrixId')) this.matrixId = args.get('matrixId') as String
            if (args.containsKey('tensorId')) this.tensorId = args.get('tensorId') as String
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId') as String
            if (args.containsKey('approximatedFunctionId')) this.approximatedFunctionId = args.get('approximatedFunctionId') as String
            if (args.containsKey('graphVertexId')) this.graphVertexId = args.get('graphVertexId') as String
            if (args.containsKey('graphEdgeId')) this.graphEdgeId = args.get('graphEdgeId') as String
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId') as String
            if (args.containsKey('meshGroupId')) this.meshGroupId = args.get('meshGroupId') as String
            if (args.containsKey('fromDate')) this.fromDate = args.get('fromDate') as java.sql.Timestamp
            if (args.containsKey('thruDate')) this.thruDate = args.get('thruDate') as java.sql.Timestamp
            if (args.containsKey('sequenceNum')) this.sequenceNum = args.get('sequenceNum') as Long
            if (args.containsKey('uomId')) this.uomId = args.get('uomId') as String
            if (args.containsKey('model')) this.model = args.get('model') as MathModel
            if (args.containsKey('type')) this.type = args.get('type') as Object
            if (args.containsKey('purpose')) this.purpose = args.get('purpose') as Object
            if (args.containsKey('genRun')) this.genRun = args.get('genRun') as MathModelRun
            if (args.containsKey('vector')) this.vector = args.get('vector') as Vector
            if (args.containsKey('matrix')) this.matrix = args.get('matrix') as Matrix
            if (args.containsKey('tensor')) this.tensor = args.get('tensor') as Tensor
            if (args.containsKey('transformation')) this.transformation = args.get('transformation') as Transformation
            if (args.containsKey('approxFunc')) this.approxFunc = args.get('approxFunc') as ApproximatedFunction
            if (args.containsKey('graphVertex')) this.graphVertex = args.get('graphVertex') as GraphVertex
            if (args.containsKey('graphEdge')) this.graphEdge = args.get('graphEdge') as GraphEdge
            if (args.containsKey('meshKCell')) this.meshKCell = args.get('meshKCell') as MeshKCell
            if (args.containsKey('meshGroup')) this.meshGroup = args.get('meshGroup') as MeshGroup
            if (args.containsKey('uom')) this.uom = args.get('uom') as Object
        }
    }

    /**
     * Explicit validation method for lifecycle and required constraint checks
     */
    void validate() {
        if (this.mathModelId == null) throw new IllegalStateException("Required property missing: MathModelData.mathModelId")
        if (this.fromDate == null) throw new IllegalStateException("Required property missing: MathModelData.fromDate")
    }

    /**
     * Gradle-style closure configurator
     */
    MathModelData configure(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelData) Closure<?> action) {
        if (action == null) return this
        Closure<?> copy = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        if (copy.maximumNumberOfParameters == 0) copy.call()
        else copy.call(this)
        this
    }

    MathModel model(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModel) Closure<?> action) {
        if (this.model == null) this.model = new MathModel()
        this.model.configure(action)
        this.model
    }

    MathModelRun genRun(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MathModelRun) Closure<?> action) {
        if (this.genRun == null) this.genRun = new MathModelRun()
        this.genRun.configure(action)
        this.genRun
    }

    Vector vector(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Vector) Closure<?> action) {
        if (this.vector == null) this.vector = new Vector()
        this.vector.configure(action)
        this.vector
    }

    Matrix matrix(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Matrix) Closure<?> action) {
        if (this.matrix == null) this.matrix = new Matrix()
        this.matrix.configure(action)
        this.matrix
    }

    Tensor tensor(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Tensor) Closure<?> action) {
        if (this.tensor == null) this.tensor = new Tensor()
        this.tensor.configure(action)
        this.tensor
    }

    Transformation transformation(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Transformation) Closure<?> action) {
        if (this.transformation == null) this.transformation = new Transformation()
        this.transformation.configure(action)
        this.transformation
    }

    ApproximatedFunction approxFunc(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = ApproximatedFunction) Closure<?> action) {
        if (this.approxFunc == null) this.approxFunc = new ApproximatedFunction()
        this.approxFunc.configure(action)
        this.approxFunc
    }

    GraphVertex graphVertex(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphVertex) Closure<?> action) {
        if (this.graphVertex == null) this.graphVertex = new GraphVertex()
        this.graphVertex.configure(action)
        this.graphVertex
    }

    GraphEdge graphEdge(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = GraphEdge) Closure<?> action) {
        if (this.graphEdge == null) this.graphEdge = new GraphEdge()
        this.graphEdge.configure(action)
        this.graphEdge
    }

    MeshKCell meshKCell(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshKCell) Closure<?> action) {
        if (this.meshKCell == null) this.meshKCell = new MeshKCell()
        this.meshKCell.configure(action)
        this.meshKCell
    }

    MeshGroup meshGroup(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = MeshGroup) Closure<?> action) {
        if (this.meshGroup == null) this.meshGroup = new MeshGroup()
        this.meshGroup.configure(action)
        this.meshGroup
    }
}
