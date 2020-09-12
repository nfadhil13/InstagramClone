package com.fdev.instagramclone.business.data.network.implementation

import com.fdev.instagramclone.business.data.network.abstraction.userNetworkDataSource
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.network.abstraction.UserFirestoreService


class UserNetworkDataSourceImpl constructor(
        private val userFireStoreService: UserFirestoreService
) : userNetworkDataSource {
    override suspend fun addorUpdateUser(user: User) =
            userFireStoreService.addOrUpdateUser(user)


    override suspend fun getUser(id: String) =
            userFireStoreService.getUser(id)


    override suspend fun loginWithEmail(email: String, password: String) =
            userFireStoreService.loginWithEmail(email, password)


    override suspend fun signupWithEmail(email: String, username : String , password: String) =

            userFireStoreService.signupWithEmail(email, username ,  password)

    override suspend fun getCurrentUser(): User = userFireStoreService.getCurrentUser()

}