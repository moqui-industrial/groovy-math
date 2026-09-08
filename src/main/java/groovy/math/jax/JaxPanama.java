/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.jax;

import groovy.math.libtorch.LibTorchBackend;

import java.io.File;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.nio.ByteBuffer;

public final class JaxPanama implements LibTorchBackend {
    public static final JaxPanama INSTANCE = new JaxPanama();

    private final Linker linker;
    private final SymbolLookup symbols;

    private final MethodHandle createPlanHandle;
    private final MethodHandle destroyHandle;
    private final MethodHandle outputWidthHandle;
    private final MethodHandle sealHandle;
    private final MethodHandle setTrainingHandle;

    private final MethodHandle addAffineHandle;
    private final MethodHandle addReluHandle;
    private final MethodHandle addSigmoidHandle;
    private final MethodHandle addGeluHandle;
    private final MethodHandle addSiluHandle;
    private final MethodHandle addTanhHandle;
    private final MethodHandle addLeakyReluHandle;
    private final MethodHandle addEluHandle;
    private final MethodHandle addSoftmaxHandle;
    private final MethodHandle addLogSoftmaxHandle;
    private final MethodHandle addLayerNormHandle;
    private final MethodHandle addRMSNormHandle;
    private final MethodHandle addMatrixProductHandle;
    private final MethodHandle addAttentionMaskHandle;
    private final MethodHandle addScaledDotProductAttentionHandle;
    private final MethodHandle addBinaryOpHandle;
    private final MethodHandle addUnaryMathHandle;
    private final MethodHandle addReductionHandle;
    private final MethodHandle addLossHandle;

    private final MethodHandle executeHandle;
    private final MethodHandle matmulHandle;
    private final MethodHandle tensorOpHandle;
    private final MethodHandle configureThreadsHandle;
    private final MethodHandle intraOpThreadsHandle;
    private final MethodHandle interOpThreadsHandle;

