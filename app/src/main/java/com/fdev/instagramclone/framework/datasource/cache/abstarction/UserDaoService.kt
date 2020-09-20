package com.fdev.instagramclone.framework.datasource.cache.abstarction

import com.fdev.instagramclone.business.domain.model.User

interface UserDaoService {

    suspend fun addUser(user : User , password : String) : Long

    suspend fun updateUser(
            primaryKey : String,
            newUsername : String,
            profileImage: String,
            name: String,
            bio: String,
            following : List<String>,
            followers : List<String>,
    ) : Int

    suspend fun deleteUser(
            primaryKey: String
    ) : Int

    suspend fun getUserPassword(primaryKey: String): String

    suspend fun getUsers(): List<User>

    suspend fun getLatestLoginUser(): User?

    suspend fun updateUserPassword(primaryKey: String , password: String) : Int

    suspend fun updateLatestLogin(latestLoginTime: Long, primaryKey: String): Int

    suspend fun updateUser(user: User): Int


}