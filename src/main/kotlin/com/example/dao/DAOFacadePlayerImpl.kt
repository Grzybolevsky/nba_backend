package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Player
import com.example.model.Players
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

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
        position = row[Players.position]
    )

    override suspend fun getAllPlayers(): List<Player> = dbQuery {
        Players.selectAll().map { resultRowToPlayer(it) }
    }

    override suspend fun getPlayerById(id: Int): Player {
        TODO("Not yet implemented")
    }

    override suspend fun addNewPlayer(player: Player, imageUrl: String): Player? = dbQuery {
        val insertStatement = Players.insert {
            it[id] = player.id
            it[firstName] = player.firstName
            it[lastName] = player.lastName
            it[heightFeet] = player.heightFeet ?: 0
            it[heightInches] = player.heightInches ?: 0
            it[weightPounds] = player.weightPounds ?: 0
            it[teamID] = player.team.id
            it[position] = player.position
            it[Players.imageUrl] = imageUrl
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToPlayer(it) }
    }

    override suspend fun addNewPlayers(players: List<Player>) = dbQuery {
        TODO("Not yet implemented")
    }
}
