package com.example.services.balldontile

import com.example.dao.*
import com.example.model.Game
import com.example.model.Player
import com.example.model.Team
import com.example.services.HttpClientService
import com.example.services.images.ImageService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.Thread.sleep

object BalldontileInfoService {
    private const val API = "https://www.balldontlie.io/api/v1"
    private val format = Json { ignoreUnknownKeys = true }
    private val daoPlayers: DAOFacadePlayer = DAOFacadePlayerImpl()
    private val daoGames: DAOFacadeGame = DAOFacadeGameImpl()
    private val daoTeams: DAOFacadeTeam = DAOFacadeTeamImpl()
    private val playersImageIds = ImageService.getPlayerIds()

    suspend fun fetchData() {
        fetchTeams()
        fetchGames()
        fetchPlayers()
    }

    private suspend fun fetchPlayers() {
        val (data, meta) = fetchOnePlayersPage(1)

        daoPlayers.addNewPlayers(data)
        (2..meta.totalPages).forEach { pageNumber ->
            val players = fetchOnePlayersPage(pageNumber).data
            fetchImageUrlsForPlayers(players)
            daoPlayers.addNewPlayers(players)
            sleep(800)
        }
    }

    private fun fetchImageUrlsForPlayers(players: List<Player>) {
        players.forEach { player ->
            val found = playersImageIds.find {
                it.firstName == player.firstName && it.lastName == player.lastName
            }
            player.imageUrl = when {
                found != null -> ImageService.getImageUrlForId(found.personId)
                else -> ""
            }
        }
    }

    private fun fetchOnePlayersPage(pageNumber: Int): RequestData<List<Player>> {
        val playersURL = "$API/players?per_page=100&page=$pageNumber"
        val playersDataString = HttpClientService.getFromUrl(playersURL)
        return format.decodeFromString(playersDataString)
    }

    private suspend fun fetchGames() {
        val (data, meta) = fetchOneGamesPage(1)

        daoGames.addNewGames(data)
        (2..meta.totalPages).forEach { pageNumber ->
            daoGames.addNewGames(fetchOneGamesPage(pageNumber).data)
            sleep(800)
        }
    }

    private fun fetchOneGamesPage(pageNumber: Int): RequestData<List<Game>> {
        val gamesURL = "$API/games?per_page=100&page=$pageNumber"
        val gamesDataString = HttpClientService.getFromUrl(gamesURL)
        return format.decodeFromString(gamesDataString)
    }

    private suspend fun fetchTeams() {
        val teamsURL = "$API/teams"
        val teamsDataString = HttpClientService.getFromUrl(teamsURL)
        val teamsData: RequestData<List<Team>> = format.decodeFromString(teamsDataString)

        daoTeams.addNewTeams(teamsData.data)
    }
}
