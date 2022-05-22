package com.example.routes

import com.example.services.GameService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val service = GameService

fun Application.gameRoutes() {
    routing {
        allGamesRoute()
    }
}

fun Route.allGamesRoute() {
    get("/games") {
        call.respond(service.getAllGames())
    }
}
