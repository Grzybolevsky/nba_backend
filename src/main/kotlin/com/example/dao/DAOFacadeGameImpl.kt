package com.example.dao

import com.example.model.Game
import com.example.model.Games
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import com.example.dao.DatabaseFactory.dbQuery

class DAOFacadeGameImpl : DAOFacadeGame {

    private val daoTeams: DAOFacadeTeam = DAOFacadeTeamImpl()
    private suspend fun resultRowToGame(row: ResultRow) = Game(
        id = row[Games.id],
        date = row[Games.date],
        homeTeam = daoTeams.getTeamById(row[Games.homeTeamId])!!,
        visitorTeam = daoTeams.getTeamById(row[Games.visitorTeamId])!!,
        homeTeamScore = row[Games.homeTeamScore],
        visitorTeamScore = row[Games.visitorTeamScore],
        period = row[Games.period],
        season = row[Games.season],
        status = row[Games.status],
    )
    override suspend fun getAllGames(): List<Game> = dbQuery {
        Games.selectAll().map { resultRowToGame(it) }
    }

    override suspend fun getGameById(id: Int): Game {
        TODO("Not yet implemented")
    }

    override suspend fun addNewGame(game:Game): Game? = dbQuery {
        val insertStatement = Games.insert {
            it[id] = game.id
            it[date] = game.date
            it[homeTeamId] = game.homeTeam.id
            it[visitorTeamId] = game.visitorTeam.id
            it[homeTeamScore] = game.homeTeamScore
            it[visitorTeamScore] = game.visitorTeamScore
            it[period] = game.period
            it[season] = game.season
            it[status] = game.status
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToGame(it) }
    }

    override suspend fun addNewGames(game: List<Game>) {
        TODO("Not yet implemented")
    }
}
