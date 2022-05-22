package com.example.dao

import com.example.model.Team

interface DAOFacadeTeam {
    suspend fun getAllTeams(): List<Team>
    suspend fun getTeamById(id: Int): Team?
    suspend fun addNewTeam(team: Team): Team?
    suspend fun addNewTeams(teams: List<Team>)
}
