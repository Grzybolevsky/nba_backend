package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object RoutingUtils {
    const val UNAUTHORIZED = "/unauthorized"
    const val LOGIN = "/login"
    const val MISSING_ID = "Missing id"
}

fun Application.utilRoutes() {
    routing {
        unauthorized()
    }
}

fun Route.unauthorized() {
    get("/api/unauthorized") {
        call.respond(HttpStatusCode.Unauthorized)
    }
}
