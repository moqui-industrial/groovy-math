/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.onnx;

import java.io.File;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

/**
 * Foreign Function & Memory API (Panama) bridge to ONNX Runtime C API.
 * Dispatches inference over ONNX computational models with zero-copy off-heap MemorySegments.
 */
public final class OnnxPanama {

    private static final OnnxPanama INSTANCE = new OnnxPanama();

    public static OnnxPanama getInstance() {
        return INSTANCE;
    }

    private final Linker linker;
    private final SymbolLookup symbols;
    private final MethodHandle isAvailableHandle;
    private final MethodHandle createSessionHandle;
    private final MethodHandle destroySessionHandle;
    private final MethodHandle runHandle;
    private final boolean available;

    public boolean isAvailable() {
        return available;
    }

    private void checkAvailable() {
        if (!available) {
            throw new UnsatisfiedLinkError("ONNX Runtime native library (libgroovy_math_onnx) is not available. " +
                "Ensure libonnxruntime is installed on the system and run './gradlew buildOnnxNative'.");
        }
    }

    private OnnxPanama() {
        this.linker = Linker.nativeLinker();
        this.symbols = loadSymbols();
        boolean hasSymbols = this.symbols != null && this.symbols.find("onnx_panama_create_session").isPresent();

        if (!hasSymbols) {
            this.isAvailableHandle = null;
            this.createSessionHandle = null;
            this.destroySessionHandle = null;
            this.runHandle = null;
            this.available = false;
            return;
        }

        this.isAvailableHandle = find("onnx_panama_is_available", FunctionDescriptor.of(ValueLayout.JAVA_INT));
        this.createSessionHandle = find("onnx_panama_create_session",
            FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));
        this.destroySessionHandle = find("onnx_panama_destroy_session",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG));
        this.runHandle = find("onnx_panama_run",
            FunctionDescriptor.of(ValueLayout.JAVA_LONG,
                ValueLayout.JAVA_LONG,    // session_handle
                ValueLayout.ADDRESS,      // input_name
                ValueLayout.ADDRESS,      // input_data
                ValueLayout.ADDRESS,      // input_shape
                ValueLayout.JAVA_LONG,    // input_rank
                ValueLayout.ADDRESS,      // output_name
                ValueLayout.ADDRESS,      // output_data
                ValueLayout.JAVA_LONG     // output_size
            ));

        boolean runtimeAvailable = false;
        try {
            if (isAvailableHandle != null) {
                int res = (int) isAvailableHandle.invokeExact();
                runtimeAvailable = (res == 1);
            }
        } catch (Throwable ignored) {
        }
        this.available = runtimeAvailable;
    }

    private static SymbolLookup loadSymbols() {
        File defaultBuildLib = new File("build/native/libgroovy_math_onnx.so");
        if (defaultBuildLib.exists()) {
            return SymbolLookup.libraryLookup(defaultBuildLib.toPath(), Arena.global());
        }
        File sourceBuildLib = new File("src/main/cpp/onnx/libgroovy_math_onnx.so");
        if (sourceBuildLib.exists()) {
            return SymbolLookup.libraryLookup(sourceBuildLib.toPath(), Arena.global());
        }
        try {
            org.moqui.math.nativeutil.NativeLibraryLoader.loadLibrary("groovy_math_onnx");
        } catch (Throwable ignored) {
        }
        return SymbolLookup.loaderLookup();
    }

    private MethodHandle find(String name, FunctionDescriptor descriptor) {
        return symbols.find(name)
            .map(addr -> linker.downcallHandle(addr, descriptor))
            .orElse(null);
    }

    private static MemorySegment allocateCString(Arena arena, String str) {
        if (str == null) return MemorySegment.NULL;
        byte[] bytes = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        MemorySegment seg = arena.allocate((long) bytes.length + 1L);
        MemorySegment.copy(MemorySegment.ofArray(bytes), 0L, seg, 0L, (long) bytes.length);
        seg.set(ValueLayout.JAVA_BYTE, (long) bytes.length, (byte) 0);
        return seg;
    }

    public long createSession(String modelPath) {
        checkAvailable();
        if (modelPath == null || modelPath.isEmpty()) return 0L;
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment pathSeg = allocateCString(arena, modelPath);
            return (long) createSessionHandle.invokeExact(pathSeg);
        } catch (Throwable t) {
            throw new RuntimeException("Failed to create ONNX session for model: " + modelPath, t);
        }
    }

    public void destroySession(long sessionHandle) {
        if (sessionHandle == 0L || !available) return;
        try {
            destroySessionHandle.invokeExact(sessionHandle);
        } catch (Throwable t) {
            throw new RuntimeException("Failed to destroy ONNX session: " + sessionHandle, t);
        }
    }

    public void run(long sessionHandle, String inputName, MemorySegment inputData, long[] shape,
                    String outputName, MemorySegment outputData, long outputSize) {
        checkAvailable();
        if (sessionHandle == 0L) throw new IllegalArgumentException("Invalid ONNX session handle: 0");

        try (Arena arena = Arena.ofConfined()) {
            MemorySegment inNameSeg = inputName != null ? allocateCString(arena, inputName) : MemorySegment.NULL;
            MemorySegment outNameSeg = outputName != null ? allocateCString(arena, outputName) : MemorySegment.NULL;

            MemorySegment shapeSeg = arena.allocate((long) shape.length * ValueLayout.JAVA_LONG.byteSize());
            for (int i = 0; i < shape.length; i++) {
                shapeSeg.setAtIndex(ValueLayout.JAVA_LONG, (long) i, shape[i]);
            }

            long status = (long) runHandle.invokeExact(
                sessionHandle,
                inNameSeg,
                inputData,
                shapeSeg,
                (long) shape.length,
                outNameSeg,
                outputData,
                outputSize
            );

            if (status != 0L) {
                throw new RuntimeException("ONNX Runtime execution failed with status code: " + status);
            }
        } catch (Throwable t) {
            if (t instanceof RuntimeException) throw (RuntimeException) t;
            throw new RuntimeException("ONNX Runtime invocation failed", t);
        }
    }
}
