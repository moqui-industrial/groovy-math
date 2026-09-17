/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
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
        if (classNode.annotations) {
            for (AnnotationNode ann : classNode.annotations) {
                String annName = ann.classNode?.name ?: 'Unknown'
                if (annName.endsWith('BaseScript') || annName.endsWith('Generated') || annName.endsWith('CompileStatic') || annName.endsWith('CompileDynamic')) continue
                source.addError(new SyntaxException(
                        "Security error: Annotation '@${annName}' is forbidden in Math DSL scripts.",
                        ann.lineNumber, ann.columnNumber))
            }
        }
    }
}
