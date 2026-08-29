/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.Morphism
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
@EqualsAndHashCode(includes = ['morphismId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Morphism implements Serializable {
    private static final long serialVersionUID = 1L

    /** morphismId */
    String morphismId

    /** parentMorphismId */
    String parentMorphismId

    /** categoryId */
    String categoryId

    /** morphismTypeEnumId */
    String morphismTypeEnumId

    /** sourceObjectId */
    String sourceObjectId

    /** targetObjectId */
    String targetObjectId

    /** morphismName */
    String morphismName

    /** morphismSymbol */
    String morphismSymbol

    /** description */
    String description

    /** transformationId */
    String transformationId

    /** serviceName */
    String serviceName

    Morphism parent

    List<Morphism> children = new ArrayList<>()

    Category category

    CategoryObject sourceObj

    CategoryObject targetObj

    Transformation transformation

    MorphismComposition composition

    List<Parameter> parameters = new ArrayList<>()

    List<MorphismParameterBinding> parameterBindings = new ArrayList<>()

    List<MorphismCompositionComponent> memberOfCompositions = new ArrayList<>()

    Morphism() {}

    Morphism(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('morphismId')) this.morphismId = args.get('morphismId')?.toString()
            if (args.containsKey('parentMorphismId')) this.parentMorphismId = args.get('parentMorphismId')?.toString()
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('morphismTypeEnumId')) this.morphismTypeEnumId = args.get('morphismTypeEnumId')?.toString()
            if (args.containsKey('sourceObjectId')) this.sourceObjectId = args.get('sourceObjectId')?.toString()
            if (args.containsKey('targetObjectId')) this.targetObjectId = args.get('targetObjectId')?.toString()
            if (args.containsKey('morphismName')) this.morphismName = args.get('morphismName')?.toString()
            if (args.containsKey('morphismSymbol')) this.morphismSymbol = args.get('morphismSymbol')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('serviceName')) this.serviceName = args.get('serviceName')?.toString()
        }
    }

    Morphism morphismId(String value) {
        this.morphismId = value
        return this;
    }

    Morphism parentMorphismId(String value) {
        this.parentMorphismId = value
        return this;
    }

    Morphism categoryId(String value) {
        this.categoryId = value
        return this;
    }

    Morphism morphismTypeEnumId(String value) {
        this.morphismTypeEnumId = value
        return this;
    }

    Morphism sourceObjectId(String value) {
        this.sourceObjectId = value
        return this;
    }

    Morphism targetObjectId(String value) {
        this.targetObjectId = value
        return this;
    }

    Morphism morphismName(String value) {
        this.morphismName = value
        return this;
    }

    Morphism morphismSymbol(String value) {
        this.morphismSymbol = value
        return this;
    }

    Morphism description(String value) {
        this.description = value
        return this;
    }

    Morphism transformationId(String value) {
        this.transformationId = value
        return this;
    }

    Morphism serviceName(String value) {
        this.serviceName = value
        return this;
    }

    Morphism parent(Morphism item) {
        this.parent = item;
        return this;
    }

    Morphism children(List<Morphism> list) {
        this.children = list;
        return this;
    }

    Morphism category(Category item) {
        this.category = item;
        return this;
    }

    Morphism sourceObj(CategoryObject item) {
        this.sourceObj = item;
        return this;
    }

    Morphism targetObj(CategoryObject item) {
        this.targetObj = item;
        return this;
    }

    Morphism transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    Morphism composition(MorphismComposition item) {
        this.composition = item;
        return this;
    }

    Morphism parameters(List<Parameter> list) {
        this.parameters = list;
        return this;
    }

    Morphism parameterBindings(List<MorphismParameterBinding> list) {
        this.parameterBindings = list;
        return this;
    }

    Morphism memberOfCompositions(List<MorphismCompositionComponent> list) {
        this.memberOfCompositions = list;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.morphismId != null) map.put('morphismId', this.morphismId);
        if (this.parentMorphismId != null) map.put('parentMorphismId', this.parentMorphismId);
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.morphismTypeEnumId != null) map.put('morphismTypeEnumId', this.morphismTypeEnumId);
        if (this.sourceObjectId != null) map.put('sourceObjectId', this.sourceObjectId);
        if (this.targetObjectId != null) map.put('targetObjectId', this.targetObjectId);
        if (this.morphismName != null) map.put('morphismName', this.morphismName);
        if (this.morphismSymbol != null) map.put('morphismSymbol', this.morphismSymbol);
        if (this.description != null) map.put('description', this.description);
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.serviceName != null) map.put('serviceName', this.serviceName);
        return map;
    }
}