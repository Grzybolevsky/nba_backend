package com.example.services.balldontile

import com.example.dao.DAOFacadeGame
import com.example.dao.DAOFacadeGameImpl
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

    private fun fetchOneGamesPage(pageNumber: Int): RequestData<List<Game>> {
        val gamesURL = "$API/games?per_page=100&page=$pageNumber"
        val gamesDataString = HttpClientService.getFromUrl(gamesURL)
        return format.decodeFromString(gamesDataString)
    }

    private fun fetchGames(): List<Game> {
        var allGames: List<Game>
        var gamesData = fetchOneGamesPage(1)
        allGames = gamesData.data
        // var pages = gamesData.meta.totalPages
        var pages = 10

        for (pageNumber in 2..pages) {
            allGames += fetchOneGamesPage(pageNumber).data
        }

        return allGames
    }

    fun fetchTeams(): List<Team> {
        val teamsURL = "$API/teams"
        val teamsDataString = HttpClientService.getFromUrl(teamsURL)
        val teamsData: RequestData<List<Team>> = format.decodeFromString(teamsDataString)

        return teamsData.data
    }

    suspend fun fetchData() {
        val teams = fetchTeams()
        val daoTeams: DAOFacadeTeam = DAOFacadeTeamImpl()

        val games = fetchGames()
        val daoGames: DAOFacadeGame = DAOFacadeGameImpl()

        for (team in teams) {
            daoTeams.addNewTeam(
                team.id,
                team.abbreviation,
                team.city,
                team.conference,
                team.division,
                team.fullName,
                team.name
            )
        }

        for (game in games) {
            daoGames.addNewGame(
                game.id,
                game.date,
                game.homeTeam.id,
                game.visitorTeam.id,
                game.homeTeamScore,
                game.visitorTeamScore,
                game.period,
                game.season,
                game.status
            )
        }
    }
}
