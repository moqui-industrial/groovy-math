/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassCodeVisitorSupport
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.MethodPointerExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.CompilationCustomizer

/**
 * AST Compilation Customizer strictly enforcing sandbox boundaries for Math DSL evaluation.
 * Enforces a strict whitelist of allowed classes and constructors, and forbids dangerous methods
 * (such as execute, evaluate, forName, getMethod, newInstance, exit, exec, start).
 */
@CompileStatic
class MathDslSandboxCustomizer extends CompilationCustomizer {

    private static final Set<String> FORBIDDEN_METHODS = Collections.unmodifiableSet([
        'execute', 'evaluate', 'exec', 'start', 'exit',
        'forName', 'loadClass', 'getClass',
        'getMethod', 'getMethods', 'getDeclaredMethod', 'getDeclaredMethods',
        'getField', 'getFields', 'getDeclaredField', 'getDeclaredFields',
        'getConstructor', 'getConstructors', 'getDeclaredConstructor', 'getDeclaredConstructors',
        'newInstance', 'invoke', 'invokeMethod', 'setProperty',
        'setAccessible', 'defineClass'
    ] as Set<String>)

    private static final Set<String> ALLOWED_JAVA_LANG = Collections.unmodifiableSet([
        'java.lang.String', 'java.lang.CharSequence',
        'java.lang.Number', 'java.lang.Integer', 'java.lang.Long',
        'java.lang.Double', 'java.lang.Float', 'java.lang.Short', 'java.lang.Byte',
        'java.lang.Boolean', 'java.lang.Character',
        'java.lang.Math', 'java.lang.Enum', 'java.lang.Object'
    ] as Set<String>)

    private static final Set<String> ALLOWED_JAVA_UTIL = Collections.unmodifiableSet([
        'java.util.Map', 'java.util.HashMap', 'java.util.LinkedHashMap', 'java.util.TreeMap',
        'java.util.List', 'java.util.ArrayList', 'java.util.LinkedList',
        'java.util.Set', 'java.util.HashSet', 'java.util.LinkedHashSet', 'java.util.TreeSet',
        'java.util.Collection', 'java.util.Collections', 'java.util.Arrays',
        'java.util.Date'
    ] as Set<String>)

    MathDslSandboxCustomizer() {
        super(CompilePhase.CONVERSION)
    }

    @Override
    void call(SourceUnit source, GeneratorContext context, ClassNode classNode) {
        new Visitor(source, classNode.name).visitClass(classNode)
    }

    private static boolean isAllowedClass(String name, String scriptClassName, SourceUnit sourceUnit) {
        if (name == null) return false
        if (scriptClassName != null && (name == scriptClassName || name.startsWith(scriptClassName + '$'))) return true
        if (sourceUnit?.AST?.classes?.any { it.name == name }) return true
        if (name.startsWith('org.moqui.math.')) return true
        if (name.startsWith('java.math.')) return true
        if (name.startsWith('groovy.lang.') || name.startsWith('groovy.util.')) {
            return name == 'groovy.lang.Closure' || name == 'groovy.lang.Script' || name == 'groovy.util.DelegatingScript'
        }
        if (name.startsWith('java.lang.')) {
            return ALLOWED_JAVA_LANG.contains(name)
        }
        if (name.startsWith('java.util.')) {
            return ALLOWED_JAVA_UTIL.contains(name)
        }
        return false
    }

    private static class Visitor extends ClassCodeVisitorSupport {
        private final SourceUnit sourceUnit
        private final String scriptClassName

        Visitor(SourceUnit sourceUnit, String scriptClassName) {
            this.sourceUnit = sourceUnit
            this.scriptClassName = scriptClassName
        }

        @Override
        protected SourceUnit getSourceUnit() {
            return sourceUnit
        }

        @Override
        void visitMethodCallExpression(MethodCallExpression call) {
            String methodName = call.methodAsString
            if (methodName != null && FORBIDDEN_METHODS.contains(methodName)) {
                addError("Security error: Method call '${methodName}' is forbidden in Math DSL scripts.", call)
            }
            super.visitMethodCallExpression(call)
        }

        @Override
        void visitMethodPointerExpression(MethodPointerExpression expr) {
            addError("Security error: Method pointers (.&) are forbidden in Math DSL scripts.", expr)
            super.visitMethodPointerExpression(expr)
        }

        @Override
        void visitClassExpression(ClassExpression expr) {
            String name = expr.type?.name
            if (name != null && !isAllowedClass(name, scriptClassName, sourceUnit)) {
                addError("Security error: Reference to class '${name}' is forbidden in Math DSL scripts.", expr)
            }
            super.visitClassExpression(expr)
        }

        @Override
        void visitConstructorCallExpression(ConstructorCallExpression call) {
            String name = call.type?.name
            if (name != null && !isAllowedClass(name, scriptClassName, sourceUnit)) {
                addError("Security error: Construction of '${name}' is forbidden in Math DSL scripts.", call)
            }
            super.visitConstructorCallExpression(call)
        }

        @Override
        void visitPropertyExpression(PropertyExpression expr) {
            String prop = expr.propertyAsString
            if (prop == 'class' || prop == 'classLoader') {
                addError("Security error: Property access '${prop}' is forbidden in Math DSL scripts.", expr)
            }
            super.visitPropertyExpression(expr)
        }
    }
}
