package com.fdev.instagramclone.business.data.util

import com.fdev.instagramclone.business.data.network.NetworkResult
import com.fdev.instagramclone.util.printLogD
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

suspend fun safeFirebaseAuthCall(
        throwable: Throwable
) : NetworkResult<Nothing>?{
    printLogD("SafeFirebaseAuthCall" , "${throwable.localizedMessage} ")
    return when(throwable){

        is FirebaseAuthInvalidCredentialsException ->{
            NetworkResult.GenericError(
                    errorMessage =  throwable.localizedMessage.toString()
            )
        }

        is FirebaseAuthInvalidUserException -> {
            NetworkResult.GenericError(
                    errorMessage = throwable.localizedMessage.toString()
            )
        }else -> {
            null
        }
    }
}