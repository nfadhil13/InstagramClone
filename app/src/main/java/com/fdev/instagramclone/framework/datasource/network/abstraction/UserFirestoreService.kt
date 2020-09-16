package com.fdev.instagramclone.framework.datasource.network.abstraction

import com.fdev.instagramclone.business.domain.model.User

interface UserFirestoreService {

    suspend fun addOrUpdateUser(user: User)

    suspend fun deleteUser(user : User)

    suspend fun getUser(id: String): User?

    suspend fun loginWithEmail(email: String, password: String): User?

    suspend fun signupWithEmail(email: String  , password: String): User?

    suspend fun getCurrentUser(): User?

    suspend fun sendEmailVerification(): Boolean

    suspend fun checkIfUsernameExist(username : String) : Boolean

    suspend fun resetEmail(email: String): Boolean

    suspend fun getUserByEmail(email : String) : User?

    suspend fun isUserVerfied(password: String): Boolean


}