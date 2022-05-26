package com.example.model

import kotlinx.serialization.json.JsonNames
import org.jetbrains.exposed.sql.Column
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
    val id: Column<Int> = integer("id")
    val abbreviation: Column<String> = varchar("abbreviation", 128)
    val city: Column<String> = varchar("city", 128)
    val conference: Column<String> = varchar("conference", 128)
    val division: Column<String> = varchar("division", 128)
    val fullName: Column<String> = varchar("fullName", 128)
    val name: Column<String> = varchar("Name", 128)

    override val primaryKey = PrimaryKey(id)
}
