package com.fdev.instagramclone.business.interactors.auth

import com.fdev.instagramclone.business.data.network.NetworkResponseHandler
import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.data.util.safeApiCall
import com.fdev.instagramclone.business.data.util.safeFirebaseAuthCall
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.*
import com.fdev.instagramclone.framework.presentation.auth.state.AuthViewState
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LogIn constructor(
        private val userNetworkDataSource: UserNetworkDataSource
) {

    companion object {
        const val LOGIN_SUCCESS = "Log in success"
        const val LOGIN_FAILED = "Log in failed"
    }

    fun logInWithEmail(
            email: String, password: String, stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>?> = flow {

        val networkCall = safeApiCall(IO, {
            safeFirebaseAuthCall(it)
        }) {
            userNetworkDataSource.loginWithEmail(email, password)
        }

        val networkResponse = object : NetworkResponseHandler<AuthViewState, User?>(
                response = networkCall,
                stateEvent = stateEvent
        ) {

            override suspend fun handleSuccess(resultObj: User?): DataState<AuthViewState>? {
                printLogD("LogIn", "Succes with ${resultObj == null}")
                return if (resultObj != null) {
                    DataState.data(
                            response = Response(
                                    message = LOGIN_SUCCESS,
                                    messageType = MessageType.Success()
                            ),
                            data = AuthViewState(succesUser = resultObj),
                            stateEvent = stateEvent
                    )
                } else {
                    DataState.error(
                            response = Response(
                                    message = LOGIN_FAILED,
                                    messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                    )
                }
            }

        }.getResult()

        emit(networkResponse)


    }

}