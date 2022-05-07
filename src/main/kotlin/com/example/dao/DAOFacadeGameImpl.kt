package com.example.dao

import com.example.model.Game
import com.example.model.Team

class DAOFacadeGameImpl : DAOFacadeGame {

    override suspend fun allGames(): List<Team> {
        TODO("Not yet implemented")
    }

    override suspend fun addNewGame(
        id: Int,
        date: String,
        homeTeam: Team,
        visitorTeam: Team,
        homeTeamScore: Int,
        visitorTeamScore: Int,
        name: String,
        period: Int,
        season: Int,
        status: Int
    ): Game? {
        TODO("Not yet implemented")
    }
}