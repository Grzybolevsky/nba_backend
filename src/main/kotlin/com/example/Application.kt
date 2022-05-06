package com.example

import com.example.dao.DatabaseFactory
import com.example.routes.configureRouting
import com.example.security.configureSecurity
import com.example.serialization.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.services.BalldontileInfoService.Companion.downloadData
import kotlinx.coroutines.launch

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        DatabaseFactory.init()
        configureRouting()
        configureSecurity()
        configureSerialization()
        launch {
            downloadData()
        }
    }.start(wait = true)
}
