/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

import groovy.math.dsl.MathDsl
import groovy.math.dsl.MathMeta
import groovy.math.opencv.OpenCv
import groovy.math.opencv.OpenCvPanama
import groovy.math.opencv.OpenCvResult

String schemaPath = System.getenv('MOQUI_MATH_ENTITIES') ?:
    '../../moqui/tests/ai/moqui-framework/runtime/component/moqui-math/entity/MathEntities.xml'
File schemaFile = new File(schemaPath)
File dslFile = new File('examples/opencv-vision-pipeline.groovy')

println '==================================================================='
println ' Moqui-Math: OpenCV Computer Vision Pipeline (Project Panama)'
println ' Textbook Example: Gaussian Blur, Sobel Gradient & 2D Spatial Filter'
println '==================================================================='
println "Schema : ${schemaFile.absolutePath}"
println "Model  : ${dslFile.name}"

int width = 8
int height = 8

// 1. Synthetic 8x8 image: Dark background (10.0) with a bright central square (255.0)
float[] inputImage = [
    10f,  10f,  10f,  10f,  10f,  10f,  10f,  10f,
    10f,  10f,  10f,  10f,  10f,  10f,  10f,  10f,
    10f,  10f, 255f, 255f, 255f, 255f,  10f,  10f,
    10f,  10f, 255f, 255f, 255f, 255f,  10f,  10f,
    10f,  10f, 255f, 255f, 255f, 255f,  10f,  10f,
    10f,  10f, 255f, 255f, 255f, 255f,  10f,  10f,
    10f,  10f,  10f,  10f,  10f,  10f,  10f,  10f,
    10f,  10f,  10f,  10f,  10f,  10f,  10f,  10f
] as float[]

println '\n1. Original Input Image (8x8):'
printImageMatrix(inputImage, width, height)

// 2. Evaluate Declarative Moqui DSL and Execute OpenCV Pipeline via Panama
MathMeta mathMeta = MathDsl.evaluate(schemaFile, dslFile)
long t0 = System.nanoTime()
OpenCvResult result = OpenCv.execute(mathMeta, 'EdgePipeline') {
    input 'InputImage', inputImage
}
long planTimeNs = System.nanoTime() - t0

println "\n2. OpenCV Gaussian Blur + Sobel X Edge Response (Calculated in ${String.format('%.2f', planTimeNs / 1_000_000.0)} ms):"
printImageMatrix(result.values, width, height)

// 3. Direct 2D Spatial Convolution (Laplacian Edge Sharpening) via Panama Plan
OpenCvPanama panama = OpenCvPanama.INSTANCE
long laplacePlan = panama.createPlan(width, height)
float[] laplacianKernel = [
     0f,  1f,  0f,
     1f, -4f,  1f,
     0f,  1f,  0f
] as float[]
panama.addFilter2d(laplacePlan, laplacianKernel, 3, 3)
panama.seal(laplacePlan, width, height)
float[] laplacianResponse = panama.execute(laplacePlan, inputImage)
panama.destroy(laplacePlan)

println '\n3. Direct Laplacian 2D Convolution (Panama C++ Native):'
printImageMatrix(laplacianResponse, width, height)

println '\n==================================================================='
println ' SUCCESS: OpenCV Vision Pipeline Executed Successfully via Panama!'
println '==================================================================='

static void printImageMatrix(float[] data, int w, int h) {
    for (int r = 0; r < h; r++) {
        print '  ['
        for (int c = 0; c < w; c++) {
            print String.format('%8.1f', data[r * w + c])
            if (c < w - 1) print ','
        }
        println ' ]'
    }
}
