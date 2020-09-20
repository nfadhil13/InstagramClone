package com.fdev.instagramclone.framework.datasource.network.model

import com.fdev.instagramclone.business.domain.model.User

data class UserNetworkEntity
constructor(
        var id: String ,
        var username: String,
        var password : String,
        var email : String,
        var profileImage: String,
        var bio: String = "",
        var name: String,
        var following : List<String>,
        var followers : List<String>,
        var isRegistered : Boolean = false
) {

    constructor() : this(
            "" ,
            "",
            "",
            "",
            "",
            "",
            "",
            ArrayList(),
            ArrayList(),
            false
    )
}