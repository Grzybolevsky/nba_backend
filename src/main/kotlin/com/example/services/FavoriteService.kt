package com.example.services

import com.example.dao.DAOFacadeFavorites
import com.example.model.FavoritePlayer
import com.example.model.FavoriteTeam

object FavoriteService {
    fun getFavoritePlayers(userId: Int): List<FavoritePlayer> {
        return DAOFacadeFavorites.getFavoritePlayers(userId)
    }

    fun deleteFavoritePlayer(userId: Int, playerId: Int): Boolean {
        return DAOFacadeFavorites.deleteFavoritePlayer(userId, playerId)
    }

    fun addFavoritePlayer(userId: Int, playerId: Int) {
        DAOFacadeFavorites.addFavoritePlayer(userId, playerId)
    }

    fun getFavoriteTeams(userId: Int): List<FavoriteTeam> {
        return DAOFacadeFavorites.getFavoriteTeams(userId)
    }

    fun addFavoriteTeam(userId: Int, teamId: Int) {
        DAOFacadeFavorites.addFavoriteTeam(userId, teamId)
    }

    fun deleteFavoriteTeam(userId: Int, teamId: Int): Boolean {
        return DAOFacadeFavorites.deleteFavoriteTeam(userId, teamId)
    }
}
