package com.fdev.instagramclone.business.interactors.auth

import com.fdev.instagramclone.business.data.network.NetworkResponseHandler
import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.data.util.safeApiCall
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.model.modelfactory.UserFactory
import com.fdev.instagramclone.business.domain.state.*
import com.fdev.instagramclone.framework.presentation.auth.state.AuthViewState
import com.fdev.instagramclone.framework.presentation.auth.state.EnterNamePasswordViewState

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InputNamePassword @Inject
constructor(
        private val userNetworkDataSource: UserNetworkDataSource,
        private val userFactory: UserFactory
) {

    companion object {
        const val SUCCESS_REGISTER = "Succes enter name , username , and password"
        const val USERNAME_EXIST = "Username is exist"
    }


    fun inputNamePassword(
            user: User,
            password: String,
            stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>?> = flow {

        val networkCall = safeApiCall(IO) {
            val isUserNameExist = userNetworkDataSource.checkIfUsernameExist(user.username)
            if (!isUserNameExist) {
                user.bio = ""
                user.isRegistered = true
                userNetworkDataSource.updatePassword(password)
                userNetworkDataSource.addorUpdateUser(user)
            }
            user
        }

        val result = object : NetworkResponseHandler<AuthViewState, User>(
                response = networkCall,
                stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: User): DataState<AuthViewState>? {
                return if (resultObj.isRegistered) {
                    DataState.data(
                            response = Response(
                                    message = SUCCESS_REGISTER,
                                    messageType = MessageType.Success()
                            ),
                            data = AuthViewState(
                                    inputNamePasswordViewState = EnterNamePasswordViewState(
                                            user = resultObj
                                    )
                            ),
                            stateEvent = stateEvent
                    )
                } else {
                    DataState.error(
                            response = Response(
                                    message = USERNAME_EXIST,
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