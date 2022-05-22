package com.example.model

@kotlinx.serialization.Serializable
data class Favourite(
    val userId: Int,
    val favouritePlayers: List<Player>,
    val favouriteTeams: List<Team>
)