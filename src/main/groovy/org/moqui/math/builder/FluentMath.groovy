/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.builder

import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import org.moqui.math.dsl.DslEnumValue
import org.moqui.math.dsl.MathMeta
import org.moqui.math.dsl.MathModelDataType
import org.moqui.math.dsl.MathModelSolvingMethod
import org.moqui.math.dsl.MathModelType
import org.moqui.math.dsl.MathModelUsageContext
import org.moqui.math.dsl.MathSpace
import org.moqui.math.dsl.MatrixPurpose
import org.moqui.math.dsl.MatrixType
import org.moqui.math.dsl.NormDomain
import org.moqui.math.dsl.NormOrder
import org.moqui.math.dsl.TensorDecompMethod
import org.moqui.math.dsl.TransformationOperandType
import org.moqui.math.dsl.TransformationPurpose
import org.moqui.math.dsl.TransformationType
import org.moqui.math.dsl.TriangularExtractionType
import org.moqui.math.metamodel.EntityRef
import org.moqui.math.model.BandExtraction
import org.moqui.math.model.BlockMatrixExtraction
import org.moqui.math.model.CoordinateSystemTransformation
import org.moqui.math.model.DiagonalExtraction
import org.moqui.math.model.Graph
import org.moqui.math.model.GraphEdge
import org.moqui.math.model.GraphVertex
import org.moqui.math.model.MathModel
import org.moqui.math.model.MathModelDef
import org.moqui.math.model.Matrix
import org.moqui.math.model.MatrixDecomposition
import org.moqui.math.model.NormResult
import org.moqui.math.model.Parameter
import org.moqui.math.model.Tensor
import org.moqui.math.model.TensorDecomposition
import org.moqui.math.model.TensorSlice
import org.moqui.math.model.Transformation
import org.moqui.math.model.TriangularExtraction
import org.moqui.math.model.Vector

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

    EntityRef<MathModel> model(final String modelId,
                              @DelegatesTo(value = MathModelBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String autoDefId = "${modelId}_Def"
        if (mathMeta.entity('moqui.math.MathModelDef').findByName(autoDefId) == null) {
            Map<String, Object> defValues = [
                mathModelDefId: autoDefId,
                modelName: modelId,
                modelTypeEnumId: MathModelType.DlFeedforward.id,
                usageContextEnumId: MathModelUsageContext.Inference.id
            ]
            mathMeta.declare('moqui.math.MathModelDef', autoDefId, defValues)
        }
        MathModelBuilder builder = new MathModelBuilder(mathMeta, autoDefId, modelId)
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

    // Top-level standalone mathematical entities (no MathModelDef/MathModel required)
    EntityRef<Matrix> matrix(final Map<String, Object> args, final String matrixId = null,
                             @DelegatesTo(value = MatrixBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = matrixId ?: (args?.get('matrixId') as String) ?: (args?.get('id') as String)
        MatrixBuilder builder = new MatrixBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<Matrix> matrix(final String matrixId,
                             @DelegatesTo(value = MatrixBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        matrix(null, matrixId, closure)
    }

    EntityRef<Matrix> matrix(final Map<String, Object> args, final String matrixId, final List<?> data) {
        Map<String, Object> merged = new LinkedHashMap<>()
        if (args) merged.putAll(args)
        merged.put('data', data)
        matrix(merged, matrixId, (Closure<?>) null)
    }

    EntityRef<Matrix> matrix(final String matrixId, final List<?> data, final Map<String, Object> args = null) {
        Map<String, Object> merged = new LinkedHashMap<>()
        if (args) merged.putAll(args)
        merged.put('data', data)
        matrix(merged, matrixId, (Closure<?>) null)
    }

    EntityRef<Matrix> matrix(final String matrixId, final Map<String, Object> args) {
        matrix(args, matrixId, (Closure<?>) null)
    }

    EntityRef<Vector> vector(final Map<String, Object> args, final String vectorId = null,
                             @DelegatesTo(value = VectorBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = vectorId ?: (args?.get('vectorId') as String) ?: (args?.get('id') as String)
        VectorBuilder builder = new VectorBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<Vector> vector(final String vectorId,
                             @DelegatesTo(value = VectorBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        vector(null, vectorId, closure)
    }

    EntityRef<Tensor> tensor(final Map<String, Object> args, final String tensorId = null,
                             @DelegatesTo(value = TensorBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = tensorId ?: (args?.get('tensorId') as String) ?: (args?.get('id') as String)
        TensorBuilder builder = new TensorBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<Tensor> tensor(final String tensorId,
                             @DelegatesTo(value = TensorBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        tensor(null, tensorId, closure)
    }

    EntityRef<Transformation> transformation(final Map<String, Object> args, final String transformationId = null,
                                             @DelegatesTo(value = TransformationBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        TransformationBuilder builder = new TransformationBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<Transformation> transformation(final String transformationId,
                                             @DelegatesTo(value = TransformationBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        transformation(null, transformationId, closure)
    }

    // Top-level 1:1 Satellite entity methods (declaring both Transformation and Satellite)
    EntityRef<MatrixDecomposition> matrixDecomposition(final Map<String, Object> args, final String transformationId = null,
                                                       @DelegatesTo(value = MatrixDecompositionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        ensureTransformation(id, args, TransformationType.Svd)
        MatrixDecompositionBuilder builder = new MatrixDecompositionBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<MatrixDecomposition> matrixDecomposition(final String transformationId,
                                                       @DelegatesTo(value = MatrixDecompositionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        matrixDecomposition(null, transformationId, closure)
    }

    EntityRef<DiagonalExtraction> diagonalExtraction(final Map<String, Object> args, final String transformationId = null,
                                                     @DelegatesTo(value = DiagonalExtractionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        ensureTransformation(id, args, TransformationType.DiagExtract)
        DiagonalExtractionBuilder builder = new DiagonalExtractionBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<DiagonalExtraction> diagonalExtraction(final String transformationId,
                                                     @DelegatesTo(value = DiagonalExtractionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        diagonalExtraction(null, transformationId, closure)
    }

    EntityRef<TriangularExtraction> triangularExtraction(final Map<String, Object> args, final String transformationId = null,
                                                         @DelegatesTo(value = TriangularExtractionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        ensureTransformation(id, args, TransformationType.UpperTriangExtract)
        TriangularExtractionBuilder builder = new TriangularExtractionBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<TriangularExtraction> triangularExtraction(final String transformationId,
                                                         @DelegatesTo(value = TriangularExtractionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        triangularExtraction(null, transformationId, closure)
    }

    EntityRef<BandExtraction> bandExtraction(final Map<String, Object> args, final String transformationId = null,
                                             @DelegatesTo(value = BandExtractionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        ensureTransformation(id, args, TransformationType.BandExtract)
        BandExtractionBuilder builder = new BandExtractionBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<BandExtraction> bandExtraction(final String transformationId,
                                             @DelegatesTo(value = BandExtractionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        bandExtraction(null, transformationId, closure)
    }

    EntityRef<BlockMatrixExtraction> blockMatrixExtraction(final Map<String, Object> args, final String transformationId = null,
                                                           @DelegatesTo(value = BlockMatrixExtractionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        ensureTransformation(id, args, TransformationType.BlockMatrixExtract)
        BlockMatrixExtractionBuilder builder = new BlockMatrixExtractionBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<BlockMatrixExtraction> blockMatrixExtraction(final String transformationId,
                                                           @DelegatesTo(value = BlockMatrixExtractionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        blockMatrixExtraction(null, transformationId, closure)
    }

    EntityRef<TensorSlice> tensorSlice(final Map<String, Object> args, final String transformationId = null,
                                       @DelegatesTo(value = TensorSliceBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        ensureTransformation(id, args, TransformationType.TensorSplit)
        TensorSliceBuilder builder = new TensorSliceBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<TensorSlice> tensorSlice(final String transformationId,
                                       @DelegatesTo(value = TensorSliceBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        tensorSlice(null, transformationId, closure)
    }

    EntityRef<TensorDecomposition> tensorDecomposition(final Map<String, Object> args, final String transformationId = null,
                                                       @DelegatesTo(value = TensorDecompositionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        ensureTransformation(id, args, TransformationType.TensorDecomp)
        TensorDecompositionBuilder builder = new TensorDecompositionBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<TensorDecomposition> tensorDecomposition(final String transformationId,
                                                       @DelegatesTo(value = TensorDecompositionBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        tensorDecomposition(null, transformationId, closure)
    }

    EntityRef<NormResult> normResult(final Map<String, Object> args, final String transformationId = null,
                                     @DelegatesTo(value = NormResultBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        ensureTransformation(id, args, TransformationType.Norm)
        NormResultBuilder builder = new NormResultBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<NormResult> normResult(final String transformationId,
                                     @DelegatesTo(value = NormResultBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        normResult(null, transformationId, closure)
    }

    EntityRef<CoordinateSystemTransformation> coordinateSystemTransformation(final Map<String, Object> args, final String transformationId = null,
                                                                             @DelegatesTo(value = CoordinateSystemTransformationBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        ensureTransformation(id, args, TransformationType.Affine)
        CoordinateSystemTransformationBuilder builder = new CoordinateSystemTransformationBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        builder.build()
    }

    EntityRef<CoordinateSystemTransformation> coordinateSystemTransformation(final String transformationId,
                                                                             @DelegatesTo(value = CoordinateSystemTransformationBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        coordinateSystemTransformation(null, transformationId, closure)
    }

    private void ensureTransformation(String transformationId, Map<String, Object> args, TransformationType defaultType) {
        if (!mathMeta.hasEntity('Transformation') || mathMeta.entity('Transformation').findByName(transformationId) == null) {
            TransformationBuilder tb = new TransformationBuilder(mathMeta, transformationId)
            tb.type(defaultType)
            if (args) tb.applyArgs(args)
            tb.build()
        }
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

    private int pipelineSequence = 1

    EntityRef<Transformation> transformation(final Map<String, Object> args, final String transformationId = null,
                                             @DelegatesTo(value = TransformationBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        TransformationBuilder builder = new TransformationBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        EntityRef<Transformation> ref = builder.build()

        String stepSeqId = String.format("%02d", pipelineSequence)
        Map<String, Object> pipelineValues = [
            mathModelDefId: defId,
            stepSeqId: stepSeqId,
            stepName: builder.name ?: id,
            transformationId: id,
            sequenceNum: (long) (pipelineSequence++)
        ]
        mathMeta.declare('moqui.math.MathModelDefPipeline', "${defId}_${stepSeqId}", pipelineValues)
        ref
    }

    EntityRef<Transformation> transformation(final String transformationId,
                                             @DelegatesTo(value = TransformationBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        transformation(null, transformationId, closure)
    }

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

    MathModelDefBuilder content(final String location, final DslEnumValue contentType = null, final DslEnumValue purpose = null) {
        String contentId = "${defId}_Content"
        Map<String, Object> contentValues = new LinkedHashMap<>()
        contentValues.put('mathModelDefContentId', contentId)
        contentValues.put('mathModelDefId', defId)
        contentValues.put('contentLocation', location)
        if (contentType != null) contentValues.put('contentTypeEnumId', contentType.id)
        else if (location.endsWith('.onnx')) contentValues.put('contentTypeEnumId', 'MmCntOnnx')
        else if (location.endsWith('.pt') || location.endsWith('.pth')) contentValues.put('contentTypeEnumId', 'MmCntTorchScript')
        if (purpose != null) contentValues.put('contentPurposeEnumId', purpose.id)
        else contentValues.put('contentPurposeEnumId', 'MmdcpFullModel')
        mathMeta.declare('moqui.math.MathModelDefContent', contentId, contentValues)
        this
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
    String location
    DslEnumValue contentType
    DslEnumValue contentPurpose
    MathModelSolvingMethod solvingMethod
    private int dataSequence = 1

    MathModelBuilder(final MathMeta mathMeta, final String defId, final String modelId) {
        this.mathMeta = mathMeta
        this.defId = defId
        this.modelId = modelId
    }

    MathModelBuilder alias(String alias) { this.alias = alias; this }
    MathModelBuilder description(String desc) { this.description = desc; this }
    MathModelBuilder location(String loc) { this.location = loc; this }
    MathModelBuilder contentLocation(String loc) { this.location = loc; this }
    MathModelBuilder contentType(DslEnumValue type) { this.contentType = type; this }
    MathModelBuilder contentPurpose(DslEnumValue purpose) { this.contentPurpose = purpose; this }
    MathModelBuilder content(String loc, DslEnumValue contentType = null, DslEnumValue purpose = null) {
        this.location = loc
        this.contentType = contentType
        this.contentPurpose = purpose
        this
    }
    MathModelBuilder solvingMethod(MathModelSolvingMethod method) { this.solvingMethod = method; this }

    EntityRef<Matrix> matrix(final Map<String, Object> args, final String matrixId = null,
                             @DelegatesTo(value = MatrixBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = matrixId ?: (args?.get('matrixId') as String) ?: (args?.get('id') as String)
        MatrixBuilder builder = new MatrixBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        EntityRef<Matrix> ref = builder.build()

        String dataId = "${modelId}_Data_${id}"
        Map<String, Object> dataValues = [
            mathModelDataId: dataId,
            mathModelId: modelId,
            matrixId: id,
            dataTypeEnumId: MathModelDataType.Matrix.id,
            sequenceNum: (long) (dataSequence++)
        ]
        mathMeta.declare('moqui.math.MathModelData', dataId, dataValues)
        ref
    }

    EntityRef<Matrix> matrix(final String matrixId,
                             @DelegatesTo(value = MatrixBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        matrix(null, matrixId, closure)
    }

    EntityRef<Vector> vector(final Map<String, Object> args, final String vectorId = null,
                             @DelegatesTo(value = VectorBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = vectorId ?: (args?.get('vectorId') as String) ?: (args?.get('id') as String)
        VectorBuilder builder = new VectorBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        EntityRef<Vector> ref = builder.build()

        String dataId = "${modelId}_Data_${id}"
        Map<String, Object> dataValues = [
            mathModelDataId: dataId,
            mathModelId: modelId,
            vectorId: id,
            dataTypeEnumId: MathModelDataType.Vector.id,
            sequenceNum: (long) (dataSequence++)
        ]
        mathMeta.declare('moqui.math.MathModelData', dataId, dataValues)
        ref
    }

    EntityRef<Vector> vector(final String vectorId,
                             @DelegatesTo(value = VectorBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        vector(null, vectorId, closure)
    }

    EntityRef<Tensor> tensor(final Map<String, Object> args, final String tensorId = null,
                             @DelegatesTo(value = TensorBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = tensorId ?: (args?.get('tensorId') as String) ?: (args?.get('id') as String)
        TensorBuilder builder = new TensorBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        EntityRef<Tensor> ref = builder.build()

        String dataId = "${modelId}_Data_${id}"
        Map<String, Object> dataValues = [
            mathModelDataId: dataId,
            mathModelId: modelId,
            tensorId: id,
            dataTypeEnumId: MathModelDataType.Tensor.id,
            sequenceNum: (long) (dataSequence++)
        ]
        mathMeta.declare('moqui.math.MathModelData', dataId, dataValues)
        ref
    }

    EntityRef<Tensor> tensor(final String tensorId,
                             @DelegatesTo(value = TensorBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        tensor(null, tensorId, closure)
    }

    EntityRef<Transformation> transformation(final Map<String, Object> args, final String transformationId = null,
                                             @DelegatesTo(value = TransformationBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        String id = transformationId ?: (args?.get('transformationId') as String) ?: (args?.get('id') as String)
        TransformationBuilder builder = new TransformationBuilder(mathMeta, id)
        if (args) builder.applyArgs(args)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = builder
            copy.call()
        }
        EntityRef<Transformation> ref = builder.build()

        String stepSeqId = String.format("%02d", dataSequence)
        Map<String, Object> pipelineValues = [
            mathModelDefId: defId,
            stepSeqId: stepSeqId,
            stepName: builder.name ?: id,
            transformationId: id,
            sequenceNum: (long) (dataSequence++)
        ]
        if (solvingMethod) pipelineValues.put('solvingMethodEnumId', solvingMethod.id)
        mathMeta.declare('moqui.math.MathModelDefPipeline', "${defId}_${stepSeqId}", pipelineValues)
        ref
    }

    EntityRef<Transformation> transformation(final String transformationId,
                                             @DelegatesTo(value = TransformationBuilder, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure = null) {
        transformation(null, transformationId, closure)
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
        mathMeta.declare('moqui.math.MathModel', modelId, values)
        if (location) {
            String contentId = "${defId}_Content"
            Map<String, Object> contentValues = new LinkedHashMap<>()
            contentValues.put('mathModelDefContentId', contentId)
            contentValues.put('mathModelDefId', defId)
            contentValues.put('contentLocation', location)
            if (contentType != null) contentValues.put('contentTypeEnumId', contentType.id)
            else if (location.endsWith('.onnx')) contentValues.put('contentTypeEnumId', 'MmCntOnnx')
            else if (location.endsWith('.pt') || location.endsWith('.pth')) contentValues.put('contentTypeEnumId', 'MmCntTorchScript')
            if (contentPurpose != null) contentValues.put('contentPurposeEnumId', contentPurpose.id)
            else contentValues.put('contentPurposeEnumId', 'MmdcpFullModel')
            mathMeta.declare('moqui.math.MathModelDefContent', contentId, contentValues)
        }
        new EntityRef<>(modelId, MathModel.class, values)
    }
}

@CompileStatic
class MatrixBuilder {
    final MathMeta mathMeta
    final String matrixId
    Long rows
    Long cols
    MatrixType matrixType
    MatrixPurpose purpose
    MathSpace domainSpace
    MathSpace codomainSpace
    String componentArray
    String name
    String symbol
    String description

    MatrixBuilder(final MathMeta mathMeta, final String matrixId) {
        this.mathMeta = mathMeta
        this.matrixId = matrixId
    }

    MatrixBuilder rows(long r) { this.rows = r; this }
    MatrixBuilder rows(int r) { this.rows = (long) r; this }
    MatrixBuilder cols(long c) { this.cols = c; this }
    MatrixBuilder cols(int c) { this.cols = (long) c; this }
    MatrixBuilder matrixType(MatrixType t) { this.matrixType = t; this }
    MatrixBuilder type(MatrixType t) { this.matrixType = t; this }
    MatrixBuilder purpose(MatrixPurpose p) { this.purpose = p; this }
    MatrixBuilder purpose(Object p) {
        if (p instanceof MatrixPurpose) this.purpose = (MatrixPurpose) p
        else if (p instanceof org.moqui.math.dsl.DslDeferredSymbol) {
            String symName = ((org.moqui.math.dsl.DslDeferredSymbol) p).name
            this.purpose = MatrixPurpose.values().find { it.name() == symName || it.id == symName }
        } else if (p instanceof org.moqui.math.dsl.DslSymbol) {
            String symName = ((org.moqui.math.dsl.DslSymbol) p).name
            this.purpose = MatrixPurpose.values().find { it.name() == symName || it.id == ((org.moqui.math.dsl.DslSymbol) p).id }
        } else if (p instanceof DslEnumValue) {
            String symId = ((DslEnumValue) p).id
            this.purpose = MatrixPurpose.values().find { it.name() == ((Enum) p).name() || it.id == symId }
        } else if (p instanceof String) {
            this.purpose = MatrixPurpose.values().find { it.name() == p || it.id == p }
        }
        this
    }
    MatrixBuilder domainSpace(MathSpace s) { this.domainSpace = s; this }
    MatrixBuilder domainSpace(Object s) {
        if (s instanceof MathSpace) this.domainSpace = (MathSpace) s
        else if (s instanceof org.moqui.math.dsl.DslDeferredSymbol) {
            String symName = ((org.moqui.math.dsl.DslDeferredSymbol) s).name
            this.domainSpace = MathSpace.values().find { it.name() == symName || it.id == symName }
        } else if (s instanceof org.moqui.math.dsl.DslSymbol) {
            String symName = ((org.moqui.math.dsl.DslSymbol) s).name
            this.domainSpace = MathSpace.values().find { it.name() == symName || it.id == ((org.moqui.math.dsl.DslSymbol) s).id }
        } else if (s instanceof DslEnumValue) {
            String symId = ((DslEnumValue) s).id
            this.domainSpace = MathSpace.values().find { it.name() == ((Enum) s).name() || it.id == symId }
        } else if (s instanceof String) {
            this.domainSpace = MathSpace.values().find { it.name() == s || it.id == s }
        }
        this
    }
    MatrixBuilder codomainSpace(MathSpace s) { this.codomainSpace = s; this }
    MatrixBuilder codomainSpace(Object s) {
        if (s instanceof MathSpace) this.codomainSpace = (MathSpace) s
        else if (s instanceof org.moqui.math.dsl.DslDeferredSymbol) {
            String symName = ((org.moqui.math.dsl.DslDeferredSymbol) s).name
            this.codomainSpace = MathSpace.values().find { it.name() == symName || it.id == symName }
        } else if (s instanceof org.moqui.math.dsl.DslSymbol) {
            String symName = ((org.moqui.math.dsl.DslSymbol) s).name
            this.codomainSpace = MathSpace.values().find { it.name() == symName || it.id == ((org.moqui.math.dsl.DslSymbol) s).id }
        } else if (s instanceof DslEnumValue) {
            String symId = ((DslEnumValue) s).id
            this.codomainSpace = MathSpace.values().find { it.name() == ((Enum) s).name() || it.id == symId }
        } else if (s instanceof String) {
            this.codomainSpace = MathSpace.values().find { it.name() == s || it.id == s }
        }
        this
    }
    MatrixBuilder componentArray(String s) { this.componentArray = s; this }
    MatrixBuilder componentArray(Object obj) {
        this.componentArray = obj instanceof String ? (String) obj : JsonOutput.toJson(obj)
        this
    }
    MatrixBuilder data(Object obj) {
        if (obj instanceof List) {
            List<?> list = (List<?>) obj
            if (!list.isEmpty() && list.get(0) instanceof List) {
                if (rows == null) rows = (long) list.size()
                if (cols == null) cols = (long) ((List<?>) list.get(0)).size()
                if (domainSpace == null) {
                    domainSpace = cols == 3L ? MathSpace.R3 : MathSpace.R2
                }
                if (codomainSpace == null) {
                    codomainSpace = rows == 3L ? MathSpace.R3 : MathSpace.R2
                }
            }
        }
        componentArray(obj)
    }
    String contentLocation
    String contentTypeEnumId
    DslEnumValue contentType

    MatrixBuilder contentLocation(String loc) { this.contentLocation = loc; this }
    MatrixBuilder contentType(DslEnumValue type) { this.contentType = type; this }
    MatrixBuilder contentType(String typeId) { this.contentTypeEnumId = typeId; this }
    MatrixBuilder content(String loc, DslEnumValue type = null) {
        this.contentLocation = loc
        this.contentType = type
        this
    }
    MatrixBuilder content(Map<String, Object> args) {
        if (args?.containsKey('location')) this.contentLocation = args.location?.toString()
        if (args?.containsKey('type')) {
            Object t = args.type
            if (t instanceof DslEnumValue) this.contentType = (DslEnumValue) t
            else if (t != null) this.contentTypeEnumId = t.toString()
        }
        this
    }
    MatrixBuilder name(String n) { this.name = n; this }
    MatrixBuilder symbol(String s) { this.symbol = s; this }
    MatrixBuilder description(String d) { this.description = d; this }

    MatrixBuilder applyArgs(Map<String, Object> args) {
        if (args.containsKey('rows')) rows(args.rows as Number)
        if (args.containsKey('cols')) cols(args.cols as Number)
        if (args.containsKey('purpose')) purpose(args.purpose)
        if (args.containsKey('domainSpace')) domainSpace(args.domainSpace)
        if (args.containsKey('codomainSpace')) codomainSpace(args.codomainSpace)
        if (args.containsKey('componentArray')) componentArray(args.componentArray)
        if (args.containsKey('data')) data(args.data)
        if (args.containsKey('contentLocation')) contentLocation(args.contentLocation as String)
        if (args.containsKey('content')) {
            if (args.content instanceof Map) content(args.content as Map<String, Object>)
            else content(args.content as String)
        }
        if (args.containsKey('name')) name(args.name as String)
        if (args.containsKey('symbol')) symbol(args.symbol as String)
        if (args.containsKey('description')) description(args.description as String)
        this
    }

    private void rows(Number n) { if (n != null) this.rows = n.longValue() }
    private void cols(Number n) { if (n != null) this.cols = n.longValue() }

    EntityRef<Matrix> build() {
        if (contentLocation && (rows == null || cols == null)) {
            File f = new File(contentLocation)
            if (f.exists() && f.name.endsWith('.npy')) {
                try {
                    org.moqui.math.memory.NpyReader.NpyHeader hdr = org.moqui.math.memory.NpyReader.parseHeader(f.toPath())
                    if (hdr.shape.size() >= 2) {
                        if (rows == null) rows = hdr.shape.get(0)
                        if (cols == null) cols = hdr.shape.get(1)
                    }
                } catch (Exception ignored) {}
            }
        }
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('matrixId', matrixId)
        values.put('rows', rows != null ? rows : 1L)
        values.put('cols', cols != null ? cols : 1L)
        values.put('matrixTypeEnumId', matrixType ? matrixType.id : 'MtDense')
        values.put('purposeEnumId', purpose ? purpose.id : 'MpOriginal')
        if (domainSpace == null && cols != null) {
            if (cols == 3L) domainSpace = MathSpace.R3
            else if (cols == 2L) domainSpace = MathSpace.R2
        }
        if (codomainSpace == null && rows != null) {
            if (rows == 3L) codomainSpace = MathSpace.R3
            else if (rows == 2L) codomainSpace = MathSpace.R2
        }
        values.put('domainSpaceEnumId', domainSpace ? domainSpace.id : 'Eng2DEuclideanSpace')
        values.put('codomainSpaceEnumId', codomainSpace ? codomainSpace.id : 'Eng2DEuclideanSpace')
        if (componentArray) values.put('componentArray', componentArray)
        if (name) values.put('name', name)
        else if (matrixId) values.put('name', matrixId)
        if (symbol) values.put('symbol', symbol)
        if (description) values.put('description', description)
        mathMeta.declare('moqui.math.Matrix', matrixId, values)
        if (contentLocation) {
            Map<String, Object> contentValues = new LinkedHashMap<>()
            String contentId = "${matrixId}_Content"
            contentValues.put('matrixContentId', contentId)
            contentValues.put('matrixId', matrixId)
            contentValues.put('contentLocation', contentLocation)
            if (contentType) contentValues.put('contentTypeEnumId', contentType.id)
            else if (contentTypeEnumId) contentValues.put('contentTypeEnumId', contentTypeEnumId)
            else if (contentLocation.endsWith('.npy')) contentValues.put('contentTypeEnumId', 'MCntNpy')
            mathMeta.declare('moqui.math.MatrixContent', contentId, contentValues)
        }
        new EntityRef<>(matrixId, Matrix.class, values)
    }
}

@CompileStatic
class VectorBuilder {
    final MathMeta mathMeta
    final String vectorId
    Long size
    DslEnumValue purpose
    MathSpace domainSpace
    MathSpace codomainSpace
    String componentArray
    String name
    String symbol
    String description

    VectorBuilder(final MathMeta mathMeta, final String vectorId) {
        this.mathMeta = mathMeta
        this.vectorId = vectorId
    }

    VectorBuilder size(long s) { this.size = s; this }
    VectorBuilder size(int s) { this.size = (long) s; this }
    VectorBuilder purpose(DslEnumValue p) { this.purpose = p; this }
    VectorBuilder domainSpace(MathSpace s) { this.domainSpace = s; this }
    VectorBuilder codomainSpace(MathSpace s) { this.codomainSpace = s; this }
    VectorBuilder componentArray(String s) { this.componentArray = s; this }
    VectorBuilder componentArray(Object obj) {
        this.componentArray = obj instanceof String ? (String) obj : JsonOutput.toJson(obj)
        this
    }
    VectorBuilder data(Object obj) { componentArray(obj) }
    String contentLocation
    String contentTypeEnumId
    DslEnumValue contentType

    VectorBuilder contentLocation(String loc) { this.contentLocation = loc; this }
    VectorBuilder contentType(DslEnumValue type) { this.contentType = type; this }
    VectorBuilder contentType(String typeId) { this.contentTypeEnumId = typeId; this }
    VectorBuilder content(String loc, DslEnumValue type = null) {
        this.contentLocation = loc
        this.contentType = type
        this
    }
    VectorBuilder content(Map<String, Object> args) {
        if (args?.containsKey('location')) this.contentLocation = args.location?.toString()
        if (args?.containsKey('type')) {
            Object t = args.type
            if (t instanceof DslEnumValue) this.contentType = (DslEnumValue) t
            else if (t != null) this.contentTypeEnumId = t.toString()
        }
        this
    }
    VectorBuilder name(String n) { this.name = n; this }
    VectorBuilder symbol(String s) { this.symbol = s; this }
    VectorBuilder description(String d) { this.description = d; this }

    VectorBuilder applyArgs(Map<String, Object> args) {
        if (args.containsKey('size')) size(args.size as Number)
        if (args.containsKey('purpose')) purpose(args.purpose as DslEnumValue)
        if (args.containsKey('domainSpace')) domainSpace(args.domainSpace as MathSpace)
        if (args.containsKey('codomainSpace')) codomainSpace(args.codomainSpace as MathSpace)
        if (args.containsKey('componentArray')) componentArray(args.componentArray)
        if (args.containsKey('data')) data(args.data)
        if (args.containsKey('contentLocation')) contentLocation(args.contentLocation as String)
        if (args.containsKey('content')) {
            if (args.content instanceof Map) content(args.content as Map<String, Object>)
            else content(args.content as String)
        }
        if (args.containsKey('name')) name(args.name as String)
        if (args.containsKey('symbol')) symbol(args.symbol as String)
        if (args.containsKey('description')) description(args.description as String)
        this
    }

    private void size(Number n) { if (n != null) this.size = n.longValue() }

    EntityRef<Vector> build() {
        if (contentLocation && size == null) {
            File f = new File(contentLocation)
            if (f.exists() && f.name.endsWith('.npy')) {
                try {
                    org.moqui.math.memory.NpyReader.NpyHeader hdr = org.moqui.math.memory.NpyReader.parseHeader(f.toPath())
                    if (!hdr.shape.isEmpty()) size = hdr.shape.get(0)
                } catch (Exception ignored) {}
            }
        }
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('vectorId', vectorId)
        values.put('dimension', size != null ? size : 1L)
        values.put('purposeEnumId', purpose ? purpose.id : 'VpOriginal')
        values.put('vectorSpaceEnumId', domainSpace ? domainSpace.id : 'Eng2DEuclideanSpace')
        if (componentArray) values.put('componentArray', componentArray)
        if (name) values.put('name', name)
        else if (vectorId) values.put('name', vectorId)
        if (symbol) values.put('symbol', symbol)
        if (description) values.put('description', description)
        mathMeta.declare('moqui.math.Vector', vectorId, values)
        if (contentLocation) {
            Map<String, Object> contentValues = new LinkedHashMap<>()
            String contentId = "${vectorId}_Content"
            contentValues.put('vectorContentId', contentId)
            contentValues.put('vectorId', vectorId)
            contentValues.put('contentLocation', contentLocation)
            if (contentType) contentValues.put('contentTypeEnumId', contentType.id)
            else if (contentTypeEnumId) contentValues.put('contentTypeEnumId', contentTypeEnumId)
            else if (contentLocation.endsWith('.npy')) contentValues.put('contentTypeEnumId', 'VCntNpy')
            mathMeta.declare('moqui.math.VectorContent', contentId, contentValues)
        }
        new EntityRef<>(vectorId, Vector.class, values)
    }
}

@CompileStatic
class TensorBuilder {
    final MathMeta mathMeta
    final String tensorId
    Long rank
    String shape
    DslEnumValue purpose
    DslEnumValue dataType
    DslEnumValue device
    String componentArray
    String contentLocation
    String contentTypeEnumId
    DslEnumValue contentType
    String name
    String symbol
    String description
    Long tensorSize

    TensorBuilder(final MathMeta mathMeta, final String tensorId) {
        this.mathMeta = mathMeta
        this.tensorId = tensorId
    }

    TensorBuilder rank(long r) { this.rank = r; this }
    TensorBuilder rank(int r) { this.rank = (long) r; this }
    TensorBuilder shape(String s) { this.shape = s; this }
    TensorBuilder shape(List<Integer> dims) { this.shape = JsonOutput.toJson(dims); this }
    TensorBuilder purpose(DslEnumValue p) { this.purpose = p; this }
    TensorBuilder dataType(DslEnumValue dt) { this.dataType = dt; this }
    TensorBuilder device(DslEnumValue dev) { this.device = dev; this }
    TensorBuilder componentArray(String s) { this.componentArray = s; this }
    TensorBuilder componentArray(Object obj) {
        this.componentArray = obj instanceof String ? (String) obj : JsonOutput.toJson(obj)
        this
    }
    TensorBuilder data(Object obj) { componentArray(obj) }
    TensorBuilder contentLocation(String loc) { this.contentLocation = loc; this }
    TensorBuilder contentType(DslEnumValue type) { this.contentType = type; this }
    TensorBuilder contentType(String typeId) { this.contentTypeEnumId = typeId; this }
    TensorBuilder content(String loc, DslEnumValue type = null) {
        this.contentLocation = loc
        this.contentType = type
        this
    }
    TensorBuilder content(Map<String, Object> args) {
        if (args?.containsKey('location')) this.contentLocation = args.location?.toString()
        if (args?.containsKey('type')) {
            Object t = args.type
            if (t instanceof DslEnumValue) this.contentType = (DslEnumValue) t
            else if (t != null) this.contentTypeEnumId = t.toString()
        }
        this
    }
    TensorBuilder name(String n) { this.name = n; this }
    TensorBuilder symbol(String s) { this.symbol = s; this }
    TensorBuilder description(String d) { this.description = d; this }
    TensorBuilder size(long s) { this.tensorSize = s; this }
    TensorBuilder size(int s) { this.tensorSize = (long) s; this }
    private void rank(Number n) { if (n != null) this.rank = n.longValue() }
    private void size(Number n) { if (n != null) this.tensorSize = n.longValue() }

    TensorBuilder applyArgs(Map<String, Object> args) {
        if (args.containsKey('rank')) rank(args.rank as Number)
        if (args.containsKey('shape')) {
            if (args.shape instanceof List) shape(args.shape as List<Integer>)
            else shape(args.shape as String)
        }
        if (args.containsKey('purpose')) purpose(args.purpose as DslEnumValue)
        if (args.containsKey('dataType')) dataType(args.dataType as DslEnumValue)
        if (args.containsKey('device')) device(args.device as DslEnumValue)
        if (args.containsKey('componentArray')) componentArray(args.componentArray)
        if (args.containsKey('data')) data(args.data)
        if (args.containsKey('contentLocation')) contentLocation(args.contentLocation as String)
        if (args.containsKey('content')) {
            if (args.content instanceof Map) content(args.content as Map<String, Object>)
            else content(args.content as String)
        }
        if (args.containsKey('name')) name(args.name as String)
        if (args.containsKey('symbol')) symbol(args.symbol as String)
        if (args.containsKey('description')) description(args.description as String)
        if (args.containsKey('size')) size(args.size as Number)
        this
    }

    EntityRef<Tensor> build() {
        if (contentLocation && (shape == null || rank == null)) {
            File f = new File(contentLocation)
            if (f.exists() && f.name.endsWith('.npy')) {
                try {
                    org.moqui.math.memory.NpyReader.NpyHeader hdr = org.moqui.math.memory.NpyReader.parseHeader(f.toPath())
                    if (!hdr.shape.isEmpty()) {
                        if (rank == null) rank = (long) hdr.shape.size()
                        if (shape == null) shape = JsonOutput.toJson(hdr.shape)
                        if (tensorSize == null) tensorSize = hdr.totalElements
                    }
                } catch (Exception ignored) {}
            }
        }
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('tensorId', tensorId)
        long r = rank != null ? rank : 2L
        values.put('rank', r)
        if (shape) values.put('shape', shape)
        else {
            List<Integer> defaultShape = []
            for (int i = 0; i < r; i++) defaultShape.add(1)
            values.put('shape', JsonOutput.toJson(defaultShape))
        }
        if (tensorSize != null) values.put('size', tensorSize)
        if (purpose) values.put('purposeEnumId', purpose.id)
        if (dataType) values.put('dataTypeEnumId', dataType.id)
        if (device) values.put('deviceEnumId', device.id)
        if (name) values.put('name', name)
        else if (tensorId) values.put('name', tensorId)
        if (symbol) values.put('symbol', symbol)
        if (description) values.put('description', description)
        mathMeta.declare('moqui.math.Tensor', tensorId, values)
        if (contentLocation) {
            Map<String, Object> contentValues = new LinkedHashMap<>()
            String contentId = "${tensorId}_Content"
            contentValues.put('tensorContentId', contentId)
            contentValues.put('tensorId', tensorId)
            contentValues.put('contentLocation', contentLocation)
            if (contentType) contentValues.put('contentTypeEnumId', contentType.id)
            else if (contentTypeEnumId) contentValues.put('contentTypeEnumId', contentTypeEnumId)
            else if (contentLocation.endsWith('.npy')) contentValues.put('contentTypeEnumId', 'TCntNpy')
            mathMeta.declare('moqui.math.TensorContent', contentId, contentValues)
        }
        new EntityRef<>(tensorId, Tensor.class, values)
    }
}

@CompileStatic
class TransformationBuilder {
    final MathMeta mathMeta
    final String transformationId
    String name
    String symbol
    String description
    String parentTransformationId
    TransformationType type
    TransformationPurpose purpose

    String resultMatrixId
    String resultVectorId
    String resultTensorId
    String resultParameterId
    String resultFunctionId

    private int operandSequence = 0

    TransformationBuilder(final MathMeta mathMeta, final String transformationId) {
        this.mathMeta = mathMeta
        this.transformationId = transformationId
    }

    TransformationBuilder name(String n) { this.name = n; this }
    TransformationBuilder symbol(String s) { this.symbol = s; this }
    TransformationBuilder description(String d) { this.description = d; this }
    TransformationBuilder type(TransformationType t) { if (t != null) this.type = t; this }
    TransformationBuilder type(Object t) {
        if (t instanceof TransformationType) {
            this.type = (TransformationType) t
        } else if (t instanceof org.moqui.math.dsl.DslDeferredSymbol) {
            String symName = ((org.moqui.math.dsl.DslDeferredSymbol) t).name
            TransformationType match = TransformationType.values().find { it.name() == symName || it.id == symName }
            if (match != null) this.type = match
        } else if (t instanceof org.moqui.math.dsl.DslSymbol) {
            String symName = ((org.moqui.math.dsl.DslSymbol) t).name
            TransformationType match = TransformationType.values().find { it.name() == symName || it.id == ((org.moqui.math.dsl.DslSymbol) t).id }
            if (match != null) this.type = match
        } else if (t instanceof DslEnumValue) {
            String symId = ((DslEnumValue) t).id
            TransformationType match = TransformationType.values().find { it.name() == ((Enum) t).name() || it.id == symId }
            if (match != null) this.type = match
        } else if (t instanceof String) {
            TransformationType match = TransformationType.values().find { it.name() == t || it.id == t }
            if (match != null) this.type = match
        }
        this
    }
    TransformationBuilder purpose(TransformationPurpose p) { this.purpose = p; this }
    TransformationBuilder parentTransformation(String pId) { this.parentTransformationId = pId; this }
    TransformationBuilder parentTransformation(EntityRef<Transformation> ref) { this.parentTransformationId = ref.id; this }
    TransformationBuilder parent(String pId) { this.parentTransformationId = pId; this }
    TransformationBuilder parent(EntityRef<Transformation> ref) { this.parentTransformationId = ref.id; this }

    // Results
    TransformationBuilder resultMatrix(String mId) { this.resultMatrixId = mId; this }
    TransformationBuilder resultMatrix(EntityRef<Matrix> ref) { this.resultMatrixId = ref.id; this }
    TransformationBuilder resultVector(String vId) { this.resultVectorId = vId; this }
    TransformationBuilder resultVector(EntityRef<Vector> ref) { this.resultVectorId = ref.id; this }
    TransformationBuilder resultTensor(String tId) { this.resultTensorId = tId; this }
    TransformationBuilder resultTensor(EntityRef<Tensor> ref) { this.resultTensorId = ref.id; this }
    TransformationBuilder resultParameter(String pId) { this.resultParameterId = pId; this }
    TransformationBuilder resultParameter(EntityRef<Parameter> ref) { this.resultParameterId = ref.id; this }
    TransformationBuilder resultFunction(String fId) { this.resultFunctionId = fId; this }

    // Operands
    TransformationBuilder operand(Map<String, Object> values) {
        int idx = operandSequence++
        Map<String, Object> opValues = new LinkedHashMap<>(values)
        opValues.put('transformationId', transformationId)
        if (!opValues.containsKey('operandIndex')) {
            opValues.put('operandIndex', (long) idx)
        }
        String opKey = "${transformationId}_Op_${idx}"
        mathMeta.declare('moqui.math.TransformationOperand', opKey, opValues)
        this
    }

    TransformationBuilder leftMatrix(String matrixId) {
        operand([operandTypeEnumId: 'TotLeftMatrix', operandMatrixId: matrixId])
    }
    TransformationBuilder leftMatrix(EntityRef<Matrix> ref) { leftMatrix(ref.id) }

    TransformationBuilder rightMatrix(String matrixId) {
        operand([operandTypeEnumId: 'TotRightMatrix', operandMatrixId: matrixId])
    }
    TransformationBuilder rightMatrix(EntityRef<Matrix> ref) { rightMatrix(ref.id) }

    TransformationBuilder operandMatrix(String matrixId) {
        operand([operandTypeEnumId: 'TotMatrix', operandMatrixId: matrixId])
    }
    TransformationBuilder operandMatrix(EntityRef<Matrix> ref) { operandMatrix(ref.id) }

    TransformationBuilder leftVector(String vectorId) {
        operand([operandTypeEnumId: 'TotLeftVector', operandVectorId: vectorId])
    }
    TransformationBuilder leftVector(EntityRef<Vector> ref) { leftVector(ref.id) }

    TransformationBuilder rightVector(String vectorId) {
        operand([operandTypeEnumId: 'TotRightVector', operandVectorId: vectorId])
    }
    TransformationBuilder rightVector(EntityRef<Vector> ref) { rightVector(ref.id) }

    TransformationBuilder operandVector(String vectorId) {
        operand([operandTypeEnumId: 'TotVector', operandVectorId: vectorId])
    }
    TransformationBuilder operandVector(EntityRef<Vector> ref) { operandVector(ref.id) }

    TransformationBuilder leftTensor(String tensorId) {
        operand([operandTypeEnumId: 'TotLeftTensor', operandTensorId: tensorId])
    }
    TransformationBuilder leftTensor(EntityRef<Tensor> ref) { leftTensor(ref.id) }

    TransformationBuilder rightTensor(String tensorId) {
        operand([operandTypeEnumId: 'TotRightTensor', operandTensorId: tensorId])
    }
    TransformationBuilder rightTensor(EntityRef<Tensor> ref) { rightTensor(ref.id) }

    TransformationBuilder operandTensor(String tensorId) {
        operand([operandTypeEnumId: 'TotTensor', operandTensorId: tensorId])
    }
    TransformationBuilder operandTensor(EntityRef<Tensor> ref) { operandTensor(ref.id) }

    TransformationBuilder kernelMatrix(String matrixId) {
        operand([operandTypeEnumId: 'TotKernelMatrix', operandMatrixId: matrixId])
    }
    TransformationBuilder kernelMatrix(EntityRef<Matrix> ref) { kernelMatrix(ref.id) }

    TransformationBuilder biasMatrix(String matrixId) {
        operand([operandTypeEnumId: 'TotBiasMatrix', operandMatrixId: matrixId])
    }
    TransformationBuilder biasMatrix(EntityRef<Matrix> ref) { biasMatrix(ref.id) }

    TransformationBuilder kernelVector(String vectorId) {
        operand([operandTypeEnumId: 'TotKernelVector', operandVectorId: vectorId])
    }
    TransformationBuilder kernelVector(EntityRef<Vector> ref) { kernelVector(ref.id) }

    TransformationBuilder biasVector(String vectorId) {
        operand([operandTypeEnumId: 'TotBiasVector', operandVectorId: vectorId])
    }
    TransformationBuilder biasVector(EntityRef<Vector> ref) { biasVector(ref.id) }

    TransformationBuilder operandTransformation(String transId) {
        operand([operandTypeEnumId: 'TotTransformation', operandTransformationId: transId])
    }
    TransformationBuilder operandTransformation(EntityRef<Transformation> ref) { operandTransformation(ref.id) }

    TransformationBuilder leftTransformation(String transId) {
        operand([operandTypeEnumId: 'TotLeft', operandTransformationId: transId])
    }
    TransformationBuilder leftTransformation(EntityRef<Transformation> ref) { leftTransformation(ref.id) }

    TransformationBuilder rightTransformation(String transId) {
        operand([operandTypeEnumId: 'TotRight', operandTransformationId: transId])
    }
    TransformationBuilder rightTransformation(EntityRef<Transformation> ref) { rightTransformation(ref.id) }

    TransformationBuilder operandParameter(String paramId) {
        operand([operandTypeEnumId: 'TotParameter', operandParameterId: paramId])
    }
    TransformationBuilder operandParameter(EntityRef<Parameter> ref) { operandParameter(ref.id) }

    // Nested satellites
    EntityRef<MatrixDecomposition> matrixDecomposition(@DelegatesTo(value = MatrixDecompositionBuilder, strategy = Closure.DELEGATE_FIRST) Closure<?> closure = null) {
        MatrixDecompositionBuilder b = new MatrixDecompositionBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = b
            copy.call()
        }
        b.build()
    }

    EntityRef<DiagonalExtraction> diagonalExtraction(@DelegatesTo(value = DiagonalExtractionBuilder, strategy = Closure.DELEGATE_FIRST) Closure<?> closure = null) {
        DiagonalExtractionBuilder b = new DiagonalExtractionBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = b
            copy.call()
        }
        b.build()
    }

    EntityRef<TriangularExtraction> triangularExtraction(@DelegatesTo(value = TriangularExtractionBuilder, strategy = Closure.DELEGATE_FIRST) Closure<?> closure = null) {
        TriangularExtractionBuilder b = new TriangularExtractionBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = b
            copy.call()
        }
        b.build()
    }

    EntityRef<BandExtraction> bandExtraction(@DelegatesTo(value = BandExtractionBuilder, strategy = Closure.DELEGATE_FIRST) Closure<?> closure = null) {
        BandExtractionBuilder b = new BandExtractionBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = b
            copy.call()
        }
        b.build()
    }

    EntityRef<BlockMatrixExtraction> blockMatrixExtraction(@DelegatesTo(value = BlockMatrixExtractionBuilder, strategy = Closure.DELEGATE_FIRST) Closure<?> closure = null) {
        BlockMatrixExtractionBuilder b = new BlockMatrixExtractionBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = b
            copy.call()
        }
        b.build()
    }

    EntityRef<TensorSlice> tensorSlice(@DelegatesTo(value = TensorSliceBuilder, strategy = Closure.DELEGATE_FIRST) Closure<?> closure = null) {
        TensorSliceBuilder b = new TensorSliceBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = b
            copy.call()
        }
        b.build()
    }

    EntityRef<TensorDecomposition> tensorDecomposition(@DelegatesTo(value = TensorDecompositionBuilder, strategy = Closure.DELEGATE_FIRST) Closure<?> closure = null) {
        TensorDecompositionBuilder b = new TensorDecompositionBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = b
            copy.call()
        }
        b.build()
    }

    EntityRef<NormResult> normResult(@DelegatesTo(value = NormResultBuilder, strategy = Closure.DELEGATE_FIRST) Closure<?> closure = null) {
        NormResultBuilder b = new NormResultBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = b
            copy.call()
        }
        b.build()
    }

    EntityRef<CoordinateSystemTransformation> coordinateSystemTransformation(@DelegatesTo(value = CoordinateSystemTransformationBuilder, strategy = Closure.DELEGATE_FIRST) Closure<?> closure = null) {
        CoordinateSystemTransformationBuilder b = new CoordinateSystemTransformationBuilder(mathMeta, transformationId)
        if (closure) {
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = b
            copy.call()
        }
        b.build()
    }

    TransformationBuilder applyArgs(Map<String, Object> args) {
        if (args.containsKey('name')) name(args.name as String)
        if (args.containsKey('symbol')) symbol(args.symbol as String)
        if (args.containsKey('description')) description(args.description as String)
        if (args.containsKey('type')) type(args.type)
        if (args.containsKey('purpose')) {
            Object p = args.purpose
            if (p instanceof TransformationPurpose) purpose((TransformationPurpose) p)
            else if (p instanceof String) purpose(TransformationPurpose.valueOf((String) p))
        }
        if (args.containsKey('parentTransformation')) parentTransformation(args.parentTransformation as String)
        if (args.containsKey('parent')) parent(args.parent as String)

        if (args.containsKey('resultMatrix')) resultMatrix(args.resultMatrix as String)
        if (args.containsKey('resultVector')) resultVector(args.resultVector as String)
        if (args.containsKey('resultTensor')) resultTensor(args.resultTensor as String)
        if (args.containsKey('resultParameter')) resultParameter(args.resultParameter as String)
        if (args.containsKey('resultFunction')) resultFunction(args.resultFunction as String)

        if (args.containsKey('leftMatrix')) leftMatrix(args.leftMatrix as String)
        if (args.containsKey('rightMatrix')) rightMatrix(args.rightMatrix as String)
        if (args.containsKey('operandMatrix')) operandMatrix(args.operandMatrix as String)
        if (args.containsKey('leftVector')) leftVector(args.leftVector as String)
        if (args.containsKey('rightVector')) rightVector(args.rightVector as String)
        if (args.containsKey('operandVector')) operandVector(args.operandVector as String)
        if (args.containsKey('leftTensor')) leftTensor(args.leftTensor as String)
        if (args.containsKey('rightTensor')) rightTensor(args.rightTensor as String)
        if (args.containsKey('operandTensor')) operandTensor(args.operandTensor as String)
        if (args.containsKey('kernelMatrix')) kernelMatrix(args.kernelMatrix as String)
        if (args.containsKey('biasMatrix')) biasMatrix(args.biasMatrix as String)
        if (args.containsKey('kernelVector')) kernelVector(args.kernelVector as String)
        if (args.containsKey('biasVector')) biasVector(args.biasVector as String)
        if (args.containsKey('operandTransformation')) operandTransformation(args.operandTransformation as String)
        if (args.containsKey('leftTransformation')) leftTransformation(args.leftTransformation as String)
        if (args.containsKey('rightTransformation')) rightTransformation(args.rightTransformation as String)
        if (args.containsKey('operandParameter')) operandParameter(args.operandParameter as String)
        this
    }

    EntityRef<Transformation> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        if (name) values.put('name', name)
        if (symbol) values.put('symbol', symbol)
        if (description) values.put('description', description)
        if (type) values.put('transformationTypeEnumId', type.id)
        if (purpose) values.put('purposeEnumId', purpose.id)
        if (parentTransformationId) values.put('parentTransformationId', parentTransformationId)

        if (resultMatrixId) values.put('resultMatrixId', resultMatrixId)
        if (resultVectorId) values.put('resultVectorId', resultVectorId)
        if (resultTensorId) values.put('resultTensorId', resultTensorId)
        if (resultParameterId) values.put('resultParameterId', resultParameterId)
        if (resultFunctionId) values.put('resultFunctionId', resultFunctionId)

        mathMeta.declare('moqui.math.Transformation', transformationId, values)
        new EntityRef<>(transformationId, Transformation.class, values)
    }
}

@CompileStatic
abstract class SatelliteBuilderBase {
    final MathMeta mathMeta
    final String transformationId
    protected int operandSequence = 0

    SatelliteBuilderBase(final MathMeta mathMeta, final String transformationId) {
        this.mathMeta = mathMeta
        this.transformationId = transformationId
    }

    // Results on Transformation
    void resultMatrix(String mId) { updateTransformation('resultMatrixId', mId) }
    void resultMatrix(EntityRef<Matrix> ref) { resultMatrix(ref.id) }
    void resultVector(String vId) { updateTransformation('resultVectorId', vId) }
    void resultVector(EntityRef<Vector> ref) { resultVector(ref.id) }
    void resultTensor(String tId) { updateTransformation('resultTensorId', tId) }
    void resultTensor(EntityRef<Tensor> ref) { resultTensor(ref.id) }
    void resultParameter(String pId) { updateTransformation('resultParameterId', pId) }
    void resultParameter(EntityRef<Parameter> ref) { resultParameter(ref.id) }
    void resultFunction(String fId) { updateTransformation('resultFunctionId', fId) }

    // Operands on TransformationOperand
    void operand(Map<String, Object> values) {
        int idx = operandSequence++
        Map<String, Object> opValues = new LinkedHashMap<>(values)
        opValues.put('transformationId', transformationId)
        if (!opValues.containsKey('operandIndex')) {
            opValues.put('operandIndex', (long) idx)
        }
        String opKey = "${transformationId}_Op_${idx}"
        mathMeta.declare('moqui.math.TransformationOperand', opKey, opValues)
    }

    void operandMatrix(String matrixId) { operand([operandTypeEnumId: 'TotMatrix', operandMatrixId: matrixId]) }
    void operandMatrix(EntityRef<Matrix> ref) { operandMatrix(ref.id) }

    void operandVector(String vectorId) { operand([operandTypeEnumId: 'TotVector', operandVectorId: vectorId]) }
    void operandVector(EntityRef<Vector> ref) { operandVector(ref.id) }

    void operandTensor(String tensorId) { operand([operandTypeEnumId: 'TotTensor', operandTensorId: tensorId]) }
    void operandTensor(EntityRef<Tensor> ref) { operandTensor(ref.id) }

    void operandParameter(String paramId) { operand([operandTypeEnumId: 'TotParameter', operandParameterId: paramId]) }
    void operandParameter(EntityRef<Parameter> ref) { operandParameter(ref.id) }

    void operandTransformation(String transId) { operand([operandTypeEnumId: 'TotTransformation', operandTransformationId: transId]) }
    void operandTransformation(EntityRef<Transformation> ref) { operandTransformation(ref.id) }

    void kernelMatrix(String matrixId) { operand([operandTypeEnumId: 'TotKernelMatrix', operandMatrixId: matrixId]) }
    void kernelMatrix(EntityRef<Matrix> ref) { kernelMatrix(ref.id) }

    void biasMatrix(String matrixId) { operand([operandTypeEnumId: 'TotBiasMatrix', operandMatrixId: matrixId]) }
    void biasMatrix(EntityRef<Matrix> ref) { biasMatrix(ref.id) }

    void kernelVector(String vectorId) { operand([operandTypeEnumId: 'TotKernelVector', operandVectorId: vectorId]) }
    void kernelVector(EntityRef<Vector> ref) { kernelVector(ref.id) }

    void biasVector(String vectorId) { operand([operandTypeEnumId: 'TotBiasVector', operandVectorId: vectorId]) }
    void biasVector(EntityRef<Vector> ref) { biasVector(ref.id) }

    protected void updateTransformation(String field, Object value) {
        if (mathMeta.hasEntity('Transformation')) {
            def rec = mathMeta.entity('Transformation').findByName(transformationId)
            if (rec != null) {
                rec.put(field, value)
                return
            }
        }
        mathMeta.declare('moqui.math.Transformation', transformationId, [(field): value])
    }

    protected void applyBaseArgs(Map<String, Object> args) {
        if (args == null) return
        if (args.containsKey('resultMatrix')) resultMatrix(args.resultMatrix as String)
        if (args.containsKey('resultVector')) resultVector(args.resultVector as String)
        if (args.containsKey('resultTensor')) resultTensor(args.resultTensor as String)
        if (args.containsKey('resultParameter')) resultParameter(args.resultParameter as String)
        if (args.containsKey('resultFunction')) resultFunction(args.resultFunction as String)

        if (args.containsKey('operandMatrix')) operandMatrix(args.operandMatrix as String)
        if (args.containsKey('operandVector')) operandVector(args.operandVector as String)
        if (args.containsKey('operandTensor')) operandTensor(args.operandTensor as String)
        if (args.containsKey('operandParameter')) operandParameter(args.operandParameter as String)
        if (args.containsKey('operandTransformation')) operandTransformation(args.operandTransformation as String)
        if (args.containsKey('kernelMatrix')) kernelMatrix(args.kernelMatrix as String)
        if (args.containsKey('biasMatrix')) biasMatrix(args.biasMatrix as String)
        if (args.containsKey('kernelVector')) kernelVector(args.kernelVector as String)
        if (args.containsKey('biasVector')) biasVector(args.biasVector as String)
    }
}

@CompileStatic
class MatrixDecompositionBuilder extends SatelliteBuilderBase {
    String leftMatrixId
    String diagMatrixId
    String rightMatrixId
    Long rankApproximation
    BigDecimal explainedVariance
    BigDecimal fitError

    MatrixDecompositionBuilder(final MathMeta mathMeta, final String transformationId) {
        super(mathMeta, transformationId)
    }

    MatrixDecompositionBuilder leftMatrix(String id) { this.leftMatrixId = id; this }
    MatrixDecompositionBuilder leftMatrix(EntityRef<Matrix> ref) { this.leftMatrixId = ref.id; this }
    MatrixDecompositionBuilder diagMatrix(String id) { this.diagMatrixId = id; this }
    MatrixDecompositionBuilder diagMatrix(EntityRef<Matrix> ref) { this.diagMatrixId = ref.id; this }
    MatrixDecompositionBuilder rightMatrix(String id) { this.rightMatrixId = id; this }
    MatrixDecompositionBuilder rightMatrix(EntityRef<Matrix> ref) { this.rightMatrixId = ref.id; this }
    MatrixDecompositionBuilder rankApproximation(long r) { this.rankApproximation = r; this }
    MatrixDecompositionBuilder rankApproximation(int r) { this.rankApproximation = (long) r; this }
    MatrixDecompositionBuilder explainedVariance(Number v) { if (v != null) this.explainedVariance = new BigDecimal(v.toString()); this }
    MatrixDecompositionBuilder fitError(Number e) { if (e != null) this.fitError = new BigDecimal(e.toString()); this }

    MatrixDecompositionBuilder applyArgs(Map<String, Object> args) {
        applyBaseArgs(args)
        if (args.containsKey('leftMatrix')) leftMatrix(args.leftMatrix as String)
        if (args.containsKey('diagMatrix')) diagMatrix(args.diagMatrix as String)
        if (args.containsKey('rightMatrix')) rightMatrix(args.rightMatrix as String)
        if (args.containsKey('rankApproximation')) rankApproximation((args.rankApproximation as Number).longValue())
        if (args.containsKey('explainedVariance')) explainedVariance(args.explainedVariance as Number)
        if (args.containsKey('fitError')) fitError(args.fitError as Number)
        this
    }

    EntityRef<MatrixDecomposition> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        if (leftMatrixId) values.put('leftMatrixId', leftMatrixId)
        if (diagMatrixId) values.put('diagMatrixId', diagMatrixId)
        if (rightMatrixId) values.put('rightMatrixId', rightMatrixId)
        if (rankApproximation != null) values.put('rankApproximation', rankApproximation)
        if (explainedVariance != null) values.put('explainedVariance', explainedVariance)
        if (fitError != null) values.put('fitError', fitError)
        mathMeta.declare('moqui.math.MatrixDecomposition', transformationId, values)
        new EntityRef<>(transformationId, MatrixDecomposition.class, values)
    }
}

@CompileStatic
class DiagonalExtractionBuilder extends SatelliteBuilderBase {
    Long axis1 = 0L
    Long axis2 = 1L
    Long axisOffset = 0L

    DiagonalExtractionBuilder(final MathMeta mathMeta, final String transformationId) {
        super(mathMeta, transformationId)
    }

    DiagonalExtractionBuilder axis1(long a) { this.axis1 = a; this }
    DiagonalExtractionBuilder axis1(int a) { this.axis1 = (long) a; this }
    DiagonalExtractionBuilder axis2(long a) { this.axis2 = a; this }
    DiagonalExtractionBuilder axis2(int a) { this.axis2 = (long) a; this }
    DiagonalExtractionBuilder axisOffset(long o) { this.axisOffset = o; this }
    DiagonalExtractionBuilder axisOffset(int o) { this.axisOffset = (long) o; this }

    DiagonalExtractionBuilder applyArgs(Map<String, Object> args) {
        applyBaseArgs(args)
        if (args.containsKey('axis1')) axis1((args.axis1 as Number).longValue())
        if (args.containsKey('axis2')) axis2((args.axis2 as Number).longValue())
        if (args.containsKey('axisOffset')) axisOffset((args.axisOffset as Number).longValue())
        this
    }

    EntityRef<DiagonalExtraction> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        values.put('axis1', axis1)
        values.put('axis2', axis2)
        values.put('axisOffset', axisOffset)
        mathMeta.declare('moqui.math.DiagonalExtraction', transformationId, values)
        new EntityRef<>(transformationId, DiagonalExtraction.class, values)
    }
}

@CompileStatic
class TriangularExtractionBuilder extends SatelliteBuilderBase {
    TriangularExtractionType type = TriangularExtractionType.Upper
    Long extractionOffset = 0L

    TriangularExtractionBuilder(final MathMeta mathMeta, final String transformationId) {
        super(mathMeta, transformationId)
    }

    TriangularExtractionBuilder type(TriangularExtractionType t) { this.type = t; this }
    TriangularExtractionBuilder type(String t) { this.type = TriangularExtractionType.valueOf(t); this }
    TriangularExtractionBuilder extractionType(TriangularExtractionType t) { type(t) }
    TriangularExtractionBuilder extractionOffset(long o) { this.extractionOffset = o; this }
    TriangularExtractionBuilder extractionOffset(int o) { this.extractionOffset = (long) o; this }

    TriangularExtractionBuilder applyArgs(Map<String, Object> args) {
        applyBaseArgs(args)
        if (args.containsKey('type')) {
            Object t = args.type
            if (t instanceof TriangularExtractionType) type((TriangularExtractionType) t)
            else if (t instanceof String) type((String) t)
        }
        if (args.containsKey('extractionType')) {
            Object t = args.extractionType
            if (t instanceof TriangularExtractionType) type((TriangularExtractionType) t)
            else if (t instanceof String) type((String) t)
        }
        if (args.containsKey('extractionOffset')) extractionOffset((args.extractionOffset as Number).longValue())
        this
    }

    EntityRef<TriangularExtraction> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        values.put('extractionTypeEnumId', type.id)
        values.put('extractionOffset', extractionOffset)
        mathMeta.declare('moqui.math.TriangularExtraction', transformationId, values)
        new EntityRef<>(transformationId, TriangularExtraction.class, values)
    }
}

@CompileStatic
class BandExtractionBuilder extends SatelliteBuilderBase {
    Long lowerBand = 0L
    Long upperBand = 0L

    BandExtractionBuilder(final MathMeta mathMeta, final String transformationId) {
        super(mathMeta, transformationId)
    }

    BandExtractionBuilder lowerBand(long l) { this.lowerBand = l; this }
    BandExtractionBuilder lowerBand(int l) { this.lowerBand = (long) l; this }
    BandExtractionBuilder upperBand(long u) { this.upperBand = u; this }
    BandExtractionBuilder upperBand(int u) { this.upperBand = (long) u; this }

    BandExtractionBuilder applyArgs(Map<String, Object> args) {
        applyBaseArgs(args)
        if (args.containsKey('lowerBand')) lowerBand((args.lowerBand as Number).longValue())
        if (args.containsKey('upperBand')) upperBand((args.upperBand as Number).longValue())
        this
    }

    EntityRef<BandExtraction> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        values.put('lowerBand', lowerBand)
        values.put('upperBand', upperBand)
        mathMeta.declare('moqui.math.BandExtraction', transformationId, values)
        new EntityRef<>(transformationId, BandExtraction.class, values)
    }
}

@CompileStatic
class BlockMatrixExtractionBuilder extends SatelliteBuilderBase {
    String blockLabel
    Long startRowBlock
    Long endRowBlock
    Long startColBlock
    Long endColBlock

    BlockMatrixExtractionBuilder(final MathMeta mathMeta, final String transformationId) {
        super(mathMeta, transformationId)
    }

    BlockMatrixExtractionBuilder blockLabel(String l) { this.blockLabel = l; this }
    BlockMatrixExtractionBuilder startRowBlock(long r) { this.startRowBlock = r; this }
    BlockMatrixExtractionBuilder startRowBlock(int r) { this.startRowBlock = (long) r; this }
    BlockMatrixExtractionBuilder endRowBlock(long r) { this.endRowBlock = r; this }
    BlockMatrixExtractionBuilder endRowBlock(int r) { this.endRowBlock = (long) r; this }
    BlockMatrixExtractionBuilder startColBlock(long c) { this.startColBlock = c; this }
    BlockMatrixExtractionBuilder startColBlock(int c) { this.startColBlock = (long) c; this }
    BlockMatrixExtractionBuilder endColBlock(long c) { this.endColBlock = c; this }
    BlockMatrixExtractionBuilder endColBlock(int c) { this.endColBlock = (long) c; this }

    BlockMatrixExtractionBuilder applyArgs(Map<String, Object> args) {
        applyBaseArgs(args)
        if (args.containsKey('blockLabel')) blockLabel(args.blockLabel as String)
        if (args.containsKey('startRowBlock')) startRowBlock((args.startRowBlock as Number).longValue())
        if (args.containsKey('endRowBlock')) endRowBlock((args.endRowBlock as Number).longValue())
        if (args.containsKey('startColBlock')) startColBlock((args.startColBlock as Number).longValue())
        if (args.containsKey('endColBlock')) endColBlock((args.endColBlock as Number).longValue())
        this
    }

    EntityRef<BlockMatrixExtraction> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        if (blockLabel) values.put('blockLabel', blockLabel)
        if (startRowBlock != null) values.put('startRowBlock', startRowBlock)
        if (endRowBlock != null) values.put('endRowBlock', endRowBlock)
        if (startColBlock != null) values.put('startColBlock', startColBlock)
        if (endColBlock != null) values.put('endColBlock', endColBlock)
        mathMeta.declare('moqui.math.BlockMatrixExtraction', transformationId, values)
        new EntityRef<>(transformationId, BlockMatrixExtraction.class, values)
    }
}

@CompileStatic
class TensorSliceBuilder extends SatelliteBuilderBase {
    String sliceDefinitionJson

    TensorSliceBuilder(final MathMeta mathMeta, final String transformationId) {
        super(mathMeta, transformationId)
    }

    TensorSliceBuilder sliceDefinitionJson(String json) { this.sliceDefinitionJson = json; this }
    TensorSliceBuilder slice(List<?> sliceList) {
        this.sliceDefinitionJson = JsonOutput.toJson(sliceList)
        this
    }

    TensorSliceBuilder applyArgs(Map<String, Object> args) {
        applyBaseArgs(args)
        if (args.containsKey('sliceDefinitionJson')) sliceDefinitionJson(args.sliceDefinitionJson as String)
        if (args.containsKey('slice')) {
            Object s = args.slice
            if (s instanceof List) slice((List<?>) s)
            else sliceDefinitionJson(s.toString())
        }
        this
    }

    EntityRef<TensorSlice> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        if (sliceDefinitionJson) values.put('sliceDefinitionJson', sliceDefinitionJson)
        mathMeta.declare('moqui.math.TensorSlice', transformationId, values)
        new EntityRef<>(transformationId, TensorSlice.class, values)
    }
}

@CompileStatic
class TensorDecompositionBuilder extends SatelliteBuilderBase {
    TensorDecompMethod method = TensorDecompMethod.Tucker
    String coreTensorId
    String sourceTensorId
    Long targetRank
    BigDecimal fitError
    BigDecimal explainedVariance
    String description

    TensorDecompositionBuilder(final MathMeta mathMeta, final String transformationId) {
        super(mathMeta, transformationId)
    }

    TensorDecompositionBuilder method(TensorDecompMethod m) { this.method = m; this }
    TensorDecompositionBuilder method(String m) { this.method = TensorDecompMethod.valueOf(m); this }
    TensorDecompositionBuilder coreTensor(String id) { this.coreTensorId = id; this }
    TensorDecompositionBuilder coreTensor(EntityRef<Tensor> ref) { this.coreTensorId = ref.id; this }
    TensorDecompositionBuilder sourceTensor(String id) { this.sourceTensorId = id; this }
    TensorDecompositionBuilder sourceTensor(EntityRef<Tensor> ref) { this.sourceTensorId = ref.id; this }
    TensorDecompositionBuilder targetRank(long r) { this.targetRank = r; this }
    TensorDecompositionBuilder targetRank(int r) { this.targetRank = (long) r; this }
    TensorDecompositionBuilder fitError(Number e) { if (e != null) this.fitError = new BigDecimal(e.toString()); this }
    TensorDecompositionBuilder explainedVariance(Number v) { if (v != null) this.explainedVariance = new BigDecimal(v.toString()); this }
    TensorDecompositionBuilder description(String d) { this.description = d; this }

    TensorDecompositionBuilder factor(int modeIndex, String factorMatrixId) {
        Map<String, Object> fVal = [
            transformationId: transformationId,
            modeIndex: (long) modeIndex,
            factorMatrixId: factorMatrixId
        ]
        mathMeta.declare('moqui.math.TensorDecompositionFactor', "${transformationId}_Mode_${modeIndex}", fVal)
        this
    }

    TensorDecompositionBuilder factor(int modeIndex, EntityRef<Matrix> ref) {
        factor(modeIndex, ref.id)
    }

    TensorDecompositionBuilder applyArgs(Map<String, Object> args) {
        applyBaseArgs(args)
        if (args.containsKey('method')) {
            Object m = args.method
            if (m instanceof TensorDecompMethod) method((TensorDecompMethod) m)
            else if (m instanceof String) method((String) m)
        }
        if (args.containsKey('coreTensor')) coreTensor(args.coreTensor as String)
        if (args.containsKey('sourceTensor')) sourceTensor(args.sourceTensor as String)
        if (args.containsKey('targetRank')) targetRank((args.targetRank as Number).longValue())
        if (args.containsKey('fitError')) fitError(args.fitError as Number)
        if (args.containsKey('explainedVariance')) explainedVariance(args.explainedVariance as Number)
        if (args.containsKey('description')) description(args.description as String)
        if (args.containsKey('factors') && args.factors instanceof List) {
            List<?> list = (List<?>) args.factors
            for (int i = 0; i < list.size(); i++) {
                factor(i, list[i].toString())
            }
        }
        this
    }

    EntityRef<TensorDecomposition> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        if (method) values.put('decompositionMethodEnumId', method.id)
        if (coreTensorId) values.put('coreTensorId', coreTensorId)
        if (sourceTensorId) values.put('sourceTensorId', sourceTensorId)
        if (targetRank != null) values.put('targetRank', targetRank)
        if (fitError != null) values.put('fitError', fitError)
        if (explainedVariance != null) values.put('explainedVariance', explainedVariance)
        if (description) values.put('description', description)
        mathMeta.declare('moqui.math.TensorDecomposition', transformationId, values)
        new EntityRef<>(transformationId, TensorDecomposition.class, values)
    }
}

@CompileStatic
class NormResultBuilder extends SatelliteBuilderBase {
    NormDomain domain = NormDomain.Matrix
    NormOrder order = NormOrder.MatFrobenius
    String reductionDimensions
    String keepDimensions = 'N'
    BigDecimal normValue

    NormResultBuilder(final MathMeta mathMeta, final String transformationId) {
        super(mathMeta, transformationId)
    }

    NormResultBuilder domain(NormDomain d) { this.domain = d; this }
    NormResultBuilder domain(String d) { this.domain = NormDomain.valueOf(d); this }
    NormResultBuilder order(NormOrder o) { this.order = o; this }
    NormResultBuilder order(String o) { this.order = NormOrder.valueOf(o); this }
    NormResultBuilder reductionDimensions(String r) { this.reductionDimensions = r; this }
    NormResultBuilder reductionDimensions(List<?> r) { this.reductionDimensions = JsonOutput.toJson(r); this }
    NormResultBuilder keepDimensions(boolean k) { this.keepDimensions = k ? 'Y' : 'N'; this }
    NormResultBuilder keepDimensions(String k) { this.keepDimensions = k; this }
    NormResultBuilder normValue(Number n) { if (n != null) this.normValue = new BigDecimal(n.toString()); this }

    NormResultBuilder applyArgs(Map<String, Object> args) {
        applyBaseArgs(args)
        if (args.containsKey('domain')) {
            Object d = args.domain
            if (d instanceof NormDomain) domain((NormDomain) d)
            else if (d instanceof String) domain((String) d)
        }
        if (args.containsKey('order')) {
            Object o = args.order
            if (o instanceof NormOrder) order((NormOrder) o)
            else if (o instanceof String) order((String) o)
        }
        if (args.containsKey('reductionDimensions')) {
            Object r = args.reductionDimensions
            if (r instanceof List) reductionDimensions((List<?>) r)
            else reductionDimensions(r.toString())
        }
        if (args.containsKey('keepDimensions')) {
            Object k = args.keepDimensions
            if (k instanceof Boolean) keepDimensions(((Boolean) k).booleanValue())
            else keepDimensions(k.toString())
        }
        if (args.containsKey('normValue')) normValue(args.normValue as Number)
        this
    }

    EntityRef<NormResult> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        if (domain) values.put('domainEnumId', domain.id)
        if (order) values.put('orderEnumId', order.id)
        if (reductionDimensions) values.put('reductionDimensions', reductionDimensions)
        values.put('keepDimensions', keepDimensions)
        if (normValue != null) values.put('normValue', normValue)
        mathMeta.declare('moqui.math.NormResult', transformationId, values)
        new EntityRef<>(transformationId, NormResult.class, values)
    }
}

@CompileStatic
class CoordinateSystemTransformationBuilder extends SatelliteBuilderBase {
    String sourceCoordinateSystemId
    String targetCoordinateSystemId
    String matrixId

    CoordinateSystemTransformationBuilder(final MathMeta mathMeta, final String transformationId) {
        super(mathMeta, transformationId)
    }

    CoordinateSystemTransformationBuilder sourceCoordSystem(String id) { this.sourceCoordinateSystemId = id; this }
    CoordinateSystemTransformationBuilder sourceCoordinateSystem(String id) { this.sourceCoordinateSystemId = id; this }
    CoordinateSystemTransformationBuilder targetCoordSystem(String id) { this.targetCoordinateSystemId = id; this }
    CoordinateSystemTransformationBuilder targetCoordinateSystem(String id) { this.targetCoordinateSystemId = id; this }
    CoordinateSystemTransformationBuilder matrix(String id) { this.matrixId = id; this }
    CoordinateSystemTransformationBuilder matrix(EntityRef<Matrix> ref) { this.matrixId = ref.id; this }

    CoordinateSystemTransformationBuilder applyArgs(Map<String, Object> args) {
        applyBaseArgs(args)
        if (args.containsKey('sourceCoordSystem')) sourceCoordSystem(args.sourceCoordSystem as String)
        if (args.containsKey('sourceCoordinateSystem')) sourceCoordinateSystem(args.sourceCoordinateSystem as String)
        if (args.containsKey('targetCoordSystem')) targetCoordSystem(args.targetCoordSystem as String)
        if (args.containsKey('targetCoordinateSystem')) targetCoordinateSystem(args.targetCoordinateSystem as String)
        if (args.containsKey('matrix')) matrix(args.matrix as String)
        this
    }

    EntityRef<CoordinateSystemTransformation> build() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.put('transformationId', transformationId)
        if (sourceCoordinateSystemId) values.put('sourceCoordinateSystemId', sourceCoordinateSystemId)
        if (targetCoordinateSystemId) values.put('targetCoordinateSystemId', targetCoordinateSystemId)
        if (matrixId) values.put('matrixId', matrixId)
        mathMeta.declare('moqui.math.CoordinateSystemTransformation', transformationId, values)
        new EntityRef<>(transformationId, CoordinateSystemTransformation.class, values)
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
