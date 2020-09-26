package com.fdev.instagramclone.business.domain.model

data class Post(

        val postID : String ,

        val postFileURL : String,

        val likes : List<String>,

        val comments : List<String>,

        val user : User,

        var caption : String = "",

        val postTime : Long
)