/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.TensorDecompositionFactor
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
@EqualsAndHashCode(includes = ['transformationId', 'modeIndex'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class TensorDecompositionFactor implements Serializable {
    private static final long serialVersionUID = 1L

    /** transformationId */
    String transformationId

    /** modeIndex */
    Long modeIndex

    /** factorMatrixId */
    String factorMatrixId

    TensorDecomposition decomposition

    Matrix factorMatrix

    TensorDecompositionFactor() {}

    TensorDecompositionFactor(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('transformationId')) this.transformationId = args.get('transformationId')?.toString()
            if (args.containsKey('modeIndex')) this.modeIndex = args.get('modeIndex') != null ? ((Number) args.get('modeIndex')).longValue() : null
            if (args.containsKey('factorMatrixId')) this.factorMatrixId = args.get('factorMatrixId')?.toString()
        }
    }

    TensorDecompositionFactor transformationId(String value) {
        this.transformationId = value
        return this;
    }

    TensorDecompositionFactor modeIndex(Long value) {
        this.modeIndex = value
        return this;
    }

    TensorDecompositionFactor factorMatrixId(String value) {
        this.factorMatrixId = value
        return this;
    }

    TensorDecompositionFactor decomposition(TensorDecomposition item) {
        this.decomposition = item;
        return this;
    }

    TensorDecompositionFactor factorMatrix(Matrix item) {
        this.factorMatrix = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.transformationId != null) map.put('transformationId', this.transformationId);
        if (this.modeIndex != null) map.put('modeIndex', this.modeIndex);
        if (this.factorMatrixId != null) map.put('factorMatrixId', this.factorMatrixId);
        return map;
    }
}