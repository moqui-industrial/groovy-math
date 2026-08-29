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
    private final MethodHandle addAffineHandle;
    private final MethodHandle addReluHandle;
    private final MethodHandle addSoftmaxHandle;
    private final MethodHandle addMatrixProductHandle;
    private final MethodHandle addAttentionMaskHandle;
    private final MethodHandle executeHandle;
    private final MethodHandle matmulHandle;
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

        this.addAffineHandle = find("jax_panama_add_affine",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS));

        this.addReluHandle = find("jax_panama_add_relu",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

        this.addSoftmaxHandle = find("jax_panama_add_softmax",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));

        this.addMatrixProductHandle = find("jax_panama_add_matrix_product",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS));

        this.addAttentionMaskHandle = find("jax_panama_add_attention_mask",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));

        this.executeHandle = find("jax_panama_execute",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS));

        this.matmulHandle = find("jax_panama_matmul",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG,
                ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));

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
        return SymbolLookup.loaderLookup();
    }

    private MethodHandle find(String symbolName, FunctionDescriptor descriptor) {
        MemorySegment symbol = symbols.find(symbolName).orElseThrow(() ->
            new IllegalStateException("JAX native symbol not found: " + symbolName));
        return linker.downcallHandle(symbol, descriptor);
    }

    private MethodHandle findOptional(String symbolName, FunctionDescriptor descriptor) {
        return symbols.find(symbolName).map(s -> linker.downcallHandle(s, descriptor)).orElse(null);
    }

    public static MemorySegment allocateFloats(Arena arena, float[] values) {
        MemorySegment seg = arena.allocate((long) values.length * Float.BYTES);
        MemorySegment.copy(MemorySegment.ofArray(values), ValueLayout.JAVA_FLOAT, 0L, seg, ValueLayout.JAVA_FLOAT, 0L, values.length);
        return seg;
    }

    public static MemorySegment allocateFloatBuffer(Arena arena, long floatCount) {
        return arena.allocate(floatCount * Float.BYTES);
    }

    public int outputWidth(long handle) {
        try {
            return (int) outputWidthHandle.invokeExact(handle);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
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

    @Override
    public void seal(long handle, int outputSlot, int outputWidth) {
        try {
            sealHandle.invokeExact(handle, outputSlot, outputWidth);
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

    public void addSoftmax(long handle, int inputSlot, int outputSlot, long dim) {
        try {
            addSoftmaxHandle.invokeExact(handle, inputSlot, outputSlot, dim);
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

    public void addAttentionMask(long handle, int inputSlot, int outputSlot, long rows, long cols, float[] maskData) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment maskSeg = allocateFloats(arena, maskData);
            addAttentionMaskHandle.invokeExact(handle, inputSlot, outputSlot, rows, cols, maskSeg);
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
