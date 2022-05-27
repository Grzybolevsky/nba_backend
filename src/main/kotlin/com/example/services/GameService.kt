package com.example.services

import com.example.dao.DAOFacadeGame
import com.example.model.Game

object GameService {
    fun getCount(): Long {
        return DAOFacadeGame.getCount()
    }

    fun getAllGames(page: Int, limit: Int): List<Game> {
        return DAOFacadeGame.getAllGames(page, limit)
    }

    fun getGameById(id: Int): Game? {
        return DAOFacadeGame.getGameById(id)
    }
}
