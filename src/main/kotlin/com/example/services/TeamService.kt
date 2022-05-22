package com.example.services

import com.example.dao.*
import com.example.model.Team

object TeamService {
    private val players: DAOFacadePlayer = DAOFacadePlayerImpl()
    private val games: DAOFacadeGame = DAOFacadeGameImpl()
    private val teams: DAOFacadeTeam = DAOFacadeTeamImpl()

    suspend fun getAllTeams(): List<Team> {
        return teams.getAllTeams()
    }
}
