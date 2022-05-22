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
        team = daoTeams.team(row[Players.teamID])!!,
        position = row[Players.position],

    )
    override suspend fun allPlayers(): List<Player> = dbQuery {
        Players.selectAll().map { resultRowToPlayer(it) }
    }

    override suspend fun addNewPlayer(
        id: Int,
        firstName: String,
        lastName: String,
        heightFeet: Int?,
        heightInches: Int?,
        weightPounds: Int?,
        teamID: Int,
        position: String,
        imageUrl: String
    ): Player? = dbQuery {
        val insertStatement = Players.insert {
            it[Players.id] = id
            it[Players.firstName] = firstName
            it[Players.lastName] = lastName
            it[Players.heightFeet] = heightFeet ?: 0
            it[Players.heightInches] = heightInches ?: 0
            it[Players.weightPounds] = weightPounds ?: 0
            it[Players.teamID] = teamID
            it[Players.position] = position
            it[Players.imageUrl] = imageUrl
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToPlayer(it) }
    }
}
