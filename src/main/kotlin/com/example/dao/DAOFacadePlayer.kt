package com.example.dao

import com.example.model.Player

interface DAOFacadePlayer {
    suspend fun getAllPlayers(): List<Player>
    suspend fun getPlayerById(id: Int): Player?
    suspend fun addNewPlayer(player: Player): Player?
    suspend fun addNewPlayers(players: List<Player>)
}
