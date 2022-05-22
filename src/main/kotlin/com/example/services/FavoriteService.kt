package com.example.services

import com.example.model.Player

object FavoriteService {
    fun getFavoritePlayers(user: String): List<Player> {

        return emptyList()
    }

    fun deleteFavoritePlayer(user: String, playerId: Int): Boolean {

        return false
    }

    fun addFavoritePlayer(user: String, playerId: Int): Boolean {
        return false
    }

    fun getFavoriteTeams(user: String) {
    }

    fun addFavoriteTeam(user: String, teamId: Int) {
    }

    fun deleteFavoriteTeam(user: String, teamId: Int) {
    }
}
