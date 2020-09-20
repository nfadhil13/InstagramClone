package com.fdev.instagramclone.framework.datasource.cache.database

import androidx.room.*
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.cache.model.UserCacheEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : UserCacheEntity) : Long


    @Query("""
        UPDATE users
        SET
        username = :newUsername,
        profileImage = :profileImage,
        name = :name ,
        bio = :bio ,
        following = :following,
        followers = :followers
        WHERE id = :primaryKey
    """)
    suspend fun updateUser(
            primaryKey : String,
            newUsername : String,
            profileImage: String,
            name: String,
            bio: String,
            following : Int,
            followers : Int,
    ) : Int

    @Query("DELETE FROM users WHERE id= :primaryKey ")
    suspend fun deleteUser(
            primaryKey: String
    ) : Int

    @Query("SELECT password FROM users WHERE id= :primaryKey")
    suspend fun getUserPassword(primaryKey: String): String

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserCacheEntity>

    @Query("""
        SELECT * FROM users 
        ORDER BY latestLoginTime DESC LIMIT 1
    """)
    suspend fun  getLatestLoginUser(): UserCacheEntity?

    @Query("UPDATE users SET latestLoginTime = :latestLoginTime WHERE id = :primaryKey")
    suspend fun updateLastLogin(latestLoginTime: Long , primaryKey: String) : Int

    @Query("UPDATE users SET password = :newPassword WHERE id = :primaryKey")
    suspend fun updateUserPassword(newPassword : String , primaryKey: String) : Int

    @Update
    suspend fun updateUser(user : UserCacheEntity) : Int

}