package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

@Serializable
data class FavoritePlayer(
    val userId: Int,
    val favoritePlayerId: Int
)

object FavoritePlayers : IntIdTable() {
    val userId: Column<Int> = integer("userId").index("favoritesPlayersUserIdIdx")
    val playerId: Column<Int> = integer("favoritePlayerId").references(Players.playerId)
    init {
        uniqueIndex("dropMultipleSameFavorites", userId, playerId)
    }
}

class FavoritePlayerEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FavoritePlayerEntity>(FavoritePlayers)

    var userId by FavoritePlayers.userId
    var playerId by FavoritePlayers.playerId
}

@Serializable
data class FavoriteTeam(
    val userId: Int,
    val favoriteTeamId: Int
)

object FavoriteTeams : IntIdTable() {
    val userId: Column<Int> = integer("userId").index("favoritesTeamsUserIdIdx")
    val teamId: Column<Int> = integer("favoriteTeamId").references(Teams.teamId)
}

class FavoriteTeamEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FavoriteTeamEntity>(FavoriteTeams)

    var userId by FavoriteTeams.userId
    var teamId by FavoriteTeams.teamId
}
