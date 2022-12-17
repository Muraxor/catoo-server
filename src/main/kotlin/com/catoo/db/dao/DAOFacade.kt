package com.catoo.db.dao

import com.catoo.db.models.User

interface DAOFacade {
    suspend fun allUsers(): List<User>
    suspend fun user(id: Int): User?
    suspend fun addNewUser(name: String, lastName: String): User?
    suspend fun editUSer(id: Int, name: String, lastName: String): Boolean
    suspend fun deleteUser(id: Int): Boolean
}