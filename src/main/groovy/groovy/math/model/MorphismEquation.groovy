/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.MorphismEquation
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
@EqualsAndHashCode(includes = ['morphismEquationId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class MorphismEquation implements Serializable {
    private static final long serialVersionUID = 1L

    /** morphismEquationId */
    String morphismEquationId

    /** categoryId */
    String categoryId

    /** categoryConstructionId */
    String categoryConstructionId

    /** leftMorphismId */
    String leftMorphismId

    /** rightMorphismId */
    String rightMorphismId

    /** description */
    String description

    Category category

    CategoryConstruction categoryConstruction

    Morphism leftMorphism

    Morphism rightMorphism

    MorphismEquation() {}

    MorphismEquation(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('morphismEquationId')) this.morphismEquationId = args.get('morphismEquationId')?.toString()
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('categoryConstructionId')) this.categoryConstructionId = args.get('categoryConstructionId')?.toString()
            if (args.containsKey('leftMorphismId')) this.leftMorphismId = args.get('leftMorphismId')?.toString()
            if (args.containsKey('rightMorphismId')) this.rightMorphismId = args.get('rightMorphismId')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    MorphismEquation morphismEquationId(String value) {
        this.morphismEquationId = value
        return this;
    }

    MorphismEquation categoryId(String value) {
        this.categoryId = value
        return this;
    }

    MorphismEquation categoryConstructionId(String value) {
        this.categoryConstructionId = value
        return this;
    }

    MorphismEquation leftMorphismId(String value) {
        this.leftMorphismId = value
        return this;
    }

    MorphismEquation rightMorphismId(String value) {
        this.rightMorphismId = value
        return this;
    }

    MorphismEquation description(String value) {
        this.description = value
        return this;
    }

    MorphismEquation category(Category item) {
        this.category = item;
        return this;
    }

    MorphismEquation categoryConstruction(CategoryConstruction item) {
        this.categoryConstruction = item;
        return this;
    }

    MorphismEquation leftMorphism(Morphism item) {
        this.leftMorphism = item;
        return this;
    }

    MorphismEquation rightMorphism(Morphism item) {
        this.rightMorphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.morphismEquationId != null) map.put('morphismEquationId', this.morphismEquationId);
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.categoryConstructionId != null) map.put('categoryConstructionId', this.categoryConstructionId);
        if (this.leftMorphismId != null) map.put('leftMorphismId', this.leftMorphismId);
        if (this.rightMorphismId != null) map.put('rightMorphismId', this.rightMorphismId);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}