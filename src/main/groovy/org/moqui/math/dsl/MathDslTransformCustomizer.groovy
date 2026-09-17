/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassCodeVisitorSupport
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.CompilationCustomizer
import org.codehaus.groovy.syntax.Types

@CompileStatic
class MathDslTransformCustomizer extends CompilationCustomizer {

    private static final Set<String> TARGET_METHODS = Collections.unmodifiableSet([
        'matrix', 'Matrix', 'vector', 'Vector', 'tensor', 'Tensor',
        'parameter', 'parameters', 'Parameter', 'model', 'MathModel'
    ] as Set<String>)

    MathDslTransformCustomizer() {
        super(CompilePhase.CONVERSION)
    }

    @Override
    void call(final SourceUnit source, final GeneratorContext context, final ClassNode classNode) {
        TransformVisitor visitor = new TransformVisitor(source)
        classNode.visitContents(visitor)
    }

    private static class TransformVisitor extends ClassCodeVisitorSupport {
        private final SourceUnit sourceUnit

        TransformVisitor(final SourceUnit sourceUnit) {
            this.sourceUnit = sourceUnit
        }

        @Override
        protected SourceUnit getSourceUnit() {
            sourceUnit
        }

        @Override
        void visitDeclarationExpression(final DeclarationExpression expression) {
            transformAssignment(expression)
            super.visitDeclarationExpression(expression)
        }

        @Override
        void visitBinaryExpression(final BinaryExpression expression) {
            if (expression.operation.type == Types.ASSIGN) {
                transformAssignment(expression)
            }
            super.visitBinaryExpression(expression)
        }

        private void transformAssignment(final BinaryExpression expression) {
            if (!(expression.leftExpression instanceof VariableExpression)) return
            if (!(expression.rightExpression instanceof MethodCallExpression)) return

            VariableExpression varExpr = (VariableExpression) expression.leftExpression
            MethodCallExpression mce = (MethodCallExpression) expression.rightExpression

            String methodName = mce.methodAsString
            if (methodName == null || !TARGET_METHODS.contains(methodName)) return

            String varName = varExpr.name
            if (!varName) return

            if (mce.arguments instanceof TupleExpression) {
                TupleExpression tuple = (TupleExpression) mce.arguments
                List<Expression> exprs = tuple.expressions

                if (exprs.empty) {
                    exprs.add(new ConstantExpression(varName))
                    MapExpression map = new MapExpression()
                    map.addMapEntryExpression(new MapEntryExpression(new ConstantExpression('name'), new ConstantExpression(varName)))
                    map.addMapEntryExpression(new MapEntryExpression(new ConstantExpression('symbol'), new ConstantExpression(varName)))
                    exprs.add(map)
                } else if (exprs.get(0) instanceof MapExpression) {
                    MapExpression map = (MapExpression) exprs.get(0)
                    injectNameAndSymbol(map, varName)
                    exprs.add(0, new ConstantExpression(varName))
                } else if (exprs.get(0) instanceof ConstantExpression) {
                    // Key already present; check if map is second argument
                    if (exprs.size() > 1 && exprs.get(1) instanceof MapExpression) {
                        injectNameAndSymbol((MapExpression) exprs.get(1), varName)
                    }
                }
            }
        }

        private void injectNameAndSymbol(final MapExpression map, final String varName) {
            boolean hasName = map.mapEntryExpressions.any {
                it.keyExpression instanceof ConstantExpression && ((ConstantExpression) it.keyExpression).value == 'name'
            }
            if (!hasName) {
                map.addMapEntryExpression(new MapEntryExpression(new ConstantExpression('name'), new ConstantExpression(varName)))
            }
            boolean hasSymbol = map.mapEntryExpressions.any {
                it.keyExpression instanceof ConstantExpression && ((ConstantExpression) it.keyExpression).value == 'symbol'
            }
            if (!hasSymbol) {
                map.addMapEntryExpression(new MapEntryExpression(new ConstantExpression('symbol'), new ConstantExpression(varName)))
            }
        }
    }
}
