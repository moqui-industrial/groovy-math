/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.NormResult
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.NormResult

@CompileStatic
class NormResult_ {
    public static final String ENTITY_NAME = 'NormResult'
    public static final String FULL_NAME = 'moqui.math.NormResult'

    public static final Attribute<NormResult, String> transformationId = new Attribute<>('transformationId', NormResult.class, String.class, true, true)
    public static final Attribute<NormResult, String> domainEnumId = new Attribute<>('domainEnumId', NormResult.class, String.class, false, true)
    public static final Attribute<NormResult, String> orderEnumId = new Attribute<>('orderEnumId', NormResult.class, String.class, false, true)
    public static final Attribute<NormResult, String> reductionDimensions = new Attribute<>('reductionDimensions', NormResult.class, String.class, false, false)
    public static final Attribute<NormResult, String> keepDimensions = new Attribute<>('keepDimensions', NormResult.class, String.class, false, false)
    public static final Attribute<NormResult, BigDecimal> normValue = new Attribute<>('normValue', NormResult.class, BigDecimal.class, false, true)
}
