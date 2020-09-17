package com.fdev.instagramclone.business.domain.model.modelfactory

import com.fdev.instagramclone.business.domain.model.User
import javax.inject.Inject

class UserFactory @Inject constructor(){

    fun createUnRegisteredUser() : User {
        return User()
    }
}