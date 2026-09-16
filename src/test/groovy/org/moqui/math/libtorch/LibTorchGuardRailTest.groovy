/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.libtorch

import org.moqui.math.dsl.MathDsl
import org.moqui.math.tensor.TensorContractException
import org.moqui.math.tensor.TensorDescriptor
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment

/**
 * Proves the guard-rail is on the real execution path, not merely unit-tested beside it.
 *
 * Every case here would, without the guard, hand LibTorch a pointer it reshapes against
 * dimensions the buffer cannot back: the C++ side calls from_blob(input, {batch, width})
 * and reads the full extent. The failure mode is a SIGSEGV that takes the JVM with it, so
 * the assertion that matters is not only the exception type but that the process is still
 * alive afterwards to run the next case.
 */
@Tag('libtorch-native')
class LibTorchGuardRailTest {

    @Test
    void refusesUndersizedInputSegmentInsteadOfSegfaulting() {
        LibTorchPanama torch = LibTorchPanama.INSTANCE
        long handle = torch.createPlan(3)
        try {
            torch.addRelu(handle, 0, 1)
            torch.seal(handle, 1, 3)
            assert torch.inputWidth(handle) == 3

            try (Arena arena = Arena.ofConfined()) {
                // A batch of 2 at width 3 needs 6 floats; give it 5.
                MemorySegment input = arena.allocate(5L * Float.BYTES)
                MemorySegment output = arena.allocate(2L * 3L * Float.BYTES)

                try {
                    torch.executeSegment(handle, input, 2, output)
                    assert false : 'Should have refused a 6-float read from a 5-float segment'
                } catch (TensorContractException e) {
                    assert e.message.contains('Undersized input segment')
                    assert e.message.contains('24 bytes')
                    assert e.message.contains('20 bytes')
                }
            }

            // Still alive: the guard returned control to the JVM rather than to the kernel.
            try (Arena arena = Arena.ofConfined()) {
                MemorySegment input = arena.allocate(6L * Float.BYTES)
                MemorySegment output = arena.allocate(6L * Float.BYTES)
                input.setAtIndex(java.lang.foreign.ValueLayout.JAVA_FLOAT, 0, 1.0f)
                input.setAtIndex(java.lang.foreign.ValueLayout.JAVA_FLOAT, 1, -2.0f)
                input.setAtIndex(java.lang.foreign.ValueLayout.JAVA_FLOAT, 2, 5.0f)
                input.setAtIndex(java.lang.foreign.ValueLayout.JAVA_FLOAT, 3, -1.0f)
                input.setAtIndex(java.lang.foreign.ValueLayout.JAVA_FLOAT, 4, 0.0f)
                input.setAtIndex(java.lang.foreign.ValueLayout.JAVA_FLOAT, 5, 7.0f)

                torch.executeSegment(handle, input, 2, output)

                float[] result = output.toArray(java.lang.foreign.ValueLayout.JAVA_FLOAT)
                assert result.toList() == [1.0f, 0.0f, 5.0f, 0.0f, 0.0f, 7.0f]
            }
        } finally {
            torch.destroy(handle)
        }
    }

    @Test
    void refusesUndersizedOutputSegmentInsteadOfCorruptingMemory() {
        LibTorchPanama torch = LibTorchPanama.INSTANCE
        long handle = torch.createPlan(3)
        try {
            torch.addRelu(handle, 0, 1)
            torch.seal(handle, 1, 3)

            try (Arena arena = Arena.ofConfined()) {
                MemorySegment input = arena.allocate(6L * Float.BYTES)
                MemorySegment output = arena.allocate(4L * Float.BYTES)  // needs 6

                try {
                    torch.executeSegment(handle, input, 2, output)
                    assert false : 'Should have refused a 6-float write into a 4-float segment'
                } catch (TensorContractException e) {
                    assert e.message.contains('Undersized output segment')
                }
            }
        } finally {
            torch.destroy(handle)
        }
    }

    @Test
    void forgetsPlanWidthOnceTheHandleIsDestroyed() {
        LibTorchPanama torch = LibTorchPanama.INSTANCE
        long handle = torch.createPlan(4)
        assert torch.inputWidth(handle) == 4
        torch.destroy(handle)
        // A stale handle is unknown, so its input side goes unchecked rather than being
        // validated against a width that no longer describes anything.
        assert torch.inputWidth(handle) == -1
    }

