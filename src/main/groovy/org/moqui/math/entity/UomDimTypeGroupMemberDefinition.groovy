/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.entity

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@CompileStatic
@EqualsAndHashCode(includes = ['uomDimTypeGroupEnumId', 'uomDimensionTypeId'])
@ToString(includePackage = false, includeNames = true)
final class UomDimTypeGroupMemberDefinition implements Serializable {
    private static final long serialVersionUID = 1L

    final String uomDimTypeGroupEnumId
    final String uomDimensionTypeId
    final Integer sequenceNum

    UomDimTypeGroupMemberDefinition(final String uomDimTypeGroupEnumId,
                                    final String uomDimensionTypeId,
                                    final Integer sequenceNum = null) {
        this.uomDimTypeGroupEnumId = Objects.requireNonNull(uomDimTypeGroupEnumId, 'uomDimTypeGroupEnumId must not be null')
        this.uomDimensionTypeId = Objects.requireNonNull(uomDimensionTypeId, 'uomDimensionTypeId must not be null')
        this.sequenceNum = sequenceNum
    }
}
