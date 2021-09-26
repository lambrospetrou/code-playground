package com.example

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.asServer
import org.sqlite.SQLiteConfig

fun makeApp(db: Database): HttpHandler = routes(
    "/ping" bind GET to {
        Response(OK).body("pong")
    },

    "/add" bind GET to {
        val name = (it.query("name") ?: "").trim()
        when {
            name.isEmpty() ->
                Response(BAD_REQUEST).body("Invalid 'name' given: $name")
            else -> {
                val userQueries = db.usersQueries
                try {
                    userQueries.transaction {
                        userQueries.addUser(name = name)
                    }
                    Response(OK).body("Welcome to our community $name!")
                } catch (e: Exception) {
                    Response(Status.INTERNAL_SERVER_ERROR).body("Sadly we failed to register you: $name")
                }
            }
        }
    },

    "/" bind GET to {
        val name = (it.query("name") ?: "").trim()
        when {
            name.isEmpty() ->
                Response(BAD_REQUEST).body("Invalid 'name' given: $name")
            else -> {
                val userQueries = db.usersQueries
                userQueries.selectByName(name = name).executeAsOneOrNull()?.let {
                    Response(OK).body("Boom! We found you: $name")
                } ?: Response(NOT_FOUND).body("Sadly we could not find you: $name")
            }
        }
    }
)

fun main() {
    val port = (System.getenv("PORT") ?: "9000").toInt()

    val config = SQLiteConfig().apply {
        setSharedCache(true)
        setJournalMode(SQLiteConfig.JournalMode.WAL)
    }
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:/tmp/users.sqlite3", properties = config.toProperties())
    Database.Schema.create(driver)

    val app: HttpHandler = makeApp(db = Database(driver))
    val server = app.asServer(ApacheServer(port)).start()

    println(Runtime.getRuntime().availableProcessors())
    println("Server started on " + server.port())
}
