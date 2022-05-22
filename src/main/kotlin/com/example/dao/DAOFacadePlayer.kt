package com.example.dao

import com.example.model.Player

interface DAOFacadePlayer {
    suspend fun allPlayers(): List<Player>

    suspend fun addNewPlayer(
        id: Int,
        firstName: String,
        lastName: String,
        heightFeet: Int?,
        heightInches: Int?,
        weightPounds: Int?,
        teamID: Int,
        position: String,
        imageUrl: String
    ): Player?
}
