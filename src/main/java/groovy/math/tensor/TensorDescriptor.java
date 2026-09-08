/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package groovy.math.tensor;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.Arrays;
import java.util.Objects;

/**
 * Universal Off-Heap Tensor Descriptor for Zero-Copy Panama FFM Dispatch.
 * Represents arbitrary N-Dimensional tensors across LibTorch, JAX, and OpenCV runtimes.
 */
public final class TensorDescriptor {
    public static final int DTYPE_FLOAT32  = 0;
    public static final int DTYPE_FLOAT64  = 1;
    public static final int DTYPE_INT32    = 2;
    public static final int DTYPE_INT64    = 3;
    public static final int DTYPE_BOOL     = 4;
    public static final int DTYPE_BFLOAT16 = 5;

    public static final int DEVICE_CPU  = 0;
    public static final int DEVICE_CUDA = 1;
    public static final int DEVICE_MPS  = 2;
    public static final int DEVICE_TPU  = 3;

    private final MemorySegment segment;
    private final int dtype;
    private final int rank;
    private final long[] shape;
    private final long[] strides;
    private final int device;

    public TensorDescriptor(MemorySegment segment, int dtype, long[] shape, long[] strides, int device) {
        this.segment = Objects.requireNonNull(segment, "MemorySegment must not be null");
        this.dtype = dtype;
        this.shape = Arrays.copyOf(shape, shape.length);
        this.rank = shape.length;
        this.strides = strides != null ? Arrays.copyOf(strides, strides.length) : computeContiguousStrides(shape);
        this.device = device;
    }

    public static TensorDescriptor ofFloats(Arena arena, float[] data, long[] shape) {
        Objects.requireNonNull(arena, "Arena must not be null");
        Objects.requireNonNull(data, "Data array must not be null");
        MemorySegment seg = arena.allocate((long) data.length * Float.BYTES, ValueLayout.JAVA_FLOAT.byteAlignment());
        MemorySegment.copy(MemorySegment.ofArray(data), 0, seg, 0, (long) data.length * Float.BYTES);
        return new TensorDescriptor(seg, DTYPE_FLOAT32, shape, null, DEVICE_CPU);
    }

    public static TensorDescriptor ofDoubles(Arena arena, double[] data, long[] shape) {
        Objects.requireNonNull(arena, "Arena must not be null");
        Objects.requireNonNull(data, "Data array must not be null");
        MemorySegment seg = arena.allocate((long) data.length * Double.BYTES, ValueLayout.JAVA_DOUBLE.byteAlignment());
        MemorySegment.copy(MemorySegment.ofArray(data), 0, seg, 0, (long) data.length * Double.BYTES);
        return new TensorDescriptor(seg, DTYPE_FLOAT64, shape, null, DEVICE_CPU);
    }

    public static TensorDescriptor allocate(Arena arena, int dtype, long[] shape, int device) {
        Objects.requireNonNull(arena, "Arena must not be null");
        long totalElements = computeNumel(shape);
        long byteSize = totalElements * elementByteSize(dtype);
        MemorySegment seg = arena.allocate(byteSize, 8);
        return new TensorDescriptor(seg, dtype, shape, null, device);
    }

    public static long[] computeContiguousStrides(long[] shape) {
        long[] str = new long[shape.length];
        long acc = 1;
        for (int i = shape.length - 1; i >= 0; i--) {
            str[i] = acc;
            acc *= shape[i];
        }
        return str;
    }

    public static long computeNumel(long[] shape) {
        long total = 1;
        for (long dim : shape) total *= dim;
        return total;
    }

    public static int elementByteSize(int dtype) {
        return switch (dtype) {
            case DTYPE_FLOAT32, DTYPE_INT32 -> 4;
            case DTYPE_FLOAT64, DTYPE_INT64 -> 8;
            case DTYPE_BOOL -> 1;
            case DTYPE_BFLOAT16 -> 2;
            default -> throw new IllegalArgumentException("Unknown dtype: " + dtype);
        };
    }

    public MemorySegment segment() { return segment; }
    public long address() { return segment.address(); }
    public int dtype() { return dtype; }
    public int rank() { return rank; }
    public long[] shape() { return Arrays.copyOf(shape, shape.length); }
    public long[] strides() { return Arrays.copyOf(strides, strides.length); }
    public int device() { return device; }
    public long numel() { return computeNumel(shape); }
    public long byteSize() { return numel() * elementByteSize(dtype); }

    public float[] toFloatArray() {
        if (dtype != DTYPE_FLOAT32) throw new IllegalStateException("Tensor is not float32");
        return segment.toArray(ValueLayout.JAVA_FLOAT);
    }

    @Override
    public String toString() {
        return "TensorDescriptor[dtype=" + dtype + ", shape=" + Arrays.toString(shape) +
               ", device=" + device + ", numel=" + numel() + "]";
    }
}
