package com.fdev.instagramclone.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User
constructor(
        var id: String,
        var username: String,
        var password: String,
        var email: String,
        var profileImage: String,
        var name: String = "",
        var bio: String = " ",
        var following: List<String>,
        var followers: List<String>,
        var isRegistered: Boolean = false
) : Parcelable{

    constructor() : this(
            "",
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


    fun isSame(
            user2: User
    ): Boolean {
        return (
                id.equals(user2.id) &&
                        username.equals(user2.username) &&
                        password.equals(user2.password) &&
                        email.equals(user2.email) &&
                        profileImage.equals(user2.profileImage) &&
                        name.equals(user2.name) &&
                        bio.equals(user2.bio) &&
                        following.equals(user2.following) &&
                        followers.equals(user2.followers)
                )
    }
}