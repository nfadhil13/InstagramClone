package com.fdev.instagramclone.business.interactors.auth

import com.fdev.instagramclone.business.data.network.NetworkResponseHandler
import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.data.util.safeApiCall
import com.fdev.instagramclone.business.data.util.safeFirebaseAuthCall
import com.fdev.instagramclone.business.domain.state.*
import com.fdev.instagramclone.framework.presentation.auth.state.AuthViewState
import com.fdev.instagramclone.framework.presentation.auth.state.ForgetPasswordViewState
import com.fdev.instagramclone.framework.presentation.auth.state.LoginViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ResetPassword @Inject
constructor(
        private val userNetworkDataSource: UserNetworkDataSource
) {

    companion object {
        const val EMAIL_SENT = "Email has been sent"
        const val EMAIL_SENT_FAILED = "Failed to sent email"
    }

    fun sendResetPasswordbyEmail(
            email: String,
            stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>?> = flow {

        val networkResult = safeApiCall(IO, {
            safeFirebaseAuthCall(it)
        }) {
            userNetworkDataSource.sendRestPasswordEmail(email)
        }

        val result = object : NetworkResponseHandler<AuthViewState, Boolean>(
                response = networkResult,
                stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: Boolean): DataState<AuthViewState>? {
                return if (resultObj) {
                    DataState.data(
                            response = Response(
                                    message = EMAIL_SENT,
                                    uiComponentType = UIComponentType.Toast(),
                                    messageType = MessageType.Success()
                            ),
                            data = AuthViewState(
                                    forgetPasswordViewState = ForgetPasswordViewState
                                    (
                                            forgotenAccountEmail = email,
                                            isSuccess = resultObj
                                    )
                            ),
                            stateEvent = stateEvent
                    )
                } else {
                    DataState.error(
                            response = Response(
                                    message = EMAIL_SENT_FAILED,
                                    uiComponentType = UIComponentType.Dialog(),
                                    messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                    )
                }
            }

        }.getResult()

        emit(result)


    }

}