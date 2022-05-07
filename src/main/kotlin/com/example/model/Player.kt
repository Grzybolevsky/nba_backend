package com.example.model

import kotlinx.serialization.json.JsonNames
import org.jetbrains.exposed.sql.Table

@kotlinx.serialization.Serializable
data class Player(
    val id: Int,
    @JsonNames("first_name") val firstName: String,
    @JsonNames("last_name") val lastName: String,
    val position: String
)

object Players : Table() {
    val id = integer("id")
    val firstName = varchar("firstName", 128)
    val lastName = varchar("lastName", 128)
    val position = varchar("position", 128)

    override val primaryKey = PrimaryKey(id)
}
