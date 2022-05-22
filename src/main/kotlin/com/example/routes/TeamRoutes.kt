package com.example.routes

import com.example.services.TeamService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val service = TeamService

fun Application.teamRoutes() {
    routing {
        allTeamsRoute()
        singleTeamRoute()
        countTeams()
    }
}

fun Route.countTeams() {
    get("/teams/count") {
        call.respond(service.getCount())
    }
}

fun Route.allTeamsRoute() {
    get("/teams") {
        val page = call.request.queryParameters["page"]?.toInt() ?: 0
        val limit = call.request.queryParameters["limit"]?.toInt() ?: 20
        call.respond(service.getAllTeams(page, limit))
    }
}

fun Route.singleTeamRoute() {
    get("/team/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Missing id",
            status = HttpStatusCode.BadRequest
        )
        val team = service.getTeamById(id.toInt()) ?: return@get call.respondText(
            "No game with id $id",
            status = HttpStatusCode.NotFound
        )
        call.respond(team)
    }
}
