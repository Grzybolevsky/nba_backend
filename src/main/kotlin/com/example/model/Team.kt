package com.example.model

import kotlinx.serialization.json.JsonNames
import org.jetbrains.exposed.sql.Table

@kotlinx.serialization.Serializable
data class Team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @JsonNames("full_name") val fullName: String,
    val name: String
)

object Teams : Table() {
    val id = integer("id")
    val abbreviation = varchar("abbreviation", 128)
    val city = varchar("city", 128)
    val conference = varchar("conference", 128)
    val division = varchar("division", 128)
    val fullName = varchar("fullName", 128)
    val name = varchar("Name", 128)

    override val primaryKey = PrimaryKey(id)
}
