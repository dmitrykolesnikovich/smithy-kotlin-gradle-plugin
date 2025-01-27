/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package aws.smithy.kotlin.runtime.io

/**
 * A buffer that supports read operations. The bytes are stored in memory but the underlying storage may or may
 * not be contiguous.
 */
public interface Buffer {

    /**
     * The number of bytes between the current position and the end of the buffer
     */
    public val readRemaining: ULong

    /**
     * Advance the internal read position discarding up to [count] readable bytes
     * @return the number of bytes discarded
     */
    public fun discard(count: ULong): ULong

    /**
     * Read a single byte from the buffer
     */
    public fun readByte(): Byte

    /**
     * Read a signed 16-bit integer in big-endian byte order
     */
    public fun readShort(): Short

    /**
     * Read an unsigned 16-bit integer in big-endian byte order
     */
    public fun readUShort(): UShort

    /**
     * Read a signed 32-bit integer in big-endian byte order
     */
    public fun readInt(): Int

    /**
     * Read an unsigned 32-bit integer in big-endian byte order
     */
    public fun readUInt(): UInt

    /**
     * Read a signed 64-bit integer in big-endian byte order
     */
    public fun readLong(): Long

    /**
     * Read an unsigned 64-bit integer in big-endian byte order
     */
    public fun readULong(): ULong

    /**
     * Read a 32-bit float in big-endian byte order
     */
    public fun readFloat(): Float

    /**
     * Read a 64-bit double in big-endian byte order
     */
    public fun readDouble(): Double

    /**
     * Read from this buffer exactly [length] bytes and write to [dest] starting at [offset]
     * @throws IllegalArgumentException if there are not enough bytes available for read or the offset/length combination is invalid
     */
    public fun readFully(dest: ByteArray, offset: Int = 0, length: Int = dest.size - offset)

    /**
     * Read all available bytes from this buffer into [dest] starting at [offset] up to the destination buffers size.
     * If the total bytes available is less than [length] then as many bytes as are available will be read.
     * The total bytes read is returned or `-1` if no data is available.
     */
    public fun readAvailable(dest: ByteArray, offset: Int = 0, length: Int = dest.size - offset): Int
}

/**
 * Reads at most [length] bytes from this buffer into the [dst] buffer
 * @return the number of bytes read
 */
public fun Buffer.readFully(dst: SdkByteBuffer, length: ULong = dst.writeRemaining): ULong {
    if (this is SdkByteBuffer) return this.readFully(dst, length)
    TODO("Buffer.readFully fallback not implemented for ${this::class}")
}

/**
 * Reads at most [length] bytes from this buffer or `-1` if no bytes are available for read.
 * @return the number of bytes read or null if the buffer is empty
 */
public fun Buffer.readAvailable(dst: SdkByteBuffer, length: ULong = dst.writeRemaining): ULong? {
    if (this is SdkByteBuffer) return this.readAvailable(dst, length)
    TODO("Buffer.readAvailable fallback not implemented for ${this::class}")
}
