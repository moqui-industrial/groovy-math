/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.opencv;

import java.io.File;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public final class OpenCvPanama {
    public static final OpenCvPanama INSTANCE = new OpenCvPanama();

    private final Linker linker;
    private final SymbolLookup symbols;

    private final MethodHandle createPlanHandle;
    private final MethodHandle destroyHandle;
    private final MethodHandle outputWidthHandle;
    private final MethodHandle outputHeightHandle;
    private final MethodHandle sealHandle;
    private final MethodHandle addGaussianBlurHandle;
    private final MethodHandle addSobelHandle;
    private final MethodHandle addWarpAffineHandle;
    private final MethodHandle addWarpPerspectiveHandle;
    private final MethodHandle addFilter2dHandle;
    private final MethodHandle executeHandle;
    private final MethodHandle filter2dDirectHandle;
    private final MethodHandle warpAffineDirectHandle;

    private OpenCvPanama() {
        this.linker = Linker.nativeLinker();
        this.symbols = loadSymbols();

        this.createPlanHandle = find("opencv_panama_create_plan",
            FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

        this.destroyHandle = find("opencv_panama_destroy",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG));

        this.outputWidthHandle = find("opencv_panama_output_width",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));

        this.outputHeightHandle = find("opencv_panama_output_height",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG));

        this.sealHandle = find("opencv_panama_seal",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

        this.addGaussianBlurHandle = find("opencv_panama_add_gaussian_blur",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_DOUBLE));

        this.addSobelHandle = find("opencv_panama_add_sobel",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

        this.addWarpAffineHandle = find("opencv_panama_add_warp_affine",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

        this.addWarpPerspectiveHandle = find("opencv_panama_add_warp_perspective",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

        this.addFilter2dHandle = find("opencv_panama_add_filter2d",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));

        this.executeHandle = find("opencv_panama_execute",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.ADDRESS));

        this.filter2dDirectHandle = find("opencv_panama_filter2d_direct",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS));

        this.warpAffineDirectHandle = find("opencv_panama_warp_affine_direct",
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT,
                ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS));
    }

    private static SymbolLookup loadSymbols() {
        String explicitPath = System.getProperty("groovy.math.opencv.library");
        if (explicitPath != null && new File(explicitPath).exists()) {
            return SymbolLookup.libraryLookup(new File(explicitPath).toPath(), Arena.global());
        }
        File defaultBuildLib = new File("build/native-opencv/libgroovy_math_opencv.so");
        if (defaultBuildLib.exists()) {
            return SymbolLookup.libraryLookup(defaultBuildLib.toPath(), Arena.global());
        }
        return SymbolLookup.loaderLookup();
    }

    private MethodHandle find(String symbolName, FunctionDescriptor descriptor) {
        MemorySegment symbol = symbols.find(symbolName).orElseThrow(() ->
            new IllegalStateException("OpenCV native symbol not found: " + symbolName));
        return linker.downcallHandle(symbol, descriptor);
    }

    public static MemorySegment allocateFloats(Arena arena, float[] values) {
        MemorySegment seg = arena.allocate((long) values.length * Float.BYTES);
        MemorySegment.copy(MemorySegment.ofArray(values), ValueLayout.JAVA_FLOAT, 0L, seg, ValueLayout.JAVA_FLOAT, 0L, values.length);
        return seg;
    }

    public static MemorySegment allocateDoubles(Arena arena, double[] values) {
        MemorySegment seg = arena.allocate((long) values.length * Double.BYTES);
        MemorySegment.copy(MemorySegment.ofArray(values), ValueLayout.JAVA_DOUBLE, 0L, seg, ValueLayout.JAVA_DOUBLE, 0L, values.length);
        return seg;
    }

    public static MemorySegment allocateFloatBuffer(Arena arena, long floatCount) {
        return arena.allocate(floatCount * Float.BYTES);
    }

    public long createPlan(int width, int height) {
        try {
            return (long) createPlanHandle.invokeExact(width, height);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

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

    public int outputHeight(long handle) {
        try {
            return (int) outputHeightHandle.invokeExact(handle);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void seal(long handle, int outputWidth, int outputHeight) {
        try {
            sealHandle.invokeExact(handle, outputWidth, outputHeight);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void addGaussianBlur(long handle, int ksize, double sigma) {
        try {
            addGaussianBlurHandle.invokeExact(handle, ksize, sigma);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void addSobel(long handle, int dx, int dy, int ksize) {
        try {
            addSobelHandle.invokeExact(handle, dx, dy, ksize);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void addWarpAffine(long handle, double[] matrix2x3, int outWidth, int outHeight) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment mSeg = allocateDoubles(arena, matrix2x3);
            addWarpAffineHandle.invokeExact(handle, mSeg, outWidth, outHeight);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void addWarpPerspective(long handle, double[] matrix3x3, int outWidth, int outHeight) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment mSeg = allocateDoubles(arena, matrix3x3);
            addWarpPerspectiveHandle.invokeExact(handle, mSeg, outWidth, outHeight);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void addFilter2d(long handle, float[] kernel, int kwidth, int kheight) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment kSeg = allocateFloats(arena, kernel);
            addFilter2dHandle.invokeExact(handle, kSeg, kwidth, kheight);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public float[] execute(long handle, float[] input) {
        try (Arena arena = Arena.ofConfined()) {
            int outW = outputWidth(handle);
            int outH = outputHeight(handle);
            MemorySegment inSeg = allocateFloats(arena, input);
            MemorySegment outSeg = allocateFloatBuffer(arena, (long) outW * outH);
            executeHandle.invokeExact(handle, inSeg, outSeg);
            return outSeg.toArray(ValueLayout.JAVA_FLOAT);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void executeSegment(long handle, MemorySegment inSeg, MemorySegment outSeg) {
        try {
            executeHandle.invokeExact(handle, inSeg, outSeg);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void filter2d(MemorySegment src, int width, int height,
                         MemorySegment kernel, int kwidth, int kheight,
                         MemorySegment dst) {
        try {
            filter2dDirectHandle.invokeExact(src, width, height, kernel, kwidth, kheight, dst);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void warpAffine(MemorySegment src, int width, int height,
                           MemorySegment matrix2x3, int outW, int outH,
                           MemorySegment dst) {
        try {
            warpAffineDirectHandle.invokeExact(src, width, height, matrix2x3, outW, outH, dst);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
