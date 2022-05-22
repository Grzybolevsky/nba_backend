package com.example.services

import com.example.dao.*
import com.example.model.Player

object PlayerService {
    private val players: DAOFacadePlayer = DAOFacadePlayerImpl()
    private val games: DAOFacadeGame = DAOFacadeGameImpl()
    private val teams: DAOFacadeTeam = DAOFacadeTeamImpl()

    suspend fun getAllPlayers(): List<Player> {
        return players.getAllPlayers()
    }
}
