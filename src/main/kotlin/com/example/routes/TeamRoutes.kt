package com.example.routes

import com.example.services.TeamService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val service = TeamService

fun Application.teamRoutes() {
    routing {
        allTeamsRoute()
    }
}

fun Route.allTeamsRoute() {
    get("/teams") {
        call.respond(service.getAllTeams())
    }
}
