package com.example.services

import com.example.dao.DAOFacadeTeam
import com.example.dao.DAOFacadeTeamImpl
import com.example.model.Player
import com.example.model.Team
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import org.json.JSONArray
import org.json.JSONObject

val format = Json { prettyPrint = true }

class BalldontileInfoService {

    companion object {

        private fun getDataFromBalldontile(urlString: String): JSONArray {
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

        suspend fun downloadData(): Void? {
            val playersURL = "https://www.balldontlie.io/api/v1/players"
            val playersData: JSONArray = getDataFromBalldontile(playersURL)

            val gamesURL = "https://www.balldontlie.io/api/v1/games"
            val gamesData: JSONArray = getDataFromBalldontile(gamesURL)

            val teamsURL = "https://www.balldontlie.io/api/v1/teams"
            val teamsData: JSONArray = getDataFromBalldontile(teamsURL)

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

            val teams: ArrayList<Team> = ArrayList()

            val dao: DAOFacadeTeam = DAOFacadeTeamImpl()

            for (i in 0 until teamsData.length()) {
                val tempTeam: JSONObject = teamsData.getJSONObject(i)
                dao.addNewTeam(
                    tempTeam["id"] as Int,
                    tempTeam["abbreviation"] as String,
                    tempTeam["city"] as String,
                    tempTeam["conference"] as String,
                    tempTeam["division"] as String,
                    tempTeam["full_name"] as String,
                    tempTeam["name"] as String,
                )
            }

            return null
        }
    }
}
