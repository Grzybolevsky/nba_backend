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
        gamesCountRoute()
    }
}

fun Route.gamesCountRoute() {
    get("/games/count") {
        call.respond(service.getCount())
    }
}

fun Route.allGamesRoute() {
    get("/games") {
        val page = call.request.queryParameters["page"]?.toInt() ?: 0
        val limit = call.request.queryParameters["limit"]?.toInt() ?: 20
        call.respond(service.getAllGames(page, limit))
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
