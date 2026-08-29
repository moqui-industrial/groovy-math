/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.memory

import groovy.transform.CompileStatic
import groovy.math.libtorch.LibTorchPanama
import groovy.math.model.Matrix
import groovy.math.model.MatrixComponent
import groovy.math.model.Tensor
import groovy.math.model.TensorContent
import groovy.math.model.TensorElement
import groovy.math.model.Vector
import groovy.math.model.VectorComponent

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.nio.channels.FileChannel
import java.nio.file.Path
import java.nio.file.StandardOpenOption

/**
 * High-performance Native Memory Mapper utilizing Project Panama (Java 21).
 * Maps Groovy domain entities (Tensor, Matrix, Vector, TensorContent) to off-heap MemorySegments
 * supporting discrete elements (physics/mechanics), inline arrays/blobs, and memory-mapped external files (ONNX/Safetensors).
 */
@CompileStatic
class NativeMemoryMapper {

    /**
     * Maps a Matrix and its discrete components (Tier 1) to an off-heap row-major float MemorySegment.
     */
    static MemorySegment mapMatrix(Arena arena, Matrix matrix, List<MatrixComponent> components = null) {
        int rows = (matrix.rows ?: 0L).intValue()
        int cols = (matrix.cols ?: 0L).intValue()
        int totalElements = rows * cols
        if (totalElements <= 0) throw new IllegalArgumentException("Matrix dimensions must be positive: ${rows}x${cols}")

        MemorySegment segment = LibTorchPanama.allocateFloatBuffer(arena, (long) totalElements)
        List<MatrixComponent> comps = components ?: matrix.components

        if (comps != null) {
            for (MatrixComponent comp : comps) {
                int r = (comp.rowIndex ?: 0L).intValue()
                int c = (comp.colIndex ?: 0L).intValue()
                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    long index = (long) r * cols + c
                    float val = (comp.realValue ?: 0.0).floatValue()
                    segment.setAtIndex(ValueLayout.JAVA_FLOAT, index, val)
                }
            }
        }
        segment
    }

    /**
     * Maps a Vector and its discrete components (Tier 1) to an off-heap float MemorySegment.
     */
    static MemorySegment mapVector(Arena arena, Vector vector, List<VectorComponent> components = null) {
        int size = (vector.dimension ?: 0L).intValue()
        if (size <= 0) throw new IllegalArgumentException("Vector dimension must be positive: ${size}")

        MemorySegment segment = LibTorchPanama.allocateFloatBuffer(arena, (long) size)
        List<VectorComponent> comps = components ?: vector.components

        if (comps != null) {
            for (VectorComponent comp : comps) {
                int idx = (comp.dimensionIndex ?: 0L).intValue()
                if (idx >= 0 && idx < size) {
                    float val = (comp.realValue ?: 0.0).floatValue()
                    segment.setAtIndex(ValueLayout.JAVA_FLOAT, (long) idx, val)
                }
            }
        }
        segment
    }

    /**
     * Maps a Tensor across all tiers:
     * - Tier 1: Discrete TensorElements (mechanics, small discrete physics tensors).
     * - Tier 2: Inline elementBlob or elementArray.
     * - Tier 3: External large files via TensorContent (memory-mapped ONNX/Safetensors/binary blobs).
     */
    static MemorySegment mapTensor(Arena arena, Tensor tensor, List<TensorElement> elements = null, TensorContent content = null) {
        long totalElements = tensor.size ?: parseShapeTotal(tensor.shape)
        if (totalElements <= 0L) totalElements = 1L

        // Tier 3: External Large File / ONNX via TensorContent
        if (content?.contentLocation) {
            File externalFile = new File(content.contentLocation)
            if (externalFile.exists()) {
                return mapExternalFile(arena, externalFile.toPath())
            }
        }

        // Tier 2: Inline elementBlob
        if (tensor.elementBlob != null && tensor.elementBlob.length > 0) {
            MemorySegment segment = arena.allocate((long) tensor.elementBlob.length)
            MemorySegment.copy(MemorySegment.ofArray(tensor.elementBlob), 0L, segment, 0L, (long) tensor.elementBlob.length)
            return segment
        }

        // Tier 2: Inline elementArray (comma-separated float string)
        if (tensor.elementArray != null && !tensor.elementArray.trim().isEmpty()) {
            String[] tokens = tensor.elementArray.split('[,\\s]+')
            MemorySegment segment = LibTorchPanama.allocateFloatBuffer(arena, (long) tokens.length)
            for (int i = 0; i < tokens.length; i++) {
                if (!tokens[i].isEmpty()) {
                    segment.setAtIndex(ValueLayout.JAVA_FLOAT, (long) i, Float.parseFloat(tokens[i]))
                }
            }
            return segment
        }

        // Tier 1: Discrete TensorElements (Physics / Mechanics)
        MemorySegment segment = LibTorchPanama.allocateFloatBuffer(arena, totalElements)
        List<TensorElement> elemList = elements ?: tensor.elements
        if (elemList != null) {
            for (TensorElement elem : elemList) {
                long idx = (elem.linearIndex ?: 0L).longValue()
                if (idx >= 0L && idx < totalElements) {
                    float val = (elem.realValue ?: 0.0).floatValue()
                    segment.setAtIndex(ValueLayout.JAVA_FLOAT, idx, val)
                }
            }
        }
        segment
    }

    /**
     * Memory-maps a large external binary file (ONNX weights / Safetensors / Raw tensor dump)
     * off-heap with zero-copy using FileChannel and Panama Arena.
     */
    static MemorySegment mapExternalFile(Arena arena, Path filePath) {
        try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            return channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size(), arena)
        }
    }

    /**
     * Converts a native float MemorySegment back into a Groovy Matrix entity with components.
     */
    static Matrix unmapMatrix(MemorySegment segment, String matrixId, int rows, int cols) {
        Matrix matrix = new Matrix(matrixId: matrixId, rows: (long) rows, cols: (long) cols)
        List<MatrixComponent> components = []
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                long idx = (long) r * cols + c
                float val = segment.getAtIndex(ValueLayout.JAVA_FLOAT, idx)
                components.add(new MatrixComponent(
                    matrixComponentId: "${matrixId}_${r}_${c}".toString(),
                    matrixId: matrixId,
                    rowIndex: (long) r,
                    colIndex: (long) c,
                    realValue: BigDecimal.valueOf(val)
                ))
            }
        }
        matrix.components = components
        matrix
    }

    /**
     * Converts a native float MemorySegment back into a Groovy Tensor entity with elements.
     */
    static Tensor unmapTensor(MemorySegment segment, String tensorId, List<Long> shape) {
        long total = 1L
        for (Long dim : shape) total *= dim
        String shapeStr = shape.join(',')

        Tensor tensor = new Tensor(
            tensorId: tensorId,
            rank: (long) shape.size(),
            shape: shapeStr,
            size: total
        )

        List<TensorElement> elements = []
        for (long i = 0; i < total; i++) {
            float val = segment.getAtIndex(ValueLayout.JAVA_FLOAT, i)
            elements.add(new TensorElement(
                tensorElementId: "${tensorId}_${i}".toString(),
                tensorId: tensorId,
                linearIndex: i,
                realValue: BigDecimal.valueOf(val)
            ))
        }
        tensor.elements = elements
        tensor
    }

    private static long parseShapeTotal(String shapeStr) {
        if (!shapeStr) return 0L
        long total = 1L
        String[] dims = shapeStr.replaceAll('[\\[\\]\\s]', '').split(',')
        for (String dim : dims) {
            if (!dim.isEmpty()) total *= Long.parseLong(dim)
        }
        total
    }
}
