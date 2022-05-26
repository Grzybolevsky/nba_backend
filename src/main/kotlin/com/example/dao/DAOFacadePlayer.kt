package com.example.dao

import com.example.model.Player
import com.example.model.PlayerEntity
import com.example.model.Players
import org.jetbrains.exposed.sql.batchInsert

object DAOFacadePlayer {

    fun getAllPlayers(page: Int, limit: Int): List<Player> {
        return PlayerEntity.all()
            .limit(limit, offset = (limit * page).toLong())
            .map { it.toDomain() }
    }

    fun getPlayerById(id: Int): Player? {
        return PlayerEntity.find { Players.playerId eq id }
            .map { it.toDomain() }
            .singleOrNull()
    }

    fun addNewPlayer(player: Player) {
        PlayerEntity.new {
            playerId = player.playerId
            firstName = player.firstName
            lastName = player.lastName
            heightFeet = player.heightFeet ?: 0
            heightInches = player.heightInches ?: 0
            weightPounds = player.weightPounds ?: 0
            teamID = player.team.teamId
            position = player.position
            imageUrl = player.imageUrl
        }
    }

    fun addNewPlayers(players: List<Player>) {
        Players.batchInsert(players, shouldReturnGeneratedValues = false) {
            this[Players.playerId] = it.playerId
            this[Players.firstName] = it.firstName
            this[Players.lastName] = it.lastName
            this[Players.heightFeet] = it.heightFeet ?: 0
            this[Players.heightInches] = it.heightInches ?: 0
            this[Players.weightPounds] = it.weightPounds ?: 0
            this[Players.teamID] = it.team.teamId
            this[Players.position] = it.position
            this[Players.imageUrl] = it.imageUrl
        }
    }

    fun getCount(): Long {
        return PlayerEntity.count()
    }
}
