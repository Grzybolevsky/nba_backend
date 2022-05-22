package com.example.services

import com.example.dao.*
import com.example.model.Player

object PlayerService {
    private val players: DAOFacadePlayer = DAOFacadePlayerImpl()

    suspend fun getAllPlayers(): List<Player> {
        return players.getAllPlayers()
    }

    suspend fun getPlayerById(id: Int): Player? {
        return players.getPlayerById(id)
    }
}
