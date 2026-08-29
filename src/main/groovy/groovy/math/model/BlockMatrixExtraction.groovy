/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.BlockMatrixExtraction
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
@EqualsAndHashCode(includes = ['transformationId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class BlockMatrixExtraction implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** blockLabel */
    String blockLabel

    /** startRowBlock */
    Long startRowBlock

    /** endRowBlock */
    Long endRowBlock

    /** startColBlock */
    Long startColBlock

    /** endColBlock */
    Long endColBlock

    Transformation transformation

    BlockMatrixExtraction() {}

    BlockMatrixExtraction(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('blockLabel')) this.blockLabel = args.get('blockLabel')?.toString()
            if (args.containsKey('startRowBlock')) this.startRowBlock = args.get('startRowBlock') != null ? ((Number) args.get('startRowBlock')).longValue() : null
            if (args.containsKey('endRowBlock')) this.endRowBlock = args.get('endRowBlock') != null ? ((Number) args.get('endRowBlock')).longValue() : null
            if (args.containsKey('startColBlock')) this.startColBlock = args.get('startColBlock') != null ? ((Number) args.get('startColBlock')).longValue() : null
            if (args.containsKey('endColBlock')) this.endColBlock = args.get('endColBlock') != null ? ((Number) args.get('endColBlock')).longValue() : null
        }
    }

    BlockMatrixExtraction transformationId(String value) {
        this.transformationId = value
        return this;
    }

    BlockMatrixExtraction blockLabel(String value) {
        this.blockLabel = value
        return this;
    }

    BlockMatrixExtraction startRowBlock(Long value) {
        this.startRowBlock = value
        return this;
    }

    BlockMatrixExtraction endRowBlock(Long value) {
        this.endRowBlock = value
        return this;
    }

    BlockMatrixExtraction startColBlock(Long value) {
        this.startColBlock = value
        return this;
    }

    BlockMatrixExtraction endColBlock(Long value) {
        this.endColBlock = value
        return this;
    }

    BlockMatrixExtraction transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.blockLabel != null) map.put('blockLabel', this.blockLabel);
        if (this.startRowBlock != null) map.put('startRowBlock', this.startRowBlock);
        if (this.endRowBlock != null) map.put('endRowBlock', this.endRowBlock);
        if (this.startColBlock != null) map.put('startColBlock', this.startColBlock);
        if (this.endColBlock != null) map.put('endColBlock', this.endColBlock);
        return map;
    }
}