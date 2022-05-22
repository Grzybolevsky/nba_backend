package com.example.services

import com.example.dao.*
import com.example.model.Team

object TeamService {
    private val teams: DAOFacadeTeam = DAOFacadeTeamImpl()

    suspend fun getAllTeams(): List<Team> {
        return teams.getAllTeams()
    }

    suspend fun getTeamById(id: Int): Team? {
        return teams.getTeamById(id)
    }
}
