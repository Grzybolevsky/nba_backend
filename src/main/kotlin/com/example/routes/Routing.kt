package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureRouting() {
    playerRoutes()
    teamRoutes()
    gameRoutes()
    favoriteRoutes()
    utilRoutes()

    install(CORS) {
        anyHost()
    }
}
