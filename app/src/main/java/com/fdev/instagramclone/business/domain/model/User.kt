package com.fdev.instagramclone.business.domain.model

data class User
constructor(
        var id: String?,
        var username: String,
        var email : String,
        var profileImage: String,
        var following : List<String>,
        var followers : List<String>
        ) {
}