package com.example.dao

import com.example.model.Game
import com.example.model.Games
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import com.example.dao.DatabaseFactory.dbQuery

class DAOFacadeGameImpl : DAOFacadeGame {

    private val daoTeams: DAOFacadeTeam = DAOFacadeTeamImpl()
    suspend fun resultRowToGame(row: ResultRow) = Game(
        id = row[Games.id],
        date = row[Games.date],
        homeTeam = daoTeams.team(row[Games.homeTeamId])!!,
        visitorTeam = daoTeams.team(row[Games.visitorTeamId])!!,
        homeTeamScore = row[Games.homeTeamScore],
        visitorTeamScore = row[Games.visitorTeamScore],
        period = row[Games.period],
        season = row[Games.season],
        status = row[Games.status],
    )
    override suspend fun allGames(): List<Game> = dbQuery {
        Games.selectAll().map { resultRowToGame(it) }
    }

    override suspend fun addNewGame(
        id: Int,
        date: String,
        homeTeamId: Int,
        visitorTeamId: Int,
        homeTeamScore: Int,
        visitorTeamScore: Int,
        period: Int,
        season: Int,
        status: String
    ): Game? = dbQuery {
        val insertStatement = Games.insert {
            it[Games.id] = id
            it[Games.date] = date
            it[Games.homeTeamId] = homeTeamId
            it[Games.visitorTeamId] = visitorTeamId
            it[Games.homeTeamScore] = homeTeamScore
            it[Games.visitorTeamScore] = visitorTeamScore
            it[Games.period] = period
            it[Games.season] = season
            it[Games.status] = status
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToGame(it) }
    }
}
