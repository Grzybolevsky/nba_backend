package com.example.dao

import com.example.model.Game
import com.example.model.Team

interface DAOFacadeGame {
    suspend fun allGames(): List<Team>
    suspend fun addNewGame(id: Int, date: String, homeTeam: Team, visitorTeam: Team, homeTeamScore: Int, visitorTeamScore: Int, name: String, period: Int, season: Int, status: Int): Game?
}

