package com.example.routes

import com.example.services.GameService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val service = GameService

fun Application.gameRoutes() {
    routing {
        route("/games") {
            games()
        }
    }
}

fun Route.games() {
    get("") {
        val page = call.request.queryParameters["page"]?.toInt() ?: 0
        val limit = call.request.queryParameters["limit"]?.toInt() ?: 20
        call.respond(service.getAllGames(page, limit))
    }

    get("/count") {
        call.respond(service.getCount())
    }

    get("/{id}") {
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
