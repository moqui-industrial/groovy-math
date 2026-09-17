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
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.control.CompilerConfiguration
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.moqui.MoquiSchemaInspector

@CompileStatic
final class MathDsl {
    private MathDsl() { }

    // Overloads without a ModelDefinition use the Moqui schema vendored in the jar, so a
    // caller needs neither a Moqui checkout nor the MOQUI_MATH_ENTITIES environment variable.

    static MathMeta math(final Closure<?> declarations) {
        math(MoquiSchemaInspector.embedded(), declarations)
    }

    static MathMeta evaluate(final File dslFile) {
        evaluate(MoquiSchemaInspector.embedded(), dslFile)
    }

    static MathMeta fluent(
            @DelegatesTo(value = org.moqui.math.builder.FluentMath, strategy = Closure.DELEGATE_FIRST) final Closure<?> declarations) {
        fluent(MoquiSchemaInspector.embedded(), declarations)
    }

    static MathMeta math(final ModelDefinition definition, final Closure<?> declarations) {
        MathMeta mathMeta = new MathMeta(definition)
        MathDslBuilder builder = new MathDslBuilder(mathMeta)
        Closure<?> configured = (Closure<?>) declarations.rehydrate(builder, declarations.owner, declarations.thisObject)
        configured.resolveStrategy = Closure.DELEGATE_FIRST
        if (configured.maximumNumberOfParameters == 0) configured.call()
        else configured.call(builder)
        mathMeta
    }

    private static final ThreadLocal<MathMeta> CURRENT_META = new ThreadLocal<>()

    static MathMeta evaluate(final ModelDefinition definition, final File dslFile) {
        if (dslFile == null || !dslFile.isFile()) {
            throw new IllegalArgumentException("DSL file does not exist: ${dslFile}")
        }

        CompilerConfiguration configuration = new CompilerConfiguration()
        configuration.scriptBaseClass = DelegatingScript.name
        ImportCustomizer imports = new ImportCustomizer()
        imports.addStarImports('org.moqui.math.dsl')
        configuration.addCompilationCustomizers(imports)

        SecureASTCustomizer secure = new SecureASTCustomizer()
        secure.starImportsWhitelist = [
            'org.moqui.math.dsl',
            'org.moqui.math.dsl.*',
            'java.lang',
            'java.lang.*',
            'java.util',
            'java.util.*',
            'java.math',
            'java.math.*'
        ]
        secure.importsWhitelist = [
            'org.moqui.math.dsl.*',
            'java.lang.*',
            'java.util.*',
            'java.math.*'
        ]
        secure.receiversBlackList = [
            'java.lang.System',
            'java.lang.Runtime',
            'java.lang.Process',
            'java.lang.ProcessBuilder',
            'java.lang.Thread',
            'java.lang.ThreadGroup',
            'java.lang.ClassLoader',
            'java.lang.reflect.Method',
            'java.lang.reflect.Field',
            'java.lang.reflect.Constructor',
            'java.io.File',
            'java.io.FileInputStream',
            'java.io.FileOutputStream',
            'java.io.FileReader',
            'java.io.FileWriter',
            'java.io.RandomAccessFile',
            'java.nio.file.Files',
            'java.nio.file.Path',
            'java.nio.file.Paths',
            'java.net.Socket',
            'java.net.ServerSocket',
            'java.net.URL',
            'java.net.URI',
            'java.net.http.HttpClient'
        ]
        secure.indirectImportCheckEnabled = true
        configuration.addCompilationCustomizers(secure)
        GroovyShell shell = new GroovyShell(MathDsl.classLoader, new Binding(), configuration)
        DelegatingScript script = (DelegatingScript) shell.parse(dslFile)

        MathMeta mathMeta = new MathMeta(definition)
        CURRENT_META.set(mathMeta)
        try {
            script.delegate = new MathDslBuilder(mathMeta)
            Object result = script.run()
            if (result instanceof MathMeta) return (MathMeta) result
            return mathMeta
        } finally {
            CURRENT_META.remove()
        }
    }

    static MathMeta evaluate(final File schemaFile, final File dslFile) {
        evaluate(MoquiSchemaInspector.inspect(schemaFile), dslFile)
    }

    static MathMeta fluent(final ModelDefinition definition,
                           @DelegatesTo(value = org.moqui.math.builder.FluentMath, strategy = Closure.DELEGATE_FIRST) final Closure<?> declarations) {
        MathMeta current = CURRENT_META.get()
        MathMeta target = (current != null) ? current : new MathMeta(definition)
        org.moqui.math.builder.FluentMath.build(target, declarations)
    }

    static MathMeta fluent(final File schemaFile,
                           @DelegatesTo(value = org.moqui.math.builder.FluentMath, strategy = Closure.DELEGATE_FIRST) final Closure<?> declarations) {
        fluent(MoquiSchemaInspector.inspect(schemaFile), declarations)
    }
}
