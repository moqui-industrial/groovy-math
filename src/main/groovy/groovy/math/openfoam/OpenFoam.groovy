/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.openfoam

import groovy.transform.CompileStatic
import groovy.math.dsl.MathMeta

@CompileStatic
final class OpenFoam {
    private OpenFoam() { }

    static OpenFoamResult execute(final MathMeta mathMeta, final String mathModelId,
                                  @DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> request = null) {
        ExecutionRequest execution = new ExecutionRequest()
        if (request != null) {
            execution.configure(request)
        }
        OpenFoamProvider provider = new OpenFoamProvider(mathModelId)
        provider.run(mathMeta, execution.inputs)
    }

    @CompileStatic
    public static final class ExecutionRequest {
        final LinkedHashMap<String, Object> inputs = new LinkedHashMap<>()

        void input(final String name, final Object value) {
            if (!name) throw new IllegalArgumentException('Input name must not be empty')
            inputs.put(name, value)
        }

        void configure(@DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> action) {
            if (action == null) return
            Closure<?> configured = (Closure<?>) action.rehydrate(this, action.owner, action.thisObject)
            configured.resolveStrategy = Closure.DELEGATE_FIRST
            configured.maximumNumberOfParameters == 0 ? configured.call() : configured.call(this)
        }
    }
}
