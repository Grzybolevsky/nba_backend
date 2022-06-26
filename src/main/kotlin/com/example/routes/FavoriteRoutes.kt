package com.example.routes

import com.example.routes.RoutingUtils.MISSING_ID
import com.example.security.UserSession
import com.example.services.FavoriteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

private val service = FavoriteService

fun Route.favoriteRoutes() {
    authenticate("auth-session") {
        route("/favorites") {
            favoritePlayers()
            favoriteTeams()
        }
    }
}

fun Route.favoritePlayers() {
    route("/players") {
        get("") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respond(HttpStatusCode.Forbidden)
                }
                else -> {
                    call.respond(HttpStatusCode.OK, service.getFavoritePlayers(userSession.id))
                }
            }
        }

        post("/{id}") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respond(HttpStatusCode.Forbidden)
                }
                else -> {
                    val id = call.parameters["id"] ?: return@post call.respondText(
                        MISSING_ID,
                        status = HttpStatusCode.BadRequest
                    )
                    call.respond(HttpStatusCode.OK, service.addFavoritePlayer(userSession.id, id.toInt()))
                }
            }
        }

        delete("/{id}") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respond(HttpStatusCode.Forbidden)
                }
                else -> {
                    val id = call.parameters["id"] ?: return@delete call.respondText(
                        MISSING_ID,
                        status = HttpStatusCode.BadRequest
                    )
                    call.respond(HttpStatusCode.OK, service.deleteFavoritePlayer(userSession.id, id.toInt()))
                }
            }
        }
    }
}

fun Route.favoriteTeams() {
    route("/teams") {
        get("") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respond(HttpStatusCode.Forbidden)
                }
                else -> {
                    call.respond(HttpStatusCode.OK, service.getFavoriteTeams(userSession.id))
                }
            }
        }

        post("/{id}") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respond(HttpStatusCode.Forbidden)
                }
                else -> {
                    val id = call.parameters["id"] ?: return@post call.respondText(
                        MISSING_ID,
                        status = HttpStatusCode.BadRequest
                    )
                    call.respond(HttpStatusCode.OK, service.addFavoriteTeam(userSession.id, id.toInt()))
                }
            }
        }

        delete("/{id}") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respond(HttpStatusCode.Forbidden)
                }
                else -> {
                    val id = call.parameters["id"] ?: return@delete call.respondText(
                        MISSING_ID,
                        status = HttpStatusCode.BadRequest
                    )
                    call.respond(HttpStatusCode.OK, service.deleteFavoriteTeam(userSession.id, id.toInt()))
                }
            }
        }
    }
}
