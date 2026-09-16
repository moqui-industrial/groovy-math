/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.tensor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Translates between the schema's TensorDataType enumeration and the dtype codes the FFM layer
 * speaks.
 *
 * <p>Tensor.dataTypeEnumId is what a model declares; {@link TensorDescriptor}'s DTYPE_ constants
 * are what a native call is told. Without a translation the two never meet and a declared
 * element type is decoration.
 */
public final class TensorDataTypes {

    private static final Map<String, Integer> BY_ENUM_ID;
    private static final Map<Integer, String> BY_CODE;

    static {
        Map<String, Integer> byEnumId = new LinkedHashMap<>();
        byEnumId.put("DtFloat32", TensorDescriptor.DTYPE_FLOAT32);
        byEnumId.put("DtFloat64", TensorDescriptor.DTYPE_FLOAT64);
        byEnumId.put("DtInt32", TensorDescriptor.DTYPE_INT32);
        byEnumId.put("DtInt64", TensorDescriptor.DTYPE_INT64);
        byEnumId.put("DtBool", TensorDescriptor.DTYPE_BOOL);
        byEnumId.put("DtBFloat16", TensorDescriptor.DTYPE_BFLOAT16);
        BY_ENUM_ID = Collections.unmodifiableMap(byEnumId);

        Map<Integer, String> byCode = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : byEnumId.entrySet()) {
            byCode.putIfAbsent(entry.getValue(), entry.getKey());
        }
        BY_CODE = Collections.unmodifiableMap(byCode);
    }

    private TensorDataTypes() { }

    /**
     * The dtype code for a declared enumeration id, or -1 when the schema declares an element
     * type the FFM layer has no code for. TensorDataType seeds more values than TensorDescriptor
     * can describe (float16, the complex and 8-bit types); those are declarable and simply not
     * dispatchable, which is a better answer than pretending they are float32.
     */
    public static int codeForEnumId(String dataTypeEnumId) {
        if (dataTypeEnumId == null) return -1;
        Integer code = BY_ENUM_ID.get(dataTypeEnumId);
        return code == null ? -1 : code;
    }

    /** The declared enumeration id for a dtype code, or null. */
    public static String enumIdForCode(int dtype) {
        return BY_CODE.get(dtype);
    }

    /** A name for a dtype code that is safe to put in an error message. */
    public static String describe(int dtype) {
        String enumId = BY_CODE.get(dtype);
        return enumId != null ? enumId : ("dtype " + dtype);
    }
}
