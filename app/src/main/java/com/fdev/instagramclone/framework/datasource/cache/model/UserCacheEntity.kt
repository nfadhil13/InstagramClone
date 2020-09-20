package com.fdev.instagramclone.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserCacheEntity constructor(

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        var id: String,

        @ColumnInfo(name = "username")
        var username: String,

        @ColumnInfo(name = "password")
        var password: String,

        @ColumnInfo(name = "email")
        var email : String,

        @ColumnInfo(name = "profileImage")
        var profileImage: String,

        @ColumnInfo(name = "bio")
        var bio: String = "",

        @ColumnInfo(name = "name")
        var name: String,


        @ColumnInfo(name = "following")
        var following : Int,


        @ColumnInfo(name = "followers")
        var followers : Int,


        @ColumnInfo(name = "latestLoginTime")
        var latestLoginTime : Long,
) {

    constructor() : this(
            "" ,
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0L
    )
}