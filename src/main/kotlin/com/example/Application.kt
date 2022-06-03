package com.example

import com.example.dao.DatabaseFactory
import com.example.routes.configureRouting
import com.example.security.configureSecurity
import com.example.serialization.configureSerialization
import com.example.services.balldontile.BalldontileInfoService
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val db_url = System.getenv("DB_URL")
        ?: "jdbc:postgresql://db:5432/nba"
    val db_driver = System.getenv("DB_DRIVER")
        ?: "org.postgresql.Driver"
    val db_user = System.getenv("DB_USER") ?: "user"
    val db_password = System.getenv("DB_PASSWORD") ?: "password"
    val database = DatabaseFactory.connect(
        driver = db_driver,
        url = db_url,
        user = db_user,
        password = db_password
    )
    val runMigration: String = System.getenv("MIGRATION") ?: "false"
    if (runMigration.toBoolean()) {
        launch {
            log.info("Running DB migration")
            DatabaseFactory.init(database)
            BalldontileInfoService.fetchData()
            log.info("Migration completed")
        }
    }
    configureRouting()
    configureSecurity()
    configureSerialization()
}
