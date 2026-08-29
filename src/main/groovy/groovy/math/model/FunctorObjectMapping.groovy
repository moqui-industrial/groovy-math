/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.FunctorObjectMapping
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
@EqualsAndHashCode(includes = ['functorId', 'sourceObjectId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class FunctorObjectMapping implements Serializable {
    private static final long serialVersionUID = 1L

    /** functorId */
    String functorId

    /** sourceObjectId */
    String sourceObjectId

    /** targetObjectId */
    String targetObjectId

    Functor functor

    CategoryObject sourceObject

    CategoryObject targetObject

    FunctorObjectMapping() {}

    FunctorObjectMapping(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('functorId')) this.functorId = args.get('functorId')?.toString()
            if (args.containsKey('sourceObjectId')) this.sourceObjectId = args.get('sourceObjectId')?.toString()
            if (args.containsKey('targetObjectId')) this.targetObjectId = args.get('targetObjectId')?.toString()
        }
    }

    FunctorObjectMapping functorId(String value) {
        this.functorId = value
        return this;
    }

    FunctorObjectMapping sourceObjectId(String value) {
        this.sourceObjectId = value
        return this;
    }

    FunctorObjectMapping targetObjectId(String value) {
        this.targetObjectId = value
        return this;
    }

    FunctorObjectMapping functor(Functor item) {
        this.functor = item;
        return this;
    }

    FunctorObjectMapping sourceObject(CategoryObject item) {
        this.sourceObject = item;
        return this;
    }

    FunctorObjectMapping targetObject(CategoryObject item) {
        this.targetObject = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.functorId != null) map.put('functorId', this.functorId);
        if (this.sourceObjectId != null) map.put('sourceObjectId', this.sourceObjectId);
        if (this.targetObjectId != null) map.put('targetObjectId', this.targetObjectId);
        return map;
    }
}