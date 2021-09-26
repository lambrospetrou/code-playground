package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.asServer
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.transactionManager
import java.sql.Connection

object DbSettings {
    val db by lazy {
        Database.connect("jdbc:sqlite:/tmp/users.sqlite3", "org.sqlite.JDBC")
//        val db = Database.connect("jdbc:sqlite:/tmp/users.sqlite3", "org.sqlite.JDBC")
//        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
//        db
    }
}

object Users: IntIdTable() {
    val name = varchar("name", 100)
}

val app: HttpHandler = routes(
    "/ping" bind GET to {
        Response(OK).body("pong")
    },

    "/" bind GET to {
        val name = (it.query("name") ?: "").trim()

        when {
            name.isEmpty() ->
                Response(BAD_REQUEST).body("Invalid 'name' given: $name")
            else ->
                transaction {
                    SchemaUtils.create(Users)

                    val found = Users.select {
                        Users.name eq name
                    }.limit(1).count() > 0
                    when (found) {
                        true -> Response(OK).body("Boom! We found you: $name")
                        false -> Response(NOT_FOUND).body("Sadly we could not find you: $name")
                    }
                }
        }
    }
)

fun main() {
    val port = (System.getenv("PORT") ?: "9000").toInt()
    DbSettings.db.transactionManager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    val printingApp: HttpHandler = app
    val server = printingApp.asServer(ApacheServer(port)).start()

    println(Runtime.getRuntime().availableProcessors())

    println("Server started on " + server.port())
}
