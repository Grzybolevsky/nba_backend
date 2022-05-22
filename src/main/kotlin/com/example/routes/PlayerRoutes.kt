package com.example.routes

import com.example.services.PlayerService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val service = PlayerService

fun Application.playerRoutes() {
    routing {
        allPlayersRoute()
    }
}

fun Route.allPlayersRoute() {
    get("/players") {
        call.respond(service.getAllPlayers())
    }
}
