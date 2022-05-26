package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.FavoritePlayer
import com.example.model.FavoritePlayers
import com.example.model.FavoriteTeam
import com.example.model.FavoriteTeams
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

interface DAOFacadeFavorites {
    suspend fun getFavoritePlayers(userId: Int): List<FavoritePlayer>
    suspend fun addFavoritePlayer(userId: Int, playerId: Int)
    suspend fun deleteFavoritePlayer(userId: Int, playerId: Int): Int
    suspend fun getFavoriteTeams(userId: Int): List<FavoriteTeam>
    suspend fun addFavoriteTeam(userId: Int, teamId: Int)
    suspend fun deleteFavoriteTeam(userId: Int, teamId: Int): Int
}

class DAOFacadeFavoritesImpl : DAOFacadeFavorites {
    override suspend fun getFavoritePlayers(userId: Int) = dbQuery {
        FavoritePlayers.select { FavoritePlayers.userId eq userId }.map {
            FavoritePlayer(it[FavoritePlayers.userId], it[FavoritePlayers.playerId])
        }.toList()
    }

    override suspend fun addFavoritePlayer(userId: Int, playerId: Int) = dbQuery {
        val insert = FavoritePlayers.insert {
            it[FavoritePlayers.userId] = userId
            it[FavoritePlayers.playerId] = playerId
        }
    }

    override suspend fun deleteFavoritePlayer(userId: Int, playerId: Int) = dbQuery {
        FavoritePlayers.deleteWhere { FavoritePlayers.userId eq userId and (FavoritePlayers.playerId eq playerId) }
    }

    override suspend fun getFavoriteTeams(userId: Int): List<FavoriteTeam> = dbQuery {
        FavoriteTeams.select { FavoriteTeams.userId eq userId }.map {
            FavoriteTeam(it[FavoriteTeams.userId], it[FavoriteTeams.teamId])
        }.toList()
    }

    override suspend fun addFavoriteTeam(userId: Int, teamId: Int) = dbQuery {
        val insert = FavoriteTeams.insert {
            it[FavoriteTeams.userId] = userId
            it[FavoriteTeams.teamId] = teamId
        }
    }

    override suspend fun deleteFavoriteTeam(userId: Int, teamId: Int) = dbQuery {
        FavoriteTeams.deleteWhere { FavoriteTeams.userId eq userId and (FavoriteTeams.teamId eq teamId) }
    }
}