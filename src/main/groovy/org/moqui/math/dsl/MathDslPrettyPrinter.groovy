/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import org.moqui.math.entity.EntityDefinition
import org.moqui.math.entity.EnumerationDefinition
import org.moqui.math.entity.ModelValue
import org.moqui.math.entity.NamedModelContainer

@CompileStatic
final class MathDslPrettyPrinter {

    private MathDslPrettyPrinter() { }

    static String print(final MathMeta meta) {
        if (meta == null) return ""
        DslVocabulary vocab = DslVocabulary.of(meta.definition)
        StringBuilder sb = new StringBuilder()

        // 1. ParameterDef declarations
        if (meta.hasEntity('ParameterDef')) {
            NamedModelContainer paramDefs = meta.entity('ParameterDef')
            paramDefs.each { ModelValue pd ->
                sb.append("parameterDef('").append(pd.modelKey).append("'")
                appendAttributes(sb, pd, vocab, ['parameterDefId', 'parameterId'], "    ")
                sb.append(")\n\n")
            }
        }

        // 2. MathModelDef declarations
        if (meta.hasEntity('MathModelDef')) {
            NamedModelContainer modelDefs = meta.entity('MathModelDef')
            modelDefs.each { ModelValue md ->
                sb.append("modelDef('").append(md.modelKey).append("'")
                appendAttributes(sb, md, vocab, ['mathModelDefId'], "    ")
                sb.append(") {\n")

                // Pipelines
                if (meta.hasEntity('MathModelDefPipeline')) {
                    NamedModelContainer pipes = meta.entity('MathModelDefPipeline')
                    pipes.findAll { ModelValue p -> p.get('mathModelDefId') == md.modelKey }.each { ModelValue p ->
                        sb.append("    pipeline('").append(p.modelKey).append("'")
                        appendAttributes(sb, p, vocab, ['mathModelDefPipelineId', 'mathModelDefId', 'pipelineId'], "        ")
                        
                        // Check nested transformation
                        String tId = (String) p.get('transformationId')
                        if (tId && meta.hasEntity('Transformation') && meta.entity('Transformation').findByName(tId) != null) {
                            sb.append(") {\n")
                            ModelValue trans = meta.entity('Transformation').findByName(tId)
                            sb.append("        transformation('").append(trans.modelKey).append("'")
                            appendAttributes(sb, trans, vocab, ['transformationId'], "            ")
                            sb.append(") {\n")

                            // Operands
                            if (meta.hasEntity('TransformationOperand')) {
                                meta.entity('TransformationOperand').findAll { ModelValue op -> op.get('transformationId') == trans.modelKey }
                                    .sort { ModelValue a, ModelValue b -> ((Long) (a.get('operandIndex') ?: 0L)) <=> ((Long) (b.get('operandIndex') ?: 0L)) }
                                    .each { ModelValue op ->
                                        String opType = (String) op.get('operandTypeEnumId')
                                        String methodName = opMethodName(opType)
                                        String target = (String) (op.get('operandMatrixId') ?: op.get('operandVectorId') ?: op.get('operandTensorId') ?: op.get('operandParameterId') ?: op.get('operandTransformationId'))
                                        sb.append("            ").append(methodName).append(" '").append(target).append("'\n")
                                    }
                            }
                            sb.append("        }\n")
                            sb.append("    }\n\n")
                        } else {
                            sb.append(")\n\n")
                        }
                    }
                }

                // MathModel children
                if (meta.hasEntity('MathModel')) {
                    NamedModelContainer models = meta.entity('MathModel')
                    models.findAll { ModelValue m -> m.get('mathModelDefId') == md.modelKey || true }.each { ModelValue m ->
                        sb.append("    model('").append(m.modelKey).append("'")
                        appendAttributes(sb, m, vocab, ['mathModelId', 'mathModelDefId'], "        ")
                        sb.append(") {\n")

                        // Parameters
                        if (meta.hasEntity('Parameter')) {
                            meta.entity('Parameter').findAll { ModelValue p -> p.get('mathModelId') == m.modelKey }.each { ModelValue p ->
                                sb.append("        parameters('").append(p.modelKey).append("'")
                                appendAttributes(sb, p, vocab, ['parameterId', 'mathModelId'], "            ")
                                sb.append(")\n")
                            }
                        }

                        // Matrices
                        if (meta.hasEntity('Matrix')) {
                            meta.entity('Matrix').each { ModelValue mat ->
                                sb.append("\n        ").append(mat.modelKey).append(" = matrix(")
                                appendAttributes(sb, mat, vocab, ['matrixId', 'name', 'symbol'], "            ", false)
                                sb.append(")\n")
                            }
                        }

                        // Vectors
                        if (meta.hasEntity('Vector')) {
                            meta.entity('Vector').each { ModelValue vec ->
                                sb.append("\n        ").append(vec.modelKey).append(" = vector(")
                                appendAttributes(sb, vec, vocab, ['vectorId', 'name', 'symbol'], "            ", false)
                                sb.append(")\n")
                            }
                        }

                        // Tensors
                        if (meta.hasEntity('Tensor')) {
                            meta.entity('Tensor').each { ModelValue tns ->
                                sb.append("\n        ").append(tns.modelKey).append(" = tensor(")
                                appendAttributes(sb, tns, vocab, ['tensorId', 'name', 'symbol'], "            ", false)
                                sb.append(")\n")
                            }
                        }

                        sb.append("    }\n")
                    }
                }

                sb.append("}\n")
            }
        }

        sb.toString()
    }

