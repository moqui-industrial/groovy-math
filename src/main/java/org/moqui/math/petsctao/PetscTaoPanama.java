/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.petsctao;

import java.io.File;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public final class PetscTaoPanama implements PetscTaoBackend {
    public static final PetscTaoPanama INSTANCE = new PetscTaoPanama();

    private final Linker linker;
    private final SymbolLookup symbols;
    private final MethodHandle isAvailableHandle;
    private final MethodHandle lastErrorHandle;
    private final MethodHandle createPlanHandle;
    private final MethodHandle solveHandle;
    private final MethodHandle destroyHandle;

    private PetscTaoPanama() {
        this.linker = Linker.nativeLinker();
        this.symbols = loadSymbols();

        this.lastErrorHandle = find("petsc_panama_last_error",
            FunctionDescriptor.of(ValueLayout.ADDRESS));

        this.createPlanHandle = find("petsc_panama_create_bounded_quadratic_plan",
            FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT,
                ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS,
                ValueLayout.ADDRESS, ValueLayout.ADDRESS));

        this.solveHandle = find("petsc_panama_solve",
            FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG));

        this.destroyHandle = find("petsc_panama_destroy",
            FunctionDescriptor.ofVoid(ValueLayout.JAVA_LONG));
        this.isAvailableHandle = null;
    }

    private static SymbolLookup loadSymbols() {
        String explicitPath = System.getProperty("moqui.math.petsctao.library");
        if (explicitPath != null && new File(explicitPath).exists()) {
            return SymbolLookup.libraryLookup(new File(explicitPath).toPath(), Arena.global());
        }
        File defaultBuildLib = new File("build/native-petsctao/libgroovy_math_petsctao.so");
        if (defaultBuildLib.exists()) {
            return SymbolLookup.libraryLookup(defaultBuildLib.toPath(), Arena.global());
        }
        return SymbolLookup.loaderLookup();
    }

    private MethodHandle find(String symbolName, FunctionDescriptor descriptor) {
        MemorySegment symbol = symbols.find(symbolName).orElseThrow(() ->
            new IllegalStateException("PETSc native symbol not found: " + symbolName));
        return linker.downcallHandle(symbol, descriptor);
    }

    public static MemorySegment allocateDoubles(Arena arena, double[] values) {
        MemorySegment seg = arena.allocate((long) values.length * Double.BYTES);
        MemorySegment.copy(MemorySegment.ofArray(values), ValueLayout.JAVA_DOUBLE, 0L, seg, ValueLayout.JAVA_DOUBLE, 0L, values.length);
        return seg;
    }

    public String getLastError() {
        if (lastErrorHandle == null) return null;
        try {
            MemorySegment seg = (MemorySegment) lastErrorHandle.invokeExact();
            if (seg.equals(MemorySegment.NULL) || seg.address() == 0) return null;
            return seg.reinterpret(4096).getUtf8String(0);
        } catch (Throwable ignored) {
            return null;
        }
    }

    @Override
    public long createBoundedQuadraticPlan(int dimension, double[] hessian, double[] linear,
                                           double[] lowerBounds, double[] upperBounds,
                                           double[] initialPoint) {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment hessianSeg = allocateDoubles(arena, hessian);
            MemorySegment linearSeg = allocateDoubles(arena, linear);
            MemorySegment lowerSeg = allocateDoubles(arena, lowerBounds);
            MemorySegment upperSeg = allocateDoubles(arena, upperBounds);
            MemorySegment initialSeg = allocateDoubles(arena, initialPoint);

            long handle = (long) createPlanHandle.invokeExact(dimension, hessianSeg, linearSeg, lowerSeg, upperSeg, initialSeg);
            if (handle == 0L) {
                String err = getLastError();
                throw new IllegalStateException("Failed to create PETSc/TAO bounded quadratic plan" +
                    (err != null ? ": " + err : ""));
            }
            return handle;
        } catch (Throwable t) {
            if (t instanceof RuntimeException) throw (RuntimeException) t;
            throw new RuntimeException(t);
        }
    }

    @Override
    public PetscTaoNativeResult solve(long handle, int dimension) {
        try (Arena arena = Arena.ofConfined()) {
            int totalSize = dimension + 4;
            MemorySegment outSeg = arena.allocate((long) totalSize * Double.BYTES);

            int status = (int) solveHandle.invokeExact(handle, outSeg, outSeg.byteSize());
            if (status != 0) {
                String err = getLastError();
                throw new IllegalStateException("PETSc/TAO solver failed with status code " + status +
                    (err != null ? ": " + err : ""));
            }

            double[] solution = new double[dimension];
            for (int i = 0; i < dimension; i++) {
                solution[i] = outSeg.getAtIndex(ValueLayout.JAVA_DOUBLE, i);
            }

            double objective = outSeg.getAtIndex(ValueLayout.JAVA_DOUBLE, dimension);
            double gradientNorm = outSeg.getAtIndex(ValueLayout.JAVA_DOUBLE, dimension + 1);
            int iterations = (int) outSeg.getAtIndex(ValueLayout.JAVA_DOUBLE, dimension + 2);
            int reasonCode = (int) outSeg.getAtIndex(ValueLayout.JAVA_DOUBLE, dimension + 3);

            return new PetscTaoNativeResult(solution, objective, gradientNorm, iterations, reasonCode);
        } catch (Throwable t) {
            if (t instanceof RuntimeException) throw (RuntimeException) t;
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
}
