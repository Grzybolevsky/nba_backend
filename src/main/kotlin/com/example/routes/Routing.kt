package com.example.routes

import io.ktor.server.application.*

fun Application.configureRouting() {
    playerRoutes()
    teamRoutes()
    gameRoutes()
    favoriteRoutes()
    utilRoutes()
}
