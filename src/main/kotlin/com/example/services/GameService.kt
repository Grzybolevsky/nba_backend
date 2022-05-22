package com.example.services

import com.example.dao.*
import com.example.model.Game

object GameService {
    private val games: DAOFacadeGame = DAOFacadeGameImpl()

    suspend fun getCount(): Long {
        return games.getCount()
    }

    suspend fun getAllGames(page: Int, limit: Int): List<Game> {
        return games.getAllGames(page, limit)
    }

    suspend fun getGameById(id: Int): Game? {
        return games.getGameById(id)
    }
}
