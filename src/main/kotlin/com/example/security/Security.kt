package com.example.security

import com.example.routes.RoutingUtils.API_URL
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {
    configureSessions()
    configureAuthentication()
    routing {
        route(API_URL) {
            configureSecurityRoutes()
        }
    }
    configureCORS()
}
