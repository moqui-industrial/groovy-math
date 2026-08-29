/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.FunctorMorphismMapping
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
@EqualsAndHashCode(includes = ['functorId', 'sourceMorphismId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class FunctorMorphismMapping implements Serializable {
    private static final long serialVersionUID = 1L

    /** functorId */
    String functorId

    /** sourceMorphismId */
    String sourceMorphismId

    /** targetMorphismId */
    String targetMorphismId

    Functor functor

    Morphism sourceMorphism

    Morphism targetMorphism

    FunctorMorphismMapping() {}

    FunctorMorphismMapping(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('functorId')) this.functorId = args.get('functorId')?.toString()
            if (args.containsKey('sourceMorphismId')) this.sourceMorphismId = args.get('sourceMorphismId')?.toString()
            if (args.containsKey('targetMorphismId')) this.targetMorphismId = args.get('targetMorphismId')?.toString()
        }
    }

    FunctorMorphismMapping functorId(String value) {
        this.functorId = value
        return this;
    }

    FunctorMorphismMapping sourceMorphismId(String value) {
        this.sourceMorphismId = value
        return this;
    }

    FunctorMorphismMapping targetMorphismId(String value) {
        this.targetMorphismId = value
        return this;
    }

    FunctorMorphismMapping functor(Functor item) {
        this.functor = item;
        return this;
    }

    FunctorMorphismMapping sourceMorphism(Morphism item) {
        this.sourceMorphism = item;
        return this;
    }

    FunctorMorphismMapping targetMorphism(Morphism item) {
        this.targetMorphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.functorId != null) map.put('functorId', this.functorId);
        if (this.sourceMorphismId != null) map.put('sourceMorphismId', this.sourceMorphismId);
        if (this.targetMorphismId != null) map.put('targetMorphismId', this.targetMorphismId);
        return map;
    }
}