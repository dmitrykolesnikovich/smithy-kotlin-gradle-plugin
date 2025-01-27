/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
package aws.smithy.kotlin.runtime.http.util

import aws.smithy.kotlin.runtime.http.QueryParameters
import aws.smithy.kotlin.runtime.util.text.urlEncodeComponent
import kotlin.test.*

class TextTest {

    @Test
    fun splitQueryStringIntoParts() {
        val query = "foo=baz&bar=quux&foo=qux&a="
        val actual = query.splitAsQueryParameters()
        val expected = QueryParameters {
            append("foo", "baz")
            append("foo", "qux")
            append("bar", "quux")
            append("a", "")
        }

        expected.entries().forEach { entry ->
            entry.value.forEach { value ->
                assertTrue(actual.contains(entry.key, value), "parsed query does not contain ${entry.key}:$value")
            }
        }

        val queryNoEquals = "abc=cde&noequalssign"
        val actualNoEquals = queryNoEquals.splitAsQueryParameters()
        val expectedNoEquals = QueryParameters {
            append("abc", "cde")
            append("noequalssign", "")
        }
        expectedNoEquals.entries().forEach { entry ->
            entry.value.forEach { value ->
                assertTrue(actualNoEquals.contains(entry.key, value), "parsed query does not contain ${entry.key}:$value")
            }
        }
    }

    @Test
    fun testFullUriToQueryParameters() {
        val uri = "http://foo.com?a=apple&b=banana&c=cherry"
        val params = uri.fullUriToQueryParameters()
        assertNotNull(params)
        assertEquals("apple", params["a"])
        assertEquals("banana", params["b"])
        assertEquals("cherry", params["c"])
    }

    @Test
    fun testFullUriToQueryParameters_withFragment() {
        val uri = "http://foo.com?a=apple&b=banana&c=cherry#d=durian"
        val params = uri.fullUriToQueryParameters()
        assertNotNull(params)
        assertEquals("apple", params["a"])
        assertEquals("banana", params["b"])
        assertEquals("cherry", params["c"])
        assertFalse("d" in params)
    }

    @Test
    fun testFullUriToQueryParameters_emptyQueryString() {
        val uri = "http://foo.com?"
        val params = uri.fullUriToQueryParameters()
        assertNull(params)
    }

    @Test
    fun testFullUriToQueryParameters_noQueryString() {
        val uri = "http://foo.com"
        val params = uri.fullUriToQueryParameters()
        assertNull(params)
    }

    @Test
    fun encodeLabels() {
        assertEquals("a%2Fb", "a/b".encodeLabel())
        assertEquals("a/b", "a/b".encodeLabel(greedy = true))
    }

    @Test
    fun encodeReservedChars() {
        // verify that both httpLabel and httpQuery bound components encode characters from the reserved
        // set of characters in section 2.2
        val input = ":/?#[]@!$&'()*+,;=% "
        val expected = "%3A%2F%3F%23%5B%5D%40%21%24%26%27%28%29%2A%2B%2C%3B%3D%25%20"
        assertEquals(expected, input.encodeLabel())
        assertEquals(expected, input.urlEncodeComponent())
    }

    @Test
    fun encodesPercent() {
        // verify that something that looks percent encoded actually encodes the percent. label/query should always
        // be going from raw -> encoded. Users should not be percent-encoding values passed to the runtime
        val input = "%25"
        val expected = "%2525"
        assertEquals(expected, input.encodeLabel())
        assertEquals(expected, input.urlEncodeComponent())
    }
}
