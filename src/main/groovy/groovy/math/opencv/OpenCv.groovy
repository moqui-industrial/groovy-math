/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.opencv

import groovy.transform.CompileStatic
import groovy.math.dsl.MathMeta

@CompileStatic
final class OpenCv {
    private OpenCv() { }

    static OpenCvResult execute(final MathMeta mathMeta, final String mathModelId,
                                @DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> request) {
        ExecutionRequest execution = new ExecutionRequest()
        execution.configure(request)
        OpenCvProvider provider = new OpenCvProvider(mathModelId)
        provider.run(mathMeta, execution.inputs)
    }

    @CompileStatic
    public static final class ExecutionRequest {
        final LinkedHashMap<String, Object> inputs = new LinkedHashMap<>()

        void input(final String name, final Object value) {
            if (!name) throw new IllegalArgumentException('Input name must not be empty')
            inputs.put(name, value)
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
