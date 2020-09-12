package com.fdev.instagramclone.framework.datasource.network.implementation

import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.network.abstraction.UserFirestoreService
import com.fdev.instagramclone.framework.datasource.network.mapper.UserNetworkMapper
import com.fdev.instagramclone.framework.datasource.network.model.UserNetworkEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFirestoreServiceImpl
@Inject
constructor(
        private val firebaseAuth: FirebaseAuth,
        private val firestore: FirebaseFirestore,
        private val userMapper: UserNetworkMapper
) : UserFirestoreService {

    companion object {
        const val USER_COLLECTION = "users"
        const val USER_DEFAULT_PICTURE_URL = ""
    }

    override suspend fun addOrUpdateUser(user: User) {
        firestore.collection(USER_COLLECTION)
                .document(user.id ?: firestore.collection(USER_COLLECTION).document().id)
                .set(userMapper.mapDomainToEntity(user))
                .await()
    }

    override suspend fun getUser(id: String): User? {
        return firestore.collection(USER_COLLECTION)
                .document(id)
                .get()
                .await()
                .toObject(UserNetworkEntity::class.java)?.let {
                    userMapper.mapEntityToDomain(it)
                }
    }

    override suspend fun loginWithEmail(email: String , password: String): User? {
        val authResult = firebaseAuth.signInWithEmailAndPassword(
                email , password
        ).await()

        authResult?.user?.let { firebaseUser ->
            return this.getUser(firebaseUser.uid)
        } ?: return null
    }


    override suspend fun signupWithEmail(email: String, username : String , password: String): User? {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(
                email , password
        ).await()

        authResult?.user?.let { firebaseUser ->
            return User(
                    id = firebaseUser.uid,
                    username = username,
                    email = firebaseUser.email.toString(),
                    profileImage = USER_DEFAULT_PICTURE_URL,
                    followers = ArrayList(),
                    following = ArrayList()
            )
        } ?: return null
    }

    override suspend fun getCurrentUser(): User {
        val currentUser = firebaseAuth.currentUser
        return User(
                id = "firebaseUser.uid",
                username = "",
                email = "firebaseUser.email.toString()",
                profileImage = USER_DEFAULT_PICTURE_URL,
                followers = ArrayList(),
                following = ArrayList()
        )
    }

}