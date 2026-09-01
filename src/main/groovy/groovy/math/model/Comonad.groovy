/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.Comonad
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
@EqualsAndHashCode(includes = ['comonadId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class Comonad implements Serializable {
    private static final long serialVersionUID = 1L

    /** comonadId */
    String comonadId

    /** categoryId */
    String categoryId

    /** endofunctorId */
    String endofunctorId

    /** counitNaturalTransformationId */
    String counitNaturalTransformationId

    /** comultiplicationNaturalTransformationId */
    String comultiplicationNaturalTransformationId

    /** inducingAdjunctionId */
    String inducingAdjunctionId

    Category category

    Functor endofunctor

    NaturalTransformation counit

    NaturalTransformation comultiplication

    Adjunction inducingAdjunction

    Comonad() {}

    Comonad(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('comonadId')) this.comonadId = args.get('comonadId')?.toString()
            if (args.containsKey('categoryId')) this.categoryId = args.get('categoryId')?.toString()
            if (args.containsKey('endofunctorId')) this.endofunctorId = args.get('endofunctorId')?.toString()
            if (args.containsKey('counitNaturalTransformationId')) this.counitNaturalTransformationId = args.get('counitNaturalTransformationId')?.toString()
            if (args.containsKey('comultiplicationNaturalTransformationId')) this.comultiplicationNaturalTransformationId = args.get('comultiplicationNaturalTransformationId')?.toString()
            if (args.containsKey('inducingAdjunctionId')) this.inducingAdjunctionId = args.get('inducingAdjunctionId')?.toString()
        }
    }

    Comonad comonadId(String value) {
        this.comonadId = value
        return this;
    }

    Comonad categoryId(String value) {
        this.categoryId = value
        return this;
    }

    Comonad endofunctorId(String value) {
        this.endofunctorId = value
        return this;
    }

    Comonad counitNaturalTransformationId(String value) {
        this.counitNaturalTransformationId = value
        return this;
    }

    Comonad comultiplicationNaturalTransformationId(String value) {
        this.comultiplicationNaturalTransformationId = value
        return this;
    }

    Comonad inducingAdjunctionId(String value) {
        this.inducingAdjunctionId = value
        return this;
    }

    Comonad category(Category item) {
        this.category = item;
        return this;
    }

    Comonad endofunctor(Functor item) {
        this.endofunctor = item;
        return this;
    }

    Comonad counit(NaturalTransformation item) {
        this.counit = item;
        return this;
    }

    Comonad comultiplication(NaturalTransformation item) {
        this.comultiplication = item;
        return this;
    }

    Comonad inducingAdjunction(Adjunction item) {
        this.inducingAdjunction = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.comonadId != null) map.put('comonadId', this.comonadId);
        if (this.categoryId != null) map.put('categoryId', this.categoryId);
        if (this.endofunctorId != null) map.put('endofunctorId', this.endofunctorId);
        if (this.counitNaturalTransformationId != null) map.put('counitNaturalTransformationId', this.counitNaturalTransformationId);
        if (this.comultiplicationNaturalTransformationId != null) map.put('comultiplicationNaturalTransformationId', this.comultiplicationNaturalTransformationId);
        if (this.inducingAdjunctionId != null) map.put('inducingAdjunctionId', this.inducingAdjunctionId);
        return map;
    }
}