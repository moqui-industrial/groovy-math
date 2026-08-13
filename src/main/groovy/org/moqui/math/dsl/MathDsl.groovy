/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

package org.moqui.math.dsl

import groovy.lang.Binding
import groovy.lang.GroovyShell
import groovy.transform.CompileStatic
import groovy.util.DelegatingScript
import org.codehaus.groovy.control.CompilerConfiguration
import org.moqui.math.model.ModelDefinition
import org.moqui.math.moqui.MoquiSchemaInspector

@CompileStatic
final class MathDsl {
    private MathDsl() { }

    static MathGraph math(final ModelDefinition definition, final Closure<?> declarations) {
        MathGraph graph = new MathGraph(definition)
        MathDslBuilder builder = new MathDslBuilder(graph)
        Closure<?> configured = (Closure<?>) declarations.rehydrate(builder, declarations.owner, declarations.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_FIRST
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(builder)
        graph
    }

    static MathGraph evaluate(final ModelDefinition definition, final File dslFile) {
        if (dslFile == null || !dslFile.isFile()) {
            throw new IllegalArgumentException("DSL file does not exist: ${dslFile}")
        }

        CompilerConfiguration configuration = new CompilerConfiguration()
        configuration.scriptBaseClass = DelegatingScript.name
        GroovyShell shell = new GroovyShell(MathDsl.classLoader, new Binding(), configuration)
        DelegatingScript script = (DelegatingScript) shell.parse(dslFile)

        MathGraph graph = new MathGraph(definition)
        script.delegate = new MathDslBuilder(graph)
        script.run()
        graph
    }

    static MathGraph evaluate(final File schemaFile, final File dslFile) {
        evaluate(MoquiSchemaInspector.inspect(schemaFile), dslFile)
    }
}
