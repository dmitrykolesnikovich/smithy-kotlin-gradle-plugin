/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0.
 */
package aws.smithy.kotlin.runtime.http.request

import aws.smithy.kotlin.runtime.http.*

/**
 * Used to construct an HTTP request
 */
class HttpRequestBuilder {
    /**
     * The HTTP method (verb) to use when making the request
     */
    var method: HttpMethod = HttpMethod.GET

    /**
     * Endpoint to make request to
     */
    val url: UrlBuilder = UrlBuilder()

    /**
     * HTTP headers
     */
    val headers: HeadersBuilder = HeadersBuilder()

    /**
     * Outgoing payload. Initially empty
     */
    var body: HttpBody = HttpBody.Empty

    fun build(): HttpRequest = HttpRequest(method, url.build(), if (headers.isEmpty()) Headers.Empty else headers.build(), body)

    override fun toString(): String = buildString {
        append("HttpRequestBuilder(method=$method, url=$url, headers=$headers, body=$body)")
    }
}

// convenience extensions

/**
 * Modify the URL inside the block
 */
fun HttpRequestBuilder.url(block: UrlBuilder.() -> Unit) = url.apply(block)

/**
 * Modify the headers inside the given block
 */
fun HttpRequestBuilder.headers(block: HeadersBuilder.() -> Unit) = headers.apply(block)

/**
 * Add a single header. This will append to any existing headers with the same name.
 */
fun HttpRequestBuilder.header(name: String, value: String) = headers.append(name, value)