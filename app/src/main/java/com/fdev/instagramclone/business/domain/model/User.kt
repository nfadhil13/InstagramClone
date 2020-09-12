package com.fdev.instagramclone.business.domain.model

data class User
constructor(
        val id: String,
        var username: String,
        var profileImage: String,
        var following : List<User>
        ) {
}