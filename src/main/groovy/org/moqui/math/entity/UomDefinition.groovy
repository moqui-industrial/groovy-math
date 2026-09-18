/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.entity

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@CompileStatic
@EqualsAndHashCode(includes = ['uomId'])
@ToString(includePackage = false, includeNames = true)
final class UomDefinition implements Serializable {
    private static final long serialVersionUID = 1L

    final String uomId
    final String uomTypeEnumId
    final String abbreviation
    final String description
    final Integer fractionDigits
    final String symbol

    UomDefinition(final String uomId,
                  final String uomTypeEnumId = null,
                  final String abbreviation = null,
                  final String description = null,
                  final Integer fractionDigits = null,
                  final String symbol = null) {
        this.uomId = Objects.requireNonNull(uomId, 'uomId must not be null')
        this.uomTypeEnumId = uomTypeEnumId
        this.abbreviation = abbreviation
        this.description = description
        this.fractionDigits = fractionDigits
        this.symbol = symbol
    }
}
