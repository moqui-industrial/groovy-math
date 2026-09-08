/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.nativeutil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Enterprise Native Library Loader.
 * Follows industry best practices (RocksDB, Netty-tcnative, ONNX Runtime Java)
 * to automatically detect OS/arch and extract pre-packaged native libraries from JAR resources.
 */
public final class NativeLibraryLoader {

    private static final ConcurrentMap<String, Boolean> LOADED_LIBS = new ConcurrentHashMap<>();

    private NativeLibraryLoader() { }

    public static synchronized void loadLibrary(String libBaseName) {
        if (LOADED_LIBS.containsKey(libBaseName)) {
            return;
        }

        // 1. First attempt: standard System.loadLibrary (respects java.library.path)
        try {
            System.loadLibrary(libBaseName);
            LOADED_LIBS.put(libBaseName, true);
            return;
        } catch (UnsatisfiedLinkError e) {
            // Not found in system path, proceed to resource extraction
        }

        // 2. Second attempt: look in common build and runtime output directories
        String filename = mapLibraryName(libBaseName);
        String[] fallbackDirs = {
                "build/native",
                "build/native-jax",
                "build/native-openfoam",
                "runtime/lib",
                "lib"
        };
        for (String dir : fallbackDirs) {
            File local = new File(dir, filename);
            if (local.exists() && local.isFile()) {
                try {
                    System.load(local.getAbsolutePath());
                    LOADED_LIBS.put(libBaseName, true);
                    return;
                } catch (UnsatisfiedLinkError ignored) {
                }
            }
        }

        // 3. Third attempt: extract from classpath JAR resource /native/${os}-${arch}/
        String os = getOsPrefix();
        String arch = getArchPrefix();
        String resourcePath = "/native/" + os + "-" + arch + "/" + filename;

        try (InputStream in = NativeLibraryLoader.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new UnsatisfiedLinkError(String.format(
                        "Native library '%s' could not be loaded from system path or JAR resource '%s' (OS: %s, Arch: %s)",
                        libBaseName, resourcePath, os, arch));
            }

            File tempDir = new File(System.getProperty("java.io.tmpdir"), "groovy_math_native");
            if (!tempDir.exists()) tempDir.mkdirs();

            File targetFile = new File(tempDir, filename);
            Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            targetFile.deleteOnExit();

            System.load(targetFile.getAbsolutePath());
            LOADED_LIBS.put(libBaseName, true);
        } catch (IOException ioe) {
            throw new UnsatisfiedLinkError("Failed to extract native library resource " + resourcePath + ": " + ioe.getMessage());
        }
    }

    public static String mapLibraryName(String baseName) {
        String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (os.contains("win")) {
            return baseName + ".dll";
        } else if (os.contains("mac") || os.contains("darwin")) {
            return "lib" + baseName + ".dylib";
        } else {
            return "lib" + baseName + ".so";
        }
    }

    public static String getOsPrefix() {
        String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        if (os.contains("win")) return "windows";
        if (os.contains("mac") || os.contains("darwin")) return "osx";
        if (os.contains("linux")) return "linux";
        return "unknown";
    }

    public static String getArchPrefix() {
        String arch = System.getProperty("os.arch", "").toLowerCase(Locale.ROOT);
        if (arch.contains("x86_64") || arch.contains("amd64")) return "x86_64";
        if (arch.contains("aarch64") || arch.contains("arm64")) return "aarch64";
        return arch;
    }
}
