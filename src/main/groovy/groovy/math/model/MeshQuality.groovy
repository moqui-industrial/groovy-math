/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshQuality
 */
package groovy.math.model

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AutoClone
import java.util.Map
import java.util.List
import java.util.ArrayList

@CompileStatic
@EqualsAndHashCode(includes = ['meshQualityId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MeshQuality implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshQualityId */
    String meshQualityId

    /** meshId */
    String meshId

    /** description */
    String description

    /** totalKCells */
    Long totalKCells

    /** shapeAspectRatioMin */
    BigDecimal shapeAspectRatioMin

    /** shapeAspectRatioMax */
    BigDecimal shapeAspectRatioMax

    /** shapeAspectRatioAvg */
    BigDecimal shapeAspectRatioAvg

    /** shapeAspectRatioStdDev */
    BigDecimal shapeAspectRatioStdDev

    /** shapeAspectRatioVariance */
    BigDecimal shapeAspectRatioVariance

    /** edgeLengthRatioMin */
    BigDecimal edgeLengthRatioMin

    /** edgeLengthRatioMax */
    BigDecimal edgeLengthRatioMax

    /** edgeLengthRatioAvg */
    BigDecimal edgeLengthRatioAvg

    /** edgeLengthRatioStdDev */
    BigDecimal edgeLengthRatioStdDev

    /** edgeLengthRatioVariance */
    BigDecimal edgeLengthRatioVariance

    /** cellMeasureRatioMin */
    BigDecimal cellMeasureRatioMin

    /** cellMeasureRatioMax */
    BigDecimal cellMeasureRatioMax

    /** cellMeasureRatioAvg */
    BigDecimal cellMeasureRatioAvg

    /** cellMeasureRatioStdDev */
    BigDecimal cellMeasureRatioStdDev

    /** cellMeasureRatioVariance */
    BigDecimal cellMeasureRatioVariance

    /** cellNonOrthogonalityMin */
    BigDecimal cellNonOrthogonalityMin

    /** cellNonOrthogonalityMax */
    BigDecimal cellNonOrthogonalityMax

    /** cellNonOrthogonalityAvg */
    BigDecimal cellNonOrthogonalityAvg

    /** cellNonOrthogonalityStdDev */
    BigDecimal cellNonOrthogonalityStdDev

    /** cellNonOrthogonalityVariance */
    BigDecimal cellNonOrthogonalityVariance

    /** skewnessMin */
    BigDecimal skewnessMin

    /** skewnessMax */
    BigDecimal skewnessMax

    /** skewnessAvg */
    BigDecimal skewnessAvg

    /** skewnessStdDev */
    BigDecimal skewnessStdDev

    /** skewnessVariance */
    BigDecimal skewnessVariance

    Mesh mesh

    MeshQuality() {}

    MeshQuality(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshQualityId')) this.meshQualityId = args.get('meshQualityId')?.toString()
            if (args.containsKey('meshId')) this.meshId = args.get('meshId')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('totalKCells')) this.totalKCells = args.get('totalKCells') != null ? ((Number) args.get('totalKCells')).longValue() : null
            if (args.containsKey('shapeAspectRatioMin')) this.shapeAspectRatioMin = args.get('shapeAspectRatioMin') != null ? (args.get('shapeAspectRatioMin') instanceof BigDecimal ? (BigDecimal) args.get('shapeAspectRatioMin') : new BigDecimal(args.get('shapeAspectRatioMin').toString())) : null
            if (args.containsKey('shapeAspectRatioMax')) this.shapeAspectRatioMax = args.get('shapeAspectRatioMax') != null ? (args.get('shapeAspectRatioMax') instanceof BigDecimal ? (BigDecimal) args.get('shapeAspectRatioMax') : new BigDecimal(args.get('shapeAspectRatioMax').toString())) : null
            if (args.containsKey('shapeAspectRatioAvg')) this.shapeAspectRatioAvg = args.get('shapeAspectRatioAvg') != null ? (args.get('shapeAspectRatioAvg') instanceof BigDecimal ? (BigDecimal) args.get('shapeAspectRatioAvg') : new BigDecimal(args.get('shapeAspectRatioAvg').toString())) : null
            if (args.containsKey('shapeAspectRatioStdDev')) this.shapeAspectRatioStdDev = args.get('shapeAspectRatioStdDev') != null ? (args.get('shapeAspectRatioStdDev') instanceof BigDecimal ? (BigDecimal) args.get('shapeAspectRatioStdDev') : new BigDecimal(args.get('shapeAspectRatioStdDev').toString())) : null
            if (args.containsKey('shapeAspectRatioVariance')) this.shapeAspectRatioVariance = args.get('shapeAspectRatioVariance') != null ? (args.get('shapeAspectRatioVariance') instanceof BigDecimal ? (BigDecimal) args.get('shapeAspectRatioVariance') : new BigDecimal(args.get('shapeAspectRatioVariance').toString())) : null
            if (args.containsKey('edgeLengthRatioMin')) this.edgeLengthRatioMin = args.get('edgeLengthRatioMin') != null ? (args.get('edgeLengthRatioMin') instanceof BigDecimal ? (BigDecimal) args.get('edgeLengthRatioMin') : new BigDecimal(args.get('edgeLengthRatioMin').toString())) : null
            if (args.containsKey('edgeLengthRatioMax')) this.edgeLengthRatioMax = args.get('edgeLengthRatioMax') != null ? (args.get('edgeLengthRatioMax') instanceof BigDecimal ? (BigDecimal) args.get('edgeLengthRatioMax') : new BigDecimal(args.get('edgeLengthRatioMax').toString())) : null
            if (args.containsKey('edgeLengthRatioAvg')) this.edgeLengthRatioAvg = args.get('edgeLengthRatioAvg') != null ? (args.get('edgeLengthRatioAvg') instanceof BigDecimal ? (BigDecimal) args.get('edgeLengthRatioAvg') : new BigDecimal(args.get('edgeLengthRatioAvg').toString())) : null
            if (args.containsKey('edgeLengthRatioStdDev')) this.edgeLengthRatioStdDev = args.get('edgeLengthRatioStdDev') != null ? (args.get('edgeLengthRatioStdDev') instanceof BigDecimal ? (BigDecimal) args.get('edgeLengthRatioStdDev') : new BigDecimal(args.get('edgeLengthRatioStdDev').toString())) : null
            if (args.containsKey('edgeLengthRatioVariance')) this.edgeLengthRatioVariance = args.get('edgeLengthRatioVariance') != null ? (args.get('edgeLengthRatioVariance') instanceof BigDecimal ? (BigDecimal) args.get('edgeLengthRatioVariance') : new BigDecimal(args.get('edgeLengthRatioVariance').toString())) : null
            if (args.containsKey('cellMeasureRatioMin')) this.cellMeasureRatioMin = args.get('cellMeasureRatioMin') != null ? (args.get('cellMeasureRatioMin') instanceof BigDecimal ? (BigDecimal) args.get('cellMeasureRatioMin') : new BigDecimal(args.get('cellMeasureRatioMin').toString())) : null
            if (args.containsKey('cellMeasureRatioMax')) this.cellMeasureRatioMax = args.get('cellMeasureRatioMax') != null ? (args.get('cellMeasureRatioMax') instanceof BigDecimal ? (BigDecimal) args.get('cellMeasureRatioMax') : new BigDecimal(args.get('cellMeasureRatioMax').toString())) : null
            if (args.containsKey('cellMeasureRatioAvg')) this.cellMeasureRatioAvg = args.get('cellMeasureRatioAvg') != null ? (args.get('cellMeasureRatioAvg') instanceof BigDecimal ? (BigDecimal) args.get('cellMeasureRatioAvg') : new BigDecimal(args.get('cellMeasureRatioAvg').toString())) : null
            if (args.containsKey('cellMeasureRatioStdDev')) this.cellMeasureRatioStdDev = args.get('cellMeasureRatioStdDev') != null ? (args.get('cellMeasureRatioStdDev') instanceof BigDecimal ? (BigDecimal) args.get('cellMeasureRatioStdDev') : new BigDecimal(args.get('cellMeasureRatioStdDev').toString())) : null
            if (args.containsKey('cellMeasureRatioVariance')) this.cellMeasureRatioVariance = args.get('cellMeasureRatioVariance') != null ? (args.get('cellMeasureRatioVariance') instanceof BigDecimal ? (BigDecimal) args.get('cellMeasureRatioVariance') : new BigDecimal(args.get('cellMeasureRatioVariance').toString())) : null
            if (args.containsKey('cellNonOrthogonalityMin')) this.cellNonOrthogonalityMin = args.get('cellNonOrthogonalityMin') != null ? (args.get('cellNonOrthogonalityMin') instanceof BigDecimal ? (BigDecimal) args.get('cellNonOrthogonalityMin') : new BigDecimal(args.get('cellNonOrthogonalityMin').toString())) : null
            if (args.containsKey('cellNonOrthogonalityMax')) this.cellNonOrthogonalityMax = args.get('cellNonOrthogonalityMax') != null ? (args.get('cellNonOrthogonalityMax') instanceof BigDecimal ? (BigDecimal) args.get('cellNonOrthogonalityMax') : new BigDecimal(args.get('cellNonOrthogonalityMax').toString())) : null
            if (args.containsKey('cellNonOrthogonalityAvg')) this.cellNonOrthogonalityAvg = args.get('cellNonOrthogonalityAvg') != null ? (args.get('cellNonOrthogonalityAvg') instanceof BigDecimal ? (BigDecimal) args.get('cellNonOrthogonalityAvg') : new BigDecimal(args.get('cellNonOrthogonalityAvg').toString())) : null
            if (args.containsKey('cellNonOrthogonalityStdDev')) this.cellNonOrthogonalityStdDev = args.get('cellNonOrthogonalityStdDev') != null ? (args.get('cellNonOrthogonalityStdDev') instanceof BigDecimal ? (BigDecimal) args.get('cellNonOrthogonalityStdDev') : new BigDecimal(args.get('cellNonOrthogonalityStdDev').toString())) : null
            if (args.containsKey('cellNonOrthogonalityVariance')) this.cellNonOrthogonalityVariance = args.get('cellNonOrthogonalityVariance') != null ? (args.get('cellNonOrthogonalityVariance') instanceof BigDecimal ? (BigDecimal) args.get('cellNonOrthogonalityVariance') : new BigDecimal(args.get('cellNonOrthogonalityVariance').toString())) : null
            if (args.containsKey('skewnessMin')) this.skewnessMin = args.get('skewnessMin') != null ? (args.get('skewnessMin') instanceof BigDecimal ? (BigDecimal) args.get('skewnessMin') : new BigDecimal(args.get('skewnessMin').toString())) : null
            if (args.containsKey('skewnessMax')) this.skewnessMax = args.get('skewnessMax') != null ? (args.get('skewnessMax') instanceof BigDecimal ? (BigDecimal) args.get('skewnessMax') : new BigDecimal(args.get('skewnessMax').toString())) : null
            if (args.containsKey('skewnessAvg')) this.skewnessAvg = args.get('skewnessAvg') != null ? (args.get('skewnessAvg') instanceof BigDecimal ? (BigDecimal) args.get('skewnessAvg') : new BigDecimal(args.get('skewnessAvg').toString())) : null
            if (args.containsKey('skewnessStdDev')) this.skewnessStdDev = args.get('skewnessStdDev') != null ? (args.get('skewnessStdDev') instanceof BigDecimal ? (BigDecimal) args.get('skewnessStdDev') : new BigDecimal(args.get('skewnessStdDev').toString())) : null
            if (args.containsKey('skewnessVariance')) this.skewnessVariance = args.get('skewnessVariance') != null ? (args.get('skewnessVariance') instanceof BigDecimal ? (BigDecimal) args.get('skewnessVariance') : new BigDecimal(args.get('skewnessVariance').toString())) : null
        }
    }

    MeshQuality meshQualityId(String value) {
        this.meshQualityId = value
        return this;
    }

    MeshQuality meshId(String value) {
        this.meshId = value
        return this;
    }

    MeshQuality description(String value) {
        this.description = value
        return this;
    }

    MeshQuality totalKCells(Long value) {
        this.totalKCells = value
        return this;
    }

    MeshQuality shapeAspectRatioMin(BigDecimal value) {
        this.shapeAspectRatioMin = value
        return this;
    }

    MeshQuality shapeAspectRatioMax(BigDecimal value) {
        this.shapeAspectRatioMax = value
        return this;
    }

    MeshQuality shapeAspectRatioAvg(BigDecimal value) {
        this.shapeAspectRatioAvg = value
        return this;
    }

    MeshQuality shapeAspectRatioStdDev(BigDecimal value) {
        this.shapeAspectRatioStdDev = value
        return this;
    }

    MeshQuality shapeAspectRatioVariance(BigDecimal value) {
        this.shapeAspectRatioVariance = value
        return this;
    }

    MeshQuality edgeLengthRatioMin(BigDecimal value) {
        this.edgeLengthRatioMin = value
        return this;
    }

    MeshQuality edgeLengthRatioMax(BigDecimal value) {
        this.edgeLengthRatioMax = value
        return this;
    }

    MeshQuality edgeLengthRatioAvg(BigDecimal value) {
        this.edgeLengthRatioAvg = value
        return this;
    }

    MeshQuality edgeLengthRatioStdDev(BigDecimal value) {
        this.edgeLengthRatioStdDev = value
        return this;
    }

    MeshQuality edgeLengthRatioVariance(BigDecimal value) {
        this.edgeLengthRatioVariance = value
        return this;
    }

    MeshQuality cellMeasureRatioMin(BigDecimal value) {
        this.cellMeasureRatioMin = value
        return this;
    }

    MeshQuality cellMeasureRatioMax(BigDecimal value) {
        this.cellMeasureRatioMax = value
        return this;
    }

    MeshQuality cellMeasureRatioAvg(BigDecimal value) {
        this.cellMeasureRatioAvg = value
        return this;
    }

    MeshQuality cellMeasureRatioStdDev(BigDecimal value) {
        this.cellMeasureRatioStdDev = value
        return this;
    }

    MeshQuality cellMeasureRatioVariance(BigDecimal value) {
        this.cellMeasureRatioVariance = value
        return this;
    }

    MeshQuality cellNonOrthogonalityMin(BigDecimal value) {
        this.cellNonOrthogonalityMin = value
        return this;
    }

    MeshQuality cellNonOrthogonalityMax(BigDecimal value) {
        this.cellNonOrthogonalityMax = value
        return this;
    }

    MeshQuality cellNonOrthogonalityAvg(BigDecimal value) {
        this.cellNonOrthogonalityAvg = value
        return this;
    }

    MeshQuality cellNonOrthogonalityStdDev(BigDecimal value) {
        this.cellNonOrthogonalityStdDev = value
        return this;
    }

    MeshQuality cellNonOrthogonalityVariance(BigDecimal value) {
        this.cellNonOrthogonalityVariance = value
        return this;
    }

    MeshQuality skewnessMin(BigDecimal value) {
        this.skewnessMin = value
        return this;
    }

    MeshQuality skewnessMax(BigDecimal value) {
        this.skewnessMax = value
        return this;
    }

    MeshQuality skewnessAvg(BigDecimal value) {
        this.skewnessAvg = value
        return this;
    }

    MeshQuality skewnessStdDev(BigDecimal value) {
        this.skewnessStdDev = value
        return this;
    }

    MeshQuality skewnessVariance(BigDecimal value) {
        this.skewnessVariance = value
        return this;
    }

    MeshQuality mesh(Mesh item) {
        this.mesh = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshQualityId != null) map.put('meshQualityId', this.meshQualityId);
        if (this.meshId != null) map.put('meshId', this.meshId);
        if (this.description != null) map.put('description', this.description);
        if (this.totalKCells != null) map.put('totalKCells', this.totalKCells);
        if (this.shapeAspectRatioMin != null) map.put('shapeAspectRatioMin', this.shapeAspectRatioMin);
        if (this.shapeAspectRatioMax != null) map.put('shapeAspectRatioMax', this.shapeAspectRatioMax);
        if (this.shapeAspectRatioAvg != null) map.put('shapeAspectRatioAvg', this.shapeAspectRatioAvg);
        if (this.shapeAspectRatioStdDev != null) map.put('shapeAspectRatioStdDev', this.shapeAspectRatioStdDev);
        if (this.shapeAspectRatioVariance != null) map.put('shapeAspectRatioVariance', this.shapeAspectRatioVariance);
        if (this.edgeLengthRatioMin != null) map.put('edgeLengthRatioMin', this.edgeLengthRatioMin);
        if (this.edgeLengthRatioMax != null) map.put('edgeLengthRatioMax', this.edgeLengthRatioMax);
        if (this.edgeLengthRatioAvg != null) map.put('edgeLengthRatioAvg', this.edgeLengthRatioAvg);
        if (this.edgeLengthRatioStdDev != null) map.put('edgeLengthRatioStdDev', this.edgeLengthRatioStdDev);
        if (this.edgeLengthRatioVariance != null) map.put('edgeLengthRatioVariance', this.edgeLengthRatioVariance);
        if (this.cellMeasureRatioMin != null) map.put('cellMeasureRatioMin', this.cellMeasureRatioMin);
        if (this.cellMeasureRatioMax != null) map.put('cellMeasureRatioMax', this.cellMeasureRatioMax);
        if (this.cellMeasureRatioAvg != null) map.put('cellMeasureRatioAvg', this.cellMeasureRatioAvg);
        if (this.cellMeasureRatioStdDev != null) map.put('cellMeasureRatioStdDev', this.cellMeasureRatioStdDev);
        if (this.cellMeasureRatioVariance != null) map.put('cellMeasureRatioVariance', this.cellMeasureRatioVariance);
        if (this.cellNonOrthogonalityMin != null) map.put('cellNonOrthogonalityMin', this.cellNonOrthogonalityMin);
        if (this.cellNonOrthogonalityMax != null) map.put('cellNonOrthogonalityMax', this.cellNonOrthogonalityMax);
        if (this.cellNonOrthogonalityAvg != null) map.put('cellNonOrthogonalityAvg', this.cellNonOrthogonalityAvg);
        if (this.cellNonOrthogonalityStdDev != null) map.put('cellNonOrthogonalityStdDev', this.cellNonOrthogonalityStdDev);
        if (this.cellNonOrthogonalityVariance != null) map.put('cellNonOrthogonalityVariance', this.cellNonOrthogonalityVariance);
        if (this.skewnessMin != null) map.put('skewnessMin', this.skewnessMin);
        if (this.skewnessMax != null) map.put('skewnessMax', this.skewnessMax);
        if (this.skewnessAvg != null) map.put('skewnessAvg', this.skewnessAvg);
        if (this.skewnessStdDev != null) map.put('skewnessStdDev', this.skewnessStdDev);
        if (this.skewnessVariance != null) map.put('skewnessVariance', this.skewnessVariance);
        return map;
    }
}