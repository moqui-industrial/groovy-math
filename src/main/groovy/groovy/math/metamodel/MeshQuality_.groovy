/*
 * Canonical Static Metamodel for Moqui Math Entity: moqui.math.MeshQuality
 * JPA Criteria-style Metamodel Descriptor
 */
package groovy.math.metamodel

import groovy.transform.CompileStatic
import groovy.math.model.MeshQuality

@CompileStatic
class MeshQuality_ {
    public static final String ENTITY_NAME = 'MeshQuality'
    public static final String FULL_NAME = 'moqui.math.MeshQuality'

    public static final Attribute<MeshQuality, String> meshQualityId = new Attribute<>('meshQualityId', MeshQuality.class, String.class, true, true)
    public static final Attribute<MeshQuality, String> meshId = new Attribute<>('meshId', MeshQuality.class, String.class, false, true)
    public static final Attribute<MeshQuality, String> description = new Attribute<>('description', MeshQuality.class, String.class, false, false)
    public static final Attribute<MeshQuality, Long> totalKCells = new Attribute<>('totalKCells', MeshQuality.class, Long.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> shapeAspectRatioMin = new Attribute<>('shapeAspectRatioMin', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> shapeAspectRatioMax = new Attribute<>('shapeAspectRatioMax', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> shapeAspectRatioAvg = new Attribute<>('shapeAspectRatioAvg', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> shapeAspectRatioStdDev = new Attribute<>('shapeAspectRatioStdDev', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> shapeAspectRatioVariance = new Attribute<>('shapeAspectRatioVariance', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> edgeLengthRatioMin = new Attribute<>('edgeLengthRatioMin', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> edgeLengthRatioMax = new Attribute<>('edgeLengthRatioMax', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> edgeLengthRatioAvg = new Attribute<>('edgeLengthRatioAvg', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> edgeLengthRatioStdDev = new Attribute<>('edgeLengthRatioStdDev', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> edgeLengthRatioVariance = new Attribute<>('edgeLengthRatioVariance', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellMeasureRatioMin = new Attribute<>('cellMeasureRatioMin', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellMeasureRatioMax = new Attribute<>('cellMeasureRatioMax', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellMeasureRatioAvg = new Attribute<>('cellMeasureRatioAvg', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellMeasureRatioStdDev = new Attribute<>('cellMeasureRatioStdDev', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellMeasureRatioVariance = new Attribute<>('cellMeasureRatioVariance', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellNonOrthogonalityMin = new Attribute<>('cellNonOrthogonalityMin', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellNonOrthogonalityMax = new Attribute<>('cellNonOrthogonalityMax', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellNonOrthogonalityAvg = new Attribute<>('cellNonOrthogonalityAvg', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellNonOrthogonalityStdDev = new Attribute<>('cellNonOrthogonalityStdDev', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> cellNonOrthogonalityVariance = new Attribute<>('cellNonOrthogonalityVariance', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> skewnessMin = new Attribute<>('skewnessMin', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> skewnessMax = new Attribute<>('skewnessMax', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> skewnessAvg = new Attribute<>('skewnessAvg', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> skewnessStdDev = new Attribute<>('skewnessStdDev', MeshQuality.class, BigDecimal.class, false, false)
    public static final Attribute<MeshQuality, BigDecimal> skewnessVariance = new Attribute<>('skewnessVariance', MeshQuality.class, BigDecimal.class, false, false)
}
