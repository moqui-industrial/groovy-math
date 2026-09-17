MathModelDef('OpenCvVisionModel',
    modelType: ComputerVision,
    usageContext: Inference,
    modelName: 'OpenCV Computer Vision Filtering Pipeline',
    description: 'Gaussian Blur followed by Sobel Gradient and 2D Spatial Filtering') {

    pipeline('BlurStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'GaussianBlur', stepName: 'Gaussian Smoothing',
        solvingMethod: OpenCv) {
        Transformation('GaussianBlur', transformationType: GaussianBlur,
            name: 'Gaussian Smoothing')
    }

    pipeline('SobelStep', stepSeqId: '02', sequenceNum: 20,
        transformationId: 'SobelGradient', stepName: 'Sobel Horizontal Gradient',
        solvingMethod: OpenCv) {
        Transformation('SobelGradient', transformationType: Sobel,
            name: 'Sobel Horizontal Gradient')
    }

    MathModel('EdgePipeline',
        modelAlias: 'edge_detection',
        statusId: 'MathModelDraft',
        description: 'Gaussian smoothing and Sobel edge detection') {

        Matrix('InputImage', matrixType: MatrixType.Dense, purpose: MatrixPurpose.Original,
            domainSpace: R2, codomainSpace: R2,
            name: 'InputImage', rows: 8, cols: 8)
    }
}
