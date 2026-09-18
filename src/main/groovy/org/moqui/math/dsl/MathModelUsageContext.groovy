/*
 * Generated domain enum for Moqui Math Metamodel
 * EnumerationType: MathModelUsageContext
 */
package org.moqui.math.dsl

import groovy.transform.CompileStatic

@CompileStatic
enum MathModelUsageContext implements DslEnumValue {
    Analysis('MmucAnalysis', '', 'Analysis', ''),
    Optimisation('MmucOptimisation', '', 'Optimisation', ''),
    Regression('MmucRegression', '', 'Regression', ''),
    Training('MmucTraining', '', 'Training', ''),
    Test('MmucTest', '', 'Test', ''),
    Inference('MmucInference', '', 'Inference', '');

    final String id
    final String enumCode
    final String description
    final String parentEnumId

    MathModelUsageContext(final String id, final String enumCode = null, final String description = null, final String parentEnumId = null) {
        this.id = id
        this.enumCode = enumCode
        this.description = description
        this.parentEnumId = parentEnumId
    }

    @Override
    String getId() { id }

    @Override
    String getEnumCode() { enumCode }

    @Override
    String getDescription() { description }

    @Override
    String getParentEnumId() { parentEnumId }

    static MathModelUsageContext fromId(final String id) {
        if (id == null) return null
        for (MathModelUsageContext val : values()) {
            if (val.id == id) return val
        }
        null
    }

    static MathModelUsageContext fromCode(final String code) {
        if (code == null) return null
        for (MathModelUsageContext val : values()) {
            if (val.enumCode == code) return val
        }
        null
    }
}
