package com.example.services

import com.example.dao.DAOFacadeTeam
import com.example.model.Team

object TeamService {
    fun getAllTeams(page: Int, limit: Int): List<Team> {
        return DAOFacadeTeam.getAllTeams(page, limit)
    }

    fun getTeamById(id: Int): Team? {
        return DAOFacadeTeam.getTeamById(id)
    }

    fun getCount(): Long {
        return DAOFacadeTeam.getCount()
    }
}
