package com.example.services

import com.example.dao.DAOFacadeFavorites
import com.example.model.FavoritePlayer
import com.example.model.FavoriteTeam
import com.example.security.UserInfo

object FavoriteService {
    private val userInfoService = UserInfoService

    fun getFavoritePlayers(sessionId: String): List<FavoritePlayer> {
        val userInfo: UserInfo = userInfoService.getUserInfo(sessionId)

        return DAOFacadeFavorites.getFavoritePlayers(userInfo.id.hashCode())
    }

    fun deleteFavoritePlayer(sessionId: String, playerId: Int): Boolean {
        val userInfo: UserInfo = userInfoService.getUserInfo(sessionId)

        return DAOFacadeFavorites.deleteFavoritePlayer(userInfo.id.hashCode(), playerId)
    }

    fun addFavoritePlayer(sessionId: String, playerId: Int) {
        val userInfo: UserInfo = userInfoService.getUserInfo(sessionId)
        DAOFacadeFavorites.addFavoritePlayer(userInfo.id.hashCode(), playerId)
    }

    fun getFavoriteTeams(sessionId: String): List<FavoriteTeam> {
        val userInfo: UserInfo = userInfoService.getUserInfo(sessionId)
        return DAOFacadeFavorites.getFavoriteTeams(userInfo.id.hashCode())
    }

    fun addFavoriteTeam(sessionId: String, teamId: Int) {
        val userInfo: UserInfo = userInfoService.getUserInfo(sessionId)
        DAOFacadeFavorites.addFavoriteTeam(userInfo.id.hashCode(), teamId)
    }

    fun deleteFavoriteTeam(sessionId: String, teamId: Int): Boolean {
        val userInfo: UserInfo = userInfoService.getUserInfo(sessionId)
        return DAOFacadeFavorites.deleteFavoriteTeam(userInfo.id.hashCode(), teamId)
    }
}
