package com.example.model

import kotlinx.serialization.json.JsonNames
import org.jetbrains.exposed.sql.Table

@kotlinx.serialization.Serializable
data class Player(
    val id: Int,
    @JsonNames("first_name") val firstName: String,
    @JsonNames("last_name") val lastName: String,
    @JsonNames("height_feet") val heightFeet: Int?,
    @JsonNames("height_inches") val heightInches: Int?,
    @JsonNames("weight_pounds") val weightPounds: Int?,
    val team: Team,
    val position: String,
    var imageUrl: String = ""
)

object Players : Table() {
    val id = integer("id")
    val firstName = varchar("firstName", 128)
    val lastName = varchar("lastName", 128)
    val heightFeet = integer("heightFeet")
    val heightInches = integer("heightInches")
    val weightPounds = integer("weightPounds")
    val teamID = integer("TeamID").references(Teams.id)
    val position = varchar("position", 128)
    val imageUrl = varchar("imageUrl", 128)

    override val primaryKey = PrimaryKey(id)
}
