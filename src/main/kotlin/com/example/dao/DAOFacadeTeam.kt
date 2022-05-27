package com.example.dao

import com.example.model.Team
import com.example.model.TeamEntity
import com.example.model.Teams
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction

object DAOFacadeTeam {
    fun getAllTeams(page: Int, limit: Int): List<Team> {
        return transaction {
            TeamEntity.all()
                .limit(limit, offset = (limit * page).toLong())
                .map(TeamEntity::toDomain)
        }
    }

    fun getTeamById(teamId: Int): Team? {
        return transaction {
            TeamEntity.find { Teams.teamId eq teamId }
                .map(TeamEntity::toDomain)
                .singleOrNull()
        }
    }

    fun addNewTeam(team: Team) {
        TeamEntity.new {
            teamId = team.teamId
            abbreviation = team.abbreviation
            city = team.city
            conference = team.conference
            division = team.division
            fullName = team.fullName
            name = team.name
        }
    }

    fun addNewTeams(teams: List<Team>) {
        transaction {
            Teams.batchInsert(teams, shouldReturnGeneratedValues = false) {
                this[Teams.teamId] = it.teamId
                this[Teams.abbreviation] = it.abbreviation
                this[Teams.city] = it.city
                this[Teams.conference] = it.conference
                this[Teams.division] = it.division
                this[Teams.fullName] = it.fullName
                this[Teams.name] = it.name
            }
        }
    }

    fun getCount(): Long {
        return transaction { TeamEntity.count() }
    }
}
