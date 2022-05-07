package com.example.model

import kotlinx.serialization.json.JsonNames
import org.jetbrains.exposed.sql.Table

@kotlinx.serialization.Serializable
data class Game(
    val id: Int,
    val date: String,
    @JsonNames("home_team") val homeTeam: Team,
    @JsonNames("visitor_team") val visitorTeam: Team,
    @JsonNames("home_team_score") val homeTeamScore: Int,
    @JsonNames("visitor_team_score") val visitorTeamScore: Int,
    val period: Int,
    val season: Int,
    val status: String
)

object Games : Table() {
    val id = integer("id")
    val date = varchar("date", 128)
    val homeTeamId = integer("homeTeamID").references(Teams.id)
    val visitorTeamId = integer("visitorTeamID").references(Teams.id)
    val homeTeamScore = integer("homeTeamScore")
    val visitorTeamScore = integer("visitorTeamScore")
    val period = integer("period")
    val season = integer("season")
    val status = varchar("status", 128)

    override val primaryKey = PrimaryKey(id)
}
