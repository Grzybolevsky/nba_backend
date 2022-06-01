package com.example.model

import com.example.dao.DAOFacadeTeam.getTeamById
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonNames
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

@OptIn(ExperimentalSerializationApi::class)
@kotlinx.serialization.Serializable
data class Game(
    @JsonNames("id") val gameId: Int,
    val date: Instant,
    @JsonNames("home_team") val homeTeam: Team,
    @JsonNames("visitor_team") val visitorTeam: Team,
    @JsonNames("home_team_score") val homeTeamScore: Int,
    @JsonNames("visitor_team_score") val visitorTeamScore: Int,
    val period: Int,
    val season: Int,
    val status: String
)

object Games : IntIdTable() {
    val gameId: Column<Int> = integer("gameId").uniqueIndex()
    val date: Column<LocalDateTime> = datetime("date")
    val homeTeamId: Column<Int> = integer("homeTeamID").references(Teams.teamId)
    val visitorTeamId: Column<Int> = integer("visitorTeamID").references(Teams.teamId)
    val homeTeamScore: Column<Int> = integer("homeTeamScore")
    val visitorTeamScore: Column<Int> = integer("visitorTeamScore")
    val period: Column<Int> = integer("period")
    val season: Column<Int> = integer("season")
    val status: Column<String> = varchar("status", 128)
}

class GameEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GameEntity>(Games)

    var gameId by Games.gameId
    var date by Games.date
    var homeTeamId by Games.homeTeamId
    var visitorTeamId by Games.visitorTeamId
    var homeTeamScore by Games.homeTeamScore
    var visitorTeamScore by Games.visitorTeamScore
    var period by Games.period
    var season by Games.season
    var status by Games.status

    fun toDomain(): Game {
        return Game(
            gameId = gameId,
            date = date.toInstant(timeZone = TimeZone.UTC),
            homeTeam = getTeamById(homeTeamId)!!,
            homeTeamScore = homeTeamScore,
            visitorTeam = getTeamById(visitorTeamId)!!,
            visitorTeamScore = visitorTeamScore,
            period = period,
            season = season,
            status = status
        )
    }
}
