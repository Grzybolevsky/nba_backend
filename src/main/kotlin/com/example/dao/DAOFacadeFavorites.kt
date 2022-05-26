package com.example.dao

import com.example.model.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object DAOFacadeFavorites {
    fun getFavoritePlayers(userId: Int): List<FavoritePlayer> {
        return transaction {
            FavoritePlayers.select { FavoritePlayers.userId eq userId }.map {
                FavoritePlayer(it[FavoritePlayers.userId], it[FavoritePlayers.playerId])
            }.toList()
        }
    }

    fun addFavoritePlayer(userId: Int, playerId: Int) {
        transaction {
            FavoritePlayerEntity.new {
                this.userId = userId
                this.playerId = playerId
            }
        }
    }

    fun deleteFavoritePlayer(userId: Int, playerId: Int): Boolean {
        val entity = transaction {
            FavoritePlayerEntity.find { FavoritePlayers.userId eq userId and (FavoritePlayers.playerId eq playerId) }
                .singleOrNull()
        }
        return if (entity != null) {
            entity.delete()
            true
        } else {
            false
        }
    }

    fun getFavoriteTeams(userId: Int): List<FavoriteTeam> {
        return transaction {
            FavoriteTeamEntity.find { FavoriteTeams.userId eq userId }.map {
                FavoriteTeam(it.userId, it.teamId)
            }.toList()
        }
    }

    fun addFavoriteTeam(userId: Int, teamId: Int) {
        transaction {
            FavoriteTeamEntity.new {
                this.userId = userId
                this.teamId = teamId
            }
        }
    }

    fun deleteFavoriteTeam(userId: Int, teamId: Int): Boolean {
        val entity = transaction {
            FavoriteTeamEntity.find { FavoriteTeams.userId eq userId and (FavoriteTeams.teamId eq teamId) }
                .singleOrNull()
        }
        return if (entity != null) {
            entity.delete()
            true
        } else {
            false
        }
    }
}
