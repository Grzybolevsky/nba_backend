package com.example.dao

import com.example.model.Game

interface DAOFacadeGame {
    suspend fun allGames(): List<Game>
    suspend fun addNewGame(
        id: Int,
        date: String,
        homeTeamId: Int,
        visitorTeamId: Int,
        homeTeamScore: Int,
        visitorTeamScore: Int,
        period: Int,
        season: Int,
        status: String
    ): Game?
}
