/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.builder

import groovy.transform.CompileStatic
import groovy.math.dsl.MathMeta
import groovy.math.dsl.MathModelDataType
import groovy.math.dsl.MathModelSolvingMethod
import groovy.math.dsl.MathModelType
import groovy.math.dsl.MathModelUsageContext
import groovy.math.dsl.MathSpace
import groovy.math.dsl.MatrixPurpose
import groovy.math.dsl.TransformationOperandType
import groovy.math.dsl.TransformationType
import groovy.math.metamodel.EntityRef
import groovy.math.model.Graph
import groovy.math.model.GraphEdge
import groovy.math.model.GraphVertex
import groovy.math.model.MathModel
import groovy.math.model.MathModelDef
import groovy.math.model.Matrix
import groovy.math.model.Parameter
import groovy.math.model.Tensor
import groovy.math.model.Transformation

@CompileStatic
class FluentMath {
    final MathMeta mathMeta

    FluentMath(final MathMeta mathMeta) {
        this.mathMeta = Objects.requireNonNull(mathMeta, 'MathMeta must not be null')
    }

    static MathMeta build(final MathMeta mathMeta,
                          @DelegatesTo(value = FluentMath, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure) {
        FluentMath fluent = new FluentMath(mathMeta)
        Closure<?> copy = (Closure<?>) closure.clone()
        copy.resolveStrategy = Closure.DELEGATE_FIRST
        copy.delegate = fluent
        copy.call()
        mathMeta
    }

    EntityRef<MathModelDef> modelDef(final String defId,
                                    @DelegatesTo(value = MathModelDefBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        MathModelDefBuilder builder = new MathModelDefBuilder(mathMeta, defId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<Graph> graph(final String graphId,
                          @DelegatesTo(value = GraphBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        GraphBuilder builder = new GraphBuilder(mathMeta, graphId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }
}

@CompileStatic
class MathModelDefBuilder {
    final MathMeta mathMeta
    final String defId
    String name
    String description
    MathModelType modelType = MathModelType.LinearAlgebra
    MathModelUsageContext usageContext = MathModelUsageContext.Inference

    MathModelDefBuilder(final MathMeta mathMeta, final String defId) {
        this.mathMeta = mathMeta
        this.defId = defId
    }

    MathModelDefBuilder name(String name) { this.name = name; this }
    MathModelDefBuilder description(String desc) { this.description = desc; this }
    MathModelDefBuilder modelType(MathModelType type) { this.modelType = type; this }
    MathModelDefBuilder usageContext(MathModelUsageContext ctx) { this.usageContext = ctx; this }

    EntityRef<MathModel> model(final String modelId,
                              @DelegatesTo(value = MathModelBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        MathModelBuilder builder = new MathModelBuilder(mathMeta, defId, modelId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<MathModelDef> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('mathModelDefId', defId)
        if (name) values.put('modelName', name)
        if (description) values.put('description', description)
        if (modelType) values.put('modelTypeEnumId', modelType.id)
        if (usageContext) values.put('usageContextEnumId', usageContext.id)
        mathMeta.declare('moqui.math.MathModelDef', defId, values)
        new EntityRef<>(defId, MathModelDef.class, values)
    }
}

@CompileStatic
class MathModelBuilder {
    final MathMeta mathMeta
    final String defId
    final String modelId
    String alias
    String description
    MathModelSolvingMethod solvingMethod
    private int dataSequence = 1

    MathModelBuilder(final MathMeta mathMeta, final String defId, final String modelId) {
        this.mathMeta = mathMeta
        this.defId = defId
        this.modelId = modelId
    }

    MathModelBuilder alias(String alias) { this.alias = alias; this }
    MathModelBuilder description(String desc) { this.description = desc; this }
    MathModelBuilder solvingMethod(MathModelSolvingMethod method) { this.solvingMethod = method; this }

    EntityRef<Matrix> matrix(final String matrixId,
                            @DelegatesTo(value = MatrixBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        MatrixBuilder builder = new MatrixBuilder(mathMeta, matrixId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        EntityRef<Matrix> ref = builder.build()

        // Bind to MathModelData
        String dataId = "${modelId}_Data_${matrixId}"
        Map<String, Object> dataValues = [
            mathModelDataId: dataId,
            mathModelId: modelId,
            matrixId: matrixId,
            dataTypeEnumId: MathModelDataType.Matrix.id,
            sequenceNum: (long) (dataSequence++)
        ]
        mathMeta.declare('moqui.math.MathModelData', dataId, dataValues)
        ref
    }

    EntityRef<Transformation> transformation(final String transformationId,
                                            @DelegatesTo(value = TransformationBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        TransformationBuilder builder = new TransformationBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        EntityRef<Transformation> ref = builder.build()

        // Bind to MathModelData
        String dataId = "${modelId}_Step_${transformationId}"
        Map<String, Object> dataValues = [
            mathModelDataId: dataId,
            mathModelId: modelId,
            transformationId: transformationId,
            dataTypeEnumId: MathModelDataType.Transformation.id,
            sequenceNum: (long) (dataSequence++)
        ]
        mathMeta.declare('moqui.math.MathModelData', dataId, dataValues)
        ref
    }

    EntityRef<Graph> graph(final String graphId,
                          @DelegatesTo(value = GraphBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        GraphBuilder builder = new GraphBuilder(mathMeta, graphId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<MathModel> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('mathModelId', modelId)
        values.put('mathModelDefId', defId)
        if (alias) values.put('modelAlias', alias)
        if (description) values.put('description', description)
        if (solvingMethod) values.put('solvingMethodEnumId', solvingMethod.id)
        mathMeta.declare('moqui.math.MathModel', modelId, values)
        new EntityRef<>(modelId, MathModel.class, values)
    }
}

@CompileStatic
class MatrixBuilder {
    final MathMeta mathMeta
    final String matrixId
    Long rows
    Long cols
    MatrixPurpose purpose
    MathSpace domainSpace
    MathSpace codomainSpace

    MatrixBuilder(final MathMeta mathMeta, final String matrixId) {
        this.mathMeta = mathMeta
        this.matrixId = matrixId
    }

    MatrixBuilder rows(long r) { this.rows = r; this }
    MatrixBuilder rows(int r) { this.rows = (long) r; this }
    MatrixBuilder cols(long c) { this.cols = c; this }
    MatrixBuilder cols(int c) { this.cols = (long) c; this }
    MatrixBuilder purpose(MatrixPurpose p) { this.purpose = p; this }
    MatrixBuilder domainSpace(MathSpace s) { this.domainSpace = s; this }
    MatrixBuilder codomainSpace(MathSpace s) { this.codomainSpace = s; this }

    EntityRef<Matrix> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('matrixId', matrixId)
        if (rows != null) values.put('rows', rows)
        if (cols != null) values.put('cols', cols)
        if (purpose) values.put('purposeEnumId', purpose.id)
        if (domainSpace) values.put('domainSpaceEnumId', domainSpace.id)
        if (codomainSpace) values.put('codomainSpaceEnumId', codomainSpace.id)
        mathMeta.declare('moqui.math.Matrix', matrixId, values)
        new EntityRef<>(matrixId, Matrix.class, values)
    }
}

@CompileStatic
class TransformationBuilder {
    final MathMeta mathMeta
    final String transformationId
    String name
    TransformationType type

    TransformationBuilder(final MathMeta mathMeta, final String transformationId) {
        this.mathMeta = mathMeta
        this.transformationId = transformationId
    }

    TransformationBuilder name(String n) { this.name = n; this }
    TransformationBuilder type(TransformationType t) { this.type = t; this }

    EntityRef<Transformation> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        if (name) values.put('name', name)
        if (type) values.put('transformationTypeEnumId', type.id)
        mathMeta.declare('moqui.math.Transformation', transformationId, values)
        new EntityRef<>(transformationId, Transformation.class, values)
    }
}

@CompileStatic
class GraphBuilder {
    final MathMeta mathMeta
    final String graphId
    String name
    String description

    GraphBuilder(final MathMeta mathMeta, final String graphId) {
        this.mathMeta = mathMeta
        this.graphId = graphId
    }

    GraphBuilder name(String n) { this.name = n; this }
    GraphBuilder description(String d) { this.description = d; this }

    EntityRef<GraphVertex> vertex(final String vertexId,
                                 @DelegatesTo(value = GraphVertexBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        GraphVertexBuilder builder = new GraphVertexBuilder(mathMeta, graphId, vertexId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<GraphEdge> edge(final String edgeId,
                             @DelegatesTo(value = GraphEdgeBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        GraphEdgeBuilder builder = new GraphEdgeBuilder(mathMeta, graphId, edgeId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<GraphEdge> connect(final EntityRef<GraphVertex> fromVertex, final EntityRef<GraphVertex> toVertex,
                                 final String edgeLabel = 'connectsTo', final double edgeWeight = 1.0) {
        String edgeId = "Edge_${fromVertex.id}_${toVertex.id}"
        edge(edgeId) {
            from(fromVertex)
            to(toVertex)
            label(edgeLabel)
            weight(edgeWeight)
        }
    }

    EntityRef<Graph> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('graphId', graphId)
        if (name) values.put('name', name)
        if (description) values.put('description', description)
        mathMeta.declare('moqui.math.Graph', graphId, values)
        new EntityRef<>(graphId, Graph.class, values)
    }
}

@CompileStatic
class GraphVertexBuilder {
    final MathMeta mathMeta
    final String graphId
    final String vertexId
    String label

    GraphVertexBuilder(final MathMeta mathMeta, final String graphId, final String vertexId) {
        this.mathMeta = mathMeta
        this.graphId = graphId
        this.vertexId = vertexId
    }

    GraphVertexBuilder label(String l) { this.label = l; this }

    EntityRef<Parameter> parameter(final String paramDefId, final Object value) {
        String paramId = "Param_${vertexId}_${paramDefId}"
        Map<String, Object> values = [
            parameterId: paramId,
            parameterDefId: paramDefId,
            graphId: graphId,
            graphVertexId: vertexId
        ]
        if (value instanceof Number) {
            values.put('numericValue', new BigDecimal(value.toString()))
        } else {
            values.put('textValue', value?.toString())
        }
        mathMeta.declare('moqui.math.Parameter', paramId, values)
        new EntityRef<>(paramId, Parameter.class, values)
    }

    EntityRef<GraphVertex> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('graphVertexId', vertexId)
        values.put('graphId', graphId)
        if (label) values.put('label', label)
        mathMeta.declare('moqui.math.GraphVertex', vertexId, values)
        new EntityRef<>(vertexId, GraphVertex.class, values)
    }
}

@CompileStatic
class GraphEdgeBuilder {
    final MathMeta mathMeta
    final String graphId
    final String edgeId
    String fromVertexId
    String toVertexId
    String label
    BigDecimal weight

    GraphEdgeBuilder(final MathMeta mathMeta, final String graphId, final String edgeId) {
        this.mathMeta = mathMeta
        this.graphId = graphId
        this.edgeId = edgeId
    }

    GraphEdgeBuilder from(String fromId) { this.fromVertexId = fromId; this }
    GraphEdgeBuilder from(EntityRef<GraphVertex> ref) { this.fromVertexId = ref.id; this }
    GraphEdgeBuilder to(String toId) { this.toVertexId = toId; this }
    GraphEdgeBuilder to(EntityRef<GraphVertex> ref) { this.toVertexId = ref.id; this }
    GraphEdgeBuilder label(String l) { this.label = l; this }
    GraphEdgeBuilder weight(double w) { this.weight = new BigDecimal(Double.toString(w)); this }
    GraphEdgeBuilder weight(BigDecimal w) { this.weight = w; this }

    EntityRef<GraphEdge> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('graphEdgeId', edgeId)
        values.put('graphId', graphId)
        if (fromVertexId) values.put('fromVertexId', fromVertexId)
        if (toVertexId) values.put('toVertexId', toVertexId)
        if (label) values.put('label', label)
        if (weight != null) values.put('weight', weight)
        mathMeta.declare('moqui.math.GraphEdge', edgeId, values)
        new EntityRef<>(edgeId, GraphEdge.class, values)
    }
}
