package com.example.routes

import com.example.routes.RoutingUtils.LOGIN
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

fun Application.favoriteRoutes() {
    routing {
        authenticate("auth-session") {
            route("/api/favorites") {
                favoritePlayers()
                favoriteTeams()
            }
        }
    }
}

fun Route.favoritePlayers() {
    route("/players") {
        get("") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respondRedirect(LOGIN)
                }
                else -> {
                    call.respond(service.getFavoritePlayers(userSession.id.hashCode()))
                }
            }
        }

        post("/{id}") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respondRedirect(LOGIN)
                }
                else -> {
                    val id = call.parameters["id"] ?: return@post call.respondText(
                        MISSING_ID,
                        status = HttpStatusCode.BadRequest
                    )
                    call.respond(service.addFavoritePlayer(userSession.id.hashCode(), id.toInt()))
                }
            }
        }

        delete("/{id}") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respondRedirect(LOGIN)
                }
                else -> {
                    call.sessions.set(userSession.copy(count = userSession.count + 1))
                    val id = call.parameters["id"] ?: return@delete call.respondText(
                        MISSING_ID,
                        status = HttpStatusCode.BadRequest
                    )
                    call.respond(service.deleteFavoritePlayer(userSession.id.hashCode(), id.toInt()))
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
                    call.respondRedirect(LOGIN)
                }
                else -> {
                    call.respond(service.getFavoriteTeams(userSession.id.hashCode()))
                }
            }
        }

        post("/{id}") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respondRedirect(LOGIN)
                }
                else -> {
                    val id = call.parameters["id"] ?: return@post call.respondText(
                        MISSING_ID,
                        status = HttpStatusCode.BadRequest
                    )
                    call.respond(service.addFavoriteTeam(userSession.id.hashCode(), id.toInt()))
                }
            }
        }

        delete("/{id}") {
            when (val userSession: UserSession? = call.sessions.get()) {
                null -> {
                    call.respondRedirect(LOGIN)
                }
                else -> {
                    val id = call.parameters["id"] ?: return@delete call.respondText(
                        MISSING_ID,
                        status = HttpStatusCode.BadRequest
                    )
                    call.respond(service.deleteFavoriteTeam(userSession.id.hashCode(), id.toInt()))
                }
            }
        }
    }
}
