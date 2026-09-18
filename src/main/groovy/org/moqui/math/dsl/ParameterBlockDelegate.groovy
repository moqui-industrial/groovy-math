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
        Object val = args.length > 0 ? args[0] : null
        String paramId = "${parent.record.modelKey}.${name.capitalize()}"
        String paramDefId = name
        if (parent.root.mathMeta.hasEntity('ParameterDef')) {
            def pdef = parent.root.mathMeta.entity('ParameterDef').find { ModelValue pv ->
                pv.get('parameterCode') == name || pv.modelKey == name || pv.get('parameterName') == name
            }
            if (pdef != null) paramDefId = pdef.modelKey
        }
        Map<String, Object> pVals = [
            parameterId: paramId,
            parameterDefId: paramDefId,
            parameterAlias: name
        ]
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
        if (val instanceof Number) {
            pVals.put('numericValue', ((Number) val).doubleValue())
        } else if (val instanceof DslSymbolicValue) {
            pVals.put('symbolicValue', ((DslSymbolicValue) val).id)
        } else if (val instanceof DslDeferredSymbol) {
            String sym = ((DslDeferredSymbol) val).name
            if (sym == 'Minimize') pVals.put('symbolicValue', 'MINIMIZE')
            else if (sym == 'Maximize') pVals.put('symbolicValue', 'MAXIMIZE')
            else {
                DslSymbol resolved = parent.root.vocabulary.resolveSymbol(sym)
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
            else pVals.put('textValue', s)
        } else if (val != null) {
            pVals.put('symbolicValue', val.toString())
        }
        parent.root.mathMeta.declare('moqui.math.Parameter', paramId, pVals)
        null
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    Object propertyMissing(final String name) {
        new DslDeferredSymbol(name)
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void propertyMissing(final String name, final Object value) {
        methodMissing(name, value)
    }
}
