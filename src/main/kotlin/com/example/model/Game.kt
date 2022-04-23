package com.example.model

data class Game (
    val id: Int,
    val date: String,
    val homeTeam: Team,
    val visitorTeam: Team,
    val homeTeamScore: Int,
    val visitorTeamScore: Int,
    val name: String,
    val period: Int,
    val season: Int,
    val status: Int
)