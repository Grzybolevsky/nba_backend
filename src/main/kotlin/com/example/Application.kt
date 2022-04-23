package com.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

import com.example.model.Player
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

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
    val rawJSON = JSONObject(dataString)
    return rawJSON.getJSONArray("data")
}

fun main(args: Array<String>) {
    val playersURL = "https://www.balldontlie.io/api/v1/players"
    val playersData: JSONArray = getDataFromBalldontile(playersURL)

    val gamesURL = "https://www.balldontlie.io/api/v1/games"
    val gamesData: JSONArray = getDataFromBalldontile(gamesURL)

    val players: ArrayList<Player> = ArrayList()
    for (i in 0 until playersData.length()) {
        val tempPlayerJSON: JSONObject = playersData.getJSONObject(i)
        val tempPlayer = Player(
            tempPlayerJSON["id"] as Int,
            tempPlayerJSON["first_name"] as String,
            tempPlayerJSON["last_name"] as String,
            tempPlayerJSON["position"] as String
        )
        players.add(tempPlayer)
    }

    val player = players[0]
    println("===============================")
    println("EXAMPLE FIRST PLAYER: ")
    println(player.firstName + " " + player.lastName + " " + player.position)
    println("===============================")

    val game = gamesData[0]
    println("===============================")
    println("EXAMPLE FIRST GAME: ")
    println(game)
    println("===============================")

    runApplication<Application>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
