/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.entity

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@CompileStatic
@EqualsAndHashCode(includes = ['uomDimensionTypeId'])
@ToString(includePackage = false, includeNames = true)
final class UomDimensionTypeDefinition implements Serializable {
    private static final long serialVersionUID = 1L

    final String uomDimensionTypeId
    final String uomTypeEnumId
    final String defaultUomId
    final String description

    UomDimensionTypeDefinition(final String uomDimensionTypeId,
                               final String uomTypeEnumId = null,
                               final String defaultUomId = null,
                               final String description = null) {
        this.uomDimensionTypeId = Objects.requireNonNull(uomDimensionTypeId, 'uomDimensionTypeId must not be null')
        this.uomTypeEnumId = uomTypeEnumId
        this.defaultUomId = defaultUomId
        this.description = description
    }
}
