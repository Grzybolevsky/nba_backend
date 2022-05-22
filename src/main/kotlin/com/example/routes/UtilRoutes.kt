package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.utilRoutes() {
    routing {
        unauthorized()
    }
}

fun Route.unauthorized() {
    get("/unauthorized") {
        call.respond(HttpStatusCode.Unauthorized)
    }
}
