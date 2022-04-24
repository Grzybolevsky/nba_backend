package com.example

import com.example.model.Player
import com.example.plugins.configureHTTP
import com.example.plugins.configureMonitoring
import com.example.plugins.configureRouting
import com.example.plugins.configureSecurity
import com.example.plugins.configureSerialization
import com.example.plugins.configureSockets
import com.example.plugins.configureTemplating
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

fun getDataFromBalldontile(urlString: String): JSONArray {
    val url = URL(urlString)
    val connection = url.openConnection()
    var dataString = ""
    BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
        var line: String?
        while (inp.readLine().also { line = it } != null) {
            dataString += line
        }
    }
    return JSONObject(dataString).getJSONArray("data")
}

fun main() {
    val playersURL = "https://www.balldontlie.io/api/v1/players"
    val playersData = getDataFromBalldontile(playersURL)

    val gamesURL = "https://www.balldontlie.io/api/v1/games"
    val gamesData = getDataFromBalldontile(gamesURL)

    val players = ArrayList<Player>()
    for (i in 0 until playersData.length()) {
        val tempPlayerJSON = playersData.getJSONObject(i)
        val tempPlayer = Player(
            tempPlayerJSON["id"] as Int,
            tempPlayerJSON["first_name"] as String,
            tempPlayerJSON["last_name"] as String,
            tempPlayerJSON["position"] as String
        )
        players.add(tempPlayer)
    }

    println("===============================")
    println("EXAMPLE FIRST PLAYER: ")
    println(players[0].firstName + " " + players[0].lastName + " " + players[0].position)
    println("===============================")

    println("===============================")
    println("EXAMPLE FIRST GAME: ")
    println(gamesData[0])
    println("===============================")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureMonitoring()
        configureTemplating()
        configureSockets()
        configureSerialization()
        configureHTTP()
    }.start(wait = true)
}
