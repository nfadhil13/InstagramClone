package com.fdev.instagramclone.business.domain.model

data class Comment(

        val id : String,

        val user : User,

        val likes : List<String>,

        val postTime : Long,

        val comments : List<String>

)