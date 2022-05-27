package com.example.dao

import com.example.model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private const val DRIVER_CLASS_NAME = "org.postgresql.Driver"
    private const val JDBC_URL = "jdbc:postgresql://localhost:5432/nba"
    private const val DB_USER = "user"
    private const val DB_PASSWORD = "password"

    fun connect(): Database {
        return Database.connect(JDBC_URL, DRIVER_CLASS_NAME, DB_USER, DB_PASSWORD)
    }

    fun init() {
        val database = connect()
        transaction(database) {
            SchemaUtils.drop(FavoritePlayers)
            SchemaUtils.drop(FavoriteTeams)
            SchemaUtils.drop(Players)
            SchemaUtils.drop(Games)
            SchemaUtils.drop(Teams)
        }
        transaction(database) {
            SchemaUtils.create(Teams)
            SchemaUtils.create(Games)
            SchemaUtils.create(Players)
            SchemaUtils.create(FavoritePlayers)
            SchemaUtils.create(FavoriteTeams)
        }
    }
}
