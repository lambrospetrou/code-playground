package com.example

import com.example.formats.kotlinXMessage
import com.example.formats.kotlinXMessageLens
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.asServer

val app: HttpHandler = routes(
    "/ping" bind GET to {
        Response(OK).body("pong")
    },

    "/formats/json/kotlinx" bind GET to {
        Response(OK).with(kotlinXMessageLens of kotlinXMessage)
    },

    "/testing/hamkrest" bind GET to {request ->
        Response(OK).body("Echo '${request.query("a")}'")
    }
)

fun main() {
    val printingApp: HttpHandler = app

    val port = (System.getenv("PORT") ?: "9000").toInt()
    val server = printingApp.asServer(ApacheServer(port)).start()

    println(Runtime.getRuntime().availableProcessors())

    println("Server started on " + server.port())
}
