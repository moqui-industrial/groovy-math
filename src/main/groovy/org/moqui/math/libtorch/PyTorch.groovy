/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta

@CompileStatic
final class PyTorch {
    private PyTorch() { }

    static LibTorchResult execute(final MathMeta mathMeta, final String mathModelId,
                                  final Closure<?> request) {
        ExecutionRequest execution = new ExecutionRequest()
        execution.configure(request)
        LibTorchProvider provider = new LibTorchProvider(mathModelId)
        if (execution.intraOpThreads != null || execution.interOpThreads != null) {
            provider.configureThreads(execution.intraOpThreads ?: 1, execution.interOpThreads ?: 1)
        }
        provider.run(mathMeta, execution.inputs)
    }

    @CompileStatic
    private static final class ExecutionRequest {
        final LinkedHashMap<String, Object> inputs = new LinkedHashMap<>()
        Integer intraOpThreads
        Integer interOpThreads

        void input(final String name, final Object value) {
            if (!name) throw new IllegalArgumentException('Input name must not be empty')
            inputs.put(name, value)
        }

        void threads(final Map<String, ?> options) {
            if (options.containsKey('intraOp')) intraOpThreads = ((Number) options.intraOp).intValue()
            if (options.containsKey('interOp')) interOpThreads = ((Number) options.interOp).intValue()
        }

        void configure(final Closure<?> action) {
            if (action == null) throw new IllegalArgumentException('PyTorch execution block is required')
            Closure<?> configured = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
            configured.resolveStrategy = Closure.DELEGATE_FIRST
            configured.maximumNumberOfParameters == 0 ? configured.call() : configured.call(this)
            if (inputs.empty) throw new IllegalStateException('At least one input is required')
        }
    }
}
