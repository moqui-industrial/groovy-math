/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.Monad
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
@EqualsAndHashCode(includes = ['monadId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Monad implements Serializable {
    private static final long serialVersionUID = 1L

    /** monadId */
    String monadId

    /** categoryId */
    String categoryId

    /** endofunctorId */
    String endofunctorId

    /** unitNaturalTransformationId */
    String unitNaturalTransformationId

    /** multiplicationNaturalTransformationId */
    String multiplicationNaturalTransformationId

    /** inducingAdjunctionId */
    String inducingAdjunctionId

    Category category

    Functor endofunctor

    NaturalTransformation unit

    NaturalTransformation multiplication

    Adjunction inducingAdjunction

    Monad() {}

    Monad(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('monadId')) this.monadId = args.get('monadId')?.toString()
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('endofunctorId')) this.endofunctorId = args.get('endofunctorId')?.toString()
            if (args.containsKey('unitNaturalTransformationId')) this.unitNaturalTransformationId = args.get('unitNaturalTransformationId')?.toString()
            if (args.containsKey('multiplicationNaturalTransformationId')) this.multiplicationNaturalTransformationId = args.get('multiplicationNaturalTransformationId')?.toString()
            if (args.containsKey('inducingAdjunctionId')) this.inducingAdjunctionId = args.get('inducingAdjunctionId')?.toString()
        }
    }

    Monad monadId(String value) {
        this.monadId = value
        return this;
    }

    Monad categoryId(String value) {
        this.categoryId = value
        return this;
    }

    Monad endofunctorId(String value) {
        this.endofunctorId = value
        return this;
    }

    Monad unitNaturalTransformationId(String value) {
        this.unitNaturalTransformationId = value
        return this;
    }

    Monad multiplicationNaturalTransformationId(String value) {
        this.multiplicationNaturalTransformationId = value
        return this;
    }

    Monad inducingAdjunctionId(String value) {
        this.inducingAdjunctionId = value
        return this;
    }

    Monad category(Category item) {
        this.category = item;
        return this;
    }

    Monad endofunctor(Functor item) {
        this.endofunctor = item;
        return this;
    }

    Monad unit(NaturalTransformation item) {
        this.unit = item;
        return this;
    }

    Monad multiplication(NaturalTransformation item) {
        this.multiplication = item;
        return this;
    }

    Monad inducingAdjunction(Adjunction item) {
        this.inducingAdjunction = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.monadId != null) map.put('monadId', this.monadId);
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.endofunctorId != null) map.put('endofunctorId', this.endofunctorId);
        if (this.unitNaturalTransformationId != null) map.put('unitNaturalTransformationId', this.unitNaturalTransformationId);
        if (this.multiplicationNaturalTransformationId != null) map.put('multiplicationNaturalTransformationId', this.multiplicationNaturalTransformationId);
        if (this.inducingAdjunctionId != null) map.put('inducingAdjunctionId', this.inducingAdjunctionId);
        return map;
    }
}