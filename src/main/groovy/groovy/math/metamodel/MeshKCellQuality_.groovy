/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshKCellQuality
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshKCellQuality

@CompileStatic
class MeshKCellQuality_ {
    public static final String ENTITY_NAME = 'MeshKCellQuality'
    public static final String FULL_NAME = 'moqui.math.MeshKCellQuality'

    public static final Attribute<MeshKCellQuality, String> meshKCellQualityId = new Attribute<>('meshKCellQualityId', MeshKCellQuality.class, String.class, true, true)
    public static final Attribute<MeshKCellQuality, String> meshKCellId = new Attribute<>('meshKCellId', MeshKCellQuality.class, String.class, false, true)
    public static final Attribute<MeshKCellQuality, String> description = new Attribute<>('description', MeshKCellQuality.class, String.class, false, false)
    public static final Attribute<MeshKCellQuality, BigDecimal> shapeAspectRatio = new Attribute<>('shapeAspectRatio', MeshKCellQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshKCellQuality, BigDecimal> edgeLengthRatio = new Attribute<>('edgeLengthRatio', MeshKCellQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshKCellQuality, BigDecimal> cellMeasureRatio = new Attribute<>('cellMeasureRatio', MeshKCellQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshKCellQuality, BigDecimal> cellNonOrthogonality = new Attribute<>('cellNonOrthogonality', MeshKCellQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshKCellQuality, BigDecimal> skewness = new Attribute<>('skewness', MeshKCellQuality.class, BigDecimal.class, false, false)
}
