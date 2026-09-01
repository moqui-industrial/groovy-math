/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.NaturalTransformation
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
@EqualsAndHashCode(includes = ['naturalTransformationId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class NaturalTransformation implements Serializable {
    private static final long serialVersionUID = 1L

    /** naturalTransformationId */
    String naturalTransformationId

    /** parentTransformationId */
    String parentTransformationId

    /** naturalTransformationTypeEnumId */
    String naturalTransformationTypeEnumId

    /** sourceFunctorId */
    String sourceFunctorId

    /** targetFunctorId */
    String targetFunctorId

    /** categoryMorphismId */
    String categoryMorphismId

    /** description */
    String description

    NaturalTransformation parent

    Functor sourceFunctor

    Functor targetFunctor

    Morphism categoryMorphism

    NaturalTransformation() {}

    NaturalTransformation(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('naturalTransformationId')) this.naturalTransformationId = args.get('naturalTransformationId')?.toString()
            if (args.containsKey('parentTransformationId')) this.parentTransformationId = args.get('parentTransformationId')?.toString()
            if (args.containsKey('naturalTransformationTypeEnumId')) this.naturalTransformationTypeEnumId = args.get('naturalTransformationTypeEnumId')?.toString()
            if (args.containsKey('sourceFunctorId')) this.sourceFunctorId = args.get('sourceFunctorId')?.toString()
            if (args.containsKey('targetFunctorId')) this.targetFunctorId = args.get('targetFunctorId')?.toString()
            if (args.containsKey('categoryMorphismId')) this.categoryMorphismId = args.get('categoryMorphismId')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    NaturalTransformation naturalTransformationId(String value) {
        this.naturalTransformationId = value
        return this;
    }

    NaturalTransformation parentTransformationId(String value) {
        this.parentTransformationId = value
        return this;
    }

    NaturalTransformation naturalTransformationTypeEnumId(String value) {
        this.naturalTransformationTypeEnumId = value
        return this;
    }

    NaturalTransformation sourceFunctorId(String value) {
        this.sourceFunctorId = value
        return this;
    }

    NaturalTransformation targetFunctorId(String value) {
        this.targetFunctorId = value
        return this;
    }

    NaturalTransformation categoryMorphismId(String value) {
        this.categoryMorphismId = value
        return this;
    }

    NaturalTransformation description(String value) {
        this.description = value
        return this;
    }

    NaturalTransformation parent(NaturalTransformation item) {
        this.parent = item;
        return this;
    }

    NaturalTransformation sourceFunctor(Functor item) {
        this.sourceFunctor = item;
        return this;
    }

    NaturalTransformation targetFunctor(Functor item) {
        this.targetFunctor = item;
        return this;
    }

    NaturalTransformation categoryMorphism(Morphism item) {
        this.categoryMorphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.naturalTransformationId != null) map.put('naturalTransformationId', this.naturalTransformationId);
        if (this.parentTransformationId != null) map.put('parentTransformationId', this.parentTransformationId);
        if (this.naturalTransformationTypeEnumId != null) map.put('naturalTransformationTypeEnumId', this.naturalTransformationTypeEnumId);
        if (this.sourceFunctorId != null) map.put('sourceFunctorId', this.sourceFunctorId);
        if (this.targetFunctorId != null) map.put('targetFunctorId', this.targetFunctorId);
        if (this.categoryMorphismId != null) map.put('categoryMorphismId', this.categoryMorphismId);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}