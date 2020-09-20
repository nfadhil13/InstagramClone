package com.fdev.instagramclone.business.data.cache.implementation

import com.fdev.instagramclone.business.data.cache.abstraction.UserCacheDataSource
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.cache.abstarction.UserDaoService
import javax.inject.Inject

class UserCacheDataSourceImpl
@Inject
constructor(
       private val userdaoService : UserDaoService
) : UserCacheDataSource{

    override suspend fun addUser(user: User , password: String): Long
        = userdaoService.addUser(user , password)

    override suspend fun updateUser
            (primaryKey: String,
             newUsername: String,
             profileImage: String,
             name: String,
             bio: String,
             following: List<String>,
             followers: List<String>
            ): Int
    = userdaoService.updateUser(
            primaryKey ,
            newUsername ,
            profileImage,
            name,
            bio,
            following,
            followers
    )

    override suspend fun updateUser(user: User): Int
        = userdaoService.updateUser(user)

    override suspend fun updateUserPassword(primaryKey: String, password: String): Int =
            userdaoService.updateUserPassword(primaryKey , password)

    override suspend fun deleteUser(primaryKey: String)
        = userdaoService.deleteUser(primaryKey)

    override suspend fun getUserPassword(primaryKey: String): String
        = userdaoService.getUserPassword(primaryKey)

    override suspend fun getUsers(): List<User>
        = userdaoService.getUsers()

    override suspend fun getLatestLoginUser(): User?
        = userdaoService.getLatestLoginUser()

    override suspend fun updateLatestLogin(latestLoginTime : Long , primaryKey: String)
        = userdaoService.updateLatestLogin(latestLoginTime,primaryKey)

}