package com.example.services

import com.example.dao.*
import com.example.model.Game

object GameService {
    private val games: DAOFacadeGame = DAOFacadeGameImpl()

    suspend fun getAllGames(): List<Game> {
        return games.getAllGames()
    }

    suspend fun getGameById(id: Int): Game? {
        return games.getGameById(id)
    }
}
