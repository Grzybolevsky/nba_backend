package com.example.model

import org.jetbrains.exposed.sql.Table

@kotlinx.serialization.Serializable
data class FavoritePlayer(
    val userId: Int,
    val favoritePlayerId: Int
)

@kotlinx.serialization.Serializable
data class FavoriteTeam(
    val userId: Int,
    val favoritePlayerId: Int
)

object FavoritePlayers : Table() {
    val userId = integer("userId").index("favoritesPlayersUserIdIdx")
    val playerId = integer("favoritePlayerId")
}

object FavoriteTeams : Table() {
    val userId = integer("userId").index("favoritesTeamsUserIdIdx")
    val gameId = integer("favoriteTeamId")
}
