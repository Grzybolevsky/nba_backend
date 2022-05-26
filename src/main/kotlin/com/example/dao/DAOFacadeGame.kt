package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Game
import com.example.model.Games
import org.jetbrains.exposed.sql.*

interface DAOFacadeGame {
    suspend fun addNewGame(game: Game): Game?
    suspend fun addNewGames(games: List<Game>)
    suspend fun getCount(): Long
    suspend fun getAllGames(page: Int, limit: Int): List<Game>
    suspend fun getGameById(id: Int): Game?
}

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

    override suspend fun getCount()= dbQuery {
        Games.selectAll().count()
    }

    override suspend fun getAllGames(page: Int, limit: Int)= dbQuery {
        Games.selectAll()
            .limit(limit, offset = (limit * page).toLong())
            .map { resultRowToGame(it) }
    }

    override suspend fun getGameById(id: Int) = dbQuery {
        Games.select { Games.id eq id }
            .map { resultRowToGame(it) }
            .singleOrNull()
    }

    override suspend fun addNewGame(game: Game) = dbQuery {
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

    override suspend fun addNewGames(games: List<Game>) = dbQuery {
        val insertStatement = Games.batchInsert(games, shouldReturnGeneratedValues = false) {
            this[Games.id] = it.id
            this[Games.date] = it.date
            this[Games.homeTeamId] = it.homeTeam.id
            this[Games.visitorTeamId] = it.visitorTeam.id
            this[Games.homeTeamScore] = it.homeTeamScore
            this[Games.visitorTeamScore] = it.visitorTeamScore
            this[Games.period] = it.period
            this[Games.season] = it.season
            this[Games.status] = it.status
        }
    }
}
