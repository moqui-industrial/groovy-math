/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassCodeVisitorSupport
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.ImportNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.CompilationCustomizer
import org.codehaus.groovy.syntax.SyntaxException

@CompileStatic
class MathDslAnnotationBlocker extends CompilationCustomizer {

    MathDslAnnotationBlocker() {
        super(CompilePhase.CONVERSION)
    }

    @Override
    void call(SourceUnit source, GeneratorContext context, ClassNode classNode) {
        // Check imports annotations
        if (source.AST != null) {
            if (source.AST.imports) {
                for (ImportNode imp : source.AST.imports) {
                    checkAnnotated(source, imp)
                }
            }
            if (source.AST.starImports) {
                for (ImportNode imp : source.AST.starImports) {
                    checkAnnotated(source, imp)
                }
            }
            if (source.AST.staticImports) {
                for (ImportNode imp : source.AST.staticImports.values()) {
                    checkAnnotated(source, imp)
                }
            }
            if (source.AST.staticStarImports) {
                for (ImportNode imp : source.AST.staticStarImports.values()) {
                    checkAnnotated(source, imp)
                }
            }
        }

        // Visit full class AST
        new AnnotationVisitor(source).visitClass(classNode)
    }

    private static void checkAnnotated(SourceUnit source, AnnotatedNode node) {
        if (node?.annotations) {
            for (AnnotationNode ann : node.annotations) {
                checkAnnotation(source, ann)
            }
        }
    }

    private static void checkAnnotation(SourceUnit source, AnnotationNode ann) {
        if (ann == null) return
        String annName = ann.classNode?.name ?: 'Unknown'
        if (annName.endsWith('BaseScript') || annName.endsWith('Generated') ||
            annName.endsWith('CompileStatic') || annName.endsWith('CompileDynamic')) {
            return
        }
        source.addError(new SyntaxException(
                "Security error: Annotation '@${annName}' is forbidden in Math DSL scripts.",
                ann.lineNumber, ann.columnNumber))
    }

    private static class AnnotationVisitor extends ClassCodeVisitorSupport {
        private final SourceUnit sourceUnit

        AnnotationVisitor(SourceUnit sourceUnit) {
            this.sourceUnit = sourceUnit
        }

        @Override
        protected SourceUnit getSourceUnit() {
            return sourceUnit
        }

        @Override
        void visitAnnotations(AnnotatedNode node) {
            checkAnnotated(sourceUnit, node)
            super.visitAnnotations(node)
        }

        @Override
        void visitField(FieldNode node) {
            checkAnnotated(sourceUnit, node)
            super.visitField(node)
        }

        @Override
        void visitProperty(PropertyNode node) {
            checkAnnotated(sourceUnit, node)
            super.visitProperty(node)
        }

        @Override
        void visitMethod(MethodNode node) {
            checkAnnotated(sourceUnit, node)
            if (node.parameters) {
                for (Parameter p : node.parameters) {
                    checkAnnotated(sourceUnit, p)
                }
            }
            super.visitMethod(node)
        }

        @Override
        void visitDeclarationExpression(DeclarationExpression expression) {
            if (expression.isMultipleAssignmentDeclaration()) {
                for (Expression expr : expression.tupleExpression.expressions) {
                    if (expr instanceof VariableExpression) {
                        checkAnnotated(sourceUnit, (VariableExpression) expr)
                    }
                }
            } else {
                VariableExpression var = expression.variableExpression
                if (var != null) {
                    checkAnnotated(sourceUnit, var)
                }
            }
            super.visitDeclarationExpression(expression)
        }
    }
}
