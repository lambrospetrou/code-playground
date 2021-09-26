package com.example

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Body
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasHeader
import org.http4k.hamkrest.hasQuery
import org.http4k.hamkrest.hasStatus
import org.http4k.lens.string
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HelloWorldTest {

    @Test
    fun `Ping test`() {
        assertEquals(app(Request(GET, "/ping")), Response(OK).body("pong"))
    }
    @Test
    fun `Check Hamkrest matcher for http4k work as expected`() {
        val request = Request(GET, "/testing/hamkrest?a=b").body("http4k is cool").header("my header", "a value")
    
        val response = app(request)
    
        // response assertions
        assertThat(response, hasStatus(OK))
        assertThat(response, hasBody("Echo 'http4k is cool'"))
    
    
        // other assertions
        // query
        assertThat(request, hasQuery("a", "b"))
    
        // header
        assertThat(request, hasHeader("my header", "a value"))
    
        // body
        assertThat(request, hasBody("http4k is cool"))
        assertThat(request, hasBody(Body.string(TEXT_HTML).toLens(), equalTo("http4k is cool")))
    
        // composite
        assertThat(request, hasBody("http4k is cool").and(hasQuery("a", "b")))
    }

}
