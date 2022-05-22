package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.simpleRoutes() {
    routing {
        simpleRoute()
    }
}

fun Route.simpleRoute() {
    get("/") {
        call.respondText("Hello World")
    }
}
