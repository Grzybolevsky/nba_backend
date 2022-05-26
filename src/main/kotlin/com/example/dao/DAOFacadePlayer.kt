package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Player
import com.example.model.Players
import org.jetbrains.exposed.sql.*

interface DAOFacadePlayer {
    suspend fun getAllPlayers(page: Int, limit: Int): List<Player>
    suspend fun getPlayerById(id: Int): Player?
    suspend fun addNewPlayer(player: Player): Player?
    suspend fun addNewPlayers(players: List<Player>)
    suspend fun getCount(): Long
}

class DAOFacadePlayerImpl : DAOFacadePlayer {
    private val daoTeams: DAOFacadeTeam = DAOFacadeTeamImpl()

    private suspend fun resultRowToPlayer(row: ResultRow) = Player(
        id = row[Players.id],
        firstName = row[Players.firstName],
        lastName = row[Players.lastName],
        heightFeet = row[Players.heightFeet],
        heightInches = row[Players.heightInches],
        weightPounds = row[Players.weightPounds],
        team = daoTeams.getTeamById(row[Players.teamID])!!,
        position = row[Players.position],
        imageUrl = row[Players.imageUrl]
    )

    override suspend fun getAllPlayers(page: Int, limit: Int) = dbQuery {
        Players.selectAll()
            .limit(limit, offset = (limit * page).toLong())
            .map { resultRowToPlayer(it) }
    }

    override suspend fun getPlayerById(id: Int) = dbQuery {
        Players.select { Players.id eq id }
            .map { resultRowToPlayer(it) }
            .singleOrNull()
    }

    override suspend fun addNewPlayer(player: Player) = dbQuery {
        val insertStatement = Players.insert {
            it[id] = player.id
            it[firstName] = player.firstName
            it[lastName] = player.lastName
            it[heightFeet] = player.heightFeet ?: 0
            it[heightInches] = player.heightInches ?: 0
            it[weightPounds] = player.weightPounds ?: 0
            it[teamID] = player.team.id
            it[position] = player.position
            it[imageUrl] = player.imageUrl
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToPlayer(it) }
    }

    override suspend fun addNewPlayers(players: List<Player>) = dbQuery {
        val insertStatement = Players.batchInsert(players, shouldReturnGeneratedValues = false) {
            this[Players.id] = it.id
            this[Players.firstName] = it.firstName
            this[Players.lastName] = it.lastName
            this[Players.heightFeet] = it.heightFeet ?: 0
            this[Players.heightInches] = it.heightInches ?: 0
            this[Players.weightPounds] = it.weightPounds ?: 0
            this[Players.teamID] = it.team.id
            this[Players.position] = it.position
            this[Players.imageUrl] = it.imageUrl
        }
    }

    override suspend fun getCount() = dbQuery {
        Players.selectAll().count()
    }
}
