/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package aws.smithy.kotlin.runtime.io

import io.ktor.utils.io.bits.*
import java.nio.ByteBuffer

internal fun SdkByteBuffer.hasArray() = memory.buffer.hasArray() && !memory.buffer.isReadOnly

public actual fun SdkByteBuffer.bytes(): ByteArray = when (hasArray()) {
    true -> memory.buffer.array().sliceArray(readPosition.toInt() until readRemaining.toInt())
    false -> ByteArray(readRemaining.toInt()).apply { readFully(this) }
}

internal actual fun Memory.Companion.ofByteArray(src: ByteArray, offset: Int, length: Int): Memory =
    Memory.of(src, offset, length)

/**
 * Create a new SdkBuffer using the given [ByteBuffer] as the contents
 */
public fun SdkByteBuffer.Companion.of(byteBuffer: ByteBuffer): SdkByteBuffer = SdkByteBuffer(Memory.of(byteBuffer))

/**
 * Read the buffer's content to the [dst] buffer moving its position.
 */
public fun SdkByteBuffer.readFully(dst: ByteBuffer) {
    val length = dst.remaining().toLong()
    read { memory, readStart, _ ->
        memory.copyTo(dst, readStart)
        length
    }
}

/**
 * Read as much from this buffer as possible to [dst] buffer moving its position
 */
public fun SdkByteBuffer.readAvailable(dst: ByteBuffer) {
    val wc = minOf(readRemaining.toInt(), dst.remaining())
    if (wc == 0) return
    val dstCopy = dst.duplicate().apply {
        limit(position() + wc)
    }
    readFully(dstCopy)
    dst.position(dst.position() + wc)
}
