package com.example.dao

import com.example.model.Team

interface DAOFacadeTeam {
    suspend fun allTeams(): List<Team>
    suspend fun addNewTeam(
        id: Int,
        abbreviation: String,
        city: String,
        conference: String,
        division: String,
        fullName: String,
        name: String
    ): Team?
}
