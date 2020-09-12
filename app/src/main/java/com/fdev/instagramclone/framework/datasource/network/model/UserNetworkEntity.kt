package com.fdev.instagramclone.framework.datasource.network.model

import com.fdev.instagramclone.business.domain.model.User

data class UserNetworkEntity
constructor(
        val id: String? = null,
        val username: String,
        val email : String,
        val profileImage: String,
        val following : List<String>,
        val followers : List<String>
) {
}