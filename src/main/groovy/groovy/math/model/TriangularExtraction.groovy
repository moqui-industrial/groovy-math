/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TriangularExtraction
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
class TriangularExtraction implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** extractionTypeEnumId */
    String extractionTypeEnumId

    /** extractionOffset */
    Long extractionOffset

    Transformation transformation

    TriangularExtraction() {}

    TriangularExtraction(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('extractionTypeEnumId')) this.extractionTypeEnumId = args.get('extractionTypeEnumId')?.toString()
            if (args.containsKey('extractionOffset')) this.extractionOffset = args.get('extractionOffset') != null ? ((Number) args.get('extractionOffset')).longValue() : null
        }
    }

    TriangularExtraction transformationId(String value) {
        this.transformationId = value
        return this;
    }

    TriangularExtraction extractionTypeEnumId(String value) {
        this.extractionTypeEnumId = value
        return this;
    }

    TriangularExtraction extractionOffset(Long value) {
        this.extractionOffset = value
        return this;
    }

    TriangularExtraction transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.extractionTypeEnumId != null) map.put('extractionTypeEnumId', this.extractionTypeEnumId);
        if (this.extractionOffset != null) map.put('extractionOffset', this.extractionOffset);
        return map;
    }
}