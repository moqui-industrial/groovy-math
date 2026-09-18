/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.dsl

import org.junit.jupiter.api.Test
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.ModelValue
import org.moqui.math.moqui.MoquiSchemaInspector

import static org.junit.jupiter.api.Assertions.*

class DslUomDimensionTest {

    @Test
    void validUomAccepted() {
        String script = '''
            ParameterDef('TemperatureParam', type: NumberFloat, purpose: MathModel,
                code: 'ambientTemp', uomType: Temperature, name: 'Ambient Temperature')

            MathModelDef('ThermalDef', type: LinearProgram, usage: Optimisation, modelName: 'Thermal') {
                MathModel('ThermalModel', status: Draft) {
                    parameters {
                        ambientTemp(293.15, uom: 'TEMP_K')
                    }
                }
            }
        '''
        MathMeta meta = MathDsl.evaluate(script)
        ModelValue param = meta.entity('Parameter').find { it.get('parameterAlias') == 'ambientTemp' }
        assertNotNull(param)
        assertEquals('TEMP_K', param.get('parameterUomId'))
        assertEquals(293.15d, ((Number) param.get('numericValue')).doubleValue(), 1e-6)
    }

    @Test
    void incompatibleUomDimensionRejected() {
        String script = '''
            ParameterDef('TemperatureParam', type: NumberFloat, purpose: MathModel,
                code: 'ambientTemp', uomType: Temperature, name: 'Ambient Temperature')

            MathModelDef('ThermalDef', type: LinearProgram, usage: Optimisation, modelName: 'Thermal') {
                MathModel('ThermalModel', status: Draft) {
                    parameters {
                        ambientTemp(100.0, uom: 'LEN_m')
                    }
                }
            }
        '''
        IllegalArgumentException err = assertThrows(IllegalArgumentException) {
            MathDsl.evaluate(script)
        }
        assertTrue(err.message.contains('Incompatible unit of measure') || err.message.contains('incompatible'))
        assertTrue(err.message.contains('LEN_m'))
    }

    @Test
    void uomConversionAppliedWhenRequested() {
        ModelDefinition schema = MoquiSchemaInspector.embedded()
        BigDecimal converted = schema.uomConvert(new BigDecimal('100.0'), 'TEMP_C', 'TEMP_F')
        assertEquals(new BigDecimal('212.00'), converted.setScale(2, java.math.RoundingMode.HALF_UP))

        BigDecimal kelvin = schema.uomConvert(new BigDecimal('0.0'), 'TEMP_C', 'TEMP_K')
        assertEquals(new BigDecimal('273.15'), kelvin.setScale(2, java.math.RoundingMode.HALF_UP))
    }
}
