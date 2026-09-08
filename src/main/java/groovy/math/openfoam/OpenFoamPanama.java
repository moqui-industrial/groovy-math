/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.openfoam;

import java.io.File;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public final class OpenFoamPanama {
    public static final OpenFoamPanama INSTANCE = new OpenFoamPanama();

    private final Linker linker;
    private final SymbolLookup symbols;
    private final boolean available;

    private final MethodHandle runSolverHandle;
    private final MethodHandle getFieldHandle;

    private OpenFoamPanama() {
        this.linker = Linker.nativeLinker();
        SymbolLookup loaded = null;
        boolean isAvail = false;
        try {
            loaded = loadSymbols();
            isAvail = loaded.find("openfoam_panama_run_solver").isPresent();
        } catch (Throwable ignored) {
            isAvail = false;
        }
        this.symbols = loaded;
        this.available = isAvail;

        if (available) {
            this.runSolverHandle = find("openfoam_panama_run_solver",
                FunctionDescriptor.of(ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS, ValueLayout.ADDRESS,
                    ValueLayout.JAVA_DOUBLE, ValueLayout.JAVA_DOUBLE,
                    ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
            this.getFieldHandle = findOptional("openfoam_panama_get_field",
                FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS));
        } else {
            this.runSolverHandle = null;
            this.getFieldHandle = null;
        }
    }

    public boolean isAvailable() {
        return available;
    }

    private MethodHandle find(String name, FunctionDescriptor descriptor) {
        return symbols.find(name)
            .map(addr -> linker.downcallHandle(addr, descriptor))
            .orElseThrow(() -> new UnsatisfiedLinkError("Missing OpenFOAM Panama symbol: " + name));
    }

    private MethodHandle findOptional(String name, FunctionDescriptor descriptor) {
        return symbols.find(name)
            .map(addr -> linker.downcallHandle(addr, descriptor))
            .orElse(null);
    }

    private static SymbolLookup loadSymbols() {
        String explicitPath = System.getProperty("groovy.math.openfoam.library");
        if (explicitPath != null && new File(explicitPath).exists()) {
            return SymbolLookup.libraryLookup(new File(explicitPath).toPath(), Arena.global());
        }
        File defaultBuildLib = new File("build/native-openfoam/libgroovy_math_openfoam.so");
        if (defaultBuildLib.exists()) {
            return SymbolLookup.libraryLookup(defaultBuildLib.toPath(), Arena.global());
        }
        try {
            groovy.math.nativeutil.NativeLibraryLoader.loadLibrary("groovy_math_openfoam");
        } catch (Throwable ignored) {
        }
        return SymbolLookup.loaderLookup();
    }

    public int runSolver(String caseDir, String solverName, double nu, double deltaT, int nx, int ny, int nz) {
        if (!available || runSolverHandle == null) {
            throw new UnsupportedOperationException("OpenFOAM Panama native library is not available");
        }
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment caseDirSeg = arena.allocateUtf8String(caseDir);
            MemorySegment solverSeg = arena.allocateUtf8String(solverName);
            return (int) runSolverHandle.invokeExact(caseDirSeg, solverSeg, nu, deltaT, nx, ny, nz);
        } catch (Throwable t) {
            throw new RuntimeException("Failed to run OpenFOAM solver via Panama", t);
        }
    }
}
