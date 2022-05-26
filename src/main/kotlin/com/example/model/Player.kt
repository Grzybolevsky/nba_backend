package com.example.model

import kotlinx.serialization.json.JsonNames
import org.jetbrains.exposed.sql.Column
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
    val id: Column<Int> = integer("id")
    val firstName: Column<String> = varchar("firstName", 128)
    val lastName: Column<String> = varchar("lastName", 128)
    val heightFeet: Column<Int> = integer("heightFeet")
    val heightInches:Column<Int> = integer("heightInches")
    val weightPounds:Column<Int> = integer("weightPounds")
    val teamID:Column<Int> = integer("TeamID").references(Teams.id)
    val position: Column<String> = varchar("position", 128)
    val imageUrl: Column<String> = varchar("imageUrl", 128)

    override val primaryKey = PrimaryKey(id)
}
