package com.example.services.images

@kotlinx.serialization.Serializable
data class PlayerInfo(
    val firstName: String,
    val lastName: String,
    val personId: Int
)

@kotlinx.serialization.Serializable
data class LeagueInfo(
    val standard: List<PlayerInfo>
)

@kotlinx.serialization.Serializable
data class Request(
    val league: LeagueInfo
)
