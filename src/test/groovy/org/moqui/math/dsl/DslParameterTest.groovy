/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.moqui.math.entity.ModelValue

import static org.junit.jupiter.api.Assertions.*

class DslParameterTest {

    @Test
    void parameterValuesMappedAccordingToParameterType() {
        String script = '''
            ParameterDef('NumParam', type: NumberDecimal, purpose: MathModel, code: 'numVal', name: 'Numeric Parameter')
            ParameterDef('EnumParam', type: PtEnumeration, purpose: MathModel, code: 'enumVal', name: 'Enum Parameter')
            ParameterDef('TextParam', type: TextShort, purpose: MathModel, code: 'textVal', name: 'Text Parameter')

            MathModelDef('TestDef', type: LinearProgram, usage: Optimisation, modelName: 'Test') {
                MathModel('TestModel', status: Draft) {
                    parameters {
                        numVal(42.5)
                        enumVal(Simplex)
                        textVal('CustomText')
                    }
                }
            }
        '''
        MathMeta meta = MathDsl.evaluate(script)

        ModelValue numParam = meta.entity('Parameter').find { it.parameterDefId == 'NumParam' || it.parameterAlias == 'numVal' }
        assertNotNull(numParam)
        assertEquals(42.5d, ((Number) numParam.get('numericValue')).doubleValue(), 1e-6)
        assertNull(numParam.get('symbolicValue'))
        assertNull(numParam.get('parameterEnumId'))

        ModelValue enumParam = meta.entity('Parameter').find { it.parameterDefId == 'EnumParam' || it.parameterAlias == 'enumVal' }
        assertNotNull(enumParam)
        assertEquals('MmsmSimplex', enumParam.get('parameterEnumId'))

        ModelValue textParam = meta.entity('Parameter').find { it.parameterDefId == 'TextParam' || it.parameterAlias == 'textVal' }
        assertNotNull(textParam)
        assertEquals('CustomText', textParam.get('symbolicValue'))
    }

    @Test
    void parametersRetrievedByParameterCode() {
        String script = '''
            ParameterDef('ToleranceDef', type: NumberFloat, purpose: SolverControl, code: 'tolerance', name: 'Residual Tolerance')

            MathModelDef('SolverDef', type: LinearProgram, usage: Optimisation, modelName: 'Solver') {
                MathModel('SolverModel', status: Draft) {
                    parameters {
                        tolerance(1e-4)
                    }
                }
            }
        '''
        MathMeta meta = MathDsl.evaluate(script)

        ModelValue param = meta.entity('Parameter').find { it.get('parameterAlias') == 'tolerance' }
        assertNotNull(param)
        assertEquals('ToleranceDef', param.get('parameterDefId'))
        assertEquals(1e-4d, ((Number) param.get('numericValue')).doubleValue(), 1e-7)
    }

    @Test
    void parameterWithUomBinding() {
        String script = '''
            ParameterDef('TempParam', type: NumberFloat, purpose: MathModel, code: 'temp', uomType: Temperature, name: 'Temperature')

            MathModelDef('ThermoDef', type: LinearProgram, usage: Optimisation, modelName: 'Thermo') {
                MathModel('ThermodynamicModel', status: Draft) {
                    parameters {
                        temp(300.0, uom: 'TEMP_K')
                    }
                }
            }
        '''
        MathMeta meta = MathDsl.evaluate(script)

        ModelValue param = meta.entity('Parameter').find { it.get('parameterAlias') == 'temp' }
        assertNotNull(param)
        assertEquals('TEMP_K', param.get('parameterUomId'))
        assertEquals(300.0d, ((Number) param.get('numericValue')).doubleValue(), 1e-6)
    }
}
