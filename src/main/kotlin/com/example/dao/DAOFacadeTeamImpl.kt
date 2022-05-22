package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Team
import com.example.model.Teams
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

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

    override suspend fun getAllTeams(): List<Team> = dbQuery {
        Teams.selectAll().map(::resultRowToTeam)
    }

    override suspend fun addNewTeam(team:Team): Team? = dbQuery {
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

    override suspend fun addNewTeams(teams: List<Team>) {
        TODO("Not yet implemented")
    }

    override suspend fun getTeamById(id: Int): Team? = dbQuery {
        Teams.select { Teams.id eq id }
            .map(::resultRowToTeam)
            .singleOrNull()
    }
}
