package com.example.model

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

@kotlinx.serialization.Serializable
data class FavoritePlayer(
    val userId: Int,
    val favoritePlayerId: Int
)

@kotlinx.serialization.Serializable
data class FavoriteTeam(
    val userId: Int,
    val favoriteTeamId: Int
)

object FavoritePlayers : IntIdTable() {
    val userId: Column<Int> = integer("userId").index("favoritesPlayersUserIdIdx")
    val playerId: Column<Int> = integer("favoritePlayerId")
}

object FavoriteTeams : Table() {
    val userId: Column<Int> = integer("userId").index("favoritesTeamsUserIdIdx")
    val teamId: Column<Int> = integer("favoriteTeamId")
}