    private JaxPanama() {
        this.linker = Linker.nativeLinker();
        this.symbols = loadSymbols();

        this.createPlanHandle = find("jax_panama_create_plan",
            FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT));
        this.destroyHandle = find("jax_panama_destroy",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG));
        this.outputWidthHandle = find("jax_panama_output_width",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));
        this.sealHandle = find("jax_panama_seal",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.setTrainingHandle = findOptional("jax_panama_set_training",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT));

        this.addAffineHandle = find("jax_panama_add_affine",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
        this.addReluHandle = find("jax_panama_add_relu",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addSigmoidHandle = findOptional("jax_panama_add_sigmoid",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addGeluHandle = findOptional("jax_panama_add_gelu",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addSiluHandle = findOptional("jax_panama_add_silu",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addTanhHandle = findOptional("jax_panama_add_tanh",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addLeakyReluHandle = findOptional("jax_panama_add_leaky_relu",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_FLOAT));
        this.addEluHandle = findOptional("jax_panama_add_elu",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_FLOAT));
        this.addSoftmaxHandle = find("jax_panama_add_softmax",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));
        this.addLogSoftmaxHandle = findOptional("jax_panama_add_log_softmax",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));
        this.addLayerNormHandle = findOptional("jax_panama_add_layer_norm",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_FLOAT));
        this.addRMSNormHandle = findOptional("jax_panama_add_rms_norm",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_FLOAT));
        this.addMatrixProductHandle = find("jax_panama_add_matrix_product",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS));
        this.addAttentionMaskHandle = find("jax_panama_add_attention_mask",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));
        this.addScaledDotProductAttentionHandle = findOptional("jax_panama_add_scaled_dot_product_attention",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_FLOAT));

        this.addBinaryOpHandle = findOptional("jax_panama_add_binary_op",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addUnaryMathHandle = findOptional("jax_panama_add_unary_math",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_FLOAT));
        this.addReductionHandle = findOptional("jax_panama_add_reduction",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT));
        this.addLossHandle = findOptional("jax_panama_add_loss",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

        this.executeHandle = find("jax_panama_execute",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS));
        this.matmulHandle = find("jax_panama_matmul",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG,
                ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));
        this.tensorOpHandle = findOptional("jax_panama_tensor_op",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_FLOAT));

        this.configureThreadsHandle = findOptional("jax_panama_configure_threads",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.intraOpThreadsHandle = findOptional("jax_panama_intra_op_threads",
            FunctionDescriptor.of(ValueLayout.JAVA_INT));
        this.interOpThreadsHandle = findOptional("jax_panama_inter_op_threads",
            FunctionDescriptor.of(ValueLayout.JAVA_INT));
    }

    private static SymbolLookup loadSymbols() {
        String explicitPath = System.getProperty("groovy.math.jax.library");
        if (explicitPath != null && new File(explicitPath).exists()) {
            return SymbolLookup.libraryLookup(new File(explicitPath).toPath(), Arena.global());
        }
        File defaultBuildLib = new File("build/native-jax/libgroovy_math_jax.so");
        if (defaultBuildLib.exists()) {
            return SymbolLookup.libraryLookup(defaultBuildLib.toPath(), Arena.global());
        }
        try {
            groovy.math.nativeutil.NativeLibraryLoader.loadLibrary("groovy_math_jax");
        } catch (Throwable ignored) {
        }
        return SymbolLookup.loaderLookup();
    }

    private MethodHandle find(String name, FunctionDescriptor descriptor) {
        return symbols.find(name)
            .map(addr -> linker.downcallHandle(addr, descriptor))
            .orElseThrow(() -> new UnsatisfiedLinkError("Symbol not found: " + name));
    }

    private MethodHandle findOptional(String name, FunctionDescriptor descriptor) {
        return symbols.find(name)
            .map(addr -> linker.downcallHandle(addr, descriptor))
            .orElse(null);
    }

    public static MemorySegment allocateFloats(Arena arena, float[] values) {
        MemorySegment seg = arena.allocate((long) values.length * Float.BYTES, ValueLayout.JAVA_FLOAT.byteAlignment());
        MemorySegment.copy(MemorySegment.ofArray(values), 0, seg, 0, (long) values.length * Float.BYTES);
        return seg;
    }

    public static MemorySegment allocateFloatBuffer(Arena arena, long elementCount) {
        return arena.allocate(elementCount * Float.BYTES, ValueLayout.JAVA_FLOAT.byteAlignment());
    }

    @Override
    public long createPlan(int inputWidth) {
        try {
            return (long) createPlanHandle.invokeExact(inputWidth);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void destroy(long handle) {
        try {
            destroyHandle.invokeExact(handle);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public int outputWidth(long handle) {
        try {
            return (int) outputWidthHandle.invokeExact(handle);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void seal(long handle, int outputSlot, int outputWidth) {
        try {
            sealHandle.invokeExact(handle, outputSlot, outputWidth);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void setTraining(long handle, boolean isTraining) {
        try {
            if (setTrainingHandle != null) setTrainingHandle.invokeExact(handle, isTraining ? 1 : 0);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addAffine(long handle, int inputSlot, int outputSlot, int inputWidth, int outputWidth,
                          float[] weight, float[] bias) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment weightSeg = allocateFloats(arena, weight);
            MemorySegment biasSeg = allocateFloats(arena, bias);
            addAffineHandle.invokeExact(handle, inputSlot, outputSlot, inputWidth, outputWidth, weightSeg, biasSeg);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addRelu(long handle, int inputSlot, int outputSlot) {
        try {
            addReluHandle.invokeExact(handle, inputSlot, outputSlot);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addSigmoid(long handle, int inputSlot, int outputSlot) {
        try {
            if (addSigmoidHandle != null) addSigmoidHandle.invokeExact(handle, inputSlot, outputSlot);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addGelu(long handle, int inputSlot, int outputSlot) {
        try {
            if (addGeluHandle != null) addGeluHandle.invokeExact(handle, inputSlot, outputSlot);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addSilu(long handle, int inputSlot, int outputSlot) {
        try {
            if (addSiluHandle != null) addSiluHandle.invokeExact(handle, inputSlot, outputSlot);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addTanh(long handle, int inputSlot, int outputSlot) {
        try {
            if (addTanhHandle != null) addTanhHandle.invokeExact(handle, inputSlot, outputSlot);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addLeakyRelu(long handle, int inputSlot, int outputSlot, float negativeSlope) {
        try {
            if (addLeakyReluHandle != null) addLeakyReluHandle.invokeExact(handle, inputSlot, outputSlot, negativeSlope);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addElu(long handle, int inputSlot, int outputSlot, float alpha) {
        try {
            if (addEluHandle != null) addEluHandle.invokeExact(handle, inputSlot, outputSlot, alpha);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addSoftmax(long handle, int inputSlot, int outputSlot, long dim) {
        try {
            addSoftmaxHandle.invokeExact(handle, inputSlot, outputSlot, dim);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addLogSoftmax(long handle, int inputSlot, int outputSlot, long dim) {
        try {
            if (addLogSoftmaxHandle != null) addLogSoftmaxHandle.invokeExact(handle, inputSlot, outputSlot, dim);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addLayerNorm(long handle, int inputSlot, int outputSlot, int normalizedWidth,
                             float[] weight, float[] bias, float eps) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment wSeg = weight != null ? allocateFloats(arena, weight) : MemorySegment.NULL;
            MemorySegment bSeg = bias != null ? allocateFloats(arena, bias) : MemorySegment.NULL;
            if (addLayerNormHandle != null) {
                addLayerNormHandle.invokeExact(handle, inputSlot, outputSlot, normalizedWidth, wSeg, bSeg, eps);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addRMSNorm(long handle, int inputSlot, int outputSlot, int normalizedWidth, float[] weight, float eps) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment wSeg = weight != null ? allocateFloats(arena, weight) : MemorySegment.NULL;
            if (addRMSNormHandle != null) {
                addRMSNormHandle.invokeExact(handle, inputSlot, outputSlot, normalizedWidth, wSeg, eps);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addMatrixProduct(long handle, int inputSlot, int outputSlot, int inputWidth, int outputWidth,
                                 float[] rightMatrix) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment rightSeg = allocateFloats(arena, rightMatrix);
            addMatrixProductHandle.invokeExact(handle, inputSlot, outputSlot, inputWidth, outputWidth, rightSeg);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addAttentionMask(long handle, int inputSlot, int outputSlot, long rows, long cols, float[] maskData) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment maskSeg = allocateFloats(arena, maskData);
            addAttentionMaskHandle.invokeExact(handle, inputSlot, outputSlot, rows, cols, maskSeg);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addScaledDotProductAttention(long handle, int querySlot, int keySlot, int valueSlot, int outputSlot, float scale) {
        try {
            if (addScaledDotProductAttentionHandle != null) {
                addScaledDotProductAttentionHandle.invokeExact(handle, querySlot, keySlot, valueSlot, outputSlot, scale);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addBinaryOp(long handle, int opType, int inputSlotA, int inputSlotB, int outputSlot) {
        try {
            if (addBinaryOpHandle != null) addBinaryOpHandle.invokeExact(handle, opType, inputSlotA, inputSlotB, outputSlot);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addUnaryMath(long handle, int opType, int inputSlot, int outputSlot, float param) {
        try {
            if (addUnaryMathHandle != null) addUnaryMathHandle.invokeExact(handle, opType, inputSlot, outputSlot, param);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addReduction(long handle, int redType, int inputSlot, int outputSlot, long dim, boolean keepDim) {
        try {
            if (addReductionHandle != null) addReductionHandle.invokeExact(handle, redType, inputSlot, outputSlot, dim, keepDim ? 1 : 0);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addLoss(long handle, int lossType, int predSlot, int targetSlot, int outputSlot) {
        try {
            if (addLossHandle != null) addLossHandle.invokeExact(handle, lossType, predSlot, targetSlot, outputSlot);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void tensorOp(int opId, float[] input, float[] output, float param) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment inSeg = allocateFloats(arena, input);
            MemorySegment outSeg = allocateFloatBuffer(arena, output.length);
            if (tensorOpHandle != null) {
                tensorOpHandle.invokeExact(opId, inSeg, (long) input.length, outSeg, param);
                float[] res = outSeg.toArray(ValueLayout.JAVA_FLOAT);
                System.arraycopy(res, 0, output, 0, output.length);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public float[] execute(long handle, float[] input, int batchSize) {
        try (Arena arena = Arena.ofConfined()) {
            int outWidth = outputWidth(handle);
            long totalOut = (long) batchSize * (outWidth > 0 ? outWidth : (input.length / batchSize));
            MemorySegment inputSeg = allocateFloats(arena, input);
            MemorySegment outputSeg = allocateFloatBuffer(arena, totalOut);
            executeHandle.invokeExact(handle, inputSeg, batchSize, outputSeg);
            return outputSeg.toArray(ValueLayout.JAVA_FLOAT);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void executeDirect(long handle, ByteBuffer input, int batchSize, ByteBuffer output) {
        try {
            MemorySegment inputSeg = MemorySegment.ofBuffer(input);
            MemorySegment outputSeg = MemorySegment.ofBuffer(output);
            executeHandle.invokeExact(handle, inputSeg, batchSize, outputSeg);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void executeSegment(long handle, MemorySegment inputSegment, int batchSize, MemorySegment outputSegment) {
        try {
            executeHandle.invokeExact(handle, inputSegment, batchSize, outputSegment);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void matmul(MemorySegment a, long aRows, long aCols,
                       MemorySegment b, long bRows, long bCols,
                       MemorySegment out) {
        try {
            matmulHandle.invokeExact(a, aRows, aCols, b, bRows, bCols, out);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void configureThreads(int intraOpThreads, int interOpThreads) {
        try {
            if (configureThreadsHandle != null) {
                configureThreadsHandle.invokeExact(intraOpThreads, interOpThreads);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public int intraOpThreads() {
        try {
            return intraOpThreadsHandle != null ? (int) intraOpThreadsHandle.invokeExact() : 1;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public int interOpThreads() {
        try {
            return interOpThreadsHandle != null ? (int) interOpThreadsHandle.invokeExact() : 1;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
