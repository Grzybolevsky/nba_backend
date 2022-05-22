package com.example.routes

import com.example.services.FavoriteService
import io.ktor.server.application.*
import io.ktor.server.routing.*

private val service = FavoriteService

fun Application.favoriteRoutes() {
    routing {
        allFavoritesRoute()
        getFavoriteById()
        updateFavorite()
        addFavorite()
        deleteFavorite()
    }
}

fun Route.allFavoritesRoute() {
    get("/favorites") {
    }
}

fun Route.getFavoriteById() {
    get("/favorite/{id}") {
    }
}

fun Route.updateFavorite() {
    put("/favorite") {
    }
}

fun Route.addFavorite() {
    post("/favorite") {
    }
}

fun Route.deleteFavorite() {
    delete("/favorite") {
    }
}
