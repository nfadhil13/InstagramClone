package com.fdev.instagramclone.business.interactors.auth

import com.fdev.instagramclone.business.data.network.NetworkResponseHandler
import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.data.util.safeApiCall
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.*
import com.fdev.instagramclone.framework.presentation.auth.state.AuthViewState
import com.fdev.instagramclone.framework.presentation.auth.state.WaitVerifiedViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckIsUserVerified
@Inject
constructor(
        private val userNetworkDataSource: UserNetworkDataSource
){

    companion object{
        const val  SUCCESS_GET_USER_VERIFIED_STATUS = "YOU ARE VERIFIED"
        const val  NOT_VERIFIED = "YOU ARE NOT VERIFIED YET , PLEASE CHECK YOUR EMAIL"
    }

    fun waitUserVerified(password :String , stateEvent : StateEvent)
            : Flow<DataState<AuthViewState>?> = flow{

        val apiCall = safeApiCall(IO){
            userNetworkDataSource.isUserVerified(password)
        }

        val result = object : NetworkResponseHandler<AuthViewState,Boolean>(
                response = apiCall,
                stateEvent = stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Boolean): DataState<AuthViewState>? {
                return if(resultObj){
                    DataState.data(
                            response = Response(
                                    message = SUCCESS_GET_USER_VERIFIED_STATUS,
                                    messageType =  MessageType.Info()
                            ),
                            data = AuthViewState(waitVerifiedViewState = WaitVerifiedViewState(resultObj)),
                            stateEvent = stateEvent
                    )
                }else{
                    DataState.data(
                            response = Response(
                                    message = NOT_VERIFIED,
                                    uiComponentType = UIComponentType.Dialog(),
                                    messageType =  MessageType.Info()
                            ),
                            data = AuthViewState(waitVerifiedViewState = WaitVerifiedViewState(resultObj)),
                            stateEvent = stateEvent
                    )
                }
            }

        }.getResult()

        emit(result)
    }

    fun deleteUnverifiedUser(user : User, stateEvent: StateEvent) : Flow<DataState<AuthViewState>?> = flow {
        val apiCall = safeApiCall(IO){
            userNetworkDataSource.deleteUser(user)
        }
    }

}