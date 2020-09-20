package com.fdev.instagramclone.business.interactors.auth

import com.fdev.instagramclone.business.data.cache.abstraction.UserCacheDataSource
import com.fdev.instagramclone.business.data.network.NetworkResponseHandler
import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.data.util.safeApiCall
import com.fdev.instagramclone.business.data.util.safeCacheCall
import com.fdev.instagramclone.business.data.util.safeFirebaseAuthCall
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.model.modelfactory.UserFactory
import com.fdev.instagramclone.business.domain.state.*
import com.fdev.instagramclone.framework.presentation.auth.state.AuthViewState
import com.fdev.instagramclone.framework.presentation.auth.state.LoginViewState
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogIn
@Inject
constructor(
        private val userNetworkDataSource: UserNetworkDataSource,
        private val userFactory: UserFactory,
        private val userCacheDataSource: UserCacheDataSource
) {

    companion object {
        const val LOGIN_SUCCESS = "Log in success"
        const val LOGIN_FAILED = "Log in failed"
        const val CANT_FIND_ACCOUNT = "Can't Find Account "
    }

    fun logInWithEmail(
            email: String, password: String, stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>?> = flow {

        val networkCall = safeApiCall(IO, {
            safeFirebaseAuthCall(it)
        }) {
            var user = userNetworkDataSource.getUserByEmail(email)
            if(user!=null){
                if(user.isRegistered){
                    user = userNetworkDataSource.loginWithEmail(email, password)
                    user?.password = password
                }
            }else{
                user = userFactory.createUnRegisteredUser()
            }
            user
        }

        val networkResponse = object : NetworkResponseHandler<AuthViewState, User>(
                response = networkCall,
                stateEvent = stateEvent
        ) {

            override suspend fun handleSuccess(resultObj: User): DataState<AuthViewState>? {

                return if(resultObj.isRegistered){
                    DataState.data(
                            response = Response(
                                    message = LOGIN_SUCCESS,
                                    uiComponentType = UIComponentType.SnackBar(),
                                    messageType = MessageType.Success()
                            ),
                            data = AuthViewState(loginViewState = LoginViewState(succesUser = resultObj)),
                            stateEvent = stateEvent
                    )
                }else{
                    DataState.error(
                            response = Response(
                                    message = CANT_FIND_ACCOUNT,
                                    uiComponentType = UIComponentType.Dialog(),
                                    messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                    )
                }



            }

        }.getResult()

        networkResponse?.data?.loginViewState?.succesUser?.let{
            safeCacheCall(IO){
                printLogD("Login" , "Add user to cache : $it")
                userCacheDataSource.addUser(it , it.password)
            }
        }

        emit(networkResponse)






    }


}