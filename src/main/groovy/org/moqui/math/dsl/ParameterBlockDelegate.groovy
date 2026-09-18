/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.transform.TypeCheckingMode
import org.moqui.math.entity.ModelValue

@CompileStatic
@PackageScope
class ParameterBlockDelegate {
    private final DslRecordDelegate parent

    ParameterBlockDelegate(final DslRecordDelegate parent) {
        this.parent = parent
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object methodMissing(final String name, final Object rawArguments) {
        Object[] args = rawArguments instanceof Object[] ? (Object[]) rawArguments : [rawArguments] as Object[]
        Object val = null
        Map<String, Object> options = [:]
        if (args.length == 2) {
            if (args[0] instanceof Map) {
                options = (Map<String, Object>) args[0]
                val = args[1]
            } else if (args[1] instanceof Map) {
                options = (Map<String, Object>) args[1]
                val = args[0]
            }
        } else if (args.length == 1) {
            if (args[0] instanceof Map) {
                options = (Map<String, Object>) args[0]
                val = options.containsKey('value') ? options.get('value') : null
            } else {
                val = args[0]
            }
        }

        String paramId = "${parent.record.modelKey}.${name.capitalize()}"
        String paramDefId = name
        ModelValue pdef = null
        if (parent.root.mathMeta.hasEntity('ParameterDef')) {
            pdef = parent.root.mathMeta.entity('ParameterDef').find { ModelValue pv ->
                pv.get('parameterCode') == name || pv.modelKey == name || pv.get('parameterName') == name
            }
            if (pdef != null) paramDefId = pdef.modelKey
        }

        Object rawUom = options.get('uom') ?: options.get('uomId') ?: options.get('parameterUomId')
        String uomId = null
        if (rawUom instanceof DslSymbol) uomId = ((DslSymbol) rawUom).id
        else if (rawUom instanceof DslEnumValue) uomId = ((DslEnumValue) rawUom).id
        else if (rawUom instanceof DslDeferredSymbol) uomId = ((DslDeferredSymbol) rawUom).name
        else if (rawUom != null) uomId = rawUom.toString()

        if (pdef != null && pdef.get('uomTypeEnumId') != null && uomId != null) {
            String expectedDimension = pdef.get('uomTypeEnumId') as String
            boolean compatible = org.moqui.math.moqui.MoquiSchemaInspector.embedded().isUomCompatible(uomId, expectedDimension)
            if (!compatible) {
                throw new IllegalArgumentException(
                    "Incompatible unit of measure '${uomId}' for parameter '${name}': parameter expects dimension '${expectedDimension}'")
            }
        }

        Map<String, Object> pVals = [
            parameterId: paramId,
            parameterDefId: paramDefId,
            parameterAlias: name
        ]
        if (uomId != null) {
            pVals.put('parameterUomId', uomId)
        }
        if (parent != null && parent.record != null && parent.record.definition != null) {
            String pEnt = parent.record.definition.fullName
            if (pEnt.endsWith('.MathModel') || pEnt == 'MathModel') {
                pVals.put('mathModelId', parent.record.modelKey)
            } else if (pEnt.endsWith('.Morphism') || pEnt == 'Morphism') {
                pVals.put('morphismId', parent.record.modelKey)
            } else if (pEnt.endsWith('.GraphVertex') || pEnt == 'GraphVertex') {
                pVals.put('graphVertexId', parent.record.modelKey)
            }
        }

        String paramType = pdef != null ? pdef.get('parameterTypeEnumId') as String : null
        if (paramType in ['PtNumber', 'PtNumberInteger', 'PtNumberDecimal', 'PtNumberFloat', 'PtCurrencyAmount', 'PtCurrencyPrecise']) {
            if (val instanceof Number) {
                pVals.put('numericValue', ((Number) val).doubleValue())
            } else if (val != null && val.toString().isNumber()) {
                pVals.put('numericValue', Double.valueOf(val.toString()))
            } else if (val != null) {
                pVals.put('numericValue', val)
            }
        } else if (paramType in ['PtEnumeration', 'PtByte', 'PtBitSet']) {
            String enumVal = null
            if (val instanceof DslEnumValue) enumVal = ((DslEnumValue) val).id
            else if (val instanceof DslSymbol) enumVal = ((DslSymbol) val).id
            else if (val instanceof DslDeferredSymbol) {
                DslDeferredSymbol defSym = (DslDeferredSymbol) val
                DslSymbol sym = parent.root.vocabulary.resolveSymbol(defSym.name, null)
                enumVal = sym != null ? sym.id : defSym.name
            } else if (val != null) {
                DslSymbol sym = parent.root.vocabulary.resolveSymbol(val.toString(), null)
                enumVal = sym != null ? sym.id : val.toString()
            }
            pVals.put('parameterEnumId', enumVal)
        } else if (paramType == 'PtTextLong') {
            pVals.put('textValue', val != null ? val.toString() : null)
        } else {
            if (val instanceof Number) {
                pVals.put('numericValue', ((Number) val).doubleValue())
            } else if (val instanceof DslSymbolicValue) {
                pVals.put('symbolicValue', ((DslSymbolicValue) val).id)
            } else if (val instanceof DslDeferredSymbol) {
                DslDeferredSymbol defSym = (DslDeferredSymbol) val
                String sym = defSym.name
                if (sym == 'Minimize') pVals.put('symbolicValue', 'MINIMIZE')
                else if (sym == 'Maximize') pVals.put('symbolicValue', 'MAXIMIZE')
                else {
                    DslSymbol resolved = parent.root.vocabulary.resolveSymbolForField(sym, paramDefId, null, 'symbolicValue', defSym.sourceFile, defSym.line)
                    pVals.put('symbolicValue', resolved != null ? resolved.id : sym)
                }
            } else if (val instanceof DslSymbol) {
                pVals.put('symbolicValue', ((DslSymbol) val).id)
            } else if (val instanceof DslEnumValue) {
                pVals.put('symbolicValue', ((DslEnumValue) val).id)
            } else if (val instanceof CharSequence) {
                String s = val.toString()
                if (s == 'Minimize') pVals.put('symbolicValue', 'MINIMIZE')
                else if (s == 'Maximize') pVals.put('symbolicValue', 'MAXIMIZE')
                else pVals.put('symbolicValue', s)
            } else if (val != null) {
                pVals.put('symbolicValue', val.toString())
            }
        }
        parent.root.mathMeta.declare('moqui.math.Parameter', paramId, pVals)
        null
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object propertyMissing(final String name) {
        new DslDeferredSymbol(name, MathDslBuilder.getCallerFile(), MathDslBuilder.getCallerLine())
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void propertyMissing(final String name, final Object value) {
        methodMissing(name, value)
    }
}
