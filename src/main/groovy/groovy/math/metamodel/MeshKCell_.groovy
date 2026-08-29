/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshKCell
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshKCell

@CompileStatic
class MeshKCell_ {
    public static final String ENTITY_NAME = 'MeshKCell'
    public static final String FULL_NAME = 'moqui.math.MeshKCell'

    public static final Attribute<MeshKCell, String> meshKCellId = new Attribute<>('meshKCellId', MeshKCell.class, String.class, true, true)
    public static final Attribute<MeshKCell, String> meshId = new Attribute<>('meshId', MeshKCell.class, String.class, false, true)
    public static final Attribute<MeshKCell, String> kCellTypeEnumId = new Attribute<>('kCellTypeEnumId', MeshKCell.class, String.class, false, false)
    public static final Attribute<MeshKCell, Long> dimension = new Attribute<>('dimension', MeshKCell.class, Long.class, false, true)
    public static final Attribute<MeshKCell, String> label = new Attribute<>('label', MeshKCell.class, String.class, false, false)
    public static final Attribute<MeshKCell, String> description = new Attribute<>('description', MeshKCell.class, String.class, false, false)
    public static final Attribute<MeshKCell, BigDecimal> measure = new Attribute<>('measure', MeshKCell.class, BigDecimal.class, false, false)
    public static final Attribute<MeshKCell, String> isBoundary = new Attribute<>('isBoundary', MeshKCell.class, String.class, false, false)
    public static final Attribute<MeshKCell, String> orientationEnumId = new Attribute<>('orientationEnumId', MeshKCell.class, String.class, false, false)
    public static final Attribute<MeshKCell, String> normalVectorId = new Attribute<>('normalVectorId', MeshKCell.class, String.class, false, false)
    public static final Attribute<MeshKCell, String> centroidVectorId = new Attribute<>('centroidVectorId', MeshKCell.class, String.class, false, false)
}
