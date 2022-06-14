package com.example.routes

import com.example.routes.RoutingUtils.API_URL
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route(API_URL) {
            playerRoutes()
            teamRoutes()
            favoriteRoutes()
        }
    }
}
