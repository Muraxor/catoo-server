package com.catoo.db.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String
)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("firstName", 128)
    val lastName = varchar("lastName", 128)

    override val primaryKey = PrimaryKey(id)
}