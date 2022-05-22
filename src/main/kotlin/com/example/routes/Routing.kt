package com.example.routes

import io.ktor.server.application.*

fun Application.configureRouting() {
    simpleRoutes()
    playerRoutes()
    teamRoutes()
    gameRoutes()
    favoriteRoutes()
}
