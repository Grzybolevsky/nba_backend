package com.example.services.balldontile

import com.example.dao.DAOFacadeTeam
import com.example.dao.DAOFacadeTeamImpl
import com.example.model.Game
import com.example.model.Player
import com.example.model.Team
import com.example.services.HttpClientService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object BalldontileInfoService {

    private const val API = "https://www.balldontlie.io/api/v1"
    private val format = Json { ignoreUnknownKeys = true }

    fun fetchPlayers(): List<Player> {
        val playersURL = "$API/players"
        val playersDataString = HttpClientService.getFromUrl(playersURL)
        val playersData: RequestData<List<Player>> = format.decodeFromString(playersDataString)

        return playersData.data
    }

    private fun fetchGames(): List<Game> {
        val gamesURL = "$API/games"
        val gamesDataString = HttpClientService.getFromUrl(gamesURL)
        val gamesData: RequestData<List<Game>> = format.decodeFromString(gamesDataString)

        return gamesData.data
    }

    fun fetchTeams(): List<Team> {
        val teamsURL = "$API/teams"
        val teamsDataString = HttpClientService.getFromUrl(teamsURL)
        val teamsData: RequestData<List<Team>> = format.decodeFromString(teamsDataString)

        return teamsData.data
    }

    suspend fun fetchData() {
        val teams = fetchTeams()
        val dao: DAOFacadeTeam = DAOFacadeTeamImpl()

        for (team in teams) {
            dao.addNewTeam(
                team.id,
                team.abbreviation,
                team.city,
                team.conference,
                team.division,
                team.fullName,
                team.name
            )
        }
    }
}
