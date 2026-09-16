/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math

import groovy.transform.CompileStatic
import org.moqui.math.dsl.MathMeta
import org.moqui.math.entity.ModelValue
import org.moqui.math.spi.MathProviderFactory
import org.moqui.math.spi.MathProviderRegistry

/**
 * Dispatches a declared MathModel to the backend its metadata selects.
 *
 * <p>Named MathEngine rather than Math: a class called Math in this package shadows
 * java.lang.Math for every other class in it, and callers were already importing it under
 * an alias to get around that.
 *
 * <p>This class names no backend. Which one runs a model is decided by the
 * MathProviderFactory implementations on the classpath, so adding a backend is adding a jar.
 */
@CompileStatic
final class MathEngine {
    private MathEngine() { }

    static Object execute(final MathMeta mathMeta, final String mathModelId,
                          @DelegatesTo(value = ExecutionRequest, strategy = Closure.DELEGATE_FIRST) final Closure<?> request) {
        ExecutionRequest execution = new ExecutionRequest()
        execution.configure(request)

        Objects.requireNonNull(mathMeta, 'Math metadata must not be null').freeze()
        ModelValue model = mathMeta.entity('MathModel').findByName(mathModelId)
        if (model == null) throw new IllegalArgumentException("Unknown MathModel '${mathModelId}'")

        MathProviderFactory factory = MathProviderRegistry.select(mathMeta, model)
        if (factory == null) {
            throw new IllegalStateException(MathProviderRegistry.unclaimedMessage(mathMeta, model))
        }
        factory.create(mathModelId).run(mathMeta, execution.inputs)
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