    @Test
    void serialisesStatefulExecutionOnASharedHandle() {
        LibTorchPanama torch = LibTorchPanama.INSTANCE
        long handle = torch.createPlan(3)
        try {
            torch.addRelu(handle, 0, 1)
            torch.seal(handle, 1, 3)

            // An inference plan carries no mutable state, so it stays lock-free.
            assert !torch.isStateful(handle)

            torch.setTraining(handle, true)
            assert torch.isStateful(handle)

            // Native run_plan move-assigns its slot map into Plan::last_slots in training mode.
            // Without exclusion these 64 concurrent calls race on that unordered_map; the point
            // of the assertion is that they all complete and agree, on a plan they share.
            java.util.concurrent.ExecutorService workers = java.util.concurrent.Executors.newFixedThreadPool(8)
            try {
                List<java.util.concurrent.Callable<List<Float>>> work = (0..<64).collect {
                    { ->
                        torch.execute(handle, [1.0f, -2.0f, 5.0f] as float[], 1).toList()
                    } as java.util.concurrent.Callable<List<Float>>
                }
                List<List<Float>> results = workers.invokeAll(work)*.get()
                assert results.every { it == [1.0f, 0.0f, 5.0f] }
            } finally {
                workers.shutdown()
            }

            torch.setTraining(handle, false)
            assert !torch.isStateful(handle)
        } finally {
            torch.destroy(handle)
        }
        assert !torch.isStateful(handle)
    }

    @Test
    void enforcesTheWholeDeclaredContractOnTheDescriptorPath() {
        // examples/matrix-product.groovy declares Matrix A as 2x3. The descriptor entry point is
        // the only one where every clause is checked together: rank, each axis, element type and
        // that the segment can actually hold what it claims.
        LibTorchProvider provider = new LibTorchProvider('MatrixProduct')
        LibTorchPlan plan = provider.compile(MathDsl.evaluate(
            new File(System.getProperty('user.dir'), 'examples/matrix-product.groovy')))
        try {
            assert plan.declaredDataType == TensorDescriptor.DTYPE_FLOAT32

            try (Arena arena = Arena.ofConfined()) {
                TensorDescriptor declared = TensorDescriptor.ofFloats(
                    arena, [1f, 2f, 3f, 4f, 5f, 6f] as float[], [2L, 3L] as long[])
                assert plan.execute(declared).values.toList() == [58f, 64f, 139f, 154f]

                // Right element count, wrong shape: 3x2 against a declared 2x3.
                TensorDescriptor transposed = TensorDescriptor.ofFloats(
                    arena, [1f, 2f, 3f, 4f, 5f, 6f] as float[], [3L, 2L] as long[])
                try {
                    plan.execute(transposed)
                    assert false : 'Should have refused a 3x2 against a declared 2x3'
                } catch (TensorContractException e) {
                    assert e.tensorId == 'A'
                }

                // Right shape, wrong element type.
                TensorDescriptor doubles = TensorDescriptor.ofDoubles(
                    arena, [1d, 2d, 3d, 4d, 5d, 6d] as double[], [2L, 3L] as long[])
                try {
                    plan.execute(doubles)
                    assert false : 'Should have refused float64 data for a float32 plan'
                } catch (TensorContractException e) {
                    assert e.message.contains('Element type does not match')
                }

                // Right shape and type, but the segment is two elements short of holding it.
                MemorySegment tooSmall = arena.allocate(4L * Float.BYTES)
                TensorDescriptor lying = new TensorDescriptor(tooSmall, TensorDescriptor.DTYPE_FLOAT32,
                    [2L, 3L] as long[], null, TensorDescriptor.DEVICE_CPU)
                try {
                    plan.execute(lying)
                    assert false : 'Should have refused a 6-element contract over a 4-element segment'
                } catch (TensorContractException e) {
                    assert e.message.contains('Underallocated')
                }
            }
        } finally {
            plan.close()
        }
    }

    @Test
    void refusesMatmulWhoseInnerDimensionsDisagree() {
        LibTorchPanama torch = LibTorchPanama.INSTANCE
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment a = arena.allocate(6L * Float.BYTES)    // 2x3
            MemorySegment b = arena.allocate(12L * Float.BYTES)   // 3x4
            MemorySegment out = arena.allocate(8L * Float.BYTES)  // 2x4

            try {
                torch.matmul(a, 2, 3, b, 4, 3, out)
                assert false : 'Should have refused a 3-vs-4 inner dimension'
            } catch (TensorContractException e) {
                assert e.message.contains('inner dimension mismatch')
            }

            // The well-formed product still goes through.
            torch.matmul(a, 2, 3, b, 3, 4, out)
        }
    }
}
