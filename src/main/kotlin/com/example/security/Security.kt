package com.example.security

import io.ktor.server.application.*

fun Application.configureSecurity() {
    configureSessions()
    configureAuthentication()
    configureSecurityRoutes()
}
