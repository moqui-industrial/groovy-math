/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshKCellIncidence
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshKCellIncidence

@CompileStatic
class MeshKCellIncidence_ {
    public static final String ENTITY_NAME = 'MeshKCellIncidence'
    public static final String FULL_NAME = 'moqui.math.MeshKCellIncidence'

    public static final Attribute<MeshKCellIncidence, String> higherCellId = new Attribute<>('higherCellId', MeshKCellIncidence.class, String.class, true, true)
    public static final Attribute<MeshKCellIncidence, String> lowerCellId = new Attribute<>('lowerCellId', MeshKCellIncidence.class, String.class, true, true)
    public static final Attribute<MeshKCellIncidence, Long> sequenceNum = new Attribute<>('sequenceNum', MeshKCellIncidence.class, Long.class, false, false)
    public static final Attribute<MeshKCellIncidence, String> orientation = new Attribute<>('orientation', MeshKCellIncidence.class, String.class, false, false)
}
