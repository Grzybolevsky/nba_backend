package com.example

import com.example.dao.DatabaseFactory
import com.example.routes.configureRouting
import com.example.security.configureSecurity
import com.example.serialization.configureSerialization
import com.example.services.balldontile.BalldontileInfoService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val runMigration: String = System.getenv("MIGRATION") ?: "false"
    embeddedServer(Netty, port = 8080, host = "localhost", watchPaths = listOf("classes")) {
        if (runMigration.toBoolean()) {
            log.info("Running DB migration")
            DatabaseFactory.init()
            BalldontileInfoService.fetchData()
            log.info("Migration completed")
        }
        configureRouting()
        configureSecurity()
        configureSerialization()
        DatabaseFactory.connect()
    }.start(wait = true)
}
