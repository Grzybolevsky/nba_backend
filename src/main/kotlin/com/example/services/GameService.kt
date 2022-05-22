package com.example.services

import com.example.dao.*
import com.example.model.Game

object GameService {
    private val players: DAOFacadePlayer = DAOFacadePlayerImpl()
    private val games: DAOFacadeGame = DAOFacadeGameImpl()
    private val teams: DAOFacadeTeam = DAOFacadeTeamImpl()

    suspend fun getAllGames(): List<Game> {
        return games.allGames()
    }
}
