package com.catoo.db.dao

import com.catoo.db.dao.DatabaseFactory.dbQuery
import com.catoo.db.models.User
import com.catoo.db.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        firstName = row[Users.firstName],
        lastName = row[Users.lastName],
    )

    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun user(firstName: String, password: String): User? = dbQuery {
        Users.select { Users.firstName eq firstName and (Users.password eq password) }
            .map(::resultRowToUser).singleOrNull()
    }

    override suspend fun userBy(id: Int): User? = dbQuery {
        Users.select { Users.id eq id }.map(::resultRowToUser).singleOrNull()
    }

    override suspend fun addNewUser(name: String, lastName: String, password: String): User? =
        dbQuery {
            Users.insert {
                it[Users.firstName] = name
                it[Users.lastName] = lastName
                it[Users.password] = lastName
            }.resultedValues?.singleOrNull()?.let(::resultRowToUser)
        }

    override suspend fun editUSer(id: Int, name: String, lastName: String): Boolean = dbQuery {
        Users.update({ Users.id eq id }) {
            it[Users.firstName] = name
            it[Users.lastName] = lastName
        } > 0
    }


    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        Users.deleteWhere {
            Users.id eq id
        } > 0
    }
}