    private static void appendAttributes(final StringBuilder sb, final ModelValue record, final DslVocabulary vocab,
                                         final List<String> excludedKeys, final String indent,
                                         final boolean leadingComma = true) {
        List<String> keys = new ArrayList<String>(record.keySet())
        keys.removeAll(excludedKeys)
        keys.sort()

        if (keys.empty) return
        if (leadingComma) {
            sb.append(",\n")
        } else {
            sb.append("\n")
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i)
            Object val = record.get(key)
            if (val == null) continue

            String displayKey = key
            if (displayKey.endsWith('EnumId')) {
                displayKey = displayKey.substring(0, displayKey.length() - 6)
            } else if (displayKey.endsWith('Id') && !displayKey.equals('id')) {
                // Keep foreign keys like resultMatrixId
            }

            String displayVal = formatPrettyValue(val, key, vocab)
            sb.append(indent).append(displayKey).append(": ").append(displayVal)
            if (i < keys.size() - 1) sb.append(",\n")
        }
    }

    private static String formatPrettyValue(final Object val, final String key, final DslVocabulary vocab) {
        if (val == null) return "null"
        if (val instanceof String) {
            String s = (String) val
            if (s.equals("MINIMIZE")) return "minimise"
            if (s.equals("MAXIMIZE")) return "maximise"
            String sym = vocab.preferredSymbolForId(s)
            if (sym != null) return sym
            return "'" + s.replace("'", "\\'") + "'"
        }
        val.toString()
    }

    private static String opMethodName(final String opTypeEnumId) {
        if (opTypeEnumId == 'TotLeftMatrix') return 'leftMatrix'
        if (opTypeEnumId == 'TotRightMatrix') return 'rightMatrix'
        if (opTypeEnumId == 'TotMatrix') return 'operandMatrix'
        if (opTypeEnumId == 'TotLeftVector') return 'leftVector'
        if (opTypeEnumId == 'TotRightVector') return 'rightVector'
        if (opTypeEnumId == 'TotVector') return 'operandVector'
        if (opTypeEnumId == 'TotLeftTensor') return 'leftTensor'
        if (opTypeEnumId == 'TotRightTensor') return 'rightTensor'
        if (opTypeEnumId == 'TotTensor') return 'operandTensor'
        if (opTypeEnumId == 'TotParameter') return 'operandParameter'
        if (opTypeEnumId == 'TotTransformation') return 'operandTransformation'
        'operand'
    }
}
