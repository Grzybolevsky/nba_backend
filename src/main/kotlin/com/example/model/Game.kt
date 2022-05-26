package com.example.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.JsonNames
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date

@kotlinx.serialization.Serializable
data class Game(
    val id: Int,
    val date: LocalDate,
    @JsonNames("home_team") val homeTeam: Team,
    @JsonNames("visitor_team") val visitorTeam: Team,
    @JsonNames("home_team_score") val homeTeamScore: Int,
    @JsonNames("visitor_team_score") val visitorTeamScore: Int,
    val period: Int,
    val season: Int,
    val status: String
)

object Games : Table() {
    val id: Column<Int> = integer("id")
    val date: Column<LocalDate> = date("date")
    val homeTeamId: Column<Int> = integer("homeTeamID").references(Teams.id)
    val visitorTeamId: Column<Int> = integer("visitorTeamID").references(Teams.id)
    val homeTeamScore: Column<Int> = integer("homeTeamScore")
    val visitorTeamScore: Column<Int> = integer("visitorTeamScore")
    val period: Column<Int> = integer("period")
    val season: Column<Int> = integer("season")
    val status: Column<String> = varchar("status", 128)

    override val primaryKey = PrimaryKey(id)
}
