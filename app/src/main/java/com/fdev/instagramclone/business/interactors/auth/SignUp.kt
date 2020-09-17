package com.fdev.instagramclone.business.interactors.auth

import com.fdev.instagramclone.business.data.network.NetworkResponseHandler
import com.fdev.instagramclone.business.data.network.abstraction.UserNetworkDataSource
import com.fdev.instagramclone.business.data.util.safeApiCall
import com.fdev.instagramclone.business.data.util.safeFirebaseAuthCall
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.*
import com.fdev.instagramclone.framework.presentation.auth.state.AuthViewState
import com.fdev.instagramclone.framework.presentation.auth.state.LoginViewState
import com.fdev.instagramclone.framework.presentation.auth.state.SignUpViewState
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class SignUp
@Inject
constructor(
        private val userNetworkDataSource: UserNetworkDataSource
) {
    companion object {
        const val SIGNUP_SUCCESS = "Signup success"
        const val SIGNUP_FAILED = "Signup failed"
        const val SIGNUP_FAILED_REGISTERED = "This Email is on Another Account"
    }

    fun signupWithEmail(
            email: String,
            stateEvent: StateEvent
    ): Flow<DataState<AuthViewState>?> = flow {
        printLogD("SignUp", "user trying to signUp : ${email}")
        var tempPassword = UUID.randomUUID().toString()

        val networkCall = safeApiCall(IO , {
            safeFirebaseAuthCall(it)
        }) {
            var user = userNetworkDataSource.getUserByEmail(email)
            if(user!=null){
                printLogD("SignUp", "userfound : ${user.email} , ${user.bio}  , ${user.isRegistered}")
                if(!user.isRegistered){
                    tempPassword = user.bio
                    userNetworkDataSource.loginWithEmail(email,tempPassword)
                    userNetworkDataSource.sendEmailVerfication()
                }
            }else{
                user = userNetworkDataSource.signupWithEmail(email,tempPassword)
                user?.let{
                    user.bio = tempPassword
                    userNetworkDataSource.addorUpdateUser(it)
                }
            }
            user
        }

        val result = object : NetworkResponseHandler<AuthViewState, User>(
                response = networkCall,
                stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(resultObj: User): DataState<AuthViewState>? {
                return if (!resultObj.isRegistered) {
                    printLogD("SignUp", "user trying to signUp : ${resultObj?.email}")
                    DataState.data(
                            response = Response(
                                    message = SIGNUP_SUCCESS,
                                    messageType = MessageType.Success()
                            ),
                            data = AuthViewState(
                                    signUpViewState = SignUpViewState(
                                            succesUser = resultObj,
                                            tempPassword = tempPassword
                                    )),
                            stateEvent = stateEvent
                    )
                } else {
                    DataState.error(
                            response = Response(
                                    message = SIGNUP_FAILED_REGISTERED,
                                    uiComponentType = UIComponentType.Dialog(),
                                    messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                    )
                }
            }

        }.getResult()

        printLogD("LogIn", "Emitting the result")
        emit(result)

    }
}


