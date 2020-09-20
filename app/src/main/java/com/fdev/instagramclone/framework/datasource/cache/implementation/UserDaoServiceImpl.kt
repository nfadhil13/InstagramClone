package com.fdev.instagramclone.framework.datasource.cache.implementation

import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.cache.abstarction.UserDaoService
import com.fdev.instagramclone.framework.datasource.cache.database.UserDao
import com.fdev.instagramclone.framework.datasource.cache.mappers.UserCacheMapper
import com.fdev.instagramclone.util.printLogD
import javax.inject.Inject

class UserDaoServiceImpl
@Inject
constructor(
        private val userDao : UserDao,
        private val userCacheMapper: UserCacheMapper
): UserDaoService{


    override suspend fun addUser(user: User , password : String): Long {
        val userEntity = userCacheMapper.mapDomainToEntity(user)
        userEntity.password = password
        printLogD("UserDaoServiceImpl" , "Last Password : ${userEntity.password}")
        return userDao.insertUser(userEntity)
    }

    override suspend fun updateUser(primaryKey: String,
                                    newUsername: String,
                                    profileImage: String,
                                    name: String,
                                    bio: String,
                                    following: List<String>,
                                    followers: List<String>): Int {

        return userDao.updateUser(
                primaryKey = primaryKey,
                newUsername = newUsername,
                profileImage = profileImage,
                name = name,
                bio = bio ,
                following = following.size,
                followers = followers.size
        )
    }

    override suspend fun deleteUser(primaryKey: String): Int {
        return userDao.deleteUser(primaryKey)
    }

    override suspend fun getUserPassword(primaryKey: String): String {
        return userDao.getUserPassword(primaryKey)
    }

    override suspend fun getUsers(): List<User> {
        return  userCacheMapper.mapToListDomain(
                userDao.getUsers()
        )
    }

    override suspend fun getLatestLoginUser(): User? {
        return userDao.getLatestLoginUser()?.let{
            userCacheMapper.mapEntityToDomain(
                    it
            )
        }
    }

    override suspend fun updateUserPassword(primaryKey: String, password: String): Int {
        return userDao.updateUserPassword(password , primaryKey)
    }

    override suspend fun updateLatestLogin(latestLoginTime: Long, primaryKey: String): Int {
        return userDao.updateLastLogin(latestLoginTime , primaryKey)
    }

    override suspend fun updateUser(user: User): Int {
        return userDao.updateUser(userCacheMapper.mapDomainToEntity(user))
    }

}