package com.fdev.instagramclone.framework.datasource.network.implementation

import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.framework.datasource.network.abstraction.UserFirestoreService
import com.fdev.instagramclone.framework.datasource.network.mapper.UserNetworkMapper
import com.fdev.instagramclone.framework.datasource.network.model.UserNetworkEntity
import com.fdev.instagramclone.util.cLog
import com.fdev.instagramclone.util.printLogD
import com.google.firebase.auth.EmailAuthProvider
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
        const val USER_ID_FIELD = "id"
        const val USER_USERNAME_FIELD = "username"
        const val USER_EMAIL_FIELD = "email"
    }

    override suspend fun addOrUpdateUser(user: User) {
        firestore.collection(USER_COLLECTION)
                .document(user.id ?: firestore.collection(USER_COLLECTION).document().id)
                .set(userMapper.mapDomainToEntity(user))
                .await()
    }

    override suspend fun deleteUser(user: User) {
        val firebaseUser = firebaseAuth.currentUser

        firebaseUser?.let{
            if(it.uid.equals(user.id)){
                user.id?.let{
                    firestore.collection(com.fdev.instagramclone.framework.datasource.network.implementation.UserFirestoreServiceImpl.Companion.USER_COLLECTION)
                            .document(it)
                            .delete()
                }
            }
        }



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

    override suspend fun loginWithEmail(email: String, password: String): User? {
        val authResult = firebaseAuth.signInWithEmailAndPassword(
                email , password
        ).await()
        printLogD("UserFirestoreServiceImpl" , "authResult is null ${authResult==null}")
        authResult?.user?.let { firebaseUser ->
            return this.getUser(firebaseUser.uid)
        } ?: return null
    }


    override suspend fun signupWithEmail(email: String , password: String): User? {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(
                    email, password
            ).await()

            authResult?.user?.let { firebaseUser ->
                this.sendEmailVerification()
                return User(
                        id = firebaseUser.uid,
                        username = "",
                        email = firebaseUser.email.toString(),
                        profileImage = USER_DEFAULT_PICTURE_URL,
                        followers = ArrayList(),
                        following = ArrayList()
                )
            } ?: return null
    }

    override suspend fun getCurrentUser(): User? {
        val currentUser = firebaseAuth.currentUser

        currentUser?.let{
            return User(
                    id = it.uid,
                    username = "",
                    email = it.email.toString(),
                    profileImage = USER_DEFAULT_PICTURE_URL,
                    followers = ArrayList(),
                    following = ArrayList()
            )
        }?: return null
    }

    override suspend fun sendEmailVerification(): Boolean {
        val firebaseUser = firebaseAuth.currentUser
        var isSent = false

        firebaseUser?.sendEmailVerification()
                ?.addOnCompleteListener {
                    isSent = true
                }
                ?.addOnFailureListener {
                    cLog("Failed To Sent email")
                }?.await()

        return isSent
    }

    override suspend fun checkIfUsernameExist(username : String): Boolean {
        return !firestore.collection(USER_COLLECTION)
                .whereEqualTo(USER_USERNAME_FIELD , username)
                .get()
                .await()
                .isEmpty
    }

    override suspend fun resetEmail(email: String): Boolean {
        var isSent = false

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    isSent = true
                }
                .addOnFailureListener {
                    cLog("Failed To Sent email")
                }.await()

        return isSent
    }

    override suspend fun getUserByEmail(email: String): User? {
        val checkUserEmail = firestore.collection(USER_COLLECTION)
                .whereEqualTo(USER_EMAIL_FIELD , email)
                .get()
                .await()



        val isEmailExist = !checkUserEmail
                .isEmpty

        printLogD("UserFirestoreServiceImpl" , "isEmailExist $isEmailExist")

        if(isEmailExist){
            val existUser = checkUserEmail.toObjects(UserNetworkEntity::class.java)[0]
            return userMapper.mapEntityToDomain(existUser)
        }else{
            return null
        }
    }

    override suspend fun isUserVerfied(password: String): Boolean {
        val currentFirebaseUser = firebaseAuth.currentUser

        var isVerified = false

        currentFirebaseUser?.let{
            val credential = EmailAuthProvider.getCredential(it.email!! , password)
            isVerified =
                    currentFirebaseUser.reauthenticateAndRetrieveData(credential).await().user?.isEmailVerified ?: false
        }

        return isVerified
    }

    override suspend fun updatePassword(password: String): Boolean {
        val currentFirebaseUser = firebaseAuth.currentUser

        var isSucces = false

        currentFirebaseUser?.updatePassword(password)
                ?.addOnSuccessListener {
                    isSucces = true
                }?.await()

        return isSucces
    }

    override suspend fun sendRestPasswordEmail(email: String): Boolean {
        var isSent = false
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    isSent = true
                }.await()

        return isSent
    }


}