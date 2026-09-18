/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.moqui

import org.junit.jupiter.api.Test
import org.moqui.math.entity.EnumerationDefinition
import org.moqui.math.entity.ModelDefinition
import org.moqui.math.entity.StatusDefinition
import org.moqui.math.entity.StatusTransitionDefinition
import org.moqui.math.entity.UomConversionDefinition

import java.math.BigDecimal
import java.sql.Timestamp

import static org.junit.jupiter.api.Assertions.*

class MoquiLookupsTest {

    @Test
    void testEnumerationsByType() {
        ModelDefinition model = MoquiSchemaInspector.embedded()

        List<EnumerationDefinition> list = model.enumerationsByType('MathModelType')
        assertNotNull(list)
        assertFalse(list.isEmpty())
        assertTrue(list.any { it.enumId == 'MmtLp' })
        assertTrue(list.any { it.enumId == 'MmtQp' })
        assertEquals(list*.description, list*.description.sort())
    }

    @Test
    void testEnumerationsByParent() {
        ModelDefinition model = MoquiSchemaInspector.embedded()

        List<EnumerationDefinition> foamNested = model.enumerationsByParent('MmsmOpenFoam', true, true)
        Set<String> foamIds = foamNested.collect { it.enumId } as Set<String>
        assertTrue(foamIds.contains('MmsmOpenFoam'))
        assertTrue(foamIds.contains('MmsmOpenFoamIcoFoam'))
        assertTrue(foamIds.contains('MmsmOpenFoamSimpleFoam'))

        // Recursive hierarchy on MathModelType
        List<EnumerationDefinition> shallow = model.enumerationsByParent('MmtModelDriven', false, false)
        assertFalse(shallow.any { it.enumId == 'MmtCFD' })

        List<EnumerationDefinition> deep = model.enumerationsByParent('MmtModelDriven', false, true)
        assertTrue(deep.any { it.enumId == 'MmtCFD' })
    }

    @Test
    void testStatusFlowAndTransitions() {
        ModelDefinition model = MoquiSchemaInspector.embedded()

        List<StatusDefinition> statuses = model.statusItemsByType('MathModelDef')
        assertNotNull(statuses)
        assertFalse(statuses.isEmpty())
        assertEquals('MathModelDraft', statuses.first().statusId)

        String initial = model.initialStatus('MathModelStatusFlow')
        assertEquals('MathModelDraft', initial)

        List<StatusTransitionDefinition> transitions = model.transitionsFrom('MathModelStatusFlow', 'MathModelDraft')
        assertNotNull(transitions)
        assertFalse(transitions.isEmpty())
        Set<String> targetStatusIds = transitions.collect { it.toStatusId } as Set<String>
        assertTrue(targetStatusIds.contains('MathModelInReview'))
        assertTrue(targetStatusIds.contains('MathModelTested'))
        assertTrue(targetStatusIds.contains('MathModelCancelled'))
    }

    @Test
    void testUomConvertIdentityAndMissing() {
        ModelDefinition model = MoquiSchemaInspector.embedded()

        // Null amount
        assertNull(model.uomConvert(null, 'TF_degC', 'TF_degC'))

        // Identity
        BigDecimal amt = new BigDecimal('42.50')
        assertEquals(amt, model.uomConvert(amt, 'TF_degC', 'TF_degC'))

        // Missing conversion
        assertThrows(IllegalArgumentException) {
            model.uomConvert(amt, 'TF_degC', 'NON_EXISTENT_UOM')
        }
    }

    @Test
    void testUomConvertDirectAndInverseWithOffset() {
        ModelDefinition model = new ModelDefinition()

        // 0 C -> 32 F, 100 C -> 212 F (F = C * 1.8 + 32)
        model.addUomConversion(new UomConversionDefinition(
            'C_TO_F',
            'TF_degC',
            'TF_degF',
            Timestamp.valueOf('2026-01-01 00:00:00'),
            null,
            1.8d,
            new BigDecimal('32.0'),
            null
        ))

        // Direct: C -> F
        BigDecimal degC0 = new BigDecimal('0')
        BigDecimal degF32 = model.uomConvert(degC0, 'TF_degC', 'TF_degF', Timestamp.valueOf('2026-06-01 00:00:00'))
        assertEquals(new BigDecimal('32.0'), degF32)

        BigDecimal degC100 = new BigDecimal('100')
        BigDecimal degF212 = model.uomConvert(degC100, 'TF_degC', 'TF_degF', Timestamp.valueOf('2026-06-01 00:00:00'))
        assertEquals(new BigDecimal('212.0'), degF212)

        // Inverse: F -> C
        BigDecimal fToC = model.uomConvert(new BigDecimal('212.0'), 'TF_degF', 'TF_degC', Timestamp.valueOf('2026-06-01 00:00:00'))
        assertEquals(0, new BigDecimal('100').compareTo(fToC))

        BigDecimal f32ToC = model.uomConvert(new BigDecimal('32.0'), 'TF_degF', 'TF_degC', Timestamp.valueOf('2026-06-01 00:00:00'))
        assertEquals(0, BigDecimal.ZERO.compareTo(f32ToC))
    }
}
