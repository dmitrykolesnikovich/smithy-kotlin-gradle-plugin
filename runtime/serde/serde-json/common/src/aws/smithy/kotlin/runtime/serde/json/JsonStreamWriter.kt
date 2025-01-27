/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
package aws.smithy.kotlin.runtime.serde.json

import aws.smithy.kotlin.runtime.util.InternalApi

/**
 * Interface for serialization. Specific formats should implement this interface according to their
 * own requirements. Currently only aws.smithy.kotlin.runtime.serde.json.JsonSerializer implements this interface.
 */
public interface JsonStreamWriter {

    /**
     * Begins encoding a new array. Each call to this method must be paired with
     * a call to {@link #endArray}.
     */
    public fun beginArray()

    /**
     * Ends encoding the current array.
     */
    public fun endArray()

    /**
     * Encodes {@code null}.
     */
    public fun writeNull()

    /**
     * Begins encoding a new object. Each call to this method must be paired
     * with a call to {@link #endObject}.
     */
    public fun beginObject()

    /**
     * Ends encoding the current object.
     */
    public fun endObject()

    /**
     * Encodes the property name.
     *
     * @param name the name of the forthcoming value. May not be null.
     */
    public fun writeName(name: String)

    /**
     * Encodes {@code value}.
     *
     * @param value the literal string value, or null to encode a null literal.
     */
    public fun writeValue(value: String)

    /**
     * Encodes {@code value}.
     */
    public fun writeValue(bool: Boolean)

    /**
     * Encodes {@code value}.
     */
    public fun writeValue(value: Number)

    /**
     * Encodes {@code value}.
     */
    public fun writeValue(value: Long)

    /**
     * Encodes {@code value}.
     *
     * @param value a finite value. May not be {@link Double#isNaN() NaNs} or
     *     {@link Double#isInfinite() infinities}.
     */
    public fun writeValue(value: Double)

    /**
     * Encodes {@code value}.
     */
    public fun writeValue(value: Float)

    /**
     * Encodes {@code value}.
     */
    public fun writeValue(value: Short)

    /**
     * Encodes {@code value}.
     */
    public fun writeValue(value: Int)

    /**
     * Encodes {@code value}.
     */
    public fun writeValue(value: Byte)

    /**
     * Appends the contents of [value] *without* any additional formatting or escaping. Use with caution
     */
    public fun writeRawValue(value: String)

    /**
     * Json content will be constructed in this UTF-8 encoded byte array.
     */
    public val bytes: ByteArray?
}

/**
 * Creates a [JsonStreamWriter] instance to write JSON
 */
@InternalApi
public fun jsonStreamWriter(pretty: Boolean = false): JsonStreamWriter = JsonEncoder(pretty)
