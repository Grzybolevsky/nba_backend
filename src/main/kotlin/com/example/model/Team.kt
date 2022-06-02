package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

@Serializable
data class Team(
    @SerialName("id") val teamId: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @SerialName("full_name") val fullName: String,
    val name: String
)

object Teams : IntIdTable() {
    val teamId: Column<Int> = integer("teamId").uniqueIndex()
    val abbreviation: Column<String> = varchar("abbreviation", 128)
    val city: Column<String> = varchar("city", 128)
    val conference: Column<String> = varchar("conference", 128)
    val division: Column<String> = varchar("division", 128)
    val fullName: Column<String> = varchar("fullName", 128)
    val name: Column<String> = varchar("Name", 128)
}

class TeamEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TeamEntity>(Teams)

    var teamId by Teams.teamId
    var abbreviation by Teams.abbreviation
    var city by Teams.city
    var conference by Teams.conference
    var division by Teams.division
    var fullName by Teams.fullName
    var name by Teams.name

    fun toDomain() = Team(
        teamId = this.teamId,
        abbreviation = this.abbreviation,
        city = this.city,
        conference = this.conference,
        division = this.division,
        fullName = this.fullName,
        name = this.name
    )
}
