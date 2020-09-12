package com.fdev.instagramclone.framework.datasource.network.abstraction

import com.fdev.instagramclone.business.domain.model.User

interface UserFirestoreService {

    suspend fun addOrUpdateUser(user: User)

    suspend fun getUser(id: String): User?

    suspend fun loginWithEmail(email: String, password: String): User?

    suspend fun signupWithEmail(email: String, username : String , password: String): User?

    suspend fun getCurrentUser(): User

}