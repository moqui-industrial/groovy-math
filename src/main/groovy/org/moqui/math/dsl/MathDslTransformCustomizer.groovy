package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ClassCodeVisitorSupport
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.CompilationCustomizer
import org.codehaus.groovy.syntax.SyntaxException
import org.codehaus.groovy.syntax.Types
import org.moqui.math.moqui.MoquiSchemaInspector

@CompileStatic
class MathDslTransformCustomizer extends CompilationCustomizer {

    private static Set<String> targetMethodsCache = null

    private static synchronized Set<String> getTargetMethods() {
        if (targetMethodsCache == null) {
            Set<String> set = new LinkedHashSet<>()
            try {
                DslVocabulary vocab = DslVocabulary.of(MoquiSchemaInspector.embedded())
                set.addAll(vocab.entityKeywords.keySet())
            } catch (Exception ignored) {
            }
            set.addAll(['matrix', 'Matrix', 'vector', 'Vector', 'tensor', 'Tensor',
                        'parameter', 'Parameter', 'parameters', 'model', 'MathModel', 'pipeline'])
            targetMethodsCache = Collections.unmodifiableSet(set)
        }
        targetMethodsCache
    }

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
        private final Map<String, Integer> declaredIdentifiers = new LinkedHashMap<>()

        TransformVisitor(final SourceUnit sourceUnit) {
            this.sourceUnit = sourceUnit
        }

        @Override
        protected SourceUnit getSourceUnit() {
            sourceUnit
        }

        @Override
        void visitDeclarationExpression(final DeclarationExpression expression) {
            if (expression.leftExpression instanceof VariableExpression &&
                expression.rightExpression instanceof MethodCallExpression) {
                MethodCallExpression mce = (MethodCallExpression) expression.rightExpression
                String methodName = mce.methodAsString
                if (methodName != null && getTargetMethods().contains(methodName)) {
                    String varName = ((VariableExpression) expression.leftExpression).name
                    sourceUnit.addError(new SyntaxException(
                        "usa '${varName} = ${methodName}(...)' senza 'def': con 'def' il nome non entra nel modello",
                        expression.lineNumber,
                        expression.columnNumber
                    ))
                    return
                }
            }
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
            if (methodName == null || !getTargetMethods().contains(methodName)) return

            String varName = varExpr.name
            if (!varName) return

            if (declaredIdentifiers.containsKey(varName)) {
                int prevLine = declaredIdentifiers.get(varName)
                sourceUnit.addError(new SyntaxException(
                    "l'identificatore '${varName}' è già dichiarato alla riga ${prevLine}",
                    expression.lineNumber,
                    expression.columnNumber
                ))
                return
            }
            declaredIdentifiers.put(varName, expression.lineNumber)

            if (mce.arguments instanceof TupleExpression) {
                TupleExpression tuple = (TupleExpression) mce.arguments
                List<Expression> exprs = tuple.expressions

                MapExpression mapExpr = null
                for (Expression e : exprs) {
                    if (e instanceof MapExpression) {
                        mapExpr = (MapExpression) e
                        break
                    }
                }
                if (mapExpr == null) {
                    mapExpr = new MapExpression()
                    exprs.add(mapExpr)
                }
                injectSymbol(mapExpr, varName)

                boolean hasKeyConstant = !exprs.empty && exprs.get(0) instanceof ConstantExpression && ((ConstantExpression) exprs.get(0)).value instanceof CharSequence
                if (!hasKeyConstant) {
                    exprs.add(0, new ConstantExpression(varName))
                }
            }
        }

        private void injectSymbol(final MapExpression map, final String varName) {
            boolean hasSymbol = map.mapEntryExpressions.any {
                it.keyExpression instanceof ConstantExpression && ((ConstantExpression) it.keyExpression).value == 'symbol'
            }
            if (!hasSymbol) {
                map.addMapEntryExpression(new MapEntryExpression(new ConstantExpression('symbol'), new ConstantExpression(varName)))
            }
        }
    }
}
