package com.example

import com.example.model.Player
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

@SpringBootApplication
class Application

fun getDataFromBalldontile(urlString: String): JSONArray {
    val url = URL(urlString)
    val connection = url.openConnection()
    var dataString: String = ""
    BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
        var line: String?
        while (inp.readLine().also { line = it } != null) {
            dataString += line
        }
    }
    var rawJSON = JSONObject(dataString)
    var arrayFromJSON: JSONArray = rawJSON.getJSONArray("data")
    return arrayFromJSON
}
fun main(args: Array<String>) {
    val playersURL: String = "https://www.balldontlie.io/api/v1/players"
    var playersData: JSONArray = getDataFromBalldontile(playersURL)

    var gamesURL: String = "https://www.balldontlie.io/api/v1/games"
    var gamesData: JSONArray = getDataFromBalldontile(gamesURL)

    var players: ArrayList<Player> = ArrayList<Player>()
    for (i in 0 until playersData.length()) {
        var tempPlayerJSON: JSONObject = playersData.getJSONObject(i)
        var tempPlayer: Player = Player(tempPlayerJSON["id"] as Int, tempPlayerJSON["first_name"] as String, tempPlayerJSON["last_name"] as String, tempPlayerJSON["position"] as String)
        players.add(tempPlayer)
    }

    var player = players.get(0)
    println("===============================")
    println("EXAMPLE FIRST PLAYER: ")
    println(player.firstName + " " + player.lastName + " " + player.position)
    println("===============================")

    var game = gamesData.get(0)
    println("===============================")
    println("EXAMPLE FIRST GAME: ")
    println(game)
    println("===============================")

    runApplication<Application>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
