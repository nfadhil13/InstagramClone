package com.fdev.instagramclone.business.interactors.auth

import com.fdev.instagramclone.business.data.cache.abstraction.UserCacheDataSource
import com.fdev.instagramclone.business.data.network.NetworkResponseHandler
import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.data.util.safeApiCall
import com.fdev.instagramclone.business.data.util.safeCacheCall
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.model.modelfactory.UserFactory
import com.fdev.instagramclone.business.domain.state.*
import com.fdev.instagramclone.framework.presentation.auth.state.AuthViewState
import com.fdev.instagramclone.framework.presentation.auth.state.EnterNamePasswordViewState
import com.fdev.instagramclone.util.printLogD

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InputNamePassword @Inject
constructor(
        private val userNetworkDataSource: UserNetworkDataSource,
        private val userFactory: UserFactory,
        private val userCacheDataSource: UserCacheDataSource
) {

    companion object {
        const val SUCCESS_REGISTER = "Succes enter name , username , and password"
        const val USERNAME_EXIST = "Username is exist"
    }


    fun inputNamePassword(
            user: User,
            stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>?> = flow {

        val networkCall = safeApiCall(IO) {
            val isUserNameExist = userNetworkDataSource.checkIfUsernameExist(user.username)
            printLogD("InputNamePassword" , "${user.username} as username is exist $isUserNameExist")
            if (!isUserNameExist) {
                user.bio = ""
                user.isRegistered = true
                userNetworkDataSource.updatePassword(user.password)
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

        result?.data?.inputNamePasswordViewState?.user?.let{
            val result = safeCacheCall(IO){
                printLogD("InputNamePassword" , "Add user to cache : $it")
                userCacheDataSource.addUser(it , it.password)
            }
            printLogD("InputNamePassword" , "Result : $result")
        }



        emit(result)

    }
}