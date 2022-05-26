package com.example.dao

import com.example.model.Game
import com.example.model.GameEntity
import com.example.model.Games
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction

object DAOFacadeGame {
    fun addNewGame(game: Game) {
        transaction {
            GameEntity.new {
                gameId = game.gameId
                date = game.date.toLocalDateTime(timeZone = TimeZone.UTC)
                homeTeamId = game.homeTeam.teamId
                visitorTeamId = game.visitorTeam.teamId
                homeTeamScore = game.homeTeamScore
                visitorTeamScore = game.visitorTeamScore
                period = game.period
                season = game.season
                status = game.status
            }
        }
    }

    fun addNewGames(games: List<Game>) {
        transaction {
            Games.batchInsert(games, shouldReturnGeneratedValues = false) {
                this[Games.gameId] = it.gameId
                this[Games.date] = it.date.toLocalDateTime(timeZone = TimeZone.UTC)
                this[Games.homeTeamId] = it.homeTeam.teamId
                this[Games.visitorTeamId] = it.visitorTeam.teamId
                this[Games.homeTeamScore] = it.homeTeamScore
                this[Games.visitorTeamScore] = it.visitorTeamScore
                this[Games.period] = it.period
                this[Games.season] = it.season
                this[Games.status] = it.status
            }
        }
    }

    fun getCount(): Long {
        return transaction { GameEntity.count() }
    }

    fun getAllGames(page: Int, limit: Int): List<Game> {
        return transaction {
            GameEntity.all().limit(limit, offset = (limit * page).toLong()).map { it.toDomain() }
        }
    }

    fun getGameById(id: Int): Game? {
        return transaction {
            GameEntity.find { Games.gameId eq id }.map { it.toDomain() }.singleOrNull()
        }
    }
}
