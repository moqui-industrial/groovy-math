/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.MeshKCellQuality
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
@EqualsAndHashCode(includes = ['meshKCellQualityId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MeshKCellQuality implements Serializable {
    private static final long serialVersionUID = 1L

    /** meshKCellQualityId */
    String meshKCellQualityId

    /** meshKCellId */
    String meshKCellId

    /** description */
    String description

    /** shapeAspectRatio */
    BigDecimal shapeAspectRatio

    /** edgeLengthRatio */
    BigDecimal edgeLengthRatio

    /** cellMeasureRatio */
    BigDecimal cellMeasureRatio

    /** cellNonOrthogonality */
    BigDecimal cellNonOrthogonality

    /** skewness */
    BigDecimal skewness

    MeshKCell cell

    MeshKCellQuality() {}

    MeshKCellQuality(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('meshKCellQualityId')) this.meshKCellQualityId = args.get('meshKCellQualityId')?.toString()
            if (args.containsKey('meshKCellId')) this.meshKCellId = args.get('meshKCellId')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('shapeAspectRatio')) this.shapeAspectRatio = args.get('shapeAspectRatio') != null ? (args.get('shapeAspectRatio') instanceof BigDecimal ? (BigDecimal) args.get('shapeAspectRatio') : new BigDecimal(args.get('shapeAspectRatio').toString())) : null
            if (args.containsKey('edgeLengthRatio')) this.edgeLengthRatio = args.get('edgeLengthRatio') != null ? (args.get('edgeLengthRatio') instanceof BigDecimal ? (BigDecimal) args.get('edgeLengthRatio') : new BigDecimal(args.get('edgeLengthRatio').toString())) : null
            if (args.containsKey('cellMeasureRatio')) this.cellMeasureRatio = args.get('cellMeasureRatio') != null ? (args.get('cellMeasureRatio') instanceof BigDecimal ? (BigDecimal) args.get('cellMeasureRatio') : new BigDecimal(args.get('cellMeasureRatio').toString())) : null
            if (args.containsKey('cellNonOrthogonality')) this.cellNonOrthogonality = args.get('cellNonOrthogonality') != null ? (args.get('cellNonOrthogonality') instanceof BigDecimal ? (BigDecimal) args.get('cellNonOrthogonality') : new BigDecimal(args.get('cellNonOrthogonality').toString())) : null
            if (args.containsKey('skewness')) this.skewness = args.get('skewness') != null ? (args.get('skewness') instanceof BigDecimal ? (BigDecimal) args.get('skewness') : new BigDecimal(args.get('skewness').toString())) : null
        }
    }

    MeshKCellQuality meshKCellQualityId(String value) {
        this.meshKCellQualityId = value
        return this;
    }

    MeshKCellQuality meshKCellId(String value) {
        this.meshKCellId = value
        return this;
    }

    MeshKCellQuality description(String value) {
        this.description = value
        return this;
    }

    MeshKCellQuality shapeAspectRatio(BigDecimal value) {
        this.shapeAspectRatio = value
        return this;
    }

    MeshKCellQuality edgeLengthRatio(BigDecimal value) {
        this.edgeLengthRatio = value
        return this;
    }

    MeshKCellQuality cellMeasureRatio(BigDecimal value) {
        this.cellMeasureRatio = value
        return this;
    }

    MeshKCellQuality cellNonOrthogonality(BigDecimal value) {
        this.cellNonOrthogonality = value
        return this;
    }

    MeshKCellQuality skewness(BigDecimal value) {
        this.skewness = value
        return this;
    }

    MeshKCellQuality cell(MeshKCell item) {
        this.cell = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.meshKCellQualityId != null) map.put('meshKCellQualityId', this.meshKCellQualityId);
        if (this.meshKCellId != null) map.put('meshKCellId', this.meshKCellId);
        if (this.description != null) map.put('description', this.description);
        if (this.shapeAspectRatio != null) map.put('shapeAspectRatio', this.shapeAspectRatio);
        if (this.edgeLengthRatio != null) map.put('edgeLengthRatio', this.edgeLengthRatio);
        if (this.cellMeasureRatio != null) map.put('cellMeasureRatio', this.cellMeasureRatio);
        if (this.cellNonOrthogonality != null) map.put('cellNonOrthogonality', this.cellNonOrthogonality);
        if (this.skewness != null) map.put('skewness', this.skewness);
        return map;
    }
}