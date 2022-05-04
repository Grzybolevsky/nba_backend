package com.example.http

import io.ktor.server.application.*
import io.ktor.server.plugins.httpsredirect.*

fun Application.configureHTTP() {
    install(HttpsRedirect) {
        sslPort = 8080
        permanentRedirect = true
    }
}
