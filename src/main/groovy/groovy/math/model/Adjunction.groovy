/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.Adjunction
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
@EqualsAndHashCode(includes = ['adjunctionId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Adjunction implements Serializable {
    private static final long serialVersionUID = 1L

    /** adjunctionId */
    String adjunctionId

    /** leftFunctorId */
    String leftFunctorId

    /** rightFunctorId */
    String rightFunctorId

    /** unitNaturalTransformationId */
    String unitNaturalTransformationId

    /** counitNaturalTransformationId */
    String counitNaturalTransformationId

    /** description */
    String description

    Functor leftFunctor

    Functor rightFunctor

    NaturalTransformation unit

    NaturalTransformation counit

    Adjunction() {}

    Adjunction(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('adjunctionId')) this.adjunctionId = args.get('adjunctionId')?.toString()
            if (args.containsKey('leftFunctorId')) this.leftFunctorId = args.get('leftFunctorId')?.toString()
            if (args.containsKey('rightFunctorId')) this.rightFunctorId = args.get('rightFunctorId')?.toString()
            if (args.containsKey('unitNaturalTransformationId')) this.unitNaturalTransformationId = args.get('unitNaturalTransformationId')?.toString()
            if (args.containsKey('counitNaturalTransformationId')) this.counitNaturalTransformationId = args.get('counitNaturalTransformationId')?.toString()
            if (args.containsKey('description')) this.description = args.get('description')?.toString()
        }
    }

    Adjunction adjunctionId(String value) {
        this.adjunctionId = value
        return this;
    }

    Adjunction leftFunctorId(String value) {
        this.leftFunctorId = value
        return this;
    }

    Adjunction rightFunctorId(String value) {
        this.rightFunctorId = value
        return this;
    }

    Adjunction unitNaturalTransformationId(String value) {
        this.unitNaturalTransformationId = value
        return this;
    }

    Adjunction counitNaturalTransformationId(String value) {
        this.counitNaturalTransformationId = value
        return this;
    }

    Adjunction description(String value) {
        this.description = value
        return this;
    }

    Adjunction leftFunctor(Functor item) {
        this.leftFunctor = item;
        return this;
    }

    Adjunction rightFunctor(Functor item) {
        this.rightFunctor = item;
        return this;
    }

    Adjunction unit(NaturalTransformation item) {
        this.unit = item;
        return this;
    }

    Adjunction counit(NaturalTransformation item) {
        this.counit = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.adjunctionId != null) map.put('adjunctionId', this.adjunctionId);
        if (this.leftFunctorId != null) map.put('leftFunctorId', this.leftFunctorId);
        if (this.rightFunctorId != null) map.put('rightFunctorId', this.rightFunctorId);
        if (this.unitNaturalTransformationId != null) map.put('unitNaturalTransformationId', this.unitNaturalTransformationId);
        if (this.counitNaturalTransformationId != null) map.put('counitNaturalTransformationId', this.counitNaturalTransformationId);
        if (this.description != null) map.put('description', this.description);
        return map;
    }
}