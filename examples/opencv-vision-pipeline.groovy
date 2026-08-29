/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('OpenCvVisionModel',
    modelTypeEnum: MathModelType.ComputerVision,
    usageContextEnum: MathModelUsageContext.Inference,
    modelName: 'OpenCV Computer Vision Filtering Pipeline',
    description: 'Gaussian Blur followed by Sobel Gradient and 2D Spatial Filtering') {

    MathModel('EdgePipeline',
        modelAlias: 'edge_detection',
        statusId: 'MathModelDraft',
        solvingMethodEnum: MathModelSolvingMethod.OpenCv,
        description: 'Gaussian smoothing and Sobel edge detection') {

        data('InputImageData', dataTypeEnum: MathModelDataType.Matrix, matrixId: 'InputImage', sequenceNum: 1) {
            Matrix('InputImage', matrixTypeEnum: MatrixType.Dense, purposeEnum: MatrixPurpose.Original,
                domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
                name: 'InputImage', rows: 8, cols: 8)
        }

        data('BlurStepData', dataTypeEnum: MathModelDataType.Transformation, transformationId: 'GaussianBlur', sequenceNum: 10) {
            Transformation('GaussianBlur', transformationTypeEnum: TransformationType.GaussianBlur,
                name: 'Gaussian Smoothing')
        }

        data('SobelStepData', dataTypeEnum: MathModelDataType.Transformation, transformationId: 'SobelGradient', sequenceNum: 20) {
            Transformation('SobelGradient', transformationTypeEnum: TransformationType.Sobel,
                name: 'Sobel Horizontal Gradient')
        }
    }
}
