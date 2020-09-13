package com.fdev.instagramclone.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fdev.instagramclone.business.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SessionManager{

    private val TAG : String = "AppDebug"

    private val _currentUser = MutableLiveData<User?>()

    val currentUser : LiveData<User?>
        get() = _currentUser

    fun login(newUser : User){
        setValue(newUser)
    }

    fun logOut(){
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
