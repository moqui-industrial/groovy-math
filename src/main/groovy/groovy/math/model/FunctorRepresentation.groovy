/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.FunctorRepresentation
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
@EqualsAndHashCode(includes = ['functorRepresentationId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class FunctorRepresentation implements Serializable {
    private static final long serialVersionUID = 1L

    /** functorRepresentationId */
    String functorRepresentationId

    /** functorId */
    String functorId

    /** representingObjectId */
    String representingObjectId

    /** homFunctorId */
    String homFunctorId

    /** naturalIsomorphismId */
    String naturalIsomorphismId

    Functor functor

    CategoryObject representingObject

    Functor homFunctor

    NaturalTransformation naturalIsomorphism

    FunctorRepresentation() {}

    FunctorRepresentation(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('functorRepresentationId')) this.functorRepresentationId = args.get('functorRepresentationId')?.toString()
            if (args.containsKey('functorId')) this.functorId = args.get('functorId')?.toString()
            if (args.containsKey('representingObjectId')) this.representingObjectId = args.get('representingObjectId')?.toString()
            if (args.containsKey('homFunctorId')) this.homFunctorId = args.get('homFunctorId')?.toString()
            if (args.containsKey('naturalIsomorphismId')) this.naturalIsomorphismId = args.get('naturalIsomorphismId')?.toString()
        }
    }

    FunctorRepresentation functorRepresentationId(String value) {
        this.functorRepresentationId = value
        return this;
    }

    FunctorRepresentation functorId(String value) {
        this.functorId = value
        return this;
    }

    FunctorRepresentation representingObjectId(String value) {
        this.representingObjectId = value
        return this;
    }

    FunctorRepresentation homFunctorId(String value) {
        this.homFunctorId = value
        return this;
    }

    FunctorRepresentation naturalIsomorphismId(String value) {
        this.naturalIsomorphismId = value
        return this;
    }

    FunctorRepresentation functor(Functor item) {
        this.functor = item;
        return this;
    }

    FunctorRepresentation representingObject(CategoryObject item) {
        this.representingObject = item;
        return this;
    }

    FunctorRepresentation homFunctor(Functor item) {
        this.homFunctor = item;
        return this;
    }

    FunctorRepresentation naturalIsomorphism(NaturalTransformation item) {
        this.naturalIsomorphism = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.functorRepresentationId != null) map.put('functorRepresentationId', this.functorRepresentationId);
        if (this.functorId != null) map.put('functorId', this.functorId);
        if (this.representingObjectId != null) map.put('representingObjectId', this.representingObjectId);
        if (this.homFunctorId != null) map.put('homFunctorId', this.homFunctorId);
        if (this.naturalIsomorphismId != null) map.put('naturalIsomorphismId', this.naturalIsomorphismId);
        return map;
    }
}