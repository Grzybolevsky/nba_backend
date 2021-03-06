package com.example.routes

import com.example.services.PlayerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val service = PlayerService

fun Route.playerRoutes() {
    route("/players") {
        players()
    }
}

fun Route.players() {
    get("") {
        val page = call.request.queryParameters["page"]?.toInt() ?: 0
        val limit = call.request.queryParameters["limit"]?.toInt() ?: 20
        call.respond(HttpStatusCode.OK, service.getAllPlayers(page, limit))
    }

    get("/count") {
        call.respond(HttpStatusCode.OK, service.getCount())
    }

    get("/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Missing id",
            status = HttpStatusCode.BadRequest
        )
        val player = service.getPlayerById(id.toInt()) ?: return@get call.respondText(
            "No player with id $id",
            status = HttpStatusCode.NotFound
        )
        call.respond(HttpStatusCode.OK, player)
    }
}
