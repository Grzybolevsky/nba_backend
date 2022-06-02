package com.example.model

import com.example.dao.DAOFacadeTeam
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

@Serializable
data class Player(
    @SerialName("id") val playerId: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("height_feet") val heightFeet: Int?,
    @SerialName("height_inches") val heightInches: Int?,
    @SerialName("weight_pounds") val weightPounds: Int?,
    val team: Team,
    val position: String,
    var imageUrl: String = ""
)

object Players : IntIdTable() {
    val playerId: Column<Int> = integer("playerId").uniqueIndex()
    val firstName: Column<String> = varchar("firstName", 128)
    val lastName: Column<String> = varchar("lastName", 128)
    val heightFeet: Column<Int> = integer("heightFeet")
    val heightInches: Column<Int> = integer("heightInches")
    val weightPounds: Column<Int> = integer("weightPounds")
    val teamID: Column<Int> = integer("teamID").references(Teams.teamId)
    val position: Column<String> = varchar("position", 128)
    val imageUrl: Column<String> = varchar("imageUrl", 128)
}

class PlayerEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PlayerEntity>(Players)

    var playerId by Players.playerId
    var firstName by Players.firstName
    var lastName by Players.lastName
    var heightFeet by Players.heightFeet
    var heightInches by Players.heightInches
    var weightPounds by Players.weightPounds
    var teamID by Players.teamID
    var position by Players.position
    var imageUrl by Players.imageUrl

    fun toDomain(): Player {
        return Player(
            playerId = playerId,
            firstName = firstName,
            lastName = lastName,
            heightFeet = heightFeet,
            heightInches = heightInches,
            weightPounds = weightPounds,
            team = DAOFacadeTeam.getTeamById(teamID)!!,
            position = position,
            imageUrl = imageUrl
        )
    }
}
