/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.onnx

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.spi.DeclaredModel
import org.moqui.math.spi.MathProvider
import org.moqui.math.spi.MathProviderFactory

@CompileStatic
final class OnnxProviderFactory implements MathProviderFactory {

    @Override
    String getProviderId() { 'onnx' }

    @Override
    int getPriority() { 70 }

    @Override
    boolean claims(final MathMeta mathMeta, final DeclaredModel model) {
        if (model.solvingMethod == 'MmsmOnnx' || model.solvingMethod == 'SmOnnx') return true
        def mathModel = mathMeta.entity('MathModel').findByName(model.mathModelId)
        String loc = (mathModel?.get('location') ?: mathModel?.get('contentLocation')) as String
        if (loc != null && loc.endsWith('.onnx')) return true
        false
    }

    @Override
    MathProvider<?, ?> create(final String mathModelId) {
        new OnnxRuntimeProvider(mathModelId)
    }
}
