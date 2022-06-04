package com.example.services.balldontile

import com.example.dao.DAOFacadePlayer
import com.example.dao.DAOFacadeTeam
import com.example.model.Player
import com.example.model.Team
import com.example.services.HttpClientService
import com.example.services.images.ImageService
import com.example.services.images.PlayerInfo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.Thread.sleep

object BalldontileInfoService {
    private const val API = "https://www.balldontlie.io/api/v1"
    private val format = Json { ignoreUnknownKeys = true }
    private val playersImageIds = fetchPlayerIds()

    private fun fetchPlayerIds(): List<PlayerInfo> {
        val playerIds = ArrayList<PlayerInfo>()
        (2012..2021).forEach { year ->
            playerIds.addAll(ImageService.getPlayerIdsForYear(year))
        }
        return playerIds
    }

    fun fetchData() {
        fetchTeams()
        fetchPlayers()
    }

    private fun fetchTeams() {
        val teamsURL = "$API/teams"
        val teamsDataString = HttpClientService.getFromUrl(teamsURL)
        val teamsData: RequestData<List<Team>> = format.decodeFromString(teamsDataString)

        DAOFacadeTeam.addNewTeams(teamsData.data)
    }

    private fun fetchPlayers() {
        val (data, meta) = fetchOnePlayersPage(1)

        DAOFacadePlayer.addNewPlayers(data)
        (2..meta.totalPages).forEach { pageNumber ->
            val players = fetchOnePlayersPage(pageNumber).data
            fetchImageUrlsForPlayers(players)
            DAOFacadePlayer.addNewPlayers(players)
            sleep(500)
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
}
