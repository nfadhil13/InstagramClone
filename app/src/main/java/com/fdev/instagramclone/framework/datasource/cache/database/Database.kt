package com.fdev.instagramclone.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fdev.instagramclone.framework.datasource.cache.model.UserCacheEntity

@Database(entities = [UserCacheEntity::class] , version = 1 , exportSchema = false)
abstract class Database : RoomDatabase(){

    abstract fun userDao() : UserDao

    companion object{
        val DATABASE_NAME : String = "instagram_clone_db"
    }

}