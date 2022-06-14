package com.example.security

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {
    configureSessions()
    configureAuthentication()
    routing {
        route("/api") {
            configureSecurityRoutes()
        }
    }
    configureCORS()
}
