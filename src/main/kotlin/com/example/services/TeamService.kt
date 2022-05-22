package com.example.services

import com.example.dao.*
import com.example.model.Team

object TeamService {
    private val teams: DAOFacadeTeam = DAOFacadeTeamImpl()

    suspend fun getAllTeams(page: Int, limit: Int): List<Team> {
        return teams.getAllTeams(page, limit)
    }

    suspend fun getTeamById(id: Int): Team? {
        return teams.getTeamById(id)
    }

    suspend fun getCount(): Long {
        return teams.getCount()
    }
}
