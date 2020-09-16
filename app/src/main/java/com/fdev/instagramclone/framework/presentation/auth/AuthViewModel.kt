package com.fdev.instagramclone.framework.presentation.auth

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.fdev.instagramclone.business.domain.model.User
import com.fdev.instagramclone.business.domain.state.DataState
import com.fdev.instagramclone.business.domain.state.StateEvent
import com.fdev.instagramclone.business.interactors.auth.AuthInteractors
import com.fdev.instagramclone.framework.presentation.BaseViewModel
import com.fdev.instagramclone.framework.presentation.auth.state.*
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class AuthViewModel
@ViewModelInject
constructor(
        private val authInteractor: AuthInteractors,
        @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<AuthViewState>() {


    override fun handleNewData(data: AuthViewState) {
        data.let { viewState ->

            viewState.loginViewState?.let {loginViewState ->
                setLoginViewState(loginViewState)
            }

            viewState.signUpViewState?.let{signUpViewState ->
                printLogD("AuthViewModel" , "new signup view state")
                setSignupViewState(signUpViewState)
            }

            viewState.waitVerifiedViewState?.let{ waitVerifiedViewState ->
                setWaitVerifiedViewState(waitVerifiedViewState)
            }
        }
    }

    private fun setWaitVerifiedViewState(waitVerifiedViewState: WaitVerifiedViewState) {
        val update = getCurrentViewStateOrNew()
        if(update.waitVerifiedViewState?.userVerfiedStatus != waitVerifiedViewState.userVerfiedStatus){
            update.waitVerifiedViewState?.userVerfiedStatus = waitVerifiedViewState.userVerfiedStatus
            setViewState(update)
            printLogD("AuthViewModel" , "test ${getCurrentViewStateOrNew().waitVerifiedViewState?.verifiedUser?.email}")
        }
    }

    private fun setSignupViewState(signUpViewState: SignUpViewState?) {
        val update = getCurrentViewStateOrNew()
        if(update.signUpViewState != signUpViewState){
            printLogD("AuthViewModel" , "setting new signup viewstate value")
            update.signUpViewState = signUpViewState
            setViewState(update)
        }
    }

    private fun setLoginViewState(loginViewState: LoginViewState) {
        val update = getCurrentViewStateOrNew()
        if(update.loginViewState != loginViewState){
            update.loginViewState = loginViewState
            setViewState(update)
        }
    }

    fun setSignUpViewStatetoNull(){
        setSignupViewState(null)
    }

    fun setNewVerfiedUser(user : User , password : String){
        val update = getCurrentViewStateOrNew()
        update.waitVerifiedViewState = WaitVerifiedViewState(false,user , password)
        printLogD("AuthViewModel" , "sudah di update : ${update.waitVerifiedViewState?.verifiedUser?.email}")
        setViewState(update)
    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<AuthViewState>?> = when (stateEvent) {

            is AuthStateEvent.LoginStateEvent -> {
                printLogD("AuthViewModel", " Logging in with email : ${stateEvent.email} & password : ${stateEvent.password}")
                authInteractor.loginInteractor.logInWithEmail(stateEvent.email, stateEvent.password, stateEvent)
            }

            is AuthStateEvent.SignupStateEvent -> {
                printLogD("AuthViewModel", " SigningUp with email ${stateEvent.email}")
                authInteractor.signUpInteractor.signupWithEmail(stateEvent.email , stateEvent)
            }

            is AuthStateEvent.CheckUserVerifiedStateEvent -> {
                authInteractor.checkIsUserVerifiedInteractor.waitUserVerified(stateEvent.password ,stateEvent)
            }


            else -> {
                emitInvalidStateEvent(stateEvent)
            }

        }

        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }

}