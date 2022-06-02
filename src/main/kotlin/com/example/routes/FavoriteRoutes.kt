package com.example.routes

import com.example.security.UserSession
import com.example.services.FavoriteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

private val service = FavoriteService

fun Application.favoriteRoutes() {
    routing {
        allFavoritePlayersRoute()
        addFavoritePlayer()
        deleteFavoritePlayer()
        allFavoriteTeamsRoute()
        addFavoriteTeam()
        deleteFavoriteTeam()
    }
}

private const val UNAUTHORIZED = "/unauthorized"
private const val MISSING_ID = "Missing id"

fun Route.allFavoritePlayersRoute() {
    get("/favorite/players") {
        val userSession: UserSession? = call.sessions.get()
        if (userSession != null) {
            call.respond(service.getFavoritePlayers(userSession.id.toInt()))
        } else {
            call.respondRedirect(UNAUTHORIZED)
        }
    }
}

fun Route.addFavoritePlayer() {
    post("/favorite/player/{id}") {
        val userSession: UserSession? = call.sessions.get()
        val id = call.parameters["id"] ?: return@post call.respondText(
            MISSING_ID,
            status = HttpStatusCode.BadRequest
        )
        if (userSession != null) {
            call.respond(service.addFavoritePlayer(userSession.id.toInt(), id.toInt()))
        } else {
            call.respondRedirect(UNAUTHORIZED)
        }
    }
}

fun Route.deleteFavoritePlayer() {
    delete("/favorite/{id}") {
        val userSession: UserSession? = call.sessions.get()
        val id = call.parameters["id"] ?: return@delete call.respondText(
            MISSING_ID,
            status = HttpStatusCode.BadRequest
        )
        if (userSession != null) {
            call.respond(service.deleteFavoritePlayer(userSession.id.toInt(), id.toInt()))
        } else {
            call.respondRedirect(UNAUTHORIZED)
        }
    }
}

fun Route.allFavoriteTeamsRoute() {
    get("/favorite/teams") {
        val userSession: UserSession? = call.sessions.get()
        if (userSession != null) {
            call.respond(service.getFavoriteTeams(userSession.id.toInt()))
        } else {
            call.respondRedirect(UNAUTHORIZED)
        }
    }
}

fun Route.addFavoriteTeam() {
    post("/favorite/team/{id}") {
        val userSession: UserSession? = call.sessions.get()
        val id = call.parameters["id"] ?: return@post call.respondText(
            MISSING_ID,
            status = HttpStatusCode.BadRequest
        )
        if (userSession != null) {
            call.respond(service.addFavoriteTeam(userSession.id.toInt(), id.toInt()))
        } else {
            call.respondRedirect(UNAUTHORIZED)
        }
    }
}

fun Route.deleteFavoriteTeam() {
    delete("/favorite/team/{id}") {
        val userSession: UserSession? = call.sessions.get()
        val id = call.parameters["id"] ?: return@delete call.respondText(
            MISSING_ID,
            status = HttpStatusCode.BadRequest
        )
        if (userSession != null) {
            call.respond(service.deleteFavoriteTeam(userSession.id.toInt(), id.toInt()))
        } else {
            call.respondRedirect(UNAUTHORIZED)
        }
    }
}
