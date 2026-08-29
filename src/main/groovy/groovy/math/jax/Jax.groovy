/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.jax

import groovy.transform.CompileStatic
import groovy.math.dsl.MathMeta
import groovy.math.libtorch.LibTorchProvider
import groovy.math.libtorch.LibTorchResult

@CompileStatic
final class Jax {
    private Jax() { }

    static LibTorchResult execute(final MathMeta mathMeta, final String mathModelId,
                                  @DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> request) {
        ExecutionRequest execution = new ExecutionRequest()
        execution.configure(request)
        LibTorchProvider provider = new LibTorchProvider(mathModelId, JaxPanama.INSTANCE)
        if (execution.intraOpThreads != null || execution.interOpThreads != null) {
            provider.configureThreads(execution.intraOpThreads ?: 1, execution.interOpThreads ?: 1)
        }
        provider.run(mathMeta, execution.inputs)
    }

    @CompileStatic
    public static final class ExecutionRequest {
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

        void configure(@DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> closure) {
            if (closure == null) return
            Closure<?> copy = (Closure<?>) closure.clone()
            copy.resolveStrategy = Closure.DELEGATE_FIRST
            copy.delegate = this
            copy.call()
        }
    }
}
