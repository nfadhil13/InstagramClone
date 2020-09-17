package com.fdev.instagramclone.business.data.network.implementation

import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.network.abstraction.UserFirestoreService
import javax.inject.Inject


class UserNetworkDataSourceImpl @Inject constructor(
        private val userFireStoreService: UserFirestoreService
) : UserNetworkDataSource {
    override suspend fun addorUpdateUser(user: User) =
            userFireStoreService.addOrUpdateUser(user)


    override suspend fun getUser(id: String) =
            userFireStoreService.getUser(id)

    override suspend fun deleteUser(user: User)
        = userFireStoreService.deleteUser(user)


    override suspend fun loginWithEmail(email: String, password: String) =
            userFireStoreService.loginWithEmail(email, password)


    override suspend fun signupWithEmail(email: String, password: String) =

            userFireStoreService.signupWithEmail(email,  password)

    override suspend fun getCurrentUser() = userFireStoreService.getCurrentUser()

    override suspend fun sendEmailVerfication() =
            userFireStoreService.sendEmailVerification()

    override suspend fun checkIfUsernameExist(username : String): Boolean
        = userFireStoreService.checkIfUsernameExist(username)

    override suspend fun resetEmail(email: String): Boolean
        = userFireStoreService.resetEmail(email)

    override suspend fun getUserByEmail(email: String): User?
        = userFireStoreService.getUserByEmail(email)

    override suspend fun isUserVerified(password : String): Boolean
        = userFireStoreService.isUserVerfied(password)

    override suspend fun updatePassword(password: String): Boolean
        = userFireStoreService.updatePassword(password)

    override suspend fun sendRestPasswordEmail(email: String): Boolean
        = userFireStoreService.sendRestPasswordEmail(email)

}