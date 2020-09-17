package com.fdev.instagramclone.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fdev.instagramclone.business.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager
@Inject
constructor(
        private val firebaseAuth : FirebaseAuth
){

    private val TAG : String = "AppDebug"

    private val _currentUser = MutableLiveData<User?>()

    val currentUser : LiveData<User?>
        get() = _currentUser

    fun login(newUser : User){
        printLogD("Session Manager", "Logging in : $newUser")
        setValue(newUser)
    }

    fun logOut(){
        firebaseAuth.signOut()
        setValue(null)
    }


    private fun setValue(newUser : User?){
        CoroutineScope(Main).launch{
            if(_currentUser.value != newUser){
                _currentUser.value = newUser
            }
        }
    }
}
