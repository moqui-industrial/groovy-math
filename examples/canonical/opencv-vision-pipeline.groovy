/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('OpenCvVisionModel',
    modelTypeEnum: MathModelType.ComputerVision,
    usageContextEnum: MathModelUsageContext.Inference,
    modelName: 'OpenCV Computer Vision Filtering Pipeline',
    description: 'Gaussian Blur followed by Sobel Gradient and 2D Spatial Filtering') {

    pipeline('BlurStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'GaussianBlur', stepName: 'Gaussian Smoothing',
        solvingMethodEnum: MathModelSolvingMethod.OpenCv) {
        Transformation('GaussianBlur', transformationTypeEnum: TransformationType.GaussianBlur,
            name: 'Gaussian Smoothing')
    }

    pipeline('SobelStep', stepSeqId: '02', sequenceNum: 20,
        transformationId: 'SobelGradient', stepName: 'Sobel Horizontal Gradient',
        solvingMethodEnum: MathModelSolvingMethod.OpenCv) {
        Transformation('SobelGradient', transformationTypeEnum: TransformationType.Sobel,
            name: 'Sobel Horizontal Gradient')
    }

    MathModel('EdgePipeline',
        modelAlias: 'edge_detection',
        statusId: 'MathModelDraft',
        description: 'Gaussian smoothing and Sobel edge detection') {

        Matrix('InputImage', matrixTypeEnum: MatrixType.Dense, purposeEnum: MatrixPurpose.Original,
            domainSpaceEnum: MathSpace.R2, codomainSpaceEnum: MathSpace.R2,
            rows: 8, cols: 8)
    }
}
