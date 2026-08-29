/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.jax

import groovy.transform.CompileStatic
import groovy.math.dsl.MathMeta
import groovy.math.libtorch.LibTorchPlan
import groovy.math.libtorch.LibTorchProvider
import groovy.math.libtorch.LibTorchResult
import groovy.math.spi.MathProvider

@CompileStatic
final class JaxProvider implements MathProvider<LibTorchPlan, LibTorchResult> {
    final String mathModelId
    private final LibTorchProvider delegateProvider

    JaxProvider(final String mathModelId) {
        this.mathModelId = mathModelId
        this.delegateProvider = new LibTorchProvider(mathModelId, JaxPanama.INSTANCE)
    }

    @Override
    String getProviderId() { 'jax' }

    @Override
    LibTorchPlan compile(final MathMeta mathMeta) {
        delegateProvider.compile(mathMeta)
    }

    @Override
    LibTorchResult execute(final LibTorchPlan plan, final Map<String, ?> context) {
        delegateProvider.execute(plan, context)
    }
}
