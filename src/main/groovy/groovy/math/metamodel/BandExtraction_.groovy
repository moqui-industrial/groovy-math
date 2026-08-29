/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.BandExtraction
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.BandExtraction

@CompileStatic
class BandExtraction_ {
    public static final String ENTITY_NAME = 'BandExtraction'
    public static final String FULL_NAME = 'moqui.math.BandExtraction'

    public static final Attribute<BandExtraction, String> transformationId = new Attribute<>('transformationId', BandExtraction.class, String.class, true, true)
    public static final Attribute<BandExtraction, Long> lowerBand = new Attribute<>('lowerBand', BandExtraction.class, Long.class, false, false)
    public static final Attribute<BandExtraction, Long> upperBand = new Attribute<>('upperBand', BandExtraction.class, Long.class, false, false)
}
