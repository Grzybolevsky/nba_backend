package com.example.dao

import com.example.model.FavoritePlayers
import com.example.model.FavoriteTeams
import com.example.model.Players
import com.example.model.Teams
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun connect(
        url: String,
        driver: String,
        user: String,
        password: String
    ): Database {
        return Database.connect(url, driver, user, password)
    }

    fun init(database: Database) {
        transaction(database) {
            SchemaUtils.drop(FavoritePlayers)
            SchemaUtils.drop(FavoriteTeams)
            SchemaUtils.drop(Players)
            SchemaUtils.drop(Teams)
        }
        transaction(database) {
            SchemaUtils.create(Teams)
            SchemaUtils.create(Players)
            SchemaUtils.create(FavoritePlayers)
            SchemaUtils.create(FavoriteTeams)
        }
    }
}
