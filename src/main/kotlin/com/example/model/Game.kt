package com.example.model

import org.jetbrains.exposed.sql.Table

data class Game(
    val id: Int,
    val date: String,
    val homeTeam: Team,
    val visitorTeam: Team,
    val homeTeamScore: Int,
    val visitorTeamScore: Int,
    val name: String,
    val period: Int,
    val season: Int,
    val status: Int
)

object Games : Table() {
    val id = integer("id")
    val date = varchar("date", 128)
    val homeTeamId = integer("homeTeamID").uniqueIndex().references(Teams.id)
    val visitorTeamId = integer("homeTeamID").uniqueIndex().references(Teams.id)
    val homeTeamScore = integer("homeTeamScore")
    val visitorTeamScore = integer("homeTeamScore")
    val name = varchar("Name", 128)
    val period = integer("period")
    val season = integer("season")
    val status = integer("status")

    override val primaryKey = PrimaryKey(id)
}
