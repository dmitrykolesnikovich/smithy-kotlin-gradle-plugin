/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0.
 */

package software.amazon.smithy.kotlin.codegen.rendering.serde

/**
 * Describes the target use case of a serde descriptor
 */
enum class SerdeSubject {
    DocumentSerializer,
    OperationSerializer,
    DocumentDeserializer,
    OperationDeserializer,
    ExceptionDeserializer;

    /**
     * [true] if the target use case is for serialization
     */
    val isSerializer: Boolean
        get() = when (this) {
            DocumentSerializer,
            OperationSerializer -> true
            else -> false
        }

    /**
     * [true] if the target use case is for deserialization
     */
    val isDeserializer: Boolean
        get() = !isSerializer
}