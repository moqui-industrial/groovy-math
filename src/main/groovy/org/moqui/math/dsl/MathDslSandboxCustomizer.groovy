/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassCodeVisitorSupport
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.AttributeExpression
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.MethodPointerExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.CompilationCustomizer

/**
 * AST Compilation Customizer strictly enforcing sandbox boundaries for Math DSL evaluation.
 * Enforces an allowlist approach for methods, classes, constructors, and property expressions.
 * Dynamic invocations, metaprogramming hooks, reflection, and dangerous GDK operations are
 * blocked at compile-time during the CANONICALIZATION phase.
 */
@CompileStatic
class MathDslSandboxCustomizer extends CompilationCustomizer {

    private static final Set<String> FORBIDDEN_METHODS = Collections.unmodifiableSet([
        'execute', 'evaluate', 'exec', 'start', 'exit',
        'forName', 'loadClass', 'getClass',
        'getMethod', 'getMethods', 'getDeclaredMethod', 'getDeclaredMethods',
        'getField', 'getFields', 'getDeclaredField', 'getDeclaredFields',
        'getConstructor', 'getConstructors', 'getDeclaredConstructor', 'getDeclaredConstructors',
        'newInstance', 'invoke', 'invokeMethod', 'setProperty', 'getProperty',
        'setAccessible', 'defineClass', 'sleep', 'wait', 'notify', 'notifyAll',
        'dump', 'inspect', 'getMetaClass', 'setMetaClass', 'getMetaMethod', 'getMetaProperty',
        'asType', 'newInputStream', 'newOutputStream', 'newReader', 'newWriter',
        'getText', 'getBytes', 'setText', 'setBytes', 'eachLine', 'withReader', 'withWriter',
        'withInputStream', 'withOutputStream', 'pipeTo', 'filterLine'
    ] as Set<String>)

    private static final Set<String> FORBIDDEN_SCRIPT_METHODS = Collections.unmodifiableSet([
        'println', 'print', 'printf', 'sleep', 'evaluate', 'run',
        'wait', 'notify', 'notifyAll', 'getClass', 'getBinding', 'setBinding',
        'getProperty', 'setProperty', 'invokeMethod', 'getMetaClass', 'setMetaClass'
    ] as Set<String>)

    private static final Set<String> FORBIDDEN_PROPERTIES = Collections.unmodifiableSet([
        'class', 'classLoader', 'metaClass', 'declaringClass'
    ] as Set<String>)

    private static final Set<String> ALLOWED_JAVA_LANG = Collections.unmodifiableSet([
        'java.lang.String', 'java.lang.CharSequence',
        'java.lang.Number', 'java.lang.Integer', 'java.lang.Long',
        'java.lang.Double', 'java.lang.Float', 'java.lang.Short', 'java.lang.Byte',
        'java.lang.Boolean', 'java.lang.Character',
        'java.lang.Math', 'java.lang.Enum', 'java.lang.Object', 'java.lang.Void'
    ] as Set<String>)

    private static final Set<String> ALLOWED_JAVA_UTIL = Collections.unmodifiableSet([
        'java.util.Map', 'java.util.HashMap', 'java.util.LinkedHashMap', 'java.util.TreeMap',
        'java.util.List', 'java.util.ArrayList', 'java.util.LinkedList',
        'java.util.Set', 'java.util.HashSet', 'java.util.LinkedHashSet', 'java.util.TreeSet',
        'java.util.Collection', 'java.util.Collections', 'java.util.Arrays',
        'java.util.Date'
    ] as Set<String>)

    MathDslSandboxCustomizer() {
        super(CompilePhase.CANONICALIZATION)
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
            return name == 'groovy.lang.Closure' || name == 'groovy.lang.Script' ||
                   name == 'groovy.util.DelegatingScript' || name == 'groovy.lang.Reference' ||
                   name == 'groovy.lang.Range' || name == 'groovy.lang.IntRange'
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
            if (call.lineNumber > 0) {
                Expression methodExpr = call.method
                if (!(methodExpr instanceof ConstantExpression)) {
                    addError("Security error: Dynamic method invocation is forbidden in Math DSL scripts.", call)
                    return
                }

                String methodName = ((ConstantExpression) methodExpr).text
                if (methodName != null && FORBIDDEN_METHODS.contains(methodName)) {
                    addError("Security error: Method call '${methodName}' is forbidden in Math DSL scripts.", call)
                    return
                }

                boolean isImplicit = call.implicitThis ||
                    (call.objectExpression instanceof VariableExpression && ((VariableExpression) call.objectExpression).isThisExpression())
                if (isImplicit && methodName != null && FORBIDDEN_SCRIPT_METHODS.contains(methodName)) {
                    addError("Security error: Script/Object method '${methodName}' is forbidden in Math DSL scripts.", call)
                    return
                }
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
            if (expr.lineNumber > 0) {
                String name = expr.type?.name
                if (name != null && !isAllowedClass(name, scriptClassName, sourceUnit)) {
                    addError("Security error: Reference to class '${name}' is forbidden in Math DSL scripts.", expr)
                }
            }
            super.visitClassExpression(expr)
        }

        @Override
        void visitConstructorCallExpression(ConstructorCallExpression call) {
            if (call.lineNumber > 0) {
                String name = call.type?.name
                if (name != null && !isAllowedClass(name, scriptClassName, sourceUnit)) {
                    addError("Security error: Construction of '${name}' is forbidden in Math DSL scripts.", call)
                }
            }
            super.visitConstructorCallExpression(call)
        }

        @Override
        void visitAttributeExpression(AttributeExpression expr) {
            addError("Security error: Direct field access (.@) is forbidden in Math DSL scripts.", expr)
        }

        @Override
        void visitPropertyExpression(PropertyExpression expr) {
            if (expr instanceof AttributeExpression) {
                addError("Security error: Direct field access (.@) is forbidden in Math DSL scripts.", expr)
                return
            }

            Expression propExpr = expr.property
            if (!(propExpr instanceof ConstantExpression)) {
                addError("Security error: Dynamic property access is forbidden in Math DSL scripts.", expr)
                return
            }

            String prop = ((ConstantExpression) propExpr).text
            if (prop != null && FORBIDDEN_PROPERTIES.contains(prop)) {
                addError("Security error: Property access '${prop}' is forbidden in Math DSL scripts.", expr)
                return
            }
            super.visitPropertyExpression(expr)
        }
    }
}
