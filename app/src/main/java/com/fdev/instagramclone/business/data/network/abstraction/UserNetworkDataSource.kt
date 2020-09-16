package com.fdev.instagramclone.business.data.network.abstraction

import com.fdev.instagramclone.business.domain.model.User

interface UserNetworkDataSource {

    suspend fun addorUpdateUser(user : User)

    suspend fun getUser(id : String) : User?

    suspend fun deleteUser(user : User)

    suspend fun loginWithEmail(email : String , password : String) : User?

    suspend fun signupWithEmail(email: String , password: String) : User?

    suspend fun getCurrentUser() : User?

    suspend fun sendEmailVerfication() : Boolean

    suspend fun checkIfUsernameExist(username : String) : Boolean

    suspend fun resetEmail(email : String): Boolean

    suspend fun getUserByEmail(email : String) : User?

    suspend fun isUserVerified(password : String) : Boolean



}
