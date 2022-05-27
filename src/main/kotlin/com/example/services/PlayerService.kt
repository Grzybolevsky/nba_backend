package com.example.services

import com.example.dao.DAOFacadePlayer
import com.example.model.Player

object PlayerService {

    fun getAllPlayers(page: Int, limit: Int): List<Player> {
        return DAOFacadePlayer.getAllPlayers(page, limit)
    }

    fun getPlayerById(id: Int): Player? {
        return DAOFacadePlayer.getPlayerById(id)
    }

    fun getCount(): Long {
        return DAOFacadePlayer.getCount()
    }
}
