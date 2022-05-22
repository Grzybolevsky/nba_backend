package com.example

import com.example.dao.DatabaseFactory
import com.example.routes.configureRouting
import com.example.security.configureSecurity
import com.example.serialization.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureRouting()
        configureSecurity()
        configureSerialization()
        DatabaseFactory.connect()
    }.start(wait = true)
}
