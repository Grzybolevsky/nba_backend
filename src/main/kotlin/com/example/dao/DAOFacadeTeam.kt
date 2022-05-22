package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Team
import com.example.model.Teams
import org.jetbrains.exposed.sql.*

interface DAOFacadeTeam {
    suspend fun getAllTeams(page: Int, limit: Int): List<Team>
    suspend fun getTeamById(id: Int): Team?
    suspend fun addNewTeam(team: Team): Team?
    suspend fun addNewTeams(teams: List<Team>)
    suspend fun getCount(): Long
}

class DAOFacadeTeamImpl : DAOFacadeTeam {
    private fun resultRowToTeam(row: ResultRow) = Team(
        id = row[Teams.id],
        abbreviation = row[Teams.abbreviation],
        city = row[Teams.city],
        conference = row[Teams.conference],
        division = row[Teams.division],
        fullName = row[Teams.fullName],
        name = row[Teams.name],
    )

    override suspend fun getAllTeams(page: Int, limit: Int): List<Team> = dbQuery {
        Teams.selectAll()
            .limit(limit, offset = (limit * page).toLong())
            .map(::resultRowToTeam)
    }

    override suspend fun getTeamById(id: Int): Team? = dbQuery {
        Teams.select { Teams.id eq id }
            .map(::resultRowToTeam)
            .singleOrNull()
    }

    override suspend fun addNewTeam(team: Team): Team? = dbQuery {
        val insertStatement = Teams.insert {
            it[id] = team.id
            it[abbreviation] = team.abbreviation
            it[city] = team.city
            it[conference] = team.conference
            it[division] = team.division
            it[fullName] = team.fullName
            it[name] = team.name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTeam)
    }

    override suspend fun addNewTeams(teams: List<Team>) = dbQuery {
        val insertStatement = Teams.batchInsert(teams, shouldReturnGeneratedValues = false) {
            this[Teams.id] = it.id
            this[Teams.abbreviation] = it.abbreviation
            this[Teams.city] = it.city
            this[Teams.conference] = it.conference
            this[Teams.division] = it.division
            this[Teams.fullName] = it.fullName
            this[Teams.name] = it.name
        }
    }

    override suspend fun getCount(): Long = dbQuery {
        Teams.selectAll().count()
    }
}
