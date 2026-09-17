/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch;

import java.io.File;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import org.moqui.math.tensor.TensorDescriptor;
import org.moqui.math.tensor.TensorValidator;

public final class LibTorchPanama implements LibTorchBackend {
    public static final LibTorchPanama INSTANCE = new LibTorchPanama();

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
    private final MethodHandle backwardHandle;
    private final MethodHandle stepOptimizerHandle;
    private final MethodHandle zeroGradHandle;
    private final MethodHandle matmulHandle;
    private final MethodHandle tensorOpHandle;
    private final MethodHandle configureThreadsHandle;
    private final MethodHandle intraOpThreadsHandle;
    private final MethodHandle interOpThreadsHandle;
    private final MethodHandle lastErrorHandle;
    private final boolean available;

    public boolean isAvailable() {
        return available;
    }

    private void checkAvailable() {
        if (!available) {
            throw new UnsatisfiedLinkError("LibTorch native library (libgroovy_math_libtorch) is not loaded or available. " +
                "Run './gradlew buildLibTorchNative' to build or package the native binaries, " +
                "or ensure the native library is available on java.library.path or ~/.local/opt/libtorch.");
        }
    }

    private LibTorchPanama() {
        this.linker = Linker.nativeLinker();
        this.symbols = loadSymbols();
        boolean hasRequiredSymbols = this.symbols != null && this.symbols.find("torch_panama_create_plan").isPresent();
        this.available = hasRequiredSymbols;

        if (!hasRequiredSymbols) {
            this.createPlanHandle = null;
            this.destroyHandle = null;
            this.outputWidthHandle = null;
            this.sealHandle = null;
            this.setTrainingHandle = null;
            this.addAffineHandle = null;
            this.addReluHandle = null;
            this.addSigmoidHandle = null;
            this.addGeluHandle = null;
            this.addSiluHandle = null;
            this.addTanhHandle = null;
            this.addLeakyReluHandle = null;
            this.addEluHandle = null;
            this.addSoftmaxHandle = null;
            this.addLogSoftmaxHandle = null;
            this.addLayerNormHandle = null;
            this.addRMSNormHandle = null;
            this.addMatrixProductHandle = null;
            this.addAttentionMaskHandle = null;
            this.addScaledDotProductAttentionHandle = null;
            this.addBinaryOpHandle = null;
            this.addUnaryMathHandle = null;
            this.addReductionHandle = null;
            this.addLossHandle = null;
            this.executeHandle = null;
            this.backwardHandle = null;
            this.stepOptimizerHandle = null;
            this.zeroGradHandle = null;
            this.matmulHandle = null;
            this.tensorOpHandle = null;
            this.configureThreadsHandle = null;
            this.intraOpThreadsHandle = null;
            this.interOpThreadsHandle = null;
            this.lastErrorHandle = null;
            return;
        }

        this.lastErrorHandle = findOptional("torch_panama_last_error",
            FunctionDescriptor.of(ValueLayout.ADDRESS));
        this.createPlanHandle = find("torch_panama_create_plan",
            FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT));
        this.destroyHandle = find("torch_panama_destroy",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));
        this.outputWidthHandle = find("torch_panama_output_width",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));
        this.sealHandle = find("torch_panama_seal",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.setTrainingHandle = findOptional("torch_panama_set_training",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT));

        this.addAffineHandle = find("torch_panama_add_affine",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
        this.addReluHandle = find("torch_panama_add_relu",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addSigmoidHandle = findOptional("torch_panama_add_sigmoid",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addGeluHandle = findOptional("torch_panama_add_gelu",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addSiluHandle = findOptional("torch_panama_add_silu",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addTanhHandle = findOptional("torch_panama_add_tanh",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addLeakyReluHandle = findOptional("torch_panama_add_leaky_relu",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_FLOAT));
        this.addEluHandle = findOptional("torch_panama_add_elu",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_FLOAT));
        this.addSoftmaxHandle = find("torch_panama_add_softmax",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));
        this.addLogSoftmaxHandle = findOptional("torch_panama_add_log_softmax",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));
        this.addLayerNormHandle = findOptional("torch_panama_add_layer_norm",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_FLOAT));
        this.addRMSNormHandle = findOptional("torch_panama_add_rms_norm",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_FLOAT));
        this.addMatrixProductHandle = find("torch_panama_add_matrix_product",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS));
        this.addAttentionMaskHandle = find("torch_panama_add_attention_mask",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));
        this.addScaledDotProductAttentionHandle = findOptional("torch_panama_add_scaled_dot_product_attention",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_FLOAT));

        this.addBinaryOpHandle = findOptional("torch_panama_add_binary_op",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.addUnaryMathHandle = findOptional("torch_panama_add_unary_math",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_FLOAT));
        this.addReductionHandle = findOptional("torch_panama_add_reduction",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT));
        this.addLossHandle = findOptional("torch_panama_add_loss",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

        this.executeHandle = find("torch_panama_execute",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG));
        this.backwardHandle = findOptional("torch_panama_backward",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT));
        this.stepOptimizerHandle = findOptional("torch_panama_step_optimizer",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_FLOAT, ValueLayout.JAVA_FLOAT, ValueLayout.JAVA_FLOAT));
        this.zeroGradHandle = findOptional("torch_panama_zero_grad",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));

        this.matmulHandle = find("torch_panama_matmul",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG,
                ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));
        this.tensorOpHandle = findOptional("torch_panama_tensor_op",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_FLOAT));

        this.configureThreadsHandle = findOptional("torch_panama_configure_threads",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
        this.intraOpThreadsHandle = findOptional("torch_panama_intra_op_threads",
            FunctionDescriptor.of(ValueLayout.JAVA_INT));
        this.interOpThreadsHandle = findOptional("torch_panama_inter_op_threads",
            FunctionDescriptor.of(ValueLayout.JAVA_INT));
    }

    private static SymbolLookup loadSymbols() {
        String explicitPath = System.getProperty("moqui.math.libtorch.library");
        if (explicitPath != null && new File(explicitPath).exists()) {
            return SymbolLookup.libraryLookup(new File(explicitPath).toPath(), Arena.global());
        }
        File defaultBuildLib = new File("build/native/libgroovy_math_libtorch.so");
        if (defaultBuildLib.exists()) {
            return SymbolLookup.libraryLookup(defaultBuildLib.toPath(), Arena.global());
        }
        String userHome = System.getProperty("user.home", "");
        File cachedLib = new File(userHome, ".groovy-math/native/libgroovy_math_libtorch.so");
        if (cachedLib.exists()) {
            return SymbolLookup.libraryLookup(cachedLib.toPath(), Arena.global());
        }
        try {
            org.moqui.math.nativeutil.NativeLibraryLoader.loadLibrary("groovy_math_libtorch");
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

    public String getLastError() {
        if (lastErrorHandle == null) return "Unknown error (lastErrorHandle unavailable)";
        try {
            MemorySegment seg = (MemorySegment) lastErrorHandle.invokeExact();
            if (seg.equals(MemorySegment.NULL) || seg.address() == 0) return "None";
            return seg.reinterpret(4096).getString(0);
        } catch (Throwable t) {
            return "Failed to retrieve native error: " + t.getMessage();
        }
    }

    private void checkRc(int rc, String opName) {
        if (rc < 0) {
            String msg = getLastError();
            throw new RuntimeException("LibTorch native operation '" + opName + "' failed (code " + rc + "): " + msg);
        }
    }

    public static MemorySegment allocateFloats(Arena arena, float[] values) {
        MemorySegment seg = arena.allocate((long) values.length * Float.BYTES, ValueLayout.JAVA_FLOAT.byteAlignment());
        MemorySegment.copy(MemorySegment.ofArray(values), 0, seg, 0, (long) values.length * Float.BYTES);
        return seg;
    }

    public static MemorySegment allocateFloatBuffer(Arena arena, long elementCount) {
        return arena.allocate(elementCount * Float.BYTES, ValueLayout.JAVA_FLOAT.byteAlignment());
    }

    /**
     * Input width per live plan handle. The native Plan struct keeps this value but does not
     * export it, and the guard in {@link #executeSegment} needs it to size the input segment.
     * Tracking it here keeps the check possible without changing the C++ ABI; a handle this
     * wrapper did not create is simply absent, and its input side goes unchecked rather than
     * being wrongly rejected.
     */
    private final ConcurrentMap<Long, Integer> planInputWidths = new ConcurrentHashMap<>();

    /**
     * Exclusion locks for plans switched into training mode.
     *
     * <p>In inference the native run_plan keeps its slot map on the stack and leaves the Plan
     * untouched, so any number of threads may execute one handle at once. In training it does
     * the opposite: it move-assigns the slot map into Plan::last_slots, bumps step_count and
     * updates the optimizer buffers. Two threads executing one training handle therefore race
     * on an unordered_map, which is undefined behaviour rather than a wrong answer.
     *
     * <p>A handle gets a lock the moment training is switched on, and stateful calls take it.
     * Inference handles never appear here and never pay for the lock. Callers who want real
     * parallel training want one native plan per thread, which is what PanamaEnginePool is for.
     */
    private final ConcurrentMap<Long, ReentrantLock> statefulPlanLocks = new ConcurrentHashMap<>();

    @Override
    public long createPlan(int inputWidth) {
        checkAvailable();
        try {
            long handle = (long) createPlanHandle.invokeExact(inputWidth);
            if (handle <= 0L) {
                throw new RuntimeException("Failed to create native plan: " + getLastError());
            }
            planInputWidths.put(handle, inputWidth);
            return handle;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void destroy(long handle) {
        try {
            planInputWidths.remove(handle);
            statefulPlanLocks.remove(handle);
            if (destroyHandle != null) {
                int rc = (int) destroyHandle.invokeExact(handle);
                checkRc(rc, "destroy");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /** Declared input width of a plan, or -1 when this wrapper did not create it. */
    public int inputWidth(long handle) {
        Integer width = planInputWidths.get(handle);
        return width == null ? -1 : width;
    }

    public int outputWidth(long handle) {
        try {
            int width = (int) outputWidthHandle.invokeExact(handle);
            if (width < 0) {
                throw new RuntimeException("LibTorch native outputWidth failed: " + getLastError());
            }
            return width;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void seal(long handle, int outputSlot, int outputWidth) {
        try {
            int rc = (int) sealHandle.invokeExact(handle, outputSlot, outputWidth);
            checkRc(rc, "seal");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void setTraining(long handle, boolean isTraining) {
        try {
            if (setTrainingHandle != null) {
                int rc = (int) setTrainingHandle.invokeExact(handle, isTraining ? 1 : 0);
                checkRc(rc, "setTraining");
            }
            if (isTraining) statefulPlanLocks.computeIfAbsent(handle, h -> new ReentrantLock());
            else statefulPlanLocks.remove(handle);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /** True when this handle has been switched into training mode and carries mutable state. */
    public boolean isStateful(long handle) {
        return statefulPlanLocks.containsKey(handle);
    }

    /**
     * Runs a native call under this handle's exclusion lock when the plan is stateful, and
     * directly otherwise. Inference stays fully parallel; training serialises instead of racing.
     */
    private <T> T underPlanExclusion(long handle, Supplier<T> call) {
        ReentrantLock lock = statefulPlanLocks.get(handle);
        if (lock == null) return call.get();
        lock.lock();
        try {
            return call.get();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void addAffine(long handle, int inputSlot, int outputSlot, int inputWidth, int outputWidth,
                          float[] weight, float[] bias) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment weightSeg = allocateFloats(arena, weight);
            MemorySegment biasSeg = allocateFloats(arena, bias);
            int rc = (int) addAffineHandle.invokeExact(handle, inputSlot, outputSlot, inputWidth, outputWidth, weightSeg, biasSeg);
            checkRc(rc, "addAffine");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addRelu(long handle, int inputSlot, int outputSlot) {
        try {
            int rc = (int) addReluHandle.invokeExact(handle, inputSlot, outputSlot);
            checkRc(rc, "addRelu");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addSigmoid(long handle, int inputSlot, int outputSlot) {
        try {
            if (addSigmoidHandle != null) {
                int rc = (int) addSigmoidHandle.invokeExact(handle, inputSlot, outputSlot);
                checkRc(rc, "addSigmoid");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addGelu(long handle, int inputSlot, int outputSlot) {
        try {
            if (addGeluHandle != null) {
                int rc = (int) addGeluHandle.invokeExact(handle, inputSlot, outputSlot);
                checkRc(rc, "addGelu");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addSilu(long handle, int inputSlot, int outputSlot) {
        try {
            if (addSiluHandle != null) {
                int rc = (int) addSiluHandle.invokeExact(handle, inputSlot, outputSlot);
                checkRc(rc, "addSilu");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addTanh(long handle, int inputSlot, int outputSlot) {
        try {
            if (addTanhHandle != null) {
                int rc = (int) addTanhHandle.invokeExact(handle, inputSlot, outputSlot);
                checkRc(rc, "addTanh");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addLeakyRelu(long handle, int inputSlot, int outputSlot, float negativeSlope) {
        try {
            if (addLeakyReluHandle != null) {
                int rc = (int) addLeakyReluHandle.invokeExact(handle, inputSlot, outputSlot, negativeSlope);
                checkRc(rc, "addLeakyRelu");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addElu(long handle, int inputSlot, int outputSlot, float alpha) {
        try {
            if (addEluHandle != null) {
                int rc = (int) addEluHandle.invokeExact(handle, inputSlot, outputSlot, alpha);
                checkRc(rc, "addElu");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addSoftmax(long handle, int inputSlot, int outputSlot, long dim) {
        try {
            int rc = (int) addSoftmaxHandle.invokeExact(handle, inputSlot, outputSlot, dim);
            checkRc(rc, "addSoftmax");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addLogSoftmax(long handle, int inputSlot, int outputSlot, long dim) {
        try {
            if (addLogSoftmaxHandle != null) {
                int rc = (int) addLogSoftmaxHandle.invokeExact(handle, inputSlot, outputSlot, dim);
                checkRc(rc, "addLogSoftmax");
            }
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
                int rc = (int) addLayerNormHandle.invokeExact(handle, inputSlot, outputSlot, normalizedWidth, wSeg, bSeg, eps);
                checkRc(rc, "addLayerNorm");
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
                int rc = (int) addRMSNormHandle.invokeExact(handle, inputSlot, outputSlot, normalizedWidth, wSeg, eps);
                checkRc(rc, "addRMSNorm");
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
            int rc = (int) addMatrixProductHandle.invokeExact(handle, inputSlot, outputSlot, inputWidth, outputWidth, rightSeg);
            checkRc(rc, "addMatrixProduct");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addAttentionMask(long handle, int inputSlot, int outputSlot, long rows, long cols, float[] maskData) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment maskSeg = allocateFloats(arena, maskData);
            int rc = (int) addAttentionMaskHandle.invokeExact(handle, inputSlot, outputSlot, rows, cols, maskSeg);
            checkRc(rc, "addAttentionMask");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addScaledDotProductAttention(long handle, int querySlot, int keySlot, int valueSlot, int outputSlot, float scale) {
        try {
            if (addScaledDotProductAttentionHandle != null) {
                int rc = (int) addScaledDotProductAttentionHandle.invokeExact(handle, querySlot, keySlot, valueSlot, outputSlot, scale);
                checkRc(rc, "addScaledDotProductAttention");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addBinaryOp(long handle, int opType, int inputSlotA, int inputSlotB, int outputSlot) {
        try {
            if (addBinaryOpHandle != null) {
                int rc = (int) addBinaryOpHandle.invokeExact(handle, opType, inputSlotA, inputSlotB, outputSlot);
                checkRc(rc, "addBinaryOp");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addUnaryMath(long handle, int opType, int inputSlot, int outputSlot, float param) {
        try {
            if (addUnaryMathHandle != null) {
                int rc = (int) addUnaryMathHandle.invokeExact(handle, opType, inputSlot, outputSlot, param);
                checkRc(rc, "addUnaryMath");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addReduction(long handle, int redType, int inputSlot, int outputSlot, long dim, boolean keepDim) {
        try {
            if (addReductionHandle != null) {
                int rc = (int) addReductionHandle.invokeExact(handle, redType, inputSlot, outputSlot, dim, keepDim ? 1 : 0);
                checkRc(rc, "addReduction");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addLoss(long handle, int lossType, int predSlot, int targetSlot, int outputSlot) {
        try {
            if (addLossHandle != null) {
                int rc = (int) addLossHandle.invokeExact(handle, lossType, predSlot, targetSlot, outputSlot);
                checkRc(rc, "addLoss");
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void backward(long handle, int lossSlot) {
        underPlanExclusion(handle, () -> {
            try {
                if (backwardHandle != null) {
                    int rc = (int) backwardHandle.invokeExact(handle, lossSlot);
                    checkRc(rc, "backward");
                }
                return null;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    @Override
    public void stepOptimizer(long handle, int optType, float lr, float weightDecay, float momentum) {
        underPlanExclusion(handle, () -> {
            try {
                if (stepOptimizerHandle != null) {
                    int rc = (int) stepOptimizerHandle.invokeExact(handle, optType, lr, weightDecay, momentum);
                    checkRc(rc, "stepOptimizer");
                }
                return null;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    @Override
    public void zeroGrad(long handle) {
        underPlanExclusion(handle, () -> {
            try {
                if (zeroGradHandle != null) {
                    int rc = (int) zeroGradHandle.invokeExact(handle);
                    checkRc(rc, "zeroGrad");
                }
                return null;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    @Override
    public void tensorOp(int opId, float[] input, float[] output, float param) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment inSeg = allocateFloats(arena, input);
            MemorySegment outSeg = allocateFloatBuffer(arena, output.length);
            if (tensorOpHandle != null) {
                int rc = (int) tensorOpHandle.invokeExact(opId, inSeg, (long) input.length, outSeg, param);
                checkRc(rc, "tensorOp");
                float[] res = outSeg.toArray(ValueLayout.JAVA_FLOAT);
                System.arraycopy(res, 0, output, 0, output.length);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public float[] execute(long handle, float[] input, int batchSize) {
        return underPlanExclusion(handle, () -> executeUnlocked(handle, input, batchSize));
    }

    private float[] executeUnlocked(long handle, float[] input, int batchSize) {
        try (Arena arena = Arena.ofConfined()) {
            int outWidth = outputWidth(handle);
            long totalOut = (long) batchSize * (outWidth > 0 ? outWidth : (input.length / batchSize));
            MemorySegment inputSeg = allocateFloats(arena, input);
            MemorySegment outputSeg = allocateFloatBuffer(arena, totalOut);
            int rc = (int) executeHandle.invokeExact(handle, inputSeg, batchSize, outputSeg, outputSeg.byteSize());
            checkRc(rc, "execute");
            return outputSeg.toArray(ValueLayout.JAVA_FLOAT);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void executeDirect(long handle, ByteBuffer input, int batchSize, ByteBuffer output) {
        MemorySegment inputSeg = MemorySegment.ofBuffer(input);
        MemorySegment outputSeg = MemorySegment.ofBuffer(output);
        guardPlanInvocation(handle, inputSeg, outputSeg, batchSize);
        underPlanExclusion(handle, () -> {
            try {
                int rc = (int) executeHandle.invokeExact(handle, inputSeg, batchSize, outputSeg, outputSeg.byteSize());
                checkRc(rc, "executeDirect");
                return null;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    public void executeSegment(long handle, MemorySegment inputSegment, int batchSize, MemorySegment outputSegment) {
        guardPlanInvocation(handle, inputSegment, outputSegment, batchSize);
        underPlanExclusion(handle, () -> {
            try {
                int rc = (int) executeHandle.invokeExact(handle, inputSegment, batchSize, outputSegment, outputSegment.byteSize());
                checkRc(rc, "executeSegment");
                return null;
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    public void matmul(MemorySegment a, long aRows, long aCols,
                       MemorySegment b, long bRows, long bCols,
                       MemorySegment out) {
        checkAvailable();
        TensorValidator.validateMatmul(a, aRows, aCols, b, bRows, bCols, out, TensorDescriptor.DTYPE_FLOAT32);
        try {
            int rc = (int) matmulHandle.invokeExact(a, aRows, aCols, b, bRows, bCols, out);
            checkRc(rc, "matmul");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public void configureThreads(int intraOpThreads, int interOpThreads) {
        try {
            if (configureThreadsHandle != null) {
                int rc = (int) configureThreadsHandle.invokeExact(intraOpThreads, interOpThreads);
                checkRc(rc, "configureThreads");
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

    /**
     * Refuses a plan invocation whose segments cannot hold the batch, before the call reaches
     * native code. Without this the C++ side reshapes the raw pointer against the declared
     * width and reads or writes past the end of the buffer.
     */
    private void guardPlanInvocation(long handle, MemorySegment input, MemorySegment output, int batchSize) {
        TensorValidator.validatePlanInvocation("libtorch:" + handle, input, output,
                batchSize, inputWidth(handle), outputWidth(handle), TensorDescriptor.DTYPE_FLOAT32);
    }
}
