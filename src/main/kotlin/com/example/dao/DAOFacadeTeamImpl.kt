package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.model.Team
import com.example.model.Teams
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOFacadeTeamImpl : DAOFacadeTeam {
    fun resultRowToTeam(row: ResultRow) = Team(
        id = row[Teams.id],
        abbreviation = row[Teams.abbreviation],
        city = row[Teams.city],
        conference = row[Teams.conference],
        division = row[Teams.division],
        fullName = row[Teams.fullName],
        name = row[Teams.name],
    )

    override suspend fun allTeams(): List<Team> = dbQuery {
        Teams.selectAll().map(::resultRowToTeam)
    }

    override suspend fun addNewTeam(
        id: Int,
        abbreviation: String,
        city: String,
        conference: String,
        division: String,
        fullName: String,
        name: String
    ): Team? = dbQuery {
        val insertStatement = Teams.insert {
            it[Teams.id] = id
            it[Teams.abbreviation] = abbreviation
            it[Teams.city] = city
            it[Teams.conference] = conference
            it[Teams.division] = division
            it[Teams.fullName] = fullName
            it[Teams.name] = name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTeam)
    }

    override suspend fun team(id: Int): Team? = dbQuery {
        Teams
            .select { Teams.id eq id }
            .map(::resultRowToTeam)
            .singleOrNull()
    }

    /*
    val dao: DAOFacadeTeam = DAOFacadeTeamImpl().apply {
        runBlocking {
            if (allTeams().isEmpty()) {
                addNewTeam(2137, "Papieski Zespol", "Watykan", "JP2", "Kremowki", "Jan Pawel 2", "JP2")
            }
        }
    }
    */
}
