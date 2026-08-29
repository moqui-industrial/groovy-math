/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.MorphismParameterBinding
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
@EqualsAndHashCode(includes = ['morphismId', 'parameterName'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MorphismParameterBinding implements Serializable {
    private static final long serialVersionUID = 1L

    /** morphismId */
    String morphismId

    /** parameterName */
    String parameterName

    /** literalValue */
    String literalValue

    /** contextPath */
    String contextPath

    /** sourceMorphismId */
    String sourceMorphismId

    /** sourceParameterName */
    String sourceParameterName

    /** description */
    String description

    Morphism morphism

    Morphism sourceMorphism

    MorphismParameterBinding() {}

    MorphismParameterBinding(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('morphismId')) this.morphismId = args.get('morphismId')?.toString()
            if (args.containsKey('parameterName')) this.parameterName = args.get('parameterName')?.toString()
            if (args.containsKey('literalValue')) this.literalValue = args.get('literalValue')?.toString()
            if (args.containsKey('contextPath')) this.contextPath = args.get('contextPath')?.toString()
            if (args.containsKey('sourceMorphismId')) this.sourceMorphismId = args.get('sourceMorphismId')?.toString()
            if (args.containsKey('sourceParameterName')) this.sourceParameterName = args.get('sourceParameterName')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    MorphismParameterBinding morphismId(String value) {
        this.morphismId = value
        return this;
    }

    MorphismParameterBinding parameterName(String value) {
        this.parameterName = value
        return this;
    }

    MorphismParameterBinding literalValue(String value) {
        this.literalValue = value
        return this;
    }

    MorphismParameterBinding contextPath(String value) {
        this.contextPath = value
        return this;
    }

    MorphismParameterBinding sourceMorphismId(String value) {
        this.sourceMorphismId = value
        return this;
    }

    MorphismParameterBinding sourceParameterName(String value) {
        this.sourceParameterName = value
        return this;
    }

    MorphismParameterBinding description(String value) {
        this.description = value
        return this;
    }

    MorphismParameterBinding morphism(Morphism item) {
        this.morphism = item;
        return this;
    }

    MorphismParameterBinding sourceMorphism(Morphism item) {
        this.sourceMorphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.morphismId != null) map.put('morphismId', this.morphismId);
        if (this.parameterName != null) map.put('parameterName', this.parameterName);
        if (this.literalValue != null) map.put('literalValue', this.literalValue);
        if (this.contextPath != null) map.put('contextPath', this.contextPath);
        if (this.sourceMorphismId != null) map.put('sourceMorphismId', this.sourceMorphismId);
        if (this.sourceParameterName != null) map.put('sourceParameterName', this.sourceParameterName);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}