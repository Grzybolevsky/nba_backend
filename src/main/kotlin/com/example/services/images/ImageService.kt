package com.example.services.images

import com.example.services.HttpClientService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object ImageService {
    private const val API = "https://data.nba.net/data/10s/prod/v1/2020/players.json"
    private const val IMAGE_API = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"
    private val format = Json { ignoreUnknownKeys = true }

    fun getPlayerIds(): List<PlayerInfo> {
        val resultString = HttpClientService.getFromUrl(API)
        val request = format.decodeFromString<Request>(resultString)
        return request.league.standard
    }

    fun getImageUrlForId(id: Int): String {
        return "$IMAGE_API/$id.png"
    }
}
