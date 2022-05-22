package com.example.dao

import com.example.model.Game

interface DAOFacadeGame {
    suspend fun addNewGame(game: Game): Game?
    suspend fun addNewGames(games: List<Game>)
    suspend fun getAllGames(): List<Game>
    suspend fun getGameById(id: Int): Game?
}
