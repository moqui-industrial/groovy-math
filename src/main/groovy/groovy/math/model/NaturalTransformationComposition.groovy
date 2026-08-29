/*
 * Generated domain model for Moqui Math Metamodel
 * Entity: moqui.math.ct.NaturalTransformationComposition
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
@EqualsAndHashCode(includes = ['natTransfCompositionId'])
@ToString(includePackage = false, includeNames = true)
@AutoClone
class NaturalTransformationComposition implements Serializable {
    private static final long serialVersionUID = 1L

    /** natTransfCompositionId */
    String natTransfCompositionId

    /** compositionTypeEnumId */
    String compositionTypeEnumId

    /** resultNatTransfId */
    String resultNatTransfId

    /** operandNatTransfId */
    String operandNatTransfId

    /** operandIndex */
    Long operandIndex

    NaturalTransformation resultNatTransf

    NaturalTransformation operandNatTransf

    NaturalTransformationComposition() {}

    NaturalTransformationComposition(Map<String, Object> args) {
        if (args != null) {
            if (args.containsKey('natTransfCompositionId')) this.natTransfCompositionId = args.get('natTransfCompositionId')?.toString()
            if (args.containsKey('compositionTypeEnumId')) this.compositionTypeEnumId = args.get('compositionTypeEnumId')?.toString()
            if (args.containsKey('resultNatTransfId')) this.resultNatTransfId = args.get('resultNatTransfId')?.toString()
            if (args.containsKey('operandNatTransfId')) this.operandNatTransfId = args.get('operandNatTransfId')?.toString()
            if (args.containsKey('operandIndex')) this.operandIndex = args.get('operandIndex') != null ? ((Number) args.get('operandIndex')).longValue() : null
        }
    }

    NaturalTransformationComposition natTransfCompositionId(String value) {
        this.natTransfCompositionId = value
        return this;
    }

    NaturalTransformationComposition compositionTypeEnumId(String value) {
        this.compositionTypeEnumId = value
        return this;
    }

    NaturalTransformationComposition resultNatTransfId(String value) {
        this.resultNatTransfId = value
        return this;
    }

    NaturalTransformationComposition operandNatTransfId(String value) {
        this.operandNatTransfId = value
        return this;
    }

    NaturalTransformationComposition operandIndex(Long value) {
        this.operandIndex = value
        return this;
    }

    NaturalTransformationComposition resultNatTransf(NaturalTransformation item) {
        this.resultNatTransf = item;
        return this;
    }

    NaturalTransformationComposition operandNatTransf(NaturalTransformation item) {
        this.operandNatTransf = item;
        return this;
    }

    Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (this.natTransfCompositionId != null) map.put('natTransfCompositionId', this.natTransfCompositionId);
        if (this.compositionTypeEnumId != null) map.put('compositionTypeEnumId', this.compositionTypeEnumId);
        if (this.resultNatTransfId != null) map.put('resultNatTransfId', this.resultNatTransfId);
        if (this.operandNatTransfId != null) map.put('operandNatTransfId', this.operandNatTransfId);
        if (this.operandIndex != null) map.put('operandIndex', this.operandIndex);
        return map;
    }
}