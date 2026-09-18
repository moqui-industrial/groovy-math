/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.entity

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import java.sql.Timestamp

@CompileStatic
@EqualsAndHashCode(includes = ['uomConversionId', 'uomId', 'toUomId', 'fromDate'])
@ToString(includePackage = false, includeNames = true)
final class UomConversionDefinition implements Serializable {
    private static final long serialVersionUID = 1L

    final String uomConversionId
    final String uomId
    final String toUomId
    final Timestamp fromDate
    final Timestamp thruDate
    final Double conversionFactor
    final BigDecimal conversionOffset
    final String purposeEnumId

    UomConversionDefinition(final String uomConversionId,
                            final String uomId,
                            final String toUomId,
                            final Timestamp fromDate = null,
                            final Timestamp thruDate = null,
                            final Double conversionFactor = null,
                            final BigDecimal conversionOffset = null,
                            final String purposeEnumId = null) {
        this.uomConversionId = uomConversionId
        this.uomId = uomId
        this.toUomId = toUomId
        this.fromDate = fromDate
        this.thruDate = thruDate
        this.conversionFactor = conversionFactor
        this.conversionOffset = conversionOffset
        this.purposeEnumId = purposeEnumId
    }
}
