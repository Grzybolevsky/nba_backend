package com.example.services

import com.example.dao.DAOFacadeFavorites
import com.example.dao.DAOFacadeFavoritesImpl
import com.example.model.FavoritePlayer
import com.example.model.FavoriteTeam

object FavoriteService {
    private val favorites: DAOFacadeFavorites = DAOFacadeFavoritesImpl()

    suspend fun getFavoritePlayers(userId: Int): List<FavoritePlayer> {
        return favorites.getFavoritePlayers(userId)
    }

    suspend fun deleteFavoritePlayer(userId: Int, playerId: Int): Boolean {
        return favorites.deleteFavoritePlayer(userId, playerId) > 0
    }

    suspend fun addFavoritePlayer(userId: Int, playerId: Int) {
        favorites.addFavoritePlayer(userId, playerId)
    }

    suspend fun getFavoriteTeams(userId: Int): List<FavoriteTeam> {
        return favorites.getFavoriteTeams(userId)
    }

    suspend fun addFavoriteTeam(userId: Int, teamId: Int) {
        favorites.addFavoriteTeam(userId, teamId)
    }

    suspend fun deleteFavoriteTeam(userId: Int, teamId: Int): Boolean {
        return favorites.deleteFavoriteTeam(userId, teamId) > 0
    }
}
