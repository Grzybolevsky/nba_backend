package com.example.services

import com.example.dao.*
import com.example.model.Player

object PlayerService {
    private val players: DAOFacadePlayer = DAOFacadePlayerImpl()

    suspend fun getAllPlayers(page: Int, limit: Int): List<Player> {
        return players.getAllPlayers(page, limit)
    }

    suspend fun getPlayerById(id: Int): Player? {
        return players.getPlayerById(id)
    }

    suspend fun getCount(): Long {
        return players.getCount()
    }
}
