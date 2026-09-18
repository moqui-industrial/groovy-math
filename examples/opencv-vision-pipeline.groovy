/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

MathModelDef('OpenCvVisionModel', type: ComputerVision, usage: Inference,
    modelName: 'OpenCV Computer Vision Filtering Pipeline',
    description: 'Gaussian Blur followed by Sobel Gradient and 2D Spatial Filtering') {

    pipeline('BlurStep', stepSeqId: '01', sequenceNum: 10,
        transformationId: 'GaussianBlur', stepName: 'Gaussian Smoothing', method: OpenCv) {
        Transformation('GaussianBlur', type: GaussianBlur, name: 'Gaussian Smoothing')
    }

    pipeline('SobelStep', stepSeqId: '02', sequenceNum: 20,
        transformationId: 'SobelGradient', stepName: 'Sobel Horizontal Gradient', method: OpenCv) {
        Transformation('SobelGradient', type: Sobel, name: 'Sobel Horizontal Gradient')
    }

    MathModel('EdgePipeline', alias: 'edge_detection', status: Draft,
        description: 'Gaussian smoothing and Sobel edge detection') {
        matrix('InputImage', rows: 8, cols: 8, purpose: Original)
    }
}
