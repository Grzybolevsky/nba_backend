package com.example.routes

import com.example.services.PlayerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val service = PlayerService

fun Application.playerRoutes() {
    routing {
        allPlayersRoute()
        singlePlayerRoute()
    }
}

fun Route.allPlayersRoute() {
    get("/players") {
        call.respond(service.getAllPlayers())
    }
}

fun Route.singlePlayerRoute() {
    get("/player/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Missing id",
            status = HttpStatusCode.BadRequest
        )
        val player = service.getPlayerById(id.toInt()) ?: return@get call.respondText(
            "No player with id $id",
            status = HttpStatusCode.NotFound
        )
        call.respond(player)
    }
}
