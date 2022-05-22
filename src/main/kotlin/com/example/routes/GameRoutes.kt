package com.example.routes

import com.example.services.GameService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val service = GameService

fun Application.gameRoutes() {
    routing {
        allGamesRoute()
        singleGameRoute()
    }
}

fun Route.allGamesRoute() {
    get("/games") {
        call.respond(service.getAllGames())
    }
}

fun Route.singleGameRoute() {
    get("/game/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Missing id",
            status = HttpStatusCode.BadRequest
        )
        val game = service.getGameById(id.toInt()) ?: return@get call.respondText(
            "No game with id $id",
            status = HttpStatusCode.NotFound
        )
        call.respond(game)
    }
}