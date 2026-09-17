/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 */

package org.moqui.math.memory

import groovy.transform.CompileStatic

import java.lang.foreign.Arena
import java.lang.foreign.MemorySegment
import java.lang.foreign.ValueLayout
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * High-performance, zero-copy NumPy (.npy) file format reader and writer.
 * Parses the NumPy binary header and memory-maps the contiguous data payload directly off-heap
 * using Java Panama FileChannel and MemorySegment.
 */
@CompileStatic
class NpyReader {

    private static final byte[] NPY_MAGIC = [(byte) 0x93, (byte) 0x4E, (byte) 0x55, (byte) 0x4D, (byte) 0x50, (byte) 0x59] as byte[]
    private static final Pattern DESCR_PATTERN = Pattern.compile("'descr'\\s*:\\s*'([^']+)'")
    private static final Pattern FORTRAN_PATTERN = Pattern.compile("'fortran_order'\\s*:\\s*(True|False)")
    private static final Pattern SHAPE_PATTERN = Pattern.compile("'shape'\\s*:\\s*\\(([^)]*)\\)")

    static class NpyHeader {
        int majorVersion
        int minorVersion
        String dtype
        boolean fortranOrder
        List<Long> shape
        long dataOffset
        long dataSizeBytes
        long totalElements
    }

    /**
     * Parses the NPY header from an external file without loading the payload into the JVM heap.
     */
    static NpyHeader parseHeader(Path path) throws IOException {
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer magicBuf = ByteBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN)
            channel.read(magicBuf)
            magicBuf.flip()

            for (int i = 0; i < 6; i++) {
                if (magicBuf.get() != NPY_MAGIC[i]) {
                    throw new IllegalArgumentException("File ${path} is not a valid NumPy .npy binary file (bad magic bytes)")
                }
            }

            int major = magicBuf.get() & 0xFF
            int minor = magicBuf.get() & 0xFF
            int headerLen
            long dataOffset

            if (major == 1) {
                headerLen = magicBuf.getShort() & 0xFFFF
                dataOffset = 10L + headerLen
            } else if (major == 2) {
                ByteBuffer lenBuf = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)
                channel.read(lenBuf)
                lenBuf.flip()
                headerLen = lenBuf.getInt()
                dataOffset = 12L + headerLen
            } else {
                throw new UnsupportedOperationException("Unsupported NPY version: ${major}.${minor}")
            }

            ByteBuffer headerBuf = ByteBuffer.allocate(headerLen)
            channel.read(headerBuf)
            headerBuf.flip()
            String headerText = new String(headerBuf.array(), StandardCharsets.US_ASCII)

            NpyHeader header = new NpyHeader()
            header.majorVersion = major
            header.minorVersion = minor
            header.dataOffset = dataOffset
            header.dataSizeBytes = channel.size() - dataOffset

            Matcher descrMatcher = DESCR_PATTERN.matcher(headerText)
            if (descrMatcher.find()) header.dtype = descrMatcher.group(1)

            Matcher fortranMatcher = FORTRAN_PATTERN.matcher(headerText)
            if (fortranMatcher.find()) header.fortranOrder = Boolean.parseBoolean(fortranMatcher.group(1))

            Matcher shapeMatcher = SHAPE_PATTERN.matcher(headerText)
            List<Long> dims = []
            if (shapeMatcher.find()) {
                String dimsStr = shapeMatcher.group(1).trim()
                if (!dimsStr.isEmpty()) {
                    String[] tokens = dimsStr.split(',')
                    for (String token : tokens) {
                        String t = token.trim()
                        if (!t.isEmpty()) {
                            dims.add(Long.parseLong(t))
                        }
                    }
                }
            }
            header.shape = dims
            long count = 1L
            for (Long d : dims) count *= d
            header.totalElements = count

            return header
        }
    }

    /**
     * Memory-maps the data payload of a NumPy (.npy) file directly off-heap in zero-copy mode.
     */
    static MemorySegment map(Arena arena, Path path) throws IOException {
        NpyHeader header = parseHeader(path)
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            return channel.map(FileChannel.MapMode.READ_ONLY, header.dataOffset, header.dataSizeBytes, arena)
        }
    }

    /**
     * Helper to write a float array to an official NumPy .npy file.
     */
    static void writeFloatArray(Path path, List<Long> shape, float[] data) throws IOException {
        StringBuilder shapeBuilder = new StringBuilder("(")
        for (int i = 0; i < shape.size(); i++) {
            shapeBuilder.append(shape.get(i))
            if (shape.size() == 1 || i < shape.size() - 1) {
                shapeBuilder.append(", ")
            }
        }
        shapeBuilder.append(")")

        String dictStr = "{'descr': '<f4', 'fortran_order': False, 'shape': " + shapeBuilder.toString() + ", }"
        int prefixLen = 10
        int unpaddedLen = prefixLen + dictStr.length() + 1
        int pad = (64 - (unpaddedLen % 64)) % 64

        StringBuilder padded = new StringBuilder(dictStr)
        for (int i = 0; i < pad; i++) padded.append(' ')
        padded.append('\n')

        byte[] headerBytes = padded.toString().getBytes(StandardCharsets.US_ASCII)

        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            ByteBuffer preBuf = ByteBuffer.allocate(10 + headerBytes.length).order(ByteOrder.LITTLE_ENDIAN)
            preBuf.put(NPY_MAGIC)
            preBuf.put((byte) 1)
            preBuf.put((byte) 0)
            preBuf.putShort((short) headerBytes.length)
            preBuf.put(headerBytes)
            preBuf.flip()
            channel.write(preBuf)

            ByteBuffer dataBuf = ByteBuffer.allocate(data.length * 4).order(ByteOrder.LITTLE_ENDIAN)
            for (float f : data) {
                dataBuf.putFloat(f)
            }
            dataBuf.flip()
            channel.write(dataBuf)
        }
    }
}
