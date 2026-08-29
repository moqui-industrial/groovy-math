/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorSlice
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
@EqualsAndHashCode(includes = ['transformationId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TensorSlice implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** sliceDefinitionJson */
    String sliceDefinitionJson

    Transformation transformation

    TensorSlice() {}

    TensorSlice(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('sliceDefinitionJson')) this.sliceDefinitionJson = args.get('sliceDefinitionJson')?.toString()
        }
    }

    TensorSlice transformationId(String value) {
        this.transformationId = value
        return this;
    }

    TensorSlice sliceDefinitionJson(String value) {
        this.sliceDefinitionJson = value
        return this;
    }

    TensorSlice transformation(Transformation item) {
        this.transformation = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.sliceDefinitionJson != null) map.put('sliceDefinitionJson', this.sliceDefinitionJson);
        return map;
    }
}