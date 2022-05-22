package com.example.services.balldontile

import com.example.dao.*
import com.example.model.Game
import com.example.model.Player
import com.example.model.Team
import com.example.services.HttpClientService
import com.example.services.images.ImageService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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

        data.forEach { storePlayerInDB(it) }
        (2..meta.totalPages).forEach { pageNumber ->
            fetchOnePlayersPage(pageNumber).data.forEach { storePlayerInDB(it) }
        }
    }

    private fun fetchOnePlayersPage(pageNumber: Int): RequestData<List<Player>> {
        val playersURL = "$API/players?per_page=100&page=$pageNumber"
        val playersDataString = HttpClientService.getFromUrl(playersURL)
        return format.decodeFromString(playersDataString)
    }

    private suspend fun storePlayerInDB(player: Player) {
        val playerInfo = playersImageIds.find {
            it.firstName == player.firstName &&
                    it.lastName == player.lastName
        }

        daoPlayers.addNewPlayer(
            player,
            when {
                playerInfo != null -> ImageService.getImageUrlForId(playerInfo.personId)
                else -> ""
            }
        )
    }

    private suspend fun fetchGames() {
        val (data, meta) = fetchOneGamesPage(1)

        data.forEach { storeGameInDB(it) }
        (2..meta.totalPages).forEach { pageNumber ->
            fetchOneGamesPage(pageNumber).data.forEach { storeGameInDB(it) }
        }
    }

    private fun fetchOneGamesPage(pageNumber: Int): RequestData<List<Game>> {
        val gamesURL = "$API/games?per_page=100&page=$pageNumber"
        val gamesDataString = HttpClientService.getFromUrl(gamesURL)
        return format.decodeFromString(gamesDataString)
    }

    private suspend fun storeGameInDB(game: Game) {
        daoGames.addNewGame(game)
    }

    private suspend fun fetchTeams() {
        val teamsURL = "$API/teams"
        val teamsDataString = HttpClientService.getFromUrl(teamsURL)
        val teamsData: RequestData<List<Team>> = format.decodeFromString(teamsDataString)

        teamsData.data.forEach { storeTeamInDB(it) }
    }

    private suspend fun storeTeamInDB(team: Team) {
        daoTeams.addNewTeam(team)
    }
}
