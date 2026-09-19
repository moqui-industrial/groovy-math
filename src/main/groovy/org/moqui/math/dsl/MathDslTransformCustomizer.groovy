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
                for (TransformationType tt : TransformationType.values()) {
                    set.add(tt.name())
                    set.add(Character.toLowerCase(tt.name().charAt(0)).toString() + tt.name().substring(1))
                }
            } catch (Exception ignored) {
            }
            set.addAll(['matrix', 'Matrix', 'vector', 'Vector', 'tensor', 'Tensor',
                        'parameter', 'Parameter', 'parameters', 'model', 'MathModel', 'pipeline',
                        'dropout', 'Dropout', 'multiHeadAttention', 'MultiHeadAttention',
                        'positionalEncoding', 'PositionalEncoding', 'rotaryEmbedding', 'RotaryEmbedding',
                        'affine', 'Affine', 'layerNorm', 'LayerNorm', 'rmsNorm', 'RMSNorm',
                        'tensorAdd', 'tensorSub', 'tensorMul', 'tensorDiv', 'tensorPow',
                        'tensorReshape', 'tensorPermute', 'tensorContract', 'tensorSum', 'tensorMean',
                        'tensorMax', 'tensorMin', 'scaledDotProductAttention', 'embedding'])
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
        private final Map<String, Integer> declaredEntityIds = new LinkedHashMap<>()
        private final Set<MethodCallExpression> processedCalls = Collections.newSetFromMap(new IdentityHashMap<MethodCallExpression, Boolean>())

        TransformVisitor(final SourceUnit sourceUnit) {
            this.sourceUnit = sourceUnit
        }

        @Override
        protected SourceUnit getSourceUnit() {
            sourceUnit
        }

        private static boolean hasExplicitId(final MethodCallExpression mce) {
            if (mce.arguments instanceof TupleExpression) {
                TupleExpression tuple = (TupleExpression) mce.arguments
                List<Expression> exprs = tuple.expressions
                for (Expression e : exprs) {
                    if (e instanceof ConstantExpression && ((ConstantExpression) e).value instanceof CharSequence) {
                        return true
                    }
                    if (e instanceof GStringExpression) {
                        return true
                    }
                    if (e instanceof MapExpression) {
                        MapExpression map = (MapExpression) e
                        for (MapEntryExpression entry : map.mapEntryExpressions) {
                            if (entry.keyExpression instanceof ConstantExpression) {
                                String k = ((ConstantExpression) entry.keyExpression).text
                                if (k == 'name' || k == '_key' || k == 'transformationId') {
                                    return true
                                }
                            }
                        }
                    }
                }
            }
            return false
        }

        private static String getExplicitConstantKey(final MethodCallExpression mce) {
            if (mce.arguments instanceof TupleExpression) {
                TupleExpression tuple = (TupleExpression) mce.arguments
                List<Expression> exprs = tuple.expressions
                if (!exprs.empty) {
                    Expression first = exprs.get(0)
                    if (first instanceof ConstantExpression && ((ConstantExpression) first).value instanceof CharSequence) {
                        return ((ConstantExpression) first).value.toString()
                    }
                }
            }
            null
        }

        @Override
        void visitDeclarationExpression(final DeclarationExpression expression) {
            if (expression.leftExpression instanceof VariableExpression &&
                expression.rightExpression instanceof MethodCallExpression) {
                MethodCallExpression mce = (MethodCallExpression) expression.rightExpression
                String methodName = mce.methodAsString
                if (methodName != null && getTargetMethods().contains(methodName)) {
                    if (!hasExplicitId(mce)) {
                        String varName = ((VariableExpression) expression.leftExpression).name
                        sourceUnit.addError(new SyntaxException(
                            "usa '${varName} = ${methodName}(...)' senza 'def': con 'def' il nome non entra nel modello",
                            expression.lineNumber,
                            expression.columnNumber
                        ))
                        return
                    }
                }
            }
            super.visitDeclarationExpression(expression)
        }

        @Override
        void visitMethodCallExpression(final MethodCallExpression call) {
            if (processedCalls.contains(call)) {
                super.visitMethodCallExpression(call)
                return
            }
            String methodName = call.methodAsString
            if (methodName != null && getTargetMethods().contains(methodName)) {
                String explicitKey = getExplicitConstantKey(call)
                if (explicitKey != null) {
                    if (declaredEntityIds.containsKey(explicitKey)) {
                        int prevLine = declaredEntityIds.get(explicitKey)
                        sourceUnit.addError(new SyntaxException(
                            "l'identificatore '${explicitKey}' è già dichiarato alla riga ${prevLine}",
                            call.lineNumber,
                            call.columnNumber
                        ))
                        return
                    }
                    declaredEntityIds.put(explicitKey, call.lineNumber)
                }
            }
            super.visitMethodCallExpression(call)
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
            VariableExpression varExpr = (VariableExpression) expression.leftExpression
            String varName = varExpr.name
            if (!varName) return

            if (expression.rightExpression instanceof BinaryExpression) {
                BinaryExpression bin = (BinaryExpression) expression.rightExpression
                int opType = bin.operation.type
                String opMethod = null
                if (opType == Types.MULTIPLY) opMethod = 'multiplyOp'
                else if (opType == Types.PLUS) opMethod = 'plusOp'
                else if (opType == Types.MINUS) opMethod = 'minusOp'
                else if (opType == Types.DIVIDE) opMethod = 'divOp'
                else if (opType == Types.POWER) opMethod = 'powerOp'

                if (opMethod != null) {
                    if (declaredEntityIds.containsKey(varName)) {
                        int prevLine = declaredEntityIds.get(varName)
                        sourceUnit.addError(new SyntaxException(
                            "l'identificatore '${varName}' è già dichiarato alla riga ${prevLine}",
                            expression.lineNumber,
                            expression.columnNumber
                        ))
                        return
                    }
                    declaredEntityIds.put(varName, expression.lineNumber)
                    expression.rightExpression = new MethodCallExpression(
                        new VariableExpression("this"),
                        opMethod,
                        new ArgumentListExpression(
                            new ConstantExpression(varName),
                            bin.leftExpression,
                            bin.rightExpression
                        )
                    )
                    return
                }
            }

            if (!(expression.rightExpression instanceof MethodCallExpression)) return
            MethodCallExpression mce = (MethodCallExpression) expression.rightExpression

            String methodName = mce.methodAsString
            if (methodName == null || !getTargetMethods().contains(methodName)) return

            processedCalls.add(mce)
            boolean hasExplicit = hasExplicitId(mce)
            String explicitKey = getExplicitConstantKey(mce)

            if (explicitKey != null) {
                if (declaredEntityIds.containsKey(explicitKey)) {
                    int prevLine = declaredEntityIds.get(explicitKey)
                    sourceUnit.addError(new SyntaxException(
                        "l'identificatore '${explicitKey}' è già dichiarato alla riga ${prevLine}",
                        expression.lineNumber,
                        expression.columnNumber
                    ))
                    return
                }
                declaredEntityIds.put(explicitKey, expression.lineNumber)
            } else if (!hasExplicit) {
                if (declaredEntityIds.containsKey(varName)) {
                    int prevLine = declaredEntityIds.get(varName)
                    sourceUnit.addError(new SyntaxException(
                        "l'identificatore '${varName}' è già dichiarato alla riga ${prevLine}",
                        expression.lineNumber,
                        expression.columnNumber
                    ))
                    return
                }
                declaredEntityIds.put(varName, expression.lineNumber)
            }

            if (mce.arguments instanceof TupleExpression) {
                TupleExpression tuple = (TupleExpression) mce.arguments
                List<Expression> exprs = tuple.expressions

                if (!hasExplicit) {
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
