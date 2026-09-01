/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.EndofunctorAlgebra
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
@EqualsAndHashCode(includes = ['endofunctorAlgebraId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class EndofunctorAlgebra implements Serializable {
    private static final long serialVersionUID = 1L

    /** endofunctorAlgebraId */
    String endofunctorAlgebraId

    /** algebraTypeEnumId */
    String algebraTypeEnumId

    /** endofunctorId */
    String endofunctorId

    /** monadId */
    String monadId

    /** comonadId */
    String comonadId

    /** carrierObjectId */
    String carrierObjectId

    /** structureMorphismId */
    String structureMorphismId

    Functor endofunctor

    Monad monad

    Comonad comonad

    CategoryObject carrierObject

    Morphism structureMorphism

    EndofunctorAlgebra() {}

    EndofunctorAlgebra(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('endofunctorAlgebraId')) this.endofunctorAlgebraId = args.get('endofunctorAlgebraId')?.toString()
            if (args.containsKey('algebraTypeEnumId')) this.algebraTypeEnumId = args.get('algebraTypeEnumId')?.toString()
            if (args.containsKey('endofunctorId')) this.endofunctorId = args.get('endofunctorId')?.toString()
            if (args.containsKey('monadId')) this.monadId = args.get('monadId')?.toString()
            if (args.containsKey('comonadId')) this.comonadId = args.get('comonadId')?.toString()
            if (args.containsKey('carrierObjectId')) this.carrierObjectId = args.get('carrierObjectId')?.toString()
            if (args.containsKey('structureMorphismId')) this.structureMorphismId = args.get('structureMorphismId')?.toString()
        }
    }

    EndofunctorAlgebra endofunctorAlgebraId(String value) {
        this.endofunctorAlgebraId = value
        return this;
    }

    EndofunctorAlgebra algebraTypeEnumId(String value) {
        this.algebraTypeEnumId = value
        return this;
    }

    EndofunctorAlgebra endofunctorId(String value) {
        this.endofunctorId = value
        return this;
    }

    EndofunctorAlgebra monadId(String value) {
        this.monadId = value
        return this;
    }

    EndofunctorAlgebra comonadId(String value) {
        this.comonadId = value
        return this;
    }

    EndofunctorAlgebra carrierObjectId(String value) {
        this.carrierObjectId = value
        return this;
    }

    EndofunctorAlgebra structureMorphismId(String value) {
        this.structureMorphismId = value
        return this;
    }

    EndofunctorAlgebra endofunctor(Functor item) {
        this.endofunctor = item;
        return this;
    }

    EndofunctorAlgebra monad(Monad item) {
        this.monad = item;
        return this;
    }

    EndofunctorAlgebra comonad(Comonad item) {
        this.comonad = item;
        return this;
    }

    EndofunctorAlgebra carrierObject(CategoryObject item) {
        this.carrierObject = item;
        return this;
    }

    EndofunctorAlgebra structureMorphism(Morphism item) {
        this.structureMorphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.endofunctorAlgebraId != null) map.put('endofunctorAlgebraId', this.endofunctorAlgebraId);
        if (this.algebraTypeEnumId != null) map.put('algebraTypeEnumId', this.algebraTypeEnumId);
        if (this.endofunctorId != null) map.put('endofunctorId', this.endofunctorId);
        if (this.monadId != null) map.put('monadId', this.monadId);
        if (this.comonadId != null) map.put('comonadId', this.comonadId);
        if (this.carrierObjectId != null) map.put('carrierObjectId', this.carrierObjectId);
        if (this.structureMorphismId != null) map.put('structureMorphismId', this.structureMorphismId);
        return map;
    }
